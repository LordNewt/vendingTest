package main

class CoinTracker {

    //--------------//
    //  Constants   //
    //--------------//

    static Integer INITIAL_QUANTITY = 5


    //----------------------//
    //  Member variables    //
    //----------------------//

    // Acceptable coins and their dollar value
    Map<String,Float> coins = [
            "nickel": 0.05,
            "dime": 0.10,
            "quarter": 0.25
    ]

    // Information about coin quantity for the machine
    List<CoinData> coinData = [
            new CoinData(name: "quarter", inQueue: 0, inMachine: INITIAL_QUANTITY, inCoinReturn: 0),
            new CoinData(name: "dime", inQueue: 0, inMachine: INITIAL_QUANTITY, inCoinReturn: 0),
            new CoinData(name: "nickel", inQueue: 0, inMachine: INITIAL_QUANTITY, inCoinReturn: 0)
    ]


    //------------------//
    //  Helper methods  //
    //------------------//

    /*
    *   Uses the supplied balance and attempts to create change using the smallest
    *   quantity of coins possible.
     */
    void makeBestChange(Float balance) {
        String bestCoin = null

        // Due to issues with Float value rounding, make sure we have
        // a legit value
        balance = balance.round(2)

        // Look for the biggest coin you can add to the coin return
        coins.each { String coin, Float value ->
            // Get "in machine" details about the coin
            CoinData coinDetails = coinData.find { coin == it.name }
            if (!bestCoin) {
                // Only choose the coin if its value is less than or equal to
                // the remaining balance, and there are some of that coin in
                // the machine
                if ((value <= balance) && coinDetails.isAvailable()) {
                    bestCoin = coin
                }
            }
            // Only select a different coin if it has a higher value than the
            // currently selected coin, its value is less than or equal to the
            // remaining balance, and there are some of that coin in the machine
            else if (value > coins[bestCoin] && value <= balance && coinDetails.isAvailable()) {
                bestCoin = coin
            }
        }

        // If a coin was found that matched the criteria, deal with it. If not,
        // either balance is zero, or too low for a remaining available coin to
        // make change with.
        if (bestCoin) {
            // Reduce the remaining balance
            balance -= coins[bestCoin]
            // Reduce the quantity of the selected coin, either from the
            // machine or the queue, and increment the "in return" quantity
            CoinData coinDetails = coinData.find { bestCoin == it.name }
            if (!coinDetails) {
                println "ERROR: Could not find coin ${bestCoin} in machine"
                return
            }
            // Already checked for validity, so not necessary to check again
            coinDetails.ejectCoin()
            // If there is still a remaining balance, recurse through again
            if (balance > 0) {
                makeBestChange(balance)
            }
        }
    }


    //------------------//
    //  Public methods  //
    //------------------//

    /*
    *   Returns the dollar value of a supplied coin (if possible)
     */
    Float getCoinValue(String coinName) {
        if (!coins.containsKey(coinName.toLowerCase())) { return null }
        return coins[coinName.toLowerCase()]
    }

    /*
    *   Formats the contents of the coin return to something that is
    *   easy to read.  Displays in descending value order.
     */
    String coinReturnToString() {
        List<String> coinOutList = []
        coinData.each { CoinData coinDetails ->
            if (coinDetails.inCoinReturn) {
                // When formatting the result, take singular/plural into account
                coinOutList.add((coinDetails.inCoinReturn == 1) ?
                        "1 ${coinDetails.name.capitalize()}" :
                        "${coinDetails.inCoinReturn} ${coinDetails.name.capitalize()}s")
            }
        }
        String outputString = coinOutList.join(", ")
        // If there were no coins, make sure to account for that
        if (!outputString) { outputString += "No coins"}
        return outputString
    }

    /*
    *   Sets the "in machine" quantity of a given coin (if possible)
     */
    Boolean setMachineQuantity(String coinName, Integer newQuantity) {
        CoinData coinDetails = coinData.find { coinName.toLowerCase() == it.name }
        if (!coinDetails) {
            println "Could not set machine quantity for ${coinName}"
            return false
        }
        coinDetails.inMachine = newQuantity
        return true
    }

    /*
    *   Increments one of the specified coins in the "queue" of the machine
     */
    Boolean addCoinToQueue(String coinName) {
        CoinData coinDetails = coinData.find { coinName.toLowerCase() == it.name }
        if (!coinDetails) {
            println "Could not set queue quantity for ${coinName}"
            return false
        }
        coinDetails.inQueue++
        return true

    }

    /*
    *   Sets all "in coin return" values for coins to zero
     */
    void resetChangeReturn() {
        coinData.each { it.inCoinReturn = 0 }
    }

    /*
    *   Checks to see if the machine is capable of making correct change for
    *   a giving balance, and returns its result without permanently modifying
    *   the "in machine" and "in coin return" values
     */
    Boolean canMakeChange(Float balance) {
        // Get initial quantities
        Map<String,Integer> inMachine = [:]
        Map<String,Integer> inQueue = [:]
        coinData.each { CoinData coinDetails ->
            inMachine[coinDetails.name] = coinDetails.inMachine
            inQueue[coinDetails.name] = coinDetails.inQueue
        }

        // See if we can make correct change
        makeBestChange(balance)
        Float actualChange = 0
        coinData.each { actualChange += (it.inCoinReturn * coins[it.name]) }

        // Now that we'll have our answer, reset the work we did
        coinData.each { CoinData coinDetails ->
            coinDetails.inMachine = inMachine[coinDetails.name]
            coinDetails.inQueue = inQueue[coinDetails.name]
            coinDetails.inCoinReturn = 0
        }

        // Return the answer
        return (actualChange.round(2) == balance.round(2))
    }

    /*
    *   Puts all "in queue" coins into the "in machine" category
     */
    void putCoinsIntoMachine() {
        coinData.each { it.inMachine += it.inQueue; it.inQueue = 0 }
    }

}
