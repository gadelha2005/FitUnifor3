package com.example.fitunifor.administrador.aulas

import Aula
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitunifor.R

class AulaAdapterAdmin(
    private val listaAulas: MutableList<Aula>,
    private val onEditarClick: (Aula) -> Unit,
    private val onRemoverClick: (Int) -> Unit
) : RecyclerView.Adapter<AulaAdapterAdmin.AulaViewHolder>() {

    inner class AulaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagemAula: ImageView = itemView.findViewById(R.id.imagem_aula_admin)
        val nomeAula: TextView = itemView.findViewById(R.id.text_nome_aula)
        val professor: TextView = itemView.findViewById(R.id.text_professor)
        val dia: TextView = itemView.findViewById(R.id.text_dia)
        val horario: TextView = itemView.findViewById(R.id.text_horario)
        val capacidade: TextView = itemView.findViewById(R.id.text_capacidade)
        val integrantes: TextView = itemView.findViewById(R.id.text_integrantes)
        val btnEditar: Button = itemView.findViewById(R.id.button_editar_aula)
        val btnRemover: Button = itemView.findViewById(R.id.button_remover_aula)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AulaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_aula_admin, parent, false)
        return AulaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AulaViewHolder, position: Int) {
        val aula = listaAulas[position]

        holder.imagemAula.setImageResource(aula.imagem)
        holder.nomeAula.text = aula.nome
        holder.professor.text = "Prof. ${aula.professor}"
        holder.dia.text = "Dia: ${aula.diaSemana}"
        holder.horario.text = "Horário: ${aula.horario}"
        holder.capacidade.text = "${aula.maxAlunos} alunos"
        holder.integrantes.text = "${aula.alunosMatriculados} alunos"

        holder.btnEditar.setOnClickListener { onEditarClick(aula) }
        holder.btnRemover.setOnClickListener { onRemoverClick(position) }
    }

    override fun getItemCount() = listaAulas.size

    fun adicionarAula(aula: Aula) {
        listaAulas.add(aula)
        notifyItemInserted(listaAulas.size - 1)
    }

    fun atualizarAula(aula: Aula) {
        val index = listaAulas.indexOfFirst { it.id == aula.id }
        if (index != -1) {
            listaAulas[index] = aula
            notifyItemChanged(index)
        }
    }

    fun removerAula(position: Int) {
        listaAulas.removeAt(position)
        notifyItemRemoved(position)
    }
}