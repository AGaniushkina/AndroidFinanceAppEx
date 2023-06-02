package com.example.androidfinanceappex.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.example.androidfinanceappex.R
import com.example.androidfinanceappex.databinding.FragmentTransactionsBinding
import com.example.androidfinanceappex.domain.enums.TransactionType
import com.example.androidfinanceappex.domain.toDomain
import com.example.androidfinanceappex.presentation.adapters.TransactionsAdapter
import com.example.androidfinanceappex.presentation.util.launchWhenStarted
import com.example.androidfinanceappex.presentation.viewmodels.TransactionsFragmentViewModel
import com.github.mikephil.charting.charts.PieChart
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.onEach

import android.graphics.Color
import android.graphics.Typeface
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.RelativeLayout
import com.example.androidfinanceappex.domain.enums.TransactionCategory

import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF


@AndroidEntryPoint
class TransactionsFragment : Fragment(), CoroutineScope by MainScope() {

    companion object {
        private val TAB_ALL = 0
        private val TAB_INCOME = 1
        private val TAB_EXPENSE = 2
    }

    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionsFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabContent(null)
        viewModel.transactions.onEach {
            binding.recyclerViewTransactions.adapter = TransactionsAdapter(
                transactions = it.map { entity -> entity.toDomain() }
            )
        }.launchWhenStarted(lifecycleScope = lifecycleScope)

        binding.btnCreateTransaction.setOnClickListener {
            val fragment = CreateTransactionFragment()
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit()
        }

        binding.btShowCategories.setOnClickListener{
            val fragment = CategoryFragment()
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit()
        }
        binding.ivArrowBack.setOnClickListener{
            val fragment = WelcomeFragment()
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit()
        }
    }

    private fun initTabContent(type: TransactionType?) {
        showProgressBar()
        viewModel.transactions.onEach {
            when(type) {
                TransactionType.Income -> {
                    binding.recyclerViewTransactions.adapter = TransactionsAdapter(
                        transactions = it.filter { entity -> entity.type == TransactionType.Income }
                            .map { it.toDomain() }.sortedByDescending { it.id }
                    )
                    val totalIncomeBalance = it.filter { t -> t.type == TransactionType.Income }
                        .sumOf { it.amount }
                    binding.tvTotalIncomeExpense.text = "Р${totalIncomeBalance}"
                }
                TransactionType.Expense -> {
                    binding.recyclerViewTransactions.adapter = TransactionsAdapter(
                        transactions = it.filter { entity -> entity.type == TransactionType.Expense }
                            .map { it.toDomain() }.sortedByDescending { it.id }
                    )
                    val totalExpenseBalance = it.filter { t -> t.type == TransactionType.Expense }
                        .sumOf { it.amount }
                    binding.tvTotalIncomeExpense.text = "Р${totalExpenseBalance}"
                }
                null -> {
                    binding.recyclerViewTransactions.adapter = TransactionsAdapter(
                        transactions = it.map { entity -> entity.toDomain() }
                            .sortedByDescending { transaction -> transaction.id }
                    )
                }
            }

            val totalIncomeBalance = it.filter { t -> t.type == TransactionType.Income }.sumOf { it.amount }
            val totalExpenseBalance = it.filter { t -> t.type == TransactionType.Expense }.sumOf { it.amount }
            binding.tvCurrentBalance.text = "Р${totalIncomeBalance - totalExpenseBalance}"
            binding.circularProgressIndicator.progress = (totalIncomeBalance / (totalExpenseBalance + totalIncomeBalance) * 100).toInt()
            hideProgressBar()
        }.launchWhenStarted(lifecycleScope)
    }

    private fun showProgressBar() {
        binding.progressBar.progressBarLayout.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.progressBarLayout.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}