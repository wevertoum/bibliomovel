package br.ufg.inf.level6.bibliomovel

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.support.annotation.NonNull
import android.support.v4.app.FragmentActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth.AuthStateListener


class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    //variáveis globais
    private var email: String? = null
    private var password: String? = null

    //elementos tela
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnLogin: Button? = null
    private var btnCreateAccount: Button? = null
    private var mProgressBar: ProgressDialog? = null


    //referências firebase
    private var mAuth: FirebaseAuth? = null

    //inicializar referencias
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initialise()
    }
    private fun initialise() {
        etEmail = findViewById<View>(R.id.loginEdtEmail) as EditText
        etPassword = findViewById<View>(R.id.loginEdtSenha) as EditText
        btnLogin = findViewById<View>(R.id.loginBtnEntrar) as Button
        btnCreateAccount = findViewById<View>(R.id.loginBtnNovaConta) as Button
        mProgressBar = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()

        btnCreateAccount!!
                .setOnClickListener { startActivity(Intent(this@LoginActivity,
                        SignupActivity::class.java)) }
        btnLogin!!.setOnClickListener { loginUser() }
    }

    private fun loginUser() {
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mProgressBar!!.setMessage("Fazendo login...")
            mProgressBar!!.show()
            Log.d(TAG, "Fazendo login")
            mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(this) { task ->
                        mProgressBar!!.hide()
                        if (task.isSuccessful) {

                            //Sucesso, atualiza informações
                            Log.d(TAG, "signInWithEmail:success")
                            updateUI()
                        } else {
                            // Mensagem de erro
                            Log.e(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(this@LoginActivity, "Não foi possível fazer login.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
        } else {
            Toast.makeText(this, "Favor preencher todos os campos", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateUI() {
        val intent = Intent(this@LoginActivity, ProfileActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
