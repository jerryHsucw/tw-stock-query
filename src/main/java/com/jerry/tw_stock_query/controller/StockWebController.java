// StockWebController.java 修正
package com.jerry.tw_stock_query.controller;

import com.jerry.tw_stock_query.model.StockDataDTO;
import com.jerry.tw_stock_query.service.StockQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// 移除不再需要的 java.time.DayOfWeek 等日期處理類

@Controller
public class StockWebController {

    @Autowired
    private StockQueryService stockService;

    // 不再需要日期格式化常量，因為輸入參數就是字串

    // 移除 getQueryDate() 方法

    @GetMapping("/")
    public String home() {
        return "query_page";
    }

    @GetMapping("/query")
    public String queryStock(
            @RequestParam(required = true) String code, // 股票代碼現在是必須的
            @RequestParam(required = true) String date, // 【新增】日期參數現在是必須的
            Model model) {

        // 將用戶輸入的代碼和日期重新放回 Model，以便下次查詢時保留在輸入框中
        model.addAttribute("searchCode", code);
        model.addAttribute("searchDate", date);

        try {
            // 直接使用用戶提供的日期參數進行 API 呼叫
            // 注意：TWSE API 只需要 YYYYMMDD 格式，並且它會自動查詢該月份的數據
            StockDataDTO stockData = stockService.getLatestStockData(code, date);

            model.addAttribute("stockData", stockData);
        } catch (Exception e) {
            System.err.println("查詢錯誤: " + e.getMessage());
            model.addAttribute("errorDTO", stockService.createErrorDTO("股票代碼/日期查詢失敗: " + e.getMessage()));
        }

        return "query_page";
    }
}