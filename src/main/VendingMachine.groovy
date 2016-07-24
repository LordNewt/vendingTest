package main

class VendingMachine {

    //----------------------//
    //  Member variables    //
    //----------------------//

    float balance = 0.00

    Map<String,Float> coins = [
            "nickel": 0.05,
            "dime": 0.10,
            "quarter": 0.25
    ]

    List<String> coinReturn = []

    Map<String,Float> menu = [
            "soda": 1.0,
            "chips": 0.5,
            "candy": 0.65
    ]


    //------------------//
    //  Helper methods  //
    //------------------//

    void makeBestChange(Float balance) {
        String bestCoin = null
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
        if (!bestCoin) {
            println String.format("ERROR: Cannot make any more change from \$%.2f", balance)
        } else {
            balance -= coins[bestCoin]
            coinReturn.add(bestCoin)
            if (balance) {
                makeBestChange(balance)
            }
        }
    }

    //------------------//
    //  Public methods  //
    //------------------//

    String displayBalance() {
        if (balance > 0) {
            return String.format("Balance: \$%.2f", balance)
        }
        return "INSERT COIN"
    }

    List<String> displayMenu() {
        List<String> output = []
        menu.each { String name, Float price ->
            output.add(String.format("%s: \$%.2f", name, price))
        }
        return output
    }

    String checkForChange() {
        String outputString = ""
        if (!coinReturn) {
            outputString += "No coins"
        } else {

        }
        outputString += " in coin return"
        return outputString
    }

    String insertCoin(String coin) {
        if (coins.containsKey(coin.toLowerCase())) {
            balance += coins[coin.toLowerCase()]
        } else {
            coinReturn.add(coin)
        }
        displayBalance()
    }

    String selectProduct(String product) {
        if (menu.containsKey(product.toLowerCase())) {
            Float price = menu[product.toLowerCase()]
            if (balance >= price) {
                balance -= price
                return "THANK YOU"
            } else {
                return String.format("Price: \$%.2f, balance: \$%.2f", price, balance)
            }
        } else {
            return "UNKNOWN PRODUCT"
        }
    }

}
