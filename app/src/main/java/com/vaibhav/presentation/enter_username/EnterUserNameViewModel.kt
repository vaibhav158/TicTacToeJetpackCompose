package com.vaibhav.presentation.enter_username

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.presentation.common.util.StandardTextFieldState
import com.vaibhav.util.Constants.MIN_USERNAME_CHAR_COUNT
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class EnterUserNameViewModel : ViewModel() {

    private val _userNameFieldState = mutableStateOf(StandardTextFieldState())
    val userNameFieldState: State<StandardTextFieldState> = _userNameFieldState

    private val _userNameValidationEvent = MutableSharedFlow<UserNameValidationEvent>()
    val userNameValidationEvent: SharedFlow<UserNameValidationEvent> = _userNameValidationEvent

    fun onEvent(event: UserNameEvent) {
        when (event) {
            is UserNameEvent.EnteredUserName -> {
                _userNameFieldState.value = _userNameFieldState.value.copy(
                    text = event.userName
                )
            }
            is UserNameEvent.ValidateUserName -> {
                viewModelScope.launch {

                    val userName = _userNameFieldState.value.text

                    when {
                        userName.isEmpty() -> {
                            _userNameFieldState.value = _userNameFieldState.value.copy(
                                error = UserNameValidationErrors.Empty
                            )
                        }
                        userName.length < MIN_USERNAME_CHAR_COUNT -> {
                            _userNameFieldState.value = _userNameFieldState.value.copy(
                                error = UserNameValidationErrors.TooShort
                            )
                        }
                        else -> _userNameValidationEvent.emit(
                            UserNameValidationEvent.Success(_userNameFieldState.value.text)
                        )
                    }
                }
            }
        }
    }
}