package com.sabir.pingheart.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sabir.pingheart.data.SoundMapping
import com.sabir.pingheart.data.SoundMappingRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MappingViewModel(private val repository: SoundMappingRepository): ViewModel(){
    val mappings: StateFlow<List<SoundMapping>> = repository.mappings

    fun addMapping(mapping: SoundMapping){
        viewModelScope.launch { repository.add(mapping) }
    }

    fun deleteMapping(mapping: SoundMapping){
        viewModelScope.launch { repository.delete(mapping) }
    }
}