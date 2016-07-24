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
