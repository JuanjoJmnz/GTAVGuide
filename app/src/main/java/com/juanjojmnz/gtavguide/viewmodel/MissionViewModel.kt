package com.juanjojmnz.gtavguide.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanjojmnz.gtavguide.data.repository.MissionRepository
import com.juanjojmnz.gtavguide.model.Mission
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class CharacterFilter(val displayName: String) {
    ALL("Todos"),
    MICHAEL("Michael"),
    FRANKLIN("Franklin"),
    TREVOR("Trevor")
}

data class MissionsUiState(
    val missions: List<Mission> = emptyList(),
    val isLoading: Boolean = true,
    val selectedFilter: CharacterFilter = CharacterFilter.ALL
)

@HiltViewModel
class MissionViewModel @Inject constructor(
    private val repository: MissionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MissionsUiState())
    val uiState: StateFlow<MissionsUiState> = _uiState.asStateFlow()

    private val _selectedMission = MutableStateFlow<Mission?>(null)
    val selectedMission: StateFlow<Mission?> = _selectedMission.asStateFlow()

    init {
        loadMissions(CharacterFilter.ALL)
    }

    fun onFilterSelected(filter: CharacterFilter) {
        _uiState.update { it.copy(selectedFilter = filter) }
        loadMissions(filter)
    }

    private fun loadMissions(filter: CharacterFilter) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val flow = when (filter) {
                CharacterFilter.ALL      -> repository.getMissionsByType("MAIN_STORY")
                CharacterFilter.MICHAEL  -> repository.getMissionsByCharacter("MICHAEL")
                CharacterFilter.FRANKLIN -> repository.getMissionsByCharacter("FRANKLIN")
                CharacterFilter.TREVOR   -> repository.getMissionsByCharacter("TREVOR")
            }
            flow.collect { missions ->
                _uiState.update {
                    it.copy(missions = missions, isLoading = false)
                }
            }
        }
    }

    fun selectMission(id: Int) {
        viewModelScope.launch {
            _selectedMission.value = repository.getMissionById(id)
        }
    }

    fun clearSelectedMission() {
        _selectedMission.value = null
    }
}