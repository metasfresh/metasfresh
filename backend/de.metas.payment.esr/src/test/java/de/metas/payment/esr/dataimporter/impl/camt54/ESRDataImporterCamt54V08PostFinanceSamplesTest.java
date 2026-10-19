package de.metas.payment.esr.dataimporter.impl.camt54;

import de.metas.payment.esr.dataimporter.ESRStatement;
import de.metas.payment.esr.dataimporter.ESRTransaction;
import de.metas.payment.esr.model.I_ESR_ImportFile;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.math.BigDecimal;
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
 * Imports the PostFinance camt.054.001.08 sample files (plus one anonymised real-life notification).
 * The expected lines are derived from each file's own content (Ntry / NtryDtls / TxDtls / RmtInf), not from a run of the importer.
 */
public class ESRDataImporterCamt54V08PostFinanceSamplesTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	private ESRStatement importFile(@NonNull final String fileName)
	{
		final String resourceName = "/postfinance/" + fileName;
		final InputStream inputStream = getClass().getResourceAsStream(resourceName);
		assertThat(inputStream).as("Unable to load %s", resourceName).isNotNull();

		return new ESRDataImporterCamt54(newInstance(I_ESR_ImportFile.class), inputStream).importData();
	}

	private static ExpectedLine line(final String amount, final String reference, final boolean hasErrors)
	{
		return new ExpectedLine(new BigDecimal(amount), reference, hasErrors);
	}

	private static void assertLines(final ESRStatement stmt, final ExpectedLine... expected)
	{
		final List<ESRTransaction> trxs = stmt.getTransactions();
		assertThat(trxs).hasSize(expected.length);
		for (int i = 0; i < expected.length; i++)
		{
			final ESRTransaction trx = trxs.get(i);
			final ExpectedLine exp = expected[i];
			assertThat(trx.getAmount()).as("amount of line %s", i + 1).isEqualByComparingTo(exp.amount);
			assertThat(trx.getEsrReferenceNumber()).as("reference of line %s", i + 1).isEqualTo(exp.reference);
			assertThat(trx.getErrorMsgs().isEmpty()).as("no error messages on line %s: %s", i + 1, trx.getErrorMsgs()).isEqualTo(!exp.hasErrors);
		}
	}

	private static final class ExpectedLine
	{
		private final BigDecimal amount;
		private final String reference;
		private final boolean hasErrors;

		private ExpectedLine(final BigDecimal amount, final String reference, final boolean hasErrors)
		{
			this.amount = amount;
			this.reference = reference;
			this.hasErrors = hasErrors;
		}
	}

	@Test
	public void qrrTwoLines()
	{
		final ESRStatement stmt = importFile("200519_camt054_P_CH2909000000250094239_1111091335_0_2020061900081727_v2019.xml");
		assertLines(stmt,
				line("778.29", "539115429773825311971477453", false),
				line("913.93", "662437765447746478179744715", false));
		assertThat(stmt.getCtrlAmount()).isEqualByComparingTo("1692.22");
		assertThat(stmt.getCtrlQty()).isEqualByComparingTo("2");
	}

	@Test
	public void qrrThreeLinesTwoEntries()
	{
		final ESRStatement stmt = importFile("camt054_P_CH2909000000250094239_1111091335_0_2024012400081727_v2019.xml");
		assertLines(stmt,
				line("500.50", "000000000000000000000000105", false),
				line("778.29", "539115429773825311971477453", false),
				line("913.93", "662437765447746478179744715", false));
		assertThat(stmt.getCtrlAmount()).isEqualByComparingTo("2192.72");
		assertThat(stmt.getCtrlQty()).isEqualByComparingTo("3");
	}

	@Test
	public void noReferenceTwoLines()
	{
		final ESRStatement stmt = importFile("200519_camt054-chdd_p_ch2909000000250094239_1110097484_0_20190520700381159_v2019.xml");
		assertLines(stmt,
				line("1.50", null, true),
				line("3.00", null, true));
		assertThat(stmt.getCtrlAmount()).isEqualByComparingTo("4.50");
		assertThat(stmt.getCtrlQty()).isEqualByComparingTo("2");
	}

	@Test
	public void reversalSingleBooking()
	{
		final ESRStatement stmt = importFile("10_camt054_Return_Detailavisierung mit Einzelbuchung_ISO2019_v2019.xml");
		assertLines(stmt,
				line("-500.00", null, true));
		assertThat(stmt.getCtrlAmount()).isEqualByComparingTo("-500.00");
		assertThat(stmt.getCtrlQty()).isEqualByComparingTo("1");
	}

	@Test
	public void returnCollectiveBooking()
	{
		final ESRStatement stmt = importFile("9_camt054 Return_Detailavisierung mit Sammelbuchung_ISO2019_v2019.xml");
		assertLines(stmt,
				line("400.00", null, true));
		assertThat(stmt.getCtrlAmount()).isEqualByComparingTo("400.00");
		assertThat(stmt.getCtrlQty()).isEqualByComparingTo("1");
	}

	@Test
	public void structuredWithoutCreditorReference1()
	{
		final ESRStatement stmt = importFile("camt054_P_CH2909000000250094239_1111111112_0_2022031011011199_v2019.xml");
		assertLines(stmt,
				line("522.10", null, true));
		assertThat(stmt.getCtrlAmount()).isEqualByComparingTo("522.10");
		assertThat(stmt.getCtrlQty()).isEqualByComparingTo("1");
	}

	@Test
	public void structuredWithoutCreditorReference2()
	{
		final ESRStatement stmt = importFile("camt054_P_CH2909000000250094239_1111111119_0_2022030911011199_v2019.xml");
		assertLines(stmt,
				line("501.05", null, true));
		assertThat(stmt.getCtrlAmount()).isEqualByComparingTo("501.05");
		assertThat(stmt.getCtrlQty()).isEqualByComparingTo("1");
	}

	@Test
	public void debitWithUntypedReference()
	{
		final ESRStatement stmt = importFile("200519_camt054-ESR-ASR_P_CH2909000000250094239_1110092704_0_2019042500372179_v2019.xml");
		assertLines(stmt,
				line("-147.00", "100041698214115449371805278", true));
		assertThat(stmt.getCtrlAmount()).isEqualByComparingTo("-147.00");
		assertThat(stmt.getCtrlQty()).isEqualByComparingTo("1");
	}

	@Test
	public void qrr63Lines()
	{
		final ESRStatement stmt = importFile("camt.054_P_CH0309000000772897159_1120564403_0_2025050601041344.xml");
		assertLines(stmt,
				line("852.10", "374275749662732624797381579", false),
				line("25.49", "875654181269329816198865890", false),
				line("405.72", "582854186851693322969113418", false),
				line("49.04", "165593123882612978826917483", false),
				line("324.74", "954889237958942491733489330", false),
				line("904.05", "298583228446792772364134814", false),
				line("571.61", "255482414585559713858299119", false),
				line("9.66", "146966869591576275226638766", false),
				line("796.11", "674173147352925694467663866", false),
				line("894.59", "233636235361886433561277642", false),
				line("173.66", "898425733768215655636495295", false),
				line("560.54", "388612456792248598392742613", false),
				line("424.34", "823346392644153391922331619", false),
				line("545.04", "375556669121449582172344966", false),
				line("843.09", "533384655868576862766254267", false),
				line("758.21", "363468942629925884736473563", false),
				line("378.47", "221767913876153134298833959", false),
				line("636.82", "691476793786927785892516812", false),
				line("470.00", "163475127612652441694217718", false),
				line("155.85", "239881953135348743445915297", false),
				line("547.76", "368857196157215859144654241", false),
				line("781.48", "967521587878192975532316897", false),
				line("565.39", "366192567356679718657425990", false),
				line("284.72", "156562215759489725889758194", false),
				line("3.21", "127495934975192951915287789", false),
				line("140.65", "134327123859282373836588276", false),
				line("530.77", "132331635712525959539564781", false),
				line("832.14", "278944265834761258397559677", false),
				line("307.85", "916763744983678395197297970", false),
				line("146.02", "122287553229188229777432438", false),
				line("553.49", "414244445285249966429512278", false),
				line("50.35", "735399216452664544931271129", false),
				line("57.87", "134285251361679454679468571", false),
				line("402.26", "956486558589729817398794711", false),
				line("274.02", "582521831414912448384247624", false),
				line("944.87", "373152469518547249411153778", false),
				line("978.02", "396894124527125373676426897", false),
				line("737.38", "852959317612442884682232113", false),
				line("420.65", "417468471359129526541761460", false),
				line("838.20", "628461833893768549762758468", false),
				line("549.40", "179236864587585894948429383", false),
				line("737.04", "784227491761577763115516630", false),
				line("728.94", "518972737388176656843816319", false),
				line("568.01", "312591644277271178559367978", false),
				line("284.13", "552977354228169349198631655", false),
				line("99.58", "315796723123637559418919381", false),
				line("961.89", "195718536516932715348854166", false),
				line("136.80", "464564852389262133235213739", false),
				line("599.98", "574483465379153463883852922", false),
				line("909.52", "869841332313347412868565625", false),
				line("136.79", "726978327967794196699553533", false),
				line("316.00", "768875173349883369879693615", false),
				line("37.04", "937685148472924474678353425", false),
				line("582.16", "933416928188522779389536648", false),
				line("517.87", "669681282546514398755911874", false),
				line("861.31", "451646332443717124776892780", false),
				line("954.48", "374882338256766596962295110", false),
				line("256.33", "647327244598682739861312465", false),
				line("821.07", "831962341266856182372158637", false),
				line("101.55", "912478165768783848518586420", false),
				line("249.78", "663238187449295112656127993", false),
				line("549.70", "871334442981693563883851757", false),
				line("143.93", "633713734276797578694179794", false));
		assertThat(stmt.getCtrlAmount()).isEqualByComparingTo("30279.53");
		assertThat(stmt.getCtrlQty()).isEqualByComparingTo("63");
	}

	@Test
	public void mixedScorAndNoReference()
	{
		assertThatThrownBy(() -> importFile("camt.054_P_CH0309000000772897159_1120564402_0_2025050600511334.xml"))
				.isInstanceOf(AdempiereException.class)
				.hasMessage(ESRDataImporterCamt54.MSG_MULTIPLE_TRANSACTIONS_TYPES.toAD_Message());
	}

	@Test
	public void debitMixedReferenceTypes()
	{
		assertThatThrownBy(() -> importFile("8_camt054 CWD_SIA Einzelbestaetigung_v2019.xml"))
				.isInstanceOf(AdempiereException.class)
				.hasMessage(ESRDataImporterCamt54.MSG_MULTIPLE_TRANSACTIONS_TYPES.toAD_Message());
	}

	/**
	 * Characterization of a known gap: the importer does not null-guard a missing RmtInf element, so the import aborts with an NPE.
	 * Replace this expectation when that guard is added.
	 */
	@Test
	public void debitConfirmationWithoutRmtInf()
	{
		assertThatThrownBy(() -> importFile("7_camt054 CND Ausfuehrungsbestaetigung_ISO2019_v2019.xml"))
				.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void qrrSingleLineWithoutBtch()
	{
		final ESRStatement stmt = importFile("camt054_v08_qrr_single_line.xml");
		assertLines(stmt,
				line("6045.85", "000000000102175968010376491", false));
		assertThat(stmt.getCtrlAmount()).isEqualByComparingTo("6045.85");
		assertThat(stmt.getCtrlQty()).isNull();
	}
}
