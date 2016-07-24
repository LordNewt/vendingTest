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
    void SelectUnknownProductDisplaysThatMessage() {
        assertEquals("UNKNOWN PRODUCT", vendingMachine.selectProduct("Cat"))
    }

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
        assertEquals("THANK YOU, no change returned", vendingMachine.selectProduct("Soda"))
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
        assertEquals("THANK YOU, no change returned", vendingMachine.selectProduct("Chips"))
    }

    @Test
    void SelectCandyWithInsufficientBalanceDisplaysPrice() {
        vendingMachine.insertCoin("nickel")
        assertEquals("Price: \$0.65, balance: \$0.05", vendingMachine.selectProduct("Candy"))
    }

    @Test
    void SelectCandyWithSufficientBalanceDisplaysThankYouAndChangeBalance() {
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        assertEquals("THANK YOU, returning \$0.10 in change", vendingMachine.selectProduct("Candy"))
    }


    /*
    *  Make change tests
     */

    @Test
    void MakeBestChangeFromZeroReturnsEmptyChangeSlot() {
        vendingMachine.makeBestChange(0.0)
        assertEquals("No coins in change slot", vendingMachine.checkForChange())
    }

    @Test
    void MakeBestChangeFromTenCentsReturnsDime() {
        vendingMachine.makeBestChange(0.1)
        assertEquals("1 Dime in change slot", vendingMachine.checkForChange())
    }

    @Test
    void MakeBestChangeFromThirtyCentsReturnsQuarterAndNickel() {
        vendingMachine.makeBestChange(0.3)
        assertEquals("1 Quarter, 1 Nickel in change slot", vendingMachine.checkForChange())
    }

    @Test
    void MakeBestChangeFromUnevenNumberDoesntBlowUp() {
        vendingMachine.makeBestChange(0.12)
        assertEquals("1 Dime in change slot", vendingMachine.checkForChange())
    }

    @Test
    void SelectProductWithExactChangeDisplaysThankYouAndNoChange() {
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        assertEquals("THANK YOU, no change returned", vendingMachine.selectProduct("Chips"))
        assertEquals("No coins in change slot", vendingMachine.checkForChange())
    }

    @Test
    void SelectProductWithChangeShowsCorrectCoinInReturn() {
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        assertEquals("THANK YOU, returning \$0.25 in change", vendingMachine.selectProduct("Chips"))
        assertEquals("1 Quarter in change slot", vendingMachine.checkForChange())
    }

    @Test
    void SelectProductWithMultipleCoinsInChangeShowsCorrectCoinsInReturn() {
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("nickel")
        assertEquals("THANK YOU, returning \$0.40 in change", vendingMachine.selectProduct("Candy"))
        assertEquals("1 Quarter, 1 Dime, 1 Nickel in change slot", vendingMachine.checkForChange())
    }

    @Test
    void CollectingChangeZeroesOutCoinSlot() {
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        vendingMachine.insertCoin("quarter")
        assertEquals("THANK YOU, returning \$0.25 in change", vendingMachine.selectProduct("Chips"))
        assertEquals("1 Quarter returned", vendingMachine.collectChange())
        assertEquals("No coins in change slot", vendingMachine.checkForChange())
    }

}
