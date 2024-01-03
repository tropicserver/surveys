package gg.tropic.surveys.experience

import gg.tropic.surveys.experience.button.NextQuestionButton
import gg.tropic.surveys.experience.button.QuestionInformationButton
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.pagination.PaginatedMenu
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.Material
import org.bukkit.entity.Player

class SurveyProcessingMenu(private val session: ActiveSurveySession) : PaginatedMenu()
{
    init
    {
        this.updateAfterClick = true
    }
    override fun getAllPagesButtons(player: Player): Map<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        session.getAllQuestionResponses()?.forEach {
            val currentQuestion = session.getCurrentQuestion()

            buttons[buttons.size] = ItemBuilder.of(Material.PAPER)
                .name("${CC.WHITE}${it.value.responseValue}")
                .apply {
                    if (session.responses.containsKey(currentQuestion.id) && session.responses[currentQuestion.id]!!.id == it.value.id)
                    {
                        this.addToLore("${CC.RED}You have already selected this option!")
                    } else
                    {
                        this.addToLore("${CC.GREEN}Click to select this option!")
                    }
                }
                .toButton { _, _ ->
                    Button.playNeutral(player)
                    session.responses[currentQuestion.id] = it.value
                }
        }

        return buttons
    }

    override fun getGlobalButtons(player: Player): Map<Int, Button>
    {
        return mutableMapOf(
            2 to NextQuestionButton(
                "&ePrevious Question",
                session.questionIndex + 1,
                session.taking.questions.size,
                true
            ) { _ ->
                if (session.questionIndex == 0)
                {
                    Button.playFail(player)
                    player.sendMessage("${CC.RED}You are already on the first question!")
                } else
                {
                    session.questionIndex -= 1
                    Button.playSuccess(player)
                }
            },

            4 to QuestionInformationButton(
                session.getCurrentQuestion(),
                session.responses[session.getCurrentQuestion().id]
            ),

            6 to NextQuestionButton(
                "&eNext Question",
                session.questionIndex + 1,
                session.taking.questions.size,
                false
            ) { _ ->
                if (session.questionIndex == session.taking.questions.size - 1)
                {
                    Button.playFail(player)
                    player.sendMessage("${CC.RED}You are already on the last question!")
                } else
                {
                    session.questionIndex += 1
                    Button.playSuccess(player)
                }
            }
        )
    }

    override fun getPrePaginatedTitle(player: Player): String
    {
        return "Question #${session.completed.size + 1}"
    }
}