package id.radenyaqien.testqerja.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.radenyaqien.testqerja.data.remote.IJobApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): IJobApi {
        return retrofit.create(IJobApi::class.java)
    }


}