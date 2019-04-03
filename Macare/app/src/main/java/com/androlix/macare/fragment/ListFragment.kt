package com.androlix.macare.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androlix.macare.R
import com.androlix.macare.data.GlycemicLevelEntry
import com.androlix.macare.tools.Tools
import com.androlix.macare.viewModel.ListViewModel
import kotlinx.android.synthetic.main.list_fragment.view.*
import kotlinx.android.synthetic.main.list_fragment_item.view.*


class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: ListViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        val item = menu.getItem(0)
        val searchView = item.actionView as SearchView
        searchView.setQuery(viewModel.query, true)
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null)
                    viewModel.query = newText
                else
                    viewModel.query = ""
                onQueryChanged()
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_settings) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onQueryChanged() {
        viewModel.onQueryChanged()
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_fragment, container, false)
        this.recyclerView = view.recyclerView
        this.recyclerView.adapter = RecyclerViewAdapter()
        this.recyclerView.setHasFixedSize(true)
        this.recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        this.viewModel.data.observe(this,
            Observer<List<GlycemicLevelEntry>> {
                recyclerView.adapter?.notifyDataSetChanged()
                view?.emptyList?.visibility = when (viewModel.data.value?.isEmpty()) {
                    true -> View.VISIBLE
                    else -> View.GONE
                }
            })
    }

    inner class RecyclerViewAdapter() : RecyclerView.Adapter<RecyclerViewAdapter.EntryViewHolder>() {

        inner class EntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val glycemiaLevel = itemView.level
            val date = itemView.dateTime
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
            return EntryViewHolder(
                LayoutInflater.from(this@ListFragment.context).inflate(
                    R.layout.list_fragment_item,
                    null
                )
            )
        }

        override fun getItemCount(): Int {
            val count = this@ListFragment.viewModel.data.value?.size
            return when (count) {
                null -> 0
                else -> count
            }
        }

        override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
            val entry = this@ListFragment.viewModel.data.value?.get(holder.adapterPosition)
            if (entry != null) {
                holder.glycemiaLevel.text = getString(R.string.glycemiaLevel, entry.level)
                holder.date.text = Tools.formatDateTimeString(entry)
            }
        }

    }
}
