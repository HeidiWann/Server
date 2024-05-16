package controller;

import model.Ingredient;
import model.WebScraper.HemköpWebScraper;
import model.WebScraper.IcaWebScraper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        //TODo skapa en tråd för varje butik?
        ArrayList<Ingredient> allScrapedProducts = new ArrayList<>();
        allScrapedProducts.addAll(scrapeIca());
        allScrapedProducts.addAll(scrapeHemköp());
        return allScrapedProducts;
    }

    private List<Ingredient> scrapeHemköp() {
        String fileName = "Project\\resources\\Hemköp_Links.txt"; //Where the file is stored
        ArrayList<String> hemköpLinks = new ArrayList<>();
        ArrayList<Ingredient> productsScraped = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String linkFromFile;
            while ((linkFromFile = bufferedReader.readLine()) != null) {
                hemköpLinks.add(linkFromFile);
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            System.out.println("File:" + fileName + " not found");
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        //Goes through every link in the file and fetches ingredients names and prices (with 100 milli sec delay)
        for (String link : hemköpLinks) {
            ArrayList<Ingredient> productsFromLink = hemköpWebScraper.scrapeHemköp(link);
            //TOdo kan kanske ersättas med addAll() @jansson
            for (Ingredient ingredient : productsFromLink) {
                System.out.println(ingredient);
                productsScraped.add(ingredient);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        return productsScraped;
    }

    private List<Ingredient> scrapeIca() {
        String fileName = "Project\\resources\\Ica_Links.txt"; //Where the file is stored
        ArrayList<String> icaLinks = new ArrayList<>();
        ArrayList<Ingredient> productsScraped = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String linkFromFile;
            while ((linkFromFile = bufferedReader.readLine()) != null) {
                icaLinks.add(linkFromFile);
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            System.out.println("File:" + fileName + " not found");
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }

        //Goes through every link in the file and fetches ingredients names and prices (with 100 milli sec delay)
        for (String link : icaLinks) {
            ArrayList productsFromLink = icaWebScraping.scrapeICAForNameAndPrice(link);
            for (Object ingredient : productsFromLink) {
                System.out.println(ingredient);
                productsScraped.add((Ingredient) ingredient);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        return productsScraped;
    }


}

