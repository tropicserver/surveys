package gg.tropic.surveys

import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.commons.annotations.container.ContainerEnable
import gg.scala.commons.core.plugin.Plugin
import gg.scala.commons.core.plugin.PluginAuthor
import gg.scala.commons.core.plugin.PluginDependency
import gg.scala.commons.core.plugin.PluginWebsite

@Plugin(
    name = "Surveys",
    version = "%remote%/%branch%/%id%"
)

@PluginAuthor("Tropic")
@PluginWebsite("https://tropic.gg")

@PluginDependency("Lemon")
@PluginDependency("ScBasics")
@PluginDependency("scala-commons")
@PluginDependency("cloudsync", soft = true)
class Surveys : ExtendedScalaPlugin()
{
    @ContainerEnable
    fun containerEnable()
    {

    }
}