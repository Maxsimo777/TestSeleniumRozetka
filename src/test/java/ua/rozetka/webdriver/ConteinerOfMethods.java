package ua.rozetka.webdriver;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.*;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class ConteinerOfMethods  {
    public static WebDriver driver;
    public static String filesFolder = "D://Testing//TestSeleniumRozetka//src//test//java//ua//rozetka//webdriver//LogFiles//";
   // protected static WebDriver driver;

    protected void clickOnLink(String idLinkText) {
        try {
            driver.findElement(By.linkText(idLinkText)).click();
        } catch (NoSuchElementException e) {
            System.out.println(String.valueOf(idLinkText) + " Element not found ");
            e.printStackTrace();
        }
    }
    protected void loginOnSite() {
        try {
            System.out.print(" Login on the site ");
            driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
        } catch (NoSuchElementException e) {
            System.out.println(" Element not found ");
            e.printStackTrace();
        }
    }
    protected void clickOn(String selectId) {
        try {
            driver.findElement(By.id(selectId)).click();

            saveDataInFile("params_for_tests.txt", " Clicked element - " + String.valueOf(selectId));
        } catch (NoSuchElementException e) {
            System.out.println(String.valueOf(selectId) + " Element for click not found ");
            e.printStackTrace();
        }
    }
    protected static void fillField (String idField, String valueField) {
        try {
            driver.findElement(By.id(idField)).clear();
            driver.findElement(By.id(idField)).sendKeys(valueField);
            saveDataInFile("params_for_tests.txt", " idField = " + String.valueOf(idField) + " valueField = " + String.valueOf(valueField));
        } catch (NoSuchElementException e) {
            System.out.println(String.valueOf(idField) + " Element not found ");
            e.printStackTrace();
        }
    }
    protected void clickOnXpath(String xpathId) {
        try {
            driver.findElement(By.xpath(xpathId)).click();
            saveDataInFile("params_for_tests.txt", " xpathId = " + String.valueOf(xpathId));
        } catch (NoSuchElementException e) {
            System.out.println (String.valueOf(xpathId) + " Element not found ");
            e.printStackTrace();
        }
    }

    protected void clickONId(String idelement) {
        try {
            driver.findElement(By.id(idelement)).click();
            saveDataInFile("params_for_tests.txt", " xpathId = " + String.valueOf(idelement));
        } catch (NoSuchElementException e) {
            System.out.println (String.valueOf(idelement) + " Element not found ");
            e.printStackTrace();
        }
    }

   // protected static boolean isElementPresent(By idElement) {
    //    List<WebElement> list = driver.findElements(idElement);
    //    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    //    if (list.size() == 0 ) {
    //        return true;
    //    } else return list.get(0).isDisplayed();
   // }

    public String generateRandomString(int lengthS){
        String allowedChars="abcdefghijklmnopqrstuvwxyz";
        return RandomStringUtils.random(lengthS, allowedChars);
    }
    public String generateDomainValue(int lengthD) {
        String allowedDomain="abcdefghijklmnopqrstuvwxyz" + "123456789";
        return RandomStringUtils.random(lengthD, allowedDomain);
    }
    public String generateRandomNumber(int lengthN){
        String allowedChars="123456789";
        return RandomStringUtils.random(lengthN, allowedChars);
    }
    protected void fillFieldWithoutClear (String idField, String valueField) {
        try {
            driver.findElement(By.id(idField)).sendKeys(valueField);
            System.out.println(" valueField = " + valueField);
            saveDataInFile("params_for_tests.txt", " Target value - " + String.valueOf(valueField));
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }
    protected String takeRandomValueFromFile(String fileName, int amountOfLines) {
        int idLine = (int) (Math.random()* amountOfLines);
        System.out.println(idLine);
        String stringId = "";
        try {
            FileReader file = new FileReader(filesFolder + fileName);
            BufferedReader buff = new BufferedReader(file); //Read file
            for (int lineNo = 1; lineNo < amountOfLines; lineNo++) {
                if (lineNo == idLine) {
                    stringId = buff.readLine(); //Save the string
                } else buff.readLine();
            }
            file.close();
            // buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringId;
    }

    protected void openPage (String address) {
        try {
            System.out.println(" open page " + address);
            driver.get(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void saveDataInFile(String fileName, String value) {
        try {
            PrintStream addTextInFile = new PrintStream(new FileOutputStream(filesFolder + fileName+".txt", true), true);
            addTextInFile.println(value);
            addTextInFile.close();
            System.out.println(value);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    protected void refreshPage() {
        //System.out.println(" Refresh Page method ");
        driver.navigate().refresh();
    }

    protected void pause(int timeInMillisecond) {
        try {
            System.out.println(" Pause page " + timeInMillisecond);
            Thread.sleep(timeInMillisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    protected void pressPgDnButton (String idOfElement) {
        try {
            System.out.print(" Down ");
            driver.findElement(By.xpath(idOfElement)).sendKeys(Keys.ARROW_DOWN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//Random
 /*   public String generateRandomString(int length){
        return RandomStringUtils.randomAlphabetic(length);
    }

    public String generateRandomNumber(int length){
        return RandomStringUtils.randomNumeric(length);
    }*/

    public String generateRandomAlphaNumeric(int length){
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public String generateStringWithAllobedSplChars(int length){
        String allowedChars="йцуккеенгншщзфіаволджлмяиь" +   //alphabets    abcdefghijklmnopqrstuvwxyz
                "1234567890";
        // fillField("FullCompanyName", allowdSplChrs);
        return RandomStringUtils.random(length, allowedChars);
    }



    public String generateEmail(int length) {
        String allowedChars="abcdefghijklmnopqrstuvwxyz" +   //alphabets
                "1234567890" +   //numbers
                "_-.";   //special characters
        String email="";
        String temp=RandomStringUtils.random(length,allowedChars);
        email=temp.substring(0,temp.length()-9)+"@test.org";
        return email;
    }

    public String generateUrl(int length) {
        String allowedChars="abcdefghijklmnopqrstuvwxyz" +   //alphabets
                "1234567890" +   //numbers
                "_-.";   //special characters
        String url="";
        String temp=RandomStringUtils.random(length,allowedChars);
        url=temp.substring(0,3)+"."+temp.substring(4,temp.length()-4)+"."+temp.substring(temp.length()-3);
        return url;
    }

    public void logTest(String string) {
        System.out.print(string);
    }

}

/*   protected static void clickOnTab(String linkText) {
        driver.findElement(By.linkText(linkText)).click();
    }
    protected static void openLoginPage() {
        driver.findElement(By.cssSelector("login")).click();
    }

    protected static void selectCountries (Integer numCountry) {
        driver.findElement(By.cssSelector("option[value=" + numCountry + "]")).click();
    }

    protected static void assertPresentText(String presentedText) {
        assertTrue(driver.findElement(By.cssSelector("//div[@id='page-container']/div/div/div/div[2]/form/legend/div/div[2]/p[2]")).getText().contains(presentedText));
    }
    protected static void pressEnter() {
        driver.findElement(null).sendKeys(Keys.ENTER);
    } */
