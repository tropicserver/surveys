package gg.tropic.surveys.menu

import gg.tropic.surveys.survey.Survey
import gg.tropic.surveys.survey.SurveyService
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.buttons.AddButton
import net.evilblock.cubed.menu.pagination.PaginatedMenu
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.Color
import net.evilblock.cubed.util.bukkit.ItemBuilder
import net.evilblock.cubed.util.bukkit.prompt.InputPrompt
import net.evilblock.cubed.util.time.TimeUtil
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.*

class SurveyAdminMenu(private val surveys: List<Survey>) : PaginatedMenu()
{
    override fun getAllPagesButtons(player: Player): Map<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        surveys.forEach {
            buttons[buttons.size] = ItemBuilder.of(Material.PAPER)
                .name(Color.translate(it.displayName))
                .addToLore(
                    "${CC.GRAY}Questions: ${CC.WHITE}${it.questions.size}",
                    "${CC.GRAY}Rewards: ${CC.WHITE}${it.rewards.size}",
                    "${CC.GRAY}Expires: &c${CC.WHITE}${
                        if (it.expiration == null) "Never" else "In ${
                            TimeUtil.formatIntoDateString(
                                Date(it.expiration - System.currentTimeMillis())
                            )
                        }"
                    }",
                    "",
                    "${CC.GREEN}Left-Click to edit this survey",
                    "${CC.RED}Right-Click to delete this survey"
                ).toButton { _, clickType ->
                    if (clickType!!.isLeftClick)
                    {
                        //todo
                    } else
                    {
                        //todo
                    }
                }
        }

        return buttons
    }

    override fun getGlobalButtons(player: Player): Map<Int, Button>
    {
        return mutableMapOf(
            4 to ItemBuilder
                .copyOf(
                    object : AddButton()
                    {}
                        .getButtonItem(player)
                )
                .name("${CC.B_GREEN}Create survey")
                .addToLore(
                    "${CC.WHITE}Creates a new survey.",
                    "",
                    "${CC.GREEN}Click to create!"
                )
                .toButton { _, _ ->
                    player.closeInventory()

                    InputPrompt()
                        .withText("${CC.GREEN}Enter the name of this survey (colors supported):")
                        .acceptInput { _, input ->
                            val survey = Survey(displayName = input)

                            SurveyService.cached().surveys[survey.identifier] = survey
                            SurveyService.sync()

                            player.sendMessage(
                                "${CC.GREEN}You have just created a new survey with the name ${Color.translate(input)}${CC.GREEN}."
                            )

                            SurveyAdminMenu(SurveyService.cached().surveys.map { it.value }).openMenu(player)
                        }.start(player)
                },
        )
    }

    override fun getPrePaginatedTitle(player: Player): String
    {
        return "Viewing All Surveys..."
    }
}