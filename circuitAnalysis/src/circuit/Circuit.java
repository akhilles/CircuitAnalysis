package circuit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import wolframalpha.WolframAlpha;
import elements.*;

public class Circuit {
	private ArrayList<Node> nodes;
	private ArrayList<Resistor> resistors;
	private ArrayList<CurrentSource> currentSources;
	private ArrayList<VoltageSource> voltageSources;
	private Node groundNode;
	
	public Circuit(int numNodes){
		nodes = new ArrayList<Node>(numNodes);
		for (int i = 0; i < numNodes; i ++)
			nodes.add(new Node((char) ('A' + i)));
		resistors = new ArrayList<Resistor>();
		currentSources = new ArrayList<CurrentSource>();
		voltageSources = new ArrayList<VoltageSource>();
		groundNode = null;
	}
	
	private Node getNode(char letter){
		return nodes.get(letter - 'A');
	}
	
	public void addResistor(char left, char right, double resistance){
		Node leftNode = getNode(left);
		Node rightNode = getNode(right);
		Resistor newResistor = new Resistor(leftNode, rightNode, resistance);
		leftNode.addAsLeft(newResistor);
		rightNode.addAsRight(newResistor);
		resistors.add(newResistor);
	}
	
	public void addCurrentSource(char left, char right, double amps){
		Node leftNode = getNode(left);
		Node rightNode = getNode(right);
		CurrentSource newCurrentSource = new CurrentSource(leftNode, rightNode, amps);
		leftNode.addAsLeft(newCurrentSource);
		rightNode.addAsRight(newCurrentSource);
		currentSources.add(newCurrentSource);
	}
	
	public void addVoltageSource(char left, char right, double volts){
		Node leftNode = getNode(left);
		Node rightNode = getNode(right);
		VoltageSource newVoltageSource = new VoltageSource(leftNode, rightNode, volts);
		leftNode.addAsLeft(newVoltageSource);
		rightNode.addAsRight(newVoltageSource);
		voltageSources.add(newVoltageSource);
	}
	
	public void setGroundNode(){
		//voltageSources.get(0).left.setNodeVoltage(0);
		getNode('A').setNodeVoltage(0);
	}
	
	private void analyzeVoltageSources(){
		for (VoltageSource a: voltageSources){
			if (a.left == groundNode)
				a.right.setNodeVoltage(a.voltageDrop);
			if (a.right == groundNode)
				a.left.setNodeVoltage(a.voltageDrop * -1);
		}
	}
	
	private void adjustValues(){
		for (Resistor a: resistors){
			if (a.voltageDropKnown && a.voltageDrop < 0){
				a.voltageDrop *= -1;
				a.currentFlow *= -1;
				a.left.leftElements.remove(a);
				a.left.rightElements.add(a);
				a.right.rightElements.remove(a);
				a.right.leftElements.add(a);
				Node temp = a.left;
				a.left = a.right;
				a.right = temp;
			}
		}
	}
	
	private String[] getEquations(){
		int counter = 0;
		for (Node a: nodes)
			if (!a.nodeVoltageKnown)
				counter ++;
		String[] equations = new String[counter];
		int iterator = 0;
		for (Node a: nodes)
			if (!a.nodeVoltageKnown){
				equations[iterator] = a.generateEquation();
				iterator ++;
			}
		return equations;
	}
	
	public void nodeVoltageAnalysis() throws MalformedURLException, IOException{
		setGroundNode();
		analyzeVoltageSources();
		String[] nodeVoltages = WolframAlpha.solveEquations(getEquations());
		for (String a: nodeVoltages){
			System.out.println(a);
			getNode(a.charAt(0)).setNodeVoltage(Double.parseDouble(a.substring(3)));
		}
		updateResistors();
		adjustValues();
	}
	
	private void updateResistors(){
		for (Resistor a: resistors)
			if (!a.voltageDropKnown && a.right.nodeVoltageKnown && a.left.nodeVoltageKnown)
				a.setVoltageDrop(Math.min(a.left.nodeVoltage - a.right.nodeVoltage, a.right.nodeVoltage - a.left.nodeVoltage));
	}
	
	public String toString(){
		String output = "Ground node: Node " + groundNode.letter;
		for (Node temp: nodes)
			output += "\n" + temp.toString();
		return output;
	}
}
