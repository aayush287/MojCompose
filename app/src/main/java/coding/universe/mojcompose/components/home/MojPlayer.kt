package coding.universe.mojcompose.components.home


import android.content.Context
import android.net.Uri
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import coding.universe.mojcompose.LogCompositions
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ProgressiveMediaSource.*
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource

@Composable
fun MojPlayer(context: Context, id: Int, url: String, selected: Boolean) {
    LogCompositions(tag = "MojPlayer - Testing")

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val defaultDataSourceFactory = DefaultDataSource.Factory(context)
                val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                    context,
                    defaultDataSourceFactory
                )
                val source = Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(Uri.parse(url)))

                setMediaSource(source)
                prepare()
            }
    }
    AndroidView(factory = {
        StyledPlayerView(it).apply {
            useController = false
            player = exoPlayer
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        }
    })


    exoPlayer.playWhenReady = selected

    DisposableEffect(id) {
        onDispose {
            exoPlayer.release()
        }
    }
}