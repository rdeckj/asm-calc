package com.rdecky.asmcalc.calculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.rdecky.asmcalc.calculator.value.HistoryValue;
import com.rdecky.asmcalc.calculator.value.NumberValue;
import com.rdecky.asmcalc.calculator.value.OperatorValue;
import com.rdecky.asmcalc.calculator.value.SpecialButtonValue;
import com.rdecky.asmcalc.util.NumberFormatter;

import java.util.ArrayList;
import java.util.List;

public class HistoryBarViewModel extends ViewModel {

    private OperatorValue previousValue;
    private Observer<String> historyFormatObserver;
    private static final OperatorValue EQUALS = new OperatorValue("=");

    private CalculatorViewModel calculatorViewModel;

    private MutableLiveData<List<HistoryValue>> _history = new MutableLiveData<>();

    private MutableLiveData<String> _decHistory = new MutableLiveData<>();
    private MutableLiveData<String> _hexHistory = new MutableLiveData<>();

    private MutableLiveData<String> _inputHistory = new MutableLiveData<>();
    public LiveData<String> inputHistory = _inputHistory;

    public HistoryBarViewModel(CalculatorViewModel calculatorViewModel) {
        this.calculatorViewModel = calculatorViewModel;
        setInputFormat(InputFormatClickListener.InputFormat.DEC);
        _history.observeForever(createHistoryObserver());
    }

    void operatorButtonPressed(OperatorValue operatorButtonValue) {
        update(operatorButtonValue);
    }

    void specialButtonPressed(SpecialButtonValue specialButtonValue) {
        switch (specialButtonValue.getText().toLowerCase()) {
            case "=":
                equals();
                break;
            case "clear":
                clear();
                break;
            default:
                break;
        }
    }

    void setInputFormat(InputFormatClickListener.InputFormat newFormat) {
        createInputHistoryObserver();
        removeOldHistoryFormatObservers();
        setNewHistoryFormatObserver(newFormat);
    }

    private void createInputHistoryObserver() {
        if (historyFormatObserver == null) {
            historyFormatObserver = new Observer<String>() {
                @Override
                public void onChanged(String newString) {
                    _inputHistory.setValue(newString);
                }
            };
        }
    }

    private void equals() {
        if (!previousValue.isRightParenthesis()) {
            update(EQUALS);
        }
        long result = Calculator.evaluate(_history.getValue());
        calculatorViewModel.setCurrentValueAsDec(result);
        clear();
    }

    private void clear() {
        _history.setValue(new ArrayList<HistoryValue>());
    }

    private void update(OperatorValue value) {
        updateViewModel(value);
        previousValue = value;
    }

    private void updateViewModel(OperatorValue value) {
        if (!value.isLeftParenthesis()) {
            addHistoryValue(new NumberValue(calculatorViewModel.getCurrentValue()));
        }
        addHistoryValue(value);
    }

    private void addHistoryValue(HistoryValue historyValue) {
        List<HistoryValue> currentHistory = _history.getValue();
        if (currentHistory == null) {
            currentHistory = new ArrayList<>();
        }
        currentHistory.add(historyValue);
        _history.setValue(currentHistory);
    }

    private Observer<List<HistoryValue>> createHistoryObserver() {
        return new Observer<List<HistoryValue>>() {
            @Override
            public void onChanged(List<HistoryValue> changedHistoryValues) {
                StringBuilder decHistory = new StringBuilder();
                StringBuilder hexHistory = new StringBuilder();

                for (HistoryValue historyValue : changedHistoryValues) {
                    if (historyValue instanceof NumberValue) {
                        long value = ((NumberValue) historyValue).getValue();
                        decHistory.append(NumberFormatter.formatDec(value));
                        hexHistory.append(NumberFormatter.formatHex(value));
                    }
                    if (historyValue instanceof OperatorValue) {
                        String operator = ((OperatorValue) historyValue).getText();
                        decHistory.append(" ");
                        decHistory.append(operator);
                        hexHistory.append(" ");
                        hexHistory.append(operator);
                    }
                }

                _decHistory.setValue(decHistory.toString());
                _hexHistory.setValue(hexHistory.toString());
            }
        };
    }

    private void setNewHistoryFormatObserver(InputFormatClickListener.InputFormat inputFormat) {
        if (inputFormat.equals(InputFormatClickListener.InputFormat.DEC)) {
            _decHistory.observeForever(historyFormatObserver);
        } else {
            _hexHistory.observeForever(historyFormatObserver);
        }
    }

    private void removeOldHistoryFormatObservers() {
        _decHistory.removeObserver(historyFormatObserver);
        _hexHistory.removeObserver(historyFormatObserver);
    }
}
