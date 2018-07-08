package br.ufg.inf.level6.bibliomovel

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SigninActivity : AppCompatActivity() {
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnCreateAccount: Button? = null
    private var mProgressBar: ProgressDialog? = null

    //Firebase referencias
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "SigninActivity"

    //variaveis globais
    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_signin)
       initialise()
    }

    //inicializa variaveis tela chamando ID
    private fun initialise() {
       etEmail = findViewById<View>(R.id.signinEdtEmail) as EditText
       etPassword = findViewById<View>(R.id.signinSenha) as EditText
       btnCreateAccount = findViewById<View>(R.id.signinBtnCadastrar) as Button
       mProgressBar = ProgressDialog(this)

       //mDatabaseReference é inicializado com banco de dados local com chave "Users"
       mDatabase = FirebaseDatabase.getInstance()
       //onde se armazena os dados
       mDatabaseReference = mDatabase!!.reference!!.child("Users")
       //servico de autenticacao para autenticar criação de novo usuário
       mAuth = FirebaseAuth.getInstance()

       //listener adicionado para chamar função que cria nova conta
       btnCreateAccount!!.setOnClickListener { createNewAccount() }
    }

    private fun createNewAccount() {
       email = etEmail?.text.toString()
       password = etPassword?.text.toString()

       //validação básica se está vazio
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mProgressBar!!.setMessage("Cadastrando usuário...")
            mProgressBar!!.show()

            mAuth!!
              .createUserWithEmailAndPassword(email!!, password!!)
              .addOnCompleteListener(this) { task ->
                  mProgressBar!!.hide()
                  if (task.isSuccessful) {
                    // Cadastro com sucesso, atualizar UI do usuario logado
                    Log.d(TAG, "createUserWithEmail:success")
                    val userId = mAuth!!.currentUser!!.uid


                    //atualizar informação do usuário
                    val currentUserDb = mDatabaseReference!!.child(userId)
                    currentUserDb.child("password").setValue(password)
                    currentUserDb.child("email").setValue(email)
                    updateUserInfoAndUI()
                  } else {
                    //se o cadastro falhar mostrar
                   Log.w(TAG, "createUserWithEmail:failure", task.exception)
                   Toast.makeText(this@SigninActivity, "Cadastro não realizado.",
                                   Toast.LENGTH_SHORT).show()
                 }
            }






        } else {
           Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }











    }

    private fun updateUserInfoAndUI() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    }


