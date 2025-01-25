package edu.ucne.registrotecnicos.presentation.ticket

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.ucne.registrotecnicos.data.local.database.TecnicoDb
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import edu.ucne.registrotecnicos.data.local.entities.TicketEntity
import edu.ucne.registrotecnicos.data.local.repositories.TicketRepository
import kotlinx.coroutines.launch

@Composable
fun EditTicketScreen(
    tecnicoDb: TecnicoDb,
    ticketId: Int,
    goTicketList: () -> Unit,
    ticketRepository: TicketRepository
) {
    val scope = rememberCoroutineScope()
    var ticket by remember { mutableStateOf<TicketEntity?>(null) }
    var cliente by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var asunto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(ticketId) {
        val loadedTicket = ticketRepository.find(ticketId)
        ticket = loadedTicket
        if (loadedTicket != null) {
            cliente = loadedTicket.cliente
            fecha = loadedTicket.fecha
            asunto = loadedTicket.asunto
            descripcion = loadedTicket.descripcion

        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        OutlinedTextField(
                            label = { Text(text = "Cliente") },
                            value = cliente,
                            onValueChange = { cliente = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text(text = "Fecha") },
                            value = fecha,
                            onValueChange = { fecha = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text(text = "Asunto") },
                            value = asunto,
                            onValueChange = { asunto = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text(text = "Descripcion") },
                            value = descripcion,
                            onValueChange = { descripcion = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(2.dp))
                        if (errorMessage.isNotEmpty()) {
                            Text(text = errorMessage, color = Color.Red)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            OutlinedButton(
                                onClick = {
                                    ticket?.let {
                                        cliente = it.cliente
                                        asunto = it.asunto
                                        descripcion = it.descripcion
                                        fecha = it.fecha

                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Resetear cambios"
                                )
                                Text(text = "Resetear")
                            }
                            Spacer(Modifier.width(100.dp))

                            OutlinedButton(
                                onClick = {
                                    if (cliente.isBlank() || asunto.isBlank() || descripcion.isBlank() || fecha.isBlank()) {
                                        errorMessage = "El campo nombre es obligatorio"
                                    } else {

                                        scope.launch {
                                            ticketRepository.updateTicket(
                                                ticket!!.copy(
                                                    cliente = cliente,
                                                    fecha = fecha,
                                                    asunto = asunto,
                                                    descripcion = descripcion
                                                )
                                            )
                                            goTicketList()
                                        }
                                    }

                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Guardar cambios"
                                )
                                Text(text = "Guardar")
                            }
                        }
                    }
                }
            }
        }
    }
}