package com.example.choixvol  // Assurez-vous que cela correspond à l'emplacement de votre fichier.
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun FlightSearchScreen(
    modifier: Modifier = Modifier,
    viewModel: FlightSearchViewModel
) {
    Column(modifier = modifier.fillMaxSize()) {
        SearchBar(
            searchQuery = viewModel.searchQuery,
            onSearchQueryChange = { viewModel.updateSearchQuery(it) }
        )

        if (viewModel.showSuggestions) {
            AutocompleteSuggestions(
                suggestions = viewModel.suggestions.collectAsState().value,
                onSuggestionClick = { viewModel.updateSearchQuery(it.name) }
            )
        }

        FlightList(
            flights = viewModel.suggestions.collectAsState().value,
            favorites = viewModel.favorites.collectAsState().value,
            onToggleFavorite = { viewModel.toggleFavorite(it) }
        )
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    TextField(
        value = searchQuery,
        onValueChange = { onSearchQueryChange(it) },
        label = { Text("Search for airports") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun AutocompleteSuggestions(
    suggestions: List<Airport>,
    onSuggestionClick: (Airport) -> Unit
) {
    LazyColumn {
        items(suggestions) { airport ->
            Text(
                text = "${airport.name} (${airport.iata_code})",
                modifier = Modifier
                    .clickable { onSuggestionClick(airport) }
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun FlightList(
    flights: List<Airport>,
    favorites: List<Favorite>,
    onToggleFavorite: (Favorite) -> Unit
) {
    LazyColumn {
        items(flights) { airport ->
            val isFavorite = favorites.any {
                it.departure_code == airport.iata_code // Simuler un favori basé sur le code IATA
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = airport.name)
                IconButton(onClick = {
                    val favorite = Favorite(departure_code = airport.iata_code, destination_code = "SomeCode") // Exemple
                    onToggleFavorite(favorite)
                }) {

                    val icon = if (isFavorite) R.drawable.favourite_5983228 else R.drawable.bookmark_10701323 // Remplacer par vos images locales
                    Image(painter = painterResource(id = icon), contentDescription = "Favorite Icon")
                }
            }
        }
    }
}
