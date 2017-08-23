package com.exempel.gustav.client.view;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CalculatorGUI {
	private FlexTable resultsTable = new FlexTable();
	private VerticalPanel mainPanel = new VerticalPanel();
	private Grid grid = new Grid(4, 4);
	private HorizontalPanel resultsPanel = new HorizontalPanel();
	private TextBox resultTextBox = new TextBox();
	private ArrayList<Button> numBtnList; /* Store Numerical Buttons */
	private ArrayList<Button> opBtnList; /* Store Operator Buttons */
	
	public CalculatorGUI(ArrayList<Button> numBtnList, ArrayList<Button> opBtnList) {
		this.numBtnList = numBtnList;
		this.opBtnList = opBtnList;
	}
	
	public void initDefaultGUI() {
		resultTextBox.setText("0");
		resultTextBox.getElement().setAttribute("max-length", "25"); /* Sets the max-length for the TextBox */
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
		
		/* Assemble Main panel. */
		mainPanel.add(resultTextBox);
		mainPanel.add(grid);
		mainPanel.add(opBtnList.get(4));
		
		resultsPanel.add(resultsTable);
		
		/* Associate the Main panel with the HTML host page. */
		RootPanel.get("calculator").add(mainPanel);
		RootPanel.get("resultsTable").add(resultsPanel);
	}
	
	/* GETTERS & SETTERS BELOW */
	public TextBox getResultTextBox() {
		return resultTextBox;
	}

	public void setResultTextBox(TextBox resultTextBox) {
		this.resultTextBox = resultTextBox;
	}
	
	public FlexTable getResultsTable() {
		return resultsTable;
	}

	public void setResultsTable(FlexTable resultsTable) {
		this.resultsTable = resultsTable;
	}
}