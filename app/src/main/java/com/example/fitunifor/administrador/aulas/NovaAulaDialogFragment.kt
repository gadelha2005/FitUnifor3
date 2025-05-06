package com.example.fitunifor.administrador.aulas

import Aula
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.fitunifor.R
import com.example.fitunifor.databinding.DialogNovaAulaBinding
import java.util.*

class NovaAulaDialogFragment : DialogFragment() {

    interface AulaDialogListener {
        fun onAulaSalva(aula: Aula)
        fun onAulaAtualizada(aula: Aula)
    }

    private var _binding: DialogNovaAulaBinding? = null
    private val binding get() = _binding!!
    private var listener: AulaDialogListener? = null
    private var aulaParaEdicao: Aula? = null
    private val diasSemana = listOf(
        "Segunda-feira",
        "Terça-feira",
        "Quarta-feira",
        "Quinta-feira",
        "Sexta-feira",
        "Sábado",
        "Domingo"
    )

    companion object {
        private const val ARG_AULA = "aula_para_edicao"

        fun newInstance(aula: Aula? = null): NovaAulaDialogFragment {
            return NovaAulaDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_AULA, aula)
                }
            }
        }
    }

    fun setListener(listener: AulaDialogListener) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aulaParaEdicao = arguments?.getParcelable(ARG_AULA)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogNovaAulaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupListeners()
        preencherCamposSeEdicao()
    }

    private fun setupViews() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            diasSemana
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerDiaSemana.adapter = adapter
    }

    private fun setupListeners() {
        binding.editTextHorario.setOnClickListener {
            mostrarTimePicker()
        }

        binding.buttonSalvarAula.setOnClickListener {
            salvarOuAtualizarAula()
        }

        binding.iconClose.setOnClickListener {
            dismiss()
        }
    }

    private fun preencherCamposSeEdicao() {
        aulaParaEdicao?.let { aula ->
            binding.textView35.text = "Editar Aula"
            binding.editTextNomeAula.setText(aula.nome)
            binding.editTextProfessor.setText(aula.professor)
            binding.editTextHorario.setText(aula.horario)
            binding.editMaximoAlunos.setText(aula.maxAlunos.toString())

            val posicaoDia = diasSemana.indexOf(aula.diaSemana)
            if (posicaoDia >= 0) {
                binding.spinnerDiaSemana.setSelection(posicaoDia)
            }
        }
    }

    private fun mostrarTimePicker() {
        val calendar = Calendar.getInstance()
        TimePickerDialog(
            requireContext(),
            { _, hour, minute ->
                binding.editTextHorario.setText("%02d:%02d".format(hour, minute))
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun salvarOuAtualizarAula() {
        val nomeAula = binding.editTextNomeAula.text.toString()
        val professor = binding.editTextProfessor.text.toString()
        val diaSemana = binding.spinnerDiaSemana.selectedItem.toString()
        val horario = binding.editTextHorario.text.toString()
        val maxAlunos = binding.editMaximoAlunos.text.toString().toIntOrNull() ?: 0
        val imagem = R.drawable.image_aula_coletiva// Ou implemente a seleção de imagem

        if (validarCampos(nomeAula, professor, diaSemana, horario, maxAlunos)) {
            if (aulaParaEdicao != null) {
                val aulaAtualizada = aulaParaEdicao!!.copy(
                    nome = nomeAula,
                    professor = professor,
                    diaSemana = diaSemana,
                    horario = horario,
                    maxAlunos = maxAlunos,
                    imagem = imagem
                )
                listener?.onAulaAtualizada(aulaAtualizada)
            } else {
                val novaAula = Aula(
                    nome = nomeAula,
                    professor = professor,
                    diaSemana = diaSemana,
                    horario = horario,
                    maxAlunos = maxAlunos,
                    imagem = imagem
                )
                listener?.onAulaSalva(novaAula)
            }
            dismiss()
        }
    }

    private fun validarCampos(
        nomeAula: String,
        professor: String,
        diaSemana: String,
        horario: String,
        maxAlunos: Int
    ): Boolean {
        return when {
            nomeAula.isEmpty() -> {
                binding.editTextNomeAula.error = "Digite o nome da aula"
                false
            }
            professor.isEmpty() -> {
                binding.editTextProfessor.error = "Digite o nome do professor"
                false
            }
            horario.isEmpty() -> {
                binding.editTextHorario.error = "Selecione um horário"
                false
            }
            maxAlunos <= 0 -> {
                binding.editMaximoAlunos.error = "Capacidade inválida"
                false
            }
            else -> true
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}