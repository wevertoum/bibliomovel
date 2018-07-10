package br.ufg.inf.level6.bibliomovel

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
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


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

    }


        }





