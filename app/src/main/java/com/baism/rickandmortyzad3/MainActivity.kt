package com.baism.rickandmortyzad3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.baism.rickandmortyzad3.data.CharacterInfo
import com.baism.rickandmortyzad3.ui.MainViewModel
import com.baism.rickandmortyzad3.ui.theme.RickAndMortyZad3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyZad3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CharacterScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var characterId by rememberSaveable { mutableStateOf("1") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Rick and Morty API",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { viewModel.loadAllCharacters() },
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pobierz postacie")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = characterId,
            onValueChange = { characterId = it },
            label = { Text("ID postaci") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.loadCharacterById(characterId) },
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pobierz postać po ID")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(8.dp))
                Text("Ładowanie...")
            }
        }

        uiState.errorMessage?.let { message ->
            Text(
                text = "Błąd: $message",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        uiState.selectedCharacter?.let { character ->
            Text(
                text = "Wybrana postać",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            CharacterDetails(character)
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))
        }

        if (uiState.characters.isNotEmpty()) {
            Text(
                text = "Lista postaci (${uiState.characters.size})",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 24.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.characters, key = { it.id }) { character ->
                    CharacterDetails(character)
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun CharacterDetails(character: CharacterInfo) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "ID: ${character.id}")
        Text(text = "Imię: ${character.name}")
        Text(text = "Status: ${character.status}")
        Text(text = "Gatunek: ${character.species}")
    }
}
