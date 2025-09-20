package com.sabir.pingheart.ui

import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class SoundOption(val title: String, val uri: Uri)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundPickerDialog(
    context: Context,
    onSoundSelected: (SoundOption) -> Unit,
    onDismiss: () -> Unit
){
    var sounds by remember { mutableStateOf<List<SoundOption>>(emptyList()) }

    LaunchedEffect(Unit) {
        val ringtoneManager = RingtoneManager(context).apply{
            setType(RingtoneManager.TYPE_NOTIFICATION)
        }
        val cursor = ringtoneManager.cursor
        val results = mutableListOf<SoundOption>()
        while(cursor.moveToNext()){
            val title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX)
            val uri = ringtoneManager.getRingtoneUri(cursor.position)
            results.add(SoundOption(title,uri))
        }
        sounds = results
    }
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        title = {Text("Select Sound")},
        text = {
            LazyColumn {
                items(sounds){ sound ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable{
                                onSoundSelected(sound)
                                onDismiss()
                            }
                            .padding(12.dp)
                    ){
                        Text(sound.title)
                    }
                }
            }
        }
    )
}
