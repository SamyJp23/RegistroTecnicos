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
import androidx.navigation.toRoute
import edu.ucne.registrotecnicos.presentation.tecnico.TecnicoListScreen
import edu.ucne.registrotecnicos.presentation.tecnico.TecnicoScreen
import edu.ucne.registrotecnicos.data.local.dao.TecnicoDao

import edu.ucne.registrotecnicos.data.local.database.TecnicoDb
import edu.ucne.registrotecnicos.data.local.repositories.TecnicoRepository
import edu.ucne.registrotecnicos.data.local.repositories.TicketRepository
import edu.ucne.registrotecnicos.presentation.Home
import edu.ucne.registrotecnicos.presentation.tecnico.DeleteTecnicoScreen
import edu.ucne.registrotecnicos.presentation.tecnico.EditTecnicoScreen
import edu.ucne.registrotecnicos.presentation.ticket.DeleteTicketScreen
import edu.ucne.registrotecnicos.presentation.ticket.EditTicketScreen

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
    val ticketList by tecnicoDb.ticketDao().getAll()
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
            lifecycleOwner = lifecycleOwner,
            minActiveState =  Lifecycle.State.STARTED
        )

    val scope = rememberCoroutineScope()
    val tecnicoRepository = TecnicoRepository(tecnicoDb)
    val ticketRepository = TicketRepository(tecnicoDb)
    NavHost(

        navController = navHostController,
        startDestination = Screen.Home
    ){

        composable<Screen.Home>{
            Home(
               goToTecnico = {
                   navHostController.navigate(Screen.TecnicoList)
               },
                goToTicket = {
                    navHostController.navigate(Screen.TicketList)
                }
            )
        }
        composable<Screen.TecnicoList>{
            TecnicoListScreen(
                tecnicoList = tecnicoList,
                onAddTecnico = {
                    navHostController.navigate(Screen.Tecnico(0))
                },
                onEditTecnico =  { tecnico ->
                    navHostController.navigate(Screen.EditTecnico(tecnico.tecnicoId ?: 0))
                },
                onDeleteTecnico =  { tecnico ->
                    navHostController.navigate(Screen.DeleteTecnico(tecnico.tecnicoId ?: 0))
                }
            )

        }
        composable<Screen.TicketList>{
            TicketListScreen(
                ticketList = ticketList,
                tecnicoList = tecnicoList,
                onAddTicket =   {
                    navHostController.navigate(Screen.Ticket(0))
                },
                onEditTicket =  { ticket ->
                    navHostController.navigate(Screen.EditTicket(ticket.ticketId ?: 0))
                },
                onDeleteTicket =  { ticket ->
                    navHostController.navigate(Screen.DeleteTicket(ticket.ticketId ?: 0))
                }
            )

        }
        composable<Screen.Tecnico>{
            TecnicoScreen(tecnicoDb = tecnicoDb,
                tecnicoRepository = tecnicoRepository,
                goTecnicoList = {navHostController.navigate(Screen.TecnicoList)}
            )
        }
        composable<Screen.Ticket>{
            TicketScreen( tecnicoDb = tecnicoDb,
                tecnicoRepository = tecnicoRepository,
                ticketRepository = ticketRepository,
                goTicketList = {
                    navHostController.navigateUp()
                }
            )
        }
        composable<Screen.EditTecnico>{
            val args = it.toRoute<Screen.EditTecnico>()
            EditTecnicoScreen( tecnicoDb = tecnicoDb,
                tecnicoId = args.tecnicoId ,
                tecnicoRepository = tecnicoRepository,
                goTecnicoList = {
                    navHostController.navigateUp()
                }
            )
        }
        composable<Screen.EditTicket>{
            val args = it.toRoute<Screen.EditTicket>()
            EditTicketScreen( tecnicoDb = tecnicoDb,
                ticketId = args.ticketId ,
                ticketRepository = ticketRepository,
                goTicketList = {
                    navHostController.navigateUp()
                }
            )
        }
        composable<Screen.DeleteTecnico>{
            val args = it.toRoute<Screen.DeleteTecnico>()
            DeleteTecnicoScreen(
                tecnicoDb = tecnicoDb,
                tecnicoId = args.tecnicoId,
                goTecnicoList = {
                    navHostController.navigateUp()
                }
            )
        }
        composable<Screen.DeleteTicket>{
            val args = it.toRoute<Screen.DeleteTicket>()
            DeleteTicketScreen(
                tecnicoDb = tecnicoDb,
                ticketId = args.ticketId,
                goTicketList = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}