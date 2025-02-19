package edu.ucne.registrotecnicos.presentation.tecnico

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import edu.ucne.registrotecnicos.data.local.entities.TicketEntity
import edu.ucne.registrotecnicos.data.local.repositories.TecnicoRepository
import edu.ucne.registrotecnicos.data.local.repositories.TicketRepository
import edu.ucne.registrotecnicos.presentation.ticket.TicketUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TicketUiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        getTickets()
    }

    fun saveTicket() {
        viewModelScope.launch {
            if (_uiState.value.descripcion.isBlank() || _uiState.value.fecha == null) {
                _uiState.update {
                    it.copy(errorMessage = "Todos los campos son obligatorios.", successMessage = null)
                }
                return@launch
            }

            try {
                ticketRepository.saveTicket(_uiState.value.toEntity())
                _uiState.update {
                    it.copy(successMessage = "Técnico guardado correctamente.", errorMessage = null)
                }
                nuevoTicket()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Error al guardar el técnico: ${e.message}", successMessage = null)
                }
            }
        }
    }
    fun find(ticketId: Int){
        viewModelScope.launch {
            if (ticketId > 0) {
                val ticket = ticketRepository.find(ticketId)
                if (ticket != null) {
                    _uiState.update {
                        it.copy(
                            ticketId = ticket.ticketId,
                            descripcion = ticket.descripcion

                        )
                    }
                }
            }
        }
    }
    fun nuevoTicket() {
        _uiState.update {
            it.copy(
                ticketId = null,
                descripcion = "",
                fecha = "",
                errorMessage = null,
                successMessage = null
            )
        }
    }

    fun selectTicket(ticketId: Int) {
        viewModelScope.launch {
            if (ticketId > 0) {
                val ticket = ticketRepository.find(ticketId)
                _uiState.update {
                    it.copy(
                        ticketId = ticket?.ticketId,
                        descripcion  = ticket?.descripcion ?: "",
                        fecha = ticket?.fecha ?:""
                    )
                }
            }
        }
    }

  fun deleteTicket() {
        viewModelScope.launch {
            try {
                ticketRepository.delete(_uiState.value.toEntity())
                _uiState.update {
                    it.copy(successMessage = "Técnico eliminado correctamente.", errorMessage = null)
                }
                nuevoTicket()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Error al eliminar el técnico: ${e.message}", successMessage = null)
                }
            }
        }
    }

    fun isValid(): Boolean {
        val descripcionValid = _uiState.value.descripcion.isNotBlank()


        _uiState.update {
            it.copy(
                errorMessage = when {
                    !descripcionValid -> "Debes rellenar el campo nombre"
                    else -> null
                }
            )
        }

        return descripcionValid
    }

    fun getTickets() {
        viewModelScope.launch {
            ticketRepository.getAll().collect { tickets ->
                _uiState.update {
                    it.copy(tickets = tickets)
                }
            }
        }
    }

   fun onDescripcionChange(descripcion: String) {
        _uiState.update {
            it.copy(descripcion = descripcion)
        }
    }

    fun onAsuntoChange(asunto: String) {
        _uiState.update {
            it.copy(asunto = asunto)
        }
    }

    fun onFechaChange(fecha: String) {
        _uiState.update {
            it.copy(fecha = fecha)
        }
    }

    fun onClienteChange(cliente: String) {
        _uiState.update {
            it.copy(cliente = cliente)
        }
    }


    fun onTecnicoChange(newTecnico: String) {
        val tecnicoInt = newTecnico.toIntOrNull()
        _uiState.update {
            it.copy(tecnicoId = tecnicoInt)
        }
    }

    fun onPrioridadChange(newPrioridad: String) {
        val prioridadInt = newPrioridad.toIntOrNull()
        _uiState.update {
            it.copy(prioridadId = prioridadInt)
        }
    }

    /*fun onSueldoChange(newSueldo: String) {
        val sueldoDouble = newSueldo.toDoubleOrNull()
        _uiState.update {
            it.copy(sueldo = sueldoDouble)
        }
    }*/

    fun clearMessages() {
        _uiState.update {
            it.copy(errorMessage = null, successMessage = null)
        }
    }


    fun TicketUiState.toEntity() = TicketEntity(
        ticketId = ticketId,
        fecha = fecha,
        cliente = cliente,
        asunto = asunto,
     descripcion = descripcion,
    prioridadId = prioridadId,
    tecnicoId = tecnicoId,
    respuesta = respuesta

    )
}