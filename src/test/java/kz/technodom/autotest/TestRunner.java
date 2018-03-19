package kz.technodom.autotest;

import org.testng.annotations.Test;

public class TestRunner extends TestBase {

    @Test
    public void orderAndCheckoutTest() throws InterruptedException {
        init();
        goToCategories("//a[@href='/catalog/smartphone']");
        selectAndgoToCheckout();
        checkout();
    }
}
