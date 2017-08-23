package com.exempel.gustav.client;

import com.exempel.gustav.client.model.Calculator;
import com.exempel.gustav.client.view.CalculatorGUI;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class CalculatorController implements EntryPoint {
	private Calculator calculator;
	private CalculatorGUI calculatorGUI;
	private ButtonHandler btnHandler;
	private float preValue;
	private float postValue;
	private String currentOperator = "";
	private int newRow = 1;
	private boolean isZeroByCalc;
	
	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {
		init();
		btnHandler.generateBtns();
		calculatorGUI.initDefaultGUI();
	}
		
	private void init() {
		calculator = new Calculator(this);
		btnHandler = new ButtonHandler(this);
		calculatorGUI = new CalculatorGUI(btnHandler.getNumBtnList(), btnHandler.getOpBtnList());
	}
	
	/* Splits a String into a array of Strings then parses the values to Float */
	public void splitFromString(String s) {
		String[] results = s.split("[" + currentOperator + "]");

		preValue = Float.parseFloat(results[0]);
		postValue = Float.parseFloat(results[1]);
	}
	
	/* Checks wheter last character in a String is a Operator or Not. [Not used as for now] */
	public boolean isOperator() {
		String check = "";		
	    check = getResultTextBox().getText().substring(0, getResultTextBox().getText().length() - 1);
		if(check == "+" || check == "-" || check == "*" || check == "/") {
			return true;
		} else {
			return false;
		}
	}
	
	/* Checks which operator has been press and set the currentOperator to the value of the Button */
	public void operatorInput(String btnText) {
		if (btnText.equals("+")) {
			currentOperator = "+";
		} else if (btnText.equals("-")) {
			currentOperator = "-";
		} else if (btnText.equals("*")) {
			currentOperator = "*";
		} else if (btnText.equals("/")) {
			currentOperator = "/";
		} 
	}
	
	/* Uses a Row count in order to put the results at the appropriate level of the table */
	public void toResultsTable() {
		int row = newRow++;
		if(!isEmpty(currentOperator)) {
			getResultsTable().setText(row, 0, preValue + " " + currentOperator + " " + postValue);
			if(isZeroByCalc || !isEmpty(getResultTextBox().getText())) {
				getResultsTable().setText(row, 1, Float.toString(calculator.getSum()));
			}
		} else {
			Window.alert("Syntax Error: Please check your inputs");
		}
	}
	
	/* Checks if a String contains an Operator and returns the result */
	public boolean checkIfOperator(String string) {
		if(string.equals("+") || string.equals("-") || string.equals("*") || string.equals("/")) {
			return true;
		}
		return false;
	}
	
	/* Checks if String is empty or not defined */
	public boolean isEmpty(String s) {
		if(s.equals("") || s.equals("0") || s.equals(null)) {
			return true;
		}
		return false;
	}
	
	/* Checks if value is 0 by Calculation or not [For allowing result to be 0 except if not through calculation] */
	public boolean isZeroByCalc(Float value) {
		if(value == 0) {
			isZeroByCalc = true;
		} else if(value == 0 && postValue == 0) {
			isZeroByCalc = true;
		} else {
			isZeroByCalc = false;
		}
		return isZeroByCalc;
	}
	
	/* GETTERS & SETTERS BELOW */
	public String getCurrentOperator() {
		return currentOperator;
	}

	public void setCurrentOperator(String currentOperator) {
		this.currentOperator = currentOperator;
	}

	public Calculator getCalculator() {
		return calculator;
	}
	
	public float getPreValue() {
		return preValue;
	}

	public void setPreValue(float preValue) {
		this.preValue = preValue;
	}

	public float getPostValue() {
		return postValue;
	}

	public void setPostValue(float postValue) {
		this.postValue = postValue;
	}
	
	public TextBox getResultTextBox() {
		return calculatorGUI.getResultTextBox();
	}
	
	public FlexTable getResultsTable() {
		return calculatorGUI.getResultsTable();
	}
}