package com.sample.test.ui.theme.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.sample.test.ui.theme.datasource.FakeRepo
import com.sample.test.ui.theme.datasource.StudentViewModel
import com.sample.test.ui.theme.themes.TestTheme
import com.sample.test.ui.theme.composables.BottomNavComponent
import com.sample.test.ui.theme.composables.MovieList
import com.sample.test.ui.theme.composables.NavHostComponent
import com.sample.test.ui.theme.composables.ProfileCard
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: StudentViewModel = viewModel()
            val navController = rememberNavController()
            var studentName by remember { mutableStateOf("Ken") }
            val isImeVisible = WindowInsets.isImeVisible
            val bottomNavController = rememberNavController()
            val bottomNavBarBgColor = Color.Black
            TestTheme {


                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text("Jetpack Learning") }, modifier = Modifier.fillMaxWidth(), colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black, titleContentColor = Color.White))
                    },
                    bottomBar = {
                        Box(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                                .clip(
                                    RoundedCornerShape(16.dp)
                                )
                                .background(Color.Black)
                        ) {
                            BottomNavComponent(
                                navController = bottomNavController
                            )
                        }
                    },
                    modifier = Modifier
                        .background(color = bottomNavBarBgColor)
                        .systemBarsPadding()
                )
                { innerPadding ->

                    val contentPadding = remember(isImeVisible) {
                        if (isImeVisible)
                            PaddingValues(start = 0.0.dp, top = 0.0.dp, end = 0.0.dp, bottom = 56.0.dp)
                        else
                            PaddingValues(start = 0.0.dp, top = 0.0.dp, end = 0.0.dp, bottom = 0.0.dp)
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth().padding(top = innerPadding.calculateTopPadding())
                    ) {
                        NavHostComponent(navController = bottomNavController, innerPadding)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp) // Should match or slightly exceed the bottomBar height
                                .align(Alignment.BottomCenter)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                                    )
                                )
                        )
                    }
                }
             /*   Scaffold(
                    topBar = {
                        TopAppBar(title = { Text("Jetpack Learning") }, modifier = Modifier.fillMaxWidth(), colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black, titleContentColor = Color.White))
                    },
                    bottomBar = {
                        CustomNavigationBar(navController)
                    }, modifier = Modifier.systemBarsPadding(),
                    content = { innerPadding ->
                        val contentPadding = remember(isImeVisible) {
                            if (isImeVisible)
                                PaddingValues(start = 0.0.dp, top = 0.0.dp, end = 0.0.dp, bottom = 56.0.dp)
                            else
                                PaddingValues(start = 0.0.dp, top = 0.0.dp, end = 0.0.dp, bottom = 0.0.dp)
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize().padding(contentPadding)
                        ) {
                            NavHostComponent(navController = navController, innerPadding = innerPadding)
                        }



                    }
                )*/


            }
        }
    }
}

@Composable
fun ProfileScreen(){
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {searchQuery = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true,
            label = {
                Text(text = "Search using keywords")
            }
        )

        ProfileList(searchQuery)
    }

}

@Composable
fun ProfileList(searchQuery: String) {

    val dataList = remember { FakeRepo.getProfiles() }
    val listState = rememberLazyListState()
    val corontineScope = rememberCoroutineScope()

    val filteredProfiles = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            dataList
        } else {
            dataList.filter { it.name.startsWith(searchQuery, ignoreCase = true) }
        }
    }

    Log.d("TAG", "ProfileList: "+filteredProfiles.size)

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        contentPadding = PaddingValues(2.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {


        item {
            Text("Header")
        }
        items(filteredProfiles) {
            ProfileCard(it)
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                Button(
                    onClick = {
                        corontineScope.launch {
                            listState.animateScrollToItem(0)
                        }

                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(8.dp),
                    colors = ButtonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White,
                        disabledContentColor = Color.Gray,
                        disabledContainerColor = Color.Gray
                    )
                ) {
                    Text(text = "Go To Top", textAlign = TextAlign.Center, color = Color.White)
                }

            }

        }
    }
}

@Composable
fun OptionButtons(onChange: (String) -> Unit) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Button(
            onClick = {
                Toast.makeText(context, "Clciked", Toast.LENGTH_SHORT).show()
                onChange.invoke("Clicked on Button One")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            ),
            modifier = Modifier
                .padding(4.dp)
                .weight(1f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "One",
                modifier = Modifier
                    .wrapContentSize()
                    .padding(4.dp)
            )
        }

        Button(
            onClick = {
                Toast.makeText(context, "Clciked", Toast.LENGTH_SHORT).show()
                onChange.invoke("Clicked on Button Two")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Cyan,
                contentColor = Color.White
            ),
            modifier = Modifier
                .padding(4.dp)
                .weight(1f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Two",
                modifier = Modifier
                    .wrapContentSize()
                    .padding(4.dp)
            )
        }

        Button(
            onClick = {
                Toast.makeText(context, "Clciked", Toast.LENGTH_SHORT).show()
                onChange.invoke("Clicked on Button Three")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier
                .padding(4.dp)
                .weight(1f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Three",
                modifier = Modifier
                    .wrapContentSize()
                    .padding(4.dp)
            )
        }
    }


}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    onTextChange: String
) {
    val context = LocalContext.current

    var text by remember { mutableStateOf("Jeev") }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = text,
            modifier = Modifier
                .wrapContentSize()
                .padding(4.dp)
        )

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
            horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = {
                    Toast.makeText(context, "Clciked", Toast.LENGTH_SHORT).show()
                    text = "Clicked on Button One - Red Button"
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(4.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "One",
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(4.dp)
                )
            }

            Button(
                onClick = {
                    Toast.makeText(context, "Clciked", Toast.LENGTH_SHORT).show()
                    text = "Clicked on Button Two - Green Button"
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green,
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(4.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Two",
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(4.dp)
                )
            }

            Button(
                onClick = {
                    Toast.makeText(context, "Clciked", Toast.LENGTH_SHORT).show()
                    text = "Clicked on Button One - Red Button"
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(4.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Three",
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(4.dp)
                )
            }
        }


    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestTheme {
       // Greeting("Android Developemnt", modifier = Modifier.padding(4.dp), "Jeevan")
        MovieList()
    }
}