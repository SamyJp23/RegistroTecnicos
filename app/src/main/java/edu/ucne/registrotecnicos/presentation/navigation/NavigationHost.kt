package edu.ucne.registrotecnicos.presentation.navigation

import Home
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

import edu.ucne.registrotecnicos.data.local.database.TecnicoDb
import edu.ucne.registrotecnicos.data.local.repositories.TecnicoRepository
import edu.ucne.registrotecnicos.data.local.repositories.TicketRepository
import edu.ucne.registrotecnicos.presentation.tecnico.ArticuloListScreen
import edu.ucne.registrotecnicos.presentation.tecnico.ArticuloScreen
import edu.ucne.registrotecnicos.presentation.tecnico.DeleteTecnicoScreen
import edu.ucne.registrotecnicos.presentation.tecnico.EditTecnicoScreen
import edu.ucne.registrotecnicos.presentation.tecnico.TecnicoTicketsScreen
import edu.ucne.registrotecnicos.presentation.tecnico.TicketListScreen
import edu.ucne.registrotecnicos.presentation.tecnico.TicketScreen
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
    val ticketRepository = TicketRepository(tecnicoDb )
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
                },
                goToArticulo = {
                    navHostController.navigate(Screen.ArticuloListScreen)
                }

            )
        }
        composable<Screen.TecnicoList>{
            TecnicoListScreen(
                createTecnico = { navHostController.navigate(Screen.Tecnico(0)) },
                goToMenu = { navHostController.navigateUp() },
                goToTecnico = { tecnicoId ->
                    navHostController.navigate(Screen.Tecnico(tecnicoId = tecnicoId))
                }
            )



        }
        composable<Screen.TicketList>{
            TicketListScreen(
                createTicket = { navHostController.navigate(Screen.Ticket(0)) },
                goToMenu = { navHostController.navigateUp() },
                goToTicket = { ticketId ->
                    navHostController.navigate(Screen.Ticket(ticketId = ticketId))
                }
            )

        }
        composable<Screen.ArticuloScreen>{
            val articuloId = it.toRoute<Screen.ArticuloScreen>().articuloId
          ArticuloScreen(
              articuloId = articuloId,
              goBackToList = {navHostController.navigateUp()}

          )
        }

        composable<Screen.ArticuloListScreen> {
            ArticuloListScreen(
                createArticulo = { navHostController.navigate(Screen.ArticuloScreen(0)) },
                goToMenu = { navHostController.navigateUp() },
                goToArticulo = { articuloId ->
                    navHostController.navigate(Screen.ArticuloScreen(articuloId = articuloId))
                }
            )
        }
        composable<Screen.Tecnico> {
            val tecnicoId = it.toRoute<Screen.Tecnico>().tecnicoId
            TecnicoScreen(
                tecnicoId = tecnicoId,
                goBackToList = {navHostController.navigateUp()}

            )
        }
        composable<Screen.Ticket>{ val ticketId = it.toRoute<Screen.Ticket>().ticketId
            TicketScreen(
                ticketId = ticketId,
                goBackToList = {navHostController.navigateUp()}

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
        composable<Screen.TecnicoTickets>{
            val args = it.toRoute<Screen.TecnicoTickets>()
            TecnicoTicketsScreen(
               tecnicoId = args.tecnicoId,
                ticketRepository = ticketRepository,
                goTecnicoTickets =  {
                    navHostController.navigateUp()
                }
            )
        }
    }
}