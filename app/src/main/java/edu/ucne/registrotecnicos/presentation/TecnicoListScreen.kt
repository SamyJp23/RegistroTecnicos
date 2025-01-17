package edu.ucne.registrotecnicos.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity

@Composable
fun TecnicoListScreen(tecnicoList: List<TecnicoEntity>){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(32.dp))
        Text("Lista de técnicos", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            items(tecnicoList){
                TecnicoRow(it)
            }
        }
    }

}
@Composable
private fun TecnicoRow(tecnico: TecnicoEntity) {
    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "ID:",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = tecnico.tecnicoId.toString(),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(3f)
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Nombre:",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = tecnico.nombre,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(3f)
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Sueldo:",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = tecnico.sueldo.toString(),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(3f)
            )
        }
        HorizontalDivider()
    }
}