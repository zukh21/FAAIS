package kg.zukhridin.factsandachievementsinsports.dto

data class FAAISDto(
    val question: String = "title",
    val firstAnswer: String = "firstAnswer",
    val secondAnswer: String = "secondAnswer",
    val thirdAnswer: String = "thirdAnswer",
    val fourthAnswer: String = "fourthAnswer",
    val correctAnswer: String = "correctAnswer",
)