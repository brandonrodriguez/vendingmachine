/**
 * This class provides a wrapper for Products that also maintains
 * a quantity of a product.
 * @author Brandon
 *
 */
public class ProductManager {
	private static String nextItemCode = "A1";
	private String itemCode;
	private Product product;
	private int quantity;
	
	/**
	 * Constructor. Expects a Product and an integer. Also
	 * assigns this ProductManager a unique itemCode.
	 * @param p The product this ProductManager will manage.
	 * @param q The quantity of said product that is in the machine.
	 */
	public ProductManager(Product p, int q) {
		itemCode = nextItemCode;
		product = p;
		quantity = q;
		incrementItemCode();
	}
	
	/**
	 * Checks to see if we can vend this item (aka if the quantity is greater than 0).
	 * If yes, then "vend" by decrementing the quantity and return true.
	 * If no, then return false.
	 * @return True if producted was vended, false otherwise.
	 */
	public boolean vendItem() {
		if (isInStock()) {
			quantity--;
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the item code assigned to this product.
	 * @return A string of length 2 that is this product's itemCode.
	 */
	public String getItemCode() {
		return itemCode;
	}
	
	/**
	 * Returns the Product this ProductManager is managing.
	 * @return A Product object.
	 */
	public Product getProduct() {
		return product;
	}
	
	/**
	 * Checks if this item is still in stock.
	 * @return True if quantity is positive, false otherwise.
	 */
	public boolean isInStock() {
		return quantity > 0;
	}
	
	/**
	 * Takes the static variable that represents the last item code used,
	 * and increments it. We know that the item code is two characters.
	 * The first character is a letter, the second is a number. Our vending machine
	 * assigns a new letter for each row of products, and 5 products can be
	 * placed on each row. Thus, after the first 5 products, the letter should
	 * be incremented. e.g. A1 => A2 => A3 => A4 => A5 => B1 => ...
	 */
	public static void incrementItemCode() {
		char letter = nextItemCode.charAt(0);
		int num = Integer.parseInt(nextItemCode.substring(1));
		num++;
		if (num > 5) {
			letter++;
			num = 1;
		}
		nextItemCode = String.valueOf(letter) + String.valueOf(num);
	}
}
