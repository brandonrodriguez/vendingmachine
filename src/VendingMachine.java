import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class VendingMachine {

	private Cashier cashier;
	private InventoryManager im;
	private Scanner keypad;
	
	/**
	 * Instantiate the Cashier, InventoryManager, and keypad.
	 */
	public VendingMachine() {
		cashier = new Cashier();
		im = new InventoryManager();
		keypad = new Scanner(System.in);
	}
	
	/**
	 * Initializes products that are loaded into the vending machine, and then alerts the user
	 * that the machine is open for business by spelling out the commands accepted.
	 */
	public void turnOn() {
		initProducts();
		alertUser();
	}
	
	/**
	 * Prints out the different inputs accepted by this machine. My machine notified the user of the
	 * following commands:
	 * Penny, Nickel, Dime, Quarter: these commands add coins to the machine
	 * Return: returns all change in case the user changes their mind before purchasing
	 * Products: a list of product itemCodes, product names, and product prices the user can choose from
	 */
	public void alertUser() {
		System.out.println("Vending machine turned on.");
		System.out.println("At any time, you may add change (Penny, Nickel, Dime, Quarter),");
		System.out.println("make a snack selection to purchase,");
		System.out.println("or get your money back by typing 'Return'");
		System.out.println("Products carried at this machine:");
		ArrayList<ProductManager> products = im.listProducts();
		for (ProductManager pm : products) {
			System.out.println(pm.getItemCode() + '\t' + pm.getProduct().getSnackName() + '\t' + Cashier.showCash(pm.getProduct().getPrice()));
		}
	}
	
	/**
	 * Enters an infinite loop to listen for keypad input from the keyboard.
	 * This function is a giant if/elseif/else block to handle the different
	 * commands our vending machine accepts (coins, return, item codes).
	 */
	public void listen() {
		while (true) {
			String userInput = keypad.nextLine();
			int[] money;
			if (userInput.equals("Penny")) {
				money = cashier.addMoney(1);
				int[] penny = {0, 1};
				System.out.println(cashier.returnChange(penny));
				System.out.println(Cashier.showCash(cashier.currentValue()));
			} else if (userInput.equals("Nickel")) {
				money = cashier.addMoney(5);
				System.out.println(Cashier.showCash(money));
			} else if (userInput.equals("Dime")) {
				money = cashier.addMoney(10);
				System.out.println(Cashier.showCash(money));
			} else if (userInput.equals("Quarter")) {
				money = cashier.addMoney(25);
				System.out.println(Cashier.showCash(money));
			} else if (userInput.length() == 2) {
				boolean purchased = im.attemptPurchase(userInput, cashier.currentValue());
				if (purchased) {
					int priceInPennies = Cashier.toPennies(im.find(userInput).getProduct().getPrice());
					cashier.purchase(priceInPennies);
					System.out.println("Purchased!");
					System.out.println(cashier.returnChange(cashier.currentValue()));
					System.out.println(Cashier.showCash(cashier.currentValue()));
				} else {
					System.out.println("Not purchased! Maybe not enough money, or bad code, or out of stock!");
					System.out.println(Cashier.showCash(cashier.currentValue()));
				}
			} else if (userInput.equals("Return")) {
				System.out.println(cashier.returnChange(cashier.currentValue()));
			} else {
				System.out.println("Input not recognized as money or snack selection. Ignoring.");
			}
		}
	}
	
	/**
	 * Right now, just hard-coding some different snacks. These could easily be read
	 * in from a file to make it more dynamic.
	 */
	public void initProducts() {
		Scanner snackFile;
		try {
			snackFile = new Scanner(new File("snacks.txt"));
			while (snackFile.hasNextLine()) {
				String snackName = snackFile.next();
				snackName = snackName.replace('_', ' ');
				int snackCost = snackFile.nextInt();
				int snackQuantity = snackFile.nextInt();
				Product p = new Product(snackName, snackCost);
				im.addProduct(p, snackQuantity);
			}
			snackFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The main method simply needs to create a VendingMachine object,
	 * turn on the vending machine, and start listening for input. Only 3 lines needed!
	 * @param args
	 */
	public static void main(String[] args) {
		VendingMachine vm = new VendingMachine();
		vm.turnOn();
		vm.listen();
	}

}
