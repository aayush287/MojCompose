package coding.universe.mojcompose

import coding.universe.mojcompose.repository.model.VideoData

sealed class MojHomeInteractionEvents {
    data class OpenProfile(val post: VideoData) : MojHomeInteractionEvents()
    data class ShareVideo(val album: Album) : MojHomeInteractionEvents()
    object OpenComments : MojHomeInteractionEvents()
}