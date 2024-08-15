package com.mahmulp.projekakhirdts.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mahmulp.projekakhirdts.R
import com.mahmulp.projekakhirdts.data.DatabaseHelper
import com.mahmulp.projekakhirdts.databinding.ActivityInputDataBinding

class InputDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputDataBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        val dataId = intent.getStringExtra("ID")

        if (dataId != null) {
            val data = databaseHelper.getAllData()
            if (data.moveToFirst()) {
                do {
                    if (data.getString(0).equals(dataId)) {
                        binding.edName.setText(data.getString(1))
                        binding.edAge.setText(data.getString(2))
                        binding.edMotto.setText(data.getString(3))
                        binding.btnSave.text = getString(R.string.update)
                    }
                } while (data.moveToNext())
            }
        }

        binding.btnSave.setOnClickListener {
            val name = binding.edName.text.toString()
            val age = binding.edAge.text.toString().toInt()
            val motto = binding.edMotto.text.toString()

            if (binding.btnSave.text == getString(R.string.update)) {
                val isUpdated = databaseHelper.updateData(id = dataId.toString(), name = name, age = age, motto = motto)
                if (isUpdated) {
                    Toast.makeText(this, "Data updated", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to update", Toast.LENGTH_SHORT).show()
                }
            } else {
                val isCreated = databaseHelper.insertData(name = name, age = age, motto = motto)
                if (isCreated) {
                    Toast.makeText(this, "Inserted Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Inserted failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}