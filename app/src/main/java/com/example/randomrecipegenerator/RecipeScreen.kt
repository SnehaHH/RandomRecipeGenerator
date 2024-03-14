package com.example.randomrecipegenerator

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import android.net.Uri
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage



@Composable
fun MainScreen(
    viewModel: MainViewModel,
) {
    val scrollState = rememberScrollState()
    val scrollTrigger = remember{ mutableStateOf(false)}
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when {
            viewModel.recipeDetails.value.loading == true -> {
                CircularProgressIndicator(color = Color(0xFF40F11A))
                Text(text = "Loading")
            }

            viewModel.recipeDetails.value.error != null -> Text(text = "ERROR OCCURRED!")
            else -> {

                Text(
                    text = viewModel.recipeDetails.value.values[0].strMeal,
                    color = Color(0xff53001D),
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(8.dp))

                AsyncImage(
                    model = viewModel.recipeDetails.value.values[0].strMealThumb,
                    contentDescription = "An image of ${viewModel.recipeDetails.value.values[0].strMeal}",

                    )


                Spacer(modifier = Modifier.padding(4.dp))
                Column(
                    Modifier.padding(4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Origin: ${viewModel.recipeDetails.value.values[0].strArea.capitalize()}",
                        color = Color(0xff08122B),
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = "Category: ${viewModel.recipeDetails.value.values[0].strCategory}",
                        color = Color(0xff08122B),
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.padding(4.dp))
                Row(
                    Modifier
                        .padding(2.dp)
                        .fillMaxSize()
                        .wrapContentWidth()
                ) {
                    Text(
                        text = "Tags: ",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold
                    )
                    val strTags = checkTags(viewModel = viewModel)
                    when {
                        strTags.isEmpty() -> Text(
                            text = "None",
                            fontSize = 15.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold,

                            )

                        else ->
                            Row {
                                Text(
                                    text = "${strTags}",
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.SemiBold,

                                    )

                            }
                    }
                }
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = "RECIPE",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = viewModel.recipeDetails.value.values[0].strInstructions,
                    Modifier
                        .padding(8.dp),
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.padding(8.dp))
                val newRecipeData =
                    CheckforNullMap(recipeData = viewModel.recipeDetails.value.values[0])
                Text(
                    text = "INGREDIENTS LIST",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.padding(8.dp))
                for (i in newRecipeData) {
                    Row(
                        Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        Text(text = "*")
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(text = "${i.key}: ${i.value}", fontWeight = FontWeight.Medium)
                    }

                }
                Spacer(modifier = Modifier.padding(16.dp))
                Row(
                    Modifier
                        .padding(8.dp)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(onClick = { val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(viewModel.recipeDetails.value.values[0].strSource)
                    )
                        startActivity(context ,intent, null)}) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = "Go to the recipe page in google", tint = Color(
                            0xFFFFC000
                        ), modifier = Modifier.size(48.dp)
                        )
                    }

                    IconButton(onClick = { scrollTrigger.value = true
                        viewModel.FetchRecipe() }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "New Recipe", tint = Color(
                            0xFF8903FF
                        ), modifier = Modifier.size(48.dp)
                        )
                    }

                    
                    IconButton(onClick = { val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(viewModel.recipeDetails.value.values[0].strYoutube)
                    )
                        startActivity(context ,intent, null) }) {
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play the recipe on Youtube", tint = Color(
                            0xFFB80202
                        ), modifier = Modifier.size(48.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(16.dp))
            }
        }
    }
    if (scrollTrigger.value)
    LaunchedEffect(Unit) {
        // Execute the scrolling operation
        scrollState.animateScrollTo(0)
    }

}


@Composable
fun checkTags(viewModel: MainViewModel): List<String> {
    if (viewModel.recipeDetails.value.values[0].strTags.isNullOrEmpty())
        return emptyList()
    else if (viewModel.recipeDetails.value.values[0].strTags.length == 1)
        return listOf(viewModel.recipeDetails.value.values[0].strTags)
    else
        return viewModel.recipeDetails.value.values[0].strTags.split(",")
}


