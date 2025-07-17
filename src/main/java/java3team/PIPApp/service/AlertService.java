package service;

import config.AppConstants;
import javafx.application.Platform;
import javafx.scene.control.Alert;


public class AlertService {

    public static void checkPriceAndAlert() {
        // AppConstants 변수 직접 참조 //구조체로 자료 변환 될 시, 수정
        double currentPrice = AppConstants.currentPrice;
        double targetPrice = AppConstants.targetPrice;
        double stopPrice = AppConstants.stopPrice;
        String companyName = AppConstants.name;

        if (currentPrice >= targetPrice && targetPrice != 0.0) {
            sendAlert(companyName + " 목표가 도달!", "현재가: " + currentPrice + ", 목표가: " + targetPrice);
        } else if (currentPrice <= stopPrice && stopPrice != 0.0) {
            sendAlert(companyName + " 손절가 도달!", "현재가: " + currentPrice + ", 손절가: " + stopPrice);
        }
    }

    private static void sendAlert(String title, String message) {
        Platform.runLater(() -> {
            //  팝업 알림만 호출
            showAlertPopup(title, message);
        });
    }

    private static void showAlertPopup(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}