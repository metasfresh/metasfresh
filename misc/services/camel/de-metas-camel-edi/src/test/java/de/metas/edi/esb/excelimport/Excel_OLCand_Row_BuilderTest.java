/*
 * #%L
 * de-metas-camel-edi
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.edi.esb.excelimport;

import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Excel_OLCand_Row_BuilderTest
{

	@Test
	public void testZero()
	{
		final String zero = "0";
		final BigDecimal result = Excel_OLCand_Row_Builder.extractBigDecimal(zero);

		assertEquals(BigDecimal.ZERO, result);
	}

	@Test
	public void testBigDecimal_Comma()
	{
		final String numberWithDelimiter = "1,2";
		final BigDecimal result = Excel_OLCand_Row_Builder.extractBigDecimal(numberWithDelimiter);

		assertEquals(new BigDecimal("1.2"), result);

	}

	@Test
	public void testBigDecimal_Dot()
	{
		final String numberWithDelimiter = "1.2";
		final BigDecimal result = Excel_OLCand_Row_Builder.extractBigDecimal(numberWithDelimiter);

		assertEquals(new BigDecimal("1.2"), result);

	}

	@Test
	public void testBigDecimal_DotAndComma()
	{
		final String numberWithDelimiter = "1.200,65";
		final BigDecimal result = Excel_OLCand_Row_Builder.extractBigDecimal(numberWithDelimiter);

		assertEquals(new BigDecimal("1200.65"), result);

	}

	@Test
	public void testBigDecimal_CommaAndDot()
	{
		final String numberWithDelimiter = "1,200.65";
		final BigDecimal result = Excel_OLCand_Row_Builder.extractBigDecimal(numberWithDelimiter);

		assertEquals(new BigDecimal("1200.65"), result);
	}

	@Test
	public void testInteger()
	{
		final String number = "123";
		final BigDecimal result = Excel_OLCand_Row_Builder.extractBigDecimal(number);

		assertEquals(new BigDecimal("123"), result);
	}

	@Test
	public void testInteger_Dot4()
	{
		final String numberWithDelimiter = "4.000000000";
		final BigDecimal result = Excel_OLCand_Row_Builder.extractBigDecimal(numberWithDelimiter);

		assertEquals(new BigDecimal("4"), result);
	}

	@Test
	public void testInteger_Comma4()
	{
		final String numberWithDelimiter = "4,000000000";
		final BigDecimal result = Excel_OLCand_Row_Builder.extractBigDecimal(numberWithDelimiter);

		assertEquals(new BigDecimal("4"), result);
	}

	@Test
	public void testInteger_Dot4000()
	{
		final String numberWithDelimiter = "4000.000000";
		final BigDecimal result = Excel_OLCand_Row_Builder.extractBigDecimal(numberWithDelimiter);

		assertEquals(new BigDecimal("4000"), result);
	}

	@Test
	public void testInteger_Comma4000()
	{
		final String numberWithDelimiter = "4000,000000";
		final BigDecimal result = Excel_OLCand_Row_Builder.extractBigDecimal(numberWithDelimiter);

		assertEquals(new BigDecimal("4000"), result);
	}

	/* This will return 1.234 and not 1234! */
	@Test
	public void testInteger_ShadyComma()
	{
		final String numberWithDelimiter = "1,234";
		final BigDecimal result = Excel_OLCand_Row_Builder.extractBigDecimal(numberWithDelimiter);

		assertEquals(new BigDecimal("1.234"), result);
	}

	/* This will return 1.234 and not 1234! */
	@Test
	public void testInteger_ShadyDot()
	{
		final String numberWithDelimiter = "1.234";
		final BigDecimal result = Excel_OLCand_Row_Builder.extractBigDecimal(numberWithDelimiter);

		assertEquals(new BigDecimal("1.234"), result);
	}
}
