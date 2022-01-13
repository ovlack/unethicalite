package dev.hoot.api.movement.pathfinder;

import dev.hoot.api.entities.Players;
import dev.hoot.api.entities.TileObjects;
import dev.hoot.api.game.Game;
import dev.hoot.api.game.GameThread;
import dev.hoot.api.game.Worlds;
import dev.hoot.api.input.Keyboard;
import dev.hoot.api.items.Equipment;
import dev.hoot.api.items.Inventory;
import dev.hoot.api.magic.Magic;
import dev.hoot.api.widgets.Dialog;
import dev.hoot.api.widgets.Widgets;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.GameState;
import net.runelite.api.Item;
import net.runelite.api.ObjectID;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.unethicalite.UnethicalConfig;

import javax.inject.Inject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class TeleportLoader
{
//	private static final int BUILD_DELAY_SECONDS = 5;
//	private static Instant lastBuild = Instant.now().minusSeconds(6);
//	private static List<Teleport> LAST_TELEPORT_LIST = Collections.emptyList();
	private static final int[] RING_OF_DUELING = new int[]{2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566};
	private static final int[] GAMES_NECKLACE = new int[]{3853, 3863, 3855, 3857, 3859, 3861, 3863, 3865, 3867};
	private static final int[] COMBAT_BRACELET = new int[]{11118, 11972, 11974, 11120, 11122, 11124};
	private static final int[] RING_OF_WEALTH = new int[]{11980, 11982, 11984, 11986, 11988};
	private static final int[] AMULET_OF_GLORY = new int[]{1712, 1706, 1708, 1710, 11976, 11978};
	private static final int[] NECKLACE_OF_PASSAGE = new int[]{21146, 21149, 21151, 21153, 21155};
	private static final int[] BURNING_AMULET = new int[]{21166, 21171, 21173, 21175, 21167};
	private static final int[] XERICS_TALISMAN = new int[]{13393};
	private static final int[] SLAYER_RING = new int[]{11866, 11867, 11868, 11869, 11870, 11871, 11872, 11873, 21268};
	private static final int[] DIGSITE_PENDANT = new int[]{11190, 11191, 11192, 11193, 11194};
	private static final int[] DRAKANS_MEDALLION = new int[]{22400};
	private static final int[] SKILLS_NECKLACE = new int[]{11105, 11111, 11109, 11107, 11970, 11968};

	@Inject
	private static ConfigManager configManager;

	public static List<Teleport> buildTeleports()
	{
//		if (lastBuild.plusSeconds(BUILD_DELAY_SECONDS).isAfter(Instant.now()))
//		{
//			return LAST_TELEPORT_LIST;
//		}

		UnethicalConfig config = configManager.getConfig(UnethicalConfig.class);

//		lastBuild = Instant.now();

		List<Teleport> teleports = new ArrayList<>();
		if (Worlds.inMembersWorld())
		{
			if (Game.getWildyLevel() <= 20)
			{
				if (ringOfDueling())
				{
					teleports.add(new Teleport(new WorldPoint(3315, 3235, 0), 6,
							() -> jewelryTeleport("Duel Arena", RING_OF_DUELING)));
					teleports.add(new Teleport(new WorldPoint(2440, 3090, 0), 2,
							() -> jewelryTeleport("Castle Wars", RING_OF_DUELING)));
					teleports.add(new Teleport(new WorldPoint(3151, 3635, 0), 2,
							() -> jewelryTeleport("Ferox Enclave", RING_OF_DUELING)));
				}

				if (gamesNecklace())
				{
					teleports.add(new Teleport(new WorldPoint(2898, 3553, 0), 2,
							() -> jewelryTeleport("Burthorpe", GAMES_NECKLACE)));
					teleports.add(new Teleport(new WorldPoint(2520, 3571, 0), 6,
							() -> jewelryTeleport("Barbarian Outpost", GAMES_NECKLACE)));
					teleports.add(new Teleport(new WorldPoint(2964, 4382, 2), 2,
							() -> jewelryTeleport("Corporeal Beast", GAMES_NECKLACE)));
					teleports.add(new Teleport(new WorldPoint(3244, 9501, 2), 2,
							() -> jewelryTeleport("Tears of Guthix", GAMES_NECKLACE)));
					teleports.add(new Teleport(new WorldPoint(1624, 3938, 0), 1,
							() -> jewelryTeleport("Wintertodt Camp", GAMES_NECKLACE)));
				}

				if (xericsTalisman())
				{
					teleports.add(new Teleport(new WorldPoint(1576, 3530, 0), 6,
							() -> jewelryTeleport("Xeric's Lookout", XERICS_TALISMAN)));
					teleports.add(new Teleport(new WorldPoint(1752, 3566, 0), 6,
							() -> jewelryTeleport("Xeric's Glade", XERICS_TALISMAN)));
					teleports.add(new Teleport(new WorldPoint(1504, 3817, 0), 6,
							() -> jewelryTeleport("Xeric's Inferno", XERICS_TALISMAN)));
					teleports.add(new Teleport(new WorldPoint(1640, 3674, 0), 6,
							() -> jewelryTeleport("Xeric's Heart", XERICS_TALISMAN)));
					//                teleports.add(new Teleport(new Position(1662, 3505, 0), 6, () -> jewelleryContainerAction(skillsNecklace(), "Xeric's Honour")));
				}

				if (slayerRing())
				{
					teleports.add(new Teleport(new WorldPoint(2432, 3423, 0), 2,
							() -> jewelryTeleport("Stronghold Slayer Cave", SLAYER_RING)));
					teleports.add(new Teleport(new WorldPoint(3422, 3537, 0), 2,
							() -> jewelryTeleport("Slayer Tower", SLAYER_RING)));
					teleports.add(new Teleport(new WorldPoint(2802, 10000, 0), 2,
							() -> jewelryTeleport("Fremennik Slayer Dungeon", SLAYER_RING)));
					teleports.add(new Teleport(new WorldPoint(3185, 4601, 0), 2,
							() -> jewelryTeleport("Tarn's Lair", SLAYER_RING)));
					teleports.add(new Teleport(new WorldPoint(2028, 4636, 0), 2,
							() -> jewelryTeleport("Dark Beasts", SLAYER_RING)));
				}

				if (digsitePendant())
				{
					teleports.add(new Teleport(new WorldPoint(3341, 3445, 0), 2,
							() -> jewelryTeleport("Digsite", DIGSITE_PENDANT)));
					teleports.add(new Teleport(new WorldPoint(3549, 10456, 0), 2,
							() -> jewelryTeleport("Lithkren", DIGSITE_PENDANT)));
				}

				if (necklaceOfPassage())
				{
					teleports.add(new Teleport(new WorldPoint(3114, 3179, 0), 2,
							() -> jewelryTeleport("Wizards' Tower", NECKLACE_OF_PASSAGE)));
					teleports.add(new Teleport(new WorldPoint(2430, 3348, 0), 2,
							() -> jewelryTeleport("The Outpost", NECKLACE_OF_PASSAGE)));
					teleports.add(new Teleport(new WorldPoint(3405, 3157, 0), 2,
							() -> jewelryTeleport("Eagles' Eyrie", NECKLACE_OF_PASSAGE)));
				}

				for (TeleportItem tele : TeleportItem.values())
				{
					if (tele.canUse() && tele.getDestination().distanceTo(Players.getLocal().getWorldLocation()) > 20)
					{
						teleports.add(new Teleport(tele.getDestination(), 5, () ->
						{
							Item item = Inventory.getFirst(tele.getItemId());
							if (item != null)
							{
								item.interact(tele.getAction());
							}
						}));
					}
				}
			}

			if (Game.getWildyLevel() <= 30)
			{
				if (combatBracelet())
				{
					teleports.add(new Teleport(new WorldPoint(2882, 3548, 0), 2,
							() -> jewelryTeleport("Warriors' Guild", COMBAT_BRACELET)));
					teleports.add(new Teleport(new WorldPoint(3191, 3367, 0), 2,
							() -> jewelryTeleport("Champions' Guild", COMBAT_BRACELET)));
					teleports.add(new Teleport(new WorldPoint(3052, 3488, 0), 2,
							() -> jewelryTeleport("Monastery", COMBAT_BRACELET)));
					teleports.add(new Teleport(new WorldPoint(2655, 3441, 0), 2,
							() -> jewelryTeleport("Ranging Guild", COMBAT_BRACELET)));
				}

				if (skillsNecklace())
				{
					teleports.add(new Teleport(new WorldPoint(2611, 3390, 0), 6,
							() -> jewelryPopupTeleport("Fishing Guild", SKILLS_NECKLACE)));
					teleports.add(new Teleport(new WorldPoint(3050, 9763, 0), 6,
							() -> jewelryPopupTeleport("Mining Guild", SKILLS_NECKLACE)));
					teleports.add(new Teleport(new WorldPoint(2933, 3295, 0), 6,
							() -> jewelryPopupTeleport("Crafting Guild", SKILLS_NECKLACE)));
					teleports.add(new Teleport(new WorldPoint(3143, 3440, 0), 6,
							() -> jewelryPopupTeleport("Cooking Guild", SKILLS_NECKLACE)));
					teleports.add(new Teleport(new WorldPoint(1662, 3505, 0), 6,
							() -> jewelryPopupTeleport("Woodcutting Guild", SKILLS_NECKLACE)));
					teleports.add(new Teleport(new WorldPoint(1249, 3718, 0), 6,
							() -> jewelryPopupTeleport("Farming Guild", SKILLS_NECKLACE)));
				}

				if (ringOfWealth())
				{
					teleports.add(new Teleport(new WorldPoint(3163, 3478, 0), 2,
							() -> jewelryTeleport("Grand Exchange", RING_OF_WEALTH)));
					teleports.add(new Teleport(new WorldPoint(2996, 3375, 0), 2,
							() -> jewelryTeleport("Falador", RING_OF_WEALTH)));
				}

				if (amuletOfGlory())
				{
					teleports.add(new Teleport(new WorldPoint(3087, 3496, 0), 0,
							() -> jewelryTeleport("Edgeville", AMULET_OF_GLORY)));
					teleports.add(new Teleport(new WorldPoint(2918, 3176, 0), 0,
							() -> jewelryTeleport("Karamja", AMULET_OF_GLORY)));
					teleports.add(new Teleport(new WorldPoint(3105, 3251, 0), 0,
							() -> jewelryTeleport("Draynor Village", AMULET_OF_GLORY)));
					teleports.add(new Teleport(new WorldPoint(3293, 3163, 0), 0,
							() -> jewelryTeleport("Al Kharid", AMULET_OF_GLORY)));
				}
			}
		}

		if (Game.getWildyLevel() <= 20)
		{
			for (TeleportSpell teleportSpell : TeleportSpell.values())
			{
				if (!teleportSpell.canCast())
				{
					continue;
				}
				WorldPoint dest = teleportSpell.getPoint();
				if (teleportSpell == TeleportSpell.HOUSE_TELEPORT)
				{
					if (!config.usePoh())
					{
						continue;
					}
					if (TileObjects.getNearest(ObjectID.PORTAL_4525) != null)
					{
						dest = Players.getLocal().getWorldLocation();
					}
				}

				if (dest.distanceTo(Players.getLocal().getWorldLocation()) > 50)
				{
					teleports.add(new Teleport(dest, 5, () ->
					{
						if (!Players.getLocal().isIdle() || Game.getClient().getGameState() == GameState.LOADING)
						{
							return;
						}

						Magic.cast(teleportSpell.getSpell());
					}));
				}
			}
		}

		return List.copyOf(teleports);
//		return List.copyOf(LAST_TELEPORT_LIST = teleports);
	}

	public static void jewelryTeleport(String target, int... ids)
	{
		Item inv = Inventory.getFirst(ids);

		if (inv != null)
		{
			if (Dialog.isViewingOptions())
			{
				Dialog.chooseOption(target);
				return;
			}

			inv.interact("Rub");
			return;
		}

		Item equipped = Equipment.getFirst(ids);
		if (equipped != null)
		{
			equipped.interact(target);
		}
	}

	public static void jewelryPopupTeleport(String target, int... ids)
	{
		Item inv = Inventory.getFirst(ids);

		if (inv != null)
		{
			Widget baseWidget = Widgets.get(187, 3);
			if (baseWidget != null && GameThread.invokeLater(() -> !baseWidget.isHidden()))
			{
				Widget[] children = baseWidget.getChildren();
				if (children == null)
				{
					return;
				}

				for (int i = 0; i < children.length; i++)
				{
					Widget teleportItem = children[i];
					if (teleportItem.getText().contains(target))
					{
						Keyboard.type((i + 1));
						return;
					}
				}
			}

			inv.interact("Rub");
			return;
		}

		Item equipped = Equipment.getFirst(ids);
		if (equipped != null)
		{
			equipped.interact(target);
		}
	}

	public static boolean ringOfDueling()
	{
		return Inventory.getFirst(RING_OF_DUELING) != null
				|| Equipment.getFirst(RING_OF_DUELING) != null;
	}

	public static boolean gamesNecklace()
	{
		return Inventory.getFirst(GAMES_NECKLACE) != null
				|| Equipment.getFirst(GAMES_NECKLACE) != null;
	}

	public static boolean combatBracelet()
	{
		return Inventory.getFirst(COMBAT_BRACELET) != null
				|| Equipment.getFirst(COMBAT_BRACELET) != null;
	}

	public static boolean skillsNecklace()
	{
		return Inventory.getFirst(SKILLS_NECKLACE) != null
				|| Equipment.getFirst(SKILLS_NECKLACE) != null;
	}

	public static boolean ringOfWealth()
	{
		return Inventory.getFirst(RING_OF_WEALTH) != null
				|| Equipment.getFirst(RING_OF_WEALTH) != null;
	}

	public static boolean amuletOfGlory()
	{
		return Inventory.getFirst(AMULET_OF_GLORY) != null
				|| Equipment.getFirst(AMULET_OF_GLORY) != null;
	}

	public static boolean necklaceOfPassage()
	{
		return Inventory.getFirst(NECKLACE_OF_PASSAGE) != null
				|| Equipment.getFirst(NECKLACE_OF_PASSAGE) != null;
	}

	public static boolean xericsTalisman() {
		return Inventory.getFirst(XERICS_TALISMAN) != null
				|| Equipment.getFirst(XERICS_TALISMAN) != null;
	}

	public static boolean slayerRing() {
		return Inventory.getFirst(SLAYER_RING) != null
				|| Equipment.getFirst(SLAYER_RING) != null;
	}

	public static boolean digsitePendant() {
		return Inventory.getFirst(DIGSITE_PENDANT) != null
				|| Equipment.getFirst(DIGSITE_PENDANT) != null;
	}
}
