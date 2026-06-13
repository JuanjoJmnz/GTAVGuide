package com.juanjojmnz.gtavguide.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanjojmnz.gtavguide.data.repository.PropertyRepository
import com.juanjojmnz.gtavguide.model.Property
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PropertiesUiState(
    val properties: List<Property> = emptyList(),
    val isLoading: Boolean = true,
    val selectedFilter: CharacterFilter = CharacterFilter.ALL
)

@HiltViewModel
class PropertyViewModel @Inject constructor(
    private val repository: PropertyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PropertiesUiState())
    val uiState: StateFlow<PropertiesUiState> = _uiState.asStateFlow()

    private val _selectedProperty = MutableStateFlow<Property?>(null)
    val selectedProperty: StateFlow<Property?> = _selectedProperty.asStateFlow()

    init {
        loadProperties(CharacterFilter.ALL)
    }

    fun onFilterSelected(filter: CharacterFilter) {
        _uiState.update { it.copy(selectedFilter = filter) }
        loadProperties(filter)
    }

    private fun loadProperties(filter: CharacterFilter) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val flow = when (filter) {
                CharacterFilter.ALL      -> repository.getAllProperties()
                CharacterFilter.MICHAEL  -> repository.getPropertiesByCharacter("MICHAEL")
                CharacterFilter.FRANKLIN -> repository.getPropertiesByCharacter("FRANKLIN")
                CharacterFilter.TREVOR   -> repository.getPropertiesByCharacter("TREVOR")
            }
            flow.collect { properties ->
                _uiState.update {
                    it.copy(properties = properties, isLoading = false)
                }
            }
        }
    }

    fun selectProperty(id: Int) {
        viewModelScope.launch {
            _selectedProperty.value = repository.getPropertyById(id)
        }
    }

    fun clearSelectedProperty() {
        _selectedProperty.value = null
    }
}