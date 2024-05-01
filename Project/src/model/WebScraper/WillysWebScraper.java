package model.WebScraper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class WillysWebScraper {

    private List<String> webpageProductList = new ArrayList<>();


    public void scrapeWillys(String url) {
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
            //Elements proNames = doc.getElementsByClass("sc-504002c3-3 ePiOKs").first().getElementsByClass("sc-504002c3-1 drRqMi").first().getElementsByClass("sc-504002c3-2 bgZdnn").first().getElementsByClass("sc-73e54f8d-0 fEdLuP").first().getElementsByClass("sc-fd4a2085-0 dJCjFh").first().getElementsByClass("sc-fd4a2085-1 ilIqBT");
            Elements proNames = doc.getElementsByClass("sc-504002c3-3 ePiOKs").first().getElementsByClass("sc-504002c3-1 drRqMi").first().getElementsByClass("sc-504002c3-2 bgZdnn").first().getElementsByClass("sc-73e54f8d-0 fEdLuP").first().getElementsByClass("sc-fd4a2085-0 dJCjFh").first().getElementsByClass("sc-fd4a2085-1 ilIqBT").first().select("sc-fd4a2085-1 ilIqBT");//.first().getElementsByClass("sc-7906aaa8-0 jZaUcY");
            //Elements proNames = doc.getElementsByClass("sc-504002c3-3 ePiOKs").first().getAllElements();//.getElementsByClass("sc-504002c3-1 drRqMi").first().getElementsByClass("sc-504002c3-2 bgZdnn").first().getElementsByClass("sc-73e54f8d-0 fEdLuP").first().getElementsByClass("sc-fd4a2085-0 dJCjFh").first().getElementsByClass("sc-fd4a2085-1 ilIqBT").first().select("div");//.first().getElementsByClass("sc-7906aaa8-0 jZaUcY");

            //Elements proNames=doc.getAllElements();

            //TODO kommer inte vidare från "sc-fd4a2085-1 ilIqBT" <div>:en. inne i den <div>en ligger en "tom" div innan
            // "sc-7906aaa8-0 jZaUcY" <div>:en (och det är den diven/klassen där datan för namn och pris ligger
            // Borde inte vara något problem att hämta "mer" data (pagington) för det borde bara vara att kalla på
            // "https://www.willys.se/c/kott-chark-och-fagel?size=30&page=7&sort=topRated" länken och ändra på
            // vad "page="?

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
