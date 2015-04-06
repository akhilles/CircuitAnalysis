package circuit;

import java.util.ArrayList;

import elements.*;

public class Node {
	public char letter;
	public boolean nodeVoltageKnown;
	public double nodeVoltage;
	public ArrayList<Element> leftElements;
	public ArrayList<Element> rightElements;

	public Node(char letter){
		this.letter = letter;
		nodeVoltageKnown = false;
		leftElements = new ArrayList<Element>();
		rightElements = new ArrayList<Element>();
	}
	
	public void setNodeVoltage(double nodeVoltage){
		this.nodeVoltage = nodeVoltage;
		nodeVoltageKnown = true;
	}
	
	public void addAsLeft(Element newElement){
		leftElements.add(newElement);
	}
	
	public void addAsRight(Element newElement){
		rightElements.add(newElement);
	}
	
	public boolean isEssential(){
		return leftElements.size() + rightElements.size() >= 3;
	}
	
	public String generateEquation(){
		if (nodeVoltageKnown)
			return letter + "=" + nodeVoltage;
		String equation = "";
		for (Element a: leftElements){
			String term;
			if (a.currentFlowKnown)
				term = "" + a.currentFlow;
			else if (a.right.nodeVoltageKnown)
				term = "(" + letter + "-" + a.right.nodeVoltage + ")/" + ((Resistor) a).resistance;
			else
				term = "(" + letter + "-" + a.right.letter + ")/" + ((Resistor) a).resistance;
			if (!equation.isEmpty() && term.charAt(0) != '-')
				term = "+" + term;
			equation += term;
		}
		for (Element a: rightElements){
			String term;
			if (a.currentFlowKnown)
				term = "" + a.currentFlow * -1;
			else if (a.left.nodeVoltageKnown)
				term = "(" + letter + "-" + a.left.nodeVoltage + ")/" + ((Resistor) a).resistance;
			else
				term = "(" + letter + "-" + a.left.letter + ")/" + ((Resistor) a).resistance;
			if (!equation.isEmpty() && term.charAt(0) != '-')
				term = "+" + term;
			equation += term;
		}
		return equation + "=0";
	}
	
	public String toString(){
		String output = "Node " + letter + ": ";
		if (isEssential())
			output = "Essential " + output;
		if (nodeVoltageKnown)
			output += Element.doubleToString(nodeVoltage) + " Volts";
		else
			output += "Unknown node voltage";
		if (isEssential() && !nodeVoltageKnown)
			output += "\n(" + generateEquation() + ")";
		for (Element a: leftElements)
			output += "\n-  " + a.toString();
		for (Element a: rightElements)
			output += "\n-  " + a.toString();
		return output;
	}
}
