package com.dhbw.progresstracker

import android.content.Context
import android.content.res.Resources
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.Display
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dhbw.progresstracker.ui.theme.ProgresstrackerTheme
import android.widget.Button
import android.widget.TextView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*setContent {
            ProgresstrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                }
            }
        }*/
        setContentView(R.layout.activity_main)

        val displayMetrics = DisplayMetrics()

        //Wenn das Gerät mindestens Android 11 verwendet, dann wird die nicht veraltete Methode benutzt
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val displayManager = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
            displayManager.getDisplay(Display.DEFAULT_DISPLAY)
        } else {
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(displayMetrics)
        }

        val screenWidth = displayMetrics.widthPixels

        val resources: Resources = resources
        val textSize = screenWidth / 10f

        val resourceId: Int = resources.getIdentifier("buttons_text_size", "dimen", packageName)
        resources.getDimensionPixelSize(resourceId)

        val textViewLernButton: TextView = findViewById(R.id.lernbutton)
        textViewLernButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)

        val textViewFreizeitButton: TextView = findViewById(R.id.freizeitbutton)
        textViewFreizeitButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)


        val lernbutton = findViewById<Button>(R.id.lernbutton)
        val freizeitbutton = findViewById<Button>(R.id.freizeitbutton)

        lernbutton.setOnClickListener {
            Log.d("MainActivity", "Hello World von Lernbutton!")
        }

        freizeitbutton.setOnClickListener {
            Log.d("MainActivity", "Hello World von Freizeitbutton!")
        }
        Log.d("MainActivity", "Hello World!")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProgresstrackerTheme {
        Greeting("Android")
    }
}