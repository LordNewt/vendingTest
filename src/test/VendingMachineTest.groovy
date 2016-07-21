package test

import main.VendingMachine
import org.junit.Test
import static org.junit.Assert.*

class VendingMachineTest {

    /*
     *  Accept coin tests
     */

    @Test
    void CoinTestAcceptsNickels() {
        VendingMachine vendingMachine = new VendingMachine()

        assertEquals("Balance: \$0.05", vendingMachine.insertCoin("Nickel"))
    }

    @Test
    void CoinTestAcceptsNickelsAllCaps() {
        VendingMachine vendingMachine = new VendingMachine()

        assertEquals("Balance: \$0.05", vendingMachine.insertCoin("NICKEL"))
    }

    @Test
    void CoinTestAcceptsNickelsLowercase() {
        VendingMachine vendingMachine = new VendingMachine()

        assertEquals("Balance: \$0.05", vendingMachine.insertCoin("nickel"))
    }

    @Test
    void CoinTestAcceptsDimes() {
        VendingMachine vendingMachine = new VendingMachine()

        assertEquals("Balance: \$0.10", vendingMachine.insertCoin("Dime"))
    }

    @Test
    void CoinTestAcceptsQuarters() {
        VendingMachine vendingMachine = new VendingMachine()

        assertEquals("Balance: \$0.25", vendingMachine.insertCoin("Quarter"))
    }

    @Test
    void CoinTestRejectsUnknown() {
        VendingMachine vendingMachine = new VendingMachine()

        assertEquals("INSERT COIN", vendingMachine.insertCoin("Penny"))
    }
}
