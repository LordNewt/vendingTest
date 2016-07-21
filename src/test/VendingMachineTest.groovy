package test

import main.VendingMachine
import org.junit.Before
import org.junit.Test
import static org.junit.Assert.*

class VendingMachineTest {

    VendingMachine vendingMachine

    @Before
    void setup() {
        vendingMachine = new VendingMachine()
    }

    /*
     *  Accept coin tests
     */

    @Test
    void CoinTestAcceptsNickels() {
        assertEquals("Balance: \$0.05", vendingMachine.insertCoin("Nickel"))
    }

    @Test
    void CoinTestAcceptsNickelsAllCaps() {
        assertEquals("Balance: \$0.05", vendingMachine.insertCoin("NICKEL"))
    }

    @Test
    void CoinTestAcceptsNickelsLowercase() {
        assertEquals("Balance: \$0.05", vendingMachine.insertCoin("nickel"))
    }

    @Test
    void CoinTestAcceptsDimes() {
        assertEquals("Balance: \$0.10", vendingMachine.insertCoin("Dime"))
    }

    @Test
    void CoinTestAcceptsQuarters() {
        assertEquals("Balance: \$0.25", vendingMachine.insertCoin("Quarter"))
    }

    @Test
    void CoinTestRejectsUnknown() {
        assertEquals("INSERT COIN", vendingMachine.insertCoin("Penny"))
    }


    /*
    *  Select Product tests
     */
    @Test
    void SelectColaWithInsufficientBalanceDisplaysPrice() {
        vendingMachine.insertCoin("nickel")
        assertEquals("Price: \$1.00, balance: \$0.05", vendingMachine.selectProduct("Soda"))
    }

    @Test
    void SelectColaWithSufficientBalanceDisplaysThankYou() {
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        assertEquals("THANK YOU", vendingMachine.selectProduct("Soda"))
    }

    @Test
    void SelectChipsWithInsufficientBalanceDisplaysPrice() {
        vendingMachine.insertCoin("nickel")
        assertEquals("Price: \$0.50, balance: \$0.05", vendingMachine.selectProduct("Chips"))
    }

    @Test
    void SelectChipsWithSufficientBalanceDisplaysThankYou() {
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        assertEquals("THANK YOU", vendingMachine.selectProduct("Chips"))
    }

    @Test
    void SelectCandyWithInsufficientBalanceDisplaysPrice() {
        vendingMachine.insertCoin("nickel")
        assertEquals("Price: \$0.65, balance: \$0.05", vendingMachine.selectProduct("Candy"))
    }

    @Test
    void SelectCandyWithSufficientBalanceDisplaysThankYou() {
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        assertEquals("THANK YOU", vendingMachine.selectProduct("Candy"))
    }

    @Test
    void SelectMultipleWithSufficientBalanceDisplaysThankYouUntilBalanceTooLow() {
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        assertEquals("THANK YOU", vendingMachine.selectProduct("Soda"))
        assertEquals("THANK YOU", vendingMachine.selectProduct("Chips"))
        assertEquals("Price: \$0.65, balance: \$0.25", vendingMachine.selectProduct("Candy"))
    }

}
