package com.kholopova.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
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

@Composable
fun Calculator(modifier: Modifier = Modifier) {
    val error = "NaN";
    var display by rememberSaveable { mutableStateOf("0") }
    var operand by rememberSaveable { mutableStateOf<Double?>(null) }
    var pendingOp by rememberSaveable { mutableStateOf<String?>(null) }
    var startNew by rememberSaveable { mutableStateOf(true) }

    fun clear() {
        display = "0"
        operand = null
        pendingOp = null
        startNew = true
    }

    fun format(value: Double): String {
        return if (value % 1.0 == 0.0) value.toLong().toString() else value.toString()
    }

    fun applyOp(a: Double, b: Double, operator: String): String {
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
        } else {
            display += digit
        }
    }

    fun onDot() {
        if (display == error || startNew) {
            display = "0."
            startNew = false
        } else if (!display.contains('.')) {
            display += "."
        }
    }

    fun onOp(operator: String) {
        if (display == error) {
            clear()
            return
        }
        val current = display.toDoubleOrNull() ?: return
        val left = operand
        val pending = pendingOp
        if (left != null && pending != null && !startNew) {
            val result = applyOp(left, current, pending)
            display = result
            operand = result.toDoubleOrNull()
        } else {
            operand = current
        }
        pendingOp = operator
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
            Button(
                onClick = { onDigit("7") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("7", fontSize = 24.sp)
            }
            Button(
                onClick = { onDigit("8") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("8", fontSize = 24.sp)
            }
            Button(
                onClick = { onDigit("9") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("9", fontSize = 24.sp)
            }
            Button(
                onClick = { onOp("+") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("+", fontSize = 24.sp)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Button(
                onClick = { onDigit("4") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("4", fontSize = 24.sp)
            }
            Button(
                onClick = { onDigit("5") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("5", fontSize = 24.sp)
            }
            Button(
                onClick = { onDigit("6") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("6", fontSize = 24.sp)
            }
            Button(
                onClick = { onOp("-") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("-", fontSize = 24.sp)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Button(
                onClick = { onDigit("1") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("1", fontSize = 24.sp)
            }
            Button(
                onClick = { onDigit("2") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("2", fontSize = 24.sp)
            }
            Button(
                onClick = { onDigit("3") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("3", fontSize = 24.sp)
            }
            Button(
                onClick = { onOp("/") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("/", fontSize = 24.sp)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Button(
                onClick = { clear() },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("C", fontSize = 24.sp)
            }
            Button(
                onClick = { onDigit("0") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("0", fontSize = 24.sp)
            }
            Button(
                onClick = { onDot() },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(".", fontSize = 24.sp)
            }
            Button(
                onClick = { onOp("x") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text("x", fontSize = 24.sp)
            }
        }

    }
}
