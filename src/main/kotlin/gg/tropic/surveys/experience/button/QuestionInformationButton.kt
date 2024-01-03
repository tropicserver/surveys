package gg.tropic.surveys.experience.button

import com.cryptomorin.xseries.XMaterial
import gg.tropic.surveys.survey.questions.Question
import gg.tropic.surveys.survey.questions.QuestionResponse
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.Color
import net.evilblock.cubed.util.bukkit.Constants
import net.evilblock.cubed.util.bukkit.ItemUtils
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

class QuestionInformationButton(
    private val question: Question,
    private val response: QuestionResponse?
)  : Button()
{

    override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView)
    {
    }

    override fun getName(player: Player): String
    {
        return "${CC.B_GREEN}Question Information"
    }

    override fun getDescription(player: Player): List<String>
    {
        return listOf(
            "",
            "${CC.GRAY}Question: ${CC.WHITE}${question.question}?",
            "${CC.GRAY}Your Answer: ${CC.WHITE}${response?.responseValue ?: "None"}"
        )
    }

    override fun getDamageValue(player: Player): Byte
    {
        return 3
    }

    override fun getMaterial(player: Player): XMaterial
    {
        return XMaterial.SKELETON_SKULL
    }

    override fun getButtonItem(player: Player): ItemStack
    {

        return ItemUtils.applySkullTexture(
            super.getButtonItem(player),
            "eyJ0aW1lc3RhbXAiOjE1ODc4MjU0NzgwNDcsInByb2ZpbGVJZCI6ImUzYjQ0NWM4NDdmNTQ4ZmI4YzhmYTNmMWY3ZWZiYThlIiwicHJvZmlsZU5hbWUiOiJNaW5pRGlnZ2VyVGVzdCIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2E1ODg4YWEyZDdlMTk5MTczYmEzN2NhNzVjNjhkZTdkN2Y4NjJiMzRhMTNiZTMyNDViZTQ0N2UyZjIyYjI3ZSJ9fX0="
        )
    }
}