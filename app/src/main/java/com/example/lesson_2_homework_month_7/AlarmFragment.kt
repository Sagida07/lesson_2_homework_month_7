package com.example.lesson_2_homework_month_7

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.timepicker.TimeFormat
import android.view.ViewGroup
import android.widget.Toast
import com.example.lesson_2_homework_month_7.databinding.FragmentAlarmBinding
import com.google.android.material.timepicker.MaterialTimePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

class AlarmFragment : Fragment() {
    private lateinit var binding: FragmentAlarmBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlarmBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnStart.setOnClickListener {
            val materialTimerPicker =
                MaterialTimePicker.Builder().setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("SELECT TIME")
                    .build()

            materialTimerPicker.show(requireFragmentManager(), "tag")

            materialTimerPicker.addOnPositiveButtonClickListener {

                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, materialTimerPicker.hour)
                calendar.set(Calendar.MINUTE, materialTimerPicker.minute)
                calendar.set(Calendar.SECOND, 0)

                val currentTime = Calendar.getInstance().timeInMillis
                val alarmTime = calendar.timeInMillis

                val delayMills = alarmTime - currentTime

                if (delayMills > 0) {
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(delayMills)
                        showToast()
                    }
                } else {
                    Toast.makeText(requireContext(), "Выберите время", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showToast() {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(requireContext(), "Будильник сработал", Toast.LENGTH_LONG).show()
        }
    }
}