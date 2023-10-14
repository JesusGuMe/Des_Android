package com.example.jesusexa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.jesusexa.databinding.FragmentListaCelBinding

class ListaCelularFragment: Fragment() {

    public var actualizarLista: Boolean = false
    private var _binding: FragmentListaCelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListaCelBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_fragment_lista_cel, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_fragment_lista_to_agregar -> {
                        findNavController().navigate(R.id.action_ListaCelFragment_to_AggCelFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)

        binding.recyclerView.adapter = ListaCelAdapter {
            MostrarCelular(it)
            EliminarCelular(it)
        }

    }

    fun MostrarCelular(celular: Celular) {
        Toast.makeText(context, "Click a ${celular.modelo} de la marca ${celular.marca}", Toast.LENGTH_SHORT).show()
    }

    fun EliminarCelular(celular: Celular) {
        val dialog = EliminarCelDialogFragment(celular, {
            binding.recyclerView.adapter = ListaCelAdapter ({
                EliminarCelular(it)
            })
        })
        activity?.let { dialog.show(it.supportFragmentManager, "EliminarCelDialogFragment") }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}