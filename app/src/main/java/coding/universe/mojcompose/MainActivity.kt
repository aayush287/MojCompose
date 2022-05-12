package coding.universe.mojcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coding.universe.mojcompose.components.HomeViewModel
import coding.universe.mojcompose.ui.theme.MojComposeTheme
import coding.universe.mojcompose.components.home.HomeScreen
import coding.universe.mojcompose.components.home.MojCreateIcon
import coding.universe.mojcompose.components.profile.ProfileScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MojComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MojAppContent()
                }
            }
        }
    }

}


@Composable
fun MojAppContent() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { MojBottomNavigation(navController = navController) },
        content = { MojBodyContent(navController = navController) }
    )
}


@Composable
fun MojBottomNavigation(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.arguments?.getString("route")

    BottomNavigation(backgroundColor = Color.Black) {
        MojDemoDataProvider.bottomBarList.forEach { mojScreens ->
            BottomNavigationItem(
                icon = { BottomBarIcon(mojScreens) },
                selected = currentRoute == mojScreens.route,
                onClick = {
                    navController.popBackStack(navController.graph.startDestinationId, false)
                    if (currentRoute != mojScreens.route) {
                        navController.navigate(mojScreens.route)
                    }
                },
                label = {
                    if (mojScreens != MojScreens.Create) {
                        Text(text = mojScreens.route)
                    }
                },
            )
        }
    }
}

@Composable
fun BottomBarIcon(screen: MojScreens) {
    when (screen) {
        MojScreens.Home -> Icon(imageVector = Icons.Filled.Home, contentDescription = null)
        MojScreens.Discover -> Icon(imageVector = Icons.Filled.Search, contentDescription = null)
        MojScreens.Create -> MojCreateIcon()
        MojScreens.Inbox -> Icon(imageVector = Icons.Filled.Email, contentDescription = null)
        MojScreens.Me -> Icon(imageVector = Icons.Filled.Person, contentDescription = null)
    }
}

@Composable
fun MojBodyContent(navController: NavHostController) {
    NavHost(navController, startDestination = MojScreens.Home.route) {
        composable(MojScreens.Home.route) {
            HomeScreen(mojInteractionEvents = { handleInteractionEvent(it, navController) })
        }
        composable(MojScreens.Discover.route) { Text(text = "Create:TODO") }
        composable(MojScreens.Create.route) { Text(text = "Create:TODO") }
        composable(MojScreens.Inbox.route) { Text(text = "Inbox:TODO") }
        composable(MojScreens.Me.route) { ProfileScreen("10", navController) }
        // This navigation is for going to user profile but it should be moved to separate place
        composable("${MojScreens.Profile.route}/{userId}") { backStackEntry ->
            ProfileScreen(backStackEntry.arguments?.getString("userId")!!, navController)
        }
    }
}

fun handleInteractionEvent(
    mojHomeInteractionEvents: MojHomeInteractionEvents,
    navController: NavHostController
) {
    when (mojHomeInteractionEvents) {
        is MojHomeInteractionEvents.OpenProfile -> {
            navController.navigate("${MojScreens.Profile.route}/${mojHomeInteractionEvents.post.id}")
        }
        else -> {
            //TODO
        }
    }
}

