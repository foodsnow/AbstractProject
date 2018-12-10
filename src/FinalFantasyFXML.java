import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FinalFantasyFXML extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FINAL_FANTASY_FXML.fxml"));

        Scene s = new Scene(root);
        primaryStage.setScene(s);
        primaryStage.setFullScreen(true);

        primaryStage.show();
    }
}
