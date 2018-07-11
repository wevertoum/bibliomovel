package br.ufg.inf.level6.bibliomovel.services

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.ufg.inf.level6.bibliomovel.models.Livro
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import br.ufg.inf.level6.bibliomovel.MainActivity
import br.ufg.inf.level6.bibliomovel.R
import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivity
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.ContextCompat
import br.ufg.inf.level6.bibliomovel.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class BooksAdapter(private val context: Context ,private val list: ArrayList<Livro>) : BaseAdapter() {

    private var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.book_view, null, false)
        //Adapter gives position parameter.
        //This parameter helps us to know which item view is wanted by adapter.
        (view.findViewById<TextView>(R.id.name)).text = list[position].nome
        (view.findViewById<TextView>(R.id.autor)).text = list[position].autor
        (view.findViewById<TextView>(R.id.txtNumEstoque)).text = list[position].estoque?.disponiveis.toString()

        var imageButton = view.findViewById<ImageButton>(R.id.imageButton)

        imageButton.setOnClickListener { reservarLivro(position) }

        return view
    }

    override fun getItem(position: Int): Livro {
        return list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return list.size
    }

    fun reservarLivro(position: Int){
        var livro = getItem(position)
        var isLogged = livro.reservarLivro(livro)

        var usuarioRef = FirebaseDatabase.getInstance().getReference("usuarios")
        
        if(livro.estoque!!.disponiveis <= 0){
            return Toast.makeText(this@BooksAdapter.context, "Não há livros disponíveis!", Toast.LENGTH_LONG).show()
        }
        if(isLogged) {
            return Toast.makeText(this@BooksAdapter.context, "Livro reservado com sucesso!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this@BooksAdapter.context, "É necessário estar logado!", Toast.LENGTH_LONG).show()
            var intent: Intent = Intent(this@BooksAdapter.context, LoginActivity::class.java)
            return this@BooksAdapter.context.startActivity(intent)
        }
    }
}