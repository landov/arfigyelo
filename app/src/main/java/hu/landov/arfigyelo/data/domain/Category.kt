package hu.landov.arfigyelo.data.domain

data class Category (
    val id : Int,
    val parentId : Int,
    val name : String,
    val path : String,
    val icon : String?,
    val hasProducts : Boolean = false
)