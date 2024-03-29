import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class MainClass {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Fostenko\\IdeaProjects\\Imgsrc_Downvote\\chromedriver.exe");
        System.setProperty("chrome.verbose", "true"); //headless  Chrome
        WebDriver driver = new ChromeDriver();
        Login login = new Login(driver);
        Registration registration = new Registration(driver);
        Gallery gallery = new Gallery(driver);

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //неявное ожидание элементов до их появления
        driver.manage().window().maximize(); //управление размером окна, максимальное
        String link = "https://imgsrc.ru/main/search.php?cat=77";

        driver.get(link);
        registration.register();
        login.clickLogin();
        login.signInWithCreds(registration.getLogin(),registration.getPassword());
        //login.signInWithCreds("d2218764","0cb86460");
        login.createNewAlbum();

        driver.get(link);
        String mainTab = driver.getWindowHandle(); //запомнить имя открытой вкладки
        gallery.switchLanguage();

        for (int i = 1; i < 50; i++) { //[2..102]
            gallery.clickAlbum(i);
            //gallery.checkAdds();
            gallery.downvote();
            driver.close();
            driver.switchTo().window(mainTab);
            System.out.println(i);
        }
        driver.quit();
    }
}
