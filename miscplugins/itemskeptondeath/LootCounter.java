package net.runelite.client.plugins.miscplugins.itemskeptondeath;

import net.runelite.client.plugins.Plugin;
import net.runelite.client.ui.overlay.infobox.Counter;
import net.runelite.client.util.QuantityFormatter;

import java.awt.image.BufferedImage;

public class LootCounter extends Counter
{

	public LootCounter(BufferedImage image, Plugin plugin, int count)
	{
		super(image, plugin, count);
	}

	@Override
	public String getText()
	{
		return QuantityFormatter.quantityToRSDecimalStack(getCount());
	}
}
