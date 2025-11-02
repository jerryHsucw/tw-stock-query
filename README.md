# 📈 台股基本資訊查詢系統 (TW Stock Query System)

這是一個基於 Spring Boot 3 構建的 Web 應用程式。專案旨在演示標準的 MVC 架構設計、與外部 RESTful API 的整合、以及複雜 JSON 數據的解析與清洗。

系統允許使用者輸入股票代碼及查詢月份，即可獲取該月份的每日成交數據，並在前端以清晰的表格和紅漲綠跌的顏色呈現。

## 🛠️ 技術棧 (Technology Stack)

* **核心框架：** Spring Boot 3.x
* **語言：** Java 17+ (LTS)
* **架構：** MVC (Model-View-Controller) 三層架構
* **數據模型：** Lombok
* **API 客戶端：** RestTemplate (傳統同步 HTTP 客戶端)
* **JSON 處理：** Jackson (ObjectMapper)
* **前端模板：** Thymeleaf

## 📐 系統架構設計

專案嚴格遵循 MVC 分層原則，實現了職責分離 (Separation of Concerns)：

1.  **Controller (`StockWebController`)**：處理 HTTP 請求，管理 Session 狀態，並選擇視圖 (View)。負責捕獲 Service 層拋出的業務異常，並傳遞錯誤 DTO 給前端。
2.  **Service (`StockQueryService`)**：核心業務邏輯層。負責 JSON 解析、數據清洗、類型轉換（特別是將字串轉換為 `BigDecimal` 和 `long` 以確保金融數據精度）。
3.  **Client (`ExternalStockClient`)**：專注於外部 API 的通訊細節，隔離外部依賴。

## ✨ 專案亮點 (Key Features & Technical Highlights)

### 1. 健壯的 JSON 數據處理 (Data Cleansing)

TWSE API 返回的 JSON 數據包含許多非標準格式 (如數字帶有逗號 `,`)。Service 層利用 **Jackson `ObjectMapper`** 實現了精確的數據清洗和類型轉換：

* **精度保證：** 將價格相關欄位（如收盤價、漲跌價差）從字串轉換為 **`java.math.BigDecimal`**，徹底避免 Java `double` 浮點數精度問題。
* **數值清理：** 在轉換股數或金額前，程式碼會使用 `replaceAll(",", "")` 清除數值中的千位分隔符。
* **業務邏輯：** 從 TWSE 返回的整個月份數據中，精確提取**最新一個交易日**的資料進行展示。

### 2. 優雅的錯誤處理機制 (Graceful Error Handling)

當 API 查詢失敗（例如：股票代碼錯誤、非交易日查詢等），系統不會拋出 HTTP 500 錯誤。

* **流程：** Service 層捕獲 TWSE API 返回的非 `"OK"` 狀態，拋出自定義異常。
* **呈現：** Controller 層捕獲異常，生成 `ErrorDTO`，並通過 Thymeleaf 在前端頁面中以友善的紅色錯誤訊息展示，提供良好的用戶體驗。

### 3. 前端動態視覺化 (Dynamic UI)

* 使用 **Thymeleaf 的條件判斷** (`th:if`, `th:unless`) 來控制結果區塊的顯示。
* 利用 **`th:classappend`** 動態判斷漲跌價差 (`changeAmount`) 的正負值，實現股價數據 **紅漲綠跌** 的樣式應用。

## 🚀 如何運行 (Getting Started)

### 預備條件

* JDK 17+ (LTS)
* Maven 3.x

### 步驟 1: 配置 API URL

在 `src/main/resources/application.properties` 中確認以下配置：

```properties
twse.api.base-url=[https://www.twse.com.tw/exchangeReport/STOCK_DAY?response=json](https://www.twse.com.tw/exchangeReport/STOCK_DAY?response=json)
```
步驟 2: 啟動應用程式
使用 Maven 執行 Spring Boot 應用程式：
mvn clean install
mvn spring-boot:run

系統分析師
自訂 Gem
好的，這是為您的 JDK 17 環境 優化後的 README.md 內容。我將所有相關的 JDK 版本資訊都調整為 17+ (LTS)，以確保文件的精確性。

請將以下內容複製並儲存到您的專案根目錄下，檔案名為 README.md：

