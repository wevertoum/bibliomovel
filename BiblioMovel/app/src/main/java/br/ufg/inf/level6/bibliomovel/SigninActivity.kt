package br.ufg.inf.level6.bibliomovel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth.AuthStateListener


class SigninActivity : AppCompatActivity() {
    private lateinit var edtEmail:   EditText
    private lateinit var edtSenha:   EditText

    //Inicializa instancia FireBaseAuth
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        mAuth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        edtEmail = findViewById(R.id.signinSenha)
        edtSenha = findViewById(R.id.loginEdtEmail) // esse nome ta certo?

    }

    override fun onStart() {
        //Checa se o Usuário já não está logado
        val currentUser = mAuth!!.currentUser
        updateUI(currentUser)

        var email: String; var senha: String

        email = edtEmail.text.toString()
        senha = edtSenha.text.toString()
        //Cria Usuaŕio com e-mail e senha
        mAuth!!.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this) {
                    task ->
                    if (task.isSuccessful) {
                        // Sign in: success
                        // update UI for current User
                        val user = mAuth!!.currentUser
                        updateUI(user)
                    } else {
                        // Sign in: fail
                        updateUI(null)
                    }
                }


    }




}
