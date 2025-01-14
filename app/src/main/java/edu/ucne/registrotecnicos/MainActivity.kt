package edu.ucne.registrotecnicos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Upsert
import edu.ucne.registrotecnicos.ui.theme.RegistroTecnicosTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var tecnicoDb: TecnicoDb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        tecnicoDb = Room.databaseBuilder(
            applicationContext,
            TecnicoDb::class.java,
            "Tecnicos.db"
        ).fallbackToDestructiveMigrationFrom()
            .build()

        setContent {
            RegistroTecnicosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()){innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ){
                        TecnicoScreen()
                    }
                }

                }
            }
        }
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

                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Nuevo"
                                    )
                                    Text(text = "Nuevo")
                                }
                                val scope = rememberCoroutineScope()
                                OutlinedButton(
                                    onClick = {
                                        if (nombre.isBlank()) {
                                            errorMessage = "Nombre vacio"
                                        } else {
                                            val sueldoD = sueldo.toDouble()
                                            scope.launch {
                                                saveTecnico(
                                                    TecnicoEntity(
                                                        nombre = nombre,
                                                        sueldo = sueldoD
                                                    )
                                                )
                                                nombre = "",
                                                sueldo = ""
                                            }
                                        }
                                    }

                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Guardar"
                                    )
                                    Text(text = "Guardar")
                                }
                            }

                        }
                        val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
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

private suspend fun saveTecnico(tecnico: TecnicoEntity){
    tecnicoDb.tecnicoDao().save(tecnico)
}
    @Entity(tableName = "Tecnicos")
    data class TecnicoEntity(
        @PrimaryKey
        val tecnicoId: Int? = null,
        val nombre: String = "",
        val sueldo: Double
    )

    @Dao
    interface TecnicoDao{
        @Upsert
        suspend fun save(tecnico: TecnicoEntity)

        @Query(
            """
            SELECT * FROM Tecnicos
            WHERE tecnicoId = :id
            LIMIT 1
        """)
        suspend fun find(id: Int): TecnicoEntity?

        @Delete
        suspend fun delete(tecnico: TecnicoEntity)

        @Query("SELECT * FROM Tecnicos")
        fun getAll(): Flow<List<TecnicoEntity>>
    }

    @Database(
        entities = [
            TecnicoEntity::class
        ],
        version = 1,
        exportSchema = false
    )
    abstract class TecnicoDb : RoomDatabase(){
        abstract fun tecnicoDao(): TecnicoDao
    }

}




