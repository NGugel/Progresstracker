package com.dhbw.progresstracker.lernen.Verwaltung.Frage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dhbw.progresstracker.databinding.ItemFrageBinding
import com.dhbw.progresstracker.repository.database.Frage


class FrageAdapter(
    var fragen: ArrayList<Frage>,
    private val onItemClick: (Frage) -> Unit
) : RecyclerView.Adapter<FrageAdapter.FrageViewHolder>() {
    class FrageViewHolder(val itemBinding: ItemFrageBinding) :
        RecyclerView.ViewHolder(itemBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemFrageBinding.inflate(layoutInflater, parent, false)
        return FrageViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FrageViewHolder, position: Int) {

        holder.itemBinding.apply {
            tvFrage.text = fragen[position].text


            tvFrage.setOnClickListener {
                // Rufe die onItemClick-Funktion nur auf, wenn der Button geklickt wurde
                onItemClick(fragen[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return fragen.size
    }

    fun updateContent(fragen: ArrayList<Frage>) {
        this.fragen = fragen
        notifyDataSetChanged()
    }

}