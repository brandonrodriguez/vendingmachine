import java.util.ArrayList;

/**
 * This class provides the InventoryManager. It's charged with keeping
 * track of ProductManagers. The InventoryManager works in conjunction with the
 * cashier to determine if a product is in stock, should be vended, and charged
 * to the user.
 * @author Brandon
 *
 */
public class InventoryManager {
	private ArrayList<ProductManager> allProducts;
	
	/**
	 * Constructor. Initializes an ArrayList to hold ProductManagers.
	 */
	public InventoryManager() {
		allProducts = new ArrayList<ProductManager>();
	}

	/**
	 * Adds a ProductManager to the ArrayList using the given parameters.
	 * @param product The Product to create a ProductManager from.
	 * @param quantity The quantity of said Product loaded into the vending machine.
	 */
	public void addProduct(Product product, int quantity) {
		ProductManager pm = new ProductManager(product, quantity);
		allProducts.add(pm);
	}
	
	/**
	 * Return all the ProductManager objects the InventoryManager knows about.
	 * @return An ArrayList of ProductManager objects.
	 */
	public ArrayList<ProductManager> listProducts() {
		return allProducts;
	}
	
	/**
	 * Given a product, this function returns whether the machine has
	 * any in stock.
	 * @param itemCode The product we're interested in.
	 * @return True if the product is in stock, false otherwise.
	 */
	public boolean isInStock(String itemCode) {
		ProductManager pm = find(itemCode);
		if (pm.isInStock()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Determines if the user inputted itemCode is legit or not.
	 * @param itemCode The item code the user inputted.
	 * @return True if the item code maps to an actual ProductManager or Product, false otherwise.
	 */
	public boolean isValidItemCode(String itemCode) {
		ProductManager pm = find(itemCode);
		if (pm == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * Creates an ArrayList of Product objects, where all Products in the ArrayList have a price
	 * equal to or less than the parameter passed in.
	 * @param price The price point we want to group Products by.
	 * @return An ArrayList containing Products that cost equal to or less than the specified price.
	 */
	public ArrayList<Product> productsByPrice(int[] price) {
		int priceInPennies = Cashier.toPennies(price);
		ArrayList<Product> matchingProducts = new ArrayList<Product>();
		for (ProductManager pm : allProducts) {
			if (Cashier.toPennies(pm.getProduct().getPrice()) <= priceInPennies) {
				matchingProducts.add(pm.getProduct());
			}
		}
		return matchingProducts;
	}
	
	/**
	 * This is a beefy function. It needs to take the itemCode passed in, make sure it is
	 * a) a valid item code, and b) the item code is in stock. After those checks,
	 * it should get a list of Products that are equal to or less than the change currently
	 * in the machine. Finally, we should check if the item code of the snack we want to purchased
	 * is in the list of products we can afford to buy. If so, then "vend" the snack and return
	 * true. Otherwise, return false!
	 * Note that this function simply "vends" the item from the InventoryManager. The method
	 * that calls this function will handle the "purchasing" of the Product from the Cashier.
	 * @param itemCode The itemCode we want to purchase.
	 * @param currentValue The amount of change currently inserted into the machine.
	 * @return True if we purchased this item, false otherwise.
	 */
	public boolean attemptPurchase(String itemCode, int[] currentValue) {
		if (isValidItemCode(itemCode) && isInStock(itemCode)) {
			ProductManager pm = find(itemCode);
			ArrayList<Product> availableProducts = productsByPrice(currentValue);
			if (availableProducts.contains(pm.getProduct())) {
				pm.vendItem();
				return true;
			}
		}		
		return false;
	}
	
	/**
	 * Iterates through the list of ProductManagers and checks if the itemCode passed in
	 * exists. If we find a match, return that ProductManager. Otherwise, return null.
	 * @param itemCode The itemCode we are investigating.
	 * @return The ProductManager of the corresponding Product with said itemCode, or null.
	 */
	public ProductManager find(String itemCode) {
		for (ProductManager pm : allProducts) {
			if (pm.getItemCode().equals(itemCode)) {
				return pm;
			}
		}
		return null;
	}
}
