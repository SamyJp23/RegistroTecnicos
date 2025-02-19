package edu.ucne.registrotecnicos.data.local.repositories

import edu.ucne.registrotecnicos.data.local.database.TecnicoDb
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import edu.ucne.registrotecnicos.data.local.entities.TicketEntity
import edu.ucne.registrotecnicos.data.local.dao.TicketDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val tecnicoDb: TecnicoDb

){
    suspend fun saveTicket(ticket: TicketEntity){
        tecnicoDb.ticketDao().save(ticket)
    }
    suspend fun delete(ticket: TicketEntity){
        return tecnicoDb.ticketDao().delete(ticket)
    }
    fun getTicketsTecnico(tecnicoId: Int): Flow<List<TicketEntity>> {
        return tecnicoDb.ticketDao().getTicketsTecnico(tecnicoId)
    }
    suspend fun responderTicket(ticketId: Int, respuesta: String) {
        tecnicoDb.ticketDao().updateRespuesta(ticketId, respuesta)
    }
    suspend fun updateTicket(ticket: TicketEntity) {
        tecnicoDb.ticketDao().update(ticket)
    }

    suspend fun find(id: Int): TicketEntity?{
        return tecnicoDb.ticketDao().find(id)
    }

    suspend fun getAll(): Flow<List<TicketEntity>> {
        return tecnicoDb.ticketDao().getAll()
    }
}