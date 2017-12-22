package de.metas.dpd.service;

/*
 * #%L
 * de.metas.swat.base
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


/**
 * Implementation of ISO/IES 7064 Mod. 37/36 as required by DPD
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
 */
public class Iso7064
{

	private static final int MOD = 36;

	static int getVal(final char character)
	{

		if (character >= 'A' && character <= 'Z')
		{
			return ((int)character) - 55;
		}
		if (character >= '0' && character <= '9')
		{
			return ((int)character) - 48;
		}

		throw new IllegalArgumentException("char=" + character
				+ "; Allowed: 0-9 and A-Z");
	}

	static char getChar(final int val)
	{

		if (val >= 0 && val <= 9)
		{
			return (char)(val + 48);
		}

		if (val >= 10 && val <= 35)
		{
			return (char)(val + 55);
		}

		throw new IllegalArgumentException("Val=" + val + "; Allowed: 0 - 35");
	}

	public static char compute(final String str)
	{

		final String strToUse = str.replace(" ", "");

		int cd = MOD;

		for (int i = 0; i < strToUse.length(); i++)
		{

			final int val = getVal(strToUse.charAt(i));

			cd = cd + val;

			if (cd > MOD)
			{
				cd = cd - MOD;
			}

			cd *= 2;

			if (cd > MOD)
			{
				cd = cd - MOD - 1;
			}
		}

		cd = MOD + 1 - cd;
		if (cd == MOD)
		{
			cd = 0;
		}
		return getChar(cd);
	}

}
