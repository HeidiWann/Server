package controller;

import model.Ingredient;
import model.WebScraper.HemköpWebScraper;
import model.WebScraper.IcaWebScraper;

import org.openqa.selenium.chrome.ChromeDriver;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * This clas is responsible for managing the web scraping process
 *
 * @author Anton Jansson
 */
public class ScrapinController {
    private IcaWebScraper icaWebScraping;
    private HemköpWebScraper hemköpWebScraper;


    /**
     * Clas constructor
     *
     * @author Anton Jansson
     */
    public ScrapinController() {
        this.icaWebScraping = new IcaWebScraper();
        this.hemköpWebScraper = new HemköpWebScraper();
    }

    /**
     * Method used for starting the scraping process from all stores
     *
     * @author Anton Jansson
     */
    public ArrayList<Ingredient> scrapeAllStores() {
        ArrayList<Ingredient> allScrapedProducts = new ArrayList<>();
        ChromeDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        allScrapedProducts.addAll(scrapeHemköp(driver));
        allScrapedProducts.addAll(scrapeIca(driver));
        driver.quit();
        return allScrapedProducts;
    }

    private List<Ingredient> scrapeHemköp(ChromeDriver driver) {
        String fileName = "Project\\resources\\Hemköp_Links.txt"; //Where the file is stored
        List<String> hemköpLinks = readLinksFromFile(fileName);
        ArrayList<Ingredient> productsScraped = new ArrayList<>();

        //Goes through every link in the file and fetches ingredients names and prices (with 100 milli sec delay)
        for (String link : hemköpLinks) {
            ArrayList<Ingredient> productsFromLink = hemköpWebScraper.scrapeHemköp(driver, link);
            productsScraped.addAll(productsFromLink);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        return productsScraped;
    }

    private List<Ingredient> scrapeIca(ChromeDriver driver) {
        String fileName = "Project\\resources\\Ica_Links.txt"; //Where the file is stored
        List<String> icaLinks = readLinksFromFile(fileName);
        ArrayList<Ingredient> productsScraped = new ArrayList<>();
        //Goes through every link in the file and fetches ingredients names and prices (with 100 milli sec delay)
        for (String link : icaLinks) {
            List<Ingredient> productsFromLink = icaWebScraping.scrapeICAForNameAndPrice(link, driver);
            productsScraped.addAll(productsFromLink);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        return productsScraped;
    }


    private List<String> readLinksFromFile(String fileName) {
        List<String> links = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String linkFromFile;
            while ((linkFromFile = bufferedReader.readLine()) != null) {
                links.add(linkFromFile);
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            System.out.println("File:" + fileName + " not found");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading file: " + fileName);
        }
        return links;
    }
}
