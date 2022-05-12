package coding.universe.mojcompose.repository

import coding.universe.mojcompose.repository.model.VideoData
import retrofit2.http.GET


interface VideosService {
    @GET("fetch_videos/")
    suspend fun fetchVideos() : List<VideoData>
}