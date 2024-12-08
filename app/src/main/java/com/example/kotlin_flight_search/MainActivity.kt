package com.example.kotlin_flight_search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlin_flight_search.ui.theme.KotlinFlight_searchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinFlight_searchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FlightSearchScreen()
                }
            }
        }
    }
}

@Composable
fun FlightSearchScreen() {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Flight Search",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            placeholder = { Text("Enter departure airport") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            active = searchQuery.isNotEmpty(),
            onActiveChange = { }
        )

        if (searchQuery.isEmpty()) {
            Text(
                text = "Favorite routes",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sampleFavorites) { favorite ->
                    FavoriteRouteCard(route = favorite)
                }
            }
        } else {
            // Afficher les résultats de recherche ici
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sampleFlights) { flight ->
                    FlightCard(flight = flight)
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        leadingIcon = leadingIcon,
        placeholder = placeholder,
        modifier = modifier,
        singleLine = true
    )
}

@Composable
fun FlightCard(flight: Flight) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("DEPART")
                Text(
                    text = flight.departureCode,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(flight.departureAirport)

                Spacer(modifier = Modifier.height(8.dp))

                Text("ARRIVE")
                Text(
                    text = flight.arrivalCode,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(flight.arrivalAirport)
            }

            IconButton(onClick = { /* TODO: Implement favorite logic */ }) {
                Icon(Icons.Default.Star, contentDescription = "Favorite")
            }
        }
    }
}

data class Flight(
    val departureCode: String,
    val departureAirport: String,
    val arrivalCode: String,
    val arrivalAirport: String
)

data class FavoriteRoute(
    val departureCode: String,
    val departureAirport: String,
    val arrivalCode: String,
    val arrivalAirport: String,
    val isFavorite: Boolean = true
)

val sampleFlights = listOf(
    Flight("FCO", "Leonardo da Vinci International Airport", "SVO", "Sheremetyevo - A.S. Pushkin International"),
    Flight("FCO", "Leonardo da Vinci International Airport", "MUC", "Munich International Airport"),
    Flight("FCO", "Leonardo da Vinci International Airport", "DUB", "Dublin Airport"),
    Flight("FCO", "Leonardo da Vinci International Airport", "LIS", "Humberto Delgado Airport"),
    Flight("FCO", "Leonardo da Vinci International Airport", "BRU", "Brussels Airport")
)

val sampleFavorites = listOf(
    FavoriteRoute(
        "FCO",
        "Leonardo da Vinci International Airport",
        "CPH",
        "Copenhagen Airport"
    ),
    FavoriteRoute(
        "OSL",
        "Oslo Airport",
        "LIS",
        "Humberto Delgado Airport"
    )
)

@Composable
fun FavoriteRouteCard(route: FavoriteRoute) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("DEPART")
                Text(
                    text = route.departureCode,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(route.departureAirport)

                Spacer(modifier = Modifier.height(8.dp))

                Text("ARRIVE")
                Text(
                    text = route.arrivalCode,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(route.arrivalAirport)
            }

            Icon(
                Icons.Default.Star,
                contentDescription = "Favorite",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinFlight_searchTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FlightSearchScreen()
        }
    }
}