package br.ufg.inf.level6.bibliomovel.services

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import br.ufg.inf.level6.bibliomovel.models.Livro
import android.view.LayoutInflater
import br.ufg.inf.level6.bibliomovel.R
import android.content.Intent
import android.widget.*
import br.ufg.inf.level6.bibliomovel.activities.LoginActivity
import br.ufg.inf.level6.bibliomovel.util.AlertType
import br.ufg.inf.level6.bibliomovel.util.EasyAlerts
import com.google.firebase.database.FirebaseDatabase


class BooksAdapter(private val context: Context, private val list: ArrayList<Livro>) : BaseAdapter() {

    private var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.book_view, null, false)
        val txtAvailable = (view.findViewById<TextView>(R.id.txtNumEstoque))
        val icon = (view.findViewById<ImageView>(R.id.ic_status))
        //Adapter gives position parameter.
        //This parameter helps us to know which item view is wanted by adapter.
        (view.findViewById<TextView>(R.id.name)).text = list[position].nome
        (view.findViewById<TextView>(R.id.autor)).text = list[position].autor

        val qtd = String.format("%02d", list[position].estoque?.disponiveis)
        txtAvailable.text = qtd

        swapStatus(icon, txtAvailable, list[position].estoque!!.disponiveis > 0)

        val imageButton = view.findViewById<ImageButton>(R.id.imageButton)
        imageButton.setOnClickListener { reservarLivro(position) }

        return view
    }

    private fun swapStatus(icon: ImageView, txtAvailable: TextView, available: Boolean) {
        val ok = context.resources.getDrawable(R.drawable.ic_library)
        val not_ok = context.resources.getDrawable(R.drawable.ic_cancel)
        val red = context.resources.getColor(android.R.color.holo_red_dark)
        val green = context.resources.getColor(android.R.color.holo_green_dark)

        if (available) {
            icon.setColorFilter(green)
            icon.setImageDrawable(ok)
            txtAvailable.background.setTint(green)
        } else {
            icon.setColorFilter(red)
            icon.setImageDrawable(not_ok)
            txtAvailable.background.setTint(red)
        }
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

        when {
            livro.estoque!!.disponiveis <= 0 -> {
                EasyAlerts().showGenericNeutralAlert(
                        AlertType.ERROR,
                        "ESGOTADO",
                        "Não há livros disponíveis no momento!",
                        this@BooksAdapter.context,
                        null
                )
            }
            isLogged -> Toast.makeText(this@BooksAdapter.context, "Livro reservado com sucesso!", Toast.LENGTH_LONG).show()
            else -> {
                EasyAlerts().showGenericNeutralAlert(
                        AlertType.SECURITY,
                        "ERRO",
                        "É necessário estar logado!",
                        this@BooksAdapter.context,
                        {
                            val intent: Intent = Intent(this@BooksAdapter.context, LoginActivity::class.java)
                            this@BooksAdapter.context.startActivity(intent)
                        }
                )
            }
        }
    }
}