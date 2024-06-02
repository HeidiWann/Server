package model.WebScraper;

import model.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Collections;
import java.util.Comparator;

/**
 * This class is responsible for scraping Hemköp webpages
 *
 * @author Anton Jansson
 */
public class HemköpWebScraper {
    /**
     * This method scrapes all products names and comparison prices from a given webpage.
     *
     * @param url The link to the webpage as a String
     * @author Anton Jansson
     */
    public ArrayList<Ingredient> scrapeHemköp(ChromeDriver driver, String url) {
        driver.get(url);

        //Handels the cookie-banner
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement acceptCookiesButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("onetrust-accept-btn-handler")));
            acceptCookiesButton.click();
        } catch (Exception e) {
            System.out.println("There was no cookie banner found or unable to click");
        }

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        // Hämta antal produkter
        List<WebElement> allProducts = driver.findElements(By.xpath("//div[@data-testid='product-container']"));

        for (WebElement productBox : allProducts) {
            try {
                WebElement productNameElement = productBox.findElement(By.xpath(".//p[@data-testid='product-title']"));
                WebElement productPriceElement = productBox.findElement(By.xpath(".//p[contains(@data-testid,'compare-price')]"));

                String productName = productNameElement.getText();
                String productPriceString = productPriceElement.getText()
                        .replace(",", ".")
                        .replace("Jmf pris ", "").split(" ")[0];
                double productPrice = Double.parseDouble(productPriceString);

                //Kod för att kategorisera,  this is not fully implemented
                /*
                ProductCategory category = ProductCategorizer.categorizeProduct(productName);
                ProductSubCategory subCategory = ProductCategorizer.getSubCategory(productName);
                System.out.println(productName + "  " + productPrice+" "+category+" "+subCategory);
                ingredients.add(new Ingredient(productName, productPrice, Store.Hemköp, category, subCategory));*/

                //Kod utan kategorisering
                Ingredient ingredient = new Ingredient(productName, productPrice, Store.ICA);
                ingredients.add(ingredient);
            } catch (Exception e) {
                System.out.println("Product #" + " gives an error. Link: " + url);
                e.printStackTrace();
            }
        }

        //driver.quit();

        Collections.sort(ingredients, Comparator.comparing(Ingredient::getIngredientName)
                .thenComparing(Ingredient::getPrice)
                .thenComparing(Ingredient::getStore));

        return ingredients;
    }

}