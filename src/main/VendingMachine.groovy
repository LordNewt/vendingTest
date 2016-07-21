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


    //------------------//
    //  Public methods  //
    //------------------//

    String displayBalance() {
        if (balance > 0) {
            return String.format("Balance: \$%.2f", balance)
        }
        return "INSERT COIN"
    }

    String insertCoin(String coin) {
        if (coins.containsKey(coin.toLowerCase())) {
            balance += coins[coin.toLowerCase()]
        }
        displayBalance()
    }

}
