package com.joshuakaplan.calculator;

import android.content.Context;
import android.content.res.Resources;

public class ButtonStrings {
    private String one;
    private String two;
    private String three;
    private String four;
    private String five;
    private String six;
    private String seven;
    private String eight;
    private String nine;
    private String zero;
    private String dot;
    private String multiply;
    private String divide;
    private String subtract;
    private String add;
    private String clear;
    private String negative;

    public ButtonStrings(Context context) {
        Resources res = context.getResources();
        one = res.getString(R.string.button_one);
        two = res.getString(R.string.button_two);
        three = res.getString(R.string.button_three);
        four = res.getString(R.string.button_four);
        five = res.getString(R.string.button_five);
        six = res.getString(R.string.button_six);
        seven = res.getString(R.string.button_seven);
        eight = res.getString(R.string.button_eight);
        nine = res.getString(R.string.button_nine);
        zero = res.getString(R.string.button_zero);
        dot = res.getString(R.string.button_dot);
        multiply = res.getString(R.string.button_multiply);
        divide = res.getString(R.string.button_divide);
        subtract = res.getString(R.string.button_subtract);
        add = res.getString(R.string.button_add);
        clear = res.getString(R.string.button_clear);
        negative = res.getString(R.string.button_negative);
    }

    public String getOne() {
        return one;
    }

    public String getTwo() {
        return two;
    }

    public String getThree() {
        return three;
    }

    public String getFour() {
        return four;
    }

    public String getFive() {
        return five;
    }

    public String getSix() {
        return six;
    }

    public String getSeven() {
        return seven;
    }

    public String getEight() {
        return eight;
    }

    public String getNine() {
        return nine;
    }

    public String getZero() {
        return zero;
    }

    public String getDot() {
        return dot;
    }

    public String getMultiply() {
        return multiply;
    }

    public String getDivide() {
        return divide;
    }

    public String getSubtract() {
        return subtract;
    }

    public String getAdd() {
        return add;
    }

    public String getNegative() {
        return negative;
    }

    public String getClear() {
        return clear;
    }
}
