package gg.tropic.surveys.commands

import gg.scala.commons.acf.annotation.*
import gg.scala.commons.annotations.commands.AutoRegister
import gg.scala.commons.command.ScalaCommand
import gg.scala.commons.issuer.ScalaPlayer
import gg.tropic.surveys.experience.SurveySelectionMenu
import gg.tropic.surveys.menu.SurveyAdminMenu
import gg.tropic.surveys.survey.SurveyService

@AutoRegister
@CommandAlias("survey|surveys")
@CommandPermission("surveys.command.survey")
object SurveyAdminCommands : ScalaCommand()
{
    @Default
    fun onSurveyMenu(player: ScalaPlayer) =
        SurveySelectionMenu(SurveyService.cached().surveys.values.toList()).openMenu(player.bukkit())

    @Subcommand("adminpanel")
    @Description("Opens the survey admin panel")
    fun onAdminPanel(player: ScalaPlayer) =
        SurveyAdminMenu().openMenu(player.bukkit())
}