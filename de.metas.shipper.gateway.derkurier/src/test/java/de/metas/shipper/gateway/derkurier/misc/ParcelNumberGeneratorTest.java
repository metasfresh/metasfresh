package de.metas.shipper.gateway.derkurier.misc;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Sequence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void computeAndAppendCheckDigit_OddLength()
	{
		final String parcelNumber = "1005255687";
		final String expectedParcelNumberWithCheckDigit = "10052556879";

		final ParcelNumberGenerator parcelNumberGenerator = new ParcelNumberGenerator();
		String parcelNumberWithCheckDigit = parcelNumberGenerator.computeAndAppendCheckDigit(parcelNumber);

		Assert.assertTrue(expectedParcelNumberWithCheckDigit.equals(parcelNumberWithCheckDigit));
	}

	@Test
	public void computeAndAppendCheckDigit_EvalLength()
	{
		final String parcelNumber = "10052556876";
		final String expectedParcelNumberWithCheckDigit = "100525568761";

		final ParcelNumberGenerator parcelNumberGenerator = new ParcelNumberGenerator();
		String parcelNumberWithCheckDigit = parcelNumberGenerator.computeAndAppendCheckDigit(parcelNumber);

		assertThat(parcelNumberWithCheckDigit).isEqualTo(expectedParcelNumberWithCheckDigit);
	}

	@Test(expected = RuntimeException.class)
	public void computeAndAppendCheckDigit_WhiteSpaces()
	{
		final String parcelNumber = "1005 255 687 6";

		final ParcelNumberGenerator parcelNumberGenerator = new ParcelNumberGenerator();
		parcelNumberGenerator.computeAndAppendCheckDigit(parcelNumber);
	}

	@Test(expected = RuntimeException.class)
	public void computeAndAppendCheckDigit_AlphaChars()
	{
		final String parcelNumber = "1005A255B687-6";

		final ParcelNumberGenerator parcelNumberGenerator = new ParcelNumberGenerator();
		parcelNumberGenerator.computeAndAppendCheckDigit(parcelNumber);
	}

	@Test(expected = RuntimeException.class)
	public void computeAndAppendCheckDigit_Null()
	{
		final String parcelNumber = null;

		final ParcelNumberGenerator parcelNumberGenerator = new ParcelNumberGenerator();
		parcelNumberGenerator.computeAndAppendCheckDigit(parcelNumber);
	}

	@Test(expected = RuntimeException.class)
	public void computeAndAppendCheckDigit_Empty()
	{
		final String parcelNumber = "";

		final ParcelNumberGenerator parcelNumberGenerator = new ParcelNumberGenerator();
		parcelNumberGenerator.computeAndAppendCheckDigit(parcelNumber);
	}

	@Test
	@Ignore // needs DB access; see DocumentNoBuilder.retrieveAndIncrementSequenceCurrentNext()
	public void retrieveConfigForShipperId()
	{
		final I_AD_Sequence sequenceRecord = newInstance(I_AD_Sequence.class);
		sequenceRecord.setName("mysequencename");
		sequenceRecord.setIsAutoSequence(true);
		sequenceRecord.setCurrentNext(02060010000);
		sequenceRecord.setDecimalPattern("00000000000");
		save(sequenceRecord);

		final ParcelNumberGenerator parcelNumberGenerator = new ParcelNumberGenerator(sequenceRecord.getAD_Sequence_ID());
		final String parcelNumber = parcelNumberGenerator.getNextParcelNumber();
		assertThat(parcelNumber).isNotEmpty();
	}
}
