package model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class WebScraping {

    private Ingredient ingredientPrice;
    public List<Ingredient> scrapeICAForPrice(String url) {
        List<Ingredient> ingredientPrices = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements priceElements = doc.select("[data-test*=fop-body]");
            System.out.println(priceElements.size());
            for (Element priceElement : priceElements) {
                String ingredientName = priceElement.select("a").text();
                System.out.println("Ingredient name: " + ingredientName);
                String price = priceElement.select("[data-test*=fop-price]").text();
                price = price.substring(0, price.indexOf(" "));
                price = price.replace(",", ".");
                price = price.replace("Ca", "");
                System.out.println("Price: " + price);
                ingredientPrices.add(new Ingredient(ingredientName, Double.parseDouble(price)));

            }

        }     catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(ingredientPrices.size());
        return ingredientPrices;
    }
    public List<Ingredient> scrapeICAForIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        String url = "https://handlaprivatkund.ica.se/stores/1003937/categories";
        ingredients.addAll(scrapeICAForPrice(url +"/mejeri-ost/7719dd17-9048-4055-ac32-56b5533a4ca7"));
        return ingredients;
    }

}
