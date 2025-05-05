package br.com.alura.ceep.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import br.com.alura.ceep.model.Nota
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDao {
    @Query("SELECT * FROM Nota WHERE toDelete = 0")
    fun getAll() : Flow<List<Nota>>

    @Query("SELECT * FROM Nota WHERE sinchronized = 0")
    suspend  fun getNotSynchronized() : List<Nota>

    @Query("SELECT * FROM Nota WHERE toDelete = 1")
    suspend fun getToDelete(): List<Nota>

    @Query("SELECT * FROM Nota WHERE id = :id")
    fun getById(id: String): Flow<Nota?>

    @Insert(onConflict = REPLACE)
    suspend fun save(note: Nota)

    @Insert(onConflict = REPLACE)
    suspend fun saveAll(notas: List<Nota>)

    @Query("DELETE FROM Nota WHERE id = :id")
    suspend fun remove(id: String)

    @Query("UPDATE Nota SET sinchronized = 1 WHERE id = :id")
    suspend fun setAsSynchronized(id: String)

    @Query("UPDATE Nota SET toDelete = 1 WHERE id = :id")
    suspend fun setAsToDelete(id: String)

}