
import java.util.Locale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * (c) zEndurance 2018
 *
 * @author Anmol <anmolguptaran@gmail.com>
 */
public class App extends Application {

	// ===========================================================
	// Methods
	// ===========================================================

	public static void main(String[] args) {
		Locale.setDefault(Locale.UK);
		Application.launch(App.class, args);
	}

	@Override
	public void start(Stage appStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
		appStage.setTitle("FitJourney");
		appStage.setScene(new Scene(root, 944, 600));
		appStage.show();
	}
}
