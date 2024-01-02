package gg.tropic.surveys.responses

import gg.scala.flavor.service.Service
import gg.scala.store.controller.DataStoreObjectControllerCache

@Service
object SurveyResponseService
{
    fun create(response: SurveyResponse) = DataStoreObjectControllerCache
        .findNotNull<SurveyResponse>()
        .save(response)
}