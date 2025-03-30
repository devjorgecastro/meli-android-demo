package com.example.meli.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface UIContract {
    interface State
    interface Intent
    interface Effect
}

abstract class StateViewModel<STATE: UIContract.State, EFFECT: UIContract.Effect, INTENT: UIContract.Intent>(
    initialState: STATE
): ViewModel() {

    protected open val stopTimeoutMillisForState = 5000L

    private val mutableState = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = mutableState
        .onStart { initialize() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillisForState),
            initialValue = initialState
        )

    private val mutableEffect: Channel<EFFECT> = Channel()
    val viewEffect = mutableEffect.receiveAsFlow()

    protected fun initialize() {}

    protected fun updateState(reducer: STATE.() -> STATE) {
        mutableState.update(reducer)
    }

    protected fun sendEffect(effect: EFFECT) {
        viewModelScope.launch { mutableEffect.send(effect) }
    }

    protected fun launch(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }

    abstract fun dispatch(intent: INTENT)
}
