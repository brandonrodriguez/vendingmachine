/**
 * This class provides the cashier, keeper of the money.
 * @author Brandon
 */
public class Cashier {
	
	// The current change inserted for the current transaction.
	private int currentCents;
	
	// After a purchase is made, we tuck away money into the register
	// drawer. This becomes part of our revenue.
	private int revenue;
	
	/**
	 * Constructor. Default constructor works in this case.
	 */
	public Cashier() {
		// Primitives have default values. For the sake of completeness, I will
		// assign a value anyway.
		currentCents = 0;
		revenue = 0;
	}
	
	/**
	 * This method is responsible for adding value to the vending machine.
	 * @param value The number of cents the user just inserted into the machine.
	 * @return The total change inserted for this transaction, formatted according to
	 * the currentValue() function.
	 */
	public int[] addMoney(int value) {
		currentCents += value;
		return currentValue();
	}
	
	/**
	 * Formats the current amount of change for this transaction into an array of size 2.
	 * Index 0 contains the number of dollars, while index 1 contains the number of pennies.
	 * Example: 135 ==> [1, 35]
	 * Example: 95 ==> [0, 95]
	 * @return An array of size 2.
	 */
	public int[] currentValue() {
		int[] money = new int[2];
		money[0] = currentCents / 100;
		money[1] = currentCents % 100;
		return money;
	}
	
	/**
	 * Converts our common array format back into a single integer
	 * representing the number of pennies the machine has for this
	 * transaction.
	 * This method can be static because it does not require the use
	 * of any instance variables.
	 * @param value A formatted array of length 2.
	 * @return The number of pennies the array represents.
	 */
	public static int toPennies(int[] value) {
		return value[0] * 100 + value[1];
	}
	
	/**
	 * Deducts some value from our transaction. Snacks aren't free!
	 * This function should only be called if you are certain there
	 * are enough funds to cover the purchase (i.e. this function can be
	 * very easy -- you do not need to do any sort of error checking).
	 * @param price The value (in pennies) to deduct from the current
	 * amount of change inserted for this transaction.
	 */
	public void purchase(int price) {
		revenue += price;
		currentCents -= price;
	}
	
	/**
	 * Return a string representing the amount of money inserted into
	 * the machine. This simply formats our magic array (of length 2) with
	 * some currency symbols.
	 * Example: [1, 35] ==> $1.35
	 * Example: [0,5] ==> $0.05 (notice that the 5 cents became 05).
	 * @param money A formatted array according to currentValue().
	 * @return A string representing human readable currency.
	 */
	public static String showCash(int[] money) {
		String cents = String.format("%02d", money[1]);
		return "$" + money[0] + "." + cents;
	}
	
	/**
	 * After a purchase is made, we need to return the change.
	 * This function should do a few things:
	 * 1. Deduct the amount of money passed in from our instance variable
	 * keeping track of the current transaction change. (If we have $1 in
	 * the machine, and we receive a request to return 5 cents, then we should
	 * only have 95 cents left in the machine).
	 * 2. Return a string formatted to be read by humans (hint: showCash())
	 * that can be used to alert the user how much money was returned.
	 * @param money The amount of money to be returned. Formatted as an array of length 2.
	 * @return A string representing how much money was returned (human readable).
	 */
	public String returnChange(int[] money) {
		int moneyInPennies = toPennies(money);
		currentCents -= moneyInPennies;
		return "Returning " + showCash(money);
	}
	
}
