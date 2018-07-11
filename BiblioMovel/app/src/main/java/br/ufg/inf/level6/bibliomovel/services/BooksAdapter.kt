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
import android.support.v4.app.ActivityCompat.startActivityForResult




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

        imageButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                reservarLivro(position)
            }
        })

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

        livro.reservarLivro(livro)
    }
}