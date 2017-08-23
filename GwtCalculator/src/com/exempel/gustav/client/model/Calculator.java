package com.exempel.gustav.client.model;

import com.exempel.gustav.client.CalculatorController;

public class Calculator {

	CalculatorController calcControl;
	private float sum;
	
	public Calculator(CalculatorController calcControl) {
		this.calcControl = calcControl;
	}

	/* Addition */
	public void add(Float preValue, Float postValue) {
		sum = preValue + postValue;
		calcControl.isZeroByCalc(sum);
	}

	/* Subtraction */
	public void sub(Float preValue, Float postValue) {
		sum = preValue - postValue;
		calcControl.isZeroByCalc(sum);
	}

	/* Multiplication */
	public void multiply(Float preValue, Float postValue) {
		sum = preValue * postValue;
		calcControl.isZeroByCalc(sum);
	}

	/* Division */
	public void divide(Float preValue, Float postValue) {
		sum = preValue / postValue;
		calcControl.isZeroByCalc(sum);
	}

	/* Makes Calculations depending of the currentOperator active */
	public void calculate(Float preValue, Float postValue) {
		String currentOperator = calcControl.getCurrentOperator();

		if (currentOperator == "+") {
			add(preValue, postValue);
		} else if (currentOperator == "-") {
			sub(preValue, postValue);
		} else if (currentOperator == "*") {
			multiply(preValue, postValue);
		} else if (currentOperator == "/") {
			divide(preValue, postValue);
		}

		if (!calcControl.isEmpty(calcControl.getResultTextBox().getText())) {
			calcControl.toResultsTable();
		}

		calcControl.getResultTextBox().setText("0");
	}
	
	/* GETTERS & SETTERS BELOW */
	public float getSum() {
		return sum;
	}

	public void setSum(float sum) {
		this.sum = sum;
	}
}