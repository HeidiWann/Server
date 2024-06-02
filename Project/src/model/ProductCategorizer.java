package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductCategorizer {
    private static final Map<ProductCategory, List<String>> categoryKeywords = new HashMap<>();
    private static final Map<ProductSubCategory, List<String>> subCategoryKeywords = new HashMap<>();

    static {
         categoryKeywords.put(ProductCategory.frukt, Arrays.asList(ProductSubCategory.nektarin.toString(),ProductSubCategory.päron.toString()));
        categoryKeywords.put(ProductCategory.grönsaker, Arrays.asList(ProductSubCategory.pumpa.toString(),ProductSubCategory.blomkål.toString(),ProductSubCategory.sötpotatis.toString(),ProductSubCategory.potatis.toString(),ProductSubCategory.rödbeta.toString(),ProductSubCategory.rödkål.toString(),ProductSubCategory.isberg.toString()));
        categoryKeywords.put(ProductCategory.kyckling, Arrays.asList(ProductSubCategory.köttbullar.toString(),ProductSubCategory.bacon.toString(),ProductSubCategory.kycklingfilé.toString(),ProductSubCategory.kycklingfärs.toString(),ProductSubCategory.kycklingpanna.toString(),ProductSubCategory.kycklingpinnar.toString(),ProductSubCategory.strimlad.toString(),ProductSubCategory.kycklingsteak.toString(),ProductSubCategory.tacokyckling.toString(),ProductSubCategory.kycklingspett.toString(),ProductSubCategory.kycklinggyros.toString(),ProductSubCategory.innerfilé.toString(),ProductSubCategory.nuggets.toString(),ProductSubCategory.kycklingkebab.toString(),ProductSubCategory.vingar.toString(),ProductSubCategory.klubba.toString(),ProductSubCategory.kycklinglårfil.toString()));
        categoryKeywords.put(ProductCategory.nöt, Arrays.asList(ProductSubCategory.oxfilé.toString(),ProductSubCategory.nötfärs.toString(),ProductSubCategory.nötspett.toString(),ProductSubCategory.nötkorv.toString()));
        categoryKeywords.put(ProductCategory.mejeriprodukter, Arrays.asList(ProductSubCategory.ost.toString(),ProductSubCategory.yoghurt.toString(),ProductSubCategory.mjölk.toString()));
        categoryKeywords.put(ProductCategory.skafferi, Arrays.asList(ProductSubCategory.kaffe.toString()));
        categoryKeywords.put(ProductCategory.fläsk, Arrays.asList(ProductSubCategory.kotlett.toString(),ProductSubCategory.karré.toString(),ProductSubCategory.bacon.toString(),ProductSubCategory.fläskfilé.toString(),ProductSubCategory.sidfläsk.toString(),ProductSubCategory.gyros.toString(),ProductSubCategory.fläskkarré.toString(),ProductSubCategory.fläskkotlett.toString(),ProductSubCategory.fläskytterfilé.toString()));
        categoryKeywords.put(ProductCategory.anka, Arrays.asList(ProductSubCategory.köttbullar.toString(),ProductSubCategory.hel.toString()));
        categoryKeywords.put(ProductCategory.fisk, Arrays.asList(ProductSubCategory.torsk.toString()));
        categoryKeywords.put(ProductCategory.bröd, Arrays.asList());
        categoryKeywords.put(ProductCategory.majskyckling, Arrays.asList());
        categoryKeywords.put(ProductCategory.kalkon, Arrays.asList(ProductSubCategory.köttbullar.toString(),ProductSubCategory.hel.toString(),ProductSubCategory.kalkon.toString(),ProductSubCategory.kalkonbröstfilé.toString(),ProductSubCategory.kalkonbröst.toString(),ProductSubCategory.kalkonbacon.toString(),ProductSubCategory.minikalkon.toString()));
        categoryKeywords.put(ProductCategory.korv, Arrays.asList(ProductSubCategory.färskkorv.toString(),ProductSubCategory.lammkorv.toString(),ProductSubCategory.ölkorv.toString(),ProductSubCategory.grillkorv.toString(),ProductSubCategory.entrecotekorv.toString(),ProductSubCategory.korv.toString(),ProductSubCategory.kabanosskorv.toString(),ProductSubCategory.salsicciakorv.toString(),ProductSubCategory.chirizokorv.toString(),ProductSubCategory.chorizo.toString()));
        categoryKeywords.put(ProductCategory.fågel, Arrays.asList());
        categoryKeywords.put(ProductCategory.tångrom, Arrays.asList());
        categoryKeywords.put(ProductCategory.vegitariskt, Arrays.asList());
        categoryKeywords.put(ProductCategory.tomat, Arrays.asList(ProductSubCategory.cocktail.toString(),ProductSubCategory.bifftomat.toString(),ProductSubCategory.kvistomat.toString(),ProductSubCategory.cocktailtomater.toString(),ProductSubCategory.krossade.toString(), ProductSubCategory.tomater.toString()));
        categoryKeywords.put(ProductCategory.gurka, Arrays.asList(ProductSubCategory.gurka.toString()));
        categoryKeywords.put(ProductCategory.melon, Arrays.asList());
        categoryKeywords.put(ProductCategory.paprika, Arrays.asList(ProductSubCategory.pulver.toString()));
        categoryKeywords.put(ProductCategory.chili, Arrays.asList(ProductSubCategory.jalapeno.toString(),ProductSubCategory.chillipeppar.toString(),ProductSubCategory.habanero.toString()));
        categoryKeywords.put(ProductCategory.sallad, Arrays.asList(ProductSubCategory.blandsallad.toString(),ProductSubCategory.isberg.toString()));
        categoryKeywords.put(ProductCategory.grönt, Arrays.asList());
        categoryKeywords.put(ProductCategory.bär, Arrays.asList(ProductSubCategory.druvor.toString(),ProductSubCategory.körsbär.toString(),ProductSubCategory.lingon.toString(),ProductSubCategory.hjortron.toString(),ProductSubCategory.tranbär.toString(),ProductSubCategory.björnbär.toString()));
        categoryKeywords.put(ProductCategory.hallon, Arrays.asList());
        categoryKeywords.put(ProductCategory.svarta, Arrays.asList(ProductSubCategory.vinbär.toString()));
        categoryKeywords.put(ProductCategory.lingon, Arrays.asList());
        categoryKeywords.put(ProductCategory.jordgubbar, Arrays.asList());
        categoryKeywords.put(ProductCategory.ost, Arrays.asList(ProductSubCategory.västerbottenost.toString(),ProductSubCategory.färskost.toString()));
        categoryKeywords.put(ProductCategory.fryst, Arrays.asList());
        categoryKeywords.put(ProductCategory.färsk, Arrays.asList());
        categoryKeywords.put(ProductCategory.krögarpytt, Arrays.asList());
        categoryKeywords.put(ProductCategory.vegofärs, Arrays.asList());
        categoryKeywords.put(ProductCategory.falafel, Arrays.asList());
        categoryKeywords.put(ProductCategory.naturell, Arrays.asList());
        categoryKeywords.put(ProductCategory.tofu, Arrays.asList());
        categoryKeywords.put(ProductCategory.quornbitar, Arrays.asList());
        categoryKeywords.put(ProductCategory.quornfiléer, Arrays.asList());
        categoryKeywords.put(ProductCategory.plant, Arrays.asList());
        categoryKeywords.put(ProductCategory.vegokorv, Arrays.asList());
        categoryKeywords.put(ProductCategory.baljväxtfärs, Arrays.asList());
        categoryKeywords.put(ProductCategory.vegobullar, Arrays.asList());
        categoryKeywords.put(ProductCategory.formbar, Arrays.asList());
        categoryKeywords.put(ProductCategory.vegobitar, Arrays.asList());
        categoryKeywords.put(ProductCategory.vegonuggets, Arrays.asList());
        categoryKeywords.put(ProductCategory.äpple, Arrays.asList(ProductSubCategory.juice.toString()));
        categoryKeywords.put(ProductCategory.vegoburgare, Arrays.asList());
        categoryKeywords.put(ProductCategory.vego, Arrays.asList());
        categoryKeywords.put(ProductCategory.vegansk, Arrays.asList());
        categoryKeywords.put(ProductCategory.vegoskivor, Arrays.asList());
        categoryKeywords.put(ProductCategory.schnitzel, Arrays.asList(ProductSubCategory.kalvschnitzel.toString()));
        categoryKeywords.put(ProductCategory.spenatpinnar, Arrays.asList());
        categoryKeywords.put(ProductCategory.köttbullar, Arrays.asList());
        categoryKeywords.put(ProductCategory.burgare, Arrays.asList());
        categoryKeywords.put(ProductCategory.filet, Arrays.asList());
        categoryKeywords.put(ProductCategory.nuggets, Arrays.asList());
        categoryKeywords.put(ProductCategory.creamy, Arrays.asList());
        categoryKeywords.put(ProductCategory.hushåll, Arrays.asList());
        categoryKeywords.put(ProductCategory.pepperoni, Arrays.asList());
        categoryKeywords.put(ProductCategory.rökt, Arrays.asList());
        categoryKeywords.put(ProductCategory.chicken, Arrays.asList(ProductSubCategory.nuggets.toString(),ProductSubCategory.fried.toString()));
        categoryKeywords.put(ProductCategory.smashed, Arrays.asList());
        categoryKeywords.put(ProductCategory.thyme, Arrays.asList());
        categoryKeywords.put(ProductCategory.crispy, Arrays.asList());
        categoryKeywords.put(ProductCategory.pulled, Arrays.asList());
        categoryKeywords.put(ProductCategory.kebab, Arrays.asList(ProductSubCategory.kebabstavar.toString()));
        categoryKeywords.put(ProductCategory.öttfri, Arrays.asList());
        categoryKeywords.put(ProductCategory.veggi, Arrays.asList());
        categoryKeywords.put(ProductCategory.mozzarella, Arrays.asList());
        categoryKeywords.put(ProductCategory.parveggio, Arrays.asList());
        categoryKeywords.put(ProductCategory.potatissallad, Arrays.asList());
        categoryKeywords.put(ProductCategory.sweet, Arrays.asList());
        categoryKeywords.put(ProductCategory.färs, Arrays.asList(ProductSubCategory.blandfärs.toString(),ProductSubCategory.kalvfärs.toString(),ProductSubCategory.salsicciafärs.toString(),ProductSubCategory.högrevsfärs.toString(),ProductSubCategory.hamburgarefärs.toString(),ProductSubCategory.lammfärs.toString()));
        categoryKeywords.put(ProductCategory.caviar, Arrays.asList());
        categoryKeywords.put(ProductCategory.torsk, Arrays.asList());
        categoryKeywords.put(ProductCategory.lax, Arrays.asList());
        categoryKeywords.put(ProductCategory.romsås, Arrays.asList());
        categoryKeywords.put(ProductCategory.organo, Arrays.asList());
        categoryKeywords.put(ProductCategory.basilika, Arrays.asList());
        categoryKeywords.put(ProductCategory.dill, Arrays.asList());
        categoryKeywords.put(ProductCategory.hovmästarsås, Arrays.asList());
        categoryKeywords.put(ProductCategory.hollandaisås, Arrays.asList());
        categoryKeywords.put(ProductCategory.remouladsås, Arrays.asList());
        categoryKeywords.put(ProductCategory.räkor, Arrays.asList());
        categoryKeywords.put(ProductCategory.rom, Arrays.asList());
        categoryKeywords.put(ProductCategory.matjes, Arrays.asList());
        categoryKeywords.put(ProductCategory.mästarmatje, Arrays.asList());
        categoryKeywords.put(ProductCategory.bohusmatjes, Arrays.asList());
        categoryKeywords.put(ProductCategory.matjessill, Arrays.asList(ProductSubCategory.matjesill.toString()));
        categoryKeywords.put(ProductCategory.fiskpinnar, Arrays.asList());
        categoryKeywords.put(ProductCategory.strömming, Arrays.asList());
        categoryKeywords.put(ProductCategory.kotlett, Arrays.asList(ProductSubCategory.lammkotlett.toString(),ProductSubCategory.grillkotlett.toString(),ProductSubCategory.fläskkotlett.toString()));
        categoryKeywords.put(ProductCategory.ryggbiff, Arrays.asList());
        categoryKeywords.put(ProductCategory.varmrökt, Arrays.asList(ProductSubCategory.lax.toString(),ProductSubCategory.laxsida.toString(),ProductSubCategory.laxfilé.toString()));
        categoryKeywords.put(ProductCategory.gravad, Arrays.asList(ProductSubCategory.lax.toString(),ProductSubCategory.laxsida.toString(),ProductSubCategory.laxfilé.toString()));
        categoryKeywords.put(ProductCategory.anjovis, Arrays.asList());
        categoryKeywords.put(ProductCategory.löjrom, Arrays.asList());
        categoryKeywords.put(ProductCategory.hummer, Arrays.asList());
        categoryKeywords.put(ProductCategory.fish, Arrays.asList());
        categoryKeywords.put(ProductCategory.laxfilé, Arrays.asList());
        categoryKeywords.put(ProductCategory.glass, Arrays.asList());
        categoryKeywords.put(ProductCategory.fikabröd, Arrays.asList());
        categoryKeywords.put(ProductCategory.ägg, Arrays.asList(ProductSubCategory.frigående.toString()));
        categoryKeywords.put(ProductCategory.yoghurt, Arrays.asList());
        categoryKeywords.put(ProductCategory.mjölk, Arrays.asList(ProductSubCategory.mellan.toString()));
        categoryKeywords.put(ProductCategory.smör, Arrays.asList());
        categoryKeywords.put(ProductCategory.juice, Arrays.asList(ProductSubCategory.orange.toString()));
        categoryKeywords.put(ProductCategory.havredryck, Arrays.asList(ProductSubCategory.havredryck.toString()));
        categoryKeywords.put(ProductCategory.vanilj, Arrays.asList());
        categoryKeywords.put(ProductCategory.choklad, Arrays.asList());
        categoryKeywords.put(ProductCategory.apelsin, Arrays.asList(ProductSubCategory.juice.toString()));
        categoryKeywords.put(ProductCategory.kvarg, Arrays.asList());
        categoryKeywords.put(ProductCategory.skogrbär, Arrays.asList());
        categoryKeywords.put(ProductCategory.mellanmjölk, Arrays.asList());
        categoryKeywords.put(ProductCategory.gräddfil, Arrays.asList());
        categoryKeywords.put(ProductCategory.creme, Arrays.asList());
        categoryKeywords.put(ProductCategory.filmjölk, Arrays.asList());
        categoryKeywords.put(ProductCategory.jäst, Arrays.asList());
        categoryKeywords.put(ProductCategory.turkisk, Arrays.asList(ProductSubCategory.yoghurt.toString()));
        categoryKeywords.put(ProductCategory.vispgrädde, Arrays.asList());
        categoryKeywords.put(ProductCategory.mild, Arrays.asList());
        categoryKeywords.put(ProductCategory.grekisk, Arrays.asList());
        categoryKeywords.put(ProductCategory.matgrädde, Arrays.asList());
        categoryKeywords.put(ProductCategory.lättmjölk, Arrays.asList());
        categoryKeywords.put(ProductCategory.Imat, Arrays.asList());
        categoryKeywords.put(ProductCategory.barista, Arrays.asList());
        categoryKeywords.put(ProductCategory.keso, Arrays.asList());
        categoryKeywords.put(ProductCategory.kyld, Arrays.asList());
        categoryKeywords.put(ProductCategory.ayran, Arrays.asList());
        categoryKeywords.put(ProductCategory.soya, Arrays.asList());
        categoryKeywords.put(ProductCategory.grillost, Arrays.asList());
        categoryKeywords.put(ProductCategory.mjukost, Arrays.asList());
        categoryKeywords.put(ProductCategory.växtbaserad, Arrays.asList());
        categoryKeywords.put(ProductCategory.fetaost, Arrays.asList());
        categoryKeywords.put(ProductCategory.grillkrydda, Arrays.asList());
        categoryKeywords.put(ProductCategory.Tomatketchup, Arrays.asList());
    }

    static {
        subCategoryKeywords.put(ProductSubCategory.ryggbiff, Arrays.asList("ryggbiff"));
    }


    public static ProductCategory categorizeProduct(String productName) {
        for (Map.Entry<ProductCategory, List<String>> entry : categoryKeywords.entrySet()) {
            for (String keyword : entry.getValue()) {
                if (productName.toLowerCase().contains(keyword.toLowerCase())) {
                    return entry.getKey();
                }
            }
        }
        return ProductCategory.okategoriserad;
    }

    public static ProductSubCategory getSubCategory(String productName) {
        for (Map.Entry<ProductSubCategory, List<String>> entry : subCategoryKeywords.entrySet()) {
            for (String keyword : entry.getValue()) {
                if (productName.toLowerCase().contains(keyword.toLowerCase())) {
                    return entry.getKey();
                }
            }
        }
        return ProductSubCategory.okategoriserad;
    }
}
