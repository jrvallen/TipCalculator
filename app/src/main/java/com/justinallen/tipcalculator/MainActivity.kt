package com.justinallen.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import java.lang.Double.parseDouble
import java.lang.NumberFormatException
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance()
    private val percentFormat: NumberFormat = NumberFormat.getPercentInstance()

    private var billAmount: Double = 0.0
    private var percent: Double = 0.15
    private var amountTextView: TextView? = null
    private var percentTextView: TextView? = null
    private var tipTextView: TextView? = null
    private var totalTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        amountTextView = findViewById<TextView>(R.id.amountTextView)
        percentTextView = findViewById<TextView>(R.id.percentTextView)
        tipTextView = findViewById<TextView>(R.id.tipTextView)
        totalTextView = findViewById<TextView>(R.id.totalTextView)

        tipTextView?.text = currencyFormat.format(0)
        totalTextView?.text = currencyFormat.format(0)

        val amountEditText: EditText = findViewById<EditText>(R.id.amountEditText)
        amountEditText.addTextChangedListener(amountEditTextWatcher)

        val percentSeekBar: SeekBar = findViewById<SeekBar>(R.id.percentSeekBar)
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener)

    }

    private val amountEditTextWatcher: TextWatcher = object:TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            try {
                billAmount = parseDouble(s.toString()) / 100.0
                amountTextView?.text = (currencyFormat.format(billAmount))
            } catch(e:NumberFormatException){
                amountTextView?.text = ""
                billAmount = 0.0
            }
            calculate()
        }
        override fun afterTextChanged(s: Editable?) {  }
    }

    private val seekBarListener:SeekBar.OnSeekBarChangeListener = object:SeekBar.OnSeekBarChangeListener{
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            percent = progress / 100.0
            calculate()
        }
        override fun onStartTrackingTouch(seekBar: SeekBar?) {  }
        override fun onStopTrackingTouch(seekBar: SeekBar?) {  }
    }

    private fun calculate(){
        percentTextView?.text = percentFormat.format(percent)
        val tip:Double = billAmount * percent
        val total:Double = billAmount + tip
        tipTextView?.text = currencyFormat.format(tip)
        totalTextView?.text = currencyFormat.format(total)
    }

}