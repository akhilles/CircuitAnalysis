package elements;

import circuit.Node;

public class VoltageSource extends Element{
	public VoltageSource(Node left, Node right, double voltageDrop) {
		super(left, right);
		setVoltageDrop(voltageDrop);
	}
	
	public String toString(){
		return doubleToString(voltageDrop) + " Volt independent voltage source (" + left.letter + " -> " + right.letter + ")";
	}
}
