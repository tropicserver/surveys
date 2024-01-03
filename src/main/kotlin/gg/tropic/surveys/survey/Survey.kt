package gg.tropic.surveys.survey

import gg.scala.store.storage.storable.IDataStoreObject
import gg.tropic.surveys.survey.questions.Question
import gg.tropic.surveys.survey.rewards.SurveyReward
import java.util.UUID

data class Survey(
    override val identifier: UUID = UUID.randomUUID(),
    var displayName: String
) : IDataStoreObject
{
    val questions = mutableMapOf<String, Question>()
    val rewards = mutableListOf<SurveyReward>()

    var expiration: Long? = null
}