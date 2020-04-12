package com.joshuakaplan.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.*;


/*
* DISCLAIMER: This calculator works great up to a length of 15 elements.
* Anything past 15 will not work. Designed for simple calculations... for I have a simple
* understanding of programming.
* -Joshua Kaplan */

public class MainActivity extends AppCompatActivity {
    private final int MAX_LENGTH = 15;

    private ButtonStrings bs;

    private double total = 0; // set equal to LONG and refactor
    private String operandHolder = "";
    private boolean clearNext = false;
    private boolean isFirst = true;
    private boolean isOperandSelected = false;
    private boolean isTotalDisplayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bs = new ButtonStrings(getApplicationContext());
    }

    public void addToCalcScreen(String n) {
        if (checkCalcScreenLength(false)) {
            TextView calcScreen = findViewById(R.id.calcScreen);
            calcScreen.append(n);
        }
    }

    public void buttonClickNumber(View view) {
        Button b = (Button) view;
        checkClearNext();
        isOperandSelected = false;
        addToCalcScreen(b.getText().toString());
    }

    public void buttonClickDot(View view) {
        Button b = (Button) view;
        checkClearNext();
        isOperandSelected = false;
        if (!getCalcScreenNumber().contains(bs.getDot())) {
            addToCalcScreen(b.getText().toString());
        }
    }

    public void buttonClickNegative(View view) {
        checkClearNext();
        isOperandSelected = false;
        String display = getCalcScreenNumber();

        if (getCalcScreenNumber().contains("-")) {
            display = display.replace("-", "");
        } else if (getCalcScreenNumber().isEmpty()) {
            display = "-";
        } else {
            display = "-" + display;
        }

        setCalcScreenNumberWithLiteral(display);
    }

    public void buttonClickEquals(View view) {
        String n = getCalcScreenNumber();

        resetOperandColors();

        if (!isOperandSelected) {
            if (isFirst) {
                handleFirstEquals(n);
            } else {
                handleNextEquals(n);
            }
        }

    }

    public void handleNextEquals(String n) {
        if (!checkError(n)) {
            handleOperation(n);
            displayTotal();
            setStateVariables(this.total, true, true, "", false, isTotalDisplayed);
        }
    }

    public void handleFirstEquals(String n) {
        if (!checkError(n)) {
            setStateVariables(Double.parseDouble(n), true, true, "", false, isTotalDisplayed);
            displayTotal();
        }
    }

    // returns true/false AND displays error on screen if true;
    public boolean checkError(String n) {
        if (n.isEmpty() ||
                n.equals(bs.getDot()) ||
                n.equals("-") ||
                n.equals("-.") ||
                n.equals("ERROR")) {
            displayError();
            return true;
        }

        return false;
    }

    public void displayError() {
        setCalcScreenNumberWithLiteral("ERROR");
        setStateVariables(0, true, true, "", false, false);
    }

    public void buttonClickOperand(View view) {
        Button b = (Button) view;
        String n = getCalcScreenNumber();

        if (!isOperandSelected) {
            if (!checkError(n)) {
                clearNext = true; // next input clears the screen

                if (isFirst) {
                    handleFirst(b, n);
                } else {
                    handleNext(b, n);
                }
            }
        } else {
            operandHolder = b.getText().toString();
        }

        highlightHeldOperand();
    }

    public void buttonClickBackspace(View view) {
        isOperandSelected = false;
        clearNext = false;


        if (!backspaceButtonExceptions(view)) {
            deleteLastChar();
        }
    }

    public void deleteLastChar() {
        StringBuilder sb = new StringBuilder(getCalcScreenNumber());
        sb.deleteCharAt(sb.length() - 1);
        setCalcScreenNumberWithLiteral(sb.toString());
    }

    public boolean backspaceButtonExceptions(View view) {
        String num = getCalcScreenNumber();

        // if calcScreen is empty
        if (num.isEmpty()) {
            return true;
        }

        // if total is displayed, and they press backspace, just reset everything
        if (isTotalDisplayed) {
            clearCalcScreen(view);
            return true;
        }

        if (hasAlpha(num)) {
            clearCalcScreen(view);
            return true;
        }

        return false;
    }

    public boolean hasAlpha(String str) {
        return Pattern.matches("[A-Za-z]+", str);
    }

    public void handleFirst(Button b, String n) {
        //                  total                        isFirst      clearNext        operandHolder
        setStateVariables(Double.parseDouble(n), false, this.clearNext, b.getText().toString(), true, isTotalDisplayed);
    }

    public void handleNext(Button b, String n) {
        handleOperation(n); // operates on total w/ held operand
        displayTotal(); // calcScreen updated
        operandHolder = b.getText().toString(); // operandHolder stores currently selected operand
        isOperandSelected = true;
    }

    // operates on total w/ held operand
    public void handleOperation(String n) {
        Double num = Double.parseDouble(n);

        if (operandHolder.equals(bs.getAdd())) {
            add(num);
        } else if (operandHolder.equals(bs.getSubtract())) {
            subtract(num);
        } else if (operandHolder.equals(bs.getMultiply())) {
            multiply(num);
        } else if (operandHolder.equals(bs.getDivide())) {
            divide(num);
        }
    }

    public void add(Double num) {
        total += num;
    }

    public void subtract(Double num) {
        total -= num;
    }

    public void multiply(Double num) {
        total *= num;
    }

    public void divide(Double num) {
        total /= num;
    }

    public String getCalcScreenNumber() {
        TextView calcScreen = findViewById(R.id.calcScreen);
        return calcScreen.getText().toString();
    }

    public void setCalcScreenNumber(String number) {
            TextView calcScreen = findViewById(R.id.calcScreen);

            String display = number;
            double num = Double.parseDouble(number);

            if (num % 1 == 0) {
                display = Long.toString((long) num);
            }


            calcScreen.setText(checkStringLengthAndFormat(display));
    }

    public void setCalcScreenNumberWithLiteral(String number) {
        TextView calcScreen = findViewById(R.id.calcScreen);
        calcScreen.setText(number);
    }

    public void clearCalcScreen(View view) {
        resetOperandColors();
        resetStateVariables();
        TextView calcScreen = findViewById(R.id.calcScreen);
        calcScreen.setText("");
    }

    public void setStateVariables(double total, boolean isFirst, boolean clearNext,
                                  String operandHolder, boolean isOperandSelected, boolean isTotalDisplayed) {
        this.total = total;
        this.isFirst = isFirst;
        this.clearNext = clearNext;
        this.operandHolder = operandHolder;
        this.isOperandSelected = isOperandSelected;
        this.isTotalDisplayed = isTotalDisplayed;
    }

    public void resetStateVariables() {
        setStateVariables(0, true, false, "", false, false);
    }

    public void clearCalcScreen() {
        if (isTotalDisplayed) {
            isTotalDisplayed = false;
        }

        TextView calcScreen = findViewById(R.id.calcScreen);
        calcScreen.setText("");
    }

    public void checkClearNext() {
        if (clearNext) {
            clearCalcScreen();
            clearNext = false;
        }
    }

    public void displayTotal() {
        isTotalDisplayed = true;
        setCalcScreenNumber(Double.toString(total));
    }

    public void highlightHeldOperand() {
        Button plus = findViewById(R.id.buttonPlus);
        Button minus = findViewById(R.id.buttonMinus);
        Button times = findViewById(R.id.buttonMultiply);
        Button divide = findViewById(R.id.buttonDivide);

        resetOperandColors();

        if (operandHolder.equals(bs.getAdd())) {
            plus.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else if (operandHolder.equals(bs.getSubtract())) {
            minus.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else if (operandHolder.equals(bs.getDivide())) {
            divide.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else if (operandHolder.equals(bs.getMultiply())) {
            times.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    public void resetOperandColors() {
        Button plus = findViewById(R.id.buttonPlus);
        Button minus = findViewById(R.id.buttonMinus);
        Button times = findViewById(R.id.buttonMultiply);
        Button divide = findViewById(R.id.buttonDivide);

        plus.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        minus.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        divide.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        times.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    public boolean checkCalcScreenLength(boolean showError) {
        String num = getCalcScreenNumber();
        int numLength = num.length() + 1;

        if (numLength > this.MAX_LENGTH) {
            if (showError) {
                displayError();
            }

            return false;
        }

        return true;
    }

    /*
    *  I am unhappy with the solution to this method. The reason it has proven to be so difficult
    * is for many reasons. Once the calculator reaches its max limit, it starts resulting in funky
    * calculations. I don't know why this is. So I have to keep it at a length where it doesn't get funky.
    * My first idea was to just give an error if the user calculates past the max length. The problem arises
    * when trying simple calculations, like 1/3, which gives an error since it's a repeating decimal. Repeating
    * decimals must be formatted. But that means long numbers will be formatted, or trimmed down, as well.
    * Trimming down a long number is something I deeply do not like because it can mislead the user.
    * An ideal solution would be to throw an error for long numbers that aren't repeating decimals.
    * But that would require some level of pattern recognition for the more complex repeating decimals.
    * It pains me to say that I simply do not have the time or ability to invest in a clean solution to this problem.
    * -Joshua Kaplan*/
    public String checkStringLengthAndFormat(String str) {

        if (str.length() > this.MAX_LENGTH) {
            StringBuilder sb = new StringBuilder(str);
            String formattedString = sb.substring(0, this.MAX_LENGTH);
            return formattedString;
        }

        return str;
    }
}
