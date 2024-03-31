package controller;
import model.Ingredient;
import model.WebScraping;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;

public class ScrapinController {
    private WebScraping webScraping;
    private ServerController serverController;

    public ScrapinController(){
        this.webScraping = new WebScraping();
        webScraping.scrapeICAForNameAndPrice("https://handlaprivatkund.ica.se/stores/1003937/categories/mejeri-ost/7719dd17-9048-4055-ac32-56b5533a4ca7");
    }
}

