package com.example.randomrecipegenerator

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {
    init {
        FetchRecipe()
    }

    data class RecipeState(
        val loading: Boolean? = true,
        val error: String? = null,
        val values: List<RecipeData> = emptyList()
    )

    private val _recipeDetails = mutableStateOf(RecipeState())
    val recipeDetails: State<RecipeState> = _recipeDetails

      fun FetchRecipe() {
        viewModelScope.launch {
            try {
                val response = reciperService.getRecipe()
                _recipeDetails.value =
                    _recipeDetails.value.copy(values = response.meals, loading = false)
            } catch (excep: Exception) {
                _recipeDetails.value = _recipeDetails.value.copy(
                    error = "An error has occured with message ${excep.message}",
                    loading = false
                )
            }
        }
    }
}
