/**
 * 
 */
package auctionhouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author pbj
 *
 */
public class AuctionHouseImp implements AuctionHouse {
	
	private  Map<String, Buyer> buyerMap = new HashMap<String,Buyer>();
	private  Map<String ,Seller> sellerMap = new HashMap<String,Seller>();
	
    private  Map<Integer, Lot> lotMap = new HashMap<Integer, Lot>();
    private  Map<Integer, Auction> auctionMap = new HashMap<Integer, Auction>();
    private  List<Integer> lotNumbers = new ArrayList<Integer>();
	
    private static Logger logger = Logger.getLogger("auctionhouse");
    private static final String LS = System.lineSeparator();
    private Parameters parameters;
    private String startBanner(String messageName) {
        return  LS 
          + "-------------------------------------------------------------" + LS
          + "MESSAGE IN: " + messageName + LS
          + "-------------------------------------------------------------";
    }
    private String warningBanner(String message) {
    	return LS 
    	  + "*************************************************************" + LS
    	  + message + LS
    	  + "*************************************************************";
    }
   
    public AuctionHouseImp(Parameters parameters) {
    	this.parameters = parameters;

    }

    public Status registerBuyer(
            String name,
            String address,
            String bankAccount,
            String bankAuthCode) {

        logger.fine(startBanner("registerBuyer " + name));
        
//--------------------------------------------------------------------------
        Buyer theBuyer = new Buyer( name,
               address,
               bankAccount,
               bankAuthCode);
       
        if(buyerMap.containsKey(name))
            {
        	logger.warning(warningBanner(name + " is existing!"));
            return Status.error("Invalid buyer name!");
            }
            
        if(name.isEmpty()) {
        	logger.warning(warningBanner("Please enter your name!"));
        	return Status.error("Please enter your name!");
        }
        if(address.isEmpty()) {
        	logger.warning(warningBanner("Please enter your address!"));
        	return Status.error("Please enter your address!");
        }
        if(bankAccount.isEmpty()) {
        	logger.warning(warningBanner("Please enter your bank account!"));
        	return Status.error("Please enter your bank account!");
        }
        if(bankAuthCode.isEmpty()) {
        	logger.warning(warningBanner("Please enter your bank authorization code!"));
        	return Status.error("Please enter your bank authorization code!");
        }
        
        buyerMap.put(name, theBuyer);
        logger.finer(name + " registered");
//--------------------------------------------------------------------------

        return Status.OK();
    }
    

    public Status registerSeller(
            String name,
            String address,
            String bankAccount) {
        logger.fine(startBanner("registerSeller " + name));
//--------------------------------------------------------------------------
     
        Seller theSeller = new Seller(
                 name,
                 address,
                 bankAccount);
        
        if(sellerMap.containsKey(name)) {
        	logger.warning(warningBanner(name + " is existing!"));
        	return Status.error("Invalid seller name!");
        }
        
        if(name.isEmpty()) {
        	logger.warning(warningBanner("Please enter your name!"));
        	return Status.error("Please enter your name!");
        }
        if(address.isEmpty()) {
        	logger.warning(warningBanner("Please enter your address!"));
        	return Status.error("Please enter your address!");
        }
        if(bankAccount.isEmpty()) {
        	logger.warning(warningBanner("Please enter your bank account!"));
        	return Status.error("Please enter your bank account!");
        }
        
        sellerMap.put(name, theSeller);
        logger.finer(name + " registered");
//--------------------------------------------------------------------------
        
        return Status.OK();      
    }
    

    public Status addLot(
            String sellerName,
            int number,
            String description,
            Money reservePrice) {
        logger.fine(startBanner("addLot " + sellerName + " " + number));
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        if(lotMap.containsKey(number)) {
        	logger.warning(warningBanner("Key number " + number + " is existing! Try another number!"));
        	return Status.error("Invalid number");
        }
        if(!sellerMap.containsKey(sellerName)) {
        	logger.warning(warningBanner("Seller doesn't exist!"));
        	return Status.error("Seller doesn't exist!");
        }
        if(description.isEmpty()) {
        	logger.warning(warningBanner("Invalid description!"));
        	return Status.error("Invalid description!");
        }
        
        Lot newLot = new Lot(sellerName, number, description, reservePrice);
        lotMap.put(newLot.number, newLot);
        
        if(lotNumbers.size() == 0) {
        	lotNumbers.add(number);
        	logger.finer("lot " + newLot.toString() + " added succeed!");
        	return Status.OK();
        }
        
        for(int i = 0; i < lotNumbers.size(); i++) {
        	if(number < lotNumbers.get(i)) {
        		lotNumbers.add(i, number);
        		logger.finer("lot " + newLot.toString() + " added succeed!");
        		return Status.OK();
        	} else if(i == lotNumbers.size() - 1){
        		lotNumbers.add(number);
        		logger.finer("lot " + newLot.toString() + " added succeed!");
        		return Status.OK();
        	}
        }
        logger.finer("lot " + newLot.toString() + " added succeed!");
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        return Status.OK();    
    }

