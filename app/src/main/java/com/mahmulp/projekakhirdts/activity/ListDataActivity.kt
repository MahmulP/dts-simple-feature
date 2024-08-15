package com.mahmulp.projekakhirdts.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.mahmulp.projekakhirdts.R
import com.mahmulp.projekakhirdts.data.DatabaseHelper
import com.mahmulp.projekakhirdts.databinding.ActivityInputDataBinding
import com.mahmulp.projekakhirdts.databinding.ActivityListDataBinding

class ListDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListDataBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var listData: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)
        listData = ArrayList<String>()
        listView = binding.listView

        loadData()

        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData)
        listView.adapter = adapter

        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            val itemParts = selectedItem.split("\n")
            val itemId = itemParts[0].replace("ID: ", "")

            val intent = Intent(this, InputDataActivity::class.java).apply {
                putExtra("ID", itemId)
            }
            startActivity(intent)
        }

        listView.setOnItemLongClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            val itemParts = selectedItem.split("\n")
            val itemId = itemParts[0].replace("ID: ", "")

            val deletedRows = databaseHelper.deleteData(itemId);
            if (deletedRows > 0) {
                Toast.makeText(this, "Data deleted", Toast.LENGTH_SHORT).show()
                loadData()
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Data failed to delete", Toast.LENGTH_SHORT).show()
            }

            true
        }

    }

    private fun loadData() {
        listData.clear()
        val data = databaseHelper.getAllData()
        if (data.count == 0) {
            Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show()
        } else {
            while (data.moveToNext()) {
                listData.add("ID: " + data.getString(0) + "\nNama: " + data.getString(1) + "\nUmur: " + data.getString(2) + "\nMotto: " + data.getString(3))
            }
        }
    }
}