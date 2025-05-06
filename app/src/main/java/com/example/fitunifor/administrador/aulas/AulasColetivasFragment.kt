package com.example.fitunifor.administrador.aulas

import Aula
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitunifor.R
import com.example.fitunifor.databinding.FragmentAulasColetivasBinding

class AulasColetivasFragment : Fragment(R.layout.fragment_aulas_coletivas) {

    private var _binding: FragmentAulasColetivasBinding? = null
    private val binding get() = _binding!!
    private lateinit var aulaAdapter: AulaAdapterAdmin

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAulasColetivasBinding.bind(view)

        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        // Aulas de exemplo
        val aulasTeste = listOf(
            Aula(
                nome = "Yoga",
                professor = "João Silva",
                diaSemana = "Segunda-feira",
                horario = "08:00",
                maxAlunos = 20,
                imagem = R.drawable.image_aula_yoga, // Substitua pelos seus drawables
                alunosMatriculados = 15
            ),
            Aula(
                nome = "Zumba",
                professor = "Maria Oliveira",
                diaSemana = "Quarta-feira",
                horario = "19:00",
                maxAlunos = 25,
                imagem = R.drawable.image_aula_zumba, // Substitua pelos seus drawables
                alunosMatriculados = 18
            )
        )

        aulaAdapter = AulaAdapterAdmin(
            aulasTeste.toMutableList(), // Converta para MutableList
            onEditarClick = { aula -> editarAula(aula) },
            onRemoverClick = { posicao -> removerAula(posicao) }
        )

        binding.recyclerAulas.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = aulaAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupListeners() {
        binding.buttonAdicionarAula.setOnClickListener {
            showNovaAulaDialog()
        }

        binding.editTextBuscarAula.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarAulas(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun showNovaAulaDialog() {
        NovaAulaDialogFragment.newInstance().apply {
            setListener(object : NovaAulaDialogFragment.AulaDialogListener {
                override fun onAulaSalva(aula: Aula) {
                    aulaAdapter.adicionarAula(aula)
                }
                override fun onAulaAtualizada(aula: Aula) {
                    aulaAdapter.atualizarAula(aula)
                }
            })
        }.show(childFragmentManager, "NovaAulaDialog")
    }

    private fun editarAula(aula: Aula) {
        NovaAulaDialogFragment.newInstance(aula).apply {
            setListener(object : NovaAulaDialogFragment.AulaDialogListener {
                override fun onAulaSalva(aula: Aula) {
                    aulaAdapter.adicionarAula(aula)
                }
                override fun onAulaAtualizada(aula: Aula) {
                    aulaAdapter.atualizarAula(aula)
                }
            })
        }.show(childFragmentManager, "EditarAulaDialog")
    }

    private fun filtrarAulas(texto: String) {
        // Implementação do filtro
    }

    private fun removerAula(posicao: Int) {
        aulaAdapter.removerAula(posicao)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}