package de.metas.vertical.pharma;

import org.adempiere.exceptions.AdempiereException;

import de.metas.util.StringUtils;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * I took the information about PZN code from here
 * http://www.pruefziffernberechnung.de/P/PZN.shtml
 *
 */
public class PharmaModulo11Validator
{
	private static final int PZN_MODULO11 = 11;

	private static final int PZN7_Length = 7;
	private static final int PZN7_InitialMultiplier = 2;

	private static final int PZN8_Length = 8;
	private static final int PZN8_InitialMultiplier = 1;

	public static boolean isValid(final String pzn)
	{
		if (pzn.length() != PZN7_Length && pzn.length() != PZN8_Length)
		{
			return false;
		}

		if (!pzn.equals(StringUtils.getDigits(pzn)))
		{
			return false;
		}

		final int codeLimit = pzn.length() - 1;

		final int checkDigit = Character.getNumericValue(pzn.charAt(codeLimit));
		final String code = pzn.substring(0, codeLimit);

		final int initialMultiplier;

		if (pzn.length() == PZN7_Length)
		{
			initialMultiplier = PZN7_InitialMultiplier;
		}
		else
		{
			initialMultiplier = PZN8_InitialMultiplier;
		}

		final int codeModuloResult = calculateModulo11(code, initialMultiplier);

		return codeModuloResult == checkDigit;
	}

	private static int calculateModulo11(final String code, final int initialMultiplier)
	{
		int total = 0;
		int multiplier = initialMultiplier;

		final int codeLength = code.length();

		for (int i = 0; i < codeLength; i++)
		{
			int value = Character.getNumericValue(code.charAt(i));
			total += value * multiplier;

			multiplier++;
		}
		if (total == 0)
		{
			throw new AdempiereException("Invalid code, sum is zero");
		}

		return total % PZN_MODULO11;
	}

}
