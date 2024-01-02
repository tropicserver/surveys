package gg.tropic.surveys.menu

import gg.tropic.surveys.survey.Survey
import gg.tropic.surveys.survey.SurveyService
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.Color
import net.evilblock.cubed.util.bukkit.ItemBuilder
import net.evilblock.cubed.util.bukkit.prompt.InputPrompt
import org.bukkit.Material
import org.bukkit.entity.Player

class SurveyEditorMenu(private val survey: Survey) : Menu("Editing Survey...")
{
    override fun getButtons(player: Player): Map<Int, Button>
    {
        return mutableMapOf(
            0 to ItemBuilder.of(Material.SIGN)
                .name("${CC.B_GREEN}Change Display Name")
                .addToLore(
                    "${CC.GRAY}Change the name that users see when",
                    "${CC.GRAY}they are viewing the survey.",
                    "",
                    "${CC.GREEN}Click to change!"
                ).toButton { _, _ ->
                    InputPrompt()
                        .withText("${CC.GREEN}Enter the new name of this survey (colors supported):")
                        .acceptInput { _, input ->
                            survey.displayName = input

                            with (SurveyService.cached()) {
                                this.surveys[survey.identifier] = survey
                                SurveyService.sync(this)
                            }

                            player.sendMessage(
                                "${CC.GREEN}You have changed the display name of this survey to ${Color.translate(input)}${CC.GREEN}."
                            )

                            SurveyEditorMenu(survey).openMenu(player)
                        }.start(player)
                },

            1 to ItemBuilder.of(Material.PAPER)
                .name("${CC.B_YELLOW}Edit Survey Questions")
                .addToLore(
                    "${CC.GRAY}Change the questions that users",
                    "${CC.GRAY}will have to answer on the survey.",
                    "",
                    "${CC.GREEN}Click to change!"
                ).toButton { _, _ ->

                },

            2 to ItemBuilder.of(Material.PAPER)
                .name("${CC.B_AQUA}Edit Survey Rewards")
                .addToLore(
                    "${CC.GRAY}Change the rewards that users",
                    "${CC.GRAY}will receive when they complete",
                    "${CC.GRAY}the survey.",
                    "",
                    "${CC.GREEN}Click to change!"
                ).toButton { _, _ ->

                },

            8 to ItemBuilder.of(Material.REDSTONE_BLOCK)
                .name("${CC.B_RED}Delete Survey")
                .addToLore(
                    "${CC.GRAY}Deletes the survey and removes",
                    "${CC.GRAY}it from the active selections",
                    "",
                    "${CC.RED}Click to delete!"
                ).toButton { _, _ ->
                    with (SurveyService.cached()) {
                        this.surveys.remove(survey.identifier)
                        SurveyService.sync(this)
                    }

                    player.sendMessage(
                        "${CC.GREEN}You have just deleted the ${Color.translate(survey.displayName)} ${CC.GREEN}survey!"
                    )

                    SurveyAdminMenu().openMenu(player)
                },
        )
    }
}