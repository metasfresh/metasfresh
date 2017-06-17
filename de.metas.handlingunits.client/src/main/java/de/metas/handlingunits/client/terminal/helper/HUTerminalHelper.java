package de.metas.handlingunits.client.terminal.helper;

/*
 * #%L
 * de.metas.handlingunits.client
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.Font;
import java.math.BigDecimal;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.adempiere.form.terminal.KeyLayout;
import de.metas.i18n.IMsgBL;

public final class HUTerminalHelper
{
	private HUTerminalHelper()
	{
		super();
	}

	/**
	 * Generate label for a split-panel key.
	 *
	 * @param name
	 * @param capacity
	 * @return client key label
	 */
	public static String createKeyLabel(final String name, final BigDecimal quantity)
	{
		final String capacityMsg = Services.get(IMsgBL.class).getMsg(Env.getCtx(), "HUCapacity");

		final StringBuilder sb = new StringBuilder().append(truncateName(name)).append("\b")
				.append(" (").append(capacityMsg).append(": ").append(quantity).append(")");
		return sb.toString();
	}

	public static String truncateName(final String name)
	{
		return truncateName(name, 18);
	}

	public static String truncateName(final String name, final int length)
	{
		// Truncate length if exceeds button size.
		// The button size is manually set in de.metas.handlingunits.client.terminal.split.view.HUSplitPanel.HUSplitPanel.

		return null != name && name.length() < length ? name : name.substring(0, length - 3) + "...";
	}

	/**
	 * Resize font resized font to be used in HU Key Layouts
	 *
	 * @param font
	 * @return resized font (decreased in size)
	 */
	public static Font getDecreasedFontSize(final Font font)
	{
		final int newFontSize = font.getSize() - 2;
		Check.assume(newFontSize > 0, "Resized font size greater than 0");

		final Font resizedFont = font.deriveFont(font.getStyle(), newFontSize);
		return resizedFont;
	}

	/**
	 * Decrease key layout font using {@link #getDecreasedFontSize(Font)} of the current font
	 *
	 * @param keyLayout
	 */
	public static void decreaseKeyLayoutFont(final KeyLayout keyLayout)
	{
		final Font initialDefaultFont = keyLayout.getDefaultFont();
		final Font resizedFont = HUTerminalHelper.getDecreasedFontSize(initialDefaultFont);
		keyLayout.setDefaultFont(resizedFont);
	}
}
