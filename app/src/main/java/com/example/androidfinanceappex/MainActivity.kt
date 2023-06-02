package com.example.androidfinanceappex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.androidfinanceappex.databinding.ActivityMainBinding
import com.example.androidfinanceappex.presentation.fragments.CreateTransactionFragment
import com.example.androidfinanceappex.presentation.fragments.TransactionsFragment
import com.example.androidfinanceappex.presentation.fragments.WelcomeFragment
import com.github.mikephil.charting.charts.PieChart
import dagger.hilt.android.AndroidEntryPoint

import android.graphics.Color
import android.graphics.Typeface
import com.example.androidfinanceappex.databinding.FragmentTransactionsByCatagoryBinding
import com.example.androidfinanceappex.presentation.fragments.CategoryFragment

import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                //add<CategoryFragment>(binding.fragmentContainerView.id)
                add<WelcomeFragment>(binding.fragmentContainerView.id)
            }
        }
    }
}