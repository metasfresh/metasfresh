package de.metas.payment.esr.dataimporter.impl.camt54;

import de.metas.payment.camt054_001_02.BatchInformation2;
import de.metas.payment.camt054_001_02.EntryDetails1;
import de.metas.payment.camt054_001_02.ReportEntry2;
import de.metas.payment.esr.dataimporter.ESRStatement;
import de.metas.payment.esr.dataimporter.ESRTransaction;
import de.metas.payment.esr.dataimporter.ESRType;
import de.metas.payment.esr.model.I_ESR_ImportFile;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

public class ESRDataImporterCamt54V02Tests
{
	private final Condition<? super ESRTransaction> trxHasNoErrors = new Condition<>(
			t -> t.getErrorMsgs().isEmpty(),
			"ESRTransaction has no error messages");

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testWithSampleFile()
	{
		final InputStream inputStream = getClass().getResourceAsStream("/camt54_v02.xml");
		assertThat(inputStream).isNotNull();

		final ESRStatement importData = new ESRDataImporterCamt54(newInstance(I_ESR_ImportFile.class), inputStream).importData();

		// no errors
		assertThat(importData.getErrorMsgs()).isEmpty();
		assertThat(importData.getTransactions())
				.allMatch(t -> t.getErrorMsgs().isEmpty());

		assertThat(importData.getCtrlAmount())
				.isEqualByComparingTo("1000");

		assertThat(importData.getCtrlQty()).as("CtrlQty")
				.isEqualByComparingTo("10");
	}

