package coding.universe.mojcompose

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember

class Ref(var value: Int)

/**
 * An effect which logs the number compositions when invoked
 * This will capture every recomposition which will degrade performance
 * Use this only for development purpose make it false when giving build to QA.
 */

@Composable
fun LogCompositions(tag: String) {
    val ref = remember { Ref(0) }
    SideEffect { ref.value++ }
    Log.d(tag, "Compositions: ${ref.value}")
}