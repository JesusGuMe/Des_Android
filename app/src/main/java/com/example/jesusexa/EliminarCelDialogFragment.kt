package com.example.jesusexa

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class EliminarCelDialogFragment(val celular: Celular, val func: (Celular) -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Eliminar articulo ${celular.modelo} de la marca ${celular.marca}?")
                .setPositiveButton("Si",
                    DialogInterface.OnClickListener { dialog, id ->
                        Singleton.listaCel.remove(celular)
                        func(celular)
                    })
                .setNegativeButton("No",
                    DialogInterface.OnClickListener { dialog, id ->
                    })
            builder.create()
        } ?: throw IllegalStateException("Se tiene que confirmar la accion.")
    }
}