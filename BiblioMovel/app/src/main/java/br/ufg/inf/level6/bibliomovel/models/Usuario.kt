package br.ufg.inf.level6.bibliomovel.models

/**
 * Created by erick on 07/07/18.
 */
data class Usuario(
        val id: String,
        var perfil: Perfil,
        var estante: Array<LivroEstante>,
        var interesses: Array<LivroInteresse>
)