package edu.ucne.registrotecnicos.data.local.repository

import edu.ucne.registrotecnicos.data.local.dao.TecnicoDao
import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import javax.inject.Inject

class TecnicoRepository @Inject constructor(
    private val tecnicoDao: TecnicoDao
){
    suspend fun save(tecnico: TecnicoEntity) = tecnicoDao.save(tecnico)
    suspend fun delete(tecnico: TecnicoEntity) = tecnicoDao.delete(tecnico)
    fun getAll() = tecnicoDao.getAll()
    suspend fun find(id: Int) = tecnicoDao.find(id)
}