package controller;


import model.DatabaseConnection;
import model.WebScraping;
/**
 * Main class for the server. It starts the server.
 * @author Anton Jansson
 * @author Heidi Wännman
 */
public class Main {

    public static void main(String[] args) {
        //DatabaseConnection dbController = new DatabaseConnection();
        new ServerController();
        WebScraping webScraping = new WebScraping();
        webScraping.scrapeICAForPrice("https://handlaprivatkund.ica.se/stores/1003937/categories/mejeri-ost/7719dd17-9048-4055-ac32-56b5533a4ca7");
    }
}


