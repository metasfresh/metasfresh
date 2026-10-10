/*
 * #%L
 * de.metas.banking.camt53
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

package de.metas.banking.camt53.wrapper.v08;

import com.google.common.collect.ImmutableList;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.camt53.Camt53Version;
import de.metas.banking.camt53.jaxb.camt053_001_08.Document;
import de.metas.banking.camt53.wrapper.IAccountStatementWrapper;
import de.metas.banking.camt53.wrapper.IStatementLineWrapper;
import de.metas.banking.camt53.wrapper.ITransactionDtlsWrapper;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.i18n.IMsgBL;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BatchBankToCustomerStatementV08WrapperTest
{
	private static final String XSD_RESOURCE = "/de/metas/banking/camt53/schema/camt_053_001_08/camt.053.001.08.xsd";
	private static final String SAMPLE_RESOURCE = "/camt053_v08_sample.xml";
	private static final String EDGE_CASES_RESOURCE = "/camt053_v08_edge_cases.xml";
	private static final ZoneId ZONE = ZoneId.of("Europe/Berlin");

	private IAccountStatementWrapper statement;

	@BeforeEach
	void beforeEach() throws Exception
	{
		AdempiereTestHelper.get().init();

		final ImmutableList<IAccountStatementWrapper> wrappers = toWrappers(SAMPLE_RESOURCE);
		assertThat(wrappers).hasSize(1);
		statement = wrappers.get(0);
	}

	private static ImmutableList<IAccountStatementWrapper> toWrappers(final String resource) throws Exception
	{
		return BatchBankToCustomerStatementV08Wrapper
				.of(unmarshal(resource).getBkToCstmrStmt())
				.getAccountStatementWrappers(
						BankAccountService.newInstanceForUnitTesting(),
						new CurrencyRepository(),
						Services.get(IMsgBL.class));
	}

	/**
	 * Unmarshals the sample with XSD validation turned on, so an unmarshal failure means the sample does not conform to the bundled schema.
	 */
	private static Document unmarshal(final String resource) throws Exception
	{
		final URL xsd = BatchBankToCustomerStatementV08WrapperTest.class.getResource(XSD_RESOURCE);
		assertThat(xsd).as("bundled XSD").isNotNull();
		final Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(xsd);

		final Unmarshaller unmarshaller = JAXBContext.newInstance(Document.class).createUnmarshaller();
		unmarshaller.setSchema(schema);

		try (final InputStream in = BatchBankToCustomerStatementV08WrapperTest.class.getResourceAsStream(resource))
		{
			assertThat(in).as("sample xml").isNotNull();
			return unmarshaller.unmarshal(new StreamSource(in), Document.class).getValue();
		}
	}

	private IStatementLineWrapper line(final int index)
	{
		return statement.getStatementLines().get(index);
	}

	@Test
	void camt53Version_ofCode_v08()
	{
		assertThat(Camt53Version.ofCode("camt.053.001.08")).isEqualTo(Camt53Version.V08);
		assertThat(Camt53Version.V08.getCode()).isEqualTo("camt.053.001.08");
	}

	@Test
	void sampleConformsToXsd() throws Exception
	{
		assertThat(unmarshal(SAMPLE_RESOURCE).getBkToCstmrStmt().getStmt()).hasSize(1);
		assertThat(unmarshal(EDGE_CASES_RESOURCE).getBkToCstmrStmt().getStmt()).hasSize(2);
	}

	@Test
	void statementHeader_idDateCurrencyBeginningBalance()
	{
		assertThat(statement.getId()).isEqualTo("TESTSTMT-0001");
		assertThat(statement.getStatementDate(ZONE).toLocalDate()).isEqualTo(LocalDate.of(2024, 5, 3));
		assertThat(statement.getStatementCurrencyCode()).contains(CurrencyCode.CHF);
		assertThat(statement.getBeginningBalance()).isEqualByComparingTo("1000.00");
		assertThat(statement.hasNoBankStatementLines()).isFalse();
	}

	@Test
	void lineCount()
	{
		assertThat(statement.getStatementLines()).hasSize(4);
	}

	@Test
	void amountsAndSign()
	{
		assertThat(line(0).isCRDT()).isTrue();
		assertThat(line(0).getStatementAmount().toBigDecimal()).isEqualByComparingTo("100.50");
		assertThat(line(1).getStatementAmount().toBigDecimal()).isEqualByComparingTo("200.00");
		assertThat(line(2).isCRDT()).isFalse();
		assertThat(line(2).getStatementAmount().toBigDecimal()).isEqualByComparingTo("-50.25");
		assertThat(line(3).getStatementAmount().toBigDecimal()).isEqualByComparingTo("300.00");
		assertThat(line(0).getStatementAmount().getCurrencyCode()).isEqualTo(CurrencyCode.CHF);
	}

	@Test
	void qrrTransaction_onlyForEntryA()
	{
		assertThat(line(0).isQRRTransaction()).isTrue();
		assertThat(line(1).isQRRTransaction()).isFalse();
		assertThat(line(2).isQRRTransaction()).isFalse();
		assertThat(line(3).isQRRTransaction()).isFalse();
	}

	@Test
	void lineReferenceAndAcctSvcrRef()
	{
		assertThat(line(0).getLineReference()).isEqualTo("NTRY-A");
		assertThat(line(0).getAcctSvcrRef()).isEqualTo("ASR-A");
		assertThat(line(2).getLineReference()).isEqualTo("NTRY-C");
		assertThat(line(2).getAcctSvcrRef()).isEqualTo("ASR-C");
	}

	@Test
	void partyNamesAndRemittanceInfo()
	{
		assertThat(line(0).getDbtrNames()).isEqualTo("Test Debtor AG");
		assertThat(line(2).getCdtrNames()).isEqualTo("Example Supplier SA");
		assertThat(line(1).getUnstructuredRemittanceInfo()).isEqualTo("Invoice\n4711");
		assertThat(line(1).getDocumentReferenceCandidates()).contains("4711", "Invoice");
	}

	@Test
	void statementLineDate_fromDtOrDtTm()
	{
		assertThat(line(0).getStatementLineDate(ZONE).orElseThrow().toLocalDate()).isEqualTo(LocalDate.of(2024, 5, 3));
		// entry B carries ValDt/DtTm; in v08 DateAndDateTime2Choice has Dt and DtTm
		assertThat(line(1).getStatementLineDate(ZONE).orElseThrow().toLocalDate()).isEqualTo(LocalDate.of(2024, 5, 3));
	}

	@Test
	void batchEntry_exposesTwoTransactionDtls()
	{
		assertThat(line(0).isBatchTransaction()).isFalse();
		assertThat(line(3).isBatchTransaction()).isTrue();

		final List<ITransactionDtlsWrapper> txs = line(3).getTransactionDtlsWrapper();
		assertThat(txs).hasSize(2);
		assertThat(txs.get(0).getAcctSvcrRef()).isEqualTo("ASR-D-TX1");
		assertThat(txs.get(1).getAcctSvcrRef()).isEqualTo("ASR-D-TX2");
		assertThat(txs.get(0).getDbtrNames()).isEqualTo("Batch Payer One");
		assertThat(txs.get(1).getCcy()).isEqualTo("CHF");
		assertThat(txs.get(1).isCRDT()).isTrue();
	}

	@Test
	void debitOpeningBalance_isNegated() throws Exception
	{
		final IAccountStatementWrapper edgeStatement = toWrappers(EDGE_CASES_RESOURCE).get(0);
		assertThat(edgeStatement.getBeginningBalance()).isEqualByComparingTo("-500.00");
	}

	@Test
	void prcdBalance_isUsedWhenOpbdIsAbsent() throws Exception
	{
		final IAccountStatementWrapper prcdStatement = toWrappers(EDGE_CASES_RESOURCE).get(1);
		assertThat(prcdStatement.getBeginningBalance()).isEqualByComparingTo("70.00");
		assertThat(prcdStatement.hasNoBankStatementLines()).isTrue();
	}

	@Test
	void transactionWithoutRefs_andWithoutDbtr_isNullSafe() throws Exception
	{
		final IStatementLineWrapper entry = toWrappers(EDGE_CASES_RESOURCE).get(0).getStatementLines().get(0);
		final ITransactionDtlsWrapper tx = entry.getTransactionDtlsWrapper().get(0);

		assertThat(tx.getAcctSvcrRef()).isNull(); // no Refs element
		assertThat(tx.getDbtrNames()).isNull(); // Cdtr only
		assertThat(tx.getCdtrNames()).isEqualTo("Only Creditor AG");
		assertThat(entry.getDbtrNames()).isEmpty();
		assertThat(entry.getCdtrNames()).isEqualTo("Only Creditor AG");
	}

	@Test
	void transactionWithoutCdtr_isNullSafe() throws Exception
	{
		final IStatementLineWrapper entry = toWrappers(EDGE_CASES_RESOURCE).get(0).getStatementLines().get(1);
		final ITransactionDtlsWrapper tx = entry.getTransactionDtlsWrapper().get(0);

		assertThat(tx.getDbtrNames()).isEqualTo("Only Debtor GmbH");
		assertThat(tx.getCdtrNames()).isNull(); // Dbtr only
		assertThat(tx.getAcctSvcrRef()).isEqualTo("ASR-E2-TX");
		assertThat(entry.getCdtrNames()).isEmpty();
	}

	@Test
	void transactionLineDescription_isEmptyWhenAddtlTxInfIsAbsent() throws Exception
	{
		final IStatementLineWrapper entryWithoutInfo = toWrappers(EDGE_CASES_RESOURCE).get(0).getStatementLines().get(0);
		assertThat(entryWithoutInfo.getTransactionDtlsWrapper().get(0).getLineDescription()).isEmpty();

		final IStatementLineWrapper entryWithInfo = toWrappers(EDGE_CASES_RESOURCE).get(0).getStatementLines().get(1);
		assertThat(entryWithInfo.getTransactionDtlsWrapper().get(0).getLineDescription()).isEqualTo("Extra info");
	}
}
