package com.app.brawlermobile.navigation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.brawlermobile.R

// Navigation drawer logo layout
@Composable
fun DrawerHeader() {
    val image: Painter = painterResource(id = R.drawable.logo)
    val typography = MaterialTheme.typography
    val circleBackground = MaterialTheme.colorScheme.secondary
    val logoTint = MaterialTheme.colorScheme.background
    val borderColor = MaterialTheme.colorScheme.background
    val borderWidth = 2.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 25.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier.size(70.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    drawCircle(color = borderColor, radius = size.minDimension / 2)
                    drawCircle(color = circleBackground, radius = size.minDimension / 2 - borderWidth.toPx())
                }
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(CircleShape)
                ) {
                    Image(
                        painter = image,
                        contentDescription = "Logo",
                        modifier = Modifier.matchParentSize(),
                        colorFilter = ColorFilter.tint(logoTint)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = LocalContext.current.resources.getString(R.string.app_name),
                style = typography.headlineMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}