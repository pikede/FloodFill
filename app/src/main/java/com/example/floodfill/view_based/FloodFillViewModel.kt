package com.example.floodfill.view_based

import android.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class FloodFillViewModel @Inject constructor() : ViewModel() {
    val currentColor = MutableStateFlow<Int>(Color.GRAY)
    private val _gridState = MutableStateFlow<MutableList<GridItem>>(mutableListOf())
    val gridState = _gridState.asStateFlow()
    private val _isLoadingState = MutableStateFlow<Boolean>(false)
    val loadingState = _isLoadingState.asStateFlow()

    var job : Job = Job()
    init {
        getGrids()
    }

    companion object {
        const val GRID_CELLS = 4
        const val GRID_SIZE = GRID_CELLS * GRID_CELLS
    }

    private fun getGrids() {
        val grid = MutableList(GRID_SIZE) { GridItem(color = currentColor.value) }

        val random = Random.nextInt(0, GRID_CELLS)
        grid[random].color = currentColor.value
        _gridState.value = grid
        currentColor.value = Color.RED
    }

    fun fillOnce(row: Int, col: Int) {
        // prevents multiple calls to fillOnceCoroutine, if a job is currently running, cancel it
        if(job.isActive) job.cancel()
        job = fillOnceCoroutine(row, col)
    }

    private fun fillOnceCoroutine(row: Int, col: Int) = viewModelScope.launch(Dispatchers.IO) {
        _isLoadingState.value = true
        val index = row * GRID_CELLS + col // index in grid

        if (row !in 0 until GRID_CELLS
            || col !in 0 until GRID_CELLS
            || _gridState.value[index].color == currentColor.value
        ) {
            _isLoadingState.value = false
            return@launch
        }

        val lastGrid = _gridState.value.map { it.copy() }.toMutableList()
        lastGrid[index].color = currentColor.value
        val directions = listOf(0 to 1, 1 to 0, -1 to 0, 0 to -1)
        for ((dr, dc) in directions) {
            val r = dr + row
            val c = dc + col
            val nextIndex = r * GRID_CELLS + c
            if (r in 0 until GRID_CELLS
                && c in 0 until GRID_CELLS
                && lastGrid[nextIndex].color != currentColor.value
            ) {
                lastGrid[nextIndex].color = currentColor.value
            }
        }

        delay(2000)
        _gridState.value = lastGrid
        _isLoadingState.value = false
    }

    fun changeFillColor(color: Int) {
        currentColor.value = color
    }

    fun changeFillColor(color: androidx.compose.ui.graphics.Color) {
        currentColor.value = color.toArgb()
    }
}