package com.exempel.gustav.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;

public class ButtonHandler {

	private CalculatorController calcControl;
	private ArrayList<Button> numBtnList = new ArrayList<Button>(); /* Store Numerical Buttons */
	private ArrayList<Button> opBtnList = new ArrayList<Button>(); /* Store Operator Buttons */
	private TextBox display;
	
	public ButtonHandler(CalculatorController calcControl) {
		this.calcControl = calcControl;
	}
	
	/* Generate Buttons */
	public void generateBtns() {	
		/* Generate Numerical Input */
		for(int i = 0; i < 10; i++) {
			numBtnList.add(new Button("" + i +""));
			Button currentBtn = numBtnList.get(i);
			final String btnText = currentBtn.getText();
			numBtnList.get(i).addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					display = calcControl.getResultTextBox();
					if(display.getText() != "0") {
						display.setText(display.getText() + btnText);
					} else {
						display.setText(btnText);
					}
					display.setFocus(true);
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
							display = calcControl.getResultTextBox();
							calcControl.splitFromString(display.getText());
							calcControl.getCalculator().calculate(calcControl.getPreValue(), calcControl.getPostValue());
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
							display = calcControl.getResultTextBox();
							display.setText("0");
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
							display = calcControl.getResultTextBox();
							String str = "";
							if (display.getText() != null && display.getText().length() > 0) {
						        str = display.getText().substring(0, display.getText().length() - 1);
						        display.setText(str);
						        
						        if(calcControl.isEmpty(display.getText())) {
									display.setText("0");
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
						display = calcControl.getResultTextBox();
						if(display.getText() != "0") {
							calcControl.operatorInput(btnText);
							if(calcControl.checkIfOperator(display.getText().substring(display.getText().length() - 1))) {
								display.setText(display.getText().substring(0, display.getText().length() - 1) + btnText);
							} else {
								display.setText(display.getText() + btnText);
							}
						} else {
							return;
						}
					}
				});
			}
			display = calcControl.getResultTextBox();
			display.setFocus(true);
		}
	}
	/* End of generateButton() */
	
	/* GETTERS & SETTERS BELOW */
	public ArrayList<Button> getNumBtnList() {
		return numBtnList;
	}

	public void setNumBtnList(ArrayList<Button> numBtnList) {
		this.numBtnList = numBtnList;
	}

	public ArrayList<Button> getOpBtnList() {
		return opBtnList;
	}

	public void setOpBtnList(ArrayList<Button> opBtnList) {
		this.opBtnList = opBtnList;
	}
}