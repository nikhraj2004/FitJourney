package model;

public class Person {

	private static Person instance = new Person();

	/*
	 * Keep track of users: age, height, weight
	 */
	private Goal currentGoal = new Goal("Maintain Weight", 1.0);
	private Activity activity = new Activity("Sedentary", 1.2);
	
	private String firstName = "Anmol";
	private String lastName = "User";
	private String units = "Metric";
	private String gender = "Male";
	private int age = 18;
	private double weight = 65;
	private double height = 165;
	private double bodyfat = 3.50;
	private double waist = 85;
	private double BMI = 0;
	private double BMR = 0;
	private double TDEE = 0;
	private  double goalCalories = 2169;
	
	/* Singleton class */
	private Person() {

	}
	
	
	
	public void calculateBMR() {
		
		if (getGender().equals("Male")) {
			// Check what units we are in and convert to Metric
			if (getUnits().equals("Imperial")) {
				// Use the converted BMR
				setBMR(66
						+ (13.75 * (getWeight() / 2.20462) + (5 * (getHeight() / 0.0328084)) - (6.8 * getAge())));
			} else {
				setBMR(66 + (13.75 * getWeight()) + (5 * getHeight()) - (6.8 * getAge()));
			}

		} else if (getGender().equals("Female")) {
			if (getUnits().equals("Imperial")) {
				// Use converted BMR to metric
				setBMR(665
						+ (9.6 * (getWeight() / 2.20462) + (1.8 * (getHeight() / 0.0328084)) - (4.7 * getAge())));
			} else {
				setBMR(655 + (9.6 * getWeight()) + (1.8 * getHeight()) - (4.7 * getAge()));
			}

		}
	}

	
	public void calculateTDEE() {
		this.setTDEE(getBMR() * getActivity().getActivityLevel());
	}
	
	
	
	static {
		try {
			instance = new Person();
		} catch (Exception e) {
			throw new RuntimeException("Exception occured in creating singleton user instance");
		}
	}
	
	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity= activity;
	}

	public void setBodyfat(double bodyfat) {
		
		validateValue(bodyfat);
		
		
		if(bodyfat > 1.0) throw new IllegalArgumentException("Illegal bodyfat set!");
		
		this.bodyfat = bodyfat;
	}

	public void setWaist(double waist) {
		this.waist = waist;
	}
	
	public void setTDEE(double value) {
		this.TDEE = value;
	}

	public double getTDEE() {
		return TDEE;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public static Person getInstance() {
		return instance;
	}

	public int getAge() {
		return age;
	}
	
	private void validateValue(double value) {
		if(value < 0) throw new IllegalArgumentException("Negative value set!");
	}

	public void setAge(int age) {
		validateValue(age);
		this.age = age;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		validateValue(weight);
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		validateValue(height);
		this.height = height;
	}

	public void setGender(String gender) {
		
		if(!gender.equals("Male") && !gender.equals("Female")) throw new IllegalArgumentException("Illegal gender set!");
		
		this.gender = gender;
		
		
		
		updatePerson();
	}
	
	private void updatePerson() {
		calculateBMR();
		calculateTDEE();
		
		setGoalCalories(getTDEE() * currentGoal.getMultiplier());
	}

	public double getBodyfat() {
		return bodyfat;
	}

	public double getWaist() {
		return waist;
	}

	public double getGoalCalories() {
		return goalCalories;
	}
	
	public void setGoalCalories(double value) {
		validateValue(value);
		this.goalCalories = value;
	}

	public Goal getCurrentGoal() {
		return currentGoal;
	}

	public void setCurrentGoal(Goal currentGoal) {
		this.currentGoal = currentGoal;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public double getBMR() {
		return BMR;
	}

	public void setBMR(double bMR) {
		BMR = bMR;
	}

	public double getBMI() {
		return BMI;
	}

	public void setBMI(double bMI) {
		BMI = bMI;
	}
}
