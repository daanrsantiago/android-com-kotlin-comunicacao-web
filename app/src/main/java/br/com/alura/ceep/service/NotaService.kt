package br.com.alura.ceep.service

import br.com.alura.ceep.http.client.NotaHttpClient
import br.com.alura.ceep.http.request.NotaRequest
import br.com.alura.ceep.model.Nota

class NotaService (
    private val notaHttpClient: NotaHttpClient
) {

    suspend fun getAllNotes() = notaHttpClient.getAllNotes().map { it.toNota() }

    suspend fun saveNote(nota: Nota) {
        notaHttpClient.updateNote(nota.id, NotaRequest(nota))
    }

    suspend fun deleteNote(id: String) {
        notaHttpClient.deleteNote(id)
    }

}