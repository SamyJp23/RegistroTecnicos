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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity

@Composable
fun TecnicoListScreen(
    viewModel: TecnicoViewModel = hiltViewModel(),
    createTecnico: () -> Unit,
    goToMenu: () -> Unit,
    goToTecnico: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TecnicoListBodyScreen(
        uiState,
        createTecnico,
        goToMenu,
        goToTecnico
    )
}

@Composable
fun TecnicoListBodyScreen(
    uiState: TecnicoUiState,
    createTecnico: () -> Unit,
    goToMenu: () -> Unit,
    goToTecnico: (Int) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    TecnicoHeaderRow()
                }
                items(uiState.tecnicos) {
                    TecnicoRow(it, goToTecnico)
                }
            }
        }

        FloatingActionButton(
            onClick = { createTecnico() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color.Gray,
            contentColor = Color.White

        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Crear Tecnico"
            )
        }
    }
}


@Composable
private fun TecnicoHeaderRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(Color.Gray)
            .padding(vertical = 30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f)
                .padding(end = 8.dp),
            text = "ID",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )
        Text(
            modifier = Modifier.weight(2f),
            text = "Nombre",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.White

        )
        Text(
            modifier = Modifier.weight(2f),
            text = "Sueldo",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.White

        )


    }
    Divider(modifier = Modifier.padding(horizontal = 16.dp))
}

@Composable
private fun TecnicoRow(
    it: TecnicoEntity,
    goToTecnico: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                goToTecnico(it.tecnicoId!!)

            },
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = it.tecnicoId.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
            Text(
                modifier = Modifier.weight(2f),
                text = it.nombre,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            Text(
                modifier = Modifier.weight(2f),
                text = it.sueldo.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )


        }
    }
}

