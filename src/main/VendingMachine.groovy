package main

class VendingMachine {

    //--------------//
    //  Constants   //
    //--------------//

    // Default initial product quantity
    static Integer INITIAL_QUANTITY = 5


    //----------------------//
    //  Member variables    //
    //----------------------//

    float balance = 0.00

    CoinTracker coinTracker = new CoinTracker()

    List<Product> products = [
            new Product(name: "soda", price: 1.0, quantity: INITIAL_QUANTITY),
            new Product(name: "chips", price: 0.5, quantity: INITIAL_QUANTITY),
            new Product(name: "candy", price: 0.65, quantity: INITIAL_QUANTITY)
    ]


    //------------------//
    //  Helper methods  //
    //------------------//

    /*
    *   Sets the quantity of a given product to a new value. Included as a
    *   debugging method, but could be used for other things in the future.
     */
    Boolean setProductQuantity(String productName, Integer newQuantity) {
        Product product = products.find { productName == it.name }
        if (!product) { return false }
        product.quantity = newQuantity
        return true
    }

    /*
    *   Sets the quantity of a given coin in the machine to a new value.
    *   Included as a debugging method, but could be used for other things
    *   in the future.
     */
    Boolean setCoinQuantity(String coin, Integer newQuantity) {
        return coinTracker.setMachineQuantity(coin, newQuantity)
    }


    //------------------//
    //  Public methods  //
    //------------------//

    /*
    *   Displays the current balance as a formatted string
     */
    String displayBalance() {
        if (balance > 0) {
            return String.format("Balance: \$%.2f", balance)
        }
        return "INSERT COIN"
    }

    /*
    *   Returns a list of product information as formatted strings
     */
    List<String> displayMenu() {
        List<String> output = []
        products.each { Product product ->
            output.add(String.format("%s -- Price: \$%.2f, Quantity: %s", product.name, product.price, product.quantity))
        }
        return output
    }

    /*
    *   Attempts to accept a given coin and, if accepted, adjust the
    *   current balance appropriately
     */
    String insertCoin(String coin) {
        Float coinValue = coinTracker.getCoinValue(coin)
        if (coinValue) {
            balance += coinValue
        } else {
            println "Unrecognized coin!"
        }
        displayBalance()
    }

    /*
    *   When the user wishes to purchase an item, it will check validity of
    *   product, makes sure it's not sold out, verifies that the user has
    *   a balance greater than or equal to the cost, and if all passes it will
    *   reduce the quantity available by one, and return the change
     */
    String selectProduct(String product) {
        Product productData = products.find { product.toLowerCase() == it.name }

        // Return if product not found
        if (!productData) {
            return "UNKNOWN PRODUCT"
        }

        // Return if product sold out
        if (!productData.quantity) {
            return "SOLD OUT, please select a different product"
        }

        // Return if the balance is too low
        if (balance < productData.price) {
            return String.format("Price: \$%.2f, balance: \$%.2f", productData.price, balance)
        }

        // Make sure exact change isn't required
        if (!coinTracker.canMakeChange(balance)) {
            return String.format("EXACT CHANGE ONLY. Price: \$%.2f, balance: \$%.2f", productData.price, balance)
        }

        // Ok, all valid.  Buy the product, make change.
        productData.quantity--
        balance -= productData.price
        coinTracker.makeBestChange(balance)
        return ("THANK YOU. " + checkForChange())

    }

    /*
    *   Forces balance out as coins into coin return
     */
    void returnBalance() {
        coinTracker.makeBestChange(balance)
    }

    /*
    *   Returns the contents of the coin return as a formatted string
     */
    String checkForChange() {
        return (coinTracker.coinReturnToString() + " in change slot")
    }

    /*
    *   Returns the contents of the coin return, and will also zero
    *   out the coin return
     */
    String collectChange() {
        String output = coinTracker.coinReturnToString() + " returned"
        coinTracker.resetChangeReturn()
        return output
    }

}
