import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import java.util.concurrent.TimeUnit;

public class Registration {
    WebDriver driver;
    String registrationPage = "https://imgsrc.ru/main/join.php";

    public Registration(WebDriver driver) {  //class constructor
        this.driver = driver;
    }

    //Elements on Registration page
    private By emailField = By.xpath("//input[@name='email']");
    private By loginField = By.xpath("//input[@name='login']");
    private By submitButton = By.xpath("//input[@type='submit']");   //'Login', 'Continue', 'I agree...' buttons
    //Elements on temporary email page
    private By emailReadField = By.xpath("//span[@id='email']");    //email on temporary email service page
    private By messageList = By.xpath("//td[text()='iMGSRC.RU registration']/..");
    private By passwordReadField = By.xpath("//body");    //password on temporary email service page

    private String email;
    private String login;
    private String password;

    //Methods
    public void clickRegister() {
        driver.get(registrationPage);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void register() {
        this.clickRegister();
        String mainTab = driver.getWindowHandle();  //remember the name of main browser tab
        ((JavascriptExecutor)driver).executeScript("window.open('https://www.minuteinbox.com','_blank');");    //driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t")
        switchToActiveTab();
        email = this.getEmail();
        login = this.getLoginFromEmail();
        driver.switchTo().window(mainTab);
        this.typeEmail(email);
        this.typeLogin(login);
        driver.findElement(submitButton).click();
        driver.findElement(submitButton).click();;
        switchToActiveTab();
        password = getPasswordFromEmail();
        switchToActiveTab();
    }

    public String getEmail() {
        return driver.findElement(emailReadField).getText();
    }

    public String getLoginFromEmail() {
        String[] parts = email.split("@");
        login = parts[0];
        System.out.println("Registration login: " + login);
        return login;
    }

    public String getPasswordFromEmail() {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); //waiting for element to appear
        driver.findElement(messageList).click();
        driver.manage().timeouts().implicitlyWait(1,TimeUnit.SECONDS); //waiting for element to appear
        String emailContent = driver.switchTo().frame("iframeMail").findElement(passwordReadField).getText();  //get text password from email
        int index = emailContent.indexOf("password: ",0);
        String password = emailContent.substring(index+10, index+18);
        System.out.println("Registration password: " + password);
        driver.close();
        return password;
    }

    public Registration typeEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
        return this;
    }

    public Registration typeLogin(String login) {
        driver.findElement(loginField).sendKeys(login);
        return this;
    }

    private void switchToActiveTab() {
        for (String tab : driver.getWindowHandles()) {  //switch to the active tab
            driver.switchTo().window(tab);
        }
    }

    public static void waitObjectLoad(int timeout) {
        try {
            Thread.sleep(timeout); //forced timeout to wait for the next object element to load
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
