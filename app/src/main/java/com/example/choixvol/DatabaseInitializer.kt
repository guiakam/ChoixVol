package com.example.choixvol

import android.content.Context
import androidx.room.Room

object DatabaseInitializer {
    fun initializeDatabase(context: Context) {
        val databaseFile = context.getDatabasePath("flight_search.db")

        if (!databaseFile.exists()) {
            // Créer le répertoire si nécessaire
            databaseFile.parentFile?.mkdirs()

            // Création de la base de données
            val database = Room.databaseBuilder(
                context.applicationContext,
                FlightDatabase::class.java,
                "flight_search.db"
            ).build()

            // Remplissage de la base de données
            database.openHelper.writableDatabase.execSQL("""
                CREATE TABLE airport (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    iata_code TEXT NOT NULL,
                    name TEXT NOT NULL, 
                    passengers INTEGER
                );

                CREATE TABLE favorite (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    departure_code TEXT NOT NULL,
                    destination_code TEXT NOT NULL
                );

                INSERT INTO airport (iata_code, name, passengers) VALUES 
                ('Cmr', 'cameroun airline', 76150000),
                ('cdi', 'cotedivoire airline', 31925000),
                ('NCE', 'Nice Côte d''Azur', 14290000),
                ('MRS', 'Marseille Provence', 9230000),
                ('LYS', 'Lyon Saint-Exupéry', 11350000),
                ('TLS', 'Toulouse-Blagnac', 7800000),
                ('BOD', 'Bordeaux-Mérignac', 6840000),
                ('RNS', 'Rennes', 2600000),
                ('NTE', 'Nantes Atlantique', 6840000),
                ('EZE', 'Buenos Aires Ezeiza', 12500000),
                ('GIG', 'Rio de Janeiro Galeão', 11200000),
                ('JFK', 'New York John F. Kennedy', 62550000),
                ('LAX', 'Los Angeles International', 88068013),
                ('LHR', 'Londres Heathrow', 80888305);

                INSERT INTO favorite (departure_code, destination_code) VALUES 
                ('CDG', 'JFK'),
                ('ORY', 'LHR');
            """)

            // Fermer la base de données
            database.close()
        }
    }
}