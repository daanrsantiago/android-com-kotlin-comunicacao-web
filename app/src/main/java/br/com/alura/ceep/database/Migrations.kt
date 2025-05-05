package br.com.alura.ceep.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.UUID

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // 1. Cria a nova tabela com id String
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS Nota_new (
                id TEXT NOT NULL PRIMARY KEY,
                titulo TEXT NOT NULL,
                descricao TEXT NOT NULL,
                imagem TEXT
            )
        """.trimIndent())

        // 2. Copia os dados da antiga tabela para a nova, gerando UUIDs
        val cursor = database.query("SELECT * FROM Nota")
        while (cursor.moveToNext()) {
            val titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"))
            val descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"))
            val imagem = if (!cursor.isNull(cursor.getColumnIndexOrThrow("imagem")))
                cursor.getString(cursor.getColumnIndexOrThrow("imagem")) else null
            val uuid = UUID.randomUUID().toString()

            database.execSQL(
                "INSERT INTO Nota_new (id, titulo, descricao, imagem) VALUES (?, ?, ?, ?)",
                arrayOf(uuid, titulo, descricao, imagem)
            )
        }
        cursor.close()

        // 3. Remove a tabela antiga
        database.execSQL("DROP TABLE Nota")

        // 4. Renomeia a nova tabela para o nome original
        database.execSQL("ALTER TABLE Nota_new RENAME TO Nota")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Nota ADD COLUMN sinchronized INTEGER NOT NULL DEFAULT 0")
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Nota ADD COLUMN toDelete INTEGER NOT NULL DEFAULT 0")
    }
}
