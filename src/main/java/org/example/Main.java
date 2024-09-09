package org.example;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public WebDriver driver;
    public String expected_title;
    public String actual_title;


    @BeforeTest
    public void launchbrowser() {
        System.out.println("launching Chrome browser");
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test(priority = 0)
    public void verifyhomepagetitle(){
        driver.get("http://live.techpanda.org/index.php/");
        expected_title="Home page";
        actual_title=driver.getTitle();
        Assert.assertEquals(expected_title,actual_title);
    }

    @Test(priority = 1)
    public void verifymobilepagetitle(){
        driver.findElement(By.xpath("//*[@id=\"nav\"]/ol/li[1]")).click();
        String expected ="Mobile";
        String actual=driver.getTitle();
        Assert.assertEquals(expected,actual);
    }

    @Test(priority = 2)
    public void sortmobilebyname(){
        List<WebElement> mobilesbeforesort=driver.findElements(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul"));
        List<String> mobilesbeforesortlist=new ArrayList<>();
        Select drpdown=new Select(driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/div[1]/div[1]/div/select")));
        drpdown.selectByVisibleText("Name");

        List<WebElement> mobilesaftersort=driver.findElements(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul"));
        List<String> mobilesaftersortlist=new ArrayList<>();

        Collections.sort(mobilesbeforesortlist);
        Assert.assertEquals(mobilesbeforesortlist,mobilesaftersortlist);
    }
    @Test(priority = 3)
    public void verifyproductpriceinlistpageanddetailspage(){
        driver.get("http://live.techpanda.org/index.php/");
        driver.findElement(By.xpath("//*[@id=\"nav\"]/ol/li[1]")).click();
        String productprice=driver.findElement(By.xpath("//*[@id=\"product-price-1\"]/span")).getText();
        driver.findElement(By.id("product-collection-image-1")).click();
        String prouctdetailsprice=driver.findElement(By.xpath("//*[@id=\"product-price-1\"]/span")).getText();
        Assert.assertEquals(productprice,prouctdetailsprice);
    }


    @Test(priority = 4)
    public void addtocartwithhighquantity(){
        driver.get("http://live.techpanda.org/index.php/");
        driver.findElement(By.xpath("//*[@id=\"nav\"]/ol/li[1]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[3]/div/div[3]/button/span/span")).click();
        driver.findElement(By.xpath("//*[@id=\"shopping-cart-table\"]/tbody/tr/td[4]/input")).sendKeys("1000");
        driver.findElement(By.xpath("//*[@id=\"shopping-cart-table\"]/tbody/tr/td[4]/button")).click();
        String expected="Some of the products cannot be ordered in requested quantity.";
        String actual=driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div/div/ul/li/ul/li/span")).getText();
        Assert.assertEquals(expected,actual);
    }

 
    @Test(priority = 5)
    public void removefromcart(){
        driver.get("http://live.techpanda.org/index.php/");
        driver.findElement(By.xpath("//*[@id=\"nav\"]/ol/li[1]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[3]/div/div[3]/button/span/span")).click();
        driver.findElement(By.xpath("//*[@id=\"empty_cart_button\"]/span/span")).click();
        String expectedtext="You have no items in your shopping cart.";
        String actualtext=driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div/div[2]/p[1]")).getText();
        Assert.assertEquals(expectedtext,actualtext);
    }

    @Test
    public void comparetwoproducts() {
        driver.get("http://live.techpanda.org/index.php/");
        driver.findElement(By.xpath("//*[@id=\"nav\"]/ol/li[1]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[3]/div/div[3]/ul/li[2]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[1]/div/div[3]/ul/li[2]/a")).click();
        driver.findElement(By.cssSelector("button[class='button']")).click();
        String MainWindow=driver.getWindowHandle();
        Set<String> s1 = driver.getWindowHandles();
        Iterator<String> i1 = s1.iterator();
        while (i1.hasNext()) {
            String ChildWindow = i1.next();
            if (!MainWindow.equalsIgnoreCase(ChildWindow)) {
                driver.switchTo().window(ChildWindow);
               String expected ="COMPARE PRODUCTS";
               String actual=driver.findElement(By.cssSelector(".page-popup h1")).getText();
               Assert.assertEquals(expected,actual);
               driver.findElement(By.cssSelector(".buttons-set button.button")).click();
            }
        }
    }

    @Test
    public void register(){
        driver.get("http://live.techpanda.org/index.php/");
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a/span[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"header-account\"]/div/ul/li[1]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"login-form\"]/div/div[1]/div[2]/a/span/span")).click();
        driver.findElement(By.id("firstname")).sendKeys("Ahmed");
        driver.findElement(By.id("middlename")).sendKeys("Abdelkader");
        driver.findElement(By.id("lastname")).sendKeys("Ahmed");
        driver.findElement(By.id("email_address")).sendKeys("Eng.ahmed.abdelkadeer.it@gmail.com");
        driver.findElement(By.id("password")).sendKeys("Test@123");
        driver.findElement(By.id("confirmation")).sendKeys("Test@123");
        driver.findElement(By.xpath("//*[@id=\"form-validate\"]/div[2]/button/span/span")).click();
        String expected ="Thank you for registering with Main Website Store.";
        String actual=driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div/div/ul/li/ul/li/span")).getText();
        Assert.assertEquals(expected,actual);
    }

   
    @Test
    public void sharewishlist(){
        driver.get("http://live.techpanda.org/index.php/");
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a/span[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"header-account\"]/div/ul/li[6]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("Eng.ahmed.abd.elkadeer.it@gmail.com");
        driver.findElement(By.id("pass")).sendKeys("Test@123");
        driver.findElement(By.xpath("//*[@id=\"send2\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"nav\"]/ol/li[2]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[2]/ul/li[2]/div/div[3]/ul/li[1]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"wishlist-view-form\"]/div/div/button[1]/span/span")).click();
        driver.findElement(By.id("email_address")).sendKeys("Eng.ahmed.abd.elkadeer.it@gmail.com");
        driver.findElement(By.id("message")).sendKeys("share my wishlist");
        driver.findElement(By.xpath("//*[@id=\"form-validate\"]/div[2]/button/span/span")).click();
        String expectedtxt="Your Wishlist has been shared.";
        String actualtxt=driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div/div[1]/ul/li/ul/li/span")).getText();
        Assert.assertEquals(expectedtxt,actualtxt);
    }

    @Test
    public void purschaseproductsandplaceorder() {
        driver.get("http://live.techpanda.org/index.php/");
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a/span[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"header-account\"]/div/ul/li[6]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("test123@gmail.com");
        driver.findElement(By.id("pass")).sendKeys("Test@123");
        driver.findElement(By.xpath("//*[@id=\"send2\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"nav\"]/ol/li[2]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[2]/ul/li[1]/div/div[3]/ul/li[1]/a")).click();
        driver.findElement(By.cssSelector(".button.button.btn-cart")).click();
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div/div/div[1]/ul/li/button/span/span")).click();
        driver.findElement(By.id("billing:company")).sendKeys("risonfield");
        driver.findElement(By.xpath("//*[@id=\"billing:street1\"]")).sendKeys("ABC");
        driver.findElement(By.xpath("//*[@id=\"billing:street2\"]")).sendKeys("ABC");
        driver.findElement(By.name("billing[city]")).sendKeys("New York");
        Select statedrpdown = new Select(driver.findElement(By.id("billing:region_id")));
        statedrpdown.selectByVisibleText("New York");
        driver.findElement(By.id("billing:postcode")).sendKeys("542896");
        Select countrydropdown = new Select(driver.findElement(By.name("billing[country_id]")));
        countrydropdown.selectByVisibleText("United States");
        driver.findElement(By.xpath("//*[@id=\"billing:telephone\"]")).sendKeys("12345678");
        driver.findElement(By.xpath("//*[@id=\"billing:fax\"]")).sendKeys("ABC");
        driver.findElement(By.xpath("//*[@id=\"billing:use_for_shipping_yes\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"billing-buttons-container\"]/button/span/span")).click();
        driver.findElement(By.xpath("//*[@id=\"shipping-method-buttons-container\"]/button/span/span")).click();
        driver.findElement(By.xpath("//*[@id=\"p_method_checkmo\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"payment-buttons-container\"]/button/span/span")).click();
        String expectedtotal = "$620.00";
        String actualtotal = driver.findElement(By.xpath("//*[@id=\"checkout-review-table\"]/tfoot/tr[3]/td[2]/strong/span")).getText();
        Assert.assertEquals(expectedtotal, actualtotal);
        driver.findElement(By.xpath("//*[@id=\"review-buttons-container\"]/button/span/span")).click();
        String expected = "YOUR ORDER HAS BEEN RECEIVED.";
        String actual = driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div/div[1]/h1")).getText();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void saveorders(){
        driver.get("http://live.techpanda.org/index.php/");
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a/span[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"header-account\"]/div/ul/li[1]/a")).click();
        driver.findElement(By.name("login[username]")).sendKeys("hatemmadouh22@gmail.com");
        driver.findElement(By.id("pass")).sendKeys("2278174Hd");
        driver.findElement(By.id("send2")).click();
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[1]/div/div[2]/ul/li[4]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"my-orders-table\"]/tbody/tr/td[6]/span/a[1]")).click();
        String expectedtext="ORDER #100022862 - PENDING";
        String actualtext=driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div/div[1]/h1")).getText();
        Assert.assertEquals(expectedtext,actualtext);
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div/div[1]/a[2]")).click();
        String mainwindow=driver.getWindowHandle();
        Set<String> s1=driver.getWindowHandles();
        Iterator<String> i1= s1.iterator();
        while(i1.hasNext()){
            String childwindow=i1.next();
            if (!mainwindow.equalsIgnoreCase(childwindow)){
                driver.switchTo().window(childwindow);
                driver.findElement(By.xpath("//*[@id=\"sidebar\"]//print-preview-button-strip//div/cr-button[1]")).click();
            }
        }
    }
     
    @Test
    public void reorder(){
        driver.get("http://live.techpanda.org/index.php/");
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a")).click();
        driver.findElement(By.xpath("//*[@id=\"header-account\"]/div/ul/li[1]/a")).click();
        driver.findElement(By.name("login[username]")).sendKeys("test123@gmail.com");
        driver.findElement(By.id("pass")).sendKeys("Test@123");
        driver.findElement(By.xpath("//*[@id=\"send2\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"my-orders-table\"]/tbody/tr/td[6]/span/a[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"shopping-cart-table\"]/tbody/tr/td[4]/input")).sendKeys("2");
        driver.findElement(By.xpath("//*[@id=\"shopping-cart-table\"]/tbody/tr/td[4]/button")).click();
        String expectedprice="$7,440.00";
        String actualprice=driver.findElement(By.xpath("//*[@id=\"shopping-cart-totals-table\"]/tfoot/tr/td[2]/strong/span")).getText();
        Assert.assertEquals(expectedprice,actualprice);
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div/div/div[3]/div/ul/li[1]/button")).click();
        Select drp=new Select(driver.findElement(By.xpath("//*[@id=\"billing-address-select\"]")));
        drp.selectByVisibleText("New Address");
        driver.findElement(By.xpath("//*[@id=\"billing-buttons-container\"]/button")).click();
        driver.findElement(By.xpath("//*[@id=\"shipping-method-buttons-container\"]/button")).click();
        driver.findElement(By.id("p_method_checkmo")).click();
        driver.findElement(By.xpath("//*[@id=\"payment-buttons-container\"]/button/span/span")).click();
        driver.findElement(By.xpath("//*[@id=\"review-buttons-container\"]/button/span/span")).click();
        String expected = "YOUR ORDER HAS BEEN RECEIVED.";
        String actual=driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div/div[1]/h1")).getText();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void verifydiscountcoupon(){
        driver.get("http://live.techpanda.org/index.php/");
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a")).click();
        driver.findElement(By.xpath("//*[@id=\"header-account\"]/div/ul/li[1]/a")).click();
        driver.findElement(By.name("login[username]")).sendKeys("test123@gmail.com");
        driver.findElement(By.id("pass")).sendKeys("Test@123");
        driver.findElement(By.xpath("//*[@id=\"send2\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"nav\"]/ol/li[1]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[1]/div/h2/a")).click();
        driver.findElement(By.xpath("//*[@id=\"product_addtocart_form\"]/div[4]/div/div/div[2]/button/span/span")).click();
        WebElement discountcode=driver.findElement(By.xpath("//*[@id=\"coupon_code\"]"));
        discountcode.sendKeys("GURU50");
        driver.findElement(By.xpath("//*[@id=\"discount-coupon-form\"]/div/div/div/div/button/span/span")).click();
        String expected="Coupon code \"GURU50\" was applied.";
        String actual =driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div/div/ul/li/ul/li/span")).getText();
        Assert.assertEquals(expected,actual);

    }


}
