package coding.universe.mojcompose.components.profile

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import coding.universe.mojcompose.Album
import coding.universe.mojcompose.AlbumsDataProvider
import coding.universe.mojcompose.ui.theme.MojComposeTheme


@Composable
fun ProfileScreen(userId: String = "10", navHostController: NavHostController) {
    val album: Album = AlbumsDataProvider.albums.first { it.id.toString() == userId }
    MojComposeTheme(darkTheme = false) {
        Scaffold(
            topBar = { ProfileAppBar(album, navHostController) }
        ) {
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                item { ProfileTopSection(album) }
            }
        }
    }
}





