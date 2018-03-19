package kz.technodom.autotest;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class Listener extends TestBase implements ITestListener {
    String filePath = "/tmp/screenshots/";

    public void takeScreenShot(WebDriver driver, String methodName) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Calendar calendar = Calendar.getInstance();
        try {
            final BufferedImage image = ImageIO.read(scrFile);
            Graphics g = image.getGraphics();
            g.setFont(g.getFont().deriveFont(30f));
            g.setColor(Color.BLACK);
            g.fillRect(80, 110, 1700, 100);
            g.setColor(Color.red);
            g.drawString("ERROR on PAGE ==> " + driver.getCurrentUrl(), 100, 140);
            g.dispose();
            ImageIO.write(image, "png", new File(filePath + methodName + "_"
                + calendar.getTime() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        String methodName = iTestResult.getName().toString().trim();
        takeScreenShot(driver, methodName);
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}
