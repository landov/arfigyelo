package hu.landov.arfigyelo.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface WatcherApiService {

    @GET("categories")
    suspend fun getCategories() : RemoteCategories

    @GET("products-by-category/{id}")  //TODO prepare for 404!
    suspend fun getProductsByParentId(@Path(value = "id") parentId: Int) : RemoteProducts

}