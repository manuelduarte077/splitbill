package dev.donmanuel.app.splitbill.views

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.donmanuel.app.splitbill.composables.MainCard
import dev.donmanuel.app.splitbill.composables.MainIcon
import dev.donmanuel.app.splitbill.composables.MainRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("SplitBill", fontWeight = FontWeight.Bold)
                }
            )
        }
    ) { padding ->
        ContentHomeView(modifier = Modifier.padding(padding))
    }
}


@Composable
fun ContentHomeView(modifier: Modifier) {
    var amount by rememberSaveable() { mutableStateOf("") }

    var options = listOf(0, 10, 15, 25, 30)
    var selectedTip by remember { mutableStateOf(10) }
    var numberPersons by remember { mutableStateOf(1) }

    var totalTip by remember { mutableStateOf(0.0) }
    var total by remember { mutableStateOf(0.0) }
    var totalByPersons by remember { mutableStateOf(0.0) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
            }
    ) {
        MainCard(title = "Total Bill Amount") {
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Amount")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                options.forEach { option ->
                    FilterChip(
                        selected = selectedTip == option,
                        onClick = { selectedTip = option },
                        label = {
                            Text("$option%")
                        }
                    )
                }
            }

            Text("Number of persons")

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                MainIcon(
                    icon = Icons.Filled.ArrowCircleDown,
                    contentDescription = "Arrow Circle Down",
                    onClick = {
                        if (numberPersons > 1) numberPersons--
                    }
                )

                Text(numberPersons.toString(), fontWeight = FontWeight.Bold, fontSize = 28.sp)

                MainIcon(
                    icon = Icons.Filled.ArrowCircleUp,
                    contentDescription = "Arrow Circle Up",
                    onClick = {
                        numberPersons++
                    }
                )
            }


            Button(
                onClick = {
                    if (!amount.isEmpty()) {
                        totalTip = amount.toDouble() * (selectedTip.toDouble() / 100)
                        total = amount.toDouble() + totalTip
                        totalByPersons = calculate(
                            amount = amount,
                            tip = selectedTip,
                            person = numberPersons,
                        )
                    }
                    focusManager.clearFocus()
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(48.dp)
                    .fillMaxWidth(),
            ) {
                Text("Calculate", fontSize = 20.sp)
            }

        }

        MainCard(
            title = "Bill Summary",
        ) {
            MainRow("Tip Amount:", totalTip.roundTwoDecimals())
            MainRow("Total:", total.roundTwoDecimals())

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color(0xFFE5F9E7),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    "Each Person Pays $${totalByPersons.roundTwoDecimals()}",
                    fontSize = 20.sp, fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

        }
    }
}

fun calculate(amount: String, tip: Int, person: Int): Double {
    val tipRes = amount.toDouble() * (tip.toDouble() / 100)
    val totalWithTip = amount.toDouble() + tipRes

    return totalWithTip / person
}

fun Double.roundTwoDecimals(): Double {
    return kotlin.math.round(this * 100) / 100
}