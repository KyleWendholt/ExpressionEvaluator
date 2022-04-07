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
                binding.expressionOutput.text = ExpressionEval(binding.expressionInput.text.toString()
                ).evaluate().toString()
            }catch (e:Exception){
                binding.expressionOutput.text = getString(R.string.invalid_expression)
            }
        }
    }
}