    public List<CatalogueEntry> viewCatalogue() {
        logger.fine(startBanner("viewCatalog"));
        
        List<CatalogueEntry> catalogue = new ArrayList<CatalogueEntry>();
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Lot currentLot;
        for(int i = 0; i < lotNumbers.size(); i++) {
        	currentLot = lotMap.get(lotNumbers.get(i));
        	catalogue.add(new CatalogueEntry(currentLot.number, currentLot.description, currentLot.lotStatus));
        }
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        logger.fine("Catalogue: " + catalogue.toString());
        return catalogue;
    }

 
    public Status noteInterest(
            String buyerName,
            int lotNumber) {
        logger.fine(startBanner("noteInterest " + buyerName + " " + lotNumber));
//--------------------------------------------------------------------------
        
        if(!lotMap.containsKey(lotNumber)) {
        	logger.warning(warningBanner("Lot does not exsit!"));
        	return Status.error("Lot does not exsit!");
        }
        if(!buyerMap.containsKey(buyerName)) {
        	logger.warning(warningBanner("Buyer does not exsit!"));
        	return Status.error("Buyer does not exist!");
        }
        
        lotMap.get(lotNumber).interestedBuyer.add(buyerMap.get(buyerName));
        logger.finer("Intrested added");
//--------------------------------------------------------------------------
        
        return Status.OK();   
    }

    public Status openAuction(
            String auctioneerName,
            String auctioneerAddress,
            int lotNumber) {
        logger.fine(startBanner("openAuction " + auctioneerName + " " + lotNumber));
//--------------------------------------------------------------------------------
        Auction theAuction = new Auction(auctioneerName,auctioneerAddress,lotNumber);
        if(auctionMap.containsKey(lotNumber)) {
        	logger.warning(warningBanner("Lot does not exsit!"));
        	return Status.error("Open auction failed.");
        	
        }
        if(!lotMap.containsKey(lotNumber)) {
        	logger.warning(warningBanner("Invalid lot!"));
        	return Status.error("Open auction failed.");
        }
        if(lotMap.get(lotNumber).lotStatus.toString() != "UNSOLD" ) {
        	logger.warning(warningBanner("Invalid lot!"));
        	return Status.error("Open auction failed.");
        }
        
        auctionMap.put(lotNumber, theAuction);
        lotMap.get(lotNumber).lotStatus = LotStatus.IN_AUCTION;
        logger.finest("Lot status changed");
        Lot theLot = lotMap.get(lotNumber);
        
        
        for(int i = 0; i < theLot.interestedBuyer.size(); i++)
        {
        	parameters.messagingService.auctionOpened(theLot.interestedBuyer.get(i).address,lotNumber);
        }
        parameters.messagingService.auctionOpened(sellerMap.get(theLot.sellerName).address,lotNumber);
        logger.finest("message has sent");
//------------------------------------------------------------------------------
        return Status.OK();
    }

    public Status makeBid(
            String buyerName,
            int lotNumber,
            Money bid) {
        logger.fine(startBanner("makeBid " + buyerName + " " + lotNumber + " " + bid));
      //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        if(!buyerMap.containsKey(buyerName)) {
        	logger.warning(warningBanner("Buyer does not exsit!"));
        	return Status.error("Buyer doesn't exist!");
        }
		if(!lotMap.containsKey(lotNumber)) {
			logger.warning(warningBanner("Lot does not exsit!"));
			return Status.error("Lot doesn't exist");
		}
		if(lotMap.get(lotNumber).lotStatus != LotStatus.IN_AUCTION) {
			logger.warning(warningBanner("This lot is not in auction!"));
			return Status.error("This lot is not in auction!");
		}
		
