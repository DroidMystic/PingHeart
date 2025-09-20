package com.sabir.pingheart.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sabir.pingheart.data.SoundMapping
import com.sabir.pingheart.ui.theme.PingHeartTheme
import com.sabir.pingheart.viewmodel.MappingViewModel
import org.koin.androidx.compose.koinViewModel

sealed class Screen{
    object Main : Screen()
    object AppPicker : Screen()
}
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PingHeartTheme {

                val viewModel: MappingViewModel = koinViewModel()
                var screen by remember { mutableStateOf<Screen>(Screen.Main) }

                var pendingApp by remember { mutableStateOf<AppInfo?>(null) }
                var pendingSound by remember { mutableStateOf<SoundOption?>(null)}

                var showSoundPicker by remember { mutableStateOf(false)}
                var showSenderInput by remember { mutableStateOf(false)}

                when(screen){
                    is Screen.Main ->{
                        MainScreen(
                            mappings  = viewModel.mappings.collectAsState().value,
                            onDelete = { viewModel.deleteMapping(it)},
                            onAddClick = { screen = Screen.AppPicker}
                        )
                    }

                    is Screen.AppPicker -> {
                        AppPickerScreen(
                            pm = packageManager,
                            onAppSelected = { app ->
                                pendingApp = app
                                showSoundPicker = true
                            },
                            onBack = { screen = Screen.Main}
                        )
                    }
                }

                if(showSoundPicker && pendingApp!= null){
                    SoundPickerDialog(
                        context = this,
                        onSoundSelected = { sound ->
                            pendingSound = sound
                            showSoundPicker = false
                            showSenderInput = true
                        },
                        onDismiss = {
                            showSoundPicker = false
                            pendingApp = null
                        }
                    )
                }

                if(showSenderInput && pendingApp != null && pendingSound !=null){
                    SenderInputDialog(
                        onConfirm  = {sender ->
                            viewModel.addMapping(
                                SoundMapping(
                                    appPackage = pendingApp!!.packageName,
                                    senderName = sender,
                                    soundUri = pendingSound!!.uri.toString()
                                )
                            )
                            pendingApp = null
                            pendingSound = null
                            showSenderInput = false
                            screen = Screen.Main
                        },
                        onDismiss = {
                            showSenderInput = false
                            pendingApp = null
                            pendingSound = null
                        }
                    )
                }
            }
        }
    }
}

