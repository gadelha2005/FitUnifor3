package com.example.fitunifor

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitunifor.EsqueciSenhaActivity
import com.example.fitunifor.administrador.PainelAdminstrativoActivity
import com.example.fitunifor.aluno.PrincipalActivity
import com.example.fitunifor.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonEntrar.setOnClickListener {
            val email = binding.textEmail.text.toString().trim()
            val senha = binding.textSenha.text.toString().trim()

            if (validarCampos(email, senha)) {
                fazerLogin(email, senha)
            }
        }

        binding.buttonEsqueciSenha.setOnClickListener {
            startActivity(Intent(this, EsqueciSenhaActivity::class.java))
        }
    }

    private fun validarCampos(email: String, senha: String): Boolean {
        if (email.isEmpty()) {
            binding.textEmail.error = "Digite seu email"
            binding.textEmail.requestFocus()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.textEmail.error = "Email inválido"
            binding.textEmail.requestFocus()
            return false
        }

        if (senha.isEmpty()) {
            binding.textSenha.error = "Digite sua senha"
            binding.textSenha.requestFocus()
            return false
        }

        return true
    }

    private fun fazerLogin(email: String, senha: String) {
        firebaseAuth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login bem-sucedido
                    redirecionarUsuario(email)
                } else {
                    Toast.makeText(
                        this,
                        "Falha no login: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun redirecionarUsuario(email: String) {
        // Verifica se é o admin (você pode melhorar isso verificando no Firestore)
        val intent = if (email == "pedro@gmail.com") {
            Intent(this, PainelAdminstrativoActivity::class.java)
        } else {
            Intent(this, PrincipalActivity::class.java)
        }

        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}