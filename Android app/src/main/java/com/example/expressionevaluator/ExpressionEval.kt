package com.example.expressionevaluator

import java.util.*
import kotlin.math.PI
import kotlin.math.pow

class ExpressionEval(inputString: String) {

    private val expression = inputString

    //class-wide variables:
    //Since expressions are evaluated one at a time, all evaluations can use the same two stacks.
    private val A: Stack<Char> = Stack<Char>() //stack for operators

    private val B: Stack<Double> = Stack<Double>() //stack for operands

    private var counter = 0

    fun evaluate(): Double {
        expression.replace(" ", "")
        var token: Char
        var lastToken = '?'
        do {
            token = expression[counter]
            if (token == '(' && lastToken == '-' && A.size == 0) {  //Handles unary negation
                A.pop()
                counter++
                B.push(0 - subExpressionEval())
            } else if (token == '(' && isPartOfNum(lastToken)) { //If its multiplication due to num being next to parenthesis
                dealWithOp('*')
                B.push(subExpressionEval())
            } else if (token == '(') {
                counter++
                B.push(subExpressionEval())
            } else if (token == '+' || token == '-' || token == '*' || token == '/' || token == '^') {
                if (B.size == 0 && token == '-') { //If it is a unary negation at the beginning of expression
                    A.push(token)
                    B.push(0.0)
                    counter++
                } else {
                    dealWithOp(token)
                }
            }
            //if the token is the leftmost digit of a number or a decimal point on the left,
            //form the number as a double and push onto stack B
            else if ((token in '0'..'9') || token == '.') {
                B.push(formNum())
            } else if (token == 960.toChar()) {  //char 960 is PI
                if (isPartOfNum(lastToken)) {
                    A.push('*')
                }
                B.push(PI)
                counter++
            } else {
                throw Exception("Invalid character detected in string")
            }
            lastToken = token
        } while (counter < expression.length)

        //after completely scanning the input string, evaluate any remaining operations
        while (A.size > 0) {
            eval()
        }
        return B.pop()
    }

    private fun isPartOfNum(token: Char): Boolean {
        return token in '0'..'9' || token == 960.toChar() || token == '.'
    }

    private fun subExpressionEval(): Double {
        var parenthesisCounter = 0
        var subExpression = ""
        do {
            if (expression[counter] == '(') parenthesisCounter++
            if (expression[counter] == ')') parenthesisCounter--
            subExpression += expression[counter]
            counter++
        } while (expression[counter] != ')' || parenthesisCounter > 0)
        counter++
        return ExpressionEval(subExpression).evaluate()
    }

    private fun formNum(): Double {
        val total: Double
        var intPart = 0
        var decimalPart = 0
        var count = 0
        val mult = 10
        var c: Char
        var d: Char
        var decimalFlag = 0
        do {
            c = expression[counter]
            //if the current character is a digit, convert to its
            //int value and include it in the value corresponding to the string.
            if ((c >= '0') && (c <= '9')) {
                if (decimalFlag == 0) {
                    intPart = intPart * mult + (c - '0')
                } else {
                    decimalPart = decimalPart * mult + (c - '0')
                    count++
                }
            } else {
                if (c == '.') {
                    decimalFlag = 1
                }
            }
            counter++ //Prepare to move to the next character to the right.
            d = '?' //reset d
            if (counter < expression.length) {
                d = expression[counter] //the next character to the left
            }
        } while (counter <= expression.length && (d in '0'..'9' || d == '_' || d == '.'))//check for a valid character
        total = if (decimalFlag == 1) {
            intPart + (decimalPart / (10.0.pow(count.toDouble())))
        } else {
            intPart.toDouble()
        }
        return total
    }

    /*This function compares the current operator token in the input string to the operator
    on top of stack A to determine precedence.*/
    private fun precedence(token: Char, StackA: Stack<Char>): Boolean {
        val topOp: Char = StackA.peek()
        return if (token == '^') {
            true
        } else if (topOp == '^') {
            false
        } else if (topOp == '*' || topOp == '/') {
            false //stack has precedence
        } else token == '*' || token == '/' //if token is * or / token has prec, otherwise stack
    }

    /*Expressions are evaluated from right to left, using a stack A of arithmetic
  operators and a stack B of operands. In this method, a single operation is
  evaluated by popping the current operator from A, its 2 operands from B, and by then
  performing the operation and pushing the result onto B as a double value.*/

    private fun dealWithOp(token: Char) {
        if (A.size == 0 || precedence(token, A)) {  //when you need to push
            A.push(token)                           //(A is empty or precedence is lower)
            counter++
        } else {
            do {
                eval()
            } while (!(A.size == 0 || precedence(token, A)))
            A.push(token)
            counter++
        }
    }

    private fun eval() {
        val op = A.pop() //current operator
        val operand2 = B.pop() //operands
        val operand1 = B.pop()
        val value = when (op) {
            '+' -> operand1 + operand2
            '-' -> operand1 - operand2
            '*' -> operand1 * operand2
            '/' -> operand1 / operand2
            else -> Math.pow(operand1, operand2)
        }
        B.push(value) //push result onto B
    }
}