package coding.universe.mojcompose.components.profile

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import coding.universe.mojcompose.Album

@Composable
fun ProfileAppBar(album: Album, navHostController: NavHostController) {
    TopAppBar(
        title = { Text(text = album.artist) },
        backgroundColor = MaterialTheme.colors.surface,
        navigationIcon = {
            IconButton(onClick = { navHostController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Back"
                )
            }
        }
    )
}
