package com.app.brawlermobile.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.brawlermobile.activities.MainActivityViewModel

import com.google.accompanist.drawablepainter.rememberDrawablePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Dropdown(
    viewModel: MainActivityViewModel,
    navController: NavController,
    drawerState: DrawerState,
    item: MenuItem
) {
    val navigateToCommanders = remember { mutableStateOf(false) }
    val valueString = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DrawerItem(
            icon = if (viewModel.expanded.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            text = item.title,
            textStyle = MaterialTheme.typography.headlineMedium,
            backgroundColor = if(viewModel.expanded.value) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.background,
            onClick = { viewModel.expanded.value = !viewModel.expanded.value }
        )
        if (viewModel.expanded.value) {
            Column {
                viewModel.colors?.keys?.forEach { colorKey ->
                    DrawerItem(
                        icon = if (viewModel.expandedItems[colorKey] == true) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        text = colorKey,
                        textStyle = MaterialTheme.typography.headlineMedium,
                        backgroundColor = if(viewModel.expandedItems[colorKey] == true) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.background,
                        onClick = { viewModel.expandedItems[colorKey] = viewModel.expandedItems[colorKey]?.not() ?: true }
                    )
                    if (viewModel.expandedItems[colorKey] == true) {
                        viewModel.colors!![colorKey]?.keys?.forEach { subColorKey ->
                            Row (
                                modifier = Modifier
                                    .padding(start = 18.dp)
                                    .clickable {
                                        valueString.value = subColorKey
                                        navigateToCommanders.value = true
                                    }
                            ){
                                subColorKey.forEach { char ->
                                    viewModel.svgImages[char.toString()]?.let { drawable ->
                                        Image(
                                            painter = rememberDrawablePainter(drawable = drawable),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(viewModel.svgSize.dp)
                                                .fillMaxSize()
                                                .align(Alignment.CenterVertically)
                                        )
                                    }
                                }
                                DrawerItem(
                                    icon = null,
                                    text = "${viewModel.colors!![colorKey]?.get(subColorKey)}",
                                    textStyle = MaterialTheme.typography.headlineMedium,
                                    isClickable = false,
                                    onClick = {}
                                )
                            }

                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(navigateToCommanders.value) {
        if (navigateToCommanders.value) {
            drawerState.close()
            viewModel.setAppBarText("Commanders")
            navController.navigate("commanders/${valueString.value}")
            valueString.value = ""
            navigateToCommanders.value = false
        }
    }
}