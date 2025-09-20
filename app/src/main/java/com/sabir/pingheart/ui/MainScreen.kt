package com.sabir.pingheart.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sabir.pingheart.data.SoundMapping

@Composable
fun MainScreen(
    mappings: List<SoundMapping>,
    onDelete: (SoundMapping) -> Unit,
    onAddClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            items(mappings) { mapping ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("App: ${mapping.appPackage}")
                        Text("Sender: ${mapping.senderName.ifBlank { "Default" }}")
                        Text("Sound: ${mapping.soundUri}")
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { onDelete(mapping) }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}