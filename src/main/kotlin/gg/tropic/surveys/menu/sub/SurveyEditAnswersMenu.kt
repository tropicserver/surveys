package gg.tropic.surveys.menu.sub

import gg.tropic.surveys.survey.Survey
import gg.tropic.surveys.survey.questions.Question
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.pagination.PaginatedMenu
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.Material
import org.bukkit.entity.Player

class SurveyEditAnswersMenu(private val survey: Survey, private val question: Question) : PaginatedMenu()
{
    override fun getAllPagesButtons(player: Player): Map<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        question.answers.forEach {
            buttons[buttons.size] = ItemBuilder.of(Material.NAME_TAG)
                .name("${CC.WHITE}${it.id}")
                .addToLore("${CC.GRAY}Answer Value: ${CC.WHITE}${it.responseValue}")
                .toButton()
        }

        return buttons
    }

    override fun getPrePaginatedTitle(player: Player): String
    {
        return "Editing Question Answers..."
    }
}