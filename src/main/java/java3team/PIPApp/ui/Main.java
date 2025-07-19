package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import api.model.FinnhubApiClient;
import api.model.StockQuote;

public class Main extends Application {
    public static Stage mainStage; //

    public void start(Stage primaryStage) throws Exception{
        Font.loadFont(getClass().getResourceAsStream("/fonts/Pretendard-Regular.ttf"), 15);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Pretendard-Bold.ttf"), 15);

        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        primaryStage.setTitle("StockPIP-App");
        primaryStage.setScene(new Scene(root, 1220, 740));
        primaryStage.show();

        mainStage = primaryStage;
    }


    public static void main(String[] args) { launch(args);

        FinnhubApiClient client = new FinnhubApiClient();
        String symbol = "AAPL"; // 또는 args[0] 로 입력 받을 수도 있음

        StockQuote quote = client.getStockQuote(symbol).orElse(null);

        if (quote != null) {
            System.out.println("📈 " + symbol + " 시세 정보:");
            System.out.println("현재가: " + quote.getCurrentPrice());
            System.out.println("시가: " + quote.getOpenPrice());
            System.out.println("고가: " + quote.getHighPrice());
            System.out.println("저가: " + quote.getLowPrice());
            System.out.println("전일 종가: " + quote.getPreviousClosePrice());
        } else {
            System.out.println("데이터를 가져올 수 없습니다.");
        }
    }
}