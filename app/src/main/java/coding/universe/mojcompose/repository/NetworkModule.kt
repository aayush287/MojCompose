package coding.universe.mojcompose.repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@[Module InstallIn(SingletonComponent::class)]
internal object NetworkModule {

    @[Provides Singleton]
    fun provideVideoServiceRetrofit(
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://8ef44ab9-12b5-4629-9b73-dfd2df8b9f33.mock.pstmn.io/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @[Provides Singleton]
    fun provideVideoService(retrofit: Retrofit) : VideosService {
        return retrofit.create(VideosService::class.java)
    }

    @[Provides Singleton]
    fun provideGson() : Gson {
       return GsonBuilder()
            .setLenient()
            .create()
    }

}