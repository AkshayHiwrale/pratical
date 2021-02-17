package com.akshay.praticaltaskedexa.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.akshay.praticaltaskedexa.R
import com.akshay.praticaltaskedexa.data.model.UserListData
import com.akshay.praticaltaskedexa.data.model.UserListItem
import kotlinx.android.synthetic.main.search_activity.*
import kotlinx.android.synthetic.main.user_list_fragment.*

class SearchActivity : AppCompatActivity() {
    lateinit var userList: ArrayList<UserListItem>
    lateinit var user: UserListData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)
        val intent: Intent = getIntent()
        if (intent != null && intent.hasExtra("USER_LIST")) {
            user = intent.getParcelableExtra<UserListData>("USER_LIST") as UserListData
            userList = user.userListItem
        }

        initViews()

    }

    private fun initViews() {

        val adapter = SearchUserAdapter(this)
        rv_user_search.adapter = adapter
        rv_user_search.layoutManager = LinearLayoutManager(this)
        adapter.setWords(userList)
        et_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
    }

}