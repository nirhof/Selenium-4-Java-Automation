package sanity;

import extensions.UIActions;
import io.qameta.allure.Description;
import org.openqa.selenium.devtools.v85.network.model.ConnectionType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilities.CommonOps;
import utilities.PerformanceMetric;
import workflows.Webflows;

public class ShopDemoPerformanceTesting extends CommonOps {


    // Verify the performance metric for task duration
    // This test verifies whether the time taken for the task to complete, such as adding a product to the cart,
    // meets the expected threshold.
    @Test(description = "Test01 - Verify performance metric for adding a product to the cart")
    @Description("This test verifies whether the time taken to add a product to the cart meets the expected threshold.")
    public void test01_verifyPerformanceMetricTimeOfAddingAProductToCart() throws Exception {
        // Enable performance metric tracking
        performanceHandler.enableMetric();

        // Click on the store button in the top menu
        UIActions.click(topMenu.getStoreButton());

        // Sort the products by price from lowest to highest
        Webflows.SortProductsByPriceLowToHigh();

        // Add a product to the cart and return to the store
        Webflows.addProductAndReturnToStore(2);

        // go to cart page
        UIActions.mouseHover(products.getCartMenuButton());

        // Print performance metrics
        performanceHandler.getMetricList().forEach(metric -> System.out.println(metric.getName() + " " + metric.getValue()));
        performanceHandler.printMetric(PerformanceMetric.TaskDuration.toString());

        // Verify the performance metric for task duration meets the expected threshold of 0.9 seconds.
        performanceHandler.verifyPerformanceMetricTime(PerformanceMetric.TaskDuration.toString(), 0.9);

    }

    // Verify the performance metric for task duration with WiFi connection
    // This test verifies whether the time taken for the task to complete, such as sorting products by price,
    // meets the expected threshold.
    @Test(description = "Test02 - Verify the performance metric for task duration with WiFi connection")
    @Description("This test verifies that the performance metric for task duration with WiFi connection meets the expected threshold.")
    public void test02_verifyPerformanceMetricTimeWithWifiConnection() throws Exception {
        // Enable performance metric tracking
        performanceHandler.enableMetric();
        // Emulate WIFI network connection speed
        connectionHandler.emulateNetwork(100, 7500, 7500, ConnectionType.WIFI);

        // Click on the store button in the top menu
        UIActions.click(topMenu.getStoreButton());

        // Sort the products by price from lowest to highest
        Webflows.SortProductsByPriceLowToHigh();

        // Print performance metrics
        performanceHandler.printMetric(PerformanceMetric.TaskDuration.toString());

        // Verify the performance metric for task duration meets the expected threshold of 0.001 seconds.
        performanceHandler.verifyPerformanceMetricTime(PerformanceMetric.TaskDuration.toString(), 0.001);

    }
    @AfterMethod
    public void afterMethod() {
        Webflows.RemoveAllItem(cartPage.getProductRows()); // Remove all the prodcuts from the cart after each test method execution
        driver.get(getData("url")); // Navigate back to the Atid Store URL after each test method execution

    }
}
