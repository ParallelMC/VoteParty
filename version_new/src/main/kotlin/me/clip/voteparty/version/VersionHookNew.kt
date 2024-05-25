package me.clip.voteparty.version

import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions

class VersionHookNew : VersionHook
{
	
	override fun display(type: EffectType, location: Location, offsetX: Double, offsetY: Double, offsetZ: Double, speed: Double, count: Int, color: Color?)
	{
		
		val world = location.world ?: return
		val particle = Particle.valueOf(type.name)
		
		if (particle.dataType == DustOptions::class.java)
		{
			return world.spawnParticle(particle, location, 1, if (color == null) OPTION else DustOptions(color, 0.8F))
		}
		
		when (particle)
		{
			in SINGLE     ->
			{
				world.spawnParticle(particle, location, count)
			}
			in SPELLS     ->
			{
				val tempColor: Color = color ?: Color.fromRGB(0, 0, 0)

				world.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, tempColor)
			}
			Particle.NOTE ->
			{
				val note = if (color == null)
				{
					0.0
				}
				else
				{
					color.red / 24.0
				}
				
				world.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, note)
			}
			else          ->
			{
				world.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, 0.001)
			}
		}
	}
	
	private companion object
	{
		
		private val OPTION = DustOptions(Color.RED, 0.8F)
		
		private val SPELLS = setOf(
			Particle.ENTITY_EFFECT
		                          )
		
		private val SINGLE = setOf(
			Particle.BUBBLE,
			Particle.FISHING,
			Particle.CRIT,
			Particle.SMOKE,
			Particle.LARGE_SMOKE,
			Particle.PORTAL,
			Particle.ENCHANT,
			Particle.FLAME,
			Particle.CLOUD,
			Particle.DRAGON_BREATH,
			Particle.END_ROD,
			Particle.DAMAGE_INDICATOR,
			Particle.TOTEM_OF_UNDYING,
			Particle.SPIT,
			Particle.SQUID_INK,
			Particle.BUBBLE_POP,
			Particle.BUBBLE_COLUMN_UP,
			Particle.NAUTILUS
		                          )
		
	}
	
}