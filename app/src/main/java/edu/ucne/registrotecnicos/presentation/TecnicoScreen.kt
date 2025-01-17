package edu.ucne.registrotecnicos.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrotecnicos.MainActivity.TecnicoEntity
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import kotlinx.coroutines.launch

@Composable
fun TecnicoScreen() {
    var nombre by remember { mutableStateOf("") }
    var sueldo by remember { mutableStateOf("") }
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
                        errorMessage?.let {
                            Text(text = it, color = Color.Red)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        )
                        {
                            OutlinedButton(
                                onClick = {

                                    nombre= ""
                                    sueldo = ""

                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Nuevo"
                                )
                                Text(text = "Nuevo")
                            }
                            Spacer(Modifier.width(100.dp))

                            val scope = rememberCoroutineScope()
                            OutlinedButton(
                                onClick = {
                                    if (nombre.isBlank()) {
                                        errorMessage = "El campo nombre es obligatorio"
                                    } else {
                                        val sueldoD = sueldo.toDouble()
                                        scope.launch {
                                            saveTecnico(
                                                TecnicoEntity(
                                                    nombre = nombre,
                                                    sueldo = sueldoD
                                                )
                                            )
                                            nombre = ""
                                            sueldo = ""
                                        }
                                    }
                                }

                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Guardar"
                                )
                                Text(text = "Guardar")
                            }
                        }

                    }
                    val lifecycleOwner = LocalLifecycleOwner.current
                    val tecnicoList by tecnicoDb.tecnicoDao().getAll()
                        .collectAsStateWithLifecycle(
                            initialValue = emptyList(),
                            lifecycleOwner = lifecycleOwner,
                            minActiveState = Lifecycle.State.STARTED
                        )
                    TecnicoListScreen(tecnicoList)
                }
            }
        }
    }
}