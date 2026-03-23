package de.metas.payment.esr.dataimporter.impl.camt54.v08;

import de.metas.payment.camt054_001_08.BatchInformation2;
import de.metas.payment.camt054_001_08.EntryDetails9;
import de.metas.payment.camt054_001_08.ReportEntry10;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.dataimporter.ESRStatement;
import de.metas.payment.esr.dataimporter.ESRTransaction;
import de.metas.payment.esr.dataimporter.ESRType;
import de.metas.payment.esr.dataimporter.impl.camt54.ESRDataImporterCamt54;
import de.metas.payment.esr.model.I_ESR_ImportFile;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.*;

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

public class ESRDataImporterCamt54V08Tests
{
	private final Condition<? super ESRTransaction> trxHasNoErrors = new Condition<>(
			t -> t.getErrorMsgs().isEmpty(),
			"ESRTransaction has no error messages");

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	private ESRStatement importXML()
	{
		final InputStream inputStream = getClass().getResourceAsStream("/camt054_v08.xml");
		assertThat(inputStream).as("Unable to load %s", "/camt054_v08.xml").isNotNull();

		return new ESRDataImporterCamt54(newInstance(I_ESR_ImportFile.class), inputStream).importData();
	}

	@Test
	public void testWithSampleFile()
	{
		final ESRStatement importData = importXML();

		// no errors
		assertThat(importData.getErrorMsgs()).isEmpty();
		// only one transaction has that little problem
		assertThat(importData.getTransactions())
				.areExactly(3, trxHasNoErrors);

		assertThat(importData.getCtrlAmount())
				.isEqualByComparingTo("380");
	}

	/**
	 * Verifies that is there is one input file without any "Batch" tag, then CtrlQty is null
	 */
	@Test
	public void testMissingCtrlQty()
	{
		final ESRStatement importData = importXML();

		assertThat(importData.getTransactions()).hasSize(4);

		// no errors
		assertThat(importData.getErrorMsgs()).isEmpty();

		// only one transaction has that little problem
		assertThat(importData.getTransactions())
				.as("MissingCtrlQty is no error")
				.areExactly(3, trxHasNoErrors);

		assertThat(importData.getCtrlAmount()).isEqualByComparingTo("380");
		assertThat(importData.getCtrlQty()).isNull();
	}

