package br.ufg.inf.level6.bibliomovel

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import br.ufg.inf.level6.bibliomovel.models.EstoqueLivro
import br.ufg.inf.level6.bibliomovel.models.Livro
import br.ufg.inf.level6.bibliomovel.services.BooksAdapter
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import java.lang.reflect.Array
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.jvm.internal.Ref
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    var bookList = ArrayList<Livro>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        var database = FirebaseDatabase.getInstance()
        var myRef = database.getReference().child("livros")

        //Add event listener to "livros"
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //add data livros to list
                addDataToList(dataSnapshot)

                var adapter = BooksAdapter(this@MainActivity, bookList)
                //ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, android.R.id.text1, array)
                var listView = findViewById<ListView>(R.id.list_item)
                listView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("ERR", "Failed to read value.", error.toException())
            }
        })

        findViewById<ImageButton>(R.id.mainActBtn1).setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }

        findViewById<ImageButton>(R.id.mainActBtn2).setOnClickListener {
            startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
        }
    }

    private fun addDataToList(dataSnapshot: DataSnapshot) {
        //get database items "Livros"
        val items = dataSnapshot.children.iterator()

        if(items !== null) {

            bookList.clear()

            while(items.hasNext()) {
                var currentItem = items.next()
                var livro = currentItem.getValue() as HashMap<Any, Any>
                val book: Livro = Livro()

                //Get properties
                book.id = currentItem.key
                book.autor = livro.getValue("autor") as String
                book.nome = livro.getValue("nome") as String
                book.descricao = livro.getValue("descricao") as String
                book.capa = livro.getValue("capa") as String
                book.cod = livro.getValue("cod") as Long
                book.total_paginas = livro.getValue("total_paginas") as Long
                var estoque = livro.getValue("estoque") as HashMap<Any, Any>

                book.estoque = EstoqueLivro(estoque.getValue("disponiveis") as Long,
                        estoque.getValue("emprestados") as Long)

                bookList.add(book)
            }
        }
    }



}
