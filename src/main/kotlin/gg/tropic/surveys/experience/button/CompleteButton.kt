package gg.tropic.surveys.experience.button

import com.cryptomorin.xseries.XMaterial
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.Color
import net.evilblock.cubed.util.bukkit.ItemUtils
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack


class CompleteButton(
    private val displayName: String,
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
            "${CC.GRAY}Click to complete the survey!"
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
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTkyZTMxZmZiNTljOTBhYjA4ZmM5ZGMxZmUyNjgwMjAzNWEzYTQ3YzQyZmVlNjM0MjNiY2RiNDI2MmVjYjliNiJ9fX0="
        )
    }
}