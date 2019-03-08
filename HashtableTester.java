//package hw4;
/**
 * 
 * @author Chaitanya Mukesh Patel
 * ID: A15346478	
 */
import org.junit.*;
import static org.junit.Assert.*;

import java.util.Hashtable;
public class HashtableTester {

	private HashTable testHashTable1, testHashTable2;
	@Before
	public void setUp()
	{
		
		testHashTable1 = new HashTable(1);
		testHashTable2 = new HashTable(2);
	}
	@Test
	public void testInsert()
	{
		assertEquals("checking insert",true,testHashTable1.insert("abc"));
		assertEquals("Checking contains after insert",true,testHashTable1.contains("abc"));
	}
	
	@Test
	public void testDelete()
	{
     	testHashTable1.insert("abc");
		assertEquals("Checking delete",true,testHashTable1.delete("abc"));
		assertEquals("Checking contains after delete",false,testHashTable1.contains("abc"));
	}
	@Test
	public void testGetSize()
	{
		testHashTable1.insert("abc");
		testHashTable1.insert("pqr");
		testHashTable1.insert("xyz");
		assertEquals("Checking getSize",new Integer(3),new Integer(testHashTable1.getSize()));
	}
	@Test
	public void testInsertException()
	{
		try
		{
			testHashTable1.insert(null);
			fail("Should throw a NullPointerException");
		}
		catch(NullPointerException e)
		{
			//Empty
		}
	}
	@Test
	public void testDeleteException()
	{
		try
		{
			testHashTable1.delete(null);
			fail("Should throw a NullPointerException");
		}
		catch(NullPointerException e)
		{
			//Empty
		}
	}
	@Test
	public void testContainsException()
	{
		try
		{
			testHashTable1.contains(null);
			fail("Should throw a NullPointerException");
		}
		catch(NullPointerException e)
		{
			//Empty
		}
	}
	@Test
	public void testContainsfalse()
	{
		testHashTable2.insert("abc");
		assertEquals("Should be false",false,testHashTable2.contains("ABC"));
	}
	@Test
	public void testContainstrue()
	{
		testHashTable2.insert("abc");
		assertEquals("Should be true",true,testHashTable2.contains("abc"));
	}
	@Test
	public void testincreaseSize()
	{
		int num = testHashTable2.getSize();
		testHashTable2.insert("abc");
		assertEquals("The size should increase by 1", num+1, testHashTable2.getSize());
		testHashTable2.insert("bcd");
		assertEquals("The size should increase by 1", num+2, testHashTable2.getSize());
		testHashTable2.insert("cde");
		assertEquals("The size should increase by 1", num+3, testHashTable2.getSize());
	}
	@Test
	public void testdecreaseSize()
	{
		
		testHashTable2.insert("abc");
		testHashTable2.insert("bcd");
		testHashTable2.insert("cde");
		int num = testHashTable2.getSize();
		testHashTable2.delete("abc");
		assertEquals("The size should decrease by 1", num-1, testHashTable2.getSize());
		testHashTable2.delete("bcd");
		assertEquals("The size should decrease by 1", num-2, testHashTable2.getSize());
		testHashTable2.delete("cde");
		assertEquals("The size should decrease by 1", num-3, testHashTable2.getSize());
	}
	
	
}
