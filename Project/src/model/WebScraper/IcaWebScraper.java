package model.WebScraper;

import model.Ingredient;
import model.Store;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for scraping data from the Ica website
 *
 * @author Heidi Wännman
 * @author Anton Jansson
 */
public class IcaWebScraper {
    private List<Ingredient> scrapedProductsList = new ArrayList<>();


    /**
     * This method is used to scrape a given webpage for the name and the price of the products.
     *
     * @param url The URL of the webpage to scrape.
     * @author Heidi Wännman
     * @author Anton Jansson
     */
    public ArrayList scrapeICAForNameAndPrice(String url) {
        ArrayList<Ingredient> scrapedProducts = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).get();
            Elements priceElements = doc.select("[data-test=\"initial-state-script\"]");
            String jsonData = priceElements.first().toString().substring(priceElements.first().toString().lastIndexOf("window.__INITIAL_STATE__=") + 25);
            jsonData = jsonData.substring(0, jsonData.lastIndexOf("</script>"));
            JSONObject productJSONData = new JSONObject(jsonData);
            JSONArray productJSONArray = new JSONArray();

            for (int i = 0; i < productJSONData.getJSONObject("data").getJSONObject("products").getJSONObject("catalogue").getJSONObject("data").getJSONArray("productGroups").length(); i++) {
                JSONArray productGroups = new JSONArray();
                productGroups.put(productJSONData.getJSONObject("data").getJSONObject("products").getJSONObject("catalogue").getJSONObject("data").getJSONArray("productGroups").get(i));
                productJSONArray.put(productGroups.getJSONObject(0).getJSONArray("products"));
            }

            List<String> productList = new ArrayList<>();

            for (int i = 0; i < productJSONArray.length(); i++) {
                for (int j = 0; j < productJSONArray.getJSONArray(i).length(); j++) {
                    productList.add(productJSONArray.getJSONArray(i).get(j).toString());
                }
            }

            List<List<String>> productsSplit = splitArray(productList, 100);

            for (List<String> strings : productsSplit) {
                StringBuilder apiString = new StringBuilder();
                for (String string : strings) {
                    apiString.append(string).append(",");
                }
                scrapedProducts=sendGetRequestAndResolveProductPrices("https://handlaprivatkund.ica.se/stores/1003937/api/v5/products/decorate?productIds=" + apiString);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return scrapedProducts;

    }

    /**
     * This method asks the specified URL for the details and we process the extracted data
     *
     * @param productName productName The URL with the product id to get the price
     * @author Heidi Wännman
     * @author Anton Jansson
     */
    public ArrayList sendGetRequestAndResolveProductPrices(String productName) {
        ArrayList <Ingredient> ingredientsScraped=new ArrayList<>();
        try {
            URL url = new URL(productName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JSONObject jsonObject = new JSONObject(response.toString());
                JSONArray products = jsonObject.getJSONArray("products");

                for (int i = 0; i < products.length(); i++) {
                    JSONObject product = products.getJSONObject(i);
//                    webpageProductList.add(product.getString("name") + " costs: " + product.getJSONObject("price").getJSONObject("unit").getJSONObject("current").getString("amount"));
                    Ingredient scrapedIngredient=new Ingredient(product.getString("name"), Double.parseDouble(product.getJSONObject("price").getJSONObject("unit").getJSONObject("current").getString("amount")), Store.ICA);
                    ingredientsScraped.add(scrapedIngredient);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ingredientsScraped;
    }

    /**
     * This method is used to split a list into smaller lists with a given size
     *
     * @param arr  arr The list to split
     * @param size Is the maximum size of each list
     * @return A list of lists, each list containing the size that the original list(up to the size of elements)
     * @author Heidi Wännman
     */
    public List<List<String>> splitArray(List<String> arr, int size) {
        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i < arr.size(); i += size) {
            result.add(arr.subList(i, Math.min(i + size, arr.size())));
        }
        return result;
    }
}

