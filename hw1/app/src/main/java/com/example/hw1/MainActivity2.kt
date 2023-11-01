package com.example.hw1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hw1.ui.theme.Hw1Theme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val number = intent.extras!!.getInt("NUM")

        setContent {
            Hw1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(number)
                }
            }
        }
    }
    companion object {
        fun newInstance(context: Context, num: Int, color: Color): Intent {

            return Intent(context, MainActivity2::class.java).apply {
                putExtra("NUM", num)
            }

        }
    }
}

@Composable
fun Greeting(number: Int) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(10.dp)
            .size((LocalConfiguration.current.screenWidthDp / 2).dp)
            .background(if (number % 2 == 0) Color.Red else Color.Blue)
    ) {
        Text(
            text = number.toString(),
            fontSize = 40.sp,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}
