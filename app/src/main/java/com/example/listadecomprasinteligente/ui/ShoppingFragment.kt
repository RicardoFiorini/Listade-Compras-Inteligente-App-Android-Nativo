package com.example.listadecomprasinteligente.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listadecomprasinteligente.ShoppingApp
import com.example.listadecomprasinteligente.databinding.FragmentShoppingBinding
import com.example.listadecomprasinteligente.ui.adapter.ShoppingAdapter
import com.example.listadecomprasinteligente.ui.viewmodel.MainViewModel
import com.example.listadecomprasinteligente.ui.viewmodel.MainViewModelFactory

class ShoppingFragment : Fragment() {

    private var _binding: FragmentShoppingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((requireActivity().application as ShoppingApp).repository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentShoppingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ShoppingAdapter(
            onCheckClick = { item -> viewModel.toggleItem(item) },
            onDeleteClick = { item -> viewModel.deleteItem(item) }
        )

        binding.rvShopping.layoutManager = LinearLayoutManager(context)
        binding.rvShopping.adapter = adapter

        viewModel.shoppingList.observe(viewLifecycleOwner) { list ->
            binding.tvEmpty.isVisible = list.isEmpty()
            adapter.submitList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}