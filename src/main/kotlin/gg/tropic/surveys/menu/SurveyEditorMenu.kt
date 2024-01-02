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
                }
        )
    }
}