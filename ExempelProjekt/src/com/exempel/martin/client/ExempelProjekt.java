package com.exempel.martin.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class ExempelProjekt implements EntryPoint {
	private VerticalPanel mainPanel = new VerticalPanel();
	private Grid grid = new Grid(4, 4);
	private HorizontalPanel addPanel = new HorizontalPanel();
	private TextBox operand1TextBox = new TextBox();
	private TextBox operand2TextBox = new TextBox();
	private Button calculateButton = new Button("Calculate");
	private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	private SuggestBox operatorTextBox = new SuggestBox(oracle);
	private Label resultLabel = new Label("0");
	private ArrayList<Button> numBtnList = new ArrayList<Button>(); /* Store Numerical Buttons */
	private ArrayList<Button> opBtnList = new ArrayList<Button>(); /* Store Operator Buttons */
	private FlexTable resultsTable = new FlexTable();
	private int newValue = 0;
	private String preValue = "";
	private String postValue = "";
	private String currentOperator = "";
	private int newRow = 1;
	
	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {
		generateBtns();
		resultLabel.addStyleName("result calc__display");
		resultsTable.setText(0, 0, "Math");
		resultsTable.setText(0, 1, "Result");
		resultsTable.getRowFormatter().addStyleName(0, "resultsListHeader");
		resultsTable.addStyleName("resultsList");
		resultsTable.getCellFormatter().addStyleName(0, 0, "resultsListColumn");
		resultsTable.getCellFormatter().addStyleName(0, 1, "resultsListColumn");
		
		// Set Numericals to Grid
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
		// Set Operators to Grid
		grid.setWidget(0, 3, opBtnList.get(0));
		grid.setWidget(1, 3, opBtnList.get(1));
		grid.setWidget(2, 3, opBtnList.get(2));
		grid.setWidget(3, 3, opBtnList.get(3));
		grid.setWidget(3, 2, opBtnList.get(5));
		grid.setWidget(3, 0, opBtnList.get(6));
		
		// Assemble Main panel.
		mainPanel.add(resultLabel);
		mainPanel.add(grid);
		mainPanel.add(opBtnList.get(4));
		
		addPanel.add(resultsTable);
		// Associate the Main panel with the HTML host page.
		RootPanel.get("calculator").add(mainPanel);
		RootPanel.get("resultsTable").add(addPanel);

		opBtnList.get(4).addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sum();
					Window.alert("Pressed: " + event.getNativeKeyCode());
				}
			}
		});
	}

	private void calculate() {

		final String operator = operatorTextBox.getText().trim();
		calculateButton.setFocus(true);
		if ((!operator.equals("*") && !operator.equals("+") && !operator.equals("%"))
				|| !isInteger(operand1TextBox.getText().trim()) || !isInteger(operand2TextBox.getText().trim())) {
			Window.alert("You have entered a non valid binary operator or one of the operands is not an integer");
			return;
		}

		int operand1 = Integer.parseInt(operand1TextBox.getText());
		int operand2 = Integer.parseInt(operand2TextBox.getText());
		int answer;
		// Multiplication
		if (operator.equals("*")) {
			answer = operand1 * operand2;
			Window.alert("The answer is: " + answer);
		}
		// Modulo
		else if (operator.equals("%")) {
			answer = operand1 % operand2;
			Window.alert("The answer is: " + answer);
		}
		// addition
		else {
			answer = operand1 + operand2;
			Window.alert("The answer is: " + answer);
		}
	}
	
	public void generateBtns() {	
		
		// Generate Numerical Input
		for(int i = 0; i < 10; i++) {
			numBtnList.add(new Button("" + i +""));
			Button currentBtn = numBtnList.get(i);
			final String btnText = currentBtn.getText();
			numBtnList.get(i).addClickHandler(new ClickHandler() {
			
				@Override
				public void onClick(ClickEvent event) {
					if(resultLabel.getText() != "0") {
						resultLabel.setText(resultLabel.getText() + btnText);
					} else {
						resultLabel.setText(btnText);
					}
				}
				
			});
		}
		
		// Generate Operators
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
			opToString = opIndex.getText().toString();
			final String btnText = opToString;
			if((opToString.equals("=") || (opToString.equals("C") || (opToString.equals("\u232B"))))) {
				if(opToString.equals("=")) {
					// Add style to Equals Operator Button
					opIndex.getElement().setId("equals");
					opIndex.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							sum();
						}
						
					});
					
				} else if(opToString.equals("C")) {
					// Add style to Clear Button
					opIndex.getElement().setId("clearResult");
					opIndex.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							resultLabel.setText("0");
						}
						
					});
					
				} else if(opToString.equals("\u232B")) {
					// Add style to RemoveLatest Button
					opIndex.getElement().setId("removeLast");
					opIndex.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							String str = "";
							if (resultLabel.getText() != null && resultLabel.getText().length() > 0) {
						        str = resultLabel.getText().substring(0, resultLabel.getText().length() - 1);
						        resultLabel.setText(str);
						        
						        if(resultLabel.getText() == null || resultLabel.getText() == "" || resultLabel.getText() == "0") {
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
						if(resultLabel.getText() != "0") {
							operatorInput(btnText);
							resultLabel.setText(resultLabel.getText() + btnText);
							
						} else {
							return;
						}
					}
					
				});
			}
		}
		
	}
	
	public void add(String preValue, String postValue) {
		newValue = Integer.parseInt(preValue) + Integer.parseInt(postValue);
	}

	public void sub(String preValue, String postValue) {
		newValue = Integer.parseInt(preValue) - Integer.parseInt(postValue);
	}
	
	public void multiply(String preValue, String postValue) {
		newValue = Integer.parseInt(preValue) * Integer.parseInt(postValue);
	}

	public void divide(String preValue, String postValue) {
		newValue = Integer.parseInt(preValue) / Integer.parseInt(postValue);
	}
	
	public void sum() {
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
		resultLabel.setText("0");
		toResultsTable();
	}
	
	public boolean isOperator() {
		String check = "";		
	    check = resultLabel.getText().substring(0, resultLabel.getText().length() - 1);
		if(check == "+" || check == "-" || check == "*" || check == "/") {
			return true;
		} else {
			return false;
		}
	}
	
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
			sum();
		}
		preValue = resultLabel.getText().substring(0, resultLabel.getText().length());
	}
	
	public void toResultsTable() {
		int row = newRow++;
		resultsTable.setText(row, 0, preValue + " " + currentOperator + " " + postValue);
		resultsTable.setText(row, 1, Integer.toString(newValue));
	}
	
	// Checks if a String could be seen as an integer
	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}