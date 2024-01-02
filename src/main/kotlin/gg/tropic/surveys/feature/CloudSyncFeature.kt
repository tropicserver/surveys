package gg.tropic.surveys.feature

import gg.scala.cloudsync.shared.discovery.CloudSyncDiscoveryService
import gg.scala.commons.annotations.plugin.SoftDependency
import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import gg.scala.flavor.service.ignore.IgnoreAutoScan

@Service
@IgnoreAutoScan
@SoftDependency("cloudsync")
object CloudSyncFeature
{
    @Configure
    fun configure()
    {
        CloudSyncDiscoveryService
            .discoverable.assets
            .apply {
                add("gg.tropic.surveys:surveys:surveys-plugin")
            }
    }
}
