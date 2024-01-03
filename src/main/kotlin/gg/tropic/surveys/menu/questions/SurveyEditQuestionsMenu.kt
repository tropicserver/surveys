package gg.tropic.surveys.menu.questions

import gg.tropic.surveys.menu.SurveyEditorMenu
import gg.tropic.surveys.survey.Survey
import gg.tropic.surveys.survey.SurveyService
import gg.tropic.surveys.survey.questions.Question
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.buttons.AddButton
import net.evilblock.cubed.menu.buttons.BackButton
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
                .name("${CC.WHITE}${it.key}")
                .addToLore(
                    "${CC.GRAY}Question: ${CC.WHITE}${it.value.question}",
                    "${CC.GRAY}Maximum Answers: ${CC.WHITE}${it.value.answers.size}",
                    "",
                    "${CC.GREEN}Left-Click to change question",
                    "${CC.YELLOW}Shift-Left-Click to change answer choices",
                    "${CC.RED}Right-Click to delete question"
                ).toButton { _, clickType ->
                    if (clickType!!.isLeftClick)
                    {
                        if (clickType.isShiftClick)
                        {
                            SurveyEditAnswersMenu(survey, it.value).openMenu(player)
                        } else
                        {
                            player.closeInventory()

                            InputPrompt()
                                .withText("${CC.GREEN}Enter the new question you want to ask the user:")
                                .acceptInput { _, input ->
                                    val current = it.value
                                    current.question = input

                                    survey.questions[current.id] = current

                                    with (SurveyService.cached()) {
                                        this.surveys[survey.identifier] = survey
                                        SurveyService.sync(this)
                                    }

                                    player.sendMessage(
                                        "${CC.GREEN}You have just changed the question of ${CC.YELLOW}${current.id}${CC.GREEN}."
                                    )

                                    SurveyEditQuestionsMenu(survey).openMenu(player)
                                }.start(player)
                        }
                    } else
                    {
                        survey.questions.remove(it.key)

                        with (SurveyService.cached()) {
                            this.surveys[survey.identifier] = survey
                            SurveyService.sync(this)
                        }

                        player.sendMessage(
                            "${CC.GREEN}You have just deleted the question ${CC.YELLOW}${it.key}${CC.GREEN}."
                        )

                        SurveyEditQuestionsMenu(survey).openMenu(player)
                    }
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
            3 to BackButton("Survey Editor") { _ -> SurveyEditorMenu(survey).openMenu(player) },
            5 to ItemBuilder
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
                            survey.questions[input.lowercase()] = Question(input.lowercase(), "Example Question")

                            with (SurveyService.cached()) {
                                this.surveys[survey.identifier] = survey
                                SurveyService.sync(this)
                            }

                            player.sendMessage(
                                "${CC.GREEN}You have just created a new survey question with the id ${CC.YELLOW}$input${CC.GREEN}."
                            )

                            SurveyEditQuestionsMenu(survey).openMenu(player)
                        }.start(player)
                },
        )
    }
}