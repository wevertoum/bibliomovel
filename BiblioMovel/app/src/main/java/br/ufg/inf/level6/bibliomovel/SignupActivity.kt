package br.ufg.inf.level6.bibliomovel

import android.app.ProgressDialog
import android.content.Intent
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


class SignupActivity : AppCompatActivity() {

    //elementos da interface
    private var etEmail: EditText? = null
    private var etNome: EditText? = null
    private var etCPF: EditText? = null
    private var etTEL: EditText? = null
    private var etPassword: EditText? = null
    private var etConfirmaPassword: EditText? = null
    private var btnCreateAccount: Button? = null
    private var mProgressBar: ProgressDialog? = null

    //referencias do firebase
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "CreateAccountActivity"

    //variáveis globais
    private var email: String? = null
    private var nome: String? = null
    private var CPF: String? = null
    private var telefone : String? = null
    private var password: String? = null
    private var confirmaPassword : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initialise()
    }

    private fun initialise() {
        etEmail = findViewById<View>(R.id.signupEdtEmail) as EditText
        etNome = findViewById<View>(R.id.signupNome) as EditText
        etCPF = findViewById<View>(R.id.signupCPF) as EditText
        etTEL = findViewById<View>(R.id.signupTelefone) as EditText
        etPassword = findViewById<View>(R.id.signupSenha) as EditText
        etConfirmaPassword = findViewById<View>(R.id.signupEdtSenhaConfirmacao) as EditText
        btnCreateAccount = findViewById<View>(R.id.signupBtnCadastrar) as Button
        mProgressBar = ProgressDialog(this)
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        btnCreateAccount!!.setOnClickListener { createNewAccount() }
    }

    private fun createNewAccount() {
        //Validação se campos estão preenchidos
        email = etEmail?.text.toString()
        nome = etNome?.text.toString()
        CPF = etCPF?.text.toString()
        telefone = etTEL?.text.toString()
        password = etPassword?.text.toString()
        confirmaPassword = etConfirmaPassword?.text.toString()

        if ((!TextUtils.isEmpty(email) && !TextUtils.isEmpty(nome)
                && !TextUtils.isEmpty(CPF) && !TextUtils.isEmpty(telefone))
                    && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmaPassword) ){

            //Indicador do progresso
            mProgressBar!!.setMessage("Cadastrando usuário...")
            mProgressBar!!.show()

            //Chamada Firebase
            mAuth!!
                    .createUserWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(this) { task ->
                        mProgressBar!!.hide()
                        if (task.isSuccessful) {
                            // Se cadastrou corretamente, atualizar informações do usuário
                            Log.d(TAG, "createUserWithEmail:success")
                            val userId = mAuth!!.currentUser!!.uid

                            //Atualiza informações do perfil
                            val currentUserDb = mDatabaseReference!!.child(userId)
                            currentUserDb.child("nome").setValue(nome)
                            currentUserDb.child("CPF").setValue(CPF)
                            updateUserInfoAndUI()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(this@SignupActivity, "Usuário não cadastrado",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }



        } else {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUserInfoAndUI() {
        //Chama próxima atividade
        val intent = Intent(this@SignupActivity, ProfileActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}