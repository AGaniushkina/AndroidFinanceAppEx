package com.example.androidfinanceappex.presentation.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.androidfinanceappex.R
import com.example.androidfinanceappex.data.local.FinanceManagerDatabase
import com.example.androidfinanceappex.databinding.FragmentTransactionsBinding
import com.example.androidfinanceappex.databinding.FragmentTransactionsByCatagoryBinding
import com.example.androidfinanceappex.domain.TransactionEntity
import com.example.androidfinanceappex.domain.enums.TransactionCategory
import com.example.androidfinanceappex.domain.enums.TransactionType
import com.example.androidfinanceappex.domain.toDomain
import com.example.androidfinanceappex.presentation.adapters.TransactionsAdapter
import com.example.androidfinanceappex.presentation.util.launchWhenStarted
import com.example.androidfinanceappex.presentation.viewmodels.TransactionsFragmentViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet

@AndroidEntryPoint
class CategoryFragment : Fragment() {
    companion object {
        private val TAB_ALL = 0
        private val TAB_INCOME = 1
        private val TAB_EXPENSE = 2
    }

    private var _binding: FragmentTransactionsByCatagoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionsFragmentViewModel by activityViewModels()

    lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionsByCatagoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivArrowBack.setOnClickListener {
            val fragment = TransactionsFragment()
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit()
        }

        pieChart = binding.pieChart
        binding.pieChart.setUsePercentValues(true)
        binding.pieChart.description.isEnabled = false
        binding.pieChart.setExtraOffsets(5f, 10f, 5f, 5f)

        // on below line we are setting drag for our pie chart
        binding.pieChart.dragDecelerationFrictionCoef = 0.95f

        // on below line we are setting hole
        // and hole color for pie chart
        binding.pieChart.isDrawHoleEnabled = true
        binding.pieChart.setHoleColor(Color.WHITE)

        // on below line we are setting circle color and alpha
        binding.pieChart.setTransparentCircleColor(Color.WHITE)
        binding.pieChart.setTransparentCircleAlpha(110)

        // on  below line we are setting hole radius
        binding.pieChart.holeRadius = 58f
        binding.pieChart.transparentCircleRadius = 61f

        // on below line we are setting center text
        binding.pieChart.setDrawCenterText(true)

        // on below line we are setting
        // rotation for our pie chart
        binding.pieChart.rotationAngle = 0f

        // enable rotation of the pieChart by touch
        binding.pieChart.isRotationEnabled = true
        binding.pieChart.isHighlightPerTapEnabled = true

        // on below line we are setting animation for our pie chart
        binding.pieChart.animateY(1400, Easing.EaseInOutQuad)

        // on below line we are disabling our legend for pie chart
        binding.pieChart.legend.isEnabled = false
        binding.pieChart.setEntryLabelColor(Color.WHITE)
        binding.pieChart.setEntryLabelTextSize(12f)

        // on below line we are creating array list and
        // adding data to it to display in pie chart
        val entries: ArrayList<PieEntry> = ArrayList()

        var totalRemittances: Double
        var totalGifts: Double
        var totalEntertainment: Double
        var totalGrocery: Double
        var totalBusiness: Double
        var totalFastFood: Double
        var totalTransport: Double
        var totalSum: Double

        var dataSet: PieDataSet

        viewModel.transactions.onEach { it ->
            totalGifts =
                it.filter { t -> t.category == TransactionCategory.Gifts }.sumOf { it.amount }
            totalEntertainment =
                it.filter { t -> t.category == TransactionCategory.Entertainment }
                    .sumOf { it.amount }
            totalGrocery =
                it.filter { t -> t.category == TransactionCategory.Grocery }.sumOf { it.amount }
            totalBusiness =
                it.filter { t -> t.category == TransactionCategory.Business }
                    .sumOf { it.amount }
            totalFastFood =
                it.filter { t -> t.category == TransactionCategory.FastFood }
                    .sumOf { it.amount }
            totalRemittances =
                it.filter { t -> t.category == TransactionCategory.Remittances }
                    .sumOf { it.amount }
            totalTransport =
                it.filter { t -> t.category == TransactionCategory.Transport }
                    .sumOf { it.amount }
            totalSum =
                it.sumOf { it.amount }          /*.launchWhenStarted(lifecycleScope = lifecycleScope)*/

            entries.add(PieEntry(((totalBusiness / totalSum) * 100).toFloat()))
            entries.add(PieEntry(((totalEntertainment / totalSum) * 100).toFloat()))
            entries.add(PieEntry(((totalFastFood / totalSum) * 100).toFloat()))
            entries.add(PieEntry(((totalGifts / totalSum) * 100).toFloat()))
            entries.add(PieEntry(((totalGrocery / totalSum) * 100).toFloat()))
            entries.add(PieEntry(((totalRemittances / totalSum) * 100).toFloat()))
            entries.add(PieEntry(((totalTransport / totalSum) * 100).toFloat()))
            binding.pieChart.data.dataSet = PieDataSet(entries, "Category Transactions")
        }

        // on below line we are setting pie data set
        entries.add(PieEntry(93.5f))
        entries.add(PieEntry(0.1f))
        entries.add(PieEntry(0.4f))
        entries.add(PieEntry(6f))
        entries.add(PieEntry(0f))
        entries.add(PieEntry(0.1f))
        entries.add(PieEntry(0f))
        dataSet = PieDataSet(entries, "Category Transactions")

        // on below line we are setting icons.
        dataSet.setDrawIcons(false)

        // on below line we are setting slice for pie
        //binding.pieChart.data.dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        //binding.pieChart.data.dataSet.selectionShift = 5f

        // add a lot of colors to list
        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.category_business))
        colors.add(resources.getColor(R.color.category_entertainment))
        colors.add(resources.getColor(R.color.category_fast_food))
        colors.add(resources.getColor(R.color.category_gifts))
        colors.add(resources.getColor(R.color.category_grocery))
        colors.add(resources.getColor(R.color.category_remittance))
        colors.add(resources.getColor(R.color.category_transport))

        binding.pieChart.data.dataSet.colors.add(resources.getColor(R.color.category_business))
        colors.add(resources.getColor(R.color.category_entertainment))
        colors.add(resources.getColor(R.color.category_fast_food))
        colors.add(resources.getColor(R.color.category_gifts))
        colors.add(resources.getColor(R.color.category_grocery))
        colors.add(resources.getColor(R.color.category_remittance))
        colors.add(resources.getColor(R.color.category_transport))

        // on below line we are setting colors.
        dataSet.colors = colors


        // on below line we are setting pie data set
        val data = PieData(dataSet)
        dataSet.valueFormatter = PercentFormatter()
        dataSet.valueTextSize = 15f
        dataSet.valueTypeface = Typeface.DEFAULT_BOLD
        dataSet.valueTextColor = Color.WHITE
        pieChart.data = data

        // undo all highlights
        binding.pieChart.highlightValues(null)

        // loading chart
        pieChart.invalidate()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}