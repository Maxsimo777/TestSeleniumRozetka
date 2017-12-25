package ua.rozetka.webdriver;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.*;
import ua.rozetka.webdriver.ConteinerOfMethods;

public class SearchElement extends ConteinerOfMethods {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private String findText;
    private int cEntry,pos,lenghtTemp;
    private String CountProduct,CountProductTemp;
    private ArrayList cartProduct;
    private int countEntryCart;
    private String FileName = "SearchElementTestLog";

    public static int countEntryChar(String text, char findchar)
    {
        int count = 0;
        for (int i=0; i < text.length(); i++)
        {
            if (text.charAt(i) == findchar)
            {
                count++;
            }
        }
        return count;
    }

    String deleteCharacters(String str, int from, int to) {
        return str.substring(0,from)+str.substring(to);
    }

    public static int countEntryWord (String text,String text1){
        int countEntry;
        countEntry = (text + "\0").split(text1).length - 1;
        return countEntry;
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:\\Testing\\TestSeleniumRozetka\\src\\test\\java\\ua\\rozetka\\webdriver\\ChromeDriver\\chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "https://www.katalon.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void SearchElement() throws Exception {
        saveDataInFile(FileName,"Test case 1 Search Element start");
        driver.get("https://rozetka.com.ua/");
        try {
            driver.findElement(By.name("text")).isEnabled();
            saveDataInFile(FileName,"Element Search exists and is active");
        } catch (Exception e){
            e.printStackTrace();
        }
        driver.findElement(By.name("text")).click();
        saveDataInFile(FileName,"Click element Search");
        driver.findElement(By.name("text")).clear();
        driver.findElement(By.name("text")).sendKeys("iPhone X");
        saveDataInFile(FileName,"Enter word iPhone X");
        try {
            driver.findElement(By.xpath("//button[@type='submit']")).isEnabled();
            saveDataInFile(FileName,"Element Button Search exists and is active");
        } catch (Exception e){
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        saveDataInFile(FileName,"Click Element Button Search");
        //driver.findElement(By.xpath("//div[@id='catalog_goods_block']/div/div/div/div/div")).click();
        findText = driver.findElement(By.xpath("//div[@name='goods_list']")).getText(); //Checking the occurrence of a name throughout the search list
       // saveDataInFile(FileName,"Text by all list product "+findText);
        //System.out.println(findText);
        //cEntry = (findText + "\0").split("iPhone X").length - 1;
        cEntry = countEntryWord(findText,"iPhone X");
        saveDataInFile(FileName,"Count entry word iPhone X = "+cEntry);
        //System.out.println(cEntry);
        //System.out.println((findText + "\0").split("iPhone X").length - 1);
        //CountProduct = driver.findElement(By.xpath("//div[@id='title_page']/div/div/div[3]/ul")).getText();
        CountProductTemp = driver.findElement(By.xpath("//div[@id='title_page']/div/div/div[3]/ul/li[4]/p")).getText();
        //System.out.println(CountProductTemp);
        CountProductTemp = CountProductTemp.replaceAll("Подобрано","");
        //System.out.println(CountProductTemp);
        pos = CountProductTemp.indexOf("товара");
        lenghtTemp = CountProductTemp.length();
        CountProduct = deleteCharacters(CountProductTemp,pos,lenghtTemp);
        //System.out.println(CountProduct);
        CountProduct = CountProduct.replace(" ","");
        saveDataInFile(FileName,"Count Product with word iPhone X = "+CountProduct);
        try {
            assertEquals(cEntry, Integer.parseInt(CountProduct));
            saveDataInFile(FileName,"Count entry equals");
        } catch (NumberFormatException e) {
            System.err.println("Value dont equals");
        }
        cartProduct = new ArrayList();
        cartProduct.add(driver.findElement(By.xpath("//div[@id='catalog_goods_block']/div/div/div/div/div")).getText());
        for (int i=1;i<Integer.parseInt(CountProduct);i++){
            cartProduct.add(driver.findElement(By.xpath("//div[@id='catalog_goods_block']/div/div["+String.valueOf(i+1)+"]/div/div/div")).getText());
        }
        saveDataInFile(FileName,"Count find element to list = "+cartProduct.size());
        //System.out.println(cartProduct.size());

        int j = 0;
        for (int i = 0; i<cartProduct.size();i++ ){
            countEntryCart = countEntryWord(cartProduct.get(i).toString(),"iPhone X");
            if (countEntryCart > 0){
                saveDataInFile(FileName,"Searh Element"+j+"Equals");
                //System.out.println("Searh Element Equals");
                j++;
            }
        }

        try {

            assertEquals(Integer.parseInt(CountProduct),j);
            saveDataInFile(FileName,"Element search is correct");
            //System.out.println("Element search is correct");

        } catch (NumberFormatException e) {
            System.err.println("Search dont rule. Element dont equals");
        }

    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
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



