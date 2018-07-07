package br.ufg.inf.level6.bibliomovel.models

/**
 * Created by erick on 07/07/18.
 */
data class LivroEstante(
        val cod: Int,
        var data_emprestimo: String,
        var data_devolucao: String?,
        var previsao_devolucao: String
)