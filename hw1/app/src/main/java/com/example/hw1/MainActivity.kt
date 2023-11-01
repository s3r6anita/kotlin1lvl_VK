package com.example.hw1

import android.content.Intent
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.hw1.ui.theme.Hw1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val func: (Int, Color) -> Unit = { t1, t2 ->
            val intent = MainActivity2.newInstance(this, t1, t2)
            startActivity(intent)
        }

        setContent {
            Hw1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SquareScreen(func)
                }
            }
        }
    }
}

@Composable
fun SquareScreen(func: (Int, Color) -> Unit) {

    val orientation = LocalConfiguration.current.orientation
    val numColumns = if (orientation == ORIENTATION_PORTRAIT) 3 else 4

    var squares = rememberSaveable { mutableStateOf(0) }

    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        val scrollState = rememberScrollState()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(9f)
                .verticalScroll(scrollState)
        ) {

            var ost = squares.value % numColumns
            val squaresInColumn = squares.value / numColumns

            for (columnNumber in 1..numColumns){
                if(ost != 0){
                    createColumn(columnNumber,squaresInColumn + 1, numColumns, func)
                    ost--
                }
                else {
                    createColumn(columnNumber, squaresInColumn, numColumns, func)
                }
            }
        }

        Button(
            onClick = { squares.value++ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(if (orientation == ORIENTATION_PORTRAIT) 1f else 3f )
        ) {
            Text(text = stringResource(id = R.string.button_text))
        }
    }
}

@Composable
fun createColumn(columnNumber:Int, numSquare: Int, numColumns: Int, func: (Int, Color) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width((LocalConfiguration.current.screenWidthDp / numColumns).dp)
    ) {
        for (i in 0 until numSquare) {
            val number = i * numColumns + columnNumber
            createSquare(number, numColumns, func)
        }
    }
}

@Composable
fun createSquare(number: Int, numColumns: Int, func: (Int, Color) -> Unit){
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(10.dp)
            .size((LocalConfiguration.current.screenWidthDp / numColumns).dp)
            .background(if (number % 2 == 0) Color.Red else Color.Blue)
            .clickable {
                func(number, if (number % 2 == 0) Color.Red else Color.Blue)
            }

    ) {
        Text(
            text = number.toString(),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


