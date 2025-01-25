package edu.ucne.registrotecnicos.presentation.navigation



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Upsert
import edu.ucne.registrotecnicos.data.local.database.TecnicoDb
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import edu.ucne.registrotecnicos.data.local.entities.TicketEntity
import edu.ucne.registrotecnicos.data.local.repositories.TecnicoRepository
import edu.ucne.registrotecnicos.data.local.repositories.TicketRepository
import edu.ucne.registrotecnicos.presentation.navigation.Screen
import edu.ucne.registrotecnicos.ui.theme.RegistroTecnicosTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.format.TextStyle

@Composable
fun TicketScreen(
    tecnicoDb: TecnicoDb,
    goTicketList: () -> Unit,
    tecnicoRepository: TecnicoRepository,
    ticketRepository: TicketRepository
) {
    var fecha by remember { mutableStateOf("") }
    var cliente by remember { mutableStateOf("") }
    var asunto by remember { mutableStateOf("") }
    var prioridadId by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var tecnicoId by remember { mutableStateOf("") }
    var errorMessage: String? by remember { mutableStateOf(null) }

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    OutlinedTextField(
                        label = { Text("Fecha") },
                        value = fecha,
                        onValueChange = { fecha = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = { Text("Cliente") },
                        value = cliente,
                        onValueChange = { cliente = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = { Text("Asunto") },
                        value = asunto,
                        onValueChange = { asunto = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = { Text("Descripción") },
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = { Text("TecnicoId") },
                        value = tecnicoId,
                        onValueChange = { tecnicoId = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = { Text("PrioridadId") },
                        value = prioridadId,
                        onValueChange = { prioridadId = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    errorMessage?.let {
                        Text(text = it, color = Color.Red)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(
                            onClick = { fecha = ""; cliente = ""; asunto = ""; descripcion = "" }
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Nuevo")
                            Text("Nuevo")
                        }
                        val scope = rememberCoroutineScope()
                        OutlinedButton(
                            onClick = {
                                if (fecha.isBlank() || cliente.isBlank() || asunto.isBlank() || descripcion.isBlank() || prioridadId.isBlank() || tecnicoId.isBlank()) {
                                    errorMessage = "Todos los campos son obligatorios y deben estar completos."
                                }
                                else {

                                    val prioridad = prioridadId.toIntOrNull()
                                    val tecnicoIdInt = tecnicoId.toIntOrNull()

                                    if (prioridad == null || tecnicoIdInt == null) {

                                        errorMessage = "Prioridad y Técnico ID deben ser números válidos."
                                    } else {
                                        scope.launch {

                                            ticketRepository.saveTicket(
                                                TicketEntity(
                                                    fecha = fecha,
                                                    cliente = cliente,
                                                    asunto = asunto,
                                                    descripcion = descripcion,
                                                    prioridadId = prioridad,
                                                    tecnicoId = tecnicoIdInt
                                                )
                                            )

                                            fecha = ""
                                            cliente = ""
                                            asunto = ""
                                            descripcion = ""
                                            prioridadId = ""
                                            tecnicoId = ""

                                            errorMessage = "Ticket guardado exitosamente."

                                            goTicketList()
                                        }
                                    }
                                }
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = "Guardar")
                            Text("Guardar")
                        }
                    }
                }
            }
        }
    }
}