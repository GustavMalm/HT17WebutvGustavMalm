package com.exempel.martin.client;

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

public class ExempelProjekt implements EntryPoint {
	private VerticalPanel mainPanel = new VerticalPanel();
	private Grid grid = new Grid(4, 4);
	private HorizontalPanel resultsPanel = new HorizontalPanel();
	private TextBox resultLabel = new TextBox();
	private ArrayList<Button> numBtnList = new ArrayList<Button>(); /* Store Numerical Buttons */
	private ArrayList<Button> opBtnList = new ArrayList<Button>(); /* Store Operator Buttons */
	private FlexTable resultsTable = new FlexTable();
	private float newValue;
	private String preValue = "";
	private String postValue = "";
	private String currentOperator = "";
	private int newRow = 1;
	private boolean isZeroByCalc;
	
	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {
		initDefaultGUI();
		
		/* Assemble Main panel. */
		mainPanel.add(resultLabel);
		mainPanel.add(grid);
		mainPanel.add(opBtnList.get(4));
		
		resultsPanel.add(resultsTable);
		
		/* Associate the Main panel with the HTML host page. */
		RootPanel.get("calculator").add(mainPanel);
		RootPanel.get("resultsTable").add(resultsPanel);

	}
	
	public void initDefaultGUI() {
		generateBtns();
		
		resultLabel.addStyleName("result calc__display");
		resultLabel.setReadOnly(true);
		resultLabel.setFocus(true);
		resultLabel.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				event.preventDefault();
				if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					opBtnList.get(4).click(); /* Equals Button */
				}
				
				if (event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE) {
					opBtnList.get(6).click(); /* Remove Last */
				}
				
			}
			
		});
		
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
			numBtnList.get(i).setFocus(false);
			numBtnList.get(i).addClickHandler(new ClickHandler() {
			
				@Override
				public void onClick(ClickEvent event) {
					if(resultLabel.getText() != "0") {
						resultLabel.setText(resultLabel.getText() + btnText);
					} else {
						resultLabel.setText(btnText);
					}
					resultLabel.setFocus(true);
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
							resultLabel.setText("0");
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
							if (resultLabel.getText() != null && resultLabel.getText().length() > 0) {
						        str = resultLabel.getText().substring(0, resultLabel.getText().length() - 1);
						        resultLabel.setText(str);
						        
						        if(isEmpty(resultLabel.getText())) {
									resultLabel.setText("0");
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
						if(resultLabel.getText() != "0") {
							operatorInput(btnText);
							checkIfDuplicate();
							resultLabel.setText(resultLabel.getText() + btnText);
							
						} else {
							return;
						}
					}
					
				});
			}
			resultLabel.setFocus(true);
		}
		
	}
	/* End of generateButton() */
	
	/* Addition */
	public void add(String preValue, String postValue) {
		newValue = Float.parseFloat(preValue) + Float.parseFloat(postValue);
		isZeroByCalc(newValue);
	}

	/* Subtraction */
	public void sub(String preValue, String postValue) {
		newValue = Float.parseFloat(preValue) - Float.parseFloat(postValue);
		isZeroByCalc(newValue);
	}
	
	/* Multiplication */
	public void multiply(String preValue, String postValue) {
		newValue = Float.parseFloat(preValue) * Float.parseFloat(postValue);
		isZeroByCalc(newValue);
	}

	/* Division */
	public void divide(String preValue, String postValue) {
		newValue = Float.parseFloat(preValue) / Float.parseFloat(postValue);
		isZeroByCalc(newValue);
	}
	
	/* Makes Calculations depending of the currentOperator active */
	public void calculate() {
		postValue = resultLabel.getText().substring(preValue.length() + 1);
		if (currentOperator == "+") {
			add(preValue, postValue);
		} else if (currentOperator == "-") {
			sub(preValue, postValue);
		} else if (currentOperator == "*") {
			multiply(preValue, postValue);
		} else if (currentOperator == "/") {
			divide(preValue, postValue);
		}
		
		if(!isEmpty(resultLabel.getText())) {
			toResultsTable();
		}
		resultLabel.setText("0");
		
	}
	
	/* Checks wheter last character in a String is a Operator or Not. [Not used as for now] */
	public boolean isOperator() {
		String check = "";		
	    check = resultLabel.getText().substring(0, resultLabel.getText().length() - 1);
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
		} else if (btnText.equals("=")) {
			if (postValue == "") {
				Window.alert("Du måste ha en komplett uträkning! Exempel 1+1, 50-5 osv...");
				return;
			}		
			calculate();
		}
		preValue = resultLabel.getText().substring(0, resultLabel.getText().length());
	}
	
	/* Uses a Row count in order to put the results at the appropriate level of the table */
	public void toResultsTable() {
		int row = newRow++;
		if(!isEmpty(preValue) && !isEmpty(currentOperator)) {
			resultsTable.setText(row, 0, preValue + " " + currentOperator + " " + postValue);
			if(isZeroByCalc || !isEmpty(resultLabel.getText())) {
				resultsTable.setText(row, 1, Float.toString(newValue));
			}
		} else {
			Window.alert("Syntax Error: Please check your inputs");
		}
	}
	
	public void checkIfDuplicate() {
		String s = resultLabel.getText();
		s = s.replace("+", "");
		s = s.replace("-", "");
		s = s.replace("*", "");
		s = s.replace("/", "");
		
		resultLabel.setText(s);
	}
	
	/* Checks if String is empty or not defined */
	public boolean isEmpty(String s) {
		if(s.equals("") || s.equals("0") || s.equals(null)) {
			return true;
		}
		return false;
	}
	
	/* Checks if value is 0 by Calculation or not [For allowing result to be 0 but not if not through calculation] */
	public boolean isZeroByCalc(Float value) {
		if(value == 0) {
			isZeroByCalc = true;
		} else if(value == 0 && postValue.equals("0")) {
			isZeroByCalc = true;
		} else {
			isZeroByCalc = false;
		}
		return isZeroByCalc;
	}
	
	/* Checks if a String could be seen as an integer */
	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}