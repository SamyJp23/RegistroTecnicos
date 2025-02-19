package edu.ucne.registrotecnicos.presentation.navigation

import kotlinx.serialization.Serializable


sealed class Screen {
    @Serializable
    data object TecnicoList : Screen()

    @Serializable
    data object TicketList : Screen()

    @Serializable
    data object ArticuloListScreen : Screen()

    @Serializable
    data class ArticuloScreen(val articuloId: Int) : Screen()

    @Serializable
    data class Tecnico(val tecnicoId: Int) : Screen()

    @Serializable
    data class Ticket(val ticketId: Int) : Screen()

    @Serializable
    data class EditTecnico(val tecnicoId: Int) : Screen()

    @Serializable
    data class DeleteTecnico(val tecnicoId: Int) : Screen()

    @Serializable
    data class TecnicoTickets(val tecnicoId: Int) : Screen()

    @Serializable
    data class EditTicket(val ticketId: Int) : Screen()

    @Serializable
    data class DeleteTicket(val ticketId: Int) : Screen()

    @Serializable
    data object Home : Screen()

}