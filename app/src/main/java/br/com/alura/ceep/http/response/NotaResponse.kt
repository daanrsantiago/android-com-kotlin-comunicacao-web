package br.com.alura.ceep.http.response

import br.com.alura.ceep.model.Nota
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class NotaResponse @JsonCreator constructor(
    @JsonProperty("id") var id: String,
    @JsonProperty("titulo") var titulo: String,
    @JsonProperty("descricao") var descricao: String,
    @JsonProperty("imagem") var imagem: String? = null,
) {

    fun toNota(): Nota = Nota(
        id = id,
        titulo = titulo,
        descricao = descricao,
        imagem = imagem
    )

}
