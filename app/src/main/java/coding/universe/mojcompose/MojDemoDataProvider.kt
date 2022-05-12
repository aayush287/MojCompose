package coding.universe.mojcompose

import androidx.compose.ui.graphics.Color

object MojDemoDataProvider {

    val bottomBarList = listOf(
        MojScreens.Home,
        MojScreens.Discover,
        MojScreens.Create,
        MojScreens.Inbox,
        MojScreens.Me
    )

//    val lanes =
//        listOf(
//            "OhHO ohNO",
//            "FunFacts",
//            "HappyDeepavli",
//            "HalloweenIsHere",
//            "BoomBoom",
//            "No no no no"
//        )

    val customGray = Color.LightGray.copy(alpha = 0.5f)

    val videos = listOf("t1.mp4", "t2.mp4", "t3.mp4")
}