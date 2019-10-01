/**
 * 
 */
package auctionhouse;

/**
 * This class is used to represent money in all bids, prices and transactions.
 * @author pbj
 */
public class Money implements Comparable<Money> {
 
    private double value;
    
    private static long getNearestPence(double pounds) {
        return Math.round(pounds * 100.0);
    }
 
    private static double normalise(double pounds) {
        return getNearestPence(pounds)/100.0;
        
    }
 
    public Money(String pounds) {
        value = normalise(Double.parseDouble(pounds));
    }
    
    private Money(double pounds) {
        value = pounds;
    }
    /**
     * This method used for adding
     * @param m The money will add to the current money
     * @return	Return a new money object, the result of calculation
     */
    public Money add(Money m) {
        return new Money(value + m.value);
    }
    /**
     * This method used for subtracting
     * @param m The money will subtract to the current money
     * @return  Return a new money object, the result of calculation
     */
    public Money subtract(Money m) {
        return new Money(value - m.value);
    }
	 /**
	  * This method will calculate the increment by percent
	  * @param percent How much the money should increase
	  * @return  Return a new money object, the result of calculation 
	  */
    public Money addPercent(double percent) {
        return new Money(normalise(value * (1 + percent/100.0)));
    }
     
    @Override
    public String toString() {
        return String.format("%.2f", value);
        
    }
    
    /**
     * Compare two money objects
     * @param m Compare m with the current money
     * @return  When current money smaller than m, return negative integer, if equal return 0, if larger than, return a positive integer
     */
    public int compareTo(Money m) {
        return Long.compare(getNearestPence(value),  getNearestPence(m.value)); 
    }
    /**
     * Determine current money whether less equal to another money 
     * @param m The money used to compare
     * @return  If current money less-equal to m, return true, otherwise return false
     */
    public Boolean lessEqual(Money m) {
        return compareTo(m) <= 0;
    }
    
    /**
     * Determine whether two money equal
     * @param o Compare o to current money
     * @return If they are equal, return true, otherwise return false
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Money)) return false;
        Money oM = (Money) o;
        return compareTo(oM) == 0;       
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(getNearestPence(value));
    }
      

}
