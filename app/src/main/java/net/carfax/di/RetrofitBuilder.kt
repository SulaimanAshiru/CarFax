package net.carfax.di

import com.karumi.dexter.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.carfax.api.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object RetrofitBuilder {

    @Provides
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient().newBuilder().followRedirects(false).followSslRedirects(false).apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
            connectTimeout(5, TimeUnit.MINUTES)
            readTimeout(5, TimeUnit.MINUTES)
        }.build()

    @Provides
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): ApiInterface =
        Retrofit.Builder().apply {
            baseUrl("https://carfax-for-consumers.firebaseio.com/")
            addConverterFactory(gsonConverterFactory)
            client(okHttpClient)
        }.build().create(ApiInterface::class.java)
}