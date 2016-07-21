package main

class Console {

    static void main(String... args) {
        Boolean leaveMachine = false
        VendingMachine machine = new VendingMachine()

        println '''
Welcome to RoboBroVend!
--
To insert a coin, type "insert <coin name>" (i.e. insert nickel).
* Note this machine accepts only nickels, dimes and quarters,
  one at a time.
--
'''
        while(!leaveMachine) {
            // Begin by displaying balance
            //-----------------------------
            println machine.displayBalance()
            println "--"

            // Prompt user for input
            //-----------------------
            String input = System.in.newReader().readLine()

            // Parse input
            //-------------
            List<String> userInput = input.split(' ')
            if (userInput[0] == "insert") {
                if (userInput.size() > 1) {
                    machine.insertCoin(userInput[1])
                } else {
                    println "Must supply a coin as well, for example: insert nickle"
                }
            } else if (userInput[0] == "exit") {
                leaveMachine = true
            } else {
                println "Unrecognized command!"
            }
        }
    }
}
