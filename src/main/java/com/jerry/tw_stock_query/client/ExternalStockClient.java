package com.jerry.tw_stock_query.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

/**
 * 外部股票 API 客戶端
 */
@Component
public class ExternalStockClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${twse.api.base-url}")
    private String twseApiBaseUrl;

    /**
     * 獲取 TWSE API 返回的原始 JSON 字串
     */
    public String fetchStockData(String stockCode, String date) {

        // 構建完整的 API URL
        String fullUrl = twseApiBaseUrl + "&date=" + date + "&stockNo=" + stockCode;

        System.out.println("API 呼叫 URL: " + fullUrl);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(fullUrl, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new RuntimeException("外部 API 呼叫失敗，狀態碼: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("API 錯誤: " + e.getStatusCode() + " - " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("連線至外部 API 失敗。", e);
        }
    }
}