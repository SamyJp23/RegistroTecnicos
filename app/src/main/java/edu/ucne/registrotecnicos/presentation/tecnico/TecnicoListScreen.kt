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

val verde = Color(0xFF016914)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TecnicoListScreen(
    viewModel: TecnicoViewModel = hiltViewModel(),
    createTecnico: () -> Unit,
    goToMenu: () -> Unit,
    goToTecnico: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Técnicos") }

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { createTecnico() },
                containerColor = Color.Blue,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Crear Tecnico"
                )
            }
        }
    ) { innerPadding ->
        TecnicoListBodyScreen(
            uiState = uiState,
            createTecnico = createTecnico,
            goToMenu = goToMenu,
            goToTecnico = goToTecnico,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun TecnicoListBodyScreen(
    uiState: TecnicoUiState,
    createTecnico: () -> Unit,
    goToMenu: () -> Unit,
    goToTecnico: (Int) -> Unit,
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

                items(uiState.tecnicos) {
                    TecnicoRow(it, goToTecnico)
                }
            }
        }
    }
}



@Composable
private fun TecnicoRow(
    it: TecnicoEntity,
    goToTecnico: (Int) -> Unit
) {

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable { goToTecnico(it.tecnicoId!!) },
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
                    text = "TécnicoId: ${it.tecnicoId}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Text(
                    text = "Nombre: ${it.nombre}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White
                    )
                )
                Text(
                    text = "Sueldo: ${it.sueldo}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}