package edu.ucne.registrotecnicos.presentation.tecnico

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity

@Composable
fun TecnicoListScreen(tecnicoList: List<TecnicoEntity>,
                      onAddTecnico: () -> Unit,
                      onDeleteTecnico: (TecnicoEntity) -> Unit,
                      onEditTecnico: (TecnicoEntity) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onAddTecnico,
                    modifier = Modifier.padding(16.dp),
                    containerColor = Color.White,
                    contentColor = Color.Black

                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Añadir Técnico")
                }
            },
            content = { paddingValues ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Lista de Técnicos",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                    )

                    if (tecnicoList.isEmpty()) {
                        Text(
                            text = "No hay técnicos disponibles",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center
                            )
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(tecnicoList) { tecnico ->
                                TecnicoRow(tecnico, onEditTecnico, onDeleteTecnico)
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun TecnicoRow(
    tecnico: TecnicoEntity,
    onEditTecnico: (TecnicoEntity) -> Unit,
    onDeleteTecnico: (TecnicoEntity) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable(onClick = { expanded = !expanded })) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "ID: ${tecnico.tecnicoId}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Nombre: ${tecnico.nombre}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Sueldo: ${tecnico.sueldo}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(Modifier.weight(1f))
            IconButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "Más opciones")
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {Text("Editar")} ,
                        onClick = {
                onEditTecnico(tecnico)
                expanded = false
            })


            DropdownMenuItem(
                text = {Text("Eliminar")},
                onClick = {
                onDeleteTecnico(tecnico)
                expanded = false
            })
        }
        HorizontalDivider()
    }
}
