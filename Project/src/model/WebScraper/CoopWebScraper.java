package model.WebScraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CoopWebScraper {

    private List<String> webpageProductList = new ArrayList<>();




    public void scrapeCoop(String url) {
        try {
          /* Detta är kod baserad på vad Heidi skrev för ica scraping, dock flyger detta mig över huvet @Jansson

          Document doc = Jsoup.connect(url).get();
            Elements script = doc.select("[id=\"__NEXT_DATA__\"]");
            String jsonData = script.first().toString().substring(script.first().toString().lastIndexOf("<script id=\"__NEXT_DATA__\" type=\"application/json\">") + 51);
            jsonData = jsonData.substring(0, jsonData.lastIndexOf("</script>"));
            JSONObject productJSONData = new JSONObject(jsonData);
            JSONArray productJSONArray = new JSONArray();
*/
            Document doc = Jsoup.connect(url).get();
            //Elements proNames=doc.getElementsByClass("ProductTeaser-link u-outlineSolidBase2");
            // Elements proPrices=doc

          //  Elements proNames = doc.getElementsByClass("Grid Grid-items Grid--gutterAxsm");
            Elements proNames = doc.getElementsByClass("Main Main--fullHeight");

            /*TODO har samma problem här som med willys, där man inte kommer vidare förbi en <div> (i detta fall
               klassen "Main Main--fullHeight"). Den <div> där datan vi letar efter ligger under denna div som vi inte
               kommer förbi.
               På ett liknande sätt som willys borde man kunna hämta ny data (paginton) genom att kalla på länken
               o ändra"page=?".

             */

            System.out.println("\nSize is:" +proNames.size());

            for(Element name:proNames){
                System.out.println(name.text());
                System.out.println(name+"\n ||------------------------------------------||");
            }
            System.out.println("All done");



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
