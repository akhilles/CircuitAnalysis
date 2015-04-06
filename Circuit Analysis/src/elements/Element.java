package elements;

import circuit.Node;

public class Element {
	public double voltageDrop, currentFlow;
	public Node left, right;
	public boolean voltageDropKnown, currentFlowKnown;
	private static final int DECIMAL_PLACES = 3;
	
	public Element(Node left, Node right){
		this.left = left;
		this.right = right;
		voltageDropKnown = false;
		currentFlowKnown = false;
	}
	
	public void setVoltageDrop(double voltageDrop){
		this.voltageDrop = voltageDrop;
		voltageDropKnown = true;
	}
	
	public void setCurrentFlow(double currentFlow){
		this.currentFlow = currentFlow;
		currentFlowKnown = true;
	}
	
	public static String doubleToString(double number){
		if (number == 0)
			return "0";
		String output = "";
		if (number < 0){
			output += "-";
			number *= -1;
		}
		int power = 0;
		if (number >= 10000)
			while (number >= 10){
				number /= 10;
				power ++;
			}
		else if (number < 0.1)
			while (number < 1){
				number *= 10;
				power --;
			}
		output += String.format("%." + DECIMAL_PLACES + "f", number);
		if (power != 0)
			output += " E" + power;
		return output;
	}
	
	public String toString(){
		String volts = "Unknown voltage drop, ";
		if (voltageDropKnown) volts = doubleToString(voltageDrop) + " Volts, ";
		String amps = "Unkown current flow";
		if (currentFlowKnown) amps = doubleToString(currentFlow) + " Amps";
		return volts + amps + " (" + left.letter + " -> " + right.letter + ")";
	}
}
