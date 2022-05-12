package coding.universe.mojcompose.components.home


import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import coding.universe.mojcompose.*
import coding.universe.mojcompose.R
import coding.universe.mojcompose.components.HomeViewModel
import coding.universe.mojcompose.repository.model.VideoData
import coding.universe.mojcompose.ui.theme.Typography
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import androidx.lifecycle.viewmodel.compose.*
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

private const val TAG = "HomeScreen"

@Composable
fun <T> rememberStateWithLifecycle(
    stateFlow: StateFlow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): State<T> {
    val initialValue = remember(stateFlow) { stateFlow.value }
    return produceState(
        key1 = stateFlow, key2 = lifecycle, key3 = minActiveState,
        initialValue = initialValue
    ) {
        lifecycle.repeatOnLifecycle(minActiveState) {
            stateFlow.collect {
                this@produceState.value = it
            }
        }
    }
}

@Composable
fun HomeScreen(
    mojInteractionEvents: (MojHomeInteractionEvents) -> Unit
) {
    HomeScreen(viewModel = hiltViewModel(), mojInteractionEvents = mojInteractionEvents)
}

@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel,
    mojInteractionEvents: (MojHomeInteractionEvents) -> Unit
) {
    val state by rememberStateWithLifecycle(stateFlow = viewModel.homeViewState)
    HomeScreen(state, mojInteractionEvents = mojInteractionEvents)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun HomeScreen(
    homeViewState: HomeViewState,
    mojInteractionEvents: (MojHomeInteractionEvents) -> Unit
) {
    LogCompositions(tag = "HomeScreen")
    val bottomBarHeight = 50.dp
    val pagerState = rememberPagerState(0)
    when (homeViewState) {
        is HomeViewState.Finished -> {
            val videos = homeViewState.videos ?: emptyList()

            VerticalPager(
                count = videos.size - 1,
                state = pagerState,
            ) {
                val videoData = videos[this.currentPage]
                val isSelected = pagerState.currentPage == this.currentPage
                PagerItem(videoData, isSelected, mojInteractionEvents)
            }

        }
    }

}


@Composable
fun PagerItem(
    post: VideoData,
    selected: Boolean,
    mojInteractionEvents: (MojHomeInteractionEvents) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(4.dp))
    ) {
        MojPlayer(post.id, post.videoUrl, selected)
        VideoOverLayUI(post, mojInteractionEvents)
    }
}

@Composable
fun VideoOverLayUI(post: VideoData, mojInteractionEvents: (MojHomeInteractionEvents) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        VideoInfoSection(Modifier.weight(1f), post)
        VideoIconsSection(post, mojInteractionEvents)
    }
}

@Composable
fun VideoIconsSection(
    post: VideoData,
    mojInteractionEvents: (MojHomeInteractionEvents) -> Unit
) {
    LogCompositions(tag = "VideoIconsSection")
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ProfileImageWithFollow(
            modifier = Modifier
                .size(64.dp)
                .clickable(onClick = {
                    mojInteractionEvents(
                        MojHomeInteractionEvents.OpenProfile(post)
                    )
                }),
            true,
            post.profilePic
        )
        Spacer(modifier = Modifier.height(20.dp))
        LikeIcon(post.id)
        Text(
            text = "256.4k",
            style = MaterialTheme.typography.body2.copy(fontSize = 12.sp),
            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_comment_dots_solid),
            contentDescription = null
        )
        Text(
            text = "1223",
            style = MaterialTheme.typography.body2.copy(fontSize = 12.sp),
            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
        )
        Icon(painter = painterResource(id = R.drawable.ic_share_solid), contentDescription = null)
        Text(
            text = "238",
            style = MaterialTheme.typography.body2.copy(fontSize = 12.sp),
            modifier = Modifier.padding(top = 4.dp, bottom = 32.dp)
        )
        val rotation = remember { Animatable(0f) }
        LaunchedEffect(Unit) {
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = repeatable(
                    iterations = 10000,
                    animation = tween(durationMillis = 3500, easing = LinearEasing),
                ),
            )
        }
        ProfileImageWithFollow(
            modifier = Modifier
                .size(64.dp)
                .graphicsLayer(rotationZ = rotation.value),
            false,
            post.musicPic
        )
    }
}

@Composable
fun LikeIcon(id: Int) {
    var fav by remember(id) { mutableStateOf(false) }
    val animatedProgress = remember { Animatable(0f) }
    if (!fav) {
        LaunchedEffect(fav) {
            animatedProgress.animateTo(
                targetValue = 1.3f,
                animationSpec = tween(600),
            )
        }
    }
    Icon(
        painter = painterResource(id = R.drawable.ic_heart_solid),
        contentDescription = null,
        modifier = Modifier
            .clickable(onClick = { fav = !fav })
            .graphicsLayer(scaleX = animatedProgress.value, scaleY = animatedProgress.value),
        tint = animateColorAsState(if (fav) Color.Red else Color.White).value
    )
}

@Composable
fun VideoInfoSection(modifier: Modifier, post: VideoData) {
    LogCompositions(tag = "VideoInfoSection")
    Column(modifier = modifier.padding(8.dp)) {
        FilterTag(text = post.genre, modifier = Modifier)
        Text(
            text = "@${post.userName}",
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.ExtraBold),
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        )
        Text(text = post.caption, style = MaterialTheme.typography.body2)
//        Text(
//            text = "#${album.artist} #cool #moj #videos",
//            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Medium)
//        )
    }
}

@Composable
fun FilterTag(text: String, modifier: Modifier) {
    val tagModifier = modifier
        .clickable(onClick = {})
        .clip(RoundedCornerShape(4.dp))
        .alpha(0.4f)
        .background(Color.Black)
        .padding(horizontal = 8.dp, vertical = 4.dp)

    Text(
        text = text,
        color = Color.White,
        modifier = tagModifier,
        style = Typography.body1.copy(fontWeight = FontWeight.Bold)
    )
}

@Composable
fun ProfileImageWithFollow(modifier: Modifier, showFollow: Boolean, image: String) {
    if (showFollow) {
        Box(modifier = modifier) {
            ImageWithBorder(imageId = image, modifier = modifier)
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
                    .align(Alignment.BottomCenter)
            )
        }
    } else {
        ImageWithBorder(imageId = image, modifier = modifier)
    }
}

@Composable
fun ImageWithBorder(imageId: String, modifier: Modifier) {
    Image(
        painter = rememberImagePainter(data = imageId),
        contentDescription = null,
        modifier = modifier
            .padding(8.dp)
            .clip(CircleShape)
            .border(
                shape = CircleShape,
                border = BorderStroke(
                    1.dp,
                    color = Color.White
                )
            )
    )
}
