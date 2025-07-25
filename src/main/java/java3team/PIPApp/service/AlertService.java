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
        List<Stocks> stocksToMonitor = StockList.getStockArray();

        for (Stocks stock : stocksToMonitor) {
            // System.out.println("\n--- 주식 체크: " + stock.getName() + " ---"); // 디버그 로그 추가
            stock.refreshQuote();

            double currentPrice = stock.getCurrentPrice();
            double targetPrice = stock.getTargetPrice();
            double stopPrice = stock.getStopPrice();
            String companyName = stock.getName();

            Stocks.AlertState previousAlertState = stock.getCurrentAlertState();
            Stocks.AlertState newAlertState = Stocks.AlertState.NONE;
            boolean shouldSendAlert = false;
            String title = "";

            // System.out.printf("  현재가: %.2f, 목표가: %.2f, 손절가: %.2f%n", currentPrice, targetPrice, stopPrice); // 디버그 로그 추가
            // System.out.println("  이전 알림 상태: " + previousAlertState); // 디버그 로그 추가

            if (targetPrice != 0.0 && currentPrice >= targetPrice) {
                newAlertState = Stocks.AlertState.ABOVE_TARGET;
                if (previousAlertState != Stocks.AlertState.ABOVE_TARGET) {
                    shouldSendAlert = true;
                    title = companyName + " 목표가 도달!";
                    // System.out.println("  -> 목표가 조건 만족 및 이전 상태와 다름. 알림 전송 예정."); // 디버그 로그 추가
                } else {
                    // System.out.println("  -> 목표가 조건 만족하지만, 이미 ABOVE_TARGET 상태였음. 알림 스킵."); // 디버그 로그 추가
                }
            } else if (stopPrice != 0.0 && currentPrice <= stopPrice) {
                newAlertState = Stocks.AlertState.BELOW_STOP;
                if (previousAlertState != Stocks.AlertState.BELOW_STOP) {
                    shouldSendAlert = true;
                    title = companyName + " 손절가 도달!";
                    // System.out.println("  -> 손절가 조건 만족 및 이전 상태와 다름. 알림 전송 예정."); // 디버그 로그 추가
                } else {
                    // System.out.println("  -> 손절가 조건 만족하지만, 이미 BELOW_STOP 상태였음. 알림 스킵."); // 디버그 로그 추가
                }
            } else {
                newAlertState = Stocks.AlertState.NONE;
                // System.out.println("  -> 어떤 조건도 만족하지 않음."); // 디버그 로그 추가
            }

            stock.setCurrentAlertState(newAlertState);
            // System.out.println("  새로운 알림 상태 설정: " + newAlertState); // 디버그 로그 추가

            if (shouldSendAlert) {
                sendAlert(title, currentPrice, targetPrice, stopPrice, newAlertState);
                // System.out.println("  *** 알림 전송됨: " + title + " ***"); // 디버그 로그 추가
            } else {
                // System.out.println("  알림 전송되지 않음."); // 디버그 로그 추가
            }
        }
    }

    private static void sendAlert(String title, double currentPrice, double targetPrice, double stopPrice, Stocks.AlertState alertType) {
        Platform.runLater(() -> {
            showAlertPopup(title, currentPrice, targetPrice, stopPrice, alertType);
        });
    }

    private static void showAlertPopup(String title, double currentPrice, double targetPrice, double stopPrice, Stocks.AlertState alertType) {
        Stage popupStage = new Stage();
        popupStage.initStyle(StageStyle.TRANSPARENT);
        popupStage.setAlwaysOnTop(true);

        StackPane root = new StackPane();
        root.setStyle(
                "-fx-background-color: rgba(0,0,0,0.3);" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 1px;" +
                        "-fx-background-radius: 5;" +
                        "-fx-border-radius: 5;"
        );
        root.setPrefSize(300, 100);
        root.setAlignment(Pos.CENTER);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;");

        TextFlow messageTextFlow = new TextFlow();
        messageTextFlow.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        // "현재가: " 부분 (흰색)
        Text currentPriceStaticText = new Text("현재가: ");
        currentPriceStaticText.setStyle("-fx-fill: white; -fx-font-size: 14px;");

        // 현재가 값 (빨간색)
        Text currentPriceValueText = new Text(String.format("%,.2f", currentPrice));
        currentPriceValueText.setStyle("-fx-fill: red; -fx-font-size: 14px;");

        // 구분자 (흰색)
        Text separatorText = new Text(", ");
        separatorText.setStyle("-fx-fill: white; -fx-font-size: 14px;");

        // 목표가/손절가 부분
        Text targetOrStopPriceStaticText;
        Text targetOrStopPriceValueText;

        if (alertType == Stocks.AlertState.ABOVE_TARGET) {
            targetOrStopPriceStaticText = new Text("목표가: ");
            targetOrStopPriceStaticText.setStyle("-fx-fill: white; -fx-font-size: 14px;");

            // 목표가 값 (파란색)
            targetOrStopPriceValueText = new Text(String.format("%,.2f", targetPrice));
            targetOrStopPriceValueText.setStyle("-fx-fill: blue; -fx-font-size: 14px;");

        } else { // Stocks.AlertState.BELOW_STOP (손절가)
            targetOrStopPriceStaticText = new Text("손절가: ");
            targetOrStopPriceStaticText.setStyle("-fx-fill: white; -fx-font-size: 14px;");

            // 손절가 값 (파란색으)
            targetOrStopPriceValueText = new Text(String.format("%,.2f", stopPrice));
            targetOrStopPriceValueText.setStyle("-fx-fill: blue; -fx-font-size: 14px;");
        }

        messageTextFlow.getChildren().addAll(
                currentPriceStaticText,
                currentPriceValueText,
                separatorText,
                targetOrStopPriceStaticText,
                targetOrStopPriceValueText
        );

        VBox contentBox = new VBox(5);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.getChildren().addAll(titleLabel, messageTextFlow);

        root.getChildren().add(contentBox);

        Scene scene = new Scene(root);
        scene.setFill(null);

        popupStage.setScene(scene);

        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        double popupWidth = root.getPrefWidth();
        double popupHeight = root.getPrefHeight();

        popupStage.setX(screenWidth - popupWidth - 20);
        popupStage.setY(screenHeight - popupHeight - 20);

        if (AppConstants.AlertSound) {
            java.awt.Toolkit.getDefaultToolkit().beep();
        }

        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> {
            popupStage.hide();
        });

        popupStage.show();
        delay.play();
    }
}