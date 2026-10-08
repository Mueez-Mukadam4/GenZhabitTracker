package com.mueez.genzhabittracker.presentation.screens.quotes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mueez.genzhabittracker.data.model.Quote
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

    var quotes by remember {
        mutableStateOf(
            quoteViewModel.getQuotes()
        )
    }

    var currentQuoteIndex by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(Unit) {
        quoteViewModel.syncQuotesFromFirestore { cloudQuotes ->
            quotes = cloudQuotes
            currentQuoteIndex = 0
        }
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
                text = "✨",
                fontSize = 40.sp
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "No quotes available",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Button(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text("Back")
            }
        }

        return
    }

    val currentQuote: Quote =
        quotes[currentQuoteIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "←",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Motivational Quotes",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "A little motivation for your journey.",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "✨",
                    fontSize = 34.sp
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                Text(
                    text = currentQuote.text,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                Text(
                    text = "— ${currentQuote.author}",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {
                currentQuoteIndex =
                    (currentQuoteIndex + 1) % quotes.size
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = "Next Quote ✨"
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        TextButton(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text("Back")
        }
    }
}