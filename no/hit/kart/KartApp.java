package no.hit.kart;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Jo on 03.09.2015.
 * Innlevering 1 - Leksjon 3
 */

public class KartApp extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
	// Løst kopiert fra mal leksjon 1
		Group root = new Group();
		Scene scene = new Scene(root,800,600);

		primaryStage.setScene(scene);
		primaryStage.setTitle("KartApp");

		primaryStage.setResizable(false);

		primaryStage.show();

	}
}
