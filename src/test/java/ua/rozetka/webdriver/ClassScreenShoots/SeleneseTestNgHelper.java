package ua.rozetka.webdriver.ClassScreenShoots;

import java.lang.reflect.Method;

import com.thoughtworks.selenium.SeleneseTestBase;
import com.thoughtworks.selenium.Selenium;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@Listeners({ScreenshotListener.class})
public class SeleneseTestNgHelper extends SeleneseTestBase
{
    public static final String SELENIUM_ATTR = "selenium";

    private static Selenium staticSelenium;
    
    @BeforeTest
    @Override
    @Parameters({"selenium.url", "selenium.browser"})
    public void setUp(@Optional String url, @Optional String browserString) throws Exception {
        if (browserString == null) browserString = runtimeBrowserString();
        super.setUp(url, browserString);
        staticSelenium = selenium;
    };
    
    @BeforeClass
    @Parameters({"selenium.restartSession"})
    public void getSelenium(@Optional("false") boolean restartSession) {
        selenium = staticSelenium;
        if (restartSession) {
            selenium.stop();
            selenium.start();
        }
    }
    
    @BeforeMethod
    public void setTestContext(ITestContext context, Method method) {
        selenium.setContext(method.getDeclaringClass().getSimpleName() + "." + method.getName());
        context.setAttribute(SELENIUM_ATTR, selenium);
    }
    
    @AfterMethod
    @Override
    public void checkForVerificationErrors() {
        super.checkForVerificationErrors();
    }
    
    @AfterMethod(alwaysRun=true)
    public void selectDefaultWindow() {
        if (selenium != null) selenium.selectWindow("null");
    }
    
    @AfterTest(alwaysRun=true)
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
    //@Override static method of super class (which assumes JUnit conventions)
    public static void assertEquals(Object actual, Object expected) {
        SeleneseTestBase.assertEquals(expected, actual);
    }
    
    //@Override static method of super class (which assumes JUnit conventions)
    public static void assertEquals(String actual, String expected) {
        SeleneseTestBase.assertEquals(expected, actual);
    }
    
    //@Override static method of super class (which assumes JUnit conventions)
    public static void assertEquals(String actual, String[] expected) {
        SeleneseTestBase.assertEquals(expected, actual);
    }

    //@Override static method of super class (which assumes JUnit conventions)
    public static void assertEquals(String[] actual, String[] expected) {
        SeleneseTestBase.assertEquals(expected, actual);
    }
    
    //@Override static method of super class (which assumes JUnit conventions)
    public static boolean seleniumEquals(Object actual, Object expected) {
        return SeleneseTestBase.seleniumEquals(expected, actual);
    }
    
    //@Override static method of super class (which assumes JUnit conventions)
    public static boolean seleniumEquals(String actual, String expected) {
        return SeleneseTestBase.seleniumEquals(expected, actual);
    }

    @Override
    public void verifyEquals(Object actual, Object expected) {
        super.verifyEquals(expected, actual);
    }
    
    @Override
    public void verifyEquals(String[] actual, String[] expected) {
        super.verifyEquals(expected, actual);
    }
}
