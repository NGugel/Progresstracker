package com.dhbw.progresstracker.lernen.Verwaltung.Kategorie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dhbw.progresstracker.repository.database.Kategorie
import com.dhbw.progresstracker.databinding.ItemKategorieBinding


class KategorieAdapter(
    var kategorien: ArrayList<Kategorie>
) : RecyclerView.Adapter<KategorieAdapter.KategorieViewHolder>() {
    class KategorieViewHolder(val itemBinding: ItemKategorieBinding) :
        RecyclerView.ViewHolder(itemBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategorieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemKategorieBinding.inflate(layoutInflater, parent, false)
        return KategorieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: KategorieViewHolder, position: Int) {
        holder.itemBinding.apply {
            tvTitel.text = kategorien[position].titel
        }
    }

    override fun getItemCount(): Int {
        return kategorien.size
    }

    fun updateContent(kategorien: ArrayList<Kategorie>) {
        this.kategorien = kategorien
        notifyDataSetChanged()
    }

}