@Composable
fun CheckforNullMap(recipeData: RecipeData): Map<String, String> {

    val newMeasures = mutableMapOf<String, String>()

    if (!recipeData.strIngredient1.isNullOrEmpty()) {
        newMeasures[recipeData.strIngredient1.toString()] = recipeData.strMeasure1.toString()
    }

    if (!recipeData.strIngredient2.isNullOrEmpty()) {
        newMeasures[recipeData.strIngredient2.toString()] = recipeData.strMeasure2.toString()
    }

    if (!recipeData.strIngredient3.isNullOrEmpty()) {

        newMeasures[recipeData.strIngredient3.toString()] = recipeData.strMeasure3.toString()
    }

    if (!recipeData.strIngredient4.isNullOrEmpty()) {
        newMeasures[recipeData.strIngredient4.toString()] = recipeData.strMeasure4.toString()
    }

    if (!recipeData.strIngredient5.isNullOrEmpty()) {

        newMeasures[recipeData.strIngredient5.toString()] = recipeData.strMeasure5.toString()
    }

    if (!recipeData.strIngredient6.isNullOrEmpty()) {

        newMeasures[recipeData.strIngredient6.toString()] = recipeData.strMeasure6.toString()
    }

    if (!recipeData.strIngredient7.isNullOrEmpty()) {

        newMeasures[recipeData.strIngredient7.toString()] = recipeData.strMeasure7.toString()
    }

    if (!recipeData.strIngredient8.isNullOrEmpty()) {

        newMeasures[recipeData.strIngredient8.toString()] = recipeData.strMeasure8.toString()
    }

    if (!recipeData.strIngredient9.isNullOrEmpty()) {

        newMeasures[recipeData.strIngredient9.toString()] = recipeData.strMeasure9.toString()
    }

    if (!recipeData.strIngredient10.isNullOrEmpty()) {

        newMeasures[recipeData.strIngredient10.toString()] = recipeData.strMeasure10.toString()
    }

    if (!recipeData.strIngredient11.isNullOrEmpty()) {

        newMeasures[recipeData.strIngredient11.toString()] = recipeData.strMeasure11.toString()
    }

    if (!recipeData.strIngredient12.isNullOrEmpty()) {

        newMeasures[recipeData.strIngredient12.toString()] = recipeData.strMeasure12.toString()
    }

    if (!recipeData.strIngredient13.isNullOrEmpty()) {

        newMeasures[recipeData.strIngredient13.toString()] = recipeData.strMeasure13.toString()
    }

    if (!recipeData.strIngredient14.isNullOrEmpty()) {

        newMeasures[recipeData.strIngredient14.toString()] = recipeData.strMeasure14.toString()
    }

    if (!recipeData.strIngredient15.isNullOrEmpty()) {

        newMeasures[recipeData.strIngredient15.toString()] = recipeData.strMeasure15.toString()
    }

    if (!recipeData.strIngredient16.isNullOrEmpty()) {

        newMeasures[recipeData.strIngredient16.toString()] = recipeData.strMeasure16.toString()
    }

    if (!recipeData.strIngredient17.isNullOrEmpty()) {

        newMeasures[recipeData.strIngredient17.toString()] = recipeData.strMeasure17.toString()
    }

    if (!recipeData.strIngredient18.isNullOrEmpty()) {

        newMeasures[recipeData.strIngredient18.toString()] = recipeData.strMeasure18.toString()
    }

    if (!recipeData.strIngredient19.isNullOrEmpty()) {
        newMeasures[recipeData.strIngredient19.toString()] = recipeData.strMeasure19.toString()
    }

    if (!recipeData.strIngredient20.isNullOrEmpty()) {
        newMeasures[recipeData.strIngredient20.toString()] = recipeData.strMeasure20.toString()
    }

    return newMeasures
}


