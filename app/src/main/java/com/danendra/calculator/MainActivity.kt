package com.danendra.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.danendra.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var lastNumeric: Boolean = false
    private var stateError: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.activity = this

    }

    fun onDigit(view: View) {
        if (stateError) {
            binding.textResult.text = (view as Button).text
            stateError = false
        } else {
            binding.textResult.append((view as Button).text)
        }
        lastNumeric = true
    }

    fun onDecimal(view: View) {
        if (lastNumeric && !stateError && !lastDot) {
            binding.textResult.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        if (lastNumeric && !stateError) {
            binding.textResult.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onBreaketStart(view: View) {
        binding.textResult.append("(")
    }

    fun onBreaketEnd(view: View) {
        binding.textResult.append(")")
    }

    fun onClear(view: View) {
        this.binding.textResult.text = ""
        lastNumeric = false
        stateError = false
        lastDot = false
    }

    fun onDelete(view: View) {
        val resultAfterDel: String = binding.textResult.text.toString()
        if (resultAfterDel.isNotEmpty()) {
            binding.textResult.text = resultAfterDel.dropLast(1)
        } else binding.textResult.text = ""
    }

    @SuppressLint("SetTextI18n")
    fun onEqual(view: View) {
        if (lastNumeric && !stateError) {
            val txt = binding.textResult.text.toString()
            val expression = ExpressionBuilder(txt).build()
            try {
                val result = expression.evaluate()
                binding.textResult.text = result.toString()
                lastDot = true
            } catch (ex: ArithmeticException) {
                binding.textResult.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }

}