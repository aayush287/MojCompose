package coding.universe.mojcompose.components

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coding.universe.mojcompose.components.home.HomeViewState
import coding.universe.mojcompose.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"
@HiltViewModel
class HomeViewModel
@Inject
constructor(private val postRepository: PostRepository) : ViewModel() {
    private val _homeViewState =  MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val homeViewState
        get() = _homeViewState.asStateFlow()

    init {
        fetchPost()
    }

    private fun fetchPost(){
        viewModelScope.launch {
            _homeViewState.emit(
                HomeViewState.Loading
            )
            val result = postRepository.getPostData()
            when {
                result.isSuccess -> {
                    val data = result.getOrNull()
                    Log.d(TAG, "fetchPost: Testing --- sucess ---")
                    _homeViewState.emit(
                        HomeViewState.Finished(
                            data
                        )
                    )
                }

                result.isFailure -> {
                    val e = result.exceptionOrNull()
                    Log.d(TAG, "fetchPost: Testing --failed -- ${e?.localizedMessage}")
                    _homeViewState.emit(
                        HomeViewState.ShowError(
                            e?.localizedMessage
                        )
                    )
                }
            }
        }

    }
}