	@Test
	public void testMissingCtrlQtyUnit()
	{
		final ESRStatement.ESRStatementBuilder stmtBuilder = ESRStatement.builder();

		final EntryDetails9 emptyNtryDetails = new EntryDetails9();
		emptyNtryDetails.setBtch(new BatchInformation2());

		final EntryDetails9 filledNtryDetails1 = new EntryDetails9();
		{
			final BatchInformation2 btch1 = new BatchInformation2();
			btch1.setNbOfTxs("2");
			filledNtryDetails1.setBtch(btch1);
		}

		final EntryDetails9 filledNtryDetails2 = new EntryDetails9();
		{
			final BatchInformation2 btch1 = new BatchInformation2();
			btch1.setNbOfTxs("3");
			filledNtryDetails2.setBtch(btch1);
		}

		//
		// now do some testing
		//

		final ESRDataImporterCamt54v08 importer = ESRDataImporterCamt54v08.builder()
				.adLanguage("de_DE")
				.build();

		// only the empty one..
		{
			final ReportEntry10 ntry = new ReportEntry10();
			ntry.getNtryDtls().add(emptyNtryDetails);
			final BigDecimal result = importer.iterateEntryDetails(stmtBuilder, ESRConstants.CTRL_QTY_NOT_YET_SET, ntry);
			assertThat(result).isEqualByComparingTo(ESRConstants.CTRL_QTY_AT_LEAST_ONE_NULL);
		}

		// only the "filled" one..
		{
			final ReportEntry10 ntry = new ReportEntry10();
			ntry.getNtryDtls().add(filledNtryDetails1);
			final BigDecimal result = importer.iterateEntryDetails(stmtBuilder, ESRConstants.CTRL_QTY_NOT_YET_SET, ntry);
			assertThat(result).isEqualByComparingTo("2");
		}

		// first empty, then filled (one ntry)
		{
			final ReportEntry10 ntry = new ReportEntry10();
			ntry.getNtryDtls().add(emptyNtryDetails);
			ntry.getNtryDtls().add(filledNtryDetails1);
			final BigDecimal result = importer.iterateEntryDetails(stmtBuilder, ESRConstants.CTRL_QTY_NOT_YET_SET, ntry);
			assertThat(result).isEqualByComparingTo("2");
		}

		// first empty, then filled (two ntrys)
		{
			final ReportEntry10 ntry1 = new ReportEntry10();
			ntry1.getNtryDtls().add(emptyNtryDetails);
			final BigDecimal resultFrom1stCall = importer.iterateEntryDetails(stmtBuilder, ESRConstants.CTRL_QTY_NOT_YET_SET, ntry1);
			assertThat(resultFrom1stCall).isEqualByComparingTo(ESRConstants.CTRL_QTY_AT_LEAST_ONE_NULL);

			final ReportEntry10 ntry2 = new ReportEntry10();
			ntry2.getNtryDtls().add(filledNtryDetails1);
			final BigDecimal resultFrom2ndCall = importer.iterateEntryDetails(stmtBuilder, resultFrom1stCall, ntry2);
			assertThat(resultFrom2ndCall).isEqualByComparingTo(ESRConstants.CTRL_QTY_AT_LEAST_ONE_NULL);
		}

		// first filled, then empty (one ntry)
		{
			final ReportEntry10 ntry = new ReportEntry10();
			ntry.getNtryDtls().add(filledNtryDetails1);
			ntry.getNtryDtls().add(emptyNtryDetails);
			final BigDecimal result = importer.iterateEntryDetails(stmtBuilder, ESRConstants.CTRL_QTY_NOT_YET_SET, ntry);
			assertThat(result).isEqualByComparingTo(ESRConstants.CTRL_QTY_AT_LEAST_ONE_NULL);
		}

		// first filled, then empty (two ntrys)
		{
			final ReportEntry10 ntry1 = new ReportEntry10();
			ntry1.getNtryDtls().add(filledNtryDetails1);
			final BigDecimal resultFrom1stCall = importer.iterateEntryDetails(stmtBuilder, ESRConstants.CTRL_QTY_NOT_YET_SET, ntry1);
			assertThat(resultFrom1stCall).isEqualByComparingTo("2");

			final ReportEntry10 ntry2 = new ReportEntry10();
			ntry2.getNtryDtls().add(emptyNtryDetails);
			final BigDecimal resultFrom2ndCall = importer.iterateEntryDetails(stmtBuilder, resultFrom1stCall, ntry2);
			assertThat(resultFrom2ndCall).isEqualByComparingTo(ESRConstants.CTRL_QTY_AT_LEAST_ONE_NULL);
		}

		// filled, filled, then empty (three ntrys)
		{
			final ReportEntry10 ntry1 = new ReportEntry10();
			ntry1.getNtryDtls().add(filledNtryDetails1);
			final BigDecimal resultFrom1stCall = importer.iterateEntryDetails(stmtBuilder, ESRConstants.CTRL_QTY_NOT_YET_SET, ntry1);
			assertThat(resultFrom1stCall).isEqualByComparingTo("2");

			final ReportEntry10 ntry2 = new ReportEntry10();
			ntry2.getNtryDtls().add(filledNtryDetails2);
			final BigDecimal resultFrom2ndCall = importer.iterateEntryDetails(stmtBuilder, resultFrom1stCall, ntry2);
			assertThat(resultFrom2ndCall).isEqualByComparingTo("5");

			final ReportEntry10 ntry3 = new ReportEntry10();
			ntry3.getNtryDtls().add(emptyNtryDetails);
			final BigDecimal resultFrom3rdCall = importer.iterateEntryDetails(stmtBuilder, resultFrom2ndCall, ntry3);
			assertThat(resultFrom3rdCall).isEqualByComparingTo(ESRConstants.CTRL_QTY_AT_LEAST_ONE_NULL);
		}
	}

	@Test
	public void testAmbigousEsrReference()
	{
		final ESRStatement importData = importXML();

		assertThat(importData.getTransactions()).hasSize(4);

		// no errors on "header" level
		assertThat(importData.getErrorMsgs()).isEmpty();

		// only one transaction has that little problem
		assertThat(importData.getTransactions())
				.areExactly(3, trxHasNoErrors);

		// all have a reference set
		assertThat(importData.getTransactions())
				.as("3 transactions have a  non-empty reference set")
				.allSatisfy(t -> assertThat(t.getEsrReferenceNumber()).isNotEmpty()

				);

		assertThat(importData.getTransactions())
				.filteredOn(t -> t.getEsrReferenceNumber().equals("100760109103380989047265478"))
				.hasSize(1) // guard
				.allSatisfy(t -> {
					assertThat(t.getErrorMsgs()).hasSize(1);
					assertThat(t.getErrorMsgs().get(0)).isEqualTo(ESRConstants.MSG_AMBIGOUS_REFERENCE.toAD_Message());
				});

		assertThat(importData.getCtrlAmount()).isEqualByComparingTo("380");
	}

	/**
	 * Verifies that are 2 QRR  transaction lines
	 */
	@Test
	public void testQRRType()
	{
		final ESRStatement importData = importXML();
		assertThat(importData.getTransactions())
				.filteredOn(t -> t.getType().equals(ESRType.TYPE_QRR))
				.hasSize(3)
				.allSatisfy(t -> assertThat(t.getEsrParticipantNo()).isNotEmpty());
	}
}
