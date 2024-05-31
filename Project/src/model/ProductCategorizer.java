package model;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductCategorizer {
    private static final Map<ProductCategory, List<String>> categoryKeywords = new HashMap<>();
    private static final Map<ProductSubCategory, List<String>> subCategoryKeywords = new HashMap<>();

    static {
        categoryKeywords.put(ProductCategory.frukt, Arrays.asList("apelsin", "äpple", "banan"));
        categoryKeywords.put(ProductCategory.grönsaker, Arrays.asList("tomat", "gurka", "paprika", "potatis"));
        categoryKeywords.put(ProductCategory.kyckling, Arrays.asList("kyckling", "nöt", "fläsk", "karre", "bröstfilé", "oxfilé", "biff", "kotlett"));
        categoryKeywords.put(ProductCategory.nöt, Arrays.asList("fläsk", "karre", "bröstfilé", "oxfilé", "biff", "kotlett"));
        categoryKeywords.put(ProductCategory.mejeriprodukter, Arrays.asList("mjölk", "yoghurt", "ost"));
        categoryKeywords.put(ProductCategory.skafferi, Arrays.asList("tomatpuré", "ketchup", "senap", "majonnäs"));
        // Lägg till fler kategorier och nyckelord efter behov
    }
    static {
        subCategoryKeywords.put(ProductSubCategory.ryggbiff, Arrays.asList("ryggbiff"));

        // Lägg till fler kategorier och nyckelord efter behov
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
