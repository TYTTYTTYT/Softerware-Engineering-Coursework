package auctionhouse;

public class Buyer {
	public String name;
	public String address;
	public String bankAccount;
	public String bankAuthCode;
	
	public Buyer(String name, String address, String bankAccount,String bankAuthCode) {
		this.name = name;
		this.address = address;
		this.bankAccount = bankAccount;
		this.bankAuthCode = bankAuthCode;
	}
}
