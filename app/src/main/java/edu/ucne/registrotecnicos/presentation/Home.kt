package edu.ucne.registrotecnicos.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Home(
    goToTecnico: () -> Unit,
    goToTicket: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Registro Tecnicos/Tickets",
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(vertical = 11.dp)
        )

        Spacer(modifier = Modifier.height(200.dp))

        Card(
            modifier = Modifier
                .width(200.dp)
                .padding(vertical = 8.dp)
                .clickable(onClick = goToTecnico),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Técnico",
                    tint = Color.Black,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = "Técnicos",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )
            }
        }

        Card(
            modifier = Modifier
                .width(200.dp)
                .padding(vertical = 12.dp)
                .clickable(onClick = goToTicket),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Call,
                    contentDescription = "Tickets",
                    tint = Color.Black,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = "Tickets",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )
            }
        }
    }
}