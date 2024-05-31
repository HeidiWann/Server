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
                ProductCategory category = ProductCategorizer.categorizeProduct(productName);
                ProductSubCategory subCategory = ProductCategorizer.getSubCategory(productName);

                System.out.println(productName + "  " + productPrice);

                ingredients.add(new Ingredient(productName, productPrice, Store.Hemköp, category, subCategory));
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

    /*
    public ArrayList<Ingredient> scrapeHemköp(String url) {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000)); //Allows some time to pass so the page can load
        driver.get(url);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //todo 15 kan ersättas med nån formel för antal produkter alt. fler länkar som har färre produkter /länk
        for (int i = 0; i < 15; i++) {
            js.executeScript("window.scrollBy(0,100000)");
            try {
                Thread.sleep(200);
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
        }
        driver.manage().window().minimize();
         ArrayList<Ingredient> webpageProductList = new ArrayList<>();

        try {
            //Fetch the total amount of products on the page
            WebElement amountOfProductsWebElement = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div/div/div/div[1]/div[2]/div/p"));
            Pattern amountOfProductsPattern = Pattern.compile("\\b\\d+");
            Matcher amountOfProductsMatcher = amountOfProductsPattern.matcher(amountOfProductsWebElement.getText());
            int amountOfProducts = 0;
            while (amountOfProductsMatcher.find()) {
                amountOfProducts = Integer.parseInt(amountOfProductsMatcher.group());
            }
            //TODo allternativt ha en beräkning av st-pris och vikt
            for (int i = 1; i <= amountOfProducts; i++) {
                //Fetches a products name from the website
                WebElement siteProductNameWebElement = driver.findElement(By.cssSelector("#__next > div.sc-71235c6c-1.eYlxta > div > div > div > div:nth-child(1) > div.sc-ecf11518-0.bInFMS > div:nth-child(" + i + ") > div > div.sc-6700b369-0.bpPxvh > div.sc-6700b369-1.gDAKuW > p > a"));
                try {
                    //Fetches a products price per kilogram from the website
                    WebElement siteProductPriceWebElement = driver.findElement(By.cssSelector("#__next > div.sc-71235c6c-1.eYlxta > div > div > div > div:nth-child(1) > div.sc-ecf11518-0.bInFMS > div:nth-child(" + i + ") > div > div.sc-6700b369-0.bpPxvh > div.sc-6700b369-1.gDAKuW > div.sc-6700b369-8.eqEgUR > div > p"));
                    Pattern pricePattern = Pattern.compile("\\b\\d+,\\d+\\b");
                    Matcher priceMatcher = pricePattern.matcher(siteProductPriceWebElement.getText());
                    String productPriceString = "";
                    while (priceMatcher.find()) {
                        productPriceString = priceMatcher.group();
                    }
                    productPriceString = productPriceString.replace(",", ".");
                    String productName = siteProductNameWebElement.getText();
                    double productPrice = Double.parseDouble(productPriceString);
                    Ingredient ingredient = new Ingredient(productName, productPrice, Store.Hemköp);
                    webpageProductList.add(ingredient);
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    //todo skapar kanske problem med att throw @jansson
                    throw new RuntimeException(nfe);
                } catch (Exception e) {
                    System.out.println("product nbr: "+i + " gives an error, it might not have a kr/kg price  Link: "+url);

                }
            }
        } catch (Exception e) {
            driver.quit();
        } finally {
            driver.quit();
        }
        return webpageProductList;
    }

     */
}
