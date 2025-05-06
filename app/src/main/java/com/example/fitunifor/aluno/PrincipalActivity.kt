package com.example.fitunifor.aluno

import Aula
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitunifor.R

class PrincipalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        // Inicializa os componentes
        val cardMeusTreinos = findViewById<CardView>(R.id.card_meus_treinos)
        val cardIa = findViewById<CardView>(R.id.card_ia)
        val cardCalendario = findViewById<CardView>(R.id.card_calendario)
        val buttonIniciarTreino = findViewById<Button>(R.id.button_iniciar_treino1)
        val recyclerAulas = findViewById<RecyclerView>(R.id.recycler_aulas_diarias)

        // Configura o RecyclerView
        setupAulasRecyclerView(recyclerAulas)

        // Configura os listeners
        cardMeusTreinos.setOnClickListener { navigateToMeusTreinos() }
        cardCalendario.setOnClickListener { navigateCalendario() }
        cardIa.setOnClickListener { navigteIa() }
        buttonIniciarTreino.setOnClickListener { navigateToTreinoIniciado() }
    }

    private fun setupAulasRecyclerView(recyclerView: RecyclerView) {
        // Criar lista de aulas de exemplo
        val listaAulas = listOf(
            Aula(
                id = "1",
                nome = "Yoga",
                professor = "João Silva",
                horario = "09:00",
                maxAlunos = 20,
                alunosMatriculados = 0,
                imagem = R.drawable.image_aula_yoga, // Substitua pelo seu drawable
                diaSemana = "Segunda"
            ),
            Aula(
                id = "2",
                nome = "Zumba",
                professor = "Marcela Souza",
                horario = "20:00",
                maxAlunos = 25,
                alunosMatriculados = 0,
                imagem = R.drawable.image_aula_zumba, // Substitua pelo seu drawable
                diaSemana = "Quarta"
            )
        )

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        val adapter = AulasAdapterAluno(listaAulas, isAdmin = false)
        recyclerView.adapter = adapter
    }

    private fun navigateToMeusTreinos() {
        try {
            val intent = Intent(this, MeusTreinosActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        } catch (e: Exception) {
            showErrorToast("Não foi possível abrir Meus Treinos", e)
        }
    }

    private fun navigateToTreinoIniciado() {
        try {
            val intent = Intent(this, TreinoIniciadoActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        } catch (e: Exception) {
            showErrorToast("Não foi possível iniciar o treino", e)
        }
    }

    private fun navigteIa() {
        try {
            val intent = Intent(this, IAActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        } catch (e: Exception) {
            showErrorToast("Não foi possível ir para a tela de IA", e)
        }
    }

    private fun navigateCalendario() {
        try {
            val intent = Intent(this, CalendarioActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        } catch (e: Exception) {
            showErrorToast("Não foi possível ir para a tela de Calendário", e)
        }
    }

    private fun showErrorToast(message: String, exception: Exception) {
        Toast.makeText(this, "$message\n${exception.localizedMessage}", Toast.LENGTH_LONG).show()
        exception.printStackTrace()
    }
}