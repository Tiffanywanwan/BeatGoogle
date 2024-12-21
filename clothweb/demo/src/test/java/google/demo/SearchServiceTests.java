package google.demo;

import google.demo.service.SearchService;
import google.util.HTMLHandler;
import google.util.ScoreCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchServiceTests {

    private SearchService searchService;
    private HTMLHandler htmlHandler;
    private ScoreCalculator scoreCalculator;

    @BeforeEach
    public void setUp() {
        // Mock HTMLHandler 和 ScoreCalculator
        htmlHandler = Mockito.mock(HTMLHandler.class);
        scoreCalculator = Mockito.mock(ScoreCalculator.class);

        // 初始化 SearchService
        searchService = new SearchService(htmlHandler, scoreCalculator);
    }

    @Test
    public void testSearch() throws Exception {
        String query = "T恤";

        // 模擬 HTMLHandler 返回的 HTML 內容
        String mockHtmlContent = """
            <html>
                <body>
                    <div class='kCrYT'>
                        <a href='/url?q=https://example.com&sa=U&ved=0'>T恤促銷</a>
                    </div>
                </body>
            </html>
        """;
        Mockito.when(htmlHandler.fetchContent("https://www.google.com/search?q=" + query + "&oe=utf8&num=20"))
               .thenReturn(mockHtmlContent);

        // 模擬 ScoreCalculator 返回的分數
        Mockito.when(scoreCalculator.calculateScore("T恤促銷", query)).thenReturn(3.5);

        // 測試搜尋功能
        Map<String, String> results = searchService.search(query);

        // 驗證結果
        assertEquals(1, results.size());
        assertEquals("https://example.com", results.get("T恤促銷 (Score: 3.5)"));
    }
}

