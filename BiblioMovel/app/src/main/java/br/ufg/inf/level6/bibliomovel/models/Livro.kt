package br.ufg.inf.level6.bibliomovel.models

/**
 * Created by erick on 07/07/18.
 */
class Livro {
    var autor: String? = null
    var capa: String? = null
    var categorias: Array<Int>? = null
    val cod: Int? = null
    var descricao: String? = null
    var estoque: EstoqueLivro? = null
    var nome: String? = null
    var total_paginas: Int? = null
}