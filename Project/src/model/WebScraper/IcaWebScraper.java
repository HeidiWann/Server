package model.WebScraper;

import model.*;
import org.openqa.selenium.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class is responsible for scraping data from the Ica website
 *
 * @author Heidi Wännman
 * @author Anton Jansson
 */
public class IcaWebScraper {
    /**
     * This method is used to scrape a given webpage for the name and the price of the products.
     *
     * @param url The URL of the webpage to scrape.
     * @author Heidi Wännman
     * @author Anton Jansson
     */
    public List<Ingredient> scrapeICAForNameAndPrice(String url, ChromeDriver driver) {
        List<Ingredient> ingredients = new ArrayList<>();
        try {
            driver.get(url);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            // Hantera cookie-banner
            try {
                WebElement acceptCookiesButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("onetrust-accept-btn-handler")));
                acceptCookiesButton.click();
            } catch (TimeoutException e) {
                System.out.println("No cookie banner found or unable to click, continuing...");
            }

            List<WebElement> allProductsBoxes = driver.findElements(By.xpath("//div[starts-with(@data-test,'fop-wrapper')]"));
            for (WebElement productBox : allProductsBoxes) {
                WebElement pricePer = productBox.findElement(By.xpath(".//span[contains(@class,'fop__PricePerText')]"));
                String priceString = pricePer.getText().replace("(", "").replace("Erbj jmf", "").replace(",", ".").replace("Ord jmf ", "").split(" ")[0];
                try {
                    Double.parseDouble(priceString);
                } catch (NumberFormatException nfe) {
                    priceString = pricePer.getText().replace("Erbj jmf ", "").replace("(", "").replace(",", ".").replace("Ord jmf ", "").split(" ")[1];
                }
                double price = Double.parseDouble(priceString);

                WebElement title = productBox.findElement(By.xpath(
                        ".//a[@data-test='fop-product-link']"));
                //Kod för att kategorisera, this is not fully implemented
                /*
                ProductCategory category = ProductCategorizer.categorizeProduct(title.getText());
                ProductSubCategory subCategory = ProductCategorizer.getSubCategory(title.getText());
                Ingredient ingredient = new Ingredient(title.getText(), price, Store.ICA, category, subCategory);
                ingredients.add(ingredient);
                System.out.println(price + " " + title.getText() + " " + category + " " + subCategory);
                 */

                //Kod utan kategorisering
                Ingredient ingredient=new Ingredient(title.getText(), price,Store.ICA);
                ingredients.add(ingredient);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Sortera ingredienser
        Collections.sort(ingredients, Comparator.comparing(Ingredient::getIngredientName).thenComparing(Ingredient::getPrice).thenComparing(Ingredient::getStore));
        return ingredients;
    }
}
