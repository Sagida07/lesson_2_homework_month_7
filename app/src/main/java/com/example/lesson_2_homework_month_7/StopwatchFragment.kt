package com.example.lesson_2_homework_month_7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.lesson_2_homework_month_7.databinding.FragmentStopwatchBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StopwatchFragment : Fragment() {

    private lateinit var binding: FragmentStopwatchBinding
    private var isRunning = false
    private var seconds = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStopwatchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStartAndStopSw.setOnClickListener {
            if (isRunning) {
                stopStopwatch()
            } else {
                startStopwatch()
            }
        }
        binding.btnReset.setOnClickListener {
            resetStopwatch()
        }
    }

    private fun startStopwatch() {
        isRunning = true
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            while (isRunning) {
                delay(1000)
                seconds++
                updateInterface()
            }
        }
    }

    private fun stopStopwatch() {
        isRunning = false
    }

    private fun resetStopwatch() {
        isRunning = false
        seconds = 0
        updateInterface()
    }

    private fun updateInterface() {
        val minutes = seconds / 60
        val hours = seconds / 3600
        val remainingSeconds = seconds % 60
        val timeString = String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
        binding.tvTimewatch.text = timeString
    }
}