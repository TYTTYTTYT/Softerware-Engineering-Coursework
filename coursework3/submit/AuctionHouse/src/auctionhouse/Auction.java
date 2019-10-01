package auctionhouse;

public class Auction {
	public String auctioneerName;
    public String auctioneerAddress;
    public int lotNumber;
	public String bidderName;
	public Money hammerPrice;
    
    public Auction(
            String auctioneerName,
            String auctioneerAddress,
            int lotNumber)
    {
    	this.auctioneerName = auctioneerName;
    	this.auctioneerAddress = auctioneerAddress;
    	this.lotNumber = lotNumber;
		this.hammerPrice = new Money("0");
    }
    
}