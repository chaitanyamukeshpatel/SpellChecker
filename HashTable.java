import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
/**
 * 
 * @author Chaitanya Mukesh Patel
 * ID: A15346478	
 */

//package hw4;


public class HashTable implements IHashTable {
	
	//You will need a HashTable of LinkedLists. 
	
	private int nelems;						//Number of element stored in the hash table
	private int expand;						//Number of times that the table has been expanded
	private int collision;  				//Number of collisions since last expansion
	private String statsFileName;			//FilePath for the file to write statistics upon every rehash
	private boolean printStats = false;		//Boolean to decide whether to write statistics to file or not after rehashing
	private double loadfactor;				//Loadfactor of the hashtable, (elements/buckets)  	
	private int longest_chain_length;		//Longest chain after collision
	private int found_arr_index;			//Index of array at which the element is found
	private int found_list_index;			//Index of the linkedlist at which the element is found
	
	private LinkedList<String>[] myTable;
	
	/**
	 * Constructor for hash table
	 * @param Initial size of the hash table
	 */
	public HashTable(int size) {
		if(size<=0)
		{
			throw new IllegalArgumentException();
		}
		else
		{
			myTable = new LinkedList[size];
			 for(int i=0; i<size; i++)
			 {
				 myTable[i] = new LinkedList<String>();
			 }
		}
		
	}
	
	/**
	 * Constructor for hash table
	 * @param Initial size of the hash table
	 * @param File path to write statistics
	 */
	public HashTable(int size, String fileName){
		
		//Set printStats to true and statsFileName to fileName
		if(size<=0)
		{
			throw new IllegalArgumentException();
		}
		else
		{
			printStats = true;
			statsFileName = fileName;
			myTable = new LinkedList[size];
			for(int i=0; i<size; i++)
			 {
				 myTable[i] = new LinkedList<String>();
			 }
		}
	
	}

	
	/**
	 * A method to insert a string value into the hashtable
	 * @param value The string to be inserted
	 * @throws NullPointerException If the word being checked is null
	 * @return boolean Returns true if the value is inserted, else returns false
	 */
	@Override
	public boolean insert(String value) throws NullPointerException {
		if(value.equals(null))
		{
			throw new NullPointerException();
		}
		//System.out.print(myTable.length + " " + value + " ");
		int hash_value = hashfunction(value);
		//System.out.println(hash_value);
		
		if(contains(value))
		{
			return false;
		}
		else if(myTable[hash_value].size()>0)
		{
			collision++;
			myTable[hash_value].add(value);
			nelems++;
			check_load();
			return true;
		}
		else
		{
			myTable[hash_value].add(value);
			nelems++;
			check_load();
			return true;
		}
				
	}
	/**
	 * A method to delete a string value into the hashtable
	 * @param value The string to be deleted
	 * @throws NullPointerException If the word being checked is null
	 * @return boolean Returns true if the value is deleted, else returns false
	 */
	@Override
	public boolean delete(String value) throws NullPointerException{
		if(value.equals(null))
		{
			throw new NullPointerException();
		}
		else if(contains(value))
		{
			//System.out.println("found_arr_index = " + found_arr_index);
			//System.out.println("found_list_index = " + found_list_index);
			myTable[found_arr_index].remove(found_list_index);
			nelems--;
			loadfactor = (double)nelems/myTable.length;
			longest_chain_length = find_longest_chain_length();
			return true;
		}
		else
		{
			return false;
		}

	}
	/**
	 * Checks whether the given value is in the hashtable, if yes, assigns a bucket number and the index of the linkedlist.
	 * @param The string to be checked
	 */
	@Override
	public boolean contains(String value) throws NullPointerException 
	{
		boolean found = false;
		if(value.equals(null))
		{
			throw new NullPointerException();
		}
		int hashvalue = hashfunction(value);
		for(int j=0; j<myTable[hashvalue].size(); j++)
		{
			if(myTable[hashvalue].get(j).equals(value))
			{
				found = true;
				found_arr_index = hashvalue;
				found_list_index = j;			
			}
		
		}
		
		return found;		
	}
	/**
	 * Prints the hashtable.
	 */
	@Override
	public void printTable() {
		for(int i=0; i<myTable.length; i++)
		{
			System.out.print(i + ": ");
			for(int j=0; j<myTable[i].size()-1; j++ )
			{
				System.out.print(myTable[i].get(j) + ", ");
			}
			System.out.print(myTable[i].get(myTable[i].size()-1) + ".\n");
		}
	}
	
	
	/**
	 * A method to return the amount of elements in the hashtable.
	 * @return Number of elements
	 */
	@Override
	public int getSize() {
		return nelems;
	}
	
