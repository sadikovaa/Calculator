package com.kholopova.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun Calculator(modifier: Modifier = Modifier) {
    val error = "NaN";
    var display by rememberSaveable { mutableStateOf("0") }
    var operand by rememberSaveable { mutableStateOf<Double?>(null) }
    var pendingOperator by rememberSaveable { mutableStateOf<String?>(null) }
    var startNew by rememberSaveable { mutableStateOf(true) }

    fun clear() {
        display = "0"
        operand = null
        pendingOperator = null
        startNew = true
    }

    fun format(value: Double): String {
        return BigDecimal.valueOf(value)
            .setScale(12, RoundingMode.HALF_UP)
            .stripTrailingZeros()
            .toPlainString()
    }

    fun applyOperator(a: Double, b: Double, operator: String): String {
        val result = when (operator) {
            "+" -> a + b
            "-" -> a - b
            "x" -> a * b
            "/" -> if (b == 0.0) return error else a / b
            else -> b
        }
        return format(result)
    }

    fun onDigit(digit: String) {
        if (display == error || startNew) {
            display = digit
            startNew = false
        } else if (display == "0") {
            display = digit
        } else if (display == "-") {
            display = "-$digit"
        } else {
            display += digit
        }
    }

    fun onDot() {
        if (display == error || startNew) {
            display = "0."
            startNew = false
        } else if (display == "-") {
            display = "-0."
        } else if (!display.contains('.')) {
            display += "."
        }
    }

    fun onOperator(operator: String) {
        if (display == error) {
            clear()
            return
        }

        if (operator == "-" && startNew) {
            display = "-"
            startNew = false
            return
        }
        if (display == "-") return

        val current = display.toDoubleOrNull() ?: return
        val left = operand
        val pending = pendingOperator
        if (left != null && pending != null && !startNew) {
            val result = applyOperator(left, current, pending)
            display = result
            operand = result.toDoubleOrNull()
        } else {
            operand = current
        }
        pendingOperator = operator
        startNew = true
    }

    fun onEquals() {
        if (display == error) {
            clear()
            return
        }
        val operator = pendingOperator ?: return
        val left = operand ?: return
        val current = display.toDoubleOrNull() ?: return
        display = applyOperator(left, current, operator)
        operand = null
        pendingOperator = null
        startNew = true
    }

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = display,
            fontSize = 48.sp,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .wrapContentHeight(Alignment.Bottom)
                .testTag("result"),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            CalculatorButton("7", modifier = Modifier.weight(1f)) { onDigit("7") }
            CalculatorButton("8", modifier = Modifier.weight(1f)) { onDigit("8") }
            CalculatorButton("9", modifier = Modifier.weight(1f)) { onDigit("9") }
            CalculatorButton("+", modifier = Modifier.weight(1f)) { onOperator("+") }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            CalculatorButton("4", modifier = Modifier.weight(1f)) { onDigit("4") }
            CalculatorButton("5", modifier = Modifier.weight(1f)) { onDigit("5") }
            CalculatorButton("6", modifier = Modifier.weight(1f)) { onDigit("6") }
            CalculatorButton("-", modifier = Modifier.weight(1f)) { onOperator("-") }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            CalculatorButton("1", modifier = Modifier.weight(1f)) { onDigit("1") }
            CalculatorButton("2", modifier = Modifier.weight(1f)) { onDigit("2") }
            CalculatorButton("3", modifier = Modifier.weight(1f)) { onDigit("3") }
            CalculatorButton("/", modifier = Modifier.weight(1f)) { onOperator("/") }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            CalculatorButton("C", modifier = Modifier.weight(1f)) { clear() }
            CalculatorButton("0", modifier = Modifier.weight(1f)) { onDigit("0") }
            CalculatorButton(".", modifier = Modifier.weight(1f)) { onDot() }
            CalculatorButton("x", modifier = Modifier.weight(1f)) { onOperator("x") }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            CalculatorButton("=", modifier = Modifier.weight(1f)) { onEquals() }
        }

    }
}
