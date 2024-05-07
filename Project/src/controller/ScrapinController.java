package controller;

import model.WebScraper.IcaWebScraper;
public class ScrapinController {
    private IcaWebScraper icaWebScraping;
    private ServerController serverController;

    public ScrapinController(){
        this.icaWebScraping = new IcaWebScraper();
        icaWebScraping.scrapeICAForNameAndPrice("https://handlaprivatkund.ica.se/stores/1003937/categories/mejeri-ost/7719dd17-9048-4055-ac32-56b5533a4ca7");
    }
}

