package gg.tropic.surveys.profile

import gg.scala.store.storage.storable.IDataStoreObject
import java.util.UUID

data class SurveyProfile(
    override val identifier: UUID
) : IDataStoreObject
{
    val completedSurveys = mutableMapOf<UUID, UUID>()
}