package edu.ucne.registrotecnicos.presentation.tecnico

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.ucne.registrotecnicos.data.local.entities.TicketEntity
import edu.ucne.registrotecnicos.data.local.repositories.TicketRepository
import edu.ucne.registrotecnicos.presentation.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TecnicoTicketsScreen(
    tecnicoId: Int,
    ticketRepository: TicketRepository,
    goTecnicoTickets: () -> Unit
) {
    val tickets by ticketRepository.getTicketsTecnico(tecnicoId).collectAsState(initial = emptyList())

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (tickets.isEmpty()) {
                Text("No hay tickets asignados a este técnico")
            } else {
                LazyColumn {
                    items(tickets) { ticket ->
                        TicketItem(ticket = ticket, onRespondTicket = { ticketId, response ->

                        })
                    }
                }
            }
        }
    }
}

@Composable
fun TicketItem(ticket: TicketEntity, onRespondTicket: (Int, String) -> Unit) {
    var response by remember { mutableStateOf("") }

    Card {
        Column {
            Text("Asunto: ${ticket.asunto}")
            Text("Descripción: ${ticket.descripcion}")
            Text("Fecha: ${ticket.fecha}")
            OutlinedTextField(
                value = response,
                onValueChange = { response = it },
                label = { Text("Respuesta") }
            )
            Button(
                onClick = {
                    if (response.isNotEmpty()) {
                        ticket.ticketId?.let { ticketId ->
                            onRespondTicket(ticketId, response)
                            response = ""
                        }
                    }
                },
                enabled = response.isNotEmpty()
            ) {
                Text("Responder")
            }
        }
    }
}
