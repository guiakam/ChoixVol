package com.example.choixvol

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FlightSearchViewModel(
    private val flightDao: FlightDao,
    private val searchPreferences: SearchPreferences
) : ViewModel() {

    private var _searchQuery by mutableStateOf("")
    private var _showSuggestions by mutableStateOf(false)
    val searchQuery: String get() = _searchQuery
    val showSuggestions: Boolean get() = _showSuggestions
    val suggestions = MutableStateFlow<List<Airport>>(emptyList())
    private val _favorites = MutableStateFlow<List<Favorite>>(emptyList())
    val favorites: StateFlow<List<Favorite>> get() = _favorites

    init {
        viewModelScope.launch {
            searchPreferences.searchQuery.collect { savedQuery ->
                _searchQuery = savedQuery
                updateSearchResults(savedQuery)
            }
        }
        viewModelScope.launch {
            flightDao.getFavorites().collect { favoritesList ->
                _favorites.value = favoritesList
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery = query
        _showSuggestions = query.isNotEmpty()

        viewModelScope.launch {
            searchPreferences.saveSearchQuery(query)
            updateSearchResults(query)
        }
    }

    private suspend fun updateSearchResults(query: String) {
        if (query.isNotEmpty()) {
            flightDao.searchAirports(query).collect { results ->
                suggestions.value = results
            }
        } else {
            suggestions.value = emptyList()
        }
    }

    fun toggleFavorite(favorite: Favorite) {
        viewModelScope.launch {
            if (_favorites.value.contains(favorite)) {
                flightDao.deleteFavorite(favorite)
            } else {
                flightDao.insertFavorite(favorite)
            }
        }
    }
}
