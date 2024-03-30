package controller;
import model.Ingredient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;

public class ScrapinController {
    private Ingredient ingredientPrice;
    public List<Ingredient> scrapeICAForPrice(String url) {
        List<Ingredient> ingredientPrices = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements priceElements = doc.select("");
            for (Element priceElement : priceElements) {
                String ingredientName = priceElement.select("").text();
                String price = priceElement.select("").text();
                ingredientPrices.add(new Ingredient(ingredientName, Double.parseDouble(price)));
            }

        }     catch (Exception e) {
            e.printStackTrace();
        }
        return ingredientPrices;
    }
    public List<Ingredient> scrapeICAForIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        String url = "https://handlaprivatkund.ica.se/stores/1003937/categories";
        ingredients.addAll(scrapeICAForPrice(url +"/mejeri-ost/7719dd17-9048-4055-ac32-56b5533a4ca7"));
        return ingredients;
    }
}
