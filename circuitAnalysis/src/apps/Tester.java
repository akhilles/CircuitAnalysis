package apps;

import java.io.IOException;
import java.net.MalformedURLException;

import circuit.Circuit;

public class Tester {

	public static void main(String[] args) throws MalformedURLException, IOException{
		Circuit a = new Circuit(4);
		a.addResistor('A', 'B', 10);
		a.addResistor('B', 'C', 10);
		a.addResistor('B', 'D', 20);
		a.addVoltageSource('D', 'A', 10);
		a.addCurrentSource('D', 'C', 3);
		
		Circuit b = new Circuit(4);
		b.addResistor('A', 'B', 4);
		b.addResistor('B', 'C', 80);
		b.addResistor('B', 'D', 10);
		b.addResistor('C', 'D', 5);
		b.addVoltageSource('D', 'A', 144);
		b.addCurrentSource('D', 'C', 3);
		
//		System.out.println(a.toString());
//		System.out.println();
//		a.nodeVoltageAnalysis();
//		System.out.println();
		System.out.println(b.toString());
		System.out.println();
		b.nodeVoltageAnalysis();
		System.out.println();
		System.out.println(b.toString());
	}

}
