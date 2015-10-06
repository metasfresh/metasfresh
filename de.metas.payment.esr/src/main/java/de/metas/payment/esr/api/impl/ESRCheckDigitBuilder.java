package de.metas.payment.esr.api.impl;

/*
 * #%L
 * de.metas.payment.esr
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


import org.adempiere.util.Check;

import de.metas.payment.esr.ESRConstants;

/**
 * Contains a method for calculating the check digit.
 * 
 * @param text
 * @return checksum digit (between 0 and 9)
 * @see <a href="http://w2.syronex.com/jmr/programming/mod11chk">ISBNs & The Modulo 11 Checksum Algorithm</a>
 */
// metas-ts: moved to a dedicated class, because it's very different from the rest of ESRImportBL and its tests fill a test class of its own.
public final class ESRCheckDigitBuilder
{
	public int calculateESRCheckDigit(final String text)
	{
		int carry = 0;
		try
		{

			for (int i = 0; i < text.length(); i++)
			{
				final int intAtIdx = Integer.parseInt(Character.toString(text.charAt(i)));
				carry = ESRConstants.CHECK_String[(intAtIdx + carry) % 10];
				// System.out.println("Idx=" + i + "\tNumber[i]=" + intAtIdx + "\tCarry=" + carry);
			}

		}
		catch (NumberFormatException e)
		{
			// NumberFormatException can be thrown from Integer.parseInt
			
			Check.errorIf(true, "Failed to compute ESR check digit; invalid ESR-String \"{0}\"", text); // will throw an exception

		}

		final int result = (10 - carry) % 10;
		// System.out.println("Result=" + result);
		return result;
	}
}
