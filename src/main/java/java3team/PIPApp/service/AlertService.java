package service;

import config.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.List;

public class AlertService {

    public static void checkPriceAndAlert() {
        // TODO: 가격 확인 및 알림 로직
    }

    private static void sendAlert(String title, double currentPrice, double targetPrice, double stopPrice, Stocks.AlertState alertType) {
        // TODO: 알림 전송 로직
    }

    private static void showAlertPopup(String title, double currentPrice, double targetPrice, double stopPrice, Stocks.AlertState alertType) {
        // TODO: 알림 팝업 표시 로직
    }
}