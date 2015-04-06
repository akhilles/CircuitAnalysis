package elements;

import circuit.Node;

public class Resistor extends Element{
	public double resistance;
	
	public Resistor(Node left, Node right, double resistance) {
		super(left, right);
		this.resistance = resistance;
	}
	
	public void setVoltageDrop(double voltageDrop){
		super.setVoltageDrop(voltageDrop);
		super.setCurrentFlow(voltageDrop / resistance);
	}
	
	public void setCurrentFlow(double currentFlow){
		super.setCurrentFlow(currentFlow);
		super.setVoltageDrop(currentFlow * resistance);
	}
	
	public String toString(){
		return doubleToString(resistance) + " Ohm resistor, " + super.toString();
	}
}
