package dad.javafx.components;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestApp extends Application {

	DateChooser date = new DateChooser();
	public void start(Stage primaryStage) throws Exception {

		
		
		Button checkButton = new Button("Inicializar");
		checkButton.setOnAction(e -> onIniciar(e));
		
		Button consultaButton = new Button("Consultar");
		consultaButton.setOnAction(e -> onConsultar(e));
		
		HBox botones = new HBox();
		botones.setSpacing(5);
		botones.setAlignment(Pos.CENTER);
		botones.getChildren().addAll(checkButton,consultaButton);
		
		VBox todo = new VBox();
		todo.setSpacing(5);
		todo.setAlignment(Pos.CENTER);
		todo.getChildren().addAll(date,botones);
		
		BorderPane root = new BorderPane();
		root.setCenter(todo);


		
		Scene scene = new Scene(root,300,200);
		
		primaryStage.setTitle("Probando JDate");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	private void onConsultar(ActionEvent e) {
		System.out.println(date.getDateProperty());
	}

	private void onIniciar(ActionEvent e) {
		date.setDateProperty(LocalDate.now());
	}
	public static void main(String[] args) {
		launch(args);
	}

}
