package gg.tropic.surveys.survey

import java.util.*

data class SurveyContainer(
    val surveys: MutableMap<UUID, Survey> = mutableMapOf()
)