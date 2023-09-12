package com.example.tiptime

data class TipTimeUiState(
    val amount: String = "",
    val tipPercent: String = "",
    val roundUp: Boolean = false,
    val tip: String = ""
)
