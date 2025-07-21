package ui;

import config.StockList;
import config.Stocks;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import service.StockService;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

public class PriceInfo_Controller {
    private final StockService stockService = new StockService(); // 주가 API
    @FXML private Label nameLabel;    // 티커

    @FXML private Label currentPriceLabel;  // 현재가
    @FXML private Label openPriceLabel;     // 시가
    @FXML private Label highPriceLabel;     // 당일 최고가
    @FXML private Label lowPriceLabel;      // 당일 최저가
    @FXML private Label previousClosePriceLabel;      // 전일 종가

    @FXML private Label refreshTimeLabel;   // 최근 갱신 시간

    @FXML private ComboBox<String> comboBoxID;  // 콤보박스

    private Timeline refreshTimeline;  // 주기적 업데이트용 타임라인


    @FXML
    public void initialize() {
        // 콤보박스 초기화
        comboBoxID.getItems().clear();
        for (Stocks stock : StockList.getStockArray()) {
            comboBoxID.getItems().add(stock.getName());
        }
        Stocks quote = null;
        // 콤보박스 선택 이벤트 핸들러
        comboBoxID.setOnAction(e -> {
            String selectedName = comboBoxID.getValue();
            if (selectedName == null) return;

            for (Stocks stock : StockList.getStockArray()) {
                if (stock.getName().equals(selectedName)) {
                    updateLabels(stock);
                    timelineRefresh(stock);
                    break;
                }
            }
        });

        if (quote.get() == null) {
            System.out.println("시세 정보를 불러오지 못했습니다.");
            return;
        }

        quote.currentPrice = quote.get().getCurrentPrice();
        quote.openPrice = quote.get().getOpenPrice();
        quote.highPrice = quote.get().getHighPrice();
        quote.lowPrice = quote.get().getLowPrice();
        quote.previousClosePrice = quote.get().getPreviousClosePrice();
        quote.refreshTime = LocalDateTime.now();

        // 초기 값 설정 (있다면)
        if (!StockList.getStockArray().isEmpty()) {
            Stocks firstStock = StockList.getStockArray().get(0);
            comboBoxID.getSelectionModel().select(firstStock.getName());
            updateLabels(firstStock);
            timelineRefresh(firstStock);
        }
    }

    // 라벨 업데이트
    private void updateLabels(Stocks stock) {
        nameLabel.setText(stock.getName());
        currentPriceLabel.setText(String.valueOf(stock.currentPrice));
        openPriceLabel.setText(String.valueOf(stock.openPrice));
        highPriceLabel.setText(String.valueOf(stock.highPrice));
        lowPriceLabel.setText(String.valueOf(stock.lowPrice));
        previousClosePriceLabel.setText(String.valueOf(stock.previousClosePrice));
        refreshTimeLabel.setText(String.valueOf(stock.api_refreshTime));

        System.out.println("🔄 [" + stock.getName() + "] 라벨 정보 자동 새로고침");
    }


    /// 선택된 종목의 주기에 따라 자동 새로고침 시작
    private void timelineRefresh(Stocks stock) {
        if (refreshTimeline != null) {
            refreshTimeline.stop();
        }

        int refreshSeconds = stock.getRefresh();
        if (refreshSeconds <= 0) return;

        refreshTimeline = new Timeline(
                new KeyFrame(Duration.seconds(refreshSeconds), event -> {
                    /// API에서 stock 값을 갱신해주고 있다면 updateLabel 호출만 해도 됨
                    updateLabels(stock);
                })
        );

        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }





    /// 사이드바 함수 ///
    // PIP 활성화
    @FXML
    private void pipClick(ActionEvent event) {
        if (!StockList.getStockArray().isEmpty()){
            // 현재 메인 스테이지 닫기
            Main.mainStage.close();

            // 타임라인 정지
            if (refreshTimeline != null) {
                refreshTimeline.stop();
            }

            // 새 PIP 스테이지 열기
            _PIP_Launcher.launchAllPipWindows();
        }
        else {
            System.out.println("⚠ 종목이 비어있어 PIP창을 활성화시킬 수 없습니다.\n\n");

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("StockPIP");
            alert.setHeaderText(null);
            alert.setContentText("종목을 먼저 입력해 주십시오.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleHomeClick(MouseEvent event) {
        System.out.println("홈 클릭됨");
        if (refreshTimeline != null) {
            refreshTimeline.stop();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
            Parent root = loader.load();
            Main.mainStage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAssetInfoClick(MouseEvent event) {
        System.out.println("종목 정보 클릭됨");
        if (refreshTimeline != null) {
            refreshTimeline.stop();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("assetInfo.fxml"));
            Parent root = loader.load();
            Main.mainStage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePriceInfoClick(MouseEvent event) {
        System.out.println("시세 정보 클릭됨");
    }

    @FXML
    private void handleSettingsClick(MouseEvent event) {
        System.out.println("설정 클릭됨");
        if (refreshTimeline != null) {
            refreshTimeline.stop();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("settings.fxml"));
            Parent root = loader.load();
            Main.mainStage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExternalClick(MouseEvent event) {
        System.out.println("외부 사이트 클릭됨");
        try {
            Desktop.getDesktop().browse(new URI("https://finnhub.io/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}