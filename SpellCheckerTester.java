//package hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Scanner;


/**
 * @author Ashlesha
 *
 */
public class SpellCheckerTester {
public static void main(String [] args)
{
	SpellChecker spellChecker = new SpellChecker();
	String path = "../resource/asnlib/";
	File dictionary = new File("longdict.txt");
	try {
        Reader reader = new FileReader( dictionary );
        spellChecker.readDictionary( reader ); // Loads the dictionary.
    } catch ( FileNotFoundException e ) {
        System.err.println( "Failed to open " + dictionary );
        e.printStackTrace(); // Error getting the dictionary.
        System.exit( 1 );
    }
	//checking
	//System.out.println(spellChecker.dictionary.getSize());
	File inputFile = new File("input.txt");
	String outputArray[]={"apple: ok", "asteroid: ok", "culmination: ok", "computerscience: computer science",
			"ashlesha: not found","appoe: apple", "banna: banana", "ornage: orange", "potato: ok",
			"cherrystrawberry: cherry strawberry", "poatta: not found", "pineapple: ok", "huwuh: not found",
			"yippie: not found", "zzzzzzzz: not found", "pol: pal, pod, pop, pot, pox, pool, poll, pole, polo",
			"transcription: ok", "ludecrous: ludicrous", "utdoings: undoings", "tiannual: biannual",
			"institutes: ok", "beicycle: bicycle", "pwillow: willow, pillow", "masterchef: master chef",
			"beding: being, beading, bedding, bending", "sprice: splice, spruce, sprite, price, spice",
			"treestop: treetop, tree stop, trees top", "starwars: star wars", "earnest: ok", "woah: not found",
			"ron: don, ion, non, son, ton, won, yon, ran, run, rob, rod, roe, rot, row, on, iron",
			"tup: cup, pup, sup, tap, tip, top, tub, tug, up", "mesy: mesh, mess, messy", "i: pi, if, in, is, it",
			"yu: you", "char: ok", "anti: ok", "farthest: ok", "porcupine: ok", "kloser: closer, loser",
			"tooawesome: too awesome", "naty: navy, nay, nasty", "alphabt: alphabet", "dedicatn: dedicate"}; 
	String inputArray[]=new String[44];
	int counter=0;
    try {
        Scanner input = new Scanner( inputFile ); // Reads list of words
        while ( input.hasNext() ) {               // from input file.
            // Converts to lowercase.
            String word = input.next().toLowerCase(); // Gets suggestions.
            String[] suggestions = spellChecker.checkWord( word ); 
            //System.out.print( word + ": " ); // Prints words.
            if ( suggestions == null ) { // If null return, word is
                                // right/exists in the dictionary).
                //System.out.println( "ok" );
            	inputArray[counter++]=word+": ok";
            } else if ( suggestions.length == 0 ) { // Word not in
                                    // dictionary and has no corrections.
                //System.out.println( "not found" ); 
            	inputArray[counter++]=word+": not found";
            } else { // Word not in dictionary and has corrections.
                //System.out.print( suggestions[ 0 ] );
                String st=word+": "+suggestions[0];
                for ( int i = 1; i < suggestions.length; i++ ) {
                    //System.out.print( ", " + suggestions[ i ] );
                    st+=(", " + suggestions[ i ]);
                }
                inputArray[counter++]=st;
                //System.out.println(); // Moves to next line.
            }
            
        }
        input.close(); // Closes input.
    } catch ( FileNotFoundException e ) {
        System.err.println( "Failed to open " + inputFile );
        e.printStackTrace(); // Error reading input file.
        System.exit( 1 );
    }
    for(int i=0;i<inputArray.length;i++)
    {
    	if(!inputArray[i].equals(outputArray[i])){
    		System.out.println(inputArray[i]+" should be "+outputArray[i]);
    		System.out.println("ERROR - Your spell checker failed");
    		System.exit(1);
    	}
    }
    System.out.println("Way to go - Your spell checker passed all tests");
    
	}
   
}
