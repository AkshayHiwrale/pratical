package com.akshay.praticaltaskedexa.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.akshay.praticaltaskedexa.R
import com.akshay.praticaltaskedexa.data.model.UserListData
import com.akshay.praticaltaskedexa.data.model.UserListItem
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager2

    val mainViewModel: MainViewModel by viewModels()
    var selectedTab:String = "ALL"


    companion object {
        private const val NUM_PAGES = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initListener()
        mainViewModel.getUserList()
    }

    private fun initViews() {
        viewPager = findViewById(R.id.view_pager)
        viewPager.isUserInputEnabled = false
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tab_layout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "ALL"
                    }
                    1 -> {
                        tab.text = "Chicago"
                    }
                    2 -> {
                        tab.text = "NewYork"
                    }
                    3 -> {
                        tab.text = "Los Angeles"
                    }
                }
            }).attach()

        mainViewModel.count.observe(this, Observer {
            tv_count.text = it.toString()
        })



    }

    private fun initListener() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mainViewModel.currentTab.postValue(position)
                mainViewModel.selectedUser.value?.clear()
                mainViewModel.count.postValue(0)
                when (position) {
                    0 -> {
                        selectedTab = "ALL"
                    }
                    1 -> {
                        selectedTab= "Chicago"
                    }
                    2 -> {
                        selectedTab = "NewYork"
                    }
                    3 -> {
                        selectedTab = "Los Angeles"
                    }


                }
            }
            })

        bt_search.setOnClickListener {
            var intent = Intent(MainActivity@this,
                SearchActivity::class.java)
            var list: ArrayList<UserListItem>
            if (mainViewModel.currentTab.value?.equals(0)!!){
                list = mainViewModel.userList.value?.data as ArrayList<UserListItem>
            }else {
               list = mainViewModel.userList.value?.data?.filter { it.city.equals(selectedTab) } as ArrayList<UserListItem>
            }
            var userData = UserListData(list)
            intent.putExtra("USER_LIST", userData )
            startActivity(intent)

        }
    }
    private inner class ScreenSlidePagerAdapter(fa: MainActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES


        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> {
                    var homeFragment = UserListFragment()
                    var bundle = Bundle().apply {
                        putString("CITY", "ALL")
                    }
                    homeFragment.arguments = bundle
                    return homeFragment

                }
                1 -> {
                    var homeFragment = UserListFragment()
                    var bundle = Bundle().apply {
                        putString("CITY", "Chicago")
                    }
                    homeFragment.arguments = bundle
                    return homeFragment

                }
                2 -> {
                    var homeFragment = UserListFragment()
                    var bundle = Bundle().apply {
                        putString("CITY", "NewYork")
                    }
                    homeFragment.arguments = bundle
                    return homeFragment

                }
                3 -> {
                    var homeFragment = UserListFragment()
                    var bundle = Bundle().apply {
                        putString("CITY", "Los Angeles")
                    }
                    homeFragment.arguments = bundle
                    return homeFragment

                }

            }
            return Fragment()
        }
    }

}