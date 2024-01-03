package gg.tropic.surveys.survey.questions

data class Question(
    val id: String,
    var question: String,
    val answers: MutableMap<String, QuestionResponse> = mutableMapOf("yes" to QuestionResponse("yes", "Yes"))
)