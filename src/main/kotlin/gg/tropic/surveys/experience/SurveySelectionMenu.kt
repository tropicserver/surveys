package gg.tropic.surveys.experience

import gg.tropic.surveys.survey.Survey
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.pagination.PaginatedMenu
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.Color
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.Material
import org.bukkit.entity.Player

class SurveySelectionMenu(private val surveys: List<Survey>) : PaginatedMenu()
{
    override fun getAllPagesButtons(player: Player): Map<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        surveys.forEach {
            buttons[buttons.size] = ItemBuilder.of(Material.PAPER)
                .name(Color.translate(it.displayName))
                .addToLore(
                    "${CC.GRAY}Upon taking this survey, you will earn",
                    "${CC.GRAY}one of the following rewards:"
                ).apply {
                    if (it.rewards.isEmpty())
                    {
                        this.addToLore("${CC.GRAY}- ${CC.RED}None")
                    } else
                    {
                        for (reward in it.rewards)
                        {
                            this.addToLore("${CC.GRAY}- ${Color.translate(reward.name)}")
                        }
                    }
                }.addToLore(
                    "",
                    "${CC.GREEN}Click to take survey!"
                ).toButton { _, _ ->
                    SurveyProcessingMenu(ActiveSurveySession(it)).openMenu(player)
                }
        }

        return buttons
    }

    override fun getPrePaginatedTitle(player: Player): String
    {
        return "Select a Survey"
    }
}