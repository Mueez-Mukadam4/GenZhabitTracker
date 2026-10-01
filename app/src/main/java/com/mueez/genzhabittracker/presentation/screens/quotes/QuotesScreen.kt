
package com.mueez.genzhabittracker.presentation.screens.quotes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import com.mueez.genzhabittracker.data.repository.QuoteRepository
import com.mueez.genzhabittracker.viewmodel.QuoteViewModel
import com.mueez.genzhabittracker.viewmodel.QuoteViewModelFactory

@Composable
fun QuotesScreen(
    navController: NavController
) {

    val repository = QuoteRepository()

    val factory = QuoteViewModelFactory(repository)

    val quoteViewModel: QuoteViewModel = viewModel(
        factory = factory
    )

    val quotes = quoteViewModel.getQuotes()

    var currentQuoteIndex by remember {
        mutableStateOf(0)
    }

    if (quotes.isEmpty()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "No quotes available",
                fontSize = 22.sp
            )

            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Back")
            }
        }

        return
    }

    val currentQuote = quotes[currentQuoteIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "✨ Motivation",
            fontSize = 28.sp
        )

        Text(
            text = currentQuote.text,
            fontSize = 22.sp,
            modifier = Modifier.padding(
                top = 24.dp,
                bottom = 12.dp
            )
        )

        Text(
            text = "— ${currentQuote.author}",
            fontSize = 16.sp
        )

        Button(
            onClick = {

                currentQuoteIndex =
                    (currentQuoteIndex + 1) % quotes.size

            },
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text("Next Quote")
        }

        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Back")
        }
    }
}

