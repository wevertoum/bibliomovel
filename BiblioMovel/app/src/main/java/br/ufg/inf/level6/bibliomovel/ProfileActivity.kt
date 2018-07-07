package br.ufg.inf.level6.bibliomovel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton

class ProfileActivity : AppCompatActivity() {

    private lateinit var edtNome:   EditText
    private lateinit var edtCpf:    EditText
    private lateinit var edtFone:   EditText
    private lateinit var edtEmail:  EditText
    private lateinit var tbBtn1:    ImageButton
    private lateinit var tbBtn2:    ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        edtNome =   findViewById(R.id.profileInputNome)
        edtCpf =    findViewById(R.id.profileInputCPF)
        edtFone =   findViewById(R.id.profileInputTelefone)
        edtEmail =  findViewById(R.id.profileInputEmail)
    }
}
