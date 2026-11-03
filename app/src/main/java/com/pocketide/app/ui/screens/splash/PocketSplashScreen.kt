package com.pocketide.app.ui.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pocketide.app.BuildConfig
import com.pocketide.app.R
import com.pocketide.app.ui.theme.PocketBorder
import com.pocketide.app.ui.theme.PocketCyan
import com.pocketide.app.ui.theme.PocketEmerald
import com.pocketide.app.ui.theme.PocketIndigo
import com.pocketide.app.ui.theme.PocketObsidian
import com.pocketide.app.ui.theme.PocketPrimaryBlue
import com.pocketide.app.ui.theme.PocketPurple
import com.pocketide.app.ui.theme.PocketSurface
import com.pocketide.app.ui.theme.PocketSurfaceElevated
import com.pocketide.app.ui.theme.PocketTextMuted
import com.pocketide.app.ui.theme.PocketTextPrimary
import com.pocketide.app.ui.theme.PocketTextSecondary
import kotlinx.coroutines.delay

@Composable
fun PocketSplashScreen(
    onSplashFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale = remember { Animatable(0.72f) }
    val alpha = remember { Animatable(0f) }
    val progress = remember { Animatable(0f) }
    var bootStep by remember { mutableStateOf("Initializing Core Engine...") }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val dotPulse by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dotPulse"
    )

    LaunchedEffect(Unit) {
        // Entrance animation
        scale.animateTo(1f, animationSpec = tween(650, easing = FastOutSlowInEasing))
        alpha.animateTo(1f, animationSpec = tween(450))

        // Sequential initialization
        bootStep = "Loading Linux ARM64 userspace kernel..."
        progress.animateTo(0.28f, animationSpec = tween(380))
        delay(220)

        bootStep = "Verifying PRoot isolated sandbox environment..."
        progress.animateTo(0.62f, animationSpec = tween(420))
        delay(260)

        bootStep = "Connecting Multi-Agent Copilot orchestrator..."
        progress.animateTo(0.90f, animationSpec = tween(360))
        delay(220)

        bootStep = "Pocket IDE ready"
        progress.animateTo(1f, animationSpec = tween(220))
        delay(380)

        onSplashFinished()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PocketObsidian),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            // Minimal Squircle Logo Card
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(112.dp)
                    .scale(scale.value)
                    .clip(RoundedCornerShape(20.dp))
                    .background(PocketSurface)
                    .border(
                        width = 1.dp,
                        color = PocketBorder,
                        shape = RoundedCornerShape(20.dp)
                    )
            ) {
                // Official High-Resolution App Logo
                Image(
                    painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = "Pocket IDE Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            // App Brand Typography
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.alpha(alpha.value)
            ) {
                Text(
                    text = "POCKET",
                    color = PocketTextPrimary,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 3.sp,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "IDE",
                    color = PocketPrimaryBlue,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 3.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Development environment for Android",
                color = PocketTextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.3.sp,
                fontFamily = FontFamily.Default,
                modifier = Modifier.alpha(alpha.value)
            )

            Spacer(modifier = Modifier.height(44.dp))

            // Sleek Progress Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.60f)
                    .height(3.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(PocketBorder)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress.value)
                        .height(3.dp)
                        .background(PocketPrimaryBlue)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Boot Status with Animated Indicator
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .scale(dotPulse)
                        .clip(CircleShape)
                        .background(PocketCyan)
                )
                Text(
                    text = bootStep,
                    color = PocketTextSecondary,
                    fontSize = 11.5.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        // Bottom System Tag & Version
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 28.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(PocketSurface)
                    .border(0.8.dp, PocketBorder, RoundedCornerShape(6.dp))
                    .padding(horizontal = 9.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "v${BuildConfig.VERSION_NAME}",
                    color = PocketEmerald,
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Monospace
                )
            }

            Text(
                text = "ARM64 Linux Environment",
                color = PocketTextMuted,
                fontSize = 10.5.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
