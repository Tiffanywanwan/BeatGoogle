package google.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ScoreCalculator {

    private final List<String> clothingKeywords = Arrays.asList("襯衫", "T恤", "毛衣", "背心", "外套", "風衣", "夾克");
    private final List<String> shoppingKeywords = Arrays.asList("購物", "折扣", "優惠", "促銷", "新品上市", "網購", "品牌", "價格", "官網");
    private final List<String> themeKeywords = Arrays.asList("服飾", "時尚", "穿搭");

    // 計算主題分數
    public double calculateThemeScore(String text) {
        return themeKeywords.stream().anyMatch(text::contains) ? 1.0 : 0.0;
    }

    // 計算衣服類型分數
    public double calculateClothingScore(String text) {
        return clothingKeywords.stream().anyMatch(text::contains) ? 0.5 : 0.0;
    }

    // 計算購物相關分數
    public double calculateShoppingScore(String text) {
        return shoppingKeywords.stream().anyMatch(text::contains) ? 0.3 : 0.0;
    }

    // 計算最終分數
    public double calculateScore(String text, String query) {
        double themeScore = calculateThemeScore(text);
        double clothingScore = calculateClothingScore(text);
        double shoppingScore = calculateShoppingScore(text);

        double queryScore = text.toLowerCase().contains(query.toLowerCase()) ? 2.0 : 0.0;

        return themeScore + clothingScore + shoppingScore + queryScore;
    }
}
