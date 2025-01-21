package edu.ucne.registrotecnicos.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.registrotecnicos.presentation.tecnico.TecnicoListScreen
import edu.ucne.registrotecnicos.presentation.tecnico.TecnicoScreen
import edu.ucne.registrotecnicos.data.local.dao.TecnicoDao

import edu.ucne.registrotecnicos.data.local.database.TecnicoDb
import edu.ucne.registrotecnicos.data.local.repositories.TecnicoRepository

@Composable
fun AppNavHost(tecnicoDb: TecnicoDb, navHostController: NavHostController
){
    val lifecycleOwner = LocalLifecycleOwner.current
    val tecnicoList by tecnicoDb.tecnicoDao().getAll()
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
            lifecycleOwner = lifecycleOwner,
            minActiveState = Lifecycle.State.STARTED
        )

    val scope = rememberCoroutineScope()
    val tecnicoRepository = TecnicoRepository(tecnicoDb)
    NavHost(

        navController = navHostController,
        startDestination = Screen.TecnicoList
    ){
        composable<Screen.TecnicoList>{
            TecnicoListScreen(
                tecnicoList = tecnicoList,
                onAddTecnico = {navHostController.navigate(Screen.Tecnico(0))}
            )

        }
        composable<Screen.Tecnico>{
            TecnicoScreen(
                goTecnicoList = {navHostController.navigate(Screen.TecnicoList)}
            )
        }
    }
}