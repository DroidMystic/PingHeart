package com.sabir.pingheart.ui

import android.content.pm.PackageManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class AppInfo(val appName: String, val packageName: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppPickerScreen(
    pm: PackageManager,
    onAppSelected: (AppInfo) -> Unit,
    onBack: () -> Unit
){
    val apps = remember {
        pm.getInstalledApplications(PackageManager.GET_META_DATA)
            .map{ AppInfo(pm.getApplicationLabel(it).toString(), it.packageName) }
            .sortedBy { it.appName }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {Text("Pick an App")}, navigationIcon = {
                IconButton(onClick = onBack) { Text("<")}
            })
        }
    ) {
        padding ->
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            items(apps){ app ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable{ onAppSelected(app)}
                        .padding(16.dp)
                ){
                    Text(app.appName)
                }
            }
        }
    }
}