package com.rdecky.asmcalc.calculator;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class CalculatorButtonTest {

    private CalculatorButton button;
    private Context context = ApplicationProvider.getApplicationContext();

    @Before
    public void setUp() {
        button = new CalculatorButton(context);
    }

    @Test
    public void isNumericDigit_0_true() {
        button.setText("0");
        assertTrue(button.isNumericDigit());
    }

    @Test
    public void isNumericDigit_5_true() {
        button.setText("5");
        assertTrue(button.isNumericDigit());
    }

    @Test
    public void isNumericDigit_9_true() {
        button.setText("9");
        assertTrue(button.isNumericDigit());
    }

    @Test
    public void isNumericDigit_10_false() {
        button.setText("9");
        assertTrue(button.isNumericDigit());
    }

    @Test
    public void isNumericDigit_Rsh_false() {
        button.setText("Rsh");
        assertFalse(button.isNumericDigit());
    }

    @Test
    public void isHexDigit_A_true() {
        button.setText("A");
        assertTrue(button.isHexDigit());
    }

    @Test
    public void isNumericDigit_C_true() {
        button.setText("C");
        assertTrue(button.isHexDigit());
    }

    @Test
    public void isNumericDigit_F_true() {
        button.setText("F");
        assertTrue(button.isHexDigit());
    }

    @Test
    public void isNumericDigit_G_false() {
        button.setText("G");
        assertFalse(button.isHexDigit());
    }

    @Test
    public void isNumericDigit_Lsh_false() {
        button.setText("Lsh");
        assertFalse(button.isHexDigit());
    }

    @Test
    public void isBinDigit_0_true() {
        button.setText("0");
        assertTrue(button.isBinDigit());
    }

    @Test
    public void isBinDigit_1_true() {
        button.setText("1");
        assertTrue(button.isBinDigit());
    }

    @Test
    public void isBinDigit_2_false() {
        button.setText("2");
        assertFalse(button.isBinDigit());
    }

    @Test
    public void isSpecialOperator_divide_true() {
        button.setText("/");
        assertTrue(button.isSpecialOperator());
    }

    @Test
    public void isSpecialOperator_multiply_true() {
        button.setText("*");
        assertTrue(button.isSpecialOperator());
    }

    @Test
    public void isSpecialOperator_minus_true() {
        button.setText("-");
        assertTrue(button.isSpecialOperator());
    }

    @Test
    public void isSpecialOperator_plus_true() {
        button.setText("+");
        assertTrue(button.isSpecialOperator());
    }

    @Test
    public void isSpecialOperator_equals_true() {
        button.setText("=");
        assertTrue(button.isSpecialOperator());
    }

    @Test
    public void isSpecialOperator_invert_true() {
        button.setText("+/-");
        assertTrue(button.isSpecialOperator());
    }

    @Test
    public void isSpecialOperator_period_true() {
        button.setText(".");
        assertTrue(button.isSpecialOperator());
    }

    @Test
    public void isSpecialOperator_openParenthesis_true() {
        button.setText("(");
        assertTrue(button.isSpecialOperator());
    }

    @Test
    public void isSpecialOperator_closeParenthesis_true() {
        button.setText(")");
        assertTrue(button.isSpecialOperator());
    }

    @Test
    public void isSpecialOperator_Lsh_true() {
        button.setText("Lsh");
        assertTrue(button.isSpecialOperator());
    }

    @Test
    public void isSpecialOperator_1_false() {
        button.setText("1");
        assertFalse(button.isSpecialOperator());
    }

}