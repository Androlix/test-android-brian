package com.androlix.macare.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.androlix.macare.R
import com.androlix.macare.tools.Tools
import com.androlix.macare.viewModel.NewEntryModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_new_entry_bottom_sheet.view.*


class AddEntryDialogFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: NewEntryModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_new_entry_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.viewModel = ViewModelProviders.of(this).get(NewEntryModel::class.java)
        setGlycemiaLevelInput(view)
        this.setDefaultDateTimeValues()
        view.timePickerButton.setOnClickListener { openTimePicker() }
        view.datePickerButton.setOnClickListener { openDatePicker() }
        view.confirmButton.setOnClickListener {
            if (view.levelEntry.text.isNullOrEmpty() || view.levelEntry.text.isNullOrBlank())
                view.levelEntryLayout.error = getString(R.string.glycemiaLevelInputError)
            else {
                this.viewModel.addNewEntry()
                this.dismiss()
            }
        }
    }

    private fun setDefaultDateTimeValues() {
        val entry = viewModel.entry
        view?.timePickerButton?.text = Tools.formatTimeString(entry)
        view?.datePickerButton?.text = Tools.formatDateString(entry)
    }

    private fun setGlycemiaLevelInput(root: View) {
        val levelView = root.levelEntry
        levelView.requestFocus()
        levelView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (levelView.text!!.isNotEmpty())
                    this@AddEntryDialogFragment.viewModel.entry.level = levelView.text.toString().toFloat()
                else
                    this@AddEntryDialogFragment.viewModel.entry.level = 0F
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
    }

    private fun openDatePicker() {
        if (context != null) {
            val entry = viewModel.entry
            val datePickerDialog = DatePickerDialog(
                this.context!!,
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    entry.year = year
                    entry.month = monthOfYear
                    entry.day = dayOfMonth
                }, entry.year, entry.month, entry.day
            )
            datePickerDialog.show()
        }
    }

    private fun openTimePicker() {
        if (context != null) {
            val entry = viewModel.entry
            val timePickerDialog = TimePickerDialog(
                this.context!!,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    entry.hour = hourOfDay
                    entry.minutes = minute
                }, entry.hour, entry.minutes, true
            )
            timePickerDialog.show()
        }
    }

    companion object {
        fun newInstance(): AddEntryDialogFragment {
            return AddEntryDialogFragment()
        }
    }
}