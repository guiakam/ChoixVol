import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.choixvol.FlightDao
import com.example.choixvol.FlightSearchViewModel
import com.example.choixvol.SearchPreferences

class FlightSearchViewModelFactory(
    private val flightDao: FlightDao,
    private val searchPreferences: SearchPreferences
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")  // Ajout de l'annotation pour supprimer l'avertissement de cast non vérifié
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlightSearchViewModel::class.java)) {
            return FlightSearchViewModel(flightDao, searchPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
