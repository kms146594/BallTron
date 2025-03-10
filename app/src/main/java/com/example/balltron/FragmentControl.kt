package com.example.balltron

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.widget.addTextChangedListener
import com.example.balltron.databinding.FragmentControlBinding

class FragmentControl : Fragment() {

    private val binding: FragmentControlBinding by lazy { FragmentControlBinding.inflate(layoutInflater) }

    private val editSpeed = binding.controlEditValue
    private val viewSpeed = binding.controlViewValue
    private val seekBar = binding.controlSeekbar
    private val sendButton = binding.controlButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_control, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 속도를 바 스크롤로 입력받는 경우
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewSpeed.text = "Speed: $progress"
                if (fromUser) {
                    editSpeed.setText(progress.toString())
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) { }
            override fun onStopTrackingTouch(p0: SeekBar?) { }
        })

        // 속도를 직접 입력받는 경우
        editSpeed.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val input = p0.toString().toIntOrNull()
                if (input != null && input in 0..120) {
                    seekBar.progress = input
                    viewSpeed.text = "Speed: $input"
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
        })

        // 전송 버튼 클릭 시
        sendButton.setOnClickListener {  }
    }
}