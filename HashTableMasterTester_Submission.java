//package hw4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.*;
import org.junit.runner.notification.Failure;
import org.junit.runner.Result;
import org.junit.runner.notification.*;
//import voc.grader.*;
import org.junit.*;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class HashTableMasterTester_Submission {
    
	HashTable table;
	String random[] = {"vanilla", "caramel", "banana", "chocolate", "raspberry", "hazelnut", "coffee", "mango", "blueberry",
			"strawberry", "honey", "avocado", "almond", "ecosphere", "meroblastic", "hitchy", "haunce", "uncomparableness", "cheerily", "mogging", "overgamble",
			"regrade", "amazon", "money", "laser", "acms", "printing", "help",
    		"inquiries", "setting", "up", "your", "account", "i", "am", "picking",
    		"random", "words", "found", "around", "dungeons", "midnight", "snack", "blanket", "cookies", "stuffed", "basket", "hungry", "lemonade", "beyonce"};
	@Before
	public void setUp() {
		table = new HashTable(3);
	}

	@Test(timeout=1000)
	public void testInsert() {
		assertTrue("Checking insert",table.insert("vanilla"));
		assertTrue("Checking insert",table.insert("hazelnut"));
		assertTrue("Checking insert",table.insert("caramel"));
		assertTrue("Checking insert",table.insert("chocolate"));
		assertTrue("Checking insert",table.insert("raspberry"));
		assertTrue("Checking insert",table.insert("mango"));
	}

	@Test(timeout=1000)
	public void testInsertDuplicates() {
		assertTrue("Checking insert",table.insert("raspberry"));
		assertTrue("Checking insert",table.insert("mango"));
		assertFalse("Checking insert duplicate",table.insert("raspberry"));
		assertFalse("Checking insert duplicate",table.insert("mango"));
	}

	@Test(timeout=1000)
	public void testInsertMore() {
		for(int i=0; i<random.length; i++)
			assertTrue("Checking insert", table.insert(random[i]));

		for(int i=0; i<random.length; i++)
			assertFalse("Checking insert", table.insert(random[i]));
	}
	
	@Test(timeout=1000)
	public void testContains() {
		for(int i=0; i<15; i++)
			assertTrue("Checking insert", table.insert(random[i]));

		for(int i=0; i<15; i++)
			assertTrue("Checking lookup", table.contains(random[i]));

		for(int i=15; i<random.length; i++)
			assertFalse("Checking lookup", table.contains(random[i]));
	}

	@Test(timeout=1000)
	public void testContainsAfterDelete() {
		for(int i=0; i<random.length; i++)
			table.insert(random[i]);

		for(int i=0; i<20; i++)
			assertTrue("Checking delete",table.delete(random[i]));

		for(int i=0; i<20; i++)
			assertFalse("Checking lookup after delete",table.contains(random[i]));
		for(int i=20; i<random.length; i++)
			assertTrue("Checking lookup after delete",table.contains(random[i]));
	}
}