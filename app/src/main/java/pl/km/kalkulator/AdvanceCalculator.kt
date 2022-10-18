package pl.km.kalkulator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AdvanceCalculator : AppCompatActivity() {
    private lateinit var display: TextView
    private lateinit var calculator: Calculator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advance_calculator)
        display = findViewById(R.id.display)
        calculator = Calculator(display, findViewById(R.id.advanceCalc))
        initListeners()
    }

    private fun initListeners() {
        findViewById<Button>(R.id.button0).setOnClickListener { calculator.addDigit(0) }
        findViewById<Button>(R.id.button1).setOnClickListener { calculator.addDigit(1) }
        findViewById<Button>(R.id.button2).setOnClickListener { calculator.addDigit(2) }
        findViewById<Button>(R.id.button3).setOnClickListener { calculator.addDigit(3) }
        findViewById<Button>(R.id.button4).setOnClickListener { calculator.addDigit(4) }
        findViewById<Button>(R.id.button5).setOnClickListener { calculator.addDigit(5) }
        findViewById<Button>(R.id.button6).setOnClickListener { calculator.addDigit(6) }
        findViewById<Button>(R.id.button7).setOnClickListener { calculator.addDigit(7) }
        findViewById<Button>(R.id.button8).setOnClickListener { calculator.addDigit(8) }
        findViewById<Button>(R.id.button9).setOnClickListener { calculator.addDigit(9) }
        findViewById<Button>(R.id.buttonBksp).setOnClickListener { calculator.removeLastCharacter() }
        findViewById<Button>(R.id.buttonClear).setOnClickListener { calculator.clearDisplay() }
        findViewById<Button>(R.id.buttonEquals).setOnClickListener { calculator.calculate() }
        findViewById<Button>(R.id.buttonComa).setOnClickListener { calculator.addDot() }
        findViewById<Button>(R.id.buttonPlusMinus).setOnClickListener { calculator.changeSign() }
        findViewById<Button>(R.id.buttonPlus).setOnClickListener { calculator.addSign("+") }
        findViewById<Button>(R.id.buttonMinus).setOnClickListener { calculator.addSign("-") }
        findViewById<Button>(R.id.buttonDivide).setOnClickListener { calculator.addSign("/") }
        findViewById<Button>(R.id.buttonMultiplication).setOnClickListener { calculator.addSign("*") }
        findViewById<Button>(R.id.buttonPow).setOnClickListener { calculator.addSign("^") }
        findViewById<Button>(R.id.buttonPow2).setOnClickListener { calculator.calculatePow2() }
        findViewById<Button>(R.id.buttonLog).setOnClickListener { calculator.calculateLog() }
        findViewById<Button>(R.id.buttonLn).setOnClickListener { calculator.calculateLn() }
        findViewById<Button>(R.id.buttonSqrt).setOnClickListener { calculator.calculateSqrt() }
        findViewById<Button>(R.id.buttonSin).setOnClickListener { calculator.calculateSin() }
        findViewById<Button>(R.id.buttonCos).setOnClickListener { calculator.calculateCos() }
        findViewById<Button>(R.id.buttonTan).setOnClickListener { calculator.calculateTan() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("display_text", display.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        this.display.text = savedInstanceState.getString("display_text")
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}