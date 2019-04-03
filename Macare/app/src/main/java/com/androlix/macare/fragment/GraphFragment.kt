package com.androlix.macare.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.androlix.macare.R
import com.androlix.macare.data.GlycemicLevelEntry
import com.androlix.macare.viewModel.GraphViewModel
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.graph_fragment.view.*


class GraphFragment : Fragment() {

    companion object {
        fun newInstance() = GraphFragment()
    }

    private lateinit var viewModel: GraphViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.graph_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GraphViewModel::class.java)
        this.viewModel.data.observe(this,
            Observer<List<GlycemicLevelEntry>> {
                view?.let { it1 -> setValuesToChart(it1) }
            })
    }


    private fun setValuesToChart(view: View) {

        val chartBar = view.chartBar
        val entries = arrayListOf<BarEntry>()

        viewModel.data.value?.forEachIndexed { index, glycemicLevelEntry ->
            entries.add(BarEntry(index.toFloat(), glycemicLevelEntry.level))
        }
        chartBar.legend.isEnabled = false
        chartBar.description.isEnabled = false
        chartBar.setDrawGridBackground(false)
        chartBar.setDrawBorders(false)

        val barDataSet = BarDataSet(entries, "")
        barDataSet.setDrawValues(false)
        barDataSet.barBorderColor = ContextCompat.getColor(context!!, R.color.white)
        barDataSet.valueTextColor = ContextCompat.getColor(context!!, R.color.white)
        barDataSet.highLightColor = ContextCompat.getColor(context!!, R.color.white)
        chartBar.setGridBackgroundColor(ContextCompat.getColor(context!!, R.color.white))
        barDataSet.setColors(ContextCompat.getColor(context!!, R.color.cardListColorA))
        chartBar.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))
        val left = chartBar.axisLeft
        left.textColor = ContextCompat.getColor(context!!, R.color.white)
        left.axisLineColor = ContextCompat.getColor(context!!, R.color.white)
        left.gridColor = ContextCompat.getColor(context!!, R.color.white)
        chartBar.axisRight.isEnabled = false
        chartBar.xAxis.isEnabled = false
        val barData = BarData(barDataSet)
        barData.barWidth = .2f
        chartBar.data = barData
        chartBar.invalidate()
    }
}
