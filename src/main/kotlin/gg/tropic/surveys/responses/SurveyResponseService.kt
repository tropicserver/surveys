package gg.tropic.surveys.responses

import gg.scala.store.controller.DataStoreObjectControllerCache

object SurveyResponseService
{
    fun create(response: SurveyResponse) = DataStoreObjectControllerCache
        .findNotNull<SurveyResponse>()
        .save(response)
}