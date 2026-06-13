package com.juanjojmnz.gtavguide.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanjojmnz.gtavguide.data.repository.RandomEventRepository
import com.juanjojmnz.gtavguide.model.RandomEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RandomEventsUiState(
    val events: List<RandomEvent> = emptyList(),
    val isLoading: Boolean = true,
    val selectedFilter: CharacterFilter = CharacterFilter.ALL
)

@HiltViewModel
class RandomEventViewModel @Inject constructor(
    private val repository: RandomEventRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RandomEventsUiState())
    val uiState: StateFlow<RandomEventsUiState> = _uiState.asStateFlow()

    private val _selectedEvent = MutableStateFlow<RandomEvent?>(null)
    val selectedEvent: StateFlow<RandomEvent?> = _selectedEvent.asStateFlow()

    init {
        loadEvents(CharacterFilter.ALL)
    }

    fun onFilterSelected(filter: CharacterFilter) {
        _uiState.update { it.copy(selectedFilter = filter) }
        loadEvents(filter)
    }

    private fun loadEvents(filter: CharacterFilter) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val flow = when (filter) {
                CharacterFilter.ALL      -> repository.getAllEvents()
                CharacterFilter.MICHAEL  -> repository.getEventsByCharacter("MICHAEL")
                CharacterFilter.FRANKLIN -> repository.getEventsByCharacter("FRANKLIN")
                CharacterFilter.TREVOR   -> repository.getEventsByCharacter("TREVOR")
            }
            flow.collect { events ->
                _uiState.update {
                    it.copy(events = events, isLoading = false)
                }
            }
        }
    }

    fun selectEvent(id: Int) {
        viewModelScope.launch {
            _selectedEvent.value = repository.getEventById(id)
        }
    }

    fun clearSelectedEvent() {
        _selectedEvent.value = null
    }
}