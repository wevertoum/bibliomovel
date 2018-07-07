package br.ufg.inf.level6.bibliomovel.models

/**
 * Created by erick on 07/07/18.
 */
data class Livro(
        var autor: String,
        var capa: String,
        var categorias: Array<Int>,
        val cod: Int,
        var descricao: String,
        var estoque: EstoqueLivro,
        var nome: String,
        var total_paginas: Int
)