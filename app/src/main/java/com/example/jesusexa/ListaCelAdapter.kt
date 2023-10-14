package com.example.jesusexa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class ListaCelAdapter(val func: (Celular) -> Unit) : RecyclerView.Adapter<ListaCelAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvModelo: TextView
        val tvPrecio: TextView
        val tvRam: TextView
        val tvAlmacenamiento: TextView
        val tvMarca: TextView
        val constraintLayout: ConstraintLayout
        val imgMarca: ImageView

        init {
            tvModelo = view.findViewById(R.id.tvModelo)
            tvPrecio = view.findViewById(R.id.tvPrecio)
            tvMarca = view.findViewById(R.id.tvMarca)
            tvRam = view.findViewById(R.id.tvRam)
            tvAlmacenamiento = view.findViewById(R.id.tvAlmacenamiento)
            constraintLayout = view.findViewById(R.id.constraint)
            imgMarca = view.findViewById(R.id.imgMarca)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_cel_view, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val celular = Singleton.listaCel[position]

        viewHolder.tvModelo.text = celular.modelo
        viewHolder.tvPrecio.text = celular.precio.toString()
        viewHolder.tvMarca.text = celular.marca
        viewHolder.tvAlmacenamiento.text = celular.almacenamientoInterno
        viewHolder.tvRam.text = celular.ramGB.toString()

        val marcaImageResId = obtenerImagenMarca(celular.marca)
        viewHolder.imgMarca.setImageResource(marcaImageResId)

        viewHolder.itemView.setOnClickListener {
            func(celular)
        }
    }

    private fun obtenerImagenMarca(marca: String): Int {
        return when (marca) {
            "Samsung" -> R.drawable.samsung_logo
            "Apple" -> R.drawable.apple_logo
            "Huawei" -> R.drawable.huawei_logo
            "ZTE" -> R.drawable.zte_logo
            "Motorola" -> R.drawable.motorola_logo
            "Xiaomi" -> R.drawable.xiaomi_logo
            "Oppo" -> R.drawable.oppo_logo
            else -> -1
        }
    }

    override fun getItemCount() = Singleton.listaCel.size
}