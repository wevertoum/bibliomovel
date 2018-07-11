package br.ufg.inf.level6.bibliomovel.models

import com.google.firebase.database.FirebaseDatabase

/**
 * Created by erick on 07/07/18.
 */
class LivroEstante {
    var id: String? = null
    var cod: Long? = null
    var data_emprestimo: String? = null
    var data_devolucao: String? = null
    var previsao_devolucao: String? = null
}