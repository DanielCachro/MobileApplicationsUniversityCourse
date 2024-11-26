package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
        val availablePlates = mapOf<Int, Color>(
            25 to Color.Red,
            20 to Color.Blue,
            15 to Color.Yellow,
            10 to Color.Green,
            5 to Color.White,
        )

        val weightArr = remember { mutableStateListOf<Int>() }

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {


            Box(modifier = Modifier.fillMaxWidth()) {
                Image(painterResource(R.drawable.bar), contentDescription = "Green plate", modifier = Modifier
                    .align(Alignment.Center)
                )

                Row(modifier = Modifier.align(Alignment.Center)) {
                    Spacer(modifier = Modifier.width(64.dp))
                    for (weight in weightArr) {
                        val drawable = when (weight) {
                            25 -> R.drawable.plate_25
                            20 -> R.drawable.plate_20
                            15 -> R.drawable.plate_15
                            10 -> R.drawable.plate_10
                            5 -> R.drawable.plate_5
                            else -> R.drawable.plate_5

                        }
                        Image(painterResource(drawable), contentDescription = "weight plate", modifier = Modifier.padding(1.dp))
                    }
                }
            }


            Text(
                text = "Total weight ${weightArr.sum()} kg",

                )

            for(plate in availablePlates){
                Button(
                    onClick = {
                        weightArr.add(plate.key)
                    }
                ) {
                    Text("Add ${plate.key} kg")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    weightArr.clear()
                },
                enabled = !weightArr.isEmpty()
            ) {
                Text("Clear")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Preview")
    }
}

@Preview(showBackground = true)
@Composable
fun SecondPreview() {
    MyApplicationTheme {
        Greeting("Second Preview")
    }
}