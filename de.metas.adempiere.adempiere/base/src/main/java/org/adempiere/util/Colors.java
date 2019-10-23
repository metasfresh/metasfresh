/**
 *
 */
package org.adempiere.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.awt.Color;

/**
 * Tools to convert colors from different formats
 * @author tsa
 *
 */
public class Colors
{
	/**
	 * Get {@link Color} from an html hex color
	 *
	 * @param htmlColor html color (e.g. #aa00cc, aa00cc, aa)
	 * @return null if the given string is empty or can'T be parsed into a color.
	 */
	public static Color toColor(String htmlColor)
	{
		if (htmlColor == null || htmlColor.length() == 0)
			return null;

		String hex = htmlColor;
		if (hex.startsWith("#"))
			hex = hex.substring(1);
		if (hex.length() < 6)
		{
			StringBuffer sb = new StringBuffer(hex);
			while(sb.length() < 6)
				sb.append("0");
			hex = sb.toString();
		}

		final int rgb;
		try
		{
			rgb = Integer.parseInt(hex, 16);
		}
		catch (NumberFormatException e)
		{
			return null;
		}

		return new Color(rgb);
	}

	public static String toHtmlColor(Color color)
	{
		return "#" + Integer.toHexString(color.getRGB() | 0xFF000000).substring(2);
	}
}
