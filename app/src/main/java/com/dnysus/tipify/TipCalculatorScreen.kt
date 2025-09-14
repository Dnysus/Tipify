package com.dnysus.tipify

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dnysus.tipify.ui.theme.TipifyTheme
import java.text.NumberFormat

@Composable
fun TipCalculatorScreen(modifier: Modifier = Modifier) {
    var billAmountInput by remember { mutableStateOf("") }
    var tipPercent by remember { mutableStateOf(15f) }
    var splitBy by remember { mutableStateOf(1) }

    val billAmount = billAmountInput.toDoubleOrNull() ?: 0.0
    val tipAmount = billAmount * tipPercent / 100
    val totalAmount = billAmount + tipAmount
    val amountPerPerson = if (splitBy > 0) totalAmount / splitBy else totalAmount

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Tipify",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Bill Amount Input
        TextField(
            value = billAmountInput,
            onValueChange = { billAmountInput = it },
            label = { Text("Bill Amount") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tip Percentage
        Text(text = "Tip Percentage: ${tipPercent.toInt()}%")
        Slider(
            value = tipPercent,
            onValueChange = { tipPercent = it },
            valueRange = 0f..30f,
            steps = 5,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Split Bill
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Split by:")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = { if (splitBy > 1) splitBy-- }) { Text("-") }
                Text(text = "$splitBy", modifier = Modifier.padding(horizontal = 8.dp))
                Button(onClick = { splitBy++ }) { Text("+") }
            }
        }


        Spacer(modifier = Modifier.height(32.dp))

        // Calculated Amounts
        Text(
            text = "Tip: ${NumberFormat.getCurrencyInstance().format(tipAmount)}",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Total: ${NumberFormat.getCurrencyInstance().format(totalAmount)}",
            style = MaterialTheme.typography.bodyLarge
        )
        if (splitBy > 1) {
            Text(
                text = "Amount per person: ${NumberFormat.getCurrencyInstance().format(amountPerPerson)}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TipCalculatorScreenPreview() {
    TipifyTheme {
        Surface {
            TipCalculatorScreen()
        }
    }
}