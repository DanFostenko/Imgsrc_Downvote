import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

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
    private By passwordReadField = By.xpath("/html/body/text()[3]");    //password on temporary email service page

    //Methods
    public void clickRegister() {
        driver.get(registrationPage);
    }

    public void register() {
        this.clickRegister();
        String mainTab = driver.getWindowHandle();  //remember the name of main browser tab
        ((JavascriptExecutor)driver).executeScript("window.open('https://www.minuteinbox.com','_blank');");    //driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t")
        switchToActiveTab();
        String email = this.getEmail();
        String[] parts = email.split("@");
        String login = parts[0];
        System.out.println("Registration email: " + email);
        driver.switchTo().window(mainTab);
        switchToActiveTab();
        //driver.close();
        this.typeEmail(email);
        this.typeLogin(login);
        driver.findElement(submitButton);
        driver.findElement(submitButton);
        switchToActiveTab();
        getPassword();
        return;
    }

    public String getEmail() {
        return driver.findElement(emailReadField).getText();
    }

    public String getPassword() {
        driver.findElement(messageList).click();
        String password = driver.switchTo().frame("iframeMail").findElement(passwordReadField).getText();  //get text password from email
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
}
