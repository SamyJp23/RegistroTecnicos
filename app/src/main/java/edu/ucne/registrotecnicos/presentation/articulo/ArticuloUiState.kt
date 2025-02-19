package edu.ucne.registrotecnicos.presentation.articulo

import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity
import edu.ucne.registrotecnicos.data.remote.dto.ArticuloDto

data class ArticuloUiState (
    val articuloId: Int = 0,
    val descripcion: String = "",
    val costo: String = "",
    val ganancia: String = "",
    val precio: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val articulos: List<ArticuloDto> = emptyList()
)
