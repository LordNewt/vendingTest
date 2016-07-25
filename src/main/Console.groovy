package main

class Console {

    //------------------//
    //  User messages   //
    //------------------//

    static String welcomeString = '''
Welcome to RoboBroVend!
--
To insert a coin, type "insert <coin name>" (i.e. insert nickel).
* Note this machine accepts only nickels, dimes and quarters,
  one at a time.

To purchase an item, type "buy <item>" (i.e. buy soda).
To view the menu, type "show menu"

To return your coins, type "return coins"
To collect your chance, type "collect change"

At any time, type "help" for a list of commands.
--
'''

    static String signOffMessage = '''
Thank you for shopping RoboBroVend!

We know you have a choice of vending options, and we at RoboBroVend
would like to say thank you for supporting our all-natural, free-range,
locally-sourced, organic, non-GMO products.
'''

    static String helpString = '''
Command syntax:  command <arguments>

insert <coin>
buy <product>
show balance
show menu
return coins
collect change
'''


    //----------------------//
    //  Actual console code //
    //----------------------//

    static void main(String... args) {
        Boolean leaveMachine = false
        VendingMachine machine = new VendingMachine()

        println welcomeString

        // On first run, display menu and balance
        //----------------------------------------
        machine.displayMenu().each { println it }
        println "--"
        println machine.displayBalance()
        println "--"

        while(!leaveMachine) {

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
                println machine.displayBalance()
            } else if (userInput[0] == "buy") {
                if (userInput.size() > 1) {
                    println machine.selectProduct(userInput[1])
                } else {
                    println "Must supply a product as well, for example: buy soda"
                }
            } else if (userInput[0] == "return" && userInput[1] == "coins") {
                println machine.collectChange()
            } else if (userInput[0] == "show" && userInput[1] == "menu") {
                machine.displayMenu().each { println it }
            } else if (userInput[0] == "show" && userInput[1] == "balance") {
                println machine.displayBalance()
            } else if (userInput[0] == "collect" && userInput[1] == "change") {
                println machine.collectChange()
            } else if (userInput[0] == "help") {
                println helpString
            } else if (userInput[0] == "exit") {
                leaveMachine = true
            } else {
                println "Unrecognized command!"
            }
            println "--"
        }

        // When the user exits, give them a sign-off message
        //---------------------------------------------------
        println signOffMessage
    }
}
