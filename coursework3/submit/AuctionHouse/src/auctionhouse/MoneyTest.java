/**
 * 
 */
package auctionhouse;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author pbj
 *
 */
public class MoneyTest {

    @Test    
    public void testAdd() {
        Money val1 = new Money("12.34");
        Money val2 = new Money("0.66");
        Money result = val1.add(val2);
        assertEquals("13.00", result.toString());
    }

    /*
     ***********************************************************************
     * BEGIN MODIFICATION AREA
     ***********************************************************************
     * Add all your JUnit tests for the Money class below.
     */
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Test
    public void testSubstract() {
    	Money val1 = new Money("25.11");
    	Money val2 = new Money("5.12");
    	Money result = val1.subtract(val2);
    	assertEquals("19.99", result.toString());
    }
    
    @Test
    public void testAddPercent() {
    	Money val = new Money("10.00");
    	val.addPercent(5).toString();
    	assertEquals("10.50", val.addPercent(5).toString());
    }
    
    @Test
    public void testToString() {
    	Money val = new Money("123321.12");
    	assertEquals("123321.12", val.toString());
    }
    
    @Test
    public void testCompareTo() {
    	Money val1 = new Money("123.22");
    	Money val2 = new Money("12.1");
    	assertEquals(true, val1.compareTo(val2) > 0);
    }
    
    @Test
    public void testLessEqual() {
    	Money val1 = new Money("12.33");
    	Money val2 = new Money("123.2");
    	assertEquals(true, val1.lessEqual(val2));
    }
    
    @Test
    public void testEquals() {
    	Money val1 = new Money("12.33");
    	Money val2 = new Money("12.33");
    	assertEquals(true, val1.equals(val2));
    }
    
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /*
     * Put all class modifications above.
     ***********************************************************************
     * END MODIFICATION AREA
     ***********************************************************************
     */


}
