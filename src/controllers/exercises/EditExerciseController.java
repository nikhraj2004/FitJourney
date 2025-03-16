package controllers.exercises;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Exercise;

public class EditExerciseController extends BaseExerciseController implements Initializable {

	@FXML
	TextField tfSets, tfReps, tfWeight, tfCaloriesBurned;
	
	@FXML
	Button btnSave;
	
	
	private Exercise editExercise;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public void setEditExercise(Exercise edit) {
		this.editExercise = edit;
		
		String[] vals = edit.getStrVals();
		
		
		tfSets.setText(vals[0]);
		tfReps.setText(vals[1]);
		tfWeight.setText(vals[2]);
		tfCaloriesBurned.setText(vals[3]);
		
	}
	
	public Exercise getEditExercise() {
		return this.editExercise;
	}
	
	@FXML
	protected void handleSave(ActionEvent event) throws IOException {
		try {
		
			
			
			editExercise = createExercise(editExercise);
			
			
			System.out.println("New weight values: " + editExercise.getWeight());
			
			
			btnSave.getScene().getWindow().hide();
		} catch (NullPointerException e) {
			System.out.println("A table row wasn't selected");
		} catch(NumberFormatException e) {
			System.out.println("couldn't convert text to numbers");
		}
	}
}
