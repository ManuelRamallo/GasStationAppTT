package com.mramallo.gasstationapp.di

import com.mramallo.gasstationapp.data.network.GasStationApiClient
import com.mramallo.gasstationapp.utils.Constants.URL_BASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    //Retrofit provider
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideGasStationApiClient(retrofit: Retrofit): GasStationApiClient {
        return retrofit.create(GasStationApiClient::class.java)
    }


}