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

    Map<String, Integer> coinReturn = [
            "quarter": 0,
            "dime": 0,
            "nickel": 0
    ]

    Map<String,Float> menu = [
            "soda": 1.0,
            "chips": 0.5,
            "candy": 0.65
    ]


    //------------------//
    //  Helper methods  //
    //------------------//

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

    String insertCoin(String coin) {
        if (coins.containsKey(coin.toLowerCase())) {
            balance += coins[coin.toLowerCase()]
        } else {
            println "Unrecognized coin!"
        }
        displayBalance()
    }

    String selectProduct(String product) {
        if (menu.containsKey(product.toLowerCase())) {
            Float price = menu[product.toLowerCase()]
            if (balance >= price) {
                balance -= price
                makeBestChange()
                return ("THANK YOU. " + checkForChange())
            } else {
                return String.format("Price: \$%.2f, balance: \$%.2f", price, balance)
            }
        } else {
            return "UNKNOWN PRODUCT"
        }
    }

    String checkForChange() {
        return (coinReturnToString() + " in change slot")
    }

    String collectChange() {
        String output = coinReturnToString() + " returned"
        coinReturn.keySet().each { coinReturn[it] = 0 }
        return output
    }

}
