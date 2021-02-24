import java.util.*;

public class Decoder{

	public Decoder() {}

	/**
	 * To separate sentence into verb and subject
	 * 
	 * @param sentence The user input
	 * @return String
	 */
	public static String[] processSentence(String sentence) {
		sentence = sentence.trim(); //To take out space between edges

		String verb = "";
		String subject = "";

		//To check does the sentence contains space between words
		if (sentence.indexOf(" ") != -1) {
			verb = sentence.substring(0, sentence.indexOf(" ")); //get verb
			sentence = sentence.substring(sentence.indexOf(" ") + 1); //take out verb word from sentence

			//To check the existence of subject in the sentence
			if (verb.length() != sentence.length()) subject = sentence.substring(0, sentence.length()); //get subject
		} else {
			verb = sentence; //Only verb
		}

		String[] result = {verb, subject}; //Pack it
		
		return result;
	}

	/**
	 * To check validated verb
	 * 
	 * @param collection The processed collection from user input
	 * @return boolean
	 */
	public static boolean filterVerb(String[] collection) {
		//All legal verbs
		String[] acceptableVerbs = new String[] {
				"take", 
				"read", 
				"go", 
				"face", 
				"search", 
				"throw",
				"unlock",
				"saw",
				"diagnostic",
				"wear",
				"whatihave",
				"whereami",
				"whatiwear",
				"use",
				"shoot",
				"put"
		};

		return Utils.arrContains(acceptableVerbs, collection[0]);
	}
	
	/**
	 * To separate multiple subject sentence to array
	 * 
	 * @param subject The subject
	 * @return String[]
	 */
	public static String[] processSubject(String subject) {
		subject = subject.trim(); //To take out space between edges
		
		List<String> result = new ArrayList<String>();
		
		//if the subject has more than one word
		while (subject.indexOf(" ") != -1) {
			result.add(subject.substring(0, subject.indexOf(" ")));
			subject = subject.substring(subject.indexOf(" ") + 1);
		}
		
		result.add(subject);
		
		return result.toArray(new String[0]);
	}
	
	public static String[] filterSubject(String[] collection) {
		List<String> subjects = new ArrayList<String>(Arrays.asList(collection));
		ArrayList<String> illegalWords = new ArrayList<String>(Arrays.asList(new String[] {
				"and",
				"or",
				"on",
				"to",
				"there",
				"here",
				"at",
				"in",
				"by"
		}));
		
		subjects.removeAll(illegalWords);
		
		return subjects.toArray(new String[0]);
	}
}