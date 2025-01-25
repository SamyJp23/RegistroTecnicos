package edu.ucne.registrotecnicos.presentation.tecnico

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
import edu.ucne.registrotecnicos.data.local.repositories.TecnicoRepository
import kotlinx.coroutines.launch

@Composable
fun EditTecnicoScreen(
    tecnicoDb: TecnicoDb,
    tecnicoId: Int,
    goTecnicoList: () -> Unit,
    tecnicoRepository: TecnicoRepository
) {
    val scope = rememberCoroutineScope()
    var tecnico by remember { mutableStateOf<TecnicoEntity?>(null) }
    var nombre by remember { mutableStateOf("") }
    var sueldo by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(tecnicoId) {
        val loadedTecnico = tecnicoRepository.find(tecnicoId)
        tecnico = loadedTecnico
        if (loadedTecnico != null) {
            nombre = loadedTecnico.nombre
            sueldo = loadedTecnico.sueldo.toString()
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
                            label = { Text(text = "Nombre") },
                            value = nombre,
                            onValueChange = { nombre = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text(text = "Sueldo") },
                            value = sueldo,
                            onValueChange = { sueldo = it },
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
                                    tecnico?.let {
                                        nombre = it.nombre
                                        sueldo = it.sueldo.toString()
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
                                    if (nombre.isBlank()) {
                                        errorMessage = "El campo nombre es obligatorio"
                                    } else {
                                        val sueldoD = sueldo.toDoubleOrNull()
                                        if (sueldoD == null) {
                                            errorMessage = "El sueldo debe ser un número válido"
                                        } else {
                                            scope.launch {
                                                tecnicoRepository.updateTecnico(
                                                    tecnico!!.copy(
                                                        nombre = nombre,
                                                        sueldo = sueldoD
                                                    )
                                                )
                                                goTecnicoList()
                                            }
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
