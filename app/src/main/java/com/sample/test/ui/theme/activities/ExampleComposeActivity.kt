package com.sample.test.ui.theme.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.sample.test.models.Student
import com.sample.test.ui.theme.viewmodels.UserViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf


class ExampleComposeActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


            Scaffold(
                topBar = {
                    Surface(
                        color = Color.Black,
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 10.dp,
                            bottomEnd = 10.dp
                        ),
                        tonalElevation = 4.dp, // Optional shadow/elevation
                    ) {
                        TopAppBar(
                            title = {
                                Text(text = "Sample App", modifier = Modifier.fillMaxWidth())
                            }, modifier = Modifier.fillMaxWidth(),
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Black,
                                titleContentColor = Color.White,
                            )

                        )
                    }

                },

                modifier = Modifier.background(Color.Red)
            ) { innerPadding ->
                MyApp(innerPadding)
            }
        }
    }

    @Composable
    private fun MyApp(innerPadding: PaddingValues) {
        val navController = rememberNavController()
        val viewModel : UserViewModel = viewModel()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {

            NavHost(
                navController = navController,
                startDestination = "sample_listing"
            ) {
                composable(route = "sample") {
                    LoginScreen("Test Value", userViewModel = viewModel, navController)
                }

                composable(route = "sample_listing") {
                    DashboardScreen(viewModel, navController)
                }

                /*composable(
                    route = "detail",
                    typeMap = mapOf(typeOf<Student>() to StudentNavType())
                ) {
                   it.toRoute<>()

                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "This is a test"+ studentDetail?.name
                        )
                    }
                }*/

            }

        }
    }


@Composable
fun DashboardScreen(viewModel: UserViewModel = viewModel(), navController: NavHostController? = null) {
    val students by viewModel.mockList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.createMockRows()
    }

    DashboardListScreen(state = students, navController)
}

@Composable
private fun DashboardListScreen(
    state: UserViewModel.DashboardListUiState,
    navController: NavHostController?
) {
    val context = LocalContext.current
    when(state) {
        is UserViewModel.DashboardListUiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.Red,
                    trackColor = Color.Black,
                    modifier = Modifier.padding(
                        10.dp
                    )
                )
            }
        }

        is UserViewModel.DashboardListUiState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                items((state as UserViewModel.DashboardListUiState.Success).list) { item ->


                    Card(
                        contentColor = Color.Black, // Content color (e.g., text)
                        backgroundColor = Color.White, // White background for the card
                        shape = RoundedCornerShape(8.dp), // Rounded corners
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = item.name, modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .weight(1f)
                                    .align(Alignment.CenterVertically)
                                    .clickable {

                                    })

                            Text(
                                text = item.area, textAlign = TextAlign.End, modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .weight(1f)
                                    .align(Alignment.CenterVertically)
                                    .clickable {
                                        Toast.makeText(
                                            context,
                                            "Clicked on Row $item",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    })
                        }
                    }

                }
            }
        }

        else -> {

        }
    }
}

    @Composable
    private fun LoginScreen(
        defaultName: String? = null,
        userViewModel: UserViewModel = viewModel(),
        navController: NavHostController
    ) {
        var password by rememberSaveable { mutableStateOf("No Password Entered") }
        val nameEntered by remember { mutableStateOf("Please enter the Login Details") }
        DisposableEffect(key1 = 1) {
            Log.d("TAG", "SampleHelloWorld: started")
            onDispose {
                Log.d("TAG", "SampleHelloWorld: stop")
            }
        }
        val context = LocalContext.current

        LaunchedEffect(key1 = System.currentTimeMillis()) {

            userViewModel.loginState.collectLatest { state ->
                when (state) {
                    is UserViewModel.LoginState.LoginFailed -> {
                        Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                    }

                    is UserViewModel.LoginState.LoginSuccess -> {
                        navController.navigate("sample_listing"){
                           this.launchSingleTop = true
                            popUpTo("sample"){
                                inclusive = false
                            }
                        }
                        Toast.makeText(context, state.successMessage, Toast.LENGTH_SHORT).show()
                    }else ->{}
                }
            }

        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp), verticalArrangement = Arrangement.Center
        ) {


            Text(
                text = defaultName ?: nameEntered,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = Color.Black,
                textAlign = TextAlign.Center
            )


            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = userViewModel.mUserName,
                onValueChange = { query ->
                    userViewModel.onUserNameChange(query)

                },
                label = {
                    Text(text = "Username", color = Color.LightGray)
                },
                placeholder = {
                    Text(text = "Please enter user name", color = Color.Gray)
                },
                isError = userViewModel.isUserNameError,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = if (userViewModel.isUserNameError) Color.White else Color.White,
                    errorPlaceholderColor = Color.Red
                )

            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { query ->
                   password = query

                },
                label = {
                    Text(text = "Password", color = Color.LightGray)
                },
                placeholder = {
                    Text(text = "Please enter password", color = Color.Gray)
                },
                isError = userViewModel.isUserNameError,
                colors = TextFieldDefaults.colors(
                    errorLabelColor = Color.Red,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )

            )



            if (userViewModel.isUserNameError) {
                Text("Please enter a valid username")
                Spacer(modifier = Modifier.height(16.dp))
            }


            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    userViewModel.onSubmit()
                }) {
                Text(text = "Continue with UserName")
            }

        }


    }

    @Preview(showBackground = true)
    @Composable
    private fun SamplePreview() {
        //MyApp(PaddingValues(16.dp))
        val mockStudents = listOf(
            Student(name = "Rahul", area = "Bangalore"),
            Student(name = "Priya", area = "Chennai")
        )

        DashboardListScreen(
            state = UserViewModel.DashboardListUiState.Success(mockStudents),
            navController = rememberNavController()
        )
    }

    @kotlinx.serialization.Serializable
    sealed class Routes {

        @kotlinx.serialization.Serializable
        data object FirstScreen : Routes()

        @Serializable
        data class SecondScreen(val studentInfo: Student) : Routes()

    }
}