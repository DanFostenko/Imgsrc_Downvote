import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class Gallery {
    WebDriver driver;

    public Gallery(WebDriver driver) {  //class constructor
        this.driver = driver;
    }

    //Elements on page
    private By continueToAlbumButton = By.xpath("//input[@value='Continue to album']");
    private By downvoteButton = By.xpath("//a[text()='\uD83D\uDC4E']");
    private By imageLink = By.xpath("//img[@class='big']");
    //private By submitButton = By.xpath("//input[@type='submit']");   //'Login', 'Continue', 'I agree...' buttons


    //Methods
    public void openGallery(String link) {
        driver.get(link);
        String mainTab = driver.getWindowHandle(); //запомнить имя открытой вкладки
    }

    public void clickAlbum(int i) {
        driver.findElement(By.xpath("//table[@class='tdd']/tbody/tr["+i+"]/td/a[1]")).click(); //начиная с tr[2]

        for (String tab : driver.getWindowHandles()) { //переключиться на свежеоткрытую вкладку
            driver.switchTo().window(tab);
        }

        if (driver.findElements(continueToAlbumButton).size() > 0)
            driver.findElement(continueToAlbumButton).click();
        else ;
    }

    public void downvote() {
        while (driver.findElements(downvoteButton).size() > 0) {
            WebDriverWait wait = (new WebDriverWait(driver, 5)); //явное ожидание элементов до их появления, которое используется 1 раз
            driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.SECONDS);
            WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(downvoteButton));
            try {
                driver.findElement(downvoteButton).click(); //driver.findElement(By.xpath("//a[@href='#voter']")).click();
                driver.findElement(imageLink).click();
            } catch (TimeoutException ignore) {

            }
        }
    }
}