	/**
	 * Verifies that is there is one input file without any "Batch" tag, then CtrlQty is null
	 */
	@Test
	public void testMissingCtrlQty()
	{
		final InputStream inputStream = getClass().getResourceAsStream("/camt54_no_Btch_v02.xml");
		assertThat(inputStream).isNotNull();

		final ESRStatement importData = new ESRDataImporterCamt54(newInstance(I_ESR_ImportFile.class), inputStream).importData();

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
	public void testMissingCtrlQtyUnit()
	{
		final ESRStatement.ESRStatementBuilder stmtBuilder = ESRStatement.builder();

		final EntryDetails1 emptyNtryDetails = new EntryDetails1();
		emptyNtryDetails.setBtch(new BatchInformation2());

		final EntryDetails1 filledNtryDetails1 = new EntryDetails1();
		{
			final BatchInformation2 btch1 = new BatchInformation2();
			btch1.setNbOfTxs("2");
			filledNtryDetails1.setBtch(btch1);
		}

		final EntryDetails1 filledNtryDetails2 = new EntryDetails1();
		{
			final BatchInformation2 btch1 = new BatchInformation2();
			btch1.setNbOfTxs("3");
			filledNtryDetails2.setBtch(btch1);
		}

		//
		// now do some testing
		//

		final ESRDataImporterCamt54v02 importer = ESRDataImporterCamt54v02.builder()
				.adLanguage("de_DE")
				.build();

		// only the empty one..
		{
			final ReportEntry2 ntry = new ReportEntry2();
			ntry.getNtryDtls().add(emptyNtryDetails);
			final BigDecimal result = importer.iterateEntryDetails(stmtBuilder, ESRDataImporterCamt54.CTRL_QTY_NOT_YET_SET, ntry);
			assertThat(result).isEqualByComparingTo(ESRDataImporterCamt54.CTRL_QTY_AT_LEAST_ONE_NULL);
		}

		// only the "filled" one..
		{
			final ReportEntry2 ntry = new ReportEntry2();
			ntry.getNtryDtls().add(filledNtryDetails1);
			final BigDecimal result = importer.iterateEntryDetails(stmtBuilder, ESRDataImporterCamt54.CTRL_QTY_NOT_YET_SET, ntry);
			assertThat(result).isEqualByComparingTo("2");
		}

		// first empty, then filled (one ntry)
		{
			final ReportEntry2 ntry = new ReportEntry2();
			ntry.getNtryDtls().add(emptyNtryDetails);
			ntry.getNtryDtls().add(filledNtryDetails1);
			final BigDecimal result = importer.iterateEntryDetails(stmtBuilder, ESRDataImporterCamt54.CTRL_QTY_NOT_YET_SET, ntry);
			assertThat(result).isEqualByComparingTo("2");
		}

		// first empty, then filled (two ntrys)
		{
			final ReportEntry2 ntry1 = new ReportEntry2();
			ntry1.getNtryDtls().add(emptyNtryDetails);
			final BigDecimal resultFrom1stCall = importer.iterateEntryDetails(stmtBuilder, ESRDataImporterCamt54.CTRL_QTY_NOT_YET_SET, ntry1);
			assertThat(resultFrom1stCall).isEqualByComparingTo(ESRDataImporterCamt54.CTRL_QTY_AT_LEAST_ONE_NULL);

			final ReportEntry2 ntry2 = new ReportEntry2();
			ntry2.getNtryDtls().add(filledNtryDetails1);
			final BigDecimal resultFrom2ndCall = importer.iterateEntryDetails(stmtBuilder, resultFrom1stCall, ntry2);
			assertThat(resultFrom2ndCall).isEqualByComparingTo(ESRDataImporterCamt54.CTRL_QTY_AT_LEAST_ONE_NULL);
		}

		// first filled, then empty (one ntry)
		{
			final ReportEntry2 ntry = new ReportEntry2();
			ntry.getNtryDtls().add(filledNtryDetails1);
			ntry.getNtryDtls().add(emptyNtryDetails);
			final BigDecimal result = importer.iterateEntryDetails(stmtBuilder, ESRDataImporterCamt54.CTRL_QTY_NOT_YET_SET, ntry);
			assertThat(result).isEqualByComparingTo(ESRDataImporterCamt54.CTRL_QTY_AT_LEAST_ONE_NULL);
		}

		// first filled, then empty (two ntrys)
		{
			final ReportEntry2 ntry1 = new ReportEntry2();
			ntry1.getNtryDtls().add(filledNtryDetails1);
			final BigDecimal resultFrom1stCall = importer.iterateEntryDetails(stmtBuilder, ESRDataImporterCamt54.CTRL_QTY_NOT_YET_SET, ntry1);
			assertThat(resultFrom1stCall).isEqualByComparingTo("2");

			final ReportEntry2 ntry2 = new ReportEntry2();
			ntry2.getNtryDtls().add(emptyNtryDetails);
			final BigDecimal resultFrom2ndCall = importer.iterateEntryDetails(stmtBuilder, resultFrom1stCall, ntry2);
			assertThat(resultFrom2ndCall).isEqualByComparingTo(ESRDataImporterCamt54.CTRL_QTY_AT_LEAST_ONE_NULL);
		}

		// filled, filled, then empty (three ntrys)
		{
			final ReportEntry2 ntry1 = new ReportEntry2();
			ntry1.getNtryDtls().add(filledNtryDetails1);
			final BigDecimal resultFrom1stCall = importer.iterateEntryDetails(stmtBuilder, ESRDataImporterCamt54.CTRL_QTY_NOT_YET_SET, ntry1);
			assertThat(resultFrom1stCall).isEqualByComparingTo("2");

			final ReportEntry2 ntry2 = new ReportEntry2();
			ntry2.getNtryDtls().add(filledNtryDetails2);
			final BigDecimal resultFrom2ndCall = importer.iterateEntryDetails(stmtBuilder, resultFrom1stCall, ntry2);
			assertThat(resultFrom2ndCall).isEqualByComparingTo("5");

			final ReportEntry2 ntry3 = new ReportEntry2();
			ntry3.getNtryDtls().add(emptyNtryDetails);
			final BigDecimal resultFrom3rdCall = importer.iterateEntryDetails(stmtBuilder, resultFrom2ndCall, ntry3);
			assertThat(resultFrom3rdCall).isEqualByComparingTo(ESRDataImporterCamt54.CTRL_QTY_AT_LEAST_ONE_NULL);
		}
	}

	@Test
	public void testAmbigousEsrReference()
	{
		final ESRStatement importData = performWithMissingOrAmbigousEsrReference("/camt54_one_ESR_reference_ambigous_v02.xml");

		// all have a reference set
		assertThat(importData.getTransactions())
				.as("All ten transactions have a  non-empty reference set")
				.allSatisfy(t -> assertThat(t.getEsrReferenceNumber()).isNotEmpty());

		assertThat(importData.getTransactions())
				.filteredOn(t -> t.getEsrReferenceNumber().equals("000000000002016030001593614"))
				.hasSize(1) // guard
				.allSatisfy(t -> {
					assertThat(t.getErrorMsgs()).hasSize(1);
					assertThat(t.getErrorMsgs().get(0)).isEqualTo(ReferenceStringHelper.MSG_AMBIGOUS_REFERENCE.toAD_Message());
				});

		assertThat(importData.getCtrlAmount()).isEqualByComparingTo("1000");
	}

	@Test
	public void testMissingEsrReference()
	{
		final ESRStatement importData = performWithMissingOrAmbigousEsrReference("/camt54_one_ESR_reference_missing_v02.xml");

		assertThat(importData.getTransactions())
				.as("those nine transactions that have a reference set, also have a non-empty string")
				.filteredOn(t -> t.getEsrReferenceNumber() != null)
				.hasSize(9)
				.allSatisfy(t -> assertThat(t.getEsrReferenceNumber()).isNotEmpty());

		assertThat(importData.getTransactions())
				.filteredOn(t -> t.getEsrReferenceNumber() == null)
				.hasSize(1) // guard
				.allSatisfy(t -> {
					assertThat(t.getErrorMsgs()).hasSize(1);
					assertThat(t.getErrorMsgs().get(0)).isEqualTo(ReferenceStringHelper.MSG_MISSING_ESR_REFERENCE.toAD_Message());
				});

		assertThat(importData.getCtrlAmount()).isEqualByComparingTo("1000");
	}

	private ESRStatement performWithMissingOrAmbigousEsrReference(final String xmlResourceName)
	{
		final InputStream inputStream = getClass().getResourceAsStream(xmlResourceName);
		assertThat(inputStream).as("Unable to load %s", xmlResourceName).isNotNull();

		final ESRStatement importData = new ESRDataImporterCamt54(newInstance(I_ESR_ImportFile.class), inputStream).importData();

		assertThat(importData.getCtrlQty()).isEqualByComparingTo("10");
		assertThat(importData.getTransactions()).hasSize(10);

		// no errors on "header" level
		assertThat(importData.getErrorMsgs()).isEmpty();

		// only one transaction has that little problem
		assertThat(importData.getTransactions())
				.areExactly(9, trxHasNoErrors);

		return importData;
	}

	/**
	 * Verifies that are 2 QRR  transaction lines
	 */
	@Test
	public void testQRRType()
	{
		final InputStream inputStream = getClass().getResourceAsStream("/camt54_v02_QRR.xml");
		assertThat(inputStream).isNotNull();

		final ESRStatement importData = new ESRDataImporterCamt54(newInstance(I_ESR_ImportFile.class), inputStream).importData();

		assertThat(importData.getTransactions())
				.filteredOn(t -> t.getType().equals(ESRType.TYPE_QRR))
				.hasSize(3)
				.allSatisfy(t -> assertThat(t.getEsrParticipantNo()).isNotEmpty());
	}

	/**
	 * Verifies that we can't mix ESR and QRR in the same entry
	 */
	@Test
	public void testQRR_IBAN()
	{

		final InputStream inputStream = getClass().getResourceAsStream("/camt54_v02_QRR_MultipleTrxTypes.xml");
		assertThat(inputStream).isNotNull();

		assertThatThrownBy(() -> new ESRDataImporterCamt54(newInstance(I_ESR_ImportFile.class), inputStream).importData())
				.isInstanceOf(AdempiereException.class)
				.hasMessage(ESRDataImporterCamt54.MSG_MULTIPLE_TRANSACTIONS_TYPES.toAD_Message());
	}
}