package br.ufg.inf.level6.bibliomovel

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.ufg.inf.level6.bibliomovel.models.EstoqueLivro
import br.ufg.inf.level6.bibliomovel.models.Livro
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import java.lang.reflect.Array
import java.util.*
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {

    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference().child("livros")
    var bookList: MutableList<Livro>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                addDataToList(dataSnapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("ERR", "Failed to read value.", error.toException())
            }
        })


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun addDataToList(dataSnapshot: DataSnapshot) {
        val items = dataSnapshot.children.iterator()
        var currentItem = items.next()

        var livro = currentItem.getValue() as HashMap<Any, Any>
        val book: Livro = Livro()

        book.autor = livro.getValue("autor") as String
        book.nome = livro.getValue("nome") as String
        book.descricao = livro.getValue("descricao") as String
        book.capa = livro.getValue("capa") as String
        book.estoque = livro.getValue("estoque") as EstoqueLivro


        Log.d("autor","autor: " + book.autor)

    }



}