	/**
	 * Rehashes the table, prints statistics before doubling the size of the table and rehashing values from the old table.
	 */
	private void rehash()
	{
		expand++;
		if(printStats)
		{
			printStatistics();
		}
		collision = 0;
		LinkedList<String>[] resized_table; 
		resized_table = new LinkedList[(myTable.length)*2];
		for(int i=0; i<(myTable.length)*2; i++)
		{
			resized_table[i] = new LinkedList<String>();
		}
		for(int j=0; j<myTable.length; j++)
		{			
			for(int k=0; k<myTable[j].size(); k++)
			{
				int hash_value = hashfunction(myTable[j].get(k), (myTable.length*2));
				resized_table[hash_value].add(myTable[j].get(k)); 
			}
			
		}
		myTable = new LinkedList[myTable.length*2];
		for(int i=0; i<myTable.length; i++)
		{
			 myTable[i] = new LinkedList<String>();
		}
		myTable = resized_table;
		loadfactor = (double)nelems/myTable.length;
		longest_chain_length = find_longest_chain_length();
		
	}
	
	/**
	 * A method to implement hash-function that will be used to create the hashtable. (Implmeneted from the CSE 12 slides by Prof. Alvarado.)
	 * @param key String entered
	 * @return Hash value generated by hash function
	 */
	private int hashfunction(String key)
	{
		int hash_value = key.charAt(0);
		
		for(int j=1; j<key.length(); j++)
		{
			int letter = key.charAt(j);
			hash_value = (hash_value*27 + letter) % myTable.length;			
		}
		
		return hash_value%myTable.length;
	}
	/**
	 * A method to generate hashvalue
	 * @param key The string which itself will be stored in the table.
	 * @param tablesize size of the hashtable
	 * @return hashvalue to ascertain at which index the key will be stored.
	 */
	private int hashfunction(String key, int tablesize)
	{
		int hash_value = key.charAt(0);
		
		for(int j=1; j<key.length(); j++)
		{
			int letter = key.charAt(j);
			hash_value = (hash_value*27 + letter) % tablesize;			
		}
		
		return hash_value%tablesize;
	}
	/**
	 * Prints statistics like resizes, loadfactor, collisions and longest chain length
	 */
	private void printStatistics()
	{
		longest_chain_length = find_longest_chain_length();
		FileWriter fw;
		try
		{
			fw = new FileWriter(statsFileName);
			fw.write(expand + " resizes, load factor " + loadfactor + ", " + collision + " collisions, " + longest_chain_length + " longest chain" );
			fw.flush();
			fw.close();
		}
		catch(IOException e)
		{
			
		}				
	}
	/**
	 * A method to find the longest chain's length
	 * @return Length of the longest chain
	 */
	private int find_longest_chain_length()
	{
		 
		if(nelems==0)
		{
			return 0; // returns 0 if there are no elements in the HashTable.
		}
		else
		{
			int length = myTable[0].size();
			//We set the length to the length of first object in the array, and then change through comparisons.
			for(int i=1; i<myTable.length; i++)
			{
				if(length<myTable[i].size())
				{
					length = myTable[i].size();
				}
			}
			return length;
		}
		
	}
	/**
	 * Checks loadfactor of the hashtable, and rehashes if needed.
	 */
	private void check_load()
	{
		loadfactor = (double)nelems/myTable.length;
		//Now checking if loadfactor is above the prescribed value 2/3
		if(loadfactor>(double)2/3)
		{
			rehash();	
		}
	}
	
}
