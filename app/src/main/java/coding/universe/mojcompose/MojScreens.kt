package coding.universe.mojcompose

sealed class MojScreens(val route: String){
    object Home : MojScreens("Home")
    object Discover : MojScreens("Discover")
    object Create : MojScreens("Create")
    object Inbox : MojScreens("Inbox")
    object Me : MojScreens("Me")
    object Profile : MojScreens("Profile")
}
