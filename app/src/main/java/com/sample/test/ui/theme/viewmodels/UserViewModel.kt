package com.sample.test.ui.theme.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.test.Util
import com.sample.test.models.Student
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

     var mUserName by mutableStateOf("")
         private set

    var isUserNameError by mutableStateOf(false)
        private set

    var mPassword by mutableStateOf("")
        private set

    private var loginStateSharedFlow = MutableSharedFlow<LoginState>()
    val loginState get() = loginStateSharedFlow


    fun onUserNameChange(username : String){
        mUserName = username
        isUserNameError = false
    }

    fun onPasswordChange(password : String){
        mPassword = password
        isUserNameError = false
    }

    fun onSubmit(){
        if (mUserName.length < 3){
            isUserNameError = true
            viewModelScope.launch {
                loginStateSharedFlow.emit(LoginState.LoginFailed)
            }
        }else if (!mUserName.equals("admin", ignoreCase = false)){
            viewModelScope.launch {
                loginStateSharedFlow.emit(LoginState.LoginFailed)
            }
            isUserNameError = true
        }else{
            isUserNameError = false
            viewModelScope.launch {
                loginStateSharedFlow.emit(LoginState.LoginSuccess("Username accepted!"))
            }
        }
    }

    sealed class LoginState {
        data class  LoginSuccess(val successMessage : String) : LoginState()
        data object LoginFailed : LoginState()

    }

    sealed class DashboardListUiState {
        data class Success(val list : List<Student>) : DashboardListUiState()
        data object Loading : DashboardListUiState()
    }



    private var mockColumnList = MutableStateFlow<DashboardListUiState>(DashboardListUiState.Loading)
    val mockList get() = mockColumnList

    suspend fun createMockRows() = viewModelScope.launch {
        delay(3000L)
        val testItems  = Util.generateRandomStudents(1000)
        mockColumnList.tryEmit(DashboardListUiState.Success(testItems))

    }

}