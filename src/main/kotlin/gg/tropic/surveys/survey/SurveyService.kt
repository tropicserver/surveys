package gg.tropic.surveys.survey

import gg.scala.commons.persist.datasync.DataSyncService
import gg.scala.commons.persist.datasync.DataSyncSource
import gg.scala.flavor.service.Service

@Service
object SurveyService : DataSyncService<SurveyContainer>()
{
    override fun keys() = SurveyKeys
    override fun type() = SurveyContainer::class.java

    override fun locatedIn() = DataSyncSource.Mongo
}