package me.clip.voteparty.conf

import com.sxtanna.korm.data.custom.KormComment
import com.sxtanna.korm.data.custom.KormList
import me.clip.voteparty.base.PREFIX
import me.clip.voteparty.conf.base.Config
import me.clip.voteparty.plugin.XMaterial
import me.clip.voteparty.version.EffectType
import org.bukkit.World
import java.util.concurrent.ThreadLocalRandom

data class ConfigVoteParty(
		
		var settings: SettingsConfig?,
		var effects: EffectsConfig?,
		@KormComment("Configuration for the crate that can be given to players.",
		             "Here, you can utilize a few different options for customization.",
		             "First off, you can enable / disable the entire crate.",
		             "This will determine if they can be given out in game & if they can be placed down to receive rewards.",
		             "Then, you can customize the lore to your choosing!",
		             "Next, you get to choose the material. The default is a chest, since it is a crate after all.",
		             "Finally, you get to customize the name of the crate to your liking!")
		var crate: CrateConfig?,
		var voting: VoteConfig?,
		var party: PartyConfig?
                          ) : Config
{
	
	data class SettingsConfig(
			@KormComment("The default language of the plugin")
			var language: String,
			@KormComment("The prefix of all the messages in the plugin")
			var prefix: String
	                         ) : Config
	
	data class EffectsConfig(
			@KormComment("Configuration for particles when a player votes")
			var vote: Effects,
			@KormComment("Configuration for particles when a party starts")
			var party_start: Effects,
			@KormComment("Configuration for particle effects you can play",
			             "throughout different parts of the plugin", "", "Configuration for particles when party", "commands are being executed")
			var party_command_execute: Effects
	                        ) : Config
	
	
	data class CrateConfig(
			var enabled: Boolean,
			var material: XMaterial,
			@KormComment("You may modify this to whatever your liking it.", "This supports color codes!")
			var name: String,
			@KormComment("Modifying the default lore of the crate is super simple!",
			             "Example: I want to make my config have two lines. I want the first line to say \"Place Me\"",
			             "I want the second line to say \"For Awesome Rewards!\"",
			             "In order to do that, I would set the lore to the following:",
			             "[\"Place Me\", \"For Awesome Rewards!\"]")
			var lore: List<String>
	                      ) : Config
	
	data class VoteConfig(
			@KormComment("Random commands to try and execute on players", "Format is [Chance Percentage, \"Command\"]",
			             "Refer to example in party for how to add more")
			var per_vote_rewards: RewardsPerVote,
			@KormComment("Guaranteed commands you want to execute on the player that voted")
			var guaranteed_rewards: Commands,
			@KormComment("Configuration for the voting part of the plugin", "", "Global commands to run when a player votes")
			var global_commands: Commands
	                     ) : Config
	
	
	data class PartyConfig(
			@KormComment("The amount of votes needed to start the party")
			var votes_needed: Int,
			@KormComment("Configuration for the vote party", "", "Input worlds you would like to disable the party in"
			             , "For example, disabledWorlds: [\"world_nether\"]", "would disable parties in the nether")
			var disabled_worlds: Set<World>,
			@KormComment("Allow offline votes to count towards party")
			var offline_votes: Boolean,
			@KormComment("Max random rewards from the list below")
			var max_rewards_per_player: Int,
			@KormComment("How long to wait to start party after reaching votes needed")
			var start_delay: Long,
			@KormComment("Random commands to try and execute on players", "Format is [Chance Percentage, \"Command\"]",
			             "For example, if I wanted to give a player", "\$100 from Essentials, with a 90% chance,",
			             "I would do the following:", "[90, \"eco give %player_name% 100\"]")
			var reward_commands: CommandsReward,
			@KormComment("Configure the commands you want to guarantee the player will get", "In other words, players will ALWAYS get ALL of these")
			var guaranteed_rewards: Commands,
			@KormComment("Commands to execute at the beginning of the start delay")
			var pre_party_commands: Commands,
			@KormComment("Commands to execute when a party is starting")
			var party_commands: Commands
	                      ) : Config
	
	data class RewardsPerVote(
			var enabled: Boolean,
			@KormComment("Max possible commands that will execute from the list above")
			var max_possible: Int,
			var commands: List<Command>
	                         ) : Config
	
	
	data class Effects(
			var enabled: Boolean,
			var effects: List<EffectType?>?
	                  ) : Config
	
	data class Commands(
			var enabled: Boolean,
			var commands: List<String>
	                   ) : Config
	
	data class CommandsReward(
			@KormComment("How long should the plugin wait", "inbetween each command being executed")
			var delay: Long,
			var commands: List<Command>
	                         ) : Config
	
	
	data class Command(var chance: Int, var command: String)
	{
		
		fun randomChance(): Boolean
		{
			return chance <= ThreadLocalRandom.current().nextInt(100)
		}
		
	}
	
	
	companion object
	{
		
		private val DEF_CRATE_CONFIG = CrateConfig(true, XMaterial.CHEST, "&b&lVote&f&lParty &7Crate", listOf("&aPlace me &e:)"))
		private val DEF_PER_VOTE_REWARDS = RewardsPerVote(true, 1, listOf(Command(50, "eco give %player_name% 100"), Command(70, "give %player_name% STEAK 10")))
		private val DEF_GUARANTEED_REWARDS = Commands(true, listOf("eco give %player_name% 10", "give %player_name% STEAK 8"))
		private val DEF_GLOBAL_COMMANDS = Commands(true, listOf("broadcast Only %voteparty_votes_required_party% more votes until a VoteParty!"))
		private val DEF_VOTE_CONFIG = VoteConfig(DEF_PER_VOTE_REWARDS, DEF_GUARANTEED_REWARDS, DEF_GLOBAL_COMMANDS)
		private val DEF_REWARD_COMMANDS = CommandsReward(1, listOf(Command(50, "eco give %player_name% 100"), Command(50, "give %player_name% DIAMOND 6"), Command(50, "give %player_name% IRON_INGOT 12")))
		private val DEF_PRE_PARTY_COMMANDS = Commands(true, listOf("broadcast Party will start soon!"))
		private val DEF_PARTY_COMMANDS = Commands(true, listOf("broadcast Party Starting!"))
		private val DEF_PARTY_CONFIG = PartyConfig(50, setOf(), true, 1, 15, DEF_REWARD_COMMANDS, DEF_GUARANTEED_REWARDS, DEF_PRE_PARTY_COMMANDS, DEF_PARTY_COMMANDS)
		private val DEF_VOTE_EFFECTS = Effects(true, listOf(EffectType.FLAME, EffectType.HEART))
		private val DEF_PARTY_START_EFFECTS = Effects(true, listOf(EffectType.SLIME, EffectType.HEART))
		private val DEF_PARTY_COMMAND_EXECUTE_EFFECTS = Effects(true, listOf(EffectType.SMOKE_NORMAL, EffectType.HEART))
		private val DEF_EFFECTS_CONFIG = EffectsConfig(DEF_VOTE_EFFECTS, DEF_PARTY_START_EFFECTS, DEF_PARTY_COMMAND_EXECUTE_EFFECTS)
		private val DEF_SETTINGS_CONFIG = SettingsConfig("en_US", PREFIX)
		
		
		val DEF = ConfigVoteParty(DEF_SETTINGS_CONFIG, DEF_EFFECTS_CONFIG, DEF_CRATE_CONFIG, DEF_VOTE_CONFIG, DEF_PARTY_CONFIG)
		
	}
	
}