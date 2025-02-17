package edu.ucne.registrotecnicos.presentation.ticket

import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import edu.ucne.registrotecnicos.data.local.entities.TicketEntity

data class TicketUiState(
    val ticketId: Int? = null,
    val fecha: String = "",
    val cliente: String = "",
    val asunto: String = "",
    val descripcion: String = "",
    val tecnicoId: Int? = null,
    val prioridadId: Int? = null,
    val mensajeError: String? = null,
    val mensajeExito: String? = null,
    val tickets: List<TicketEntity> = emptyList(),
    val tecnicos: List<TecnicoEntity> = emptyList()
)
