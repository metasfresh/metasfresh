package de.metas.payment.esr.dataimporter.impl.camt54;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Test;

import de.metas.payment.esr.dataimporter.ESRStatement;
import de.metas.payment.esr.dataimporter.ESRTransaction;
import de.metas.payment.esr.model.I_ESR_Import;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class ESRDataImporterCamt54Tests
{
	private final Condition<? super ESRTransaction> trxHasNoErrors = new Condition<>(
			t -> t.getErrorMsgs().isEmpty(),
			"ESRTransaction has no error messages");

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testWithSampleFile()
	{
		final InputStream inputStream = getClass().getResourceAsStream("/camt054.xml");
		assertThat(inputStream).isNotNull();

		final ESRStatement importData = new ESRDataImporterCamt54(newInstance(I_ESR_Import.class), inputStream).importData();

		// no errors
		assertThat(importData.getErrorMsgs()).isEmpty();
		assertThat(importData.getTransactions())
				.allMatch(t -> t.getErrorMsgs().isEmpty());

		assertThat(importData.getCtrlAmount())
				.isEqualByComparingTo("1000");

		assertThat(importData.getCtrlQty()).as("CtrlQty")
				.isEqualByComparingTo("10");
	}

	@Test
	public void testMissingCtrlQty()
	{
		final InputStream inputStream = getClass().getResourceAsStream("/camt54_no_Btch.xml");
		assertThat(inputStream).isNotNull();

		final ESRStatement importData = new ESRDataImporterCamt54(newInstance(I_ESR_Import.class), inputStream).importData();

		assertThat(importData.getTransactions()).hasSize(10);

		// no errors
		assertThat(importData.getErrorMsgs()).isEmpty();

		assertThat(importData.getTransactions())
				.as("MissingCtrlQty is no error")
				.are(trxHasNoErrors);

		assertThat(importData.getCtrlAmount()).isEqualByComparingTo("1000");
		assertThat(importData.getCtrlQty()).isNull();

	}

	@Test
	public void testAmbigousEsrReference()
	{
		final ESRStatement importData = performWithMissingOrAmbigousEsrReference("/camt54_one_ESR_reference_ambigous.xml");

		// all have a reference set
		assertThat(importData.getTransactions())
				.as("All ten transactions have a  non-empty reference set")
				.allSatisfy(t -> {
					assertThat(t.getEsrReferenceNumber()).isNotEmpty();
				});

		assertThat(importData.getTransactions())
				.filteredOn(t -> t.getEsrReferenceNumber().equals("000000000002016030001593614"))
				.hasSize(1) // guard
				.allSatisfy(t -> {
					assertThat(t.getErrorMsgs()).hasSize(1);
					assertThat(t.getErrorMsgs().get(0)).isEqualTo(ESRDataImporterCamt54.MSG_AMBIGOUS_REFERENCE);
				});

		assertThat(importData.getCtrlAmount()).isEqualByComparingTo("1000");
	}

	@Test
	public void testMissingEsrReference()
	{
		final ESRStatement importData = performWithMissingOrAmbigousEsrReference("/camt54_one_ESR_reference_missing.xml");

		assertThat(importData.getTransactions())
				.as("those nine transactions that have a reference set, also have a non-empty string")
				.filteredOn(t -> t.getEsrReferenceNumber() != null)
				.hasSize(9)
				.allSatisfy(t -> {
					assertThat(t.getEsrReferenceNumber()).isNotEmpty();
				});

		assertThat(importData.getTransactions())
				.filteredOn(t -> t.getEsrReferenceNumber() == null)
				.hasSize(1) // guard
				.allSatisfy(t -> {
					assertThat(t.getErrorMsgs()).hasSize(1);
					assertThat(t.getErrorMsgs().get(0)).isEqualTo(ESRDataImporterCamt54.MSG_MISSING_ESR_REFERENCE);
				});

		assertThat(importData.getCtrlAmount()).isEqualByComparingTo("1000");
	}

	private ESRStatement performWithMissingOrAmbigousEsrReference(final String xmlResourceName)
	{
		final InputStream inputStream = getClass().getResourceAsStream(xmlResourceName);
		assertThat(inputStream).as("Unable to load %s", xmlResourceName).isNotNull();

		final ESRStatement importData = new ESRDataImporterCamt54(newInstance(I_ESR_Import.class), inputStream).importData();

		assertThat(importData.getCtrlQty()).isEqualByComparingTo("10");
		assertThat(importData.getTransactions()).hasSize(10);

		// no errors on "header" level
		assertThat(importData.getErrorMsgs()).isEmpty();

		// only one transaction has that little problem
		assertThat(importData.getTransactions())
				.areExactly(9, trxHasNoErrors);

		return importData;
	}

	// @Test
	public void otherTest()
	{
		final InputStream inputStream = getClass().getResourceAsStream("/adempiere_7924089069896117994_017-Binningen_1707_camt.054-ESR-ASR_P_CH3909000000400099806_303041189_0_2017072823282302.xml");
		assertThat(inputStream).isNotNull();

		final ESRStatement importData = new ESRDataImporterCamt54(newInstance(I_ESR_Import.class), inputStream).importData();
		assertThat(importData.getTransactions().size())
				.isEqualTo(13);

		assertThat(importData.getCtrlQty())
				.isEqualByComparingTo(new BigDecimal("13"));

		assertThat(importData.getCtrlAmount())
				.isEqualByComparingTo(new BigDecimal("540"));

	}
}
