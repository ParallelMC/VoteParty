package me.clip.voteparty.listener

import me.clip.voteparty.conf.sections.CrateSettings
import me.clip.voteparty.conf.sections.PartySettings
import me.clip.voteparty.exte.meta
import me.clip.voteparty.exte.name
import me.clip.voteparty.listener.base.VotePartyListener
import me.clip.voteparty.plugin.VotePartyPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.meta.ItemMeta

internal class CrateListener(override val plugin: VotePartyPlugin) : VotePartyListener
{
	
	@EventHandler
	fun PlayerInteractEvent.onInteract()
	{
		if (party.conf().getProperty(CrateSettings.ENABLED) == false)
		{
			return
		}
		
		if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK)
		{
			return
		}
		
		val held = player.inventory.itemInMainHand
		val item = party.partyHandler.buildCrate(1)


		if (held.type != item.type) {
			return
		}

		val heldMeta: ItemMeta? = held.itemMeta
		val itemMeta: ItemMeta? = item.itemMeta

		//if (!held.isSimilar(item))
		if (heldMeta == null || itemMeta == null)
		{
			return
		}

		if (heldMeta.name != itemMeta.name || heldMeta.lore != itemMeta.lore) {
			return
		}
		
		if (player.world.name in party.conf().getProperty(PartySettings.DISABLED_WORLDS))
		{
			return
		}
		
		isCancelled = true
		
		if (held.amount == 1)
		{
			player.inventory.removeItem(held)
		}
		else
		{
			player.inventory.itemInHand.amount = held.amount - 1
		}
		
		party.partyHandler.runAll(player)
	}
	
}