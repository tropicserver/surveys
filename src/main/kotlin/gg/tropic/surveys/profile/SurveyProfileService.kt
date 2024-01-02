package gg.tropic.surveys.profile

import gg.scala.commons.persist.ProfileOrchestrator
import gg.scala.flavor.service.Service
import java.util.*

@Service
object SurveyProfileService : ProfileOrchestrator<SurveyProfile>()
{
    override fun new(uniqueId: UUID) = SurveyProfile(uniqueId)
    override fun type() = SurveyProfile::class
}