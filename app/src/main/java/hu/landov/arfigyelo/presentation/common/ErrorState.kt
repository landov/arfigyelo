package hu.landov.arfigyelo.presentation.common

data class ErrorState (
    val message: String,
    val retryable : Boolean
)
