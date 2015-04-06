package apps;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import circuit.*;

public class CircuitTools {
	static Scanner stdin = new Scanner(System.in);
	
	public static Circuit readFile(){
		System.out.print("Enter file name: ");
		String fileName = stdin.nextLine();
		String fileLocation = "C:\\Users\\Akhil\\workspace\\Circuit Analysis\\Circuits\\" + fileName;
		if (!fileLocation.contains(".circuit"))
			fileLocation += ".circuit";
		try {
			Scanner fileReader = new Scanner(new File(fileLocation));
			while (fileReader.hasNext())
				System.out.println(fileReader.next());
			fileReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File could not be found.");
		}
		return null;
	}
	
	public static void main(String[] args) {
		readFile();
	}

}
