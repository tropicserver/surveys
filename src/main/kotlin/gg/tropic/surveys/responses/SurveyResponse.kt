package gg.tropic.surveys.responses

import gg.scala.commons.annotations.Model
import gg.scala.store.controller.annotations.Indexed
import gg.scala.store.storage.storable.IDataStoreObject
import gg.tropic.surveys.survey.questions.QuestionResponse
import java.util.UUID

@Model
data class SurveyResponse(
    override val identifier: UUID,
    @Indexed val surveyId: UUID,
    @Indexed val playerId: UUID,
    val responses: Map<String, QuestionResponse>
) : IDataStoreObject
