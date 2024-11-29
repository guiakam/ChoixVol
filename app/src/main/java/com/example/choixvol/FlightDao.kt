package com.example.choixvol

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {

    // Recherche des aéroports par le nom ou le code IATA, triée par le nombre de passagers
    @Query("SELECT * FROM airport WHERE name LIKE '%' || :searchQuery || '%' OR iata_code LIKE '%' || :searchQuery || '%' ORDER BY passengers DESC")
    fun searchAirports(searchQuery: String): Flow<List<Airport>>

    // Récupérer les favoris
    @Query("SELECT * FROM favorite")
    fun getFavorites(): Flow<List<Favorite>>

    // Ajouter un favori
    @Insert
    suspend fun insertFavorite(favorite: Favorite)

    // Supprimer un favori
    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}
