package com.akshay.praticaltaskedexa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.akshay.praticaltaskedexa.R
import com.akshay.praticaltaskedexa.data.model.SelectedListener
import com.akshay.praticaltaskedexa.data.model.UserListItem
import kotlinx.android.synthetic.main.user_list_fragment.*
import kotlinx.coroutines.selects.select

class UserListFragment : Fragment(),SelectedListener {
    lateinit var mainViewModel: MainViewModel
    lateinit var selected: String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.user_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        var bundle = arguments
         selected = bundle?.getString("CITY","ALL").toString()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val adapter = UserListAdapter(requireActivity(),this)
        rv_user_list.adapter = adapter
        rv_user_list.layoutManager = LinearLayoutManager(activity)
        mainViewModel.userList.observe(viewLifecycleOwner, Observer {
            if (selected.equals("ALL")){
                it.data?.let { it1 -> adapter.setWords(it1) }
            }else {
                it.data?.let { it1 -> adapter.setWords(it1.filter { it.city.equals(selected) }) }
            }
            })
        if(mainViewModel.selectedUser.value.isNullOrEmpty()) mainViewModel.selectedUser.postValue(mutableListOf())
        mainViewModel.currentTab.observe(viewLifecycleOwner, Observer {
            rv_user_list.adapter?.notifyDataSetChanged()
            mainViewModel.selectedUser.value?.clear()
        })
    }

    override fun addSelectedItem(item: Any, isSelected: Boolean) {
        if (isSelected) {
           var selectedUser = item as UserListItem
            mainViewModel.selectedUser.value?.add(selectedUser)
            mainViewModel.count.postValue(mainViewModel.selectedUser.value?.size)
        }else if(!isSelected && mainViewModel.selectedUser.value?.contains(item as UserListItem)!!){
            mainViewModel.selectedUser.value!!!!.remove(item as UserListItem)
            mainViewModel.count.postValue(mainViewModel.selectedUser.value?.size)
        }
        rv_user_list.adapter?.notifyDataSetChanged()
    }

    override fun isSelectedItem(item: Any, isSelected: Boolean): Boolean {
        if (mainViewModel.selectedUser.value?.contains(item as UserListItem)!!) {
            return true
        }
        return false
    }

}
