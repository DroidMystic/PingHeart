package com.sabir.pingheart.di
import com.sabir.pingheart.data.SoundMappingRepository
import com.sabir.pingheart.viewmodel.MappingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{
    single{ SoundMappingRepository() }
    viewModel{ MappingViewModel(get()) }
}