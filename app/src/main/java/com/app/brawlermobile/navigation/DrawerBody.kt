package com.app.brawlermobile.navigation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController
import com.app.brawlermobile.activities.MainActivityViewModel

@Composable
fun DrawerBody(
    modifier: Modifier = Modifier,
    items: List<MenuItem>,
    viewModel: MainActivityViewModel,
    navController: NavController,
    itemTextStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    onItemClick: (MenuItem) -> Unit,
    drawerState: DrawerState
) {
    LazyColumn(modifier) {
        itemsIndexed(items) { _, item ->
            if (item.id == "commanders" && viewModel.colors != null) {
                Dropdown(viewModel, navController, drawerState, item)
            } else {
                DrawerItem(
                    icon = item.icon,
                    text = item.title,
                    textStyle = itemTextStyle,
                    onClick = { onItemClick(item) }
                )
            }
        }
    }
}

