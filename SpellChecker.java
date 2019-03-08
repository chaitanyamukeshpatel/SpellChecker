//package hw4;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * 
 * @author Chaitanya Mukesh Patel
 * ID: A15346478	
 */
public class SpellChecker {
	
	private HashTable dictionary = new HashTable(10);
	ArrayList<String> suggestions;
	/**
	 * No argument constructor, initiates a dictionary of type HashTable.
	 */
	public SpellChecker()
	{
		//No Argument Constructor
	}
	/**
	 * A method to read the dictionary into the hashtable from a text file.
	 * @param dictFileReader Input Stream from where the text file is being read.
	 */
	public void readDictionary(Reader dictFileReader)
	{
		String line = new String();
		BufferedReader whatever =  new BufferedReader(dictFileReader);
		try 
		{
			while((line = whatever.readLine()) != null)
			{
				dictionary.insert(line);
			}
		}
		catch(IOException e)
		{
			//Exception
		}
	}
	/**
	 * A function to check whether the given word is in the dictionary, if not to suggest corrections.
	 * @param word String to be checked
	 * @return A String array of suggestions if word is not contained by the dictionary
	 */
	public String[] checkWord(String word)
	{
		//System.out.println("Initial Checking for word: " + word);
		suggestions = new ArrayList<>(); 
		if(dictionary.contains(word))
		{
			System.out.println(word + ": ok");
			return null;
		}
		else
		{
			
			//First converting all letters to lowercase
			word.toLowerCase();
			
			StringBuilder manipulator;
			String tempword = new String();
			
			//Checking for wrong letter
			for(int i=0; i<word.length(); i++)
			{
				for(int j=97; j<=122; j++)
				{
					manipulator = new StringBuilder(word);
					manipulator.setCharAt(i, (char)j);
					tempword = manipulator.toString();
					if(dictionary.contains(tempword) && !contains(tempword, suggestions))
					{
						suggestions.add(tempword);
					}
				}
			}
			
			//Checking for inserted letter
			if(word.length()>1)
			{
				for(int i=0; i<word.length(); i++)
				{
					manipulator = new StringBuilder(word);
					manipulator.deleteCharAt(i);
					tempword = manipulator.toString();
					if(dictionary.contains(tempword) && !contains(tempword, suggestions))
					{
						suggestions.add(tempword);
					}
				}
			}
			
			
			//Checking for a deleted letter
			for(int i=0; i<=word.length(); i++)
			{
				for(int j=97; j<=122; j++)
				{
					manipulator = new StringBuilder(word);
					manipulator.insert(i, (char)j);
					tempword = manipulator.toString();
					if(dictionary.contains(tempword) && !contains(tempword, suggestions))
					{
						System.out.println("Found Suggestion: " + tempword);
						suggestions.add(tempword);
					}
				}
			}
			
			//Checking for adjacent transposed letters
			for(int i=0; i<word.length()-1; i++)
			{
				manipulator = new StringBuilder(word);
				char temp = manipulator.charAt(i);
				manipulator.setCharAt(i, manipulator.charAt(i+1));
				manipulator.setCharAt(i+1, temp);
				tempword = manipulator.toString();
				if(dictionary.contains(tempword) && !contains(tempword, suggestions))
				{
					suggestions.add(tempword);
				}
			}
				
			//Checking for inserted spaces
			for(int i=1; i<word.length(); i++)
			{
				
				manipulator = new StringBuilder(word);
				manipulator.insert(i, (char)32);
				tempword = manipulator.toString();
				//Now checking if all the space seperated words are contained by the dictionary
				String[] splited = tempword.split("\\s+");
				int counter = 0;
				for(int j=0; j<splited.length; j++)
				{
					if(dictionary.contains(splited[j]))
					{
						counter++;
					}
				}
				if(counter==splited.length  && !contains(tempword, suggestions))
				{
					suggestions.add(tempword);
				}
				if(dictionary.contains(tempword) && !contains(tempword, suggestions))
				{
					suggestions.add(tempword);
				}
			}
			
			
		}
		//Converting the ArrayList to String array and returning the array.
		String[] suggestions_return = new String[suggestions.size()];
		for(int i=0; i<suggestions.size(); i++)
		{
			suggestions_return[i] = suggestions.get(i);
		}
		return suggestions_return;
	}
	
	/**
	 * A private method to check if the suggested word is already a part of the suggestions arraylist.
	 * @param word The String being tested
	 * @param suggestions An arraylist of unique suggestions
	 * @return A boolean value. If the word is not in the arraylist, returns false. 
	 */
	private boolean contains(String word, ArrayList<String> suggestions)
	{
		for(int i=0; i<suggestions.size(); i++)
		{
			if(suggestions.get(i).equals(word))
			{
				return true;
			}
		}
		return false;
	}
	
}
