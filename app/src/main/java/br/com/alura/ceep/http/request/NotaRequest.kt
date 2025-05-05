package br.com.alura.ceep.http.request

import br.com.alura.ceep.model.Nota

data class NotaRequest(
    val id: String,
    val titulo: String,
    val descricao: String,
    val imagem: String? = null
) {

    constructor(nota: Nota) : this(
        id = nota.id,
        titulo = nota.titulo,
        descricao = nota.descricao,
        imagem = nota.imagem
    )

}
