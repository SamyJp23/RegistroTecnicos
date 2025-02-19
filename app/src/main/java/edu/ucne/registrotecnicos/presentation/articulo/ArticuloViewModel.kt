package edu.ucne.registrotecnicos.presentation.articulo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrotecnicos.data.local.repositories.ArticuloRepository
import edu.ucne.registrotecnicos.data.remote.Resource
import edu.ucne.registrotecnicos.data.remote.dto.ArticuloDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticuloViewModel @Inject constructor(
    private val articuloRepository: ArticuloRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArticuloUiState())
    val uiState = _uiState.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        getArticulos()
    }

    fun saveArticulo() {
        viewModelScope.launch {
            if (isValid()) {
                articuloRepository.saveArticulo(_uiState.value.toEntity())
            }
        }
    }

    fun delete(articuloId: Int) {
        viewModelScope.launch {
            articuloRepository.delete(articuloId)
        }
    }
    fun refreshArticulos() {
        getArticulos()
    }
    fun update() {
        viewModelScope.launch {
            articuloRepository.update(
                _uiState.value.articuloId, ArticuloDto(
                    articuloId = _uiState.value.articuloId,
                    descripcion = _uiState.value.descripcion,
                    costo = _uiState.value.costo.toDouble(),
                    ganancia = _uiState.value.ganancia.toDouble(),
                    precio = _uiState.value.precio.toDouble()
                )
            )
        }
    }

    fun new() {
        _uiState.value = ArticuloUiState()
    }

    fun onDescripcionChange(descripcion: String) {
        _uiState.update {
            it.copy(
                descripcion = descripcion,
                errorMessage = if (descripcion.isBlank()) "Debes rellenar el campo Descripción"
                else null
            )
        }
    }

    fun onCostoChange(costo: String) {
        _uiState.update {
            val costoDouble = costo.toDoubleOrNull()
            val precioDouble = it.precio.toDoubleOrNull()

            val gananciaDouble = if (costoDouble != null && precioDouble != null && costoDouble > 0) {
                ((precioDouble - costoDouble) / costoDouble) * 100
            } else null

            it.copy(
                costo = costo,
                ganancia = gananciaDouble?.toString() ?: "",
                errorMessage = when {
                    costoDouble == null -> "Valor no numérico"
                    costoDouble <= 0 -> "Debe ingresar un valor mayor a 0"
                    else -> null
                }
            )
        }
    }

    fun onPrecioChange(precio: String) {
        _uiState.update {
            val precioDouble = precio.toDoubleOrNull()
            val costoDouble = it.costo.toDoubleOrNull()

            val gananciaDouble = if (costoDouble != null && precioDouble != null && costoDouble > 0) {
                ((precioDouble - costoDouble) / costoDouble) * 100
            } else null

            it.copy(
                precio = precio,
                ganancia = gananciaDouble?.toString() ?: "",
                errorMessage = when {
                    precioDouble == null -> "Valor no numérico"
                    precioDouble <= 0 -> "Debe ingresar un valor mayor a 0"
                    else -> null
                }
            )
        }
    }

    fun onGananciaChange(ganancia: String) {
        _uiState.update {
            val gananciaDouble = ganancia.toDoubleOrNull()
            val costoDouble = it.costo.toDoubleOrNull()

            val precio = if (costoDouble != null && gananciaDouble != null) {
                (costoDouble * gananciaDouble / 100) + costoDouble
            } else null

            it.copy(
                ganancia = ganancia,
                precio = precio?.toString() ?: "",
                errorMessage = when {
                    gananciaDouble == null -> "Valor no numérico"
                    gananciaDouble < 0 -> "Debe ser 0 o mayor"
                    else -> null
                }
            )
        }
    }



    fun find(articuloId: Int) {
        viewModelScope.launch {
            if (articuloId > 0) {
                val articuloDto = articuloRepository.find(articuloId)
                if (articuloDto.articuloId != 0) {
                    _uiState.update {
                        it.copy(
                            articuloId = articuloDto.articuloId,
                            descripcion = articuloDto.descripcion,
                            costo = articuloDto.costo.toString(),
                            ganancia = articuloDto.ganancia.toString(),
                            precio = articuloDto.precio.toString()
                        )
                    }
                }
            }
        }
    }

    fun getArticulos() {
        viewModelScope.launch {
            articuloRepository.getArticulos().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                articulos = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = result.message ?: "Error desconocido",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun isValid(): Boolean {
        return uiState.value.descripcion.isNotBlank() &&
                uiState.value.costo.isNotBlank() &&
                uiState.value.ganancia.isNotBlank() &&
                uiState.value.precio.isNotBlank()
    }
}

fun ArticuloUiState.toEntity() = ArticuloDto(
    articuloId = articuloId,
    descripcion = descripcion,
    costo = costo.toDouble(),
    ganancia = ganancia.toDouble(),
    precio = precio.toDouble()
)