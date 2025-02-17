package edu.ucne.registrotecnicos.presentation.tecnico

import edu.ucne.registrotecnicos.data.local.entities.TecnicoEntity

data class TecnicoUiState(
    val tecnicoId: Int? = null,
    val nombre: String = "",
    val sueldo: Double? = null,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val tecnicos: List<TecnicoEntity> = emptyList()
)
