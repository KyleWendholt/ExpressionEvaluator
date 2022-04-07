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
                val inputString = ExpressionEval(binding.expressionInput.text.toString()
                ).evaluate().toString()
                inputString.replace(215.toChar(),'*')
                inputString.replace(257.toChar(),'/')
                binding.expressionOutput.text = inputString
            }catch (e:Exception){
                binding.expressionOutput.text = getString(R.string.invalid_expression)
            }
        }
    }
}

