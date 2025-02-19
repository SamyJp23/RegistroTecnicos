package edu.ucne.registrotecnicos.presentation.tecnico

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrotecnicos.data.local.database.TecnicoDb
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import edu.ucne.registrotecnicos.data.local.repositories.TecnicoRepository
import edu.ucne.registrotecnicos.presentation.ticket.TicketUiState
import kotlinx.coroutines.launch

@Composable
fun TicketScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    ticketId: Int,
    goBackToList: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TicketBodyScreen(
        ticketId = ticketId,
        viewModel = viewModel,
        onTecnicoChange = viewModel::onTecnicoChange,
        onPrioridadChange = viewModel::onPrioridadChange,
        uiState = uiState,
        goBackToList = goBackToList
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketBodyScreen(
    ticketId: Int,
    viewModel: TicketViewModel,
    onTecnicoChange: (String) -> Unit,
    onPrioridadChange: (String) -> Unit,
    uiState: TicketUiState,
    goBackToList: () -> Unit
) {
    LaunchedEffect(ticketId) {
        if (ticketId > 0) viewModel.find(ticketId)
    }

    Scaffold(

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
                .background(Color.White)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.White)
                ) {
                    OutlinedTextField(
                        label = { Text(text = "Cliente") },
                        value = uiState.cliente,
                        onValueChange = viewModel::onClienteChange,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledTextColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Black,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Black
                        )
                    )

                    OutlinedTextField(
                        label = { Text(text = "Asunto") },
                        value = uiState.asunto,
                        onValueChange = viewModel::onAsuntoChange,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledTextColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Black,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Black
                        )
                    )
                    OutlinedTextField(
                        label = { Text(text = "Descripcion") },
                        value = uiState.descripcion,
                        onValueChange = viewModel::onDescripcionChange,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledTextColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Black,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Black
                        )
                    )

                    OutlinedTextField(
                        label = { Text(text = "Fecha") },
                        value = uiState.fecha,
                        onValueChange = viewModel::onFechaChange,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledTextColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Black,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Black
                        )
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledTextColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Black,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Black
                        ),
                        label = { Text(text = "TecnicoId") },
                        value = uiState.tecnicoId?.toString() ?: "",
                        onValueChange = { input ->
                            try {
                                onTecnicoChange(input.toIntOrNull()?.toString() ?: "")
                            } catch (e: NumberFormatException) {
                                onTecnicoChange("")
                            }
                        },
                        isError = uiState.tecnicoId == null
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledTextColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Black,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Black
                        ),
                        label = { Text(text = "PrioridadId") },
                        value = uiState.prioridadId?.toString() ?: "",
                        onValueChange = { input ->
                            try {
                                onPrioridadChange(input.toIntOrNull()?.toString() ?: "")
                            } catch (e: NumberFormatException) {
                                onPrioridadChange("")
                            }
                        },
                        isError = uiState.prioridadId == null
                    )




                    Spacer(modifier = Modifier.padding(2.dp))
                    uiState.errorMessage?.let {
                        Text(text = it, color = Color.Red)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { goBackToList() },colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Blue)) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Go back",
                                tint = Color.White
                            )
                            Text(text = "Atrás",
                                color = Color.White)
                        }

                        OutlinedButton(
                            onClick = {
                                if (viewModel.isValid()) {
                                    viewModel.saveTicket()
                                    goBackToList()
                                }
                            },colors = ButtonDefaults.outlinedButtonColors(containerColor = verde)) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Save button",
                                tint = Color.White

                            )
                            Text(text = "Guardar",
                                color = Color.White)
                        }

                        OutlinedButton(
                            onClick = {
                                viewModel.deleteTicket()
                                goBackToList()
                            },colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Red)) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete button",
                                tint = Color.White

                            )
                            Text(
                                text = "Eliminar",
                                color = Color.White)
                        }
                    }
                }
            }
        }
    }
}