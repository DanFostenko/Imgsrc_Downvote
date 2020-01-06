import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Login {
    WebDriver driver;
    String loginPage = "https://imgsrc.ru/main/login.php?cnt=%2Fmembers%2F&lang=en";
    String logoutPage = "https://imgsrc.ru/main/logout.php";

    public Login(WebDriver driver) {  //class constructor
        this.driver = driver;
    }

    //Elements on page
    private By usernameField = By.xpath("//td/input[@name='login']");
    private By passwordField = By.xpath("//input[@name='pass']");
    private By submitButton = By.xpath("//input[@type='submit']");   //'Login', 'Continue', 'I agree...' buttons
    private By newAlbumNameField = By.xpath("//input[@name='name']");
    private By createAlbumButton = By.xpath("//input[@value='Create album']");
    private By logoutButton = By.xpath("//td/input[@type='submit']");

    //Methods
    public void clickLogin() {
        driver.get(loginPage);
    }

    public Login typeUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
        return this;
    }

    public Login typePassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    public Login signInWithCreds(String username, String password) {
        this.clickLogin();
        this.typeUsername(username);
        this.typePassword(password);
        driver.findElement(submitButton).click();
        return new Login(driver);
    }

    public Login createNewAlbum() {
        for (int i = 0; i < 1; i++) {
            WebDriverWait wait = (new WebDriverWait(driver, 5)); //явное ожидание элементов до их появления, которое используется 1 раз
            driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.SECONDS);
            WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(newAlbumNameField));
            try {
                driver.findElement(newAlbumNameField).sendKeys(""+ Calendar.getInstance().getTime());
                driver.findElement(createAlbumButton).click();
                driver.get(loginPage);
            } catch (TimeoutException ignore) {
            }
            /*driver.findElement(By.xpath("//input[@type='file']")).click();
            Robot r = null;
            try {
                r = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
            driver.switchTo().activeElement();
            r.keyPress(KeyEvent.VK_A);  r.keyRelease(KeyEvent.VK_PERIOD);
            r.keyPress(KeyEvent.VK_P);  r.keyPress(KeyEvent.VK_N);  r.keyPress(KeyEvent.VK_G);
            driver.findElement(By.xpath("//input[@value='Upload photos to your album']")).click();*/
        }
        return this;
    }

    public void clickLogOut() {
        driver.get(logoutPage);
    }
}
