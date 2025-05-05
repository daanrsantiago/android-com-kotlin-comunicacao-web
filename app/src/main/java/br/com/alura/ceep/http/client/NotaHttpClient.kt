package br.com.alura.ceep.http.client

import br.com.alura.ceep.http.request.NotaRequest
import br.com.alura.ceep.http.response.NotaResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotaHttpClient {

    @GET("/notas")
    suspend fun getAllNotes(): List<NotaResponse>

    @PUT("/notas/{id}")
    suspend fun updateNote(@Path("id") id: String, @Body nota: NotaRequest)

    @DELETE("/notas/{id}")
    suspend fun deleteNote(@Path("id") id: String)

}