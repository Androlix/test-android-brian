package com.androlix.macare

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.androlix.macare.data.Repository
import com.androlix.macare.fragment.AddEntryDialogFragment
import com.androlix.macare.fragment.GraphFragment
import com.androlix.macare.fragment.ListFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Repository.initializeRepository(applicationContext)
        this.setContentView(R.layout.activity_main)
        this.setSupportActionBar(bottomAppBar)
        this.mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        this.container.adapter = mSectionsPagerAdapter
        this.container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        this.tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        fab.setOnClickListener {
            val dialogFragment = AddEntryDialogFragment.newInstance()
            dialogFragment.show(supportFragmentManager, "new entry")
        }
    }

    override fun onStop() {
        Repository.saveDataList(applicationContext)
        super.onStop()
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> ListFragment.newInstance()
                else -> GraphFragment.newInstance()
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }
}
