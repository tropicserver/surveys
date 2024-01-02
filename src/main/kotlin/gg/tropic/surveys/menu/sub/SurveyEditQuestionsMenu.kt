package gg.tropic.surveys.menu.sub

import gg.tropic.surveys.menu.SurveyAdminMenu
import gg.tropic.surveys.survey.Survey
import gg.tropic.surveys.survey.SurveyService
import gg.tropic.surveys.survey.questions.Question
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.buttons.AddButton
import net.evilblock.cubed.menu.pagination.PaginatedMenu
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import net.evilblock.cubed.util.bukkit.prompt.InputPrompt
import org.bukkit.Material
import org.bukkit.entity.Player

class SurveyEditQuestionsMenu(private val survey: Survey) : PaginatedMenu()
{
    override fun getAllPagesButtons(player: Player): Map<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        survey.questions.forEach {
            buttons[buttons.size] = ItemBuilder.of(Material.PAPER)
                .name("${CC.WHITE}${it.id}")
                .addToLore(
                    "${CC.GRAY}Question: ${CC.WHITE}${it.question}",
                    "${CC.GRAY}Maximum Answers: ${CC.WHITE}${it.answers.size}",
                    "",
                    "${CC.GREEN}Left-Click to change question",
                    "${CC.YELLOW}Shift-Left-Click to change answer choices",
                    "${CC.RED}Right-Click to delete question"
                ).toButton { _, clickType ->

                }

        }

        return buttons
    }

    override fun getPrePaginatedTitle(player: Player): String
    {
        return "Editing Survey Questions..."
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
                .name("${CC.B_GREEN}Create New Question")
                .addToLore(
                    "${CC.WHITE}Creates a new question.",
                    "",
                    "${CC.GREEN}Click to create!"
                )
                .toButton { _, _ ->
                    player.closeInventory()

                    InputPrompt()
                        .withText("${CC.GREEN}Enter the id of this question:")
                        .acceptInput { _, input ->
                            survey.questions.add(Question(input.lowercase(), "Example Question"))

                            with (SurveyService.cached()) {
                                this.surveys[survey.identifier] = survey
                                SurveyService.sync(this)
                            }

                            player.sendMessage(
                                "${CC.GREEN}You have just created a new survey question with the id $input${CC.GREEN}."
                            )

                            SurveyEditQuestionsMenu(survey).openMenu(player)
                        }.start(player)
                },
        )
    }
}