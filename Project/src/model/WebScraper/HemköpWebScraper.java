package model.WebScraper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HemköpWebScraper {
    private List<String> webpageProductList = new ArrayList<>();



    public void scrapeHemköp(String url) {
        try {
          /*  Document doc = Jsoup.connect(url).get();
            Elements script = doc.select("[id=\"__NEXT_DATA__\"]");
            String jsonData = script.first().toString().substring(script.first().toString().lastIndexOf("<script id=\"__NEXT_DATA__\" type=\"application/json\">") + 51);
            jsonData = jsonData.substring(0, jsonData.lastIndexOf("</script>"));
            JSONObject productJSONData = new JSONObject(jsonData);
            JSONArray productJSONArray = new JSONArray();
*/
            Document doc = Jsoup.connect(url).get();
            //Elements proNames=doc.getElementsByClass("sc-fbb8bf8b-2 kZfUIt");//30 st för namn
            Elements proNames=doc.getElementsByClass("sc-39e3fbc8-0 fCbxkY");//30 st
            Elements proPrices=doc.getElementsByClass("sc-480ead59-0 eKvrKl");

            System.out.println("Size is:" +proNames.size());
            System.out.println(proNames.get(0));




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
