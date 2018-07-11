package br.ufg.inf.level6.bibliomovel.models

import android.util.Log
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

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
    var usuarioRef = FirebaseDatabase.getInstance().getReference("usuarios")

    fun reservarLivro(livro: Livro) : Boolean{

        var user = FirebaseAuth.getInstance().currentUser

        if(user == null)
            return false

        if(livro.estoque!!.disponiveis > 0){
            var disponiveis = livro.estoque!!.disponiveis - 1
            var emprestados = livro.estoque!!.emprestados + 1


            livroRef.child(livro.id).child("estoque").child("disponiveis").setValue(disponiveis)
            livroRef.child(livro.id).child("estoque").child("emprestados").setValue(emprestados)

            var interesse = LivroInteresse(livro.cod!!, 0)
            var interesseId = usuarioRef.child(user!!.uid).child("interesses").push().key
            usuarioRef.child(user!!.uid).child("interesses").child(interesseId).setValue(interesse)
            return true
        }
        return false
    }
}