package de.metas.shipper.gateway.derkurier.misc;

import org.junit.Assert;
import org.junit.Test;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ParcelNumberGeneratorTest
{

	@Test
	public void computeAndAppendCheckDigit_OddLength()
	{
		final String parcelNumber = "1005255687";
		final String expectedParcelNumberWithCheckDigit = "10052556879";

		final ParcelNumberGenerator parcelNumberGenerator = new ParcelNumberGenerator(parcelNumber);
		String parcelNumberWithCheckDigit = parcelNumberGenerator.computeAndAppendCheckDigit(parcelNumber);

		Assert.assertTrue(expectedParcelNumberWithCheckDigit.equals(parcelNumberWithCheckDigit));

	}

	@Test
	public void computeAndAppendCheckDigit_EvanLength()
	{
		final String parcelNumber = "10052556876";
		final String expectedParcelNumberWithCheckDigit = "100525568761";

		final ParcelNumberGenerator parcelNumberGenerator = new ParcelNumberGenerator(parcelNumber);
		String parcelNumberWithCheckDigit = parcelNumberGenerator.computeAndAppendCheckDigit(parcelNumber);

		Assert.assertTrue(expectedParcelNumberWithCheckDigit.equals(parcelNumberWithCheckDigit));

	}

	@Test(expected = RuntimeException.class)
	public void computeAndAppendCheckDigit_WhiteSpaces()
	{
		final String parcelNumber = "1005 255 687 6";

		final ParcelNumberGenerator parcelNumberGenerator = new ParcelNumberGenerator(parcelNumber);
		parcelNumberGenerator.computeAndAppendCheckDigit(parcelNumber);

	}

	@Test(expected = RuntimeException.class)
	public void computeAndAppendCheckDigit_AlphaChars()
	{
		final String parcelNumber = "1005A255B687-6";

		final ParcelNumberGenerator parcelNumberGenerator = new ParcelNumberGenerator(parcelNumber);
		parcelNumberGenerator.computeAndAppendCheckDigit(parcelNumber);

	}

	@Test(expected = RuntimeException.class)
	public void computeAndAppendCheckDigit_Null()
	{
		final String parcelNumber = null;

		final ParcelNumberGenerator parcelNumberGenerator = new ParcelNumberGenerator(parcelNumber);
		parcelNumberGenerator.computeAndAppendCheckDigit(parcelNumber);

	}

	@Test(expected = RuntimeException.class)
	public void computeAndAppendCheckDigit_Empty()
	{
		final String parcelNumber = "";

		final ParcelNumberGenerator parcelNumberGenerator = new ParcelNumberGenerator(parcelNumber);
		parcelNumberGenerator.computeAndAppendCheckDigit(parcelNumber);

	}
}
