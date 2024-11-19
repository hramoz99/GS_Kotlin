package com.example.hugoramoz_95475.lista

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hugoramoz_95475.R
import com.example.hugoramoz_95475.models.Dica

class DicasAdapter(
    private var dicas: MutableList<Dica>,
    private val onItemClick: (Dica) -> Unit // Passando uma função de callback para tratar o clique
) : RecyclerView.Adapter<DicasAdapter.DicaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DicaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dica_prod, parent, false)
        return DicaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DicaViewHolder, position: Int) {
        val dica = dicas[position]
        holder.bind(dica)

        holder.itemView.setOnClickListener {
            onItemClick(dica) // Chamando o callback que foi passado para o Adapter
        }
    }
    override fun getItemCount(): Int = dicas.size

    fun updateList(newDicas: List<Dica>) {
        dicas.clear()
        dicas.addAll(newDicas)
        notifyDataSetChanged()
    }
    class DicaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titulo: TextView = itemView.findViewById(R.id.tituloDica)
        private val descricao: TextView = itemView.findViewById(R.id.descricaoDica)

        fun bind(dica: Dica) {
            titulo.text = dica.titulo
            descricao.text = dica.descricao
        }
    }
}
