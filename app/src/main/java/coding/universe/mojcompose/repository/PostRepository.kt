package coding.universe.mojcompose.repository

import coding.universe.mojcompose.repository.model.VideoData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

interface PostRepository{
    suspend fun getPostData() : Result<List<VideoData>>
}

@Singleton
class PostRepositoryImpl
@Inject
constructor(
   private val service: VideosService
) : PostRepository{

    override suspend fun getPostData() : Result<List<VideoData>> =
        withContext(Dispatchers.IO){
            try {
                Result.success(service.fetchVideos())
            }catch (e : Exception){
                Result.failure(e)
            }
        }

}