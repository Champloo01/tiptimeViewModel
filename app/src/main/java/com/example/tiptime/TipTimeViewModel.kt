package com.example.tiptime

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat

class TipTimeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TipTimeUiState())
    val uiState: StateFlow<TipTimeUiState> = _uiState.asStateFlow()

    private var userGuessAmountInput by mutableStateOf("")
    private var userGuessTipInput by mutableStateOf("")
    private var amount by mutableStateOf(0.0)
    private var tipPercent by mutableStateOf(0.0)
    private var roundUp by mutableStateOf(false)

    fun updateUserGuessAmountInput(GuessAmountInput: String) {
        userGuessAmountInput = GuessAmountInput
        amount = GuessAmountInput.toDoubleOrNull() ?: 0.0
        updateTipTimeState()
    }

    fun updateUserGuessTipInput(GuessTipInput: String) {
        userGuessTipInput = GuessTipInput
        tipPercent = GuessTipInput.toDoubleOrNull() ?: 0.0
        updateTipTimeState()
    }

    fun updateRoundUp(_roundUp: Boolean) {
        roundUp = if (roundUp == _roundUp) _roundUp
        else !roundUp
        updateTipTimeState()
    }

    private fun updateTip() : String{
        return calculateTip(amount, tipPercent, roundUp)
    }

    private fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundUp: Boolean): String {
        var tip = tipPercent / 100 * amount
        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }
        return NumberFormat.getCurrencyInstance().format(tip)
    }



    private fun updateTipTimeState() {
        _uiState.update { uiState ->
            uiState.copy(
                amount = userGuessAmountInput,
                tipPercent = userGuessTipInput,
                roundUp = roundUp,
                tip = updateTip()
            )
        }
    }
}