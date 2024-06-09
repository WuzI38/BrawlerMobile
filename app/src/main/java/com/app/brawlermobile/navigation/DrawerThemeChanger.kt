package com.app.brawlermobile.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.app.brawlermobile.activities.ThemeViewModel

// Toggle theme and save it in ThemeViewModel
@Composable
fun DrawerThemeChanger(viewModel: ThemeViewModel) {
    val darkTheme by viewModel.darkTheme.observeAsState(initial = false)

    DrawerItem(
        icon = if (darkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
        text = if (darkTheme) "Dark Theme" else "Light Theme",
        onClick = { viewModel.toggleTheme() }
    )
}