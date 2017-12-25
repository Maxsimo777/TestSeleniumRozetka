package ua.rozetka.webdriver.ClassScreenShoots;

import java.io.File;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener2;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.thoughtworks.selenium.Selenium;

public class ScreenshotListener implements IInvokedMethodListener2 {

    public ScreenshotListener() {}

	@Override
	public void beforeInvocation(IInvokedMethod mth, ITestResult result) {}

	@Override
	public void beforeInvocation(IInvokedMethod mth, ITestResult result, ITestContext context) {}

	@Override
	public void afterInvocation(IInvokedMethod mth, ITestResult result) {}

	@Override
	public void afterInvocation(IInvokedMethod mth, ITestResult result, ITestContext context) {
        if (!mth.isTestMethod()) return;
        if (result.isSuccess()) return;

        Reporter.setCurrentTestResult(result);

        File outputDirectory = new File(context.getOutputDirectory());
        Selenium selenium = (Selenium) context.getAttribute(SeleneseTestNgHelper.SELENIUM_ATTR);
        
        try {
            outputDirectory.mkdirs();
            File outFile = File.createTempFile("TEST-"+result.getName(), ".png", outputDirectory);
            outFile.delete();
            selenium.windowFocus();
            selenium.windowMaximize();
            selenium.captureScreenshot(outFile.getAbsolutePath());
            Reporter.log("<a href='" +
            		outFile.getName() +
            		"'>screenshot</a>");
        } catch (Exception e) {
            e.printStackTrace();
            Reporter.log("Couldn't create screenshot");
            Reporter.log(e.getMessage());
        }  
        
        Reporter.setCurrentTestResult(null);
	}

}
