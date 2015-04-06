package wolframalpha;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class WolframAlpha {
	private static final String APP_ID = "HRK2U5-UAERQUGUW5";
	
	public static String xmlOutput(String input) throws MalformedURLException, IOException{
		String url = "http://api.wolframalpha.com/v2/query?input=";
		url += URLEncoder.encode(input, "UTF-8");
		url += "&appid=" + APP_ID;
		url += "&includepodid=Result&podstate=Result__Approximate+form&podstate=Result__More+digits";
		System.out.println(url);
		InputStream in = new URL(url).openStream();
		String output = "";
		Scanner scan = new Scanner(in);
		while (scan.hasNextLine())
			output += scan.nextLine() + "\n";
		scan.close();
		return output;
	}
	
	public static String[] solveEquations(String[] equations) throws MalformedURLException, IOException{
		ArrayList<Character> unknowns = new ArrayList<Character>();
		char[] chars = Arrays.toString(equations).toCharArray();
		for (char a: chars)
			if (!unknowns.contains(a) && a >= 'A' && a <= 'Z')
				unknowns.add(a);
		System.out.println(unknowns);
		Collections.sort(unknowns);
		String[] results = new String[unknowns.size()];
		String input = "solve(" + equations[0];
		for (int i = 1; i < equations.length; i ++)
			input += ";" + equations[i];
		input += ")";
		System.out.println(input);
		String output = WolframAlpha.xmlOutput(input);
		for (int i = 0; i < results.length; i ++){
			int startIndex = Math.max(output.indexOf(unknowns.get(i) + "~~") + 3, output.indexOf(unknowns.get(i) + " = ") + 4);
			int endIndex = output.indexOf("</plaintext>");
			System.out.println(startIndex + ", " + endIndex);
			if (i < results.length - 1)
				endIndex = output.indexOf(" and " + unknowns.get(i + 1));
			results[i] = unknowns.get(i) + ": " + output.substring(startIndex, endIndex);
		}
		return results;
	}
}
