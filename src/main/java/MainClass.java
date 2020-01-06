import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MainClass {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\DanFo\\IdeaProjects\\Imgsrc_Downvote\\chromedriver.exe");
        System.setProperty("chrome.verbose", "true"); //headless  Chrome
        WebDriver driver = new ChromeDriver();
        Login login = new Login(driver);
        Registration registration = new Registration(driver);

        /*driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //неявное ожидание элементов до их появления*/
        driver.manage().window().maximize(); //управление размером окна, максимальное
        String link = "https://imgsrc.ru/main/search.php?nopass=on&cat=77&page=1";

        registration.register();
        login.clickLogin();
        login.signInWithCreds(registration.getLogin(),registration.getPassword());
        login.createNewAlbum();

        driver.get(link);
        String mainTab = driver.getWindowHandle(); //запомнить имя открытой вкладки
        /*try {
            Thread.sleep(5000); //forced timeout to wait for the next object element to load
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //driver.findElement(By.xpath("//span[@class='labs-iframe-close-button']")).click(); //закрыть окно рекламы
        //JavascriptExecutor jse = (JavascriptExecutor)driver; // выполнять JavaScript с помощью переменной
        //driver.findElements(By.xpath("//table[@class='tdd']/tbody/tr"));

        /*WebDriverWait wait = (new WebDriverWait(driver, 5)); //явное ожидание элементов до их появления, которое используется 1 раз
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='tdd']")));

        try {
            Thread.sleep(10000); //forced timeout to wait for the next object element to load
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        for (int i = 2; i < 102; i++) { //[2..102]

            //driver.get(link);
            driver.findElement(By.xpath("//table[@class='tdd']/tbody/tr["+i+"]/td/a[1]")).click(); //начиная с tr[2]

            for (String tab : driver.getWindowHandles()) { //переключиться на свежеоткрытую вкладку
                driver.switchTo().window(tab);
            }

            if (driver.findElements(By.xpath("//input[@value='Continue to album']")).size() > 0)
                driver.findElement(By.xpath("//input[@value='Continue to album']")).click();
            else ;

            while (driver.findElements(By.xpath("//a[text()='\uD83D\uDC4E']")).size() > 0) {
                WebDriverWait wait = (new WebDriverWait(driver, 5)); //явное ожидание элементов до их появления, которое используется 1 раз
                driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.SECONDS);
                WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='\uD83D\uDC4E']")));
                try {
                    driver.findElement(By.xpath("//a[text()='\uD83D\uDC4E']")).click(); //driver.findElement(By.xpath("//a[@href='#voter']")).click();
                    driver.findElement(By.xpath("//img[@class='big']")).click();
                } catch (TimeoutException ignore) {
                }

            }
            /*for (int i = 1; i < 360; i++) {
            //jse.executeScript("window.scrollBy(0, 500)",""); //прокрутить страницу вниз с помощью JS
            driver.findElement(By.xpath("//a[text()='\uD83D\uDC4E']")).click(); //driver.findElement(By.xpath("//a[@href='#voter']")).click();
            driver.findElement(By.xpath("//img[@class='big']")).click();
            }*/
            driver.close();
            driver.switchTo().window(mainTab);
        }
        driver.quit();
    }
}
