package apps;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;

import circuit.Circuit;

public class CircuitAnalysis {
	static Scanner stdin = new Scanner(System.in);
	
	public static int getNumNodes(){
		System.out.print("How many nodes are in the circuit? ");
		return stdin.nextInt();
	}
	
	public static void addResistors(Circuit circuit){
		System.out.println();
		System.out.println("Use the following syntax to add resistors:");
		System.out.println("'Left Node','Right Node','Resistance'");
		System.out.println("Ender 'done' after all resistors are added.");
		String input = stdin.next().toUpperCase();
		while (!input.equals("DONE")){
			char left = input.charAt(0);
			char right = input.charAt(2);
			double resistance = Double.parseDouble(input.substring(4));
			circuit.addResistor(left, right, resistance);
			input = stdin.next().toUpperCase();
		}
	}
	
	public static void addVoltageSources(Circuit circuit){
		System.out.println();
		System.out.println("Use the following syntax to add voltage sources:");
		System.out.println("'Left Node','Right Node','Voltage'");
		System.out.println("Ender 'done' after all voltage sources are added.");
		System.out.println("(Note: Enter voltage such that it represents Right Node - Left Node)");
		String input = stdin.next().toUpperCase();
		while (!input.equals("DONE")){
			char left = input.charAt(0);
			char right = input.charAt(2);
			double volts = Double.parseDouble(input.substring(4));
			circuit.addVoltageSource(left, right, volts);
			input = stdin.next().toUpperCase();
		}
	}
	
	public static void addCurrentSources(Circuit circuit){
		System.out.println();
		System.out.println("Use the following syntax to add current sources:");
		System.out.println("'Left Node','Right Node','Current'");
		System.out.println("Ender 'done' after all current sources are added.");
		System.out.println("(Note: Enter current such that it flows Left Node -> Right Node)");
		String input = stdin.next().toUpperCase();
		while (!input.equals("DONE")){
			char left = input.charAt(0);
			char right = input.charAt(2);
			double amps = Double.parseDouble(input.substring(4));
			circuit.addCurrentSource(left, right, amps);
			input = stdin.next().toUpperCase();
		}
	}
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		int numNodes = getNumNodes();
		Circuit circuit = new Circuit(numNodes);
		addResistors(circuit);
		addVoltageSources(circuit);
		addCurrentSources(circuit);
		System.out.println();
		circuit.nodeVoltageAnalysis();
		System.out.println();
		System.out.println(circuit.toString());
	}
}
