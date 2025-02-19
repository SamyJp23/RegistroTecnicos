package edu.ucne.registrotecnicos.presentation.tecnico

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import edu.ucne.registrotecnicos.data.local.entities.TicketEntity
import edu.ucne.registrotecnicos.presentation.ticket.TicketUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketListScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    createTicket: () -> Unit,
    goToMenu: () -> Unit,
    goToTicket: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Tickets") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { createTicket() },
                containerColor = Color.Blue,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Crear Ticket"
                )
            }
        }
    ) { innerPadding ->
        TicketListBodyScreen(
            uiState = uiState,
            createTicket = createTicket,
            goToMenu = goToMenu,
            goToTicket = goToTicket,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun TicketListBodyScreen(
    uiState: TicketUiState,
    createTicket: () -> Unit,
    goToMenu: () -> Unit,
    goToTicket: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                items(uiState.tickets) {
                    TicketRow(it, goToTicket)
                }
            }
        }
    }
}



@Composable
private fun TicketRow(
    it: TicketEntity,
    goToTicket: (Int) -> Unit
) {

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable { goToTicket(it.ticketId!!) },
        colors = CardDefaults.cardColors(containerColor = verde),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(verde),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(5f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "TicketId: ${it.tecnicoId}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Text(
                    text = "Cliente: ${it.cliente}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White
                    )
                )
                Text(
                    text = "Asunto: ${it.asunto}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White
                    )
                )
                Text(
                    text = "Descripcion: ${it.descripcion}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White
                    )
                )
                Text(
                    text = "Fecha: ${it.fecha}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White
                    )
                )
                Text(
                    text = "PrioridadId: ${it.prioridadId}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White
                    )
                )
                Text(
                    text = "Tecnico: ${it.tecnicoId}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}


