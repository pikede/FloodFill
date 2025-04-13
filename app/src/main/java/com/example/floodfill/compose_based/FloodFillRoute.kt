package com.example.floodfill.compose_based

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.floodfill.ui.theme.FloodFillTheme
import com.example.floodfill.view_based.FloodFillViewModel
import com.example.floodfill.view_based.FloodFillViewModel.Companion.GRID_CELLS
import com.example.floodfill.view_based.FloodFillViewModel.Companion.GRID_SIZE
import com.example.floodfill.view_based.GridItem

private data class ButtonColor(val color: Color, val name: String)

private val buttons = setOf<ButtonColor>(
    ButtonColor(color = Color.Red, name = "Red"),
    ButtonColor(color = Color.Blue, name = "Blue"),
    ButtonColor(color = Color.Green, name = "Green")
)

@Composable
fun FloodFillRoute(modifier: Modifier = Modifier, viewModel: FloodFillViewModel = hiltViewModel()) {
    val gridState by viewModel.gridState.collectAsStateWithLifecycle()
    val isLoading by viewModel.loadingState.collectAsStateWithLifecycle()

    Box(
        modifier
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        when {
            isLoading ->
                LinearProgressIndicator(Modifier.padding(top = 100.dp))

            else -> {
                Grid(
                    gridState = gridState,
                    fillPosition = { row: Int, col: Int -> viewModel.fillOnce(row, col) },
                    onChangeColor = { color: Color -> viewModel.changeFillColor(color) },
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
private fun Grid(
    gridState: MutableList<GridItem>,
    fillPosition: (Int, Int) -> Unit,
    onChangeColor: (Color) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (r in 0 until GRID_CELLS) {
            Row {
                for (c in 0 until GRID_CELLS) {
                    val index = r * GRID_CELLS + c
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(color = Color(gridState[index].color))
                            .clickable { fillPosition(r, c) }
                    )
                }
            }
        }

        Row(modifier.padding(vertical = 42.dp)) {
            buttons.forEach {
                Button(
                    onClick = { onChangeColor(it.color) },
                    colors = ButtonDefaults.buttonColors().copy(it.color),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .background(
                            color = it.color,
                            shape = RoundedCornerShape(0, 0, 0)
                        )
                ) {
                    Text(text = it.name, color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FloodFillPreview() {
    FloodFillTheme {
        Grid(
            gridState = MutableList(GRID_SIZE) { GridItem() },
            fillPosition = { _, _ -> },
            onChangeColor = { _ -> },
        )
    }
}