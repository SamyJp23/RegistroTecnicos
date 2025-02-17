package edu.ucne.registrotecnicos.presentation.tecnico

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import edu.ucne.registrotecnicos.data.local.repositories.TecnicoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TecnicoViewModel @Inject constructor(
    private val tecnicoRepository: TecnicoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TecnicoUiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        getTecnicos()
    }

    fun saveTecnico() {
        viewModelScope.launch {
            if (_uiState.value.nombre.isBlank() || _uiState.value.sueldo == null) {
                _uiState.update {
                    it.copy(errorMessage = "Todos los campos son obligatorios.", successMessage = null)
                }
                return@launch
            }

            try {
                tecnicoRepository.saveTecnico(_uiState.value.toEntity())
                _uiState.update {
                    it.copy(successMessage = "Técnico guardado correctamente.", errorMessage = null)
                }
                nuevoTecnico()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Error al guardar el técnico: ${e.message}", successMessage = null)
                }
            }
        }
    }

    fun nuevoTecnico() {
        _uiState.update {
            it.copy(
                tecnicoId = null,
                nombre = "",
                sueldo = null,
                errorMessage = null,
                successMessage = null
            )
        }
    }

    fun selectTecnico(tecnicoId: Int) {
        viewModelScope.launch {
            if (tecnicoId > 0) {
                val tecnico = tecnicoRepository.find(tecnicoId)
                _uiState.update {
                    it.copy(
                        tecnicoId = tecnico?.tecnicoId,
                        nombre = tecnico?.nombre ?: "",
                        sueldo = tecnico?.sueldo
                    )
                }
            }
        }
    }

    fun deleteTecnico() {
        viewModelScope.launch {
            try {
                tecnicoRepository.delete(_uiState.value.toEntity())
                _uiState.update {
                    it.copy(successMessage = "Técnico eliminado correctamente.", errorMessage = null)
                }
                nuevoTecnico()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Error al eliminar el técnico: ${e.message}", successMessage = null)
                }
            }
        }
    }

    fun getTecnicos() {
        viewModelScope.launch {
            tecnicoRepository.getAll().collect { tecnicos ->
                _uiState.update {
                    it.copy(tecnicos = tecnicos)
                }
            }
        }
    }

    fun onNombresChange(nombres: String) {
        _uiState.update {
            it.copy(nombre = nombres)
        }
    }

    fun onSueldoChange(newSueldo: String) {
        val sueldoDouble = newSueldo.toDoubleOrNull()
        _uiState.update {
            it.copy(sueldo = sueldoDouble)
        }
    }

    fun clearMessages() {
        _uiState.update {
            it.copy(errorMessage = null, successMessage = null)
        }
    }


    fun TecnicoUiState.toEntity() = TecnicoEntity(
        tecnicoId = tecnicoId,
        nombre = nombre,
        sueldo = sueldo ?: 0.0
    )
}