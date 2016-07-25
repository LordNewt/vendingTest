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
        vendingMachine.balance = 0.05
        assertEquals("Price: \$1.00, balance: \$0.05", vendingMachine.selectProduct("Soda"))
    }

    @Test
    void SelectColaWithSufficientBalanceDisplaysThankYou() {
        vendingMachine.balance = 1.0
        assertEquals("THANK YOU. No coins in change slot", vendingMachine.selectProduct("Soda"))
    }

    @Test
    void SelectChipsWithInsufficientBalanceDisplaysPrice() {
        vendingMachine.balance = 0.05
        assertEquals("Price: \$0.50, balance: \$0.05", vendingMachine.selectProduct("Chips"))
    }

    @Test
    void SelectChipsWithSufficientBalanceDisplaysThankYou() {
        vendingMachine.balance = 0.5
        assertEquals("THANK YOU. No coins in change slot", vendingMachine.selectProduct("Chips"))
    }

    @Test
    void SelectCandyWithInsufficientBalanceDisplaysPrice() {
        vendingMachine.balance = 0.05
        assertEquals("Price: \$0.65, balance: \$0.05", vendingMachine.selectProduct("Candy"))
    }

    @Test
    void SelectCandyWithSufficientBalanceDisplaysThankYouAndChangeBalance() {
        vendingMachine.balance = 0.75
        assertEquals("THANK YOU. 1 Dime in change slot", vendingMachine.selectProduct("Candy"))
    }


    /*
    *  Make change tests
     */

    @Test
    void MakeBestChangeFromZeroReturnsEmptyChangeSlot() {
        vendingMachine.balance = 0
        vendingMachine.makeBestChange()
        assertEquals("No coins in change slot", vendingMachine.checkForChange())
    }

    @Test
    void MakeBestChangeFromTenCentsReturnsDime() {
        vendingMachine.balance = 0.1
        vendingMachine.makeBestChange()
        assertEquals("1 Dime in change slot", vendingMachine.checkForChange())
    }

    @Test
    void MakeBestChangeFromThirtyCentsReturnsQuarterAndNickel() {
        vendingMachine.balance = 0.3
        vendingMachine.makeBestChange()
        assertEquals("1 Quarter, 1 Nickel in change slot", vendingMachine.checkForChange())
    }

    @Test
    void MakeBestChangeFromUnevenNumberDoesntBlowUp() {
        vendingMachine.balance = 0.12
        vendingMachine.makeBestChange()
        assertEquals("1 Dime in change slot", vendingMachine.checkForChange())
    }

    @Test
    void SelectProductWithExactChangeDisplaysThankYouAndNoChange() {
        vendingMachine.balance = 0.5
        assertEquals("THANK YOU. No coins in change slot", vendingMachine.selectProduct("Chips"))
        assertEquals("No coins in change slot", vendingMachine.checkForChange())
    }

    @Test
    void SelectProductWithChangeShowsCorrectCoinInReturn() {
        vendingMachine.balance = 0.75
        assertEquals("THANK YOU. 1 Quarter in change slot", vendingMachine.selectProduct("Chips"))
        assertEquals("1 Quarter in change slot", vendingMachine.checkForChange())
    }

    @Test
    void SelectProductWithMultipleCoinsInChangeShowsCorrectCoinsInReturn() {
        vendingMachine.balance = 1.05
        assertEquals("THANK YOU. 1 Quarter, 1 Dime, 1 Nickel in change slot", vendingMachine.selectProduct("Candy"))
        assertEquals("1 Quarter, 1 Dime, 1 Nickel in change slot", vendingMachine.checkForChange())
    }


    /*
    *  Return Coins test
     */

    @Test
    void CollectingChangeZeroesOutCoinSlot() {
        vendingMachine.balance = 0.75
        assertEquals("THANK YOU. 1 Quarter in change slot", vendingMachine.selectProduct("Chips"))
        assertEquals("1 Quarter returned", vendingMachine.collectChange())
        assertEquals("No coins in change slot", vendingMachine.checkForChange())
    }


    /*
    *  Sold Out tests
     */
    @Test
    void PurchasingSoldOutItemDisplaysUnavailableMessage() {
        vendingMachine.balance = 1.0
        assertTrue(vendingMachine.setProductQuantity("chips", 0))
        assertEquals("SOLD OUT, please select a different product", vendingMachine.selectProduct("Chips"))
    }


    /*
    *   Exact change tests
     */
    @Test
    void ProductSelectedButOutOfBestCoinCanStillMakeChange() {
        vendingMachine.balance = 0.6
        assertTrue(vendingMachine.setCoinQuantity("dime", 0))
        assertEquals("THANK YOU. 2 Nickels in change slot", vendingMachine.selectProduct("Chips"))
    }

    @Test
    void UnableToMakeChangeForChipsDisplaysExactChangeMessage() {
        vendingMachine.balance = 0.6
        assertTrue(vendingMachine.setCoinQuantity("dime", 0))
        assertTrue(vendingMachine.setCoinQuantity("nickel", 0))
        assertEquals("EXACT CHANGE ONLY. Price: \$0.50, balance: \$0.60", vendingMachine.selectProduct("Chips"))
    }

}
