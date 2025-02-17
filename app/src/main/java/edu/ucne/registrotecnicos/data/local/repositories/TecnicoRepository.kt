package edu.ucne.registrotecnicos.data.local.repositories

import edu.ucne.registrotecnicos.data.local.database.TecnicoDb
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import kotlinx.coroutines.flow.Flow

class TecnicoRepository(
    private val tecnicoDb: TecnicoDb
) {
    suspend fun saveTecnico(tecnico: TecnicoEntity){
        tecnicoDb.tecnicoDao().save(tecnico)
    }
    suspend fun updateTecnico(tecnico: TecnicoEntity) {
        tecnicoDb.tecnicoDao().update(tecnico)
    }

    suspend fun find(id: Int): TecnicoEntity?{
        return tecnicoDb.tecnicoDao().find(id)
    }
    suspend fun delete(tecnico: TecnicoEntity){
        return tecnicoDb.tecnicoDao().delete(tecnico)
    }
    fun getAll(): Flow<List<TecnicoEntity>> {
        return tecnicoDb.tecnicoDao().getAll()
    }
}