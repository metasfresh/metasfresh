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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;

import org.junit.Test;

import de.metas.payment.esr.ESRTestBase;
import de.metas.payment.esr.model.I_ESR_Import;

public class ESRReceiptLineMatcherTest extends ESRTestBase
{
	@Test
	public void test_controlLine()
	{
		// the code under test should be able to deal with the trailing spaces
		final String esrImportLineText = "999010599310999999999999999999999999999000000092000000000000025130118000000000000000000                                       ";

		final I_ESR_Import esrImport = createImport();

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));

		assertThat("Invalid Control Amt",
				esrImport.getESR_Control_Amount(),
				comparesEqualTo(new BigDecimal("920")));
		assertThat("Invalid ESR_Control_Trx_Qty",
				esrImport.getESR_Control_Trx_Qty(),
				comparesEqualTo(new BigDecimal("25")));

		// TODO: there needs to be an error because "missing trx lines"
		// Assert.assertEquals("Invalid TrxType", ESRConstants.ESRTRXTYPE_Receipt, esrImportLine.getESRTrxType());
		// Assert.assertEquals("Invalid IsValid", true, esrImportLine.isValid());
		// assertThat("Invalid ImportErrorMsg", esrImportLine.getImportErrorMsg(), nullValue());
		// assertThat("Invalid MatchErrorMsg", esrImportLine.getMatchErrorMsg(), nullValue());
		// Assert.assertEquals("Invalid Processed", true, esrImportLine.isProcessed());
	}

	@Test
	public void test_invalidLength()
	{
		final String esrImportLineText = "9990105993109999999999999999999999999990000000920000000000000";
		final I_ESR_Import esrImport = createImport();

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));

		assertThat(esrImport.isValid(), is(false));
		assertThat(esrImport.getDescription(), containsString("ESR_Wrong_Ctrl_Line_Length_[61]"));
	}

	@Test
	public void test_invalidControlAmt()
	{
		final String esrImportLineText = "999010599310999999999999999999999999999000000092x00000000000025130118000000000000000000";
		final I_ESR_Import esrImport = createImport();

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));

		assertThat("Invalid IsValid", esrImport.isValid(), is(false));
	}

	@Test
	public void test_invalidControlQty()
	{
		final String esrImportLineText = "99901059931099999999999999999999999999900000009200000000000002x130118000000000000000000";
		final I_ESR_Import esrImport = createImport();

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));

		assertThat("Invalid IsValid", esrImport.isValid(), is(false));
	}

}
