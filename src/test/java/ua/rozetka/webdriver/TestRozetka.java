package ua.rozetka.webdriver;

import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import static jdk.nashorn.internal.objects.NativeMath.log;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.*;
import ua.rozetka.webdriver.ProductObject.EntityGoods;
import java.lang.*;
import java.util.concurrent.TimeUnit;
import java.io.*;

import org.openqa.selenium.interactions.Actions;
import java.lang.InterruptedException;
import java.awt.*;
import org.testng.TestListenerAdapter;
import ua.rozetka.webdriver.ConteinerOfMethods;


public class TestRozetka extends ConteinerOfMethods   {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private String Name1;
    private String Name2;
    private String Price1;
    private String Price2;
    private String Price3;
    private String EmptyCart;
    private int length;
    private List<EntityGoods> EntityGoods;
    private int summa;
    private String FileName = "AddProductTestLog";

    public static String removeCharStr(String s, int pos) {

        return s.substring(0,pos)+s.substring(pos+1);

    }

    public void sleep(int seconds)
    {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {

        }
    }

    public void AddGoodsInCart(String ValueProduct, String PriceValue, String ActiveElement, String NumberBuyButton, int IndexList, String sign) {
        //String Name = driver.findElement(By.xpath("//a[@onclick=\"document.fireEvent('goodsTitleClick', {extend_event: [{name: 'goods_id', value: 17982198}, {name: 'eventLocation', value: 'CatalogHighLevel'}]}); return true\"]")).getText();
        String Name = driver.findElement(By.xpath("//a[@onclick=\"document.fireEvent('goodsTitleClick', {extend_event: [{name: 'goods_id', value: "+ValueProduct+"}, {name: 'eventLocation', value: 'CatalogHighLevel'}]}); return true\"]")).getText();
        saveDataInFile(FileName,"Select and copy name product Name "+Name);
        //System.out.println(Name);
        String Price = driver.findElement(By.xpath(PriceValue)).getText();
        System.out.println(Price);
        Price = Price.replaceAll("грн","");
        Price = Price.replaceAll(" ","");
        int length1 = Price.length();
        Price = removeCharStr(Price,length1-1);
        saveDataInFile(FileName,"Select and copy price product Price "+Price);
        //System.out.println(Price);
        EntityGoods.add(new EntityGoods(Name,Price));
        sleep(5);
        Actions action = new Actions(driver);
        //action.moveToElement(driver.findElement(By.xpath("//div[@name='prices_active_element_original']"+ActiveElement)))
        action.moveToElement(driver.findElement(By.xpath("//div[@id='catalog_goods_block']/div/div" + ActiveElement + "/div/div/div/div/div["+sign+"]")));
        action.perform();
        saveDataInFile(FileName,"Go to selected product");
        driver.findElement(By.xpath("//div[@id='catalog_goods_block']/div/div"+NumberBuyButton+"/div/div/div/div/div[7]/div/div/div/form/span/button/span")).click();
        saveDataInFile(FileName,"Click button Buy");
        sleep(10);
        String Sum = driver.findElement(By.xpath("//span[@name='sum']")).getText();
        //System.out.println(Sum);
        Sum = Sum.replaceAll("грн","");
        Sum = Sum.replaceAll(" ","");
        saveDataInFile(FileName,"Summa product in cart "+Sum);
        //System.out.println(Sum);
        saveDataInFile(FileName,"Summa product in cart "+EntityGoods.get(IndexList).getPriceGoods());
        //System.out.println(EntityGoods.get(IndexList).getPriceGoods());
        try {
            assertEquals(EntityGoods.get(IndexList).getPriceGoods(), Sum);
            saveDataInFile(FileName,"Summa in cart and summa product equals");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        driver.findElement(By.linkText("Продолжить покупки")).click();
        saveDataInFile(FileName,"Click button Continue shopping");
        driver.findElement(By.xpath("//div[@id='content-inner-block']/div[2]/div/header/div/ul/li[4]/a/span")).click();
        saveDataInFile(FileName,"Go back to the selected section");
    }

   public Boolean CheckEmptyCart() {
        try{
            driver.findElement(By.xpath("//a[@onclick=\"document.fireEvent('openCart', {extend_event: [{name: 'eventLocation', value: 'Head'}]});\"]")).click();
            assertEquals("Корзина пуста",driver.findElement(By.id("drop-block")).getText());
            driver.findElement(By.xpath("//img[@alt='×']")).click();

        } catch (NumberFormatException e) {

        }

        return true;
    }

    public void CheckTotalSum(int SumAddProduct) throws IOException {
        String TotalSumCart;

        driver.findElement(By.xpath("//a[@onclick=\"document.fireEvent('openCart', {extend_event: [{name: 'eventLocation', value: 'Head'}]});\"]")).click();
        saveDataInFile(FileName,"Click button Cart");
        TotalSumCart = driver.findElement(By.xpath("//div[@id='cart_payment_info']/div/span[2]")).getText();
        saveDataInFile(FileName,"Find total summa cart");
        TotalSumCart = TotalSumCart.replaceAll("грн","");
        TotalSumCart = TotalSumCart.replaceAll(" ","");
        saveDataInFile(FileName,"Total summa by cart = "+TotalSumCart);
        //System.out.println(TotalSumCart);
        try {
            assertEquals(SumAddProduct, Integer.parseInt(TotalSumCart));
            saveDataInFile(FileName,"Summa Add product and summa Total summa Cart equals");
        } catch (NoSuchElementException e) {
            System.out.println("Summa dont equals");
        }
      //  File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
       // FileUtils.copyFile(scrFile, new File("D:/screenshot.png"));
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        //driver = new FirefoxDriver();
        //System.setProperty("webdriver.chrome.driver", " path of chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "D:\\Testing\\TestSeleniumRozetka\\src\\test\\java\\ua\\rozetka\\webdriver\\ChromeDriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        baseUrl = "https://rozetka.com.ua/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    @Test
    public void AddPopularProduct() throws Exception {
        saveDataInFile(FileName,"Test case № 1 Add Product start");
        driver.get("https://rozetka.com.ua/"); //go to the site https://rozetka.com.ua/
        Thread.sleep(5000);
        Name1 = driver.findElement(By.xpath("//a[@onclick=\"document.fireEvent('goodsTitleClick',{extend_event:[{name:'goods_id',value:20851979},{name:'eventLocation',value:'популярные'}]});\"]")).getText();
        saveDataInFile(FileName,"Select Name Product Name1 = "+Name1);
        //System.out.println("Name1= "+Name1);
        Price1 = driver.findElement(By.xpath("//section[@id='popular_goods_2']/div/div/div/div/div[4]/div/div")).getText(); //get text price by element
        saveDataInFile(FileName,"Select Price Product Price1 = "+Price1);
        //System.out.println("Price1= "+Price1);
        Thread.sleep(5000);
        driver.findElement(By.name("topurchases")).click();
        saveDataInFile(FileName,"Click button Buy");
        Thread.sleep(10000);
        Name2 = driver.findElement(By.xpath("//a[@onclick=\"document.fireEvent('goodsTitleClick',{extend_event:[{name:'goods_id',value:20851979},{name:'eventLocation',value:'популярные'}]});\"]")).getText();
        saveDataInFile(FileName,"Select name product Name2 = "+Name2);
        //System.out.println("Name2= "+Name2);
        Name1 = Name1.replaceAll(" ","");
        Name2 = Name2.replaceAll(" ","");
        //System.out.println("Name1= "+Name1);
        //System.out.println("Name2= "+Name2);
        saveDataInFile(FileName,"Name1 = "+Name1+" Name2 = "+Name2);
        try {
            Assert.assertEquals(Name1, Name2);
            saveDataInFile(FileName,"Name1 and Name2 equals");
        } catch (Exception e){
            e.printStackTrace();
        }
        //System.out.println("Name1 and Name2 equals");
        Price2 = driver.findElement(By.xpath("//div[@id='cart-popup']/div[2]/div/div[2]/div[2]/div[2]/div[2]/div[2]/span")).getText();
        //System.out.println("Price2= "+Price2);
        Price1 = Price1.replaceAll("грн","");
        Price2 = Price2.replaceAll("грн","");
        Price1 = Price1.replaceAll(" ","");
        Price2 = Price2.replaceAll(" ","");
        length = Price1.length();
        Price1 = removeCharStr(Price1,length-1);
        //System.out.println("Price1= "+Price1);
        //System.out.println("Price2= "+Price2);
        saveDataInFile(FileName,"Price1 = "+Price1+" Price2 = "+Price2);
        try {
            Assert.assertEquals(Price1, Price2);
            saveDataInFile(FileName,"Price1 and Price2 equals");
        } catch (Exception e){
            e.printStackTrace();
        }
        //System.out.println("Price1 and Price2 equals");
        Price3 = driver.findElement(By.xpath("//div[@id='cart_payment_info']/div/span[2]")).getText();
        //System.out.println("Price3= "+Price3);
        Price3 = Price3.replaceAll(" ","");
        Price3 = Price3.replaceAll("грн","");
        saveDataInFile(FileName,"Price3 = "+Price3);
        //System.out.println("Price3= "+Price3);
        try {
            Assert.assertEquals(Price2, Price3);
            saveDataInFile(FileName,"Price2 and Price3 equals");
        } catch (Exception e){
            e.printStackTrace();
        }
        //System.out.println("Price2 and Price3 equals");
        try {
            Assert.assertEquals(Price1, Price3);
            saveDataInFile(FileName,"Price1 and Price3 equals");
        } catch (Exception e){
            e.printStackTrace();
        }
        //System.out.println("Price1 and Price3 equals");
        try {
            driver.findElement(By.xpath("//img[@alt='✓']")).isEnabled();
            saveDataInFile(FileName,"Element delete select product exists and is active");
        } catch (Exception e){
            e.printStackTrace();
        }

        driver.findElement(By.xpath("//img[@alt='✓']")).click();
        saveDataInFile(FileName,"Click button delete select product");
        //System.out.println("Click button delete select product");
        Thread.sleep(5000);
        try {
            driver.findElement(By.xpath("//div[@id='cart-popup']/div[2]/div/div[2]/div/div/div/div[2]/a")).isEnabled();
            saveDataInFile(FileName,"Button delete dont save exists and is active");
        } catch (Exception e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//div[@id='cart-popup']/div[2]/div/div[2]/div/div/div/div[2]/a")).click();
        saveDataInFile(FileName,"Click button Delete dont save");
        //System.out.println("Click button Delete dont save");
        Thread.sleep(5000);
        EmptyCart = driver.findElement(By.id("drop-block")).getText();
        saveDataInFile(FileName,"Cart = "+EmptyCart);
        //System.out.println("Cart="+EmptyCart);
        EmptyCart = EmptyCart.replaceAll(" ","");
        try {
            Assert.assertEquals("Корзинапуста", EmptyCart);
            saveDataInFile(FileName,"Empty Cart");
        } catch (Exception e){
            e.printStackTrace();
        }
        //System.out.println("Empty Cart");
        driver.findElement(By.xpath("//img[@alt='×']")).click(); //close cart
        saveDataInFile(FileName,"Close Cart");
        //System.out.println("Close Cart");
        //time.sleep(3)
    }

    @Test
    public void AddProductByCategorie() throws Exception {
        saveDataInFile(FileName,"Test case № 2 Add Product by categorie start");
        driver.get("https://rozetka.com.ua/"); //go to the site https://rozetka.com.ua/
        Thread.sleep(5000);
        driver.findElement(By.linkText("Ноутбуки и компьютеры")).click();
        saveDataInFile(FileName,"Click link Laptops and Computers");
        driver.findElement(By.linkText("Asus")).click();
        saveDataInFile(FileName,"Click link Asus");
        EntityGoods = new ArrayList<EntityGoods>();
        AddGoodsInCart("17982198","//div[@name='price']","","",0,"7");
        AddGoodsInCart("17998158","(//div[@name='price'])[3]","[2]","[2]",1,"7");
        int size = EntityGoods.size();
        for (int i=0; i<size; i++){
            summa = summa + Integer.parseInt(EntityGoods.get(i).getPriceGoods());
        }
        saveDataInFile(FileName,"Total by select product = "+summa);
        //System.out.println(summa);
        CheckTotalSum(summa);
    }

    @Test
    public void AddProductByCategorieFridge() throws Exception {
        saveDataInFile(FileName,"Test case № 3 Add Product by categorie Fridge start");
        driver.get("https://rozetka.com.ua/"); //go to the site https://rozetka.com.ua/
        Thread.sleep(5000);
        driver.findElement(By.linkText("Бытовая техника")).click();
        saveDataInFile(FileName,"Click section menu Appliances");
        driver.findElement(By.xpath("(//a[contains(text(),'Холодильники')])[2]")).click();
        saveDataInFile(FileName,"Click section menu Fridge");
        driver.findElement(By.xpath("(//a[contains(text(),'Однокамерные')])[2]")).click();
        saveDataInFile(FileName,"Click section menu single-chamber");
        EntityGoods = new ArrayList<EntityGoods>();
        AddGoodsInCart("15781895","(//div[@name='price'])[2]","","",0,"5");
        AddGoodsInCart("14931374","//div[@name='price']","","",1,"5");
        int size = EntityGoods.size();
        for (int i=0; i<size; i++){
            summa = summa + Integer.parseInt(EntityGoods.get(i).getPriceGoods());
        }
        saveDataInFile(FileName,"Actual summa = 38000");
        //System.out.println(38000);
        CheckTotalSum(38000);
    }


    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void takeScreenshot(ITestResult result) {
        if (! result.isSuccess()) {       File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                String scrFilePath = "D:/Testing/TestSeleniumRozetka/src/test/java/ua/rozetka/webdriver/ScreenShoots/screenshot.png";
                FileUtils.copyFile(scrFile, new File(scrFilePath));
                log(Level.SEVERE, "<a href='file:///" + scrFilePath + "'>"+ result.getMethod().getMethodName()+"</a>");
            } catch (IOException ex) {
               // log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}

