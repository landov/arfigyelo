package hu.landov.arfigyelo.data.remote

data class RemoteCategoryNode (
    val id : Int,
    val name : String,
    val path : String,
    val icon : String?,
    val categoryNodes : List<RemoteCategoryNode>
)