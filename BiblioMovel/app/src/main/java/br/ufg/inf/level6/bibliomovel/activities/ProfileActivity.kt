package br.ufg.inf.level6.bibliomovel.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import br.ufg.inf.level6.bibliomovel.R
import br.ufg.inf.level6.bibliomovel.util.AlertType
import br.ufg.inf.level6.bibliomovel.util.EasyAlerts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {

    //Referencias Firebase
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    //Elementos tela
    private var profileNome: TextView? = null
    private var profileCPF: TextView? = null
    private var profileTelefone: TextView? = null
    private var profileEmail: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initialise()

        findViewById<ImageButton>(R.id.profileActBtn1).setOnClickListener {
            finish()
        }

        findViewById<ImageButton>(R.id.profileActBtn2).setOnClickListener {
            EasyAlerts().showGenericNeutralAlert(
                    AlertType.WARNING,
                    "EM CONSTRUÇÃO",
                    "Essa funcionalidade ainda não foi implementada!",
                    this@ProfileActivity,
                    null
            )
        }
    }

    private fun initialise() {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("usuarios")
        mAuth = FirebaseAuth.getInstance()
        profileNome = findViewById<View>(R.id.profileInputNome) as TextView
        profileCPF = findViewById<View>(R.id.profileInputCPF) as TextView
        profileTelefone = findViewById<View>(R.id.profileInputTelefone) as TextView
        profileEmail = findViewById<View>(R.id.profileInputEmail) as TextView
    }

    //puxar dados salvos no firebase
    override fun onStart() {
        super.onStart()
        val mUser = mAuth!!.currentUser
        if (mUser != null) {
            val mUserReference = mDatabaseReference!!.child(mUser!!.uid)

            profileEmail!!.text = mUser.email

            mUserReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    profileCPF!!.text = snapshot.child("perfil").child("cpf").value as String
                    profileTelefone!!.text = snapshot.child("perfil").child("telefone").value as String
                    profileNome!!.text = snapshot.child("perfil").child("nome").value as String
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        } else {
            Toast.makeText(this@ProfileActivity, "É necessário estar logado para acessar o perfil", Toast.LENGTH_LONG).show()
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
            finish()
        }
    }

}
