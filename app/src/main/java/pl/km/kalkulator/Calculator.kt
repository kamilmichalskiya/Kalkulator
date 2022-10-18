package pl.km.kalkulator

import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

class Calculator constructor(private val display: TextView, viewId: View) {
    private val signRegex = Regex("[-+/*^]")
    private val notationError =
        Snackbar.make(viewId, R.string.notation_error, Snackbar.LENGTH_SHORT)
    private val calculationError =
        Snackbar.make(viewId, R.string.calculation_error, Snackbar.LENGTH_SHORT)
    private val sizeError = Snackbar.make(viewId, R.string.size_error, Snackbar.LENGTH_SHORT)
    private val divideError = Snackbar.make(viewId, R.string.divide_error, Snackbar.LENGTH_SHORT)

    private fun getDisplayText(): String = this.display.text.toString()

    fun addDigit(digit: Int) {
        if (isWrongNotation() || isLimitReached(1)) return

        val displayText = getDisplayText()
        this.display.text = if (displayText.length == 1 && displayText.first() == '0')
            digit.toString()
        else
            displayText.plus(digit)
    }

    private fun isLimitReached(length: Int): Boolean = if (getDisplayText().length + length >= 18) {
        sizeError.show()
        true
    } else false

    fun removeLastCharacter() {
        if (getDisplayText().length > 1)
            this.display.text = getDisplayText().dropLast(1)
        else
            clearDisplay()
    }

    fun clearDisplay() {
        this.display.text = "0"
    }

    fun calculate() {
        if (isWrongNotation()) return

        val firstNumber = getFirstNumber()
        val secondNumber = getSecondNumber()
        if (firstNumber == null || secondNumber == null)
            return

        when (getOperation()) {
            "/" -> {
                if (secondNumber == 0.0)
                    divideError.show()
                else
                    display.text = getResult(firstNumber.div(secondNumber))
            }
            "*" -> display.text = getResult(firstNumber * secondNumber)
            "+" -> display.text = getResult(firstNumber.plus(secondNumber))
            "-" -> display.text = getResult(firstNumber.minus(secondNumber))
            "^" -> display.text = getResult(Math.pow(firstNumber, secondNumber))
            else -> clearDisplay()
        }
    }

    fun addSign(sign: String) {
        if (isWrongNotation() || isLimitReached(sign.length)) return

        val text = getTextWithoutFirstSign()

        if (isPossibleToAddSign()) {
            display.text = if (display.text.last() == '.')
                display.text.dropLast(1).toString() + sign
            else
                getDisplayText().plus(sign)
        } else if (text.isNotEmpty() && text.first().isDigit() && text.last().isDigit()) {
            calculate()
            if (!isWrongNotation() || isLimitReached(sign.length)) display.text =
                getDisplayText().plus(sign)
        }
    }

    fun changeSign() {
        if (isWrongNotation() || isLimitReached(1)) return

        if (isPossibleToAddSign()) {
            display.text = if (display.text.first() == '-')
                display.text.drop(1)
            else
                "-" + display.text
        }
    }

    fun addDot() {
        if (isWrongNotation() || isLimitReached(1)) return

        for (i in display.text.lastIndex downTo 0) {
            if ("-+/*".contains(display.text[i])) {
                break
            } else if (display.text[i] == '.') {
                return
            }
        }
        display.text = getDisplayText().plus(".")
    }

    private fun isWrongNotation(): Boolean = if (getDisplayText().contains('E')) {
        notationError.show()
        true
    } else false

    private fun getTextWithoutFirstSign() = if (display.text.first() == '-') {
        display.text.drop(1)
    } else {
        display.text
    }

    private fun getResult(result: Double): String {
        isLimitReached(0)
        return if (result.isFinite()) {
            val value = result.toString()
            if (value.endsWith(".0")) value.dropLast(2) else value
        } else {
            calculationError.show()
            "0"
        }
    }

    private fun isPossibleToAddSign(): Boolean {
        val text = getTextWithoutFirstSign()
        if (text.isEmpty() || !text.first().isDigit())
            return false

        return !text.contains(signRegex)
    }

    private fun getFirstNumber(): Double? {
        val text = getDisplayText()
        if (text.isEmpty() || (!text.startsWith('-') && !text.first().isDigit())
            || (text.startsWith('-') && text.length <= 1)
            || (text.startsWith('-') && !text[1].isDigit())
        ) return null

        return if (text.startsWith('-'))
            ("-" + getTextWithoutFirstSign().split(signRegex)[0]).toDoubleOrNull()
        else
            text.split(signRegex)[0].toDoubleOrNull()
    }

    private fun getSecondNumber(): Double? {
        val text = display.text
        if (text.isEmpty() || !(text.startsWith('-') || text.first().isDigit()))
            return null

        val number: List<String> = if (text.startsWith('-'))
            getTextWithoutFirstSign().split(signRegex)
        else
            text.split(signRegex)

        return if (number.size >= 2)
            number[1].toDoubleOrNull()
        else
            null
    }

    private fun getOperation(): String {
        val text = if (display.text.startsWith('-'))
            display.text.drop(1)
        else
            display.text

        return if (text.isEmpty()) ""
        else if (text.contains('/')) "/"
        else if (text.contains('*')) "*"
        else if (text.contains('+')) "+"
        else if (text.contains('-')) "-"
        else if (text.contains('^')) "^"
        else ""
    }

    fun calculatePow2() {
        if (isWrongNotation() || !isOneNumberCalculation()) return
        val firstNumber = getFirstNumber() ?: return
        display.text = getResult(firstNumber.pow(2.0))
    }

    fun calculateLn() {
        if (isWrongNotation() || !isOneNumberCalculation()) return
        val firstNumber = getFirstNumber() ?: return
        display.text = getResult(ln(firstNumber))
    }

    fun calculateSqrt() {
        if (isWrongNotation() || !isOneNumberCalculation()) return
        val firstNumber = getFirstNumber() ?: return
        display.text = getResult(sqrt(firstNumber))
    }

    fun calculateSin() {
        if (isWrongNotation() || !isOneNumberCalculation()) return
        val firstNumber = getFirstNumber() ?: return
        display.text = getResult(sin(firstNumber))
    }

    fun calculateCos() {
        if (isWrongNotation() || !isOneNumberCalculation()) return
        val firstNumber = getFirstNumber() ?: return
        display.text = getResult(cos(firstNumber))
    }

    fun calculateTan() {
        if (isWrongNotation() || !isOneNumberCalculation()) return
        val firstNumber = getFirstNumber() ?: return
        display.text = getResult(tan(firstNumber))
    }

    fun calculateLog() {
        if (isWrongNotation() || !isOneNumberCalculation()) return
        val firstNumber = getFirstNumber() ?: return
        display.text = getResult(log10(firstNumber))
    }

    private fun isOneNumberCalculation(): Boolean {
        val text = if (display.text.startsWith('-'))
            display.text.drop(1)
        else
            display.text

        return !text.contains(signRegex)
    }
}