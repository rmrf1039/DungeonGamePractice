import java.util.*;

public class Utils{
	/**
	 * To make user input easier.
	 * 
	 * @param words The words appear before input
	 * @return String
	 */
	public static String input(String words) {
		Scanner scanner = new Scanner(System.in);

		System.out.print(words);

		return scanner.nextLine();
	}

	/**
	 * To check the existence of element in an array
	 * 
	 * @param array The array to search
	 * @param v The element to check
	 * @return boolean
	 */
	public static <T> boolean arrContains(final T[] array, final T v) {
		if (v == null) {
			for (final T e : array)
				if (e == null)
					return true;
		} else {
			for (final T e : array)
				if (e == v || v.equals(e))
					return true;
		}

		return false;
	}
}