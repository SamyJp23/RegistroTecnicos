package edu.ucne.registrotecnicos.data.remote.dto

import edu.ucne.registrotecnicos.data.remote.ArticuloManagerApi
import javax.inject.Inject

class DataSource @Inject constructor(
    private val articuloManagerApi: ArticuloManagerApi
){
    suspend fun getArticulos() = articuloManagerApi.getArticulos()

    suspend fun getArticulo(id: Int) = articuloManagerApi.getArticulo(id)

    suspend fun saveArticulo(articuloDto: ArticuloDto) = articuloManagerApi.saveArticulo(articuloDto)

    suspend fun deleteArticulo(id: Int) = articuloManagerApi.deleteArticulo(id)

    suspend fun updateArticulo(id: Int, articuloDto: ArticuloDto) = articuloManagerApi.actualizarArticulo(id, articuloDto)


}