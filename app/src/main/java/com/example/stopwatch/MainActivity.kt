package com.example.stopwatch

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stopwatch.ui.theme.StopWatchTheme
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StopWatchTheme {
                        Surface (modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colorScheme.background
                        ){
                            StopWatch()
                        }
                }
            }
        }
    }

@SuppressLint("DefaultLocale")
@Composable
fun StopWatch() {

    var time by remember {mutableStateOf(0L)}
    var isRunning by remember {mutableStateOf(false)}
    var lapTimes by remember {mutableStateOf(mutableListOf<Long>())}
    var lapCount by remember {mutableStateOf(0)}

    LaunchedEffect(isRunning) {
        while (isRunning) {
            time++
            delay(1000L)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Stop Watch", style = MaterialTheme.typography.displayLarge)

    Spacer(modifier = Modifier.height(50.dp))

    Text(text = String.format("%02d:%02d:%02d", time / 3600, (time % 3600) / 60, time % 60),
        style = MaterialTheme.typography.headlineLarge)

    Spacer(modifier = Modifier.height(16.dp))

        Row {
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = {isRunning = true}, modifier = Modifier.weight(1f)) {
                Text(text = "Start")
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Button(onClick = {isRunning = false}, modifier = Modifier.weight(1f)) {
                Text(text = "Pause")
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Spacer(modifier = Modifier.padding(8.dp))

            Button(onClick = {isRunning = true}, modifier = Modifier.weight(1f)) {
                Text(text = "Resume")
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Button(onClick = { isRunning = false ; time = 0L ; lapTimes.clear() },
                modifier = Modifier.weight(1f)) {
                Text(text = "Reset")
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row  {
            Spacer(modifier = Modifier.padding(8.dp))

            Button(onClick = {if (isRunning){
                lapTimes.add(time)
            } }, modifier = Modifier.weight(1f)) {
                Text(text = "Lap")
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Button(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Text(text = "Record")
            }
            Spacer(modifier = Modifier.padding(8.dp))

        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Lap Times", style = MaterialTheme.typography.headlineMedium)

        LazyColumn {
            items(lapTimes) { lap ->

                Text(
                    text = String.format(
                        "Lap %02d: %02d:%02d:%02d",
                        lapCount++,
                        TimeUnit.MILLISECONDS.toHours(lap) % 24,
                        TimeUnit.MILLISECONDS.toMinutes(lap) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(lap) % 60
                    )
                )
            }
        }


        }
    }
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StopWatchTheme {
        StopWatch()
    }
}



