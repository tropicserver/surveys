package gg.tropic.surveys.survey.questions

data class Question(
    val id: String,
    var question: String,
    val answers: MutableList<QuestionResponse> = mutableListOf(QuestionResponse("yes", "Yes"))
)