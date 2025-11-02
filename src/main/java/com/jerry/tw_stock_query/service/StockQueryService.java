package com.jerry.tw_stock_query.service;

import com.jerry.tw_stock_query.client.ExternalStockClient;
import com.jerry.tw_stock_query.model.StockDataDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StockQueryService {

    @Autowired
    private ExternalStockClient stockClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public StockDataDTO getLatestStockData(String stockCode, String date) throws Exception {

        String rawJson = stockClient.fetchStockData(stockCode, date);
        JsonNode rootNode = objectMapper.readTree(rawJson);

        if (!"OK".equals(rootNode.get("stat").asText())) {
            String title = rootNode.get("title") != null ? rootNode.get("title").asText() : "查詢失敗";
            throw new RuntimeException("TWSE 查詢狀態錯誤: " + title);
        }

        JsonNode dataArray = rootNode.get("data");
        if (dataArray == null || !dataArray.isArray() || dataArray.isEmpty()) {
            throw new RuntimeException("TWSE API 未返回交易數據，可能月份無資料。");
        }

        JsonNode latestData = dataArray.get(dataArray.size() - 1);

        String title = rootNode.get("title").asText();
        String stockName = title.split(" ")[2].trim();

        // 數據清洗和轉換
        String rawVolume = latestData.get(1).asText();
        long volume = Long.parseLong(rawVolume.replaceAll(",", ""));

        String rawClosePrice = latestData.get(6).asText();
        BigDecimal closingPrice = new BigDecimal(rawClosePrice.replaceAll(",", ""));

        String rawChange = latestData.get(7).asText();
        BigDecimal changeAmount = new BigDecimal(rawChange.replaceAll("[,\\+\\-]", ""));

        // 建立 DTO
        StockDataDTO dto = new StockDataDTO();
        dto.setStockCode(stockCode);
        dto.setStockName(stockName);
        dto.setTradeDate(latestData.get(0).asText());
        dto.setVolume(volume);
        dto.setClosingPrice(closingPrice);

        if (rawChange.contains("-")) {
            dto.setChangeAmount(changeAmount.negate());
        } else {
            dto.setChangeAmount(changeAmount);
        }

        return dto;
    }

    // StockQueryService.java 中的 createErrorDTO 方法
    public StockDataDTO createErrorDTO(String errorMessage) {
        StockDataDTO errorDTO = new StockDataDTO();
        // 讓錯誤訊息包含用戶輸入的細節
        errorDTO.setErrorMessage(errorMessage);
        return errorDTO;
    }
}