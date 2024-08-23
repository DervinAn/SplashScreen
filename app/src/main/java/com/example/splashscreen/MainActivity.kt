package com.example.splashscreen

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFF202020)
            ) {
                Navigation()
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(Screens.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Screens.MainScreen.route) {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF202020)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to VortexCraft!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
            Button(onClick = {
                // Action for button click
            }) {
                Text(text = "Get Started")
            }
        }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        val scale = remember { Animatable(initialValue = 0.6f) }
        var repeatCount by remember { mutableStateOf(0) }

        LaunchedEffect(key1 = repeatCount) {
            if (repeatCount < 2) {
                scale.animateTo(
                    targetValue = 0.9f,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = {
                            OvershootInterpolator(2f).getInterpolation(it)
                        }
                    )
                )
                delay(1100L)
                scale.animateTo(
                    targetValue = 0.6f,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = {
                            OvershootInterpolator(2f).getInterpolation(it)
                        }
                    )
                )
                repeatCount++
            } else {
                // Navigate to MainScreen after the animation finishes
                navController.navigate(Screens.MainScreen.route) {
                    popUpTo(Screens.SplashScreen.route) { inclusive = true }
                }
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.vortexcraftlogo),
            contentDescription = null,
            modifier = Modifier
                .scale(scale.value)
                .align(Alignment.Center)
                .size(320.dp),
            tint = Color.Unspecified
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 55.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "vortexcraft",
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

sealed class Screens(val route: String) {
    object MainScreen : Screens("mainScreen")
    object SplashScreen : Screens("splashScreen")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainScreen()
}
