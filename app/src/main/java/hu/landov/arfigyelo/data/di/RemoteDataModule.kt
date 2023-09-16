package hu.landov.arfigyelo.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.landov.arfigyelo.data.remote.ApiConstants
import hu.landov.arfigyelo.data.remote.WatcherApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiConstants.BASE_ENDPOINT)
            .build()
    }

    @Provides
    fun provideRetrofitApiService(retrofit: Retrofit) : WatcherApiService{
        return retrofit.create(WatcherApiService::class.java)
    }

}