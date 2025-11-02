package com.jerry.tw_stock_query.model;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 股票數據傳輸物件 (DTO)
 */
@Data
public class StockDataDTO {

    private String stockCode;
    private String stockName;
    private String tradeDate;

    // 使用 BigDecimal 確保價格精度
    private BigDecimal closingPrice;

    // 成交股數
    private long volume;

    // 漲跌價差
    private BigDecimal changeAmount;

    // 額外欄位：用於顯示錯誤訊息
    private String errorMessage;
}