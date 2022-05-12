package coding.universe.mojcompose.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun MojCreateIcon() {
    Box(contentAlignment = Alignment.Center) {
        Icon(
            imageVector = Icons.Filled.Add,
            tint = Color.Black,
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(vertical = 4.dp, horizontal = 8.dp)
        )
    }
}