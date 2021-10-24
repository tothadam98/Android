import java.io.File

class QuizController {
   private val questions = mutableListOf<Question>()
    private val correctAnswers = mutableListOf<String>()
    constructor(){
        File("text.txt").useLines { lines -> lines.forEach { val question = Question(it.split(",").get(0),it.split(",").subList(1,5))
        questions.add(question)
        correctAnswers.add(question.answers.get(0))} }
    }

    fun randomizeQuestions(){
        questions.shuffle()
    }

    fun doQuiz(){
//        val questions = mutableListOf<String>()
//        quiz.forEach{
//            questions.add(it.text)
//        }
//        questions.shuffle()
        questions.shuffle()
        var numberOfCorrectAnswers = 0
        println("Enter the number of how many questions you want to get? \n")
        var numberString = readLine()
        val number = numberString!!.toInt()
        var i = 0
        val playerAnswers = mutableListOf<Int>()
        while (i < number){
            println("Question number "+i+1 + ": " + questions.get(i).text)
            println("Answers:")
            var k = 1
            var correctIndex = 0
            questions.get(i).answers.shuffled();
            for(j in questions.get(i).answers){
                print(k)
                println("." + j)
                if(correctAnswers.contains(j)){
                    //k-1 because the indexing starts at 0
                    correctIndex = k-1;
                }
                k++
            }
            println("Please enter the number of your answer:")
            numberString = readLine()
            playerAnswers.add(numberString!!.toInt())
            if (correctAnswers.contains(questions.get(i).answers.get(correctIndex))){
                numberOfCorrectAnswers++
            }
            i++
        }
        println("" + numberOfCorrectAnswers + "/" + number)
    }
}

fun main(){
    val quizController = QuizController()
    quizController.doQuiz()

}