package google.demo;

import google.util.ScoreCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreCalculatorTests {

    @Test
    public void testCalculateThemeScore() {
        ScoreCalculator scoreCalculator = new ScoreCalculator();

        // 測試包含 "服飾" 這個關鍵字的標題
        String titleWithTheme = "T恤促銷 - 服飾";
        double themeScore = scoreCalculator.calculateThemeScore(titleWithTheme);
        assertEquals(1.0, themeScore);

        // 測試不包含 "服飾" 這個關鍵字的標題
        String titleWithoutTheme = "促銷活動";
        double themeScoreWithoutTheme = scoreCalculator.calculateThemeScore(titleWithoutTheme);
        assertEquals(0.0, themeScoreWithoutTheme);
    }

    @Test
    public void testCalculateClothingScore() {
        ScoreCalculator scoreCalculator = new ScoreCalculator();

        // 測試包含衣服類型關鍵字的標題
        String titleWithClothing = "冬季外套大促銷";
        double clothingScore = scoreCalculator.calculateClothingScore(titleWithClothing);
        assertEquals(0.5, clothingScore);

        // 測試不包含衣服類型關鍵字的標題
        String titleWithoutClothing = "夏季折扣";
        double clothingScoreWithoutClothing = scoreCalculator.calculateClothingScore(titleWithoutClothing);
        assertEquals(0.0, clothingScoreWithoutClothing);
    }

    @Test
    public void testCalculateShoppingScore() {
        ScoreCalculator scoreCalculator = new ScoreCalculator();

        // 測試包含購物相關關鍵字的標題
        String titleWithShopping = "新款T恤 優惠促銷";
        double shoppingScore = scoreCalculator.calculateShoppingScore(titleWithShopping);
        assertEquals(0.3, shoppingScore);

        // 測試不包含購物相關關鍵字的標題
        String titleWithoutShopping = "最新時尚潮流";
        double shoppingScoreWithoutShopping = scoreCalculator.calculateShoppingScore(titleWithoutShopping);
        assertEquals(0.0, shoppingScoreWithoutShopping);
    }

    @Test
    public void testFinalScore() {
        ScoreCalculator scoreCalculator = new ScoreCalculator();

        // 測試綜合分數計算
        String title = "T恤 促銷 優惠";
        String query = "T恤";
        double finalScore = scoreCalculator.calculateScore(title, query);

        // 根據預定義的分數邏輯（主題1.0，衣服類型0.5，購物0.3，查詢分數2.0），此分數為3.8
        assertEquals(3.8, finalScore);
    }
}
