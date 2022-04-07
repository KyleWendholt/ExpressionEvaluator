import java.util.Scanner

fun main() {
    while (true){
        println("Is there an expression to evaluate?" +
                "\n(Note that the expression must be a valid expression" +
                " without any variables) \nEnter y/n")
        val kb = Scanner(System.`in`)
        when(kb.nextLine()[0]){
            'y'-> {
                println("Input an expression")
                try {
                    println(ExpressionEval(kb.nextLine()).evaluate())
                }catch (e:Exception){
                    println("Invalid expression")
                }
            }
            'n'-> {
                print("Goodbye")
                break
            }
        }


    }
}
