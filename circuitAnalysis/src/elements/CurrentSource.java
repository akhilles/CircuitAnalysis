package elements;

import circuit.Node;

public class CurrentSource extends Element{
	public CurrentSource(Node left, Node right, double currentFlow) {
		super(left, right);
		setCurrentFlow(currentFlow);
	}
	
	public String toString(){
		return doubleToString(currentFlow) + " Amp independent current source (" + left.letter + " -> " + right.letter + ")";
	}
}
