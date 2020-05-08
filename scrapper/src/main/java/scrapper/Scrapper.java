package scrapper;

import java.util.List;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import villager.Villager;
import villager.VillagerDetail;

public class Scrapper {

    static final String CHROMEDRIVER_PROPERTY = "webdriver.chrome.driver";
    static final String BASE_URL = "https://animalcrossing.fandom.com/wiki/Villager_list_(New_Horizons)";
    static final int WAIT_VALUE = 10000;

    WebDriver driver;
    WebDriverWait wait;

    public Scrapper(String browserPath) {
        System.setProperty(CHROMEDRIVER_PROPERTY, browserPath);
        ChromeOptions options = new ChromeOptions();

        // make sure it doesn't load images. It slows the process
        options.addArguments("--blink-settings=imagesEnabled=false");

        // make sure it's headless
        options.addArguments("--headless");

        this.driver = new ChromeDriver(options);
        this.wait = new WebDriverWait(driver, WAIT_VALUE);
    }

    public List<Villager> scrapVillagerNames() {
        List<Villager> villagers = new ArrayList<Villager>();
        
        try {
            // Visit page
            this.driver.get(BASE_URL);

            // Find list of villager name
            WebElement article    = this.driver.findElement(By.id("WikiaArticle"));
            List<WebElement> rows = article.findElements(By.xpath("//table[2]/tbody/tr[2]//tbody/tr"));

            for (WebElement row : rows) {
                String name = getName(row.getText());
                String link = row.findElement(By.tagName("a")).getAttribute("href");    
                
                villagers.add(new Villager(name, link));
            }
        } finally {
            this.driver.quit();
        }

        return villagers;
    }

    public VillagerDetail scrapVillagerDetails(String name, String link) {
        VillagerDetail villagerDetail;

        try {
            // Visit page
            this.driver.get(link);
            WebElement infoBox = this.driver.findElement(By.tagName("aside"));
            
            // Find villager gender
            String gender = infoBox.findElement(By.xpath("//div[@data-source=\"Gender\"]/div[1]")).getText();

            // Find villager personality
            String personality = infoBox.findElement(By.xpath("//div[@data-source=\"Personality\"]/div[1]")).getText();

            // Find villager species
            String species = infoBox.findElement(By.xpath("//div[@data-source=\"Species\"]/div[1]")).getText();

            // Find villager birthday
            String birthday = infoBox.findElement(By.xpath("//div[@data-source=\"Birthday\"]/div[1]")).getText();

            // Find villager image source
            String imageSource = infoBox
                                    .findElement(By.tagName("figure"))
                                    .findElement(By.tagName("a"))
                                    .getAttribute("href");

            // Printout villager data
            System.out.println("Villager Data");
            System.out.println("Name: " + name);
            System.out.println("Gender: " + gender);
            System.out.println("Personality: " + personality);
            System.out.println("Species: " + species);
            System.out.println("Birthday: " + birthday);
            System.out.println("Image Source: " + imageSource);
            System.out.println("Character Link: " + link);

            // Create villager detail object
            villagerDetail = new VillagerDetail(name, gender, species, personality, birthday, imageSource, link);
        } finally {
            this.driver.quit();
        }

        return villagerDetail;
    }

    public String getName(String uncleanName) {
        return uncleanName.split(" ")[0];
    }
}