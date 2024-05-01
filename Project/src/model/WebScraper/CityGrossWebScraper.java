package model.WebScraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CityGrossWebScraper {
    private List<String> webpageProductList = new ArrayList<>();


    public void scrapeCityGross(String url) {
        try {
         /*   Detta är kod baserad på vad Heidi skrev för ica scraping, dock flyger detta mig över huvet @Jansson

          Document doc = Jsoup.connect(url).get();
            Elements script = doc.select("[id=\"__NEXT_DATA__\"]");
            String jsonData = script.first().toString().substring(script.first().toString().lastIndexOf("<script id=\"__NEXT_DATA__\" type=\"application/json\">") + 51);
            jsonData = jsonData.substring(0, jsonData.lastIndexOf("</script>"));
            JSONObject productJSONData = new JSONObject(jsonData);
            JSONArray productJSONArray = new JSONArray();
*/
            Document doc = Jsoup.connect(url).get();
            Elements proNames=doc.getElementsByClass("b-container fluid");

            /*todo samma problem som resterande klasser att man inte kommer åt den diven vi behöver

             */


            // en länk för Element klassen https://jsoup.org/apidocs/org/jsoup/nodes/Element.html#method-summary



            System.out.println("\nSize is:" + proNames.size());

            for (Element name : proNames) {
                System.out.println(name+"\n ||------------------------------------------||");
            }
            System.out.println("All done");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
