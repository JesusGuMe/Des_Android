package com.example.jesusexa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.jesusexa.databinding.FragmentAgregarCelBinding
import com.google.android.material.snackbar.Snackbar

class AgregarCelularFragment: Fragment() {
    private var _binding: FragmentAgregarCelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAgregarCelBinding.inflate(inflater, container, false)
        val view = binding.root

        val spinner: Spinner = view.findViewById(R.id.spinMarca)
        val marcas = arrayOf("Samsung", "Apple", "Huawei", "Xiaomi", "ZTE", "Oppo")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, marcas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val marcaSeleccionada = parent.getItemAtPosition(position).toString()
                Toast.makeText(
                    requireContext(),
                    "Selecciono: $marcaSeleccionada",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(requireContext(), "Seleccione una marca", Toast.LENGTH_SHORT).show()
            }
        }
        val spinnerAlmacenamiento: Spinner = view.findViewById(R.id.spinAlmacenamiento)
        val opcionesAlmacenamiento = arrayOf("16 GB", "32 GB", "64 GB", "128 GB", "256 GB", "512 GB")
        val almacenamientoAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opcionesAlmacenamiento)
        almacenamientoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAlmacenamiento.adapter = almacenamientoAdapter

        spinnerAlmacenamiento.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val almacenamientoSeleccionado = parent.getItemAtPosition(position).toString()
                Toast.makeText(requireContext(), "Seleccionó: $almacenamientoSeleccionado", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(requireContext(), "Seleccione una opción de almacenamiento", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    val marcaImagenesMap = mapOf(
        "Samsung" to R.drawable.samsung_logo,
        "Apple" to R.drawable.apple_logo,
        "Huawei" to R.drawable.huawei_logo,
        "ZTE" to R.drawable.zte_logo,
        "Motorola" to R.drawable.motorola_logo,
        "Xiaomi" to R.drawable.xiaomi_logo,
        "Oppo" to R.drawable.oppo_logo
    )

    private fun obtenerImagenMarca(marca: String): Int {
        return marcaImagenesMap[marca] ?: -1
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val marcaSpinner: Spinner = binding.spinMarca
        val tvSO: TextView = binding.tvSO  // Obtén la referencia al TextView tvSO

        marcaSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val marcaSeleccionada: String = parent.getItemAtPosition(position).toString()

                val imagenMarca = obtenerImagenMarca(marcaSeleccionada)

                binding.imvMarca.setImageResource(imagenMarca)

                val sistemaOperativo = obtenerSistemaOperativo(marcaSeleccionada)
                tvSO.text = "Sistema operativo: $sistemaOperativo"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(requireContext(), "Necesita tener un SO", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnAgregar.setOnClickListener {
            val id = binding.editid.text.toString()
            val marcaSpinner: Spinner = binding.spinMarca
            val ramRadioGroup: RadioGroup = binding.radioGroup
            val modelo = binding.editModelo.text.toString()
            val precioText = binding.editPrecio.text.toString()
            val almacenamientoSpinner: Spinner = binding.spinAlmacenamiento

            if (id.isEmpty()) {
                Snackbar.make(it, "Por favor, ingrese un ID", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (modelo.isEmpty()) {
                Snackbar.make(it, "Por favor, ingrese el modelo", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (marcaSpinner.selectedItemPosition == AdapterView.INVALID_POSITION) {
                Snackbar.make(it, "Por favor, seleccione una marca", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (ramRadioGroup.checkedRadioButtonId == -1) {
                Snackbar.make(it, "Por favor, seleccione la RAM", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val marcaSeleccionada: String = marcaSpinner.selectedItem.toString()
            val ramSeleccionada: Int = obtenerValorRAM(ramRadioGroup.checkedRadioButtonId)
            val marca = binding.spinMarca.selectedItem.toString()
            val ram = obtenerValorRAM(binding.radioGroup.checkedRadioButtonId)
            val almacenamiento = almacenamientoSpinner.selectedItem.toString()
            val sistemaOperativo = binding.tvSO.text.toString()
            val almacenamientoSeleccionado: String = almacenamientoSpinner.selectedItem.toString()
            val imagenMarca = obtenerImagenMarca(marca)

            val precio = try {
                precioText.toDouble()
            } catch (e: NumberFormatException) {
                null
            }
            if (precio == null || precio <= 0.0) {
                Snackbar.make(it, "Por favor, ingrese un precio valido", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val celular = Celular(
                id = id,
                modelo = modelo,
                precio = precio,
                marca = marca,
                ramGB = ram,
                almacenamientoInterno = almacenamiento,
                sistemaOperativo = sistemaOperativo,
                imagenMarca = imagenMarca
            )
            Singleton.listaCel.add(celular)
            Snackbar.make(it, "Celular Agregado con exito!", Snackbar.LENGTH_LONG)
                .setAction("Ir a la lista") {
                    findNavController().navigate(R.id.action_AggCelFragment_to_ListaCelFragment)
                }
                .show()
        }
    }

    private fun obtenerSistemaOperativo(marca: String): String {
        return when (marca) {
            "Samsung", "Oppo", "Xiaomi", "ZTE", "Motorola" -> "Android"
            "Apple" -> "iOS"
            "Huawei" -> "HarmonyOS"
            else -> "Desconocido"
        }
    }

    private fun obtenerValorRAM(radRam: Int): Int {
        return when (radRam) {
            R.id.rad4R -> 4
            R.id.rad6R -> 6
            R.id.rad8R -> 8
            R.id.rad12R -> 12
            else -> 0
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}