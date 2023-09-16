package hu.landov.arfigyelo.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.landov.arfigyelo.data.domain.Favorite
import kotlinx.coroutines.flow.Flow

//TODO Implement ONE-TO-MANY!!!

@Dao
interface WatcherDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(node: LocalCategory)

    @Query("SELECT * FROM category_nodes WHERE parent_id = :parent")
    fun getChildCategoriesLive(parent: Int) : LiveData<List<LocalCategory>>

    @Query("SELECT * FROM category_nodes WHERE parent_id = :parent")
    fun getChildCategories(parent: Int) : List<LocalCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: LocalProduct)

    @Query("SELECT * FROM products WHERE parent_id = :parent ORDER BY min_unit_price ASC, id")
    fun getProductsByCategory(parent: Int) : LiveData<List<LocalProduct>>

    @Query("SELECT * FROM products WHERE is_favorite = 1 ORDER BY name")
    fun getFavoriteProductsLive() : LiveData<List<LocalProduct>>

    @Query("SELECT * FROM products WHERE id = :productId LIMIT 1")
    fun getProductById(productId: String) : LiveData<LocalProduct>

    //Products can be duplicated with different parent ids --
    @Query("SELECT * FROM products WHERE id = :productId")
    fun getProductsById(productId: String) : List<LocalProduct>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPrice(price: LocalPrice)

    @Query("SELECT * FROM prices WHERE product_id = :productId ORDER BY actual_price ASC")
    fun getPricesByProduct(productId : String) : List<LocalPrice>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorites")
    fun getFavorites() : List<Favorite>

    @Query("SELECT * FROM favorites")
    fun getFavoritesLive() : LiveData<List<Favorite>>

    @Query("DELETE FROM favorites WHERE product_id = :productId")
    fun deleteFavorite(productId: String)

}
