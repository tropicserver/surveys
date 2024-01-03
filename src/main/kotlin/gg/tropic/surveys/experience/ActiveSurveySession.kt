package gg.tropic.surveys.experience

import gg.tropic.surveys.survey.Survey
import gg.tropic.surveys.survey.questions.Question
import gg.tropic.surveys.survey.questions.QuestionResponse

data class ActiveSurveySession(
    val taking: Survey,
    var questionIndex: Int = 0,
    val completed: MutableList<Question> = mutableListOf(),
    val responses: MutableMap<String, QuestionResponse> = mutableMapOf()
)
{
    fun isComplete() = completed.size == taking.questions.size

    fun getAllQuestionResponses() = taking.questions.values.elementAtOrNull(questionIndex)?.answers

    fun getCurrentQuestion() = taking.questions.values.elementAt(questionIndex)
}