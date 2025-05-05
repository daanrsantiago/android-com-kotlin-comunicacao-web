package br.com.alura.ceep.repository

import android.util.Log
import br.com.alura.ceep.database.dao.NotaDao
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.service.NotaService
import kotlinx.coroutines.flow.Flow

class NotaRepository (
    private val notaDao: NotaDao,
    private val notaService: NotaService
) {

    suspend fun synchronizeDatabase() {
        try {
            uploadNotesToApi()
            deleteNotesMarkedForDeletion()
            downloadNotesFromApi()
            Log.d("NotaRepository", "Database synchronized successfully")
        } catch (e: Exception) {
            Log.e("NotaRepository", "Error synchronizing database", e)
        }
    }

    private suspend fun uploadNotesToApi() {
        val notSynchronizedNotes = notaDao.getNotSynchronized()
        for (note in notSynchronizedNotes) {
            try {
                notaService.saveNote(note)
                Log.d("NotaRepository", "Note ${note.id} uploaded successfully")
            } catch (exception: Exception) {
                Log.e("NotaRepository", "Error uploading note ${note.id}", exception)
            }
        }
    }

    private suspend fun deleteNotesMarkedForDeletion() {
        val notesToDelete = notaDao.getToDelete()
        for (note in notesToDelete) {
            try {
                notaService.deleteNote(note.id)
                Log.d("NotaRepository", "Note ${note.id} deleted successfully")
            } catch (exception: Exception) {
                Log.e("NotaRepository", "Error deleting note ${note.id}", exception)
            }
        }
    }

    private suspend fun downloadNotesFromApi() {
        val notesFromApi = notaService.getAllNotes().onEach { note -> note.sinchronized = true }
        notaDao.saveAll(notesFromApi)
    }

    fun getAll(): Flow<List<Nota>> {
        return notaDao.getAll()
    }

    fun getById(id: String): Flow<Nota?> {
        return notaDao.getById(id)
    }

    suspend fun save(note: Nota) {
        note.sinchronized = false
        notaDao.save(note)
        try {
            notaService.saveNote(note)
            notaDao.setAsSynchronized(note.id)
            Log.d("NotaRepository", "Note saved successfully")
        } catch (e: Exception) {
            Log.e("NotaRepository", "Error saving note", e)
        }
    }

    suspend fun delete(id: String) {
        notaDao.setAsToDelete(id)
        try {
            notaService.deleteNote(id)
            notaDao.remove(id)
            Log.d("NotaRepository", "Note deleted successfully")
        } catch (e: Exception) {
            Log.e("NotaRepository", "Error deleting note", e)
        }
    }

}