package dad.javafx.components;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		
		BorderPane root = new BorderPane();
		root.setCenter(new DateChooser());
		
		Scene scene = new Scene(root,600,400);
		
		primaryStage.setTitle("Custom com");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	public static void main(String[] args) {
		launch(args);
	}

}
