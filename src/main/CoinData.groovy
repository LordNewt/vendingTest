package main

class CoinData {

    String name
    Integer inQueue
    Integer inMachine
    Integer inCoinReturn

    /*
    *   A coin is "available" if it's in either the queue or the machine
     */
    Boolean isAvailable() {
        return (inQueue || inMachine)
    }

    /*
    *   Eject the specified coin into the coin return. Preference will be
    *   given to "in machine" coins.
     */
    Boolean ejectCoin() {
        if (inMachine) { inMachine-- }
        else if (inQueue) { inQueue-- }
        else { return false }

        inCoinReturn++
        return true
    }
}
