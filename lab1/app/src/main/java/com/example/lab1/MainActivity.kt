package com.example.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
//import androidx.core.content.ContextCompat
//import org.w3c.dom.Text
//import java.lang.Exception
//import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private var canAddOperation = false
    private var canAddDecimal = true
    /*private var workingsTV: TextView = findViewById(R.id.workingsTV)
    private var resultsTV: TextView = findViewById(R.id.resultsTV)*/
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun numberAction(view: View)
    {
        var workingsTV: TextView = findViewById(R.id.workingsTV)
        if(view is Button)
        {
            if(view.text == ".")
            {
                if(canAddDecimal)
                {
                    workingsTV.append(view.text)
                }
                canAddDecimal = false
            }
            else
            {
                workingsTV.append(view.text)
            }
            canAddOperation = true
        }
    }

    fun operationAction(view: View)
    {
        var workingsTV: TextView = findViewById(R.id.workingsTV)
        if(view is Button && canAddOperation)
        {
            workingsTV.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
    }

    fun allClearAction(view: View)
    {
        var workingsTV: TextView = findViewById(R.id.workingsTV)
        var resultsTV: TextView = findViewById(R.id.resultsTV)
        workingsTV.text = ""
        resultsTV.text = ""
    }

    fun backSpaceAction(view: View)
    {
        var workingsTV: TextView = findViewById(R.id.workingsTV)
        val length = workingsTV.length()
        if(length > 0)
        {
            workingsTV.text = workingsTV.text.subSequence(0, length - 1)
        }
    }
    fun equalsAction(view: View)
    {
        var resultsTV: TextView = findViewById(R.id.resultsTV)
        resultsTV.text = calculateResults()
    }

    private fun calculateResults(): String
    {
        val digitsOperators = digitsOperators()
        if(digitsOperators.isEmpty())
        {
            return ""
        }
        for (item in digitsOperators) {
            if (item is Float && item == 0.0f) {
                return "Error: Division by 0"
            }
        }

        val timesDivisions = timesDivisionCalculate(digitsOperators)
        if(timesDivisions.isEmpty())
        {
            return ""
        }
        val result = addSubtractCalculate(timesDivisions)
        return result.toString()
    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Float
    {
        var result = passedList[0] as Float
        for(i in passedList.indices)
        {
            if(passedList[i] is Char && i != passedList.lastIndex)
            {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Float
                if(operator == '+')
                {
                    result += nextDigit
                }
                if(operator == '-')
                {
                    result -= nextDigit
                }
            }
        }
        return result
    }

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any>
    {
        var list = passedList
        while(list.contains('x') || list.contains('/'))
        {
            list = calcTimesDiv(list)
        }
        return list
    }

    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any>
    {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size
        var errorDetected = false

        for(i in passedList.indices)
        {
            if(errorDetected) break
            if(passedList[i] is Char && i != passedList.lastIndex && i < restartIndex)
            {
                val operator = passedList[i]
                val prevDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float
                when(operator)
                {
                    'x' ->
                    {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '/' ->
                    {
                        if (nextDigit != 0.0f) {
                            newList.add(prevDigit / nextDigit)
                            restartIndex = i + 1
                        } else {
                            newList.clear()
                            newList.add("Error:Division by zero")
                            errorDetected = true
                        }
                        //newList.add(prevDigit / nextDigit)
                        //restartIndex = i + 1
                    }
                    else ->
                    {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }
            if(i > restartIndex)
            {
                newList.add(passedList[i])
            }
        }
        return newList
    }

    private fun digitsOperators(): MutableList<Any>
    {
        var workingsTV: TextView = findViewById(R.id.workingsTV)
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for(character in workingsTV.text)
        {
            if(character.isDigit() || character == '.')
            {
                currentDigit += character
            }
            else
            {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(character)
            }
        }

        if(currentDigit != "")
        {
            list.add(currentDigit.toFloat())
        }

        return list
    }
}