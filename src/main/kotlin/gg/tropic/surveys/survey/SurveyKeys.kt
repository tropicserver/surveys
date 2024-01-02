package gg.tropic.surveys.survey

import gg.scala.commons.persist.datasync.DataSyncKeys

object SurveyKeys : DataSyncKeys
{
    override fun store() = keyOf("surveys", "surveys")

    override fun newStore() = "surveys"
    override fun sync() = keyOf("surveys", "survey-sync")
}