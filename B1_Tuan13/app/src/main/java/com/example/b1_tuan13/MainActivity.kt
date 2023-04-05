package com.example.b1_tuan13

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.b1_tuan13.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDate.setOnClickListener {
            selectDate()
        }

    }

    private fun selectDate() {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
//        Log.d("test", "$day/${month + 1}/$year")
        DatePickerDialog(
            this, DatePickerDialog.OnDateSetListener { view, sYear, sMonth, sDayOfMonth ->
                Toast.makeText(this, "$sDayOfMonth/${sMonth + 1}/$sYear", Toast.LENGTH_SHORT).show()

                val dateBirth = "$sDayOfMonth/${sMonth+1}/$sYear"
                binding.txtDateSelected.text = dateBirth

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                val ngaySinh = sdf.parse(dateBirth)
                val ngaySinhTheoPhut = ngaySinh.time / 60000

                val currDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                val currDateInMinute = currDate.time/60000

                val diff = currDateInMinute - ngaySinhTheoPhut
                binding.txtAgeInMinute.text = diff.toString()


            }, year, month, day
        ).show()
    }
}

