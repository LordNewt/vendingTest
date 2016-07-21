package test

import org.junit.Test

class VendingMachineTest {

    @Test
    void CoinTestAcceptsNickels() {
        VendingMachine vendingMachine = new VendingMachine()

        assertEquals("Balance: \$0.05", vendingMachine.insertCoin("Nickel"))
    }
}
