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

public class Excel_OLCand_Row_BuilderTest extends CamelTestSupport
{
	@Test
	public void testBigDecimal_Comma()
	{
		final String numberWithComma = "1,2";
		final BigDecimal result = Excel_OLCand_Row_Builder.extractBigDecimal(numberWithComma);

		assertEquals(new BigDecimal("1.2"), result);

	}

	@Test
	public void testBigDecimal_Dot()
	{
		final String numberWithComma = "1.2";
		final BigDecimal result = Excel_OLCand_Row_Builder.extractBigDecimal(numberWithComma);

		assertEquals(new BigDecimal("1.2"), result);

	}

	public void testBigDecimal_DotAndComma()
	{
		final String numberWithComma = "1.200,65";
		final BigDecimal result = Excel_OLCand_Row_Builder.extractBigDecimal(numberWithComma);

		assertEquals(new BigDecimal("1200.65"), result);

	}

	public void testBigDecimal_CommaAndDot()
	{
		final String numberWithComma = "1,200.65";
		final BigDecimal result = Excel_OLCand_Row_Builder.extractBigDecimal(numberWithComma);

		assertEquals(new BigDecimal("1200.65"), result);
	}

}
