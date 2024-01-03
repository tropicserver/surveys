package gg.tropic.surveys.menu.questions

import gg.tropic.surveys.menu.SurveyAdminMenu
import gg.tropic.surveys.menu.SurveyEditorMenu
import gg.tropic.surveys.survey.Survey
import gg.tropic.surveys.survey.SurveyService
import gg.tropic.surveys.survey.questions.Question
import gg.tropic.surveys.survey.questions.QuestionResponse
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.buttons.AddButton
import net.evilblock.cubed.menu.buttons.BackButton
import net.evilblock.cubed.menu.pagination.PaginatedMenu
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import net.evilblock.cubed.util.bukkit.prompt.InputPrompt
import org.bukkit.Material
import org.bukkit.entity.Player

class SurveyEditAnswersMenu(private val survey: Survey, private val question: Question) : PaginatedMenu()
{
    override fun getAllPagesButtons(player: Player): Map<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        question.answers.forEach {
            buttons[buttons.size] = ItemBuilder.of(Material.NAME_TAG)
                .name("${CC.WHITE}${it.key}")
                .addToLore(
                    "${CC.GRAY}Answer Value: ${CC.WHITE}${it.value.responseValue}",
                    "",
                    "${CC.GREEN}Left-Click to change answer value",
                    "${CC.RED}Right-Click to delete answer"
                )
                .toButton { _, clickType ->
                    if (clickType!!.isLeftClick)
                    {
                        player.closeInventory()

                        InputPrompt()
                            .withText("${CC.GREEN}Enter the new answer to this response:")
                            .acceptInput { _, input ->
                                val response = it.value
                                response.responseValue = input

                                question.answers[it.key] = response
                                survey.questions[question.id] = question

                                with (SurveyService.cached()) {
                                    this.surveys[survey.identifier] = survey
                                    SurveyService.sync(this)
                                }

                                player.sendMessage(
                                    "${CC.GREEN}You have just changed the survey answer of ${CC.YELLOW}${response.id}${CC.GREEN}."
                                )

                                SurveyEditAnswersMenu(survey, question).openMenu(player)
                            }.start(player)
                    } else
                    {
                        question.answers.remove(it.key)
                        survey.questions[question.id] = question

                        with (SurveyService.cached()) {
                            this.surveys[survey.identifier] = survey
                            SurveyService.sync(this)
                        }

                        player.sendMessage(
                            "${CC.GREEN}You have just deleted the question answer ${CC.YELLOW}${it.key}${CC.GREEN}."
                        )

                        SurveyEditAnswersMenu(survey, question).openMenu(player)
                    }
                }
        }

        return buttons
    }

    override fun getPrePaginatedTitle(player: Player): String
    {
        return "Editing Question Answers..."
    }

    override fun getGlobalButtons(player: Player): Map<Int, Button>
    {
        return mutableMapOf(
            3 to BackButton("Question Editor") { _ -> SurveyEditQuestionsMenu(survey).openMenu(player) },
            5 to ItemBuilder
                .copyOf(
                    object : AddButton()
                    {}
                        .getButtonItem(player)
                )
                .name("${CC.B_GREEN}Create New Answer")
                .addToLore(
                    "${CC.WHITE}Creates a new answer.",
                    "",
                    "${CC.GREEN}Click to create!"
                )
                .toButton { _, _ ->
                    player.closeInventory()

                    InputPrompt()
                        .withText("${CC.GREEN}Enter the id of this answer:")
                        .acceptInput { _, input ->
                            question.answers[input.lowercase()] = QuestionResponse(input.lowercase(), input)
                            survey.questions[question.id] = question

                            with (SurveyService.cached()) {
                                this.surveys[survey.identifier] = survey
                                SurveyService.sync(this)
                            }

                            player.sendMessage(
                                "${CC.GREEN}You have just created a new survey answer with the id ${CC.YELLOW}$input${CC.GREEN}."
                            )

                            SurveyEditAnswersMenu(survey, question).openMenu(player)
                        }.start(player)
                },
        )
    }
}