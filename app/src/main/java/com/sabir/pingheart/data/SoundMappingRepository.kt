package com.sabir.pingheart.data

import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SoundMappingRepository {
    private val _mappings = MutableStateFlow<List<SoundMapping>>(emptyList())
    val mappings = _mappings.asStateFlow()

    private val cache = mutableListOf<SoundMapping>()

    suspend fun add(mapping: SoundMapping) {
        cache.add(mapping)
        _mappings.value = cache.toList()
    }

    suspend fun delete(mapping: SoundMapping) {
        cache.remove(mapping)
        _mappings.value = cache.toList()
    }

    fun resolveSound(appPackage: String, sender: String?): String? {

        //Sender Specific
        cache.firstOrNull {
            it.appPackage == appPackage && it.senderName.equals(sender, ignoreCase = true)
        }?.let { return it.soundUri }

        //Fallback to Default Mapping
        cache.firstOrNull {
            it.appPackage == appPackage && it.senderName.isBlank()
        }?.let { return it.soundUri }

        return null
    }
}