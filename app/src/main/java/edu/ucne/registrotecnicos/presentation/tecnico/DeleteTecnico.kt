package edu.ucne.registrotecnicos.presentation.tecnico

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.registrotecnicos.data.local.database.TecnicoDb
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import kotlinx.coroutines.launch

@Composable
fun DeleteTecnicoScreen(
    tecnicoDb: TecnicoDb,
    tecnicoId: Int,
    goTecnicoList: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val tecnico = remember { mutableStateOf<TecnicoEntity?>(null) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(tecnicoId) {
        val t = tecnicoDb.tecnicoDao().find(tecnicoId)
        tecnico.value = t
        isLoading.value = false
    }

    val tecnicoEntity = tecnico.value
    var mensajeError by remember { mutableStateOf("") }
    var mensajeExito by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Text(
                text = "¿Está seguro de Eliminar el Técnico?",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if (isLoading.value) {
                Text(
                    text = "Cargando...",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else if (tecnicoEntity != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Nombre",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            ),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = tecnicoEntity.nombre,
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.Gray
                            ),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )


                        Text(
                            text = "Sueldo",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),
                            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                        )
                        Text(
                            text = "$${tecnicoEntity.sueldo}",
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (mensajeError.isNotEmpty()) {
                    Text(
                        text = mensajeError,
                        color = Color.Red,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                if (mensajeExito.isNotEmpty()) {
                    Text(
                        text = mensajeExito,
                        color = Color.Green,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Button(
                    onClick = {
                        scope.launch {
                            tecnicoDb.tecnicoDao().delete(tecnicoEntity)
                            mensajeExito = "Se elimino el tecnico."
                            launch {
                                kotlinx.coroutines.delay(2000)
                                goTecnicoList()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Text(text = "Eliminar")
                }

                Button(
                    onClick = {
                        goTecnicoList()
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)

                ) {
                    Text(text = "Volver")
                }
            } else {
                Text(
                    text = "Técnico no encontrado.",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.Red
                )
            }
        }
    }
}
