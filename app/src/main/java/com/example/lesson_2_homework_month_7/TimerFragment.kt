package com.example.lesson_2_homework_month_7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lesson_2_homework_month_7.databinding.FragmentTimerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class TimerFragment : Fragment() {

    private lateinit var binding: FragmentTimerBinding
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStartT.setOnClickListener {

            val hours = binding.etHours.text.toString().toIntOrNull() ?: 0
            val minutes = binding.etMinutes.text.toString().toIntOrNull() ?: 0
            val seconds = binding.etSeconds.text.toString().toIntOrNull() ?: 0
            var remainingSeconds = hours * 3600 + minutes * 60 + seconds

            val timer = Timer()
            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    job = CoroutineScope(Dispatchers.Main).launch {

                        val hoursLeft = remainingSeconds / 3600
                        val minutesLeft = (remainingSeconds % 3600) / 60
                        val secondsLeft = remainingSeconds % 60
                        activity?.runOnUiThread {
                            binding.tvTimer.text =
                                String.format("%02d:%02d:%02d", hoursLeft, minutesLeft, secondsLeft)
                        }

                        if (remainingSeconds == 0) {
                            timer.cancel()
                            activity?.runOnUiThread {
                                Toast.makeText(
                                    requireContext(),
                                    "Таймер закончился",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        remainingSeconds--
                    }
                }
            }, 0, 1000)
        }
    }
}