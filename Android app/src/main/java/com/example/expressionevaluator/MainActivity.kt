package com.example.expressionevaluator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.expressionevaluator.databinding.ActivityMainBinding
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.evaluateButton.setOnClickListener{
            try {
                var inputString = binding.expressionInput.text.toString()
                inputString = inputString.replace(215.toChar(),'*')
                inputString = inputString.replace(247.toChar(),'/')
                val outputString = ExpressionEval(inputString).evaluate().toString()
                binding.expressionOutput.text = outputString
            }catch (e:Exception){
                binding.expressionOutput.text = getString(R.string.invalid_expression)
            }
        }
    }
}

