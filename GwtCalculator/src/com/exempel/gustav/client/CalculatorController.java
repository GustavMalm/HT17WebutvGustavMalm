package com.exempel.gustav.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class CalculatorController implements EntryPoint {
	private VerticalPanel mainPanel = new VerticalPanel();
	private Grid grid = new Grid(4, 4);
	private HorizontalPanel resultsPanel = new HorizontalPanel();
	private TextBox resultTextBox = new TextBox();
	private ArrayList<Button> numBtnList = new ArrayList<Button>(); /* Store Numerical Buttons */
	private ArrayList<Button> opBtnList = new ArrayList<Button>(); /* Store Operator Buttons */
	private FlexTable resultsTable = new FlexTable();
	private float newValue;
	private float preValue;
	private float postValue;
	private String currentOperator = "";
	private int newRow = 1;
	private boolean isZeroByCalc;
	
	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {
		initDefaultGUI();
		
		/* Assemble Main panel. */
		mainPanel.add(resultTextBox);
		mainPanel.add(grid);
		mainPanel.add(opBtnList.get(4));
		
		resultsPanel.add(resultsTable);
		
		/* Associate the Main panel with the HTML host page. */
		RootPanel.get("calculator").add(mainPanel);
		RootPanel.get("resultsTable").add(resultsPanel);

	}
	
	public void initDefaultGUI() {
		generateBtns();
		
		resultTextBox.setText("0");
		resultTextBox.addStyleName("result calc__display");
		resultTextBox.setReadOnly(true);
		resultTextBox.setFocus(true);
		resultTextBox.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				event.preventDefault();
				
				/* Add onKeyDown event to Numerical Buttons (Looking for a better solution) */
				int keyCode = event.getNativeKeyCode();
				if(keyCode == KeyCodes.KEY_NUM_ZERO) {
					numBtnList.get(0).click();
				} else if(keyCode == KeyCodes.KEY_NUM_ONE) {
					numBtnList.get(1).click();
				} else if(keyCode == KeyCodes.KEY_NUM_TWO) {
					numBtnList.get(2).click();
				} else if(keyCode == KeyCodes.KEY_NUM_THREE) {
					numBtnList.get(3).click();
				} else if(keyCode == KeyCodes.KEY_NUM_FOUR) {
					numBtnList.get(4).click();
				} else if(keyCode == KeyCodes.KEY_NUM_FIVE) {
					numBtnList.get(5).click();
				} else if(keyCode == KeyCodes.KEY_NUM_SIX) {
					numBtnList.get(6).click();
				} else if(keyCode == KeyCodes.KEY_NUM_SEVEN) {
					numBtnList.get(7).click();
				} else if(keyCode == KeyCodes.KEY_NUM_EIGHT) {
					numBtnList.get(8).click();
				} else if(keyCode == KeyCodes.KEY_NUM_NINE) {
					numBtnList.get(9).click();
				}
				
				/* Add onKeyDown event to Operator Buttons (Looking for a better solution) */
				if(keyCode == KeyCodes.KEY_NUM_PLUS) {
					opBtnList.get(0).click();
				} else if(keyCode == KeyCodes.KEY_NUM_MINUS) {
					opBtnList.get(1).click();
				}  else if(keyCode == KeyCodes.KEY_NUM_MULTIPLY) {
					opBtnList.get(2).click();
				} else if(keyCode == KeyCodes.KEY_NUM_DIVISION) {
					opBtnList.get(3).click();
				} 
							
				if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					opBtnList.get(4).click(); /* Equals Button */
				}
				
				if (event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE) {
					opBtnList.get(6).click(); /* Remove Last */
				}
				
				if (event.getNativeKeyCode() == KeyCodes.KEY_C || event.getNativeKeyCode() == KeyCodes.KEY_NUM_PERIOD) {
					opBtnList.get(5).click(); /* Clear Display */
				}			
			}
		});
		
		/* Set-Up FlexTable */
		resultsTable.setText(0, 0, "Math");
		resultsTable.setText(0, 1, "Result");
		resultsTable.getRowFormatter().addStyleName(0, "resultsListHeader");
		resultsTable.addStyleName("resultsList");
		resultsTable.getCellFormatter().addStyleName(0, 0, "resultsListColumn");
		resultsTable.getCellFormatter().addStyleName(0, 1, "resultsListColumn");
		
		/* Set Numericals to Grid */
		grid.setWidget(0, 0, numBtnList.get(7));
		grid.setWidget(0, 1, numBtnList.get(8));
		grid.setWidget(0, 2, numBtnList.get(9));
		grid.setWidget(1, 0, numBtnList.get(4));
		grid.setWidget(1, 1, numBtnList.get(5));
		grid.setWidget(1, 2, numBtnList.get(6));
		grid.setWidget(2, 0, numBtnList.get(1));
		grid.setWidget(2, 1, numBtnList.get(2));
		grid.setWidget(2, 2, numBtnList.get(3));
		grid.setWidget(3, 1, numBtnList.get(0));
		
		/* Set Operators to Grid */
		grid.setWidget(0, 3, opBtnList.get(0));
		grid.setWidget(1, 3, opBtnList.get(1));
		grid.setWidget(2, 3, opBtnList.get(2));
		grid.setWidget(3, 3, opBtnList.get(3));
		grid.setWidget(3, 2, opBtnList.get(5));
		grid.setWidget(3, 0, opBtnList.get(6));
	}
	
	public void generateBtns() {	
		/* Generate Numerical Input */
		for(int i = 0; i < 10; i++) {
			numBtnList.add(new Button("" + i +""));
			Button currentBtn = numBtnList.get(i);
			final String btnText = currentBtn.getText();
			numBtnList.get(i).addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if(resultTextBox.getText() != "0") {
						resultTextBox.setText(resultTextBox.getText() + btnText);
					} else {
						resultTextBox.setText(btnText);
					}
					resultTextBox.setFocus(true);
				}
			});
		}
		
		/* Generate Operators */
		opBtnList.add(new Button("+"));
		opBtnList.add(new Button("-"));
		opBtnList.add(new Button("*"));
		opBtnList.add(new Button("/"));
		opBtnList.add(new Button("="));
		opBtnList.add(new Button("C"));
		opBtnList.add(new Button("\u232B")); /* Symbol of Unicode Character 'ERASE TO THE LEFT' (U+232B) (Simulates delete last input) */
		
		
		/* Add style to Numerical Buttons */
		for(Button numIndex : numBtnList) {
			numIndex.setStyleName("calc__number");
		}

		/* Add style to Operator Buttons */
		for(Button opIndex : opBtnList) {
			String opToString = "";
			opToString = opIndex.getText();
			final String btnText = opToString;
			if((opToString.equals("=") || (opToString.equals("C") || (opToString.equals("\u232B"))))) {
				if(opToString.equals("=")) {
					/* Add style to Equals Operator Button */
					opIndex.getElement().setId("equals");
					opIndex.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							event.preventDefault();
							calculate();
						}
					});
				} else if(opToString.equals("C")) {
					/* Add style to Clear Button */
					opIndex.getElement().setId("clearResult");
					opIndex.setTitle("Clear Display");
					opIndex.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							event.preventDefault();
							resultTextBox.setText("0");
						}
					});
				} else if(opToString.equals("\u232B")) {
					/* Add style to RemoveLatest Button */
					opIndex.getElement().setId("removeLast");
					opIndex.setTitle("Remove last character");
					opIndex.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							event.preventDefault();
							String str = "";
							if (resultTextBox.getText() != null && resultTextBox.getText().length() > 0) {
						        str = resultTextBox.getText().substring(0, resultTextBox.getText().length() - 1);
						        resultTextBox.setText(str);
						        
						        if(isEmpty(resultTextBox.getText())) {
									resultTextBox.setText("0");
								}
						    }
						}				
					});
				}
			} else {
				opIndex.setStyleName("calc__sign");
				opIndex.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						event.preventDefault();
						if(resultTextBox.getText() != "0") {
							operatorInput(btnText);
							if(checkIfOperator(resultTextBox.getText().substring(resultTextBox.getText().length() - 1))) {
								resultTextBox.setText(resultTextBox.getText().substring(0, resultTextBox.getText().length() - 1) + btnText);
							} else {
								resultTextBox.setText(resultTextBox.getText() + btnText);
							}
						} else {
							return;
						}
					}
				});
			}
			resultTextBox.setFocus(true);
		}
	}
	/* End of generateButton() */
	
	/* Addition */
	public void add() {
		newValue = preValue + postValue;
		isZeroByCalc(newValue);
	}

	/* Subtraction */
	public void sub() {
		newValue = preValue - postValue;
		isZeroByCalc(newValue);
	}
	
	/* Multiplication */
	public void multiply() {
		newValue = preValue * postValue;
		isZeroByCalc(newValue);
	}

	/* Division */
	public void divide() {
		newValue = preValue / postValue;
		isZeroByCalc(newValue);
	}
	
	/* Makes Calculations depending of the currentOperator active */
	public void calculate() {
		splitFromString(resultTextBox.getText());
		
		if (currentOperator == "+") {
			add();
		} else if (currentOperator == "-") {
			sub();
		} else if (currentOperator == "*") {
			multiply();
		} else if (currentOperator == "/") {
			divide();
		}
				
		if(!isEmpty(resultTextBox.getText())) {
			toResultsTable();
		}
		
		resultTextBox.setText("0");
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
	    check = resultTextBox.getText().substring(0, resultTextBox.getText().length() - 1);
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
			resultsTable.setText(row, 0, preValue + " " + currentOperator + " " + postValue);
			if(isZeroByCalc || !isEmpty(resultTextBox.getText())) {
				resultsTable.setText(row, 1, Float.toString(newValue));
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
}