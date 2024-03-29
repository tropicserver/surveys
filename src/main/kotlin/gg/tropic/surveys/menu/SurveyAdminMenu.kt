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

class SurveyAdminMenu : PaginatedMenu()
{
    init
    {
        updateAfterClick = true
    }

    override fun getAllPagesButtons(player: Player): Map<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        SurveyService.cached().surveys.map { it.value }.forEach {
            buttons[buttons.size] = ItemBuilder.of(Material.PAPER)
                .name(Color.translate(it.displayName))
                .addToLore(
                    "${CC.GRAY}Questions: ${CC.WHITE}${it.questions.size}",
                    "${CC.GRAY}Rewards: ${CC.WHITE}${it.rewards.size}",
                    "${CC.GRAY}Expires: &c${CC.WHITE}${
                        if (it.expiration == null) "Never" else "In ${
                            TimeUtil.formatIntoDateString(
                                Date(it.expiration!! - System.currentTimeMillis())
                            )
                        }"
                    }",
                    "",
                    "${CC.GREEN}Left-Click to edit this survey",
                    "${CC.YELLOW}Shift-Left-Click to view metrics",
                    "${CC.RED}Right-Click to delete this survey"
                ).toButton { _, clickType ->
                    if (clickType!!.isLeftClick)
                    {
                        if (!clickType.isShiftClick)
                        {
                            SurveyEditorMenu(it).openMenu(player)
                        } else
                        {
                            //todo
                        }
                    } else
                    {
                        with (SurveyService.cached()) {
                            this.surveys.remove(it.identifier)
                            SurveyService.sync(this)
                        }

                        player.sendMessage("${CC.GREEN}You have just deleted the ${it.displayName} ${CC.GREEN}survey!")
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

                            with (SurveyService.cached()) {
                                this.surveys[survey.identifier] = survey
                                SurveyService.sync(this)
                            }

                            player.sendMessage(
                                "${CC.GREEN}You have just created a new survey with the name ${Color.translate(input)}${CC.GREEN}."
                            )

                            SurveyAdminMenu().openMenu(player)
                        }.start(player)
                },
        )
    }

    override fun getPrePaginatedTitle(player: Player): String
    {
        return "Viewing All Surveys..."
    }
}