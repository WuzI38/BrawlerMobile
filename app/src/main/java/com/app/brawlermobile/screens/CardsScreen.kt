package com.app.brawlermobile.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.app.brawlermobile.api.ApiHandler.getCards
import com.app.brawlermobile.layout.CommanderCard
import com.app.brawlermobile.layout.TitleBox

@Composable
fun CardsScreen() {
    var cards by remember { mutableStateOf<List<List<String>>?>(null) }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val columns = when {
        isLandscape -> 3
        else -> 1
    }

    LaunchedEffect(Unit) {
        cards = getCards(limit = 50)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (cards != null) {
            LazyColumn(
                modifier = Modifier.padding(top = 70.dp)
            ) {
                item {
                    TitleBox(title = "Top Cards")
                }
                itemsIndexed(cards!!.chunked(columns)) { _, rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        for (card in rowItems) {
                            CommanderCard(name = card[0], imageUrl = card[1])
                        }
                    }
                }
            }
        } else {
            CircularProgressIndicator()
        }
    }
}