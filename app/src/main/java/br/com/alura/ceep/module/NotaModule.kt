package br.com.alura.ceep.module

import android.app.Application
import br.com.alura.ceep.database.AppDatabase
import br.com.alura.ceep.database.dao.NotaDao
import br.com.alura.ceep.http.client.NotaHttpClient
import br.com.alura.ceep.repository.NotaRepository
import br.com.alura.ceep.service.NotaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NotaModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        app: Application
    ): AppDatabase {
        return AppDatabase.instancia(app)
    }

    @Provides
    @Singleton
    fun provideNotaDao(
        appDatabase: AppDatabase
    ): NotaDao {
        return appDatabase.notaDao()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.100.110:8080")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNotaHttpClient(retrofit: Retrofit): NotaHttpClient {
        return retrofit.create(NotaHttpClient::class.java)
    }

    @Provides
    @Singleton
    fun provideNotaService(notaHttpClient: NotaHttpClient): NotaService {
        return NotaService(notaHttpClient)
    }

    @Provides
    @Singleton
    fun provideNotaRepository(
        notaDao: NotaDao,
        notaService: NotaService
    ): NotaRepository {
        return NotaRepository(notaDao, notaService)
    }

}