package com.app.brawlermobile.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun DrawerItem(
    icon: ImageVector?,
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    isClickable: Boolean = true,
    onClick: () -> Unit,
) {
    val paddingVal = 18.dp
    val tintCol = MaterialTheme.colorScheme.secondary

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .let { if (isClickable) it.clickable(onClick = onClick) else it }
            .padding(paddingVal)
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = tintCol
            )
            Spacer(modifier = Modifier.width(paddingVal))
        }
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier.weight(1f),
            color = tintCol
        )
    }
}