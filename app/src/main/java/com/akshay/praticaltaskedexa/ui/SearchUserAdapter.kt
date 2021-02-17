package com.akshay.praticaltaskedexa.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.akshay.praticaltaskedexa.R
import com.akshay.praticaltaskedexa.data.model.SelectedListener
import com.akshay.praticaltaskedexa.data.model.UserListItem
import com.google.android.material.card.MaterialCardView
import java.util.*
import kotlin.collections.ArrayList

class SearchUserAdapter internal constructor(
    var context: Context
) : RecyclerView.Adapter<SearchUserAdapter.WordViewHolder>(), Filterable {

    var userFilterList = ArrayList<UserListItem>()

    private val inflater:
            LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<UserListItem>() // Cached copy of words

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.tv_user_name)
        val tvCity: TextView = itemView.findViewById(R.id.tv_city_name)
        var cardView: MaterialCardView = itemView!!.findViewById(R.id.cv_selection)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.item_user_list, parent, false)
        return WordViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return words.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = words[position]
        holder.wordItemView.text = "${current.firstName} ${current.lastName}"
        holder.tvCity.text = current.city
        holder.wordItemView.setOnLongClickListener(View.OnLongClickListener {
            current.firstName + " " + current.lastName?.let { it1 -> showDialog(current.firstName + " " + current.lastName) }
            true
        })
    }

    fun showDialog(user: String) {
        var alertDialog = AlertDialog.Builder(context)
            .setTitle("Selected User")
            .setMessage(user)
            .setPositiveButton(android.R.string.ok, null)
            .setCancelable(true)
            .show()
    }

    internal fun setWords(words: List<UserListItem>) {
        this.words = words
        userFilterList = words as ArrayList<UserListItem>
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    userFilterList = words as ArrayList<UserListItem>
                } else {
                    val resultList = ArrayList<UserListItem>()
                    for (row in words) {
                        if (row.firstName?.toLowerCase(Locale.ROOT)?.contains(charSearch.toLowerCase(Locale.ROOT))!!) {
                            resultList.add(row)
                        }
                    }
                    userFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = userFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                words = results?.values as ArrayList<UserListItem>
                notifyDataSetChanged()
            }

        }
    }


}