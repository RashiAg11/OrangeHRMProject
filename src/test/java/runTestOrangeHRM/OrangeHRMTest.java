package runTestOrangeHRM;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import orangeHRMPageClasses.*;
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class OrangeHRMTest {
    WebDriver driver;
    String baseUrl;
    LoginPage loginPage;
    AdminPage adminPage;
    PIMPage pimPage;
    ScreenShot screenShot;
    AssertionCheck ac;
    SoftAssert sa = new SoftAssert();
    String ExpectedValue = "(1) Record Found";
    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setUpReporting() {
        // Set up the Extent Reporting infrastructure
        extent = new ExtentReports();
        extent.setSystemInfo("Project Name", "XYZ Automation");
        extent.setSystemInfo("Organisation", "XYZ Pvt. Ltd.");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("ExtentReports/ExtentReport.html");
        extent.attachReporter(sparkReporter);

    }


    @BeforeSuite(enabled = true)
    public void setup() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
        loginPage = new LoginPage(driver);
        adminPage = new AdminPage(driver);
        pimPage = new PIMPage(driver);
        screenShot = new ScreenShot(driver);
        ac = new AssertionCheck(driver);
        driver.manage().window().maximize();
        driver.get(baseUrl);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
    }

    @Test(priority = 1)
    public void login() throws Exception {
        test = extent.createTest("Verify the Login Page");
        loginPage.inputUserName();
        test.log(Status.INFO, "Login Page give Username");
        loginPage.inputPassword();
        test.log(Status.INFO, "Login page give Password");
        loginPage.clickLoginButton();
        test.log(Status.INFO, "Login page click submit button ");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
        WebElement dashBoardHeader = driver.findElement(By.xpath("//h6[contains(@class,'oxd-text')]"));
        Assert.assertTrue(dashBoardHeader.isDisplayed());
        System.out.println("Login Passed");
    }

    @Test(priority = 3)
    public void admin() throws Exception {
        test = extent.createTest("Verify the Admin Page");
        adminPage.adminClick();
        test.log(Status.INFO, "Admin clicked");
        adminPage.enterUserName();
        test.log(Status.INFO, "User Name Entered");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
        adminPage.clickUserRoll();
        adminPage.selectUserRole();
        test.log(Status.INFO, "UserRoll Selected");
        adminPage.sendEmployeeName();
        Thread.sleep(2000);
        adminPage.selectEmploymentName();
        test.log(Status.INFO, "Employee Name Selected");
        adminPage.clickStatus();
        adminPage.selectStatus();
        adminPage.clickSearch();
        test.log(Status.INFO, "Admin page searched");
        screenShot.screenShotPage();
        String value = ac.assertCheck();
        sa.assertEquals(value, ExpectedValue, "Record Not Found");
        Thread.sleep(3000);
        System.out.println("Admin Test passed");
        sa.assertAll();


    }

    @Test(priority = 2)
    public void pim() throws Exception {
        test = extent.createTest("Verify the PIN Page");
        pimPage.clickPIM();
        test.log(Status.INFO, "PIN page clicked");
        pimPage.sendEmployeeName();
        Thread.sleep(2000);
        pimPage.selectEmployeeName();
        test.log(Status.INFO, "PIN page Employee Name Selected");
        pimPage.employId();
        test.log(Status.INFO, "PIN page Employee ID");
        pimPage.clickEmploymentStatus();
        pimPage.EmploymentStatus();
        test.log(Status.INFO, "PIN page Employee Status");
        pimPage.clickInclude();
        pimPage.selectInclude();
        pimPage.supervisorName();
        pimPage.selectSupervisorName();
        test.log(Status.INFO, "PIN page Supervisor Name Selected");
        Thread.sleep(3000);
        pimPage.clickJobTitle();
        pimPage.selectJobTitle();
        test.log(Status.INFO, "PIN page Job Tittle Selected");
        pimPage.clickSubUnit();
        pimPage.selectSubUnit();
        pimPage.clickSearch();
        test.log(Status.INFO, "PIN page searched");
        Thread.sleep(3000);
        WebElement elementToAssert = driver.findElement(By.xpath("//div[contains(@class,'orangehrm-horizontal-padding orangehrm-vertical-padding')]"));
        Assert.assertTrue(elementToAssert.isDisplayed());
        System.out.println("PIM Test Passed");

    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        extent.flush(); // Save the report
    }
}


