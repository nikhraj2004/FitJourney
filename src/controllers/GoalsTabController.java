package controllers;

import java.io.IOException;
/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.Activity;
import model.Goal;
import model.Helper;
import model.Person;

public class GoalsTabController extends BaseController implements Initializable {

	
	@FXML
	TextField tfCurrentGoal, tfTDEE, tfBMR, tfCaloricReqs;
	@FXML
	Button btnMaintainWeight, btnGainWeight, btnLoseWeight;
	@FXML
	ChoiceBox<Activity> cbActivityLevel;

	
	
	private Goal currentGoal;
	private Activity currentActivity;

	
	private ArrayList<Activity> activities = new ArrayList<Activity>();
	private ArrayList<Goal> goals = new ArrayList<Goal>();

	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		person = Person.getInstance();

		
		setupActivityLevels();
		setupGoals();

		
		calculateValues();
	}

	
	private void setupGoals() {
		
		currentGoal = person.getCurrentGoal();

		
		Goal loseWeight = new Goal("Lose Weight", 0.8);
		Goal maintainWeight = new Goal("Maintain Weight", 1.0);
		Goal gainWeight = new Goal("Gain Weight", 1.2);

		goals.addAll(Arrays.asList(loseWeight, maintainWeight, gainWeight));

		tfCurrentGoal.setText(currentGoal.getName());
	}

	
	private void setupActivityLevels() {

		
		currentActivity = person.getActivity();

		
		Activity sedentary = new Activity("Passive", 1.2);
		Activity lightlyActive = new Activity("Casual Activity", 1.375);
		Activity moderateActive = new Activity("Balanced Movement", 1.55);
		Activity veryActive = new Activity("Highly Energetic", 1.725);
		Activity extremelyActive = new Activity("Intensely Energetic", 1.9);

		
		activities.addAll(Arrays.asList(sedentary, lightlyActive, moderateActive, veryActive, extremelyActive));

		
		cbActivityLevel.setItems(FXCollections.observableArrayList(activities));

		// Set the loaded value of the ChoiceBox to what the Person has set
		cbActivityLevel.setValue(getActivityLevel(person.getActivity()));

		// Add event listener for when we change the ChoiceBox
		ChangeListener<Activity> changeListener = new ChangeListener<Activity>() {
			@Override
			public void changed(ObservableValue<? extends Activity> observable, //
					Activity oldValue, Activity newValue) {
				if (newValue != null) {

					// Set the currentActivity of this page
					currentActivity = newValue;

					// Save it into person
					person.setActivity(currentActivity);

					// Update values
					calculateValues();
				}
			}
		};
		// Selected Item Changed.
		cbActivityLevel.getSelectionModel().selectedItemProperty().addListener(changeListener);
	}

	/**
	 * Retrieves an identical activity (same activity level) from the activities
	 * ArrayList to the persons activity
	 * 
	 * @param ac
	 *            The Activity object we want to check exists
	 * @return Activity object
	 * @throws NullPointerException
	 */
	private Activity getActivityLevel(Activity ac) throws NullPointerException {
		for (int i = 0; i < activities.size(); i++) {
			if (activities.get(i).getActivityLevel() == ac.getActivityLevel()) {
				return activities.get(i);
			}
		}
		return null;
	}

	/**
	 * Retrieves an identical goal object with same names
	 * 
	 * @param name
	 *            of the goal
	 * @return Goal object with same name from goals ArrayList
	 */
	private Goal getGoal(String name) {
		for (int i = 0; i < goals.size(); i++) {
			if (goals.get(i).getName().equals(name)) {
				return goals.get(i);
			}
		}
		return null;
	}

	/**
	 * Calls separate methods to calculate the values of BMR and TDEE as well as
	 * setting the Total Calories required for a Person to reach his Goal, also
	 * updates the TextField with calories goal amount
	 */
	private void calculateValues() {

		// Recalculate and set textfields
		person.calculateBMR();
		tfBMR.setText(Double.toString(Helper.round(person.getBMR(), 2)));

		person.calculateTDEE();
		tfTDEE.setText(Double.toString(Helper.round(person.getTDEE(), 2)));
		
		// Set the new calorie goal for the Person
		person.setGoalCalories(person.getTDEE() * currentGoal.getMultiplier());

		tfCaloricReqs.setText(Double.toString(Helper.round(person.getGoalCalories(), 2)));
	}

	/**
	 * Update method which will update everything inside this controller, this is
	 * used in MainProgramController and called whenever we switch back to this tab
	 */
	public void update() {
		calculateValues();
	}

	/**
	 * Updates the TextField 'tfCurrentGoal' with the newly selected Goal, also
	 * changes the currentGoal object and recalculates BMR and TDEE based off new
	 * goal
	 * 
	 * @param name
	 */
	private void updateGoal(String name) {
		// set the 'current' Goal object of this controller
		currentGoal = getGoal(name);
		
		// Update persons goal
		person.setCurrentGoal(currentGoal);

		// set the TextField value of this current Goal
		tfCurrentGoal.setText(currentGoal.getName());

		// update the values of BMR and TDEE based of changes
		calculateValues();
	}

	/***********************************************************************
	 * FXML button handlers
	 * 
	 * FXML buttons which will call 'updateGoal' and change the Goal Type
	 *********************************************************************/
	@FXML
	protected void handleLoseWeight(ActionEvent event) throws IOException {
		updateGoal("Lose Weight");
	}

	@FXML
	protected void handleGainWeight(ActionEvent event) throws IOException {
		updateGoal("Gain Weight");
	}

	@FXML
	protected void handleMaintainWeight(ActionEvent event) throws IOException {
		updateGoal("Maintain Weight");
	}
}