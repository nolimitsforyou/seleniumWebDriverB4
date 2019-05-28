import org.testng.Assert;
import org.testng.annotations.Test;


public class ProductCartTest extends TestBase{

    @Test
    public void test() {
        app.openUrl("http://localhost/litecart/en/");
        for (int i = 0; i < 3; i++) {
            int beforeAddProduct = app.cart.getProductsCount();
            app.addProductToCart();
            int afterAddProduct = app.cart.getProductsCount();
            Assert.assertTrue(beforeAddProduct != afterAddProduct);
        }
        app.removeAllProductsFromCart();
    }
}
