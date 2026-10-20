package de.metas.payment.esr.dataimporter.impl.camt54;

import com.google.common.collect.ImmutableList;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.dataimporter.ESRStatement;
import de.metas.payment.esr.dataimporter.ESRTransaction;
import de.metas.payment.esr.dataimporter.ESRType;
import de.metas.payment.esr.model.I_ESR_ImportFile;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Imports the PostFinance camt.054.001.08 sample files (unmodified, public test data) and one synthetic QR-reference credit file.
 * The PostFinance files are the ISO 2019 sample files PostFinance publishes for business customers ("Musterfiles für Geschäftskunden",
 * https://www.postfinance.ch/de/support/dokumente/musterfiles-fuer-geschaeftskunden.html), downloaded 2026-10-19 and committed unchanged.
 * <p>
 * The expected values of every test are derived from the file's own content (entries, transaction details, amounts, references);
 * they are never taken from a run of the importer. One test per file.
 * No test asserts a crash: a file the importer refuses on purpose is asserted as an {@link AdempiereException} carrying the named
 * AD_Message; a payment without remittance info or reference becomes a line with an error message.
 */
public class ESRDataImporterCamt54V08PostFinanceSamplesTest
{
	private static final String RESOURCE_FOLDER = "/postfinance/";

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	private static final class ExpectedLine
	{
		private final String amount;
		@Nullable
		private final String reference;
		private final ESRType type;
		private final String trxType;
		private final int errorCount;

		private ExpectedLine(final String amount, @Nullable final String reference, final ESRType type, final String trxType, final int errorCount)
		{
			this.amount = amount;
			this.reference = reference;
			this.type = type;
			this.trxType = trxType;
			this.errorCount = errorCount;
		}
	}

	private static ExpectedLine expected(final String amount, @Nullable final String reference, final ESRType type, final String trxType, final int errorCount)
	{
		return new ExpectedLine(amount, reference, type, trxType, errorCount);
	}

	private ESRStatement importFile(@NonNull final String fileName)
	{
		final InputStream inputStream = getClass().getResourceAsStream(RESOURCE_FOLDER + fileName);
		assertThat(inputStream).as("Unable to load %s", fileName).isNotNull();

		return new ESRDataImporterCamt54(newInstance(I_ESR_ImportFile.class), inputStream).importData();
	}

	private void assertImport(
			@NonNull final String fileName,
			@NonNull final String expectedCtrlAmount,
			@Nullable final String expectedCtrlQty,
			@NonNull final List<ExpectedLine> expectedLines)
	{
		final ESRStatement statement = importFile(fileName);

		assertThat(statement.getErrorMsgs()).as("%s: statement error messages", fileName).isEmpty();
		assertThat(statement.getCtrlAmount()).as("%s: ctrlAmount", fileName).isEqualByComparingTo(expectedCtrlAmount);
		if (expectedCtrlQty == null)
		{
			assertThat(statement.getCtrlQty()).as("%s: ctrlQty", fileName).isNull();
		}
		else
		{
			assertThat(statement.getCtrlQty()).as("%s: ctrlQty", fileName).isEqualByComparingTo(expectedCtrlQty);
		}

		final List<ESRTransaction> transactions = statement.getTransactions();
		assertThat(transactions).as("%s: number of lines", fileName).hasSize(expectedLines.size());
		for (int i = 0; i < expectedLines.size(); i++)
		{
			final ExpectedLine expectedLine = expectedLines.get(i);
			final ESRTransaction trx = transactions.get(i);
			final String what = fileName + " line " + (i + 1);

			assertThat(trx.getAmount()).as("%s: amount", what).isEqualByComparingTo(expectedLine.amount);
			if (expectedLine.reference == null)
			{
				assertThat(trx.getEsrReferenceNumber()).as("%s: reference", what).isNullOrEmpty();
			}
			else
			{
				assertThat(trx.getEsrReferenceNumber()).as("%s: reference", what).isEqualTo(expectedLine.reference);
			}
			assertThat(trx.getType()).as("%s: type", what).isEqualTo(expectedLine.type);
			assertThat(trx.getTrxType()).as("%s: trxType", what).isEqualTo(expectedLine.trxType);
			assertThat(trx.getErrorMsgs()).as("%s: error messages", what).hasSize(expectedLine.errorCount);
		}
	}

	/** 200519_camt054_P_CH2909000000250094239_1111091335_0_2020061900081727_v2019.xml: 2 line(s), control amount 1692.22, control quantity 2. */
	@Test
	public void testFile01_QrrTwoLines()
	{
		assertImport(
				"200519_camt054_P_CH2909000000250094239_1111091335_0_2020061900081727_v2019.xml",
				"1692.22",
				"2",
				ImmutableList.of(
					expected("778.29", "539115429773825311971477453", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("913.93", "662437765447746478179744715", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0)));
	}

	/** camt054_P_CH2909000000250094239_1111091335_0_2024012400081727_v2019.xml: 3 line(s), control amount 2192.72, control quantity 3. */
	@Test
	public void testFile02_QrrThreeLines()
	{
		assertImport(
				"camt054_P_CH2909000000250094239_1111091335_0_2024012400081727_v2019.xml",
				"2192.72",
				"3",
				ImmutableList.of(
					expected("500.50", "000000000000000000000000105", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("778.29", "539115429773825311971477453", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("913.93", "662437765447746478179744715", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0)));
	}

	/** 200519_camt054-chdd_p_ch2909000000250094239_1110097484_0_20190520700381159_v2019.xml: 2 line(s), control amount 4.50, control quantity 2. */
	@Test
	public void testFile03_Chdd_NoReference()
	{
		assertImport(
				"200519_camt054-chdd_p_ch2909000000250094239_1110097484_0_20190520700381159_v2019.xml",
				"4.50",
				"2",
				ImmutableList.of(
					expected("1.50", null, ESRType.TYPE_ESR, ESRConstants.ESRTRXTYPE_CreditMemo, 1),
					expected("3.00", null, ESRType.TYPE_ESR, ESRConstants.ESRTRXTYPE_CreditMemo, 1)));
	}

	/** 10_camt054_Return_Detailavisierung mit Einzelbuchung_ISO2019_v2019.xml: 1 line(s), control amount -500.00, control quantity 1. */
	@Test
	public void testFile04_Return_SingleBooking_Reversal()
	{
		assertImport(
				"10_camt054_Return_Detailavisierung mit Einzelbuchung_ISO2019_v2019.xml",
				"-500.00",
				"1",
				ImmutableList.of(
					expected("-500.00", null, ESRType.TYPE_ESR, ESRConstants.ESRTRXTYPE_ReverseBooking, 1)));
	}

	/** 9_camt054 Return_Detailavisierung mit Sammelbuchung_ISO2019_v2019.xml: 1 line(s), control amount 400.00, control quantity 1. */
	@Test
	public void testFile05_Return_CollectiveBooking()
	{
		assertImport(
				"9_camt054 Return_Detailavisierung mit Sammelbuchung_ISO2019_v2019.xml",
				"400.00",
				"1",
				ImmutableList.of(
					expected("400.00", null, ESRType.TYPE_ESR, ESRConstants.ESRTRXTYPE_CreditMemo, 1)));
	}

	/** camt054_P_CH2909000000250094239_1111111112_0_2022031011011199_v2019.xml: 1 line(s), control amount 522.10, control quantity 1. */
	@Test
	public void testFile06_StrdWithoutCreditorReference_522_10()
	{
		assertImport(
				"camt054_P_CH2909000000250094239_1111111112_0_2022031011011199_v2019.xml",
				"522.10",
				"1",
				ImmutableList.of(
					expected("522.10", null, ESRType.TYPE_ESR, ESRConstants.ESRTRXTYPE_CreditMemo, 1)));
	}

	/** camt054_P_CH2909000000250094239_1111111119_0_2022030911011199_v2019.xml: 1 line(s), control amount 501.05, control quantity 1. */
	@Test
	public void testFile07_StrdWithoutCreditorReference_501_05()
	{
		assertImport(
				"camt054_P_CH2909000000250094239_1111111119_0_2022030911011199_v2019.xml",
				"501.05",
				"1",
				ImmutableList.of(
					expected("501.05", null, ESRType.TYPE_ESR, ESRConstants.ESRTRXTYPE_CreditMemo, 1)));
	}

	/** 200519_camt054-ESR-ASR_P_CH2909000000250094239_1110092704_0_2019042500372179_v2019.xml: 1 line(s), control amount -147.00, control quantity 1. */
	@Test
	public void testFile08_EsrAsr_Debit()
	{
		assertImport(
				"200519_camt054-ESR-ASR_P_CH2909000000250094239_1110092704_0_2019042500372179_v2019.xml",
				"-147.00",
				"1",
				ImmutableList.of(
					expected("-147.00", "100041698214115449371805278", ESRType.TYPE_ESR, ESRConstants.ESRTRXTYPE_UNKNOWN, 2)));
	}

	/** camt.054_P_CH0309000000772897159_1120564403_0_2025050601041344.xml: 63 line(s), control amount 30279.53, control quantity 63. */
	@Test
	public void testFile09_QrrSixtyThreeLines()
	{
		assertImport(
				"camt.054_P_CH0309000000772897159_1120564403_0_2025050601041344.xml",
				"30279.53",
				"63",
				ImmutableList.of(
					expected("852.10", "374275749662732624797381579", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("25.49", "875654181269329816198865890", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("405.72", "582854186851693322969113418", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("49.04", "165593123882612978826917483", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("324.74", "954889237958942491733489330", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("904.05", "298583228446792772364134814", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("571.61", "255482414585559713858299119", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("9.66", "146966869591576275226638766", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("796.11", "674173147352925694467663866", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("894.59", "233636235361886433561277642", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("173.66", "898425733768215655636495295", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("560.54", "388612456792248598392742613", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("424.34", "823346392644153391922331619", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("545.04", "375556669121449582172344966", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("843.09", "533384655868576862766254267", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("758.21", "363468942629925884736473563", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("378.47", "221767913876153134298833959", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("636.82", "691476793786927785892516812", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("470.00", "163475127612652441694217718", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("155.85", "239881953135348743445915297", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("547.76", "368857196157215859144654241", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("781.48", "967521587878192975532316897", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("565.39", "366192567356679718657425990", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("284.72", "156562215759489725889758194", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("3.21", "127495934975192951915287789", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("140.65", "134327123859282373836588276", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("530.77", "132331635712525959539564781", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("832.14", "278944265834761258397559677", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("307.85", "916763744983678395197297970", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("146.02", "122287553229188229777432438", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("553.49", "414244445285249966429512278", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("50.35", "735399216452664544931271129", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("57.87", "134285251361679454679468571", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("402.26", "956486558589729817398794711", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("274.02", "582521831414912448384247624", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("944.87", "373152469518547249411153778", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("978.02", "396894124527125373676426897", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("737.38", "852959317612442884682232113", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("420.65", "417468471359129526541761460", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("838.20", "628461833893768549762758468", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("549.40", "179236864587585894948429383", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("737.04", "784227491761577763115516630", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("728.94", "518972737388176656843816319", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("568.01", "312591644277271178559367978", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("284.13", "552977354228169349198631655", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("99.58", "315796723123637559418919381", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("961.89", "195718536516932715348854166", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("136.80", "464564852389262133235213739", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("599.98", "574483465379153463883852922", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("909.52", "869841332313347412868565625", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("136.79", "726978327967794196699553533", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("316.00", "768875173349883369879693615", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("37.04", "937685148472924474678353425", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("582.16", "933416928188522779389536648", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("517.87", "669681282546514398755911874", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("861.31", "451646332443717124776892780", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("954.48", "374882338256766596962295110", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("256.33", "647327244598682739861312465", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("821.07", "831962341266856182372158637", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("101.55", "912478165768783848518586420", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("249.78", "663238187449295112656127993", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("549.70", "871334442981693563883851757", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0),
					expected("143.93", "633713734276797578694179794", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0)));
	}

	/** camt054_v08_qrr_credit_sample.xml (synthetic): 1 line(s), control amount 6045.85, control quantity none. */
	@Test
	public void testQrrCreditSample_OneLine()
	{
		assertImport(
				"camt054_v08_qrr_credit_sample.xml",
				"6045.85",
				null,
				ImmutableList.of(
					expected("6045.85", "000000000000000000000000218", ESRType.TYPE_QRR, ESRConstants.ESRTRXTYPE_CreditMemo, 0)));
	}

	/**
	 * camt.054_P_CH0309000000772897159_1120564402_0_2025050600511334.xml: the import is refused with
	 * {@code ESR_CAMT54_MultipleTransactionsTypes} (deliberate rule, not a crash).
	 * Reason, from the file content: every one of its 4 {@code NtryDtls} mixes lines that carry a QRR / SCOR reference
	 * (16 {@code Cd=SCOR} references in total) with lines that have none, and the importer refuses a batch that mixes
	 * reference types (kept as is on purpose).
	 */
	@Test
	public void testFile10_ScorMixedWithoutReference_RefusedMultipleTransactionsTypes()
	{
		assertImportRefusedMultipleTransactionsTypes("camt.054_P_CH0309000000772897159_1120564402_0_2025050600511334.xml");
	}

	/**
	 * 8_camt054 CWD_SIA Einzelbestaetigung_v2019.xml: the import is refused with {@code ESR_CAMT54_MultipleTransactionsTypes}
	 * (deliberate rule, not a crash).
	 * Reason, from the file content: its single {@code NtryDtls} holds 4 debit lines, one with a QRR reference, one with a
	 * {@code Cd=SCOR} reference and two without any, so it mixes reference types (kept as is on purpose).
	 */
	@Test
	public void testFile11_CwdSia_Debit_ScorAndQrr_RefusedMultipleTransactionsTypes()
	{
		assertImportRefusedMultipleTransactionsTypes("8_camt054 CWD_SIA Einzelbestaetigung_v2019.xml");
	}

	/**
	 * 7_camt054 CND Ausfuehrungsbestaetigung_ISO2019_v2019.xml: a debit confirmation with 1 {@code TxDtls} of -241.50 and no
	 * {@code RmtInf} block, no {@code Btch}. A payment without remittance info is not an exception: it becomes one line
	 * with 2 error messages ("unsupported credit/debit code" for the debit, "missing ESR reference" for the absent reference),
	 * type ESR (the fallback), no reference.
	 */
	@Test
	public void testFile12_Cnd_DebitConfirmation_NoRmtInf_BecomesErrorLine()
	{
		assertImport(
				"7_camt054 CND Ausfuehrungsbestaetigung_ISO2019_v2019.xml",
				"-241.50",
				null,
				ImmutableList.of(
					expected("-241.50", null, ESRType.TYPE_ESR, ESRConstants.ESRTRXTYPE_UNKNOWN, 2)));

		final ESRStatement statement = importFile("7_camt054 CND Ausfuehrungsbestaetigung_ISO2019_v2019.xml");
		assertThat(statement.getTransactions().get(0).getErrorMsgs())
				.contains(ReferenceStringHelper.MSG_MISSING_ESR_REFERENCE.toAD_Message());
	}

	private void assertImportRefusedMultipleTransactionsTypes(@NonNull final String fileName)
	{
		assertThatThrownBy(() -> importFile(fileName))
				.isInstanceOf(AdempiereException.class)
				.hasMessage(ESRDataImporterCamt54.MSG_MULTIPLE_TRANSACTIONS_TYPES.toAD_Message());
	}
}