📄 README.md 內容 (JDK 17 版本)
Markdown
# 📈 台股基本資訊查詢系統 (TW Stock Query System)

這是一個基於 Spring Boot 3 構建的 Web 應用程式。專案旨在演示標準的 MVC 架構設計、與外部 RESTful API 的整合、以及複雜 JSON 數據的解析與清洗。

系統允許使用者輸入股票代碼及查詢月份，即可獲取該月份的每日成交數據，並在前端以清晰的表格和紅漲綠跌的顏色呈現。

## 🛠️ 技術棧 (Technology Stack)

* **核心框架：** Spring Boot 3.x
* **語言：** Java 17+ (LTS)
* **架構：** MVC (Model-View-Controller) 三層架構
* **數據模型：** Lombok
* **API 客戶端：** RestTemplate (傳統同步 HTTP 客戶端)
* **JSON 處理：** Jackson (ObjectMapper)
* **前端模板：** Thymeleaf

## 📐 系統架構設計

專案嚴格遵循 MVC 分層原則，實現了職責分離 (Separation of Concerns)：

1.  **Controller (`StockWebController`)**：處理 HTTP 請求，管理 Session 狀態，並選擇視圖 (View)。負責捕獲 Service 層拋出的業務異常，並傳遞錯誤 DTO 給前端。
2.  **Service (`StockQueryService`)**：核心業務邏輯層。負責 JSON 解析、數據清洗、類型轉換（特別是將字串轉換為 `BigDecimal` 和 `long` 以確保金融數據精度）。
3.  **Client (`ExternalStockClient`)**：專注於外部 API 的通訊細節，隔離外部依賴。

## ✨ 專案亮點 (Key Features & Technical Highlights)

### 1. 健壯的 JSON 數據處理 (Data Cleansing)

TWSE API 返回的 JSON 數據包含許多非標準格式 (如數字帶有逗號 `,`)。Service 層利用 **Jackson `ObjectMapper`** 實現了精確的數據清洗和類型轉換：

* **精度保證：** 將價格相關欄位（如收盤價、漲跌價差）從字串轉換為 **`java.math.BigDecimal`**，徹底避免 Java `double` 浮點數精度問題。
* **數值清理：** 在轉換股數或金額前，程式碼會使用 `replaceAll(",", "")` 清除數值中的千位分隔符。
* **業務邏輯：** 從 TWSE 返回的整個月份數據中，精確提取**最新一個交易日**的資料進行展示。

### 2. 優雅的錯誤處理機制 (Graceful Error Handling)

當 API 查詢失敗（例如：股票代碼錯誤、非交易日查詢等），系統不會拋出 HTTP 500 錯誤。

* **流程：** Service 層捕獲 TWSE API 返回的非 `"OK"` 狀態，拋出自定義異常。
* **呈現：** Controller 層捕獲異常，生成 `ErrorDTO`，並通過 Thymeleaf 在前端頁面中以友善的紅色錯誤訊息展示，提供良好的用戶體驗。

### 3. 前端動態視覺化 (Dynamic UI)

* 使用 **Thymeleaf 的條件判斷** (`th:if`, `th:unless`) 來控制結果區塊的顯示。
* 利用 **`th:classappend`** 動態判斷漲跌價差 (`changeAmount`) 的正負值，實現股價數據 **紅漲綠跌** 的樣式應用。

## 🚀 如何運行 (Getting Started)

### 預備條件

* JDK 17+ (LTS)
* Maven 3.x

### 步驟 1: 配置 API URL

在 `src/main/resources/application.properties` 中確認以下配置：

```properties
twse.api.base-url=[https://www.twse.com.tw/exchangeReport/STOCK_DAY?response=json](https://www.twse.com.tw/exchangeReport/STOCK_DAY?response=json)
步驟 2: 啟動應用程式
使用 Maven 執行 Spring Boot 應用程式：
```
mvn clean install
mvn spring-boot:run
步驟 3: 訪問系統
應用程式將在 8080 端口啟動。在瀏覽器中訪問：

http://localhost:8080/
在輸入框中輸入股票代碼（如 2330、2454）和查詢月份 (YYYYMMDD 格式)，即可查詢結果。



