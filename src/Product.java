/**
 * This class provides a basic product with a name and price.
 * @author Brandon
 *
 */
public class Product {
	private String snackName;
	private int costInCents;
	
	/**
	 * Constructor. Expects a name and price.
	 * @param name A string of the name of the snack.
	 * @param price The cost of this snack (in pennies).
	 */
	public Product(String name, int price) {
		snackName = name;
		costInCents = price;
	}
	
	/**
	 * Returns the name of the snack.
	 * @return The name of the snack.
	 */
	public String getSnackName() {
		return snackName;
	}
	
	/**
	 * Returns the price of the snack (formatted as an array
	 * where index 0 is dollars, index 1 is pennies).
	 * @return The array representing the price of the snack.
	 */
	public int[] getPrice() {
		int[] money = new int[2];
		money[0] = costInCents / 100;
		money[1] = costInCents % 100;
		return money;
	}
	
}
