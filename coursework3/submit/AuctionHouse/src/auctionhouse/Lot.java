package auctionhouse;

import java.util.ArrayList;
import java.util.List;

public class Lot {
	String sellerName;
    int number;
    String description;
    Money reservePrice;
    public List<Buyer> interestedBuyer;
    LotStatus lotStatus;
    
    public Lot(String sellerName, int number, String description, Money reservePrice) {
    	this.sellerName = sellerName;
    	this.number = number;
    	this.description = description;
    	this.reservePrice = reservePrice;
    	this.lotStatus = LotStatus.UNSOLD;
    	this.interestedBuyer = new ArrayList<Buyer>();
    }
    
    @Override
    public String toString() {
        return Integer.toString(number) + ": " + description;
    }
}
