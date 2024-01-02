package gg.tropic.surveys.survey.rewards

import me.lucko.helper.random.Weighted
import net.evilblock.cubed.serializers.impl.AbstractTypeSerializable
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.function.UnaryOperator

data class SurveyReward(
    var name: String,
    val command: String,
    var weightInternal: Double,
    var description: MutableList<String>
) : UnaryOperator<Player>, Weighted, AbstractTypeSerializable
{
    override fun getWeight() = this.weightInternal

    override fun apply(player: Player): Player
    {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("<target>", player.name))
        return player
    }
}