		Auction theAuction = auctionMap.get(lotNumber);
		theAuction.bidderName = buyerName;
		if((!theAuction.hammerPrice.lessEqual(bid)) || theAuction.hammerPrice.equals(bid)) {
			logger.warning(warningBanner("Invalid bid!"));
			return Status.error("Invalid bid!");
		}
		else auctionMap.get(lotNumber).hammerPrice = bid;
		parameters.messagingService.bidAccepted(sellerMap.get(lotMap.get(lotNumber).sellerName).address, lotNumber, bid);
		parameters.messagingService.bidAccepted(auctionMap.get(lotNumber).auctioneerAddress, lotNumber, bid);
		for(int i = 0; i < lotMap.get(lotNumber).interestedBuyer.size(); i++) {
			if(buyerName != lotMap.get(lotNumber).interestedBuyer.get(i).name) parameters.messagingService.bidAccepted(lotMap.get(lotNumber).interestedBuyer.get(i).address, lotNumber, bid);
		}
		logger.finest("message has sent");
      //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        return Status.OK();    
    }

    public Status closeAuction(
            String auctioneerName,
            int lotNumber) {
        logger.fine(startBanner("closeAuction " + auctioneerName + " " + lotNumber));
//----------------------------------------------------------------------------------------------------
        if(!auctionMap.containsKey(lotNumber)) {
        	logger.warning(warningBanner("Auction does not exists!"));
        	return Status.error("Auction does not exists!");
        }
        Lot theLot = lotMap.get(lotNumber);
        Seller theSeller = sellerMap.get(theLot.sellerName);
        Buyer theBuyer = buyerMap.get(auctionMap.get(lotNumber).bidderName);
        String houseAccount = parameters.houseBankAccount;
        String houseBankAuthCode = parameters.houseBankAuthCode;
        if(!(theLot.reservePrice.lessEqual(auctionMap.get(lotNumber).hammerPrice)))
        {
        	lotMap.get(theLot.number).lotStatus = LotStatus.UNSOLD;
        	logger.fine(startBanner("No satisfy hammerPrice, lot: " + theLot.toString() + " UNSOLD"));
        	for(int i = 0; i < theLot.interestedBuyer.size(); i++)
        		parameters.messagingService.lotUnsold(theLot.interestedBuyer.get(i).address, lotNumber);
        	parameters.messagingService.lotUnsold(sellerMap.get(theLot.sellerName).address, lotNumber);
        	logger.finest("message has sent");
        
        	return new Status(Status.Kind.NO_SALE);        	
        }
               
        Double commission = parameters.commission;
        Double premium = parameters.buyerPremium;
        Money mPremium = new Money(premium.toString());
        Money mCommission = new Money(commission.toString());
        Money mToHouse = auctionMap.get(lotNumber).hammerPrice.add(mPremium);
        Money mToSeller = auctionMap.get(lotNumber).hammerPrice.subtract(mCommission);
        
        Status transferStatus = parameters.bankingService.transfer(theBuyer.bankAccount, theBuyer.bankAuthCode,houseAccount, mToHouse);
        if(transferStatus.kind.equals(Status.Kind.ERROR) )
        {
        	theLot.lotStatus = LotStatus.SOLD_PENDING_PAYMENT;
        	logger.finest("Lot status changed");
        	
        	
        	for(int i = 0; i < theLot.interestedBuyer.size(); i++)
        		parameters.messagingService.lotUnsold(theLot.interestedBuyer.get(i).address, lotNumber);
        	parameters.messagingService.lotUnsold(sellerMap.get(theLot.sellerName).address, lotNumber);
        	logger.finest("message has sent");
        	return new Status(Status.Kind.SALE_PENDING_PAYMENT);
        }
        transferStatus = parameters.bankingService.transfer(houseAccount, houseBankAuthCode, theSeller.bankAccount, mToSeller);
        if(transferStatus.kind.equals(Status.Kind.ERROR) )
        {
        	theLot.lotStatus = LotStatus.SOLD_PENDING_PAYMENT;
        	logger.finest("Lot status changed");
        	
        	
        	for(int i = 0; i < theLot.interestedBuyer.size(); i++)
        		parameters.messagingService.lotUnsold(theLot.interestedBuyer.get(i).address, lotNumber);
        	parameters.messagingService.lotUnsold(sellerMap.get(theLot.sellerName).address, lotNumber);
        	logger.finest("message has sent");
        	return new Status(Status.Kind.SALE_PENDING_PAYMENT);
        }
        for(int i = 0; i < theLot.interestedBuyer.size(); i++)
    		parameters.messagingService.lotSold(theLot.interestedBuyer.get(i).address, lotNumber);
    	parameters.messagingService.lotSold(sellerMap.get(theLot.sellerName).address, lotNumber);
    	logger.finest("message has sent");
    	        	
        return new Status(Status.Kind.SALE);
        
//----------------------------------------------------------------------------------------
        //return Status.OK();  
        
    }
}
