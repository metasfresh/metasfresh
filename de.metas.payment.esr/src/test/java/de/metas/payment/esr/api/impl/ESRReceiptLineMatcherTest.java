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


import static org.hamcrest.Matchers.comparesEqualTo;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.ESRTestBase;
import de.metas.payment.esr.exception.ESRParserException;
import de.metas.payment.esr.model.I_ESR_ImportLine;

public class ESRReceiptLineMatcherTest extends ESRTestBase
{
	private ESRLineMatcher matcher;

	@Override
	public void init()
	{
		matcher = new ESRLineMatcher();
	}

	@Test
	public void test_controlLine()
	{
		final String esrImportLineText = "999010599310999999999999999999999999999000000092000000000000025130118000000000000000000                                       ";

		final I_ESR_ImportLine esrImportLine = createImportLine(esrImportLineText);

		matcher.match(esrImportLine);

		Assert.assertThat("Invalid Control Amt",
				esrImportLine.getESR_Import().getESR_Control_Amount(),
				comparesEqualTo(new BigDecimal("920")));
		Assert.assertThat("Invalid ESR_Control_Trx_Qty",
				esrImportLine.getESR_Import().getESR_Control_Trx_Qty(),
				comparesEqualTo(new BigDecimal("25")));

		Assert.assertEquals("Invalid TrxType", ESRConstants.ESRTRXTYPE_Receipt, esrImportLine.getESRTrxType());
		Assert.assertEquals("Invalid IsValid", true, esrImportLine.isValid());
		Assert.assertEquals("Invalid ErrorMsg", null, esrImportLine.getErrorMsg());
		Assert.assertEquals("Invalid Processed", true, esrImportLine.isProcessed());
	}

	@Test(expected = ESRParserException.class)
	public void test_invalidLength()
	{
		final String esrImportLineText = "9990105993109999999999999999999999999990000000920000000000000";
		final I_ESR_ImportLine esrImportLine = createImportLine(esrImportLineText);

		matcher.match(esrImportLine);
	}

	@Test
	public void test_invalidControlAmt()
	{
		final String esrImportLineText = "999010599310999999999999999999999999999000000092x00000000000025130118000000000000000000";
		final I_ESR_ImportLine esrImportLine = createImportLine(esrImportLineText);

		matcher.match(esrImportLine);

		Assert.assertEquals("Invalid IsValid", false, esrImportLine.isValid());
	}

	@Test
	public void test_invalidControlQty()
	{
		final String esrImportLineText = "99901059931099999999999999999999999999900000009200000000000002x130118000000000000000000";
		final I_ESR_ImportLine esrImportLine = createImportLine(esrImportLineText);

		matcher.match(esrImportLine);

		Assert.assertEquals("Invalid IsValid", false, esrImportLine.isValid());
	}

}
