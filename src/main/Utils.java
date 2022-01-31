package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.layout.RowLayout;

public class Utils {

	public static boolean checkIfEmail(String email) {
		Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
		Matcher mat = pattern.matcher(email);

		return mat.matches() ? true : false;
	}

	public static RowLayout getJustifiedRowLayout(int style) {
		RowLayout layout = new RowLayout(style);
		layout.justify = true;

		return layout;
	}
}
