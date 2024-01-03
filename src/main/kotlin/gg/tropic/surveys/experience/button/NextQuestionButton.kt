package gg.tropic.surveys.experience.button

import com.cryptomorin.xseries.XMaterial
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.Color
import net.evilblock.cubed.util.bukkit.Constants
import net.evilblock.cubed.util.bukkit.ItemUtils
import net.evilblock.cubed.util.nms.MinecraftReflection
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

class NextQuestionButton(
    private val displayName: String,
    private val current: Int,
    private val total: Int,
    private val left: Boolean,
    private val callback: (Player) -> Unit,
) : Button()
{

    override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView)
    {
        callback.invoke(player)
    }

    override fun getName(player: Player): String
    {
        return Color.translate(displayName)
    }

    override fun getDescription(player: Player): List<String>
    {
        return listOf(
            "",
            "${CC.GRAY}Click to switch questions",
            "${CC.GRAY}Currently on question: ${CC.WHITE}$current/$total"
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
        val texture = if (!left)
        {
            Constants.WOOD_ARROW_RIGHT_TEXTURE
        } else
        {
            Constants.WOOD_ARROW_LEFT_TEXTURE
        }


        return ItemUtils.applySkullTexture(super.getButtonItem(player), texture)
    }

}