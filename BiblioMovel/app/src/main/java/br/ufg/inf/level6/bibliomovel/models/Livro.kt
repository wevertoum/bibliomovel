package br.ufg.inf.level6.bibliomovel.models

import com.google.firebase.database.FirebaseDatabase

/**
 * Created by erick on 07/07/18.
 */
class Livro {
    var id: String? = null
    var autor: String? = null
    var capa: String? = null
    var categorias: Array<Int>? = null
    var cod: Long? = null
    var descricao: String? = null
    var estoque: EstoqueLivro? = null
    var nome: String? = null
    var total_paginas: Long? = null

    var livroRef = FirebaseDatabase.getInstance().getReference("livros")

    fun reservarLivro(livro: Livro){
        if(livro.estoque!!.disponiveis > 0){
            var disponiveis = livro.estoque!!.disponiveis - 1
            var emprestados = livro.estoque!!.emprestados + 1


            livroRef.child(livro.id).child("estoque").child("disponiveis").setValue(disponiveis)
            livroRef.child(livro.id).child("estoque").child("emprestados").setValue(emprestados)
        }
    }
}