package com.example.choixvol // Assurez-vous que le package correspond à l'emplacement du fichier

import FlightSearchViewModelFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.choixvol.ui.theme.ChoixVolTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialiser la base de données
        DatabaseInitializer.initializeDatabase(applicationContext)
        val database = FlightDatabase.getDatabase(applicationContext)
        val searchPreferences = SearchPreferences(applicationContext)

        setContent {
            ChoixVolTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Créez le ViewModel en utilisant la Factory
                    val viewModel: FlightSearchViewModel = viewModel(
                        factory = FlightSearchViewModelFactory(
                            database.flightDao(),
                            searchPreferences
                        )
                    )

                    // Passez le ViewModel à votre composable
                    FlightSearchScreen(viewModel = viewModel)
                }
            }
        }
    }
}
