package com.app.brawlermobile.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.app.brawlermobile.ui.theme.BrawlerMobileTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.app.brawlermobile.navigation.AppBar
import com.app.brawlermobile.navigation.DrawerBody
import com.app.brawlermobile.navigation.DrawerHeader
import com.app.brawlermobile.navigation.MenuItem
import kotlinx.coroutines.launch

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.brawlermobile.navigation.DrawerThemeChanger
import com.app.brawlermobile.screens.CardsScreen
import com.app.brawlermobile.screens.CommanderDetailScreen
import com.app.brawlermobile.screens.CommandersScreen
import com.app.brawlermobile.screens.HomeScreen

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private val themeViewModel: ThemeViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Display splash screen (works for api >= 31)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }
        }

        setContent {
            // Change theme
            val darkTheme by themeViewModel.darkTheme.observeAsState(initial = false)
            // Is navigation drawer opened
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            // Drawer coroutine scope
            val scope = rememberCoroutineScope()
            // Navigation
            val navController = rememberNavController()
            // Appbar
            val appBarText by viewModel.appBarText.collectAsState()

            BrawlerMobileTheme(darkTheme = darkTheme) {
                // A surface container using the 'background' color from the theme
                ModalNavigationDrawer(
                    // Define list of navigation drawer elements
                    modifier = Modifier.fillMaxSize(),
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            DrawerHeader()
                            DrawerThemeChanger(themeViewModel)
                            DrawerBody(
                                items = listOf(
                                    MenuItem(
                                        id = "home",
                                        title = "Home",
                                        contentDescription = "Show top commanders",
                                        icon = Icons.Default.Home
                                    ),
                                    MenuItem(
                                        id = "cards",
                                        title = "Top cards",
                                        contentDescription = "Show top cards",
                                        icon = Icons.Default.Info
                                    ),
                                    MenuItem(
                                        id = "commanders",
                                        title = "Commanders",
                                        contentDescription = "Show commanders for given color",
                                        icon = Icons.Default.KeyboardArrowDown
                                    ),
                                ),
                                onItemClick = {
                                    scope.launch {
                                        drawerState.close()
                                        when (it.id) {
                                            "home" -> {
                                                viewModel.setAppBarText(it.title)
                                                navController.navigate("home")
                                            }
                                            "cards" -> {
                                                viewModel.setAppBarText(it.title)
                                                navController.navigate("top_cards")
                                            }
                                        }
                                    }
                                },
                                drawerState = drawerState,
                                viewModel = viewModel,
                                navController = navController
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.background)
                            )
                        }
                    }
                ) {
                    Scaffold(
                        // Set top bar and change its content (based on view state)
                        topBar = {
                            AppBar(
                                appBarText = appBarText,
                                contentDescription = "Show navigation drawer",
                                onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            )
                        },
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { },

                        // Display correct page (also based on view state)
                    ) {
                        NavHost(navController, startDestination = "home") {
                            composable("home") { HomeScreen() }
                            composable("top_cards") { CardsScreen() }
                            composable("commanders/{colors}") { backStackEntry ->
                                CommandersScreen(navController, backStackEntry.arguments?.getString("colors"))
                            }
                            composable("commander/{id}") { backStackEntry ->
                                backStackEntry.arguments?.getString("id")
                                    ?.let { it1 -> CommanderDetailScreen(it1) }
                            }
                        }
                    }
                }
            }
        }
    }
}

// @app.route('/commanders/popularity', methods=['GET'])
// @app.route('/commanders/winrate', methods=['GET'])
// @app.route('/cards', methods=['GET'])
// @app.route('/commanders/<string:colors>', methods=['GET'])
// @app.route('/commanders/<int:commander_id>', methods=['GET'])
// @app.route('/colors', methods=['GET'])
// @app.route('/types', methods=['GET'])
