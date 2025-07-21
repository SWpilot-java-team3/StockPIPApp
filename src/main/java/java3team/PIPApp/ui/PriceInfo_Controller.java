package ui;

import config.AppConstants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.StockService;
import api.model.StockQuote;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

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

    private Timeline refreshTimeline;  // 자동 갱신 타이머

    /// API 연동 이후 빈칸 라벨에 셋
    @FXML
    public void initialize() {
        // ComboBox에 NameList 넣기 (이름 목록만 보여줌)
        comboBoxID.getItems().setAll(AppConstants.NameList);

        // 현재 선택된 name이 있다면 그것도 선택해줌 (선택 유지 목적)
        if (AppConstants.NameList.contains(AppConstants.name)) {
            comboBoxID.setValue(AppConstants.name);
        }

        // 콤보박스 선택 시 업데이트
        comboBoxID.setOnAction(event -> {
            String selected = comboBoxID.getValue();
            if (selected != null && !selected.isEmpty()) {
                AppConstants.name = selected;
                updateStockQuote();
            }
        });

        // 최초 로딩 시 업데이트
        if (comboBoxID.getValue() != null) {
            updateStockQuote();
        }

        // 5초마다 주기적으로 업데이트
        refreshTimeline = new Timeline( //업데이트 주기(main에서 받아오는 로직 추가 필요)
                new KeyFrame(Duration.seconds(5), e -> updateStockQuote())
        );
        refreshTimeline.setCycleCount(Timeline.INDEFINITE);
        refreshTimeline.play();
    }

    private void updateStockQuote() {
        if (AppConstants.name == null || AppConstants.name.isEmpty()) return;

        StockQuote quote = stockService.getLiveStockQuote(AppConstants.name);
        if (quote == null) {
            System.out.println("시세 정보를 불러오지 못했습니다.");
            return;
        }

        AppConstants.currentPrice = quote.getCurrentPrice();
        AppConstants.openPrice = quote.getOpenPrice();
        AppConstants.highPrice = quote.getHighPrice();
        AppConstants.lowPrice = quote.getLowPrice();
        AppConstants.previousClosePrice = quote.getPreviousClosePrice();
        AppConstants.refreshTime = LocalDateTime.now();

        nameLabel.setText(AppConstants.name);
        currentPriceLabel.setText(String.valueOf(AppConstants.currentPrice));
        openPriceLabel.setText(String.valueOf(AppConstants.openPrice));
        highPriceLabel.setText(String.valueOf(AppConstants.highPrice));
        lowPriceLabel.setText(String.valueOf(AppConstants.lowPrice));
        previousClosePriceLabel.setText(String.valueOf(AppConstants.previousClosePrice));
        refreshTimeLabel.setText(String.valueOf(AppConstants.refreshTime));

        // name이 있을 때만 항목에 보임
        boolean visible = !AppConstants.name.isEmpty();
        nameLabel.setVisible(visible);
        currentPriceLabel.setVisible(visible);
        openPriceLabel.setVisible(visible);
        highPriceLabel.setVisible(visible);
        lowPriceLabel.setVisible(visible);
        previousClosePriceLabel.setVisible(visible);
        refreshTimeLabel.setVisible(visible);
    }

    /// 사이드바 함수 ///
    @FXML
    private void pipClick(ActionEvent event) {
        Main.mainStage.close();
        Stage pipStage = new Stage();
        _PIP_Main pipWindow = new _PIP_Main();
        pipWindow.pip_On(pipStage);
    }

    @FXML
    private void handleHomeClick(MouseEvent event) {
        System.out.println("홈 클릭됨");
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
