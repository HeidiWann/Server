package controller;

import model.Ingredient;
import model.WebScraper.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clas responsible for handling the web scraping
 *
 * @author Anton Jansson
 */
public class ScrapinController {
    private IcaWebScraper icaWebScraping;
    private CityGrossWebScraper cityGrossWebScraper;
    private CoopWebScraper coopWebScraper;
    private HemköpWebScraper hemköpWebScraper;
    private WillysWebScraper willysWebScraper;


    /**
     * Clas constructor
     *
     * @author Anton Jansson
     */
    public ScrapinController() {
        icaWebScraping = new IcaWebScraper();
        cityGrossWebScraper = new CityGrossWebScraper();
        coopWebScraper = new CoopWebScraper();
        hemköpWebScraper = new HemköpWebScraper();
        willysWebScraper = new WillysWebScraper();

    }


    /**
     * Method used for starting the scraping process from all stores
     *
     * @author Anton Jansson
     */
    public void scrapeAllStores() {

        //TODo skapa en tråd för varje butik?

        scrapeIca();
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



        //Goes through every link in file and fetches ingredients names and prices (with 1 sec delay)
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

