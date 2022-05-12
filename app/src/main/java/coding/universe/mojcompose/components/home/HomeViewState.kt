package coding.universe.mojcompose.components.home

import coding.universe.mojcompose.repository.model.VideoData

sealed class HomeViewState {
    object Loading : HomeViewState()
    data class Finished(val videos: List<VideoData>?) : HomeViewState()
    data class ShowError(val message : String?) : HomeViewState()
}
