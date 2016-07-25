package main

class VendingMachine {

    //--------------//
    //  Constants   //
    //--------------//

    // Default initial product quantity
    Integer INITIAL_QUANTITY = 5

    //----------------------//
    //  Member variables    //
    //----------------------//

    float balance = 0.00

    Map<String,Float> coins = [
            "nickel": 0.05,
            "dime": 0.10,
            "quarter": 0.25
    ]

    Map<String, Integer> coinReturn = [
            "quarter": 0,
            "dime": 0,
            "nickel": 0
    ]

    List<Product> products = [
            new Product(name: "soda", price: 1.0, quantity: INITIAL_QUANTITY),
            new Product(name: "chips", price: 0.5, quantity: INITIAL_QUANTITY),
            new Product(name: "candy", price: 0.65, quantity: INITIAL_QUANTITY)
    ]


    //------------------//
    //  Helper methods  //
    //------------------//

    /*
    *   Looks at the current balance and attempts to create change using the smallest
    *   quantity of coins possible.
     */
    void makeBestChange() {
        String bestCoin = null
        // Due to issues with Float value rounding, make sure we have
        // a legit value
        balance = balance.round(2)
        // Look for the biggest coin you can add to the coin return
        coins.each { String coin, Float value ->
            if (!bestCoin) {
                if (value <= balance) {
                    bestCoin = coin
                }
            }
            else if (value > coins[bestCoin] && value <= balance) {
                bestCoin = coin
            }
        }
        // Check results
        if (bestCoin) {
            balance -= coins[bestCoin]
            coinReturn[bestCoin]++
            if (balance > 0) {
                makeBestChange()
            }
        }
    }

    /*
    *   Formats the contents of the coinReturn list to something that is
    *   easy to read.  Displays in descending value order.
     */
    String coinReturnToString() {
        List<String> coinOutList = []
        coinReturn.each { String coin, Integer quantity ->
            if (quantity) {
                coinOutList.add((quantity == 1) ? "1 ${coin.capitalize()}" : "${quantity} ${coin.capitalize()}}s")
            }
        }
        String outputString = coinOutList.join(", ")
        if (!outputString) { outputString += "No coins"}
        return outputString
    }

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
        if (coins.containsKey(coin.toLowerCase())) {
            balance += coins[coin.toLowerCase()]
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

        // Ok, all valid.  Buy the product, make chance.
        productData.quantity--
        balance -= productData.price
        makeBestChange()
        return ("THANK YOU. " + checkForChange())

    }

    /*
    *   Returns the contents of the coin return as a formatted string
     */
    String checkForChange() {
        return (coinReturnToString() + " in change slot")
    }

    /*
    *   Returns the contents of the coin return, and will also zero
    *   out the coin return
     */
    String collectChange() {
        String output = coinReturnToString() + " returned"
        coinReturn.keySet().each { coinReturn[it] = 0 }
        return output
    }

}
