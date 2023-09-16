package hu.landov.arfigyelo.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.landov.arfigyelo.data.remote.RemoteCategoryNode

@Entity(tableName = "category_nodes")
data class LocalCategory (
    @PrimaryKey
    val id : Int,
    @ColumnInfo(name = "parent_id")
    val parentId : Int,
    val name : String,
    val path : String,
    val icon : String?,
    @ColumnInfo(name = "has_products")
    val hasProducts : Boolean = false
) {
    constructor(remoteCategoryNode: RemoteCategoryNode, parentId: Int, hasProducts: Boolean) : this(
        id = remoteCategoryNode.id,
        parentId = parentId,
        name = remoteCategoryNode.name,
        path = remoteCategoryNode.path,
        icon = remoteCategoryNode.icon,
        hasProducts = hasProducts
    )
}
