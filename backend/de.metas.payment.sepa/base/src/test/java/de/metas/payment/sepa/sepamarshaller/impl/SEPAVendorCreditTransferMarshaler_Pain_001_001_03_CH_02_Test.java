package de.metas.payment.sepa.sepamarshaller.impl;

import de.metas.banking.Bank;
import de.metas.banking.BankCreateRequest;
import de.metas.banking.api.BankRepository;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.sepa.api.SEPAExportContext;
import de.metas.payment.sepa.api.SEPAProtocol;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.CreditTransferTransactionInformation10CH;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.Document;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.PaymentIdentification1;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

public class SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02_Test
{
	private BankRepository bankRepository;
	private SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02 xmlGenerator;
	private Document xmlDocument;

	private CurrencyId eur;
	private CurrencyId chf;

	@BeforeEach
	public void beforeTest()
	{
		AdempiereTestHelper.get().init();

		this.bankRepository = new BankRepository();
		final SEPAExportContext exportContext = SEPAExportContext.builder()
				.referenceAsEndToEndId(false)
				.build();
		this.xmlGenerator = new SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02(bankRepository, exportContext);
		this.xmlDocument = null;

		eur = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		chf = PlainCurrencyDAO.createCurrencyId(CurrencyCode.CHF);
	}

	@Test
	public void createDocument_batch() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport(
				"org", // SEPA_CreditorName
				"12345", // SEPA_CreditorIdentifier
				"INGBNL2A" // bic
		);
		createSEPAExportLine(sepaExport,
				"001",// SEPA_MandateRefNo
				"NL31INGB0000000044",// IBAN
				"INGBNL2A", // BIC
				new BigDecimal("100"), // amount
				eur);
		createSEPAExportLine(sepaExport,
				"002", // SEPA_MandateRefNo
				"NL31INGB0000000044", // IBAN
				"INGBNL2A",// BIC
				new BigDecimal("30"), // amount
				eur);

		createSEPAExportLine(sepaExport,
				"002", // SEPA_MandateRefNo
				"NL31INGB0000000044", // IBAN
				"INGBNL2A",// BIC
				new BigDecimal("40"), // amount
				chf);

		// invoke the method under test
		xmlDocument = xmlGenerator.createDocument(sepaExport);

		assertThat(xmlDocument.getCstmrCdtTrfInitn().getGrpHdr().getCtrlSum()).isEqualByComparingTo("170");
		assertThat(xmlDocument.getCstmrCdtTrfInitn().getGrpHdr().getNbOfTxs()).isEqualTo("3"); // needs to be 3, no matter wheter we do batch or not.
		assertThat(xmlDocument.getCstmrCdtTrfInitn().getGrpHdr().getInitgPty().getNm()).isEqualTo(sepaExport.getSEPA_CreditorName());

		// if no batch, it would be 3..
		// assertThat(xmlDocument.getCstmrCdtTrfInitn().getPmtInf()).hasSize(3);

		assertThat(xmlDocument.getCstmrCdtTrfInitn().getPmtInf()).hasSize(2);
		assertThat(xmlDocument.getCstmrCdtTrfInitn().getPmtInf()).allSatisfy(pmtInf -> assertThat(pmtInf.isBtchBookg()).isTrue());
	}

	@Test
	public void createDocument_batch_with_QR_IBAN() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport(
				"org", // SEPA_CreditorName
				"12345", // SEPA_CreditorIdentifier
				"INGBNL2A" // bic
		);
		createSEPAExportLineQRVersion(sepaExport,
									  "001",// SEPA_MandateRefNo
									  "NL31INGB0000000044",// IBAN
									  "INGBNL2A", // BIC
									  new BigDecimal("100"), // amount
									  eur, "210000000003139471430009017");

		createSEPAExportLineQRVersion(sepaExport,
									  "002", // SEPA_MandateRefNo
									  "NL31INGB0000000044", // IBAN
									  "INGBNL2A",// BIC
									  new BigDecimal("40"), // amount
									  chf, "210000000003139471430009017");

		// invoke the method under test
		xmlDocument = xmlGenerator.createDocument(sepaExport);

		assertThat(xmlDocument.getCstmrCdtTrfInitn().getGrpHdr().getCtrlSum()).isEqualByComparingTo("140");
		assertThat(xmlDocument.getCstmrCdtTrfInitn().getGrpHdr().getNbOfTxs()).isEqualTo("2");
		assertThat(xmlDocument.getCstmrCdtTrfInitn().getGrpHdr().getInitgPty().getNm()).isEqualTo(sepaExport.getSEPA_CreditorName());

		assertThat(xmlDocument.getCstmrCdtTrfInitn().getPmtInf()).hasSize(2);
		assertThat(xmlDocument.getCstmrCdtTrfInitn().getPmtInf()).allSatisfy(pmtInf -> assertThat(pmtInf.isBtchBookg()).isTrue());
	}


	@Test
	public void createDocument_batch_noCollectiveTransfer_noRef() throws Exception
	{
		final SEPAExportContext exportContext = SEPAExportContext.builder()
				.referenceAsEndToEndId(true)
				.build();
		this.xmlGenerator = new SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02(bankRepository, exportContext);

		final I_SEPA_Export sepaExport = createSEPAExport(
				"org", // SEPA_CreditorName
				"12345", // SEPA_CreditorIdentifier
				"INGBNL2A" // bic
		);
		createSEPAExportLine(sepaExport,
				"001",// SEPA_MandateRefNo
				"NL31INGB0000000044",// IBAN
				"INGBNL2A", // BIC
				new BigDecimal("100"), // amount
				eur);
		createSEPAExportLine(sepaExport,
				"002", // SEPA_MandateRefNo
				"NL31INGB0000000044", // IBAN
				"INGBNL2A",// BIC
				new BigDecimal("30"), // amount
				eur);

		createSEPAExportLine(sepaExport,
				"002", // SEPA_MandateRefNo
				"NL31INGB0000000044", // IBAN
				"INGBNL2A",// BIC
				new BigDecimal("40"), // amount
				chf);

		// invoke the method under test
		xmlDocument = xmlGenerator.createDocument(sepaExport);

		assertThat(xmlDocument.getCstmrCdtTrfInitn().getGrpHdr().getCtrlSum()).isEqualByComparingTo("170");
		assertThat(xmlDocument.getCstmrCdtTrfInitn().getGrpHdr().getNbOfTxs()).isEqualTo("3"); // needs to be 3, no matter wheter we do batch or not.
		assertThat(xmlDocument.getCstmrCdtTrfInitn().getGrpHdr().getInitgPty().getNm()).isEqualTo(sepaExport.getSEPA_CreditorName());
		final Set<String> endToEndIds = xmlDocument.getCstmrCdtTrfInitn().getPmtInf()
				.stream()
				.flatMap(pmtInf -> pmtInf.getCdtTrfTxInf().stream())
				.map(CreditTransferTransactionInformation10CH::getPmtId)
				.map(PaymentIdentification1::getEndToEndId)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		assertThat(endToEndIds).containsExactlyInAnyOrder(SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02.NOTPROVIDED_VALUE);

		assertThat(xmlDocument.getCstmrCdtTrfInitn().getPmtInf()).hasSize(2);
		assertThat(xmlDocument.getCstmrCdtTrfInitn().getPmtInf()).allSatisfy(pmtInf -> assertThat(pmtInf.isBtchBookg()).isTrue());
	}

	@Test
	public void createDocument_batch_noCollectiveTransfer_withRef() throws Exception
	{
		final SEPAExportContext exportContext = SEPAExportContext.builder()
				.referenceAsEndToEndId(true)
				.build();
		this.xmlGenerator = new SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02(bankRepository, exportContext);

		final I_SEPA_Export sepaExport = createSEPAExport(
				"org", // SEPA_CreditorName
				"12345", // SEPA_CreditorIdentifier
				"INGBNL2A" // bic
		);
		createSEPAExportLine(sepaExport,
				"001",// SEPA_MandateRefNo
				"NL31INGB0000000044",// IBAN
				"INGBNL2A", // BIC
				new BigDecimal("100"), // amount
				eur, // currency
				"Ref123"); // reference
		createSEPAExportLine(sepaExport,
				"002", // SEPA_MandateRefNo
				"NL31INGB0000000044", // IBAN
				"INGBNL2A",// BIC
				new BigDecimal("30"), // amount
				eur, // currency
				"Ref456"); // reference
		createSEPAExportLine(sepaExport,
				"002", // SEPA_MandateRefNo
				"NL31INGB0000000044", // IBAN
				"INGBNL2A",// BIC
				new BigDecimal("40"), // amount
				chf, // currency
				"Ref789"); // reference

		// invoke the method under test
		xmlDocument = xmlGenerator.createDocument(sepaExport);

		assertThat(xmlDocument.getCstmrCdtTrfInitn().getGrpHdr().getCtrlSum()).isEqualByComparingTo("170");
		assertThat(xmlDocument.getCstmrCdtTrfInitn().getGrpHdr().getNbOfTxs()).isEqualTo("3"); // needs to be 3, no matter wheter we do batch or not.
		assertThat(xmlDocument.getCstmrCdtTrfInitn().getGrpHdr().getInitgPty().getNm()).isEqualTo(sepaExport.getSEPA_CreditorName());
		final Set<String> endToEndIds = xmlDocument.getCstmrCdtTrfInitn().getPmtInf()
				.stream()
				.flatMap(pmtInf -> pmtInf.getCdtTrfTxInf().stream())
				.map(CreditTransferTransactionInformation10CH::getPmtId)
				.map(PaymentIdentification1::getEndToEndId)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		assertThat(endToEndIds).containsExactlyInAnyOrder("Ref123", "Ref456", "Ref789");

		assertThat(xmlDocument.getCstmrCdtTrfInitn().getPmtInf()).hasSize(2);
		assertThat(xmlDocument.getCstmrCdtTrfInitn().getPmtInf()).allSatisfy(pmtInf -> assertThat(pmtInf.isBtchBookg()).isTrue());
	}

	private I_SEPA_Export createSEPAExport(
			final String SEPA_CreditorName,
			final String SEPA_CreditorIdentifier,
			final String bic)
	{
		final I_SEPA_Export sepaExport = newInstance(I_SEPA_Export.class);
		sepaExport.setSEPA_Protocol(SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02.getCode());
		sepaExport.setSEPA_CreditorName(SEPA_CreditorName);
		sepaExport.setSEPA_CreditorIdentifier(SEPA_CreditorIdentifier);
		sepaExport.setSwiftCode(bic);
		sepaExport.setIsExportBatchBookings(true);
		save(sepaExport);

		return sepaExport;
	}

	@Test
	public void marshalDocument() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport(
				"org", // SEPA_CreditorName
				"12345", // SEPA_CreditorIdentifier
				"INGBNL2A" // bic
		);
		createSEPAExportLine(sepaExport,
				"001",// SEPA_MandateRefNo
				"NL31INGB0000000044",// IBAN
				"INGBNL2A", // BIC
				new BigDecimal("100"), // amount
				eur);
		createSEPAExportLine(sepaExport,
				"002", // SEPA_MandateRefNo
				"NL31INGB0000000044", // IBAN
				"INGBNL2A",// BIC
				new BigDecimal("30"), // amount
				eur);

		createSEPAExportLine(sepaExport,
				"002", // SEPA_MandateRefNo
				"NL31INGB0000000044", // IBAN
				"INGBNL2A",// BIC
				new BigDecimal("40"), // amount
				chf);

		final File fstream = File.createTempFile("sepaExport", ".xml");
		fstream.deleteOnExit();
		final FileOutputStream sepaExportOut = new FileOutputStream(fstream);
		xmlGenerator.marshal(sepaExport, sepaExportOut);
		sepaExportOut.close();

		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		final FileInputStream fis = new FileInputStream(fstream);
		final org.w3c.dom.Document sepaExportDoc = builder.parse(fis);

		assertThat(sepaExportDoc.getDocumentElement().getAttributes().getNamedItem("xsi:schemaLocation").getNodeValue()).isEqualTo("http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd pain.001.001.03.ch.02.xsd");

		assertThat(sepaExportDoc.getDocumentElement().getAttributes().getNamedItem("xmlns").getNodeValue()).isEqualTo("http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd");

	}

	private I_SEPA_Export_Line createSEPAExportLine(
			final I_SEPA_Export sepaExport,
			final String SEPA_MandateRefNo,
			final String iban,
			final String bic,
			final BigDecimal amt,
			final CurrencyId currencyId)
	{
		return createSEPAExportLine(sepaExport, SEPA_MandateRefNo, iban, bic, amt, currencyId, null);
	}

	private I_SEPA_Export_Line createSEPAExportLine(
			final I_SEPA_Export sepaExport,
			final String SEPA_MandateRefNo,
			final String iban,
			final String bic,
			final BigDecimal amt,
			final CurrencyId currencyId,
			final String structuredRemittanceInfo)
	{
		final Bank bank = bankRepository.createBank(BankCreateRequest.builder()
				.bankName("myBank")
				.routingNo("routingNo")
				.build());

		final I_C_BP_BankAccount bankAccount = newInstance(I_C_BP_BankAccount.class);
		bankAccount.setC_Bank_ID(bank.getBankId().getRepoId());
		bankAccount.setC_Currency_ID(currencyId.getRepoId());
		bankAccount.setIBAN(iban);
		bankAccount.setIsEsrAccount(true);
		bankAccount.setA_Name("bankAccount.A_Name");
		save(bankAccount);

		final I_SEPA_Export_Line line = newInstance(I_SEPA_Export_Line.class);
		line.setIBAN(iban);
		line.setSwiftCode(bic);
		line.setAmt(amt);
		line.setC_Currency_ID(currencyId.getRepoId());
		line.setSEPA_MandateRefNo(SEPA_MandateRefNo);
		line.setStructuredRemittanceInfo(structuredRemittanceInfo);

		line.setC_BP_BankAccount(bankAccount);
		line.setSEPA_Export(sepaExport);
		line.setIsActive(true);
		line.setIsError(false);
		save(line);

		return line;
	}

	private I_SEPA_Export_Line createSEPAExportLineQRVersion(
			final I_SEPA_Export sepaExport,
			final String SEPA_MandateRefNo,
			final String QRIban,
			final String bic,
			final BigDecimal amt,
			final CurrencyId currencyId,
			final String reference)
	{
		final Bank bank = bankRepository.createBank(BankCreateRequest.builder()
															.bankName("myBank")
															.routingNo("routingNo")
															.build());

		final I_C_BP_BankAccount bankAccount = newInstance(I_C_BP_BankAccount.class);
		bankAccount.setC_Bank_ID(bank.getBankId().getRepoId());
		bankAccount.setC_Currency_ID(currencyId.getRepoId());
		bankAccount.setQR_IBAN(QRIban);
		bankAccount.setIsEsrAccount(true);
		bankAccount.setA_Name("bankAccount.A_Name");
		save(bankAccount);

		final I_SEPA_Export_Line line = newInstance(I_SEPA_Export_Line.class);
		line.setIBAN(QRIban);
		line.setSwiftCode(bic);
		line.setAmt(amt);
		line.setC_Currency_ID(currencyId.getRepoId());
		line.setSEPA_MandateRefNo(SEPA_MandateRefNo);
		line.setStructuredRemittanceInfo(reference);

		line.setC_BP_BankAccount(bankAccount);
		line.setSEPA_Export(sepaExport);
		line.setIsActive(true);
		line.setIsError(false);
		save(line);

		return line;
	}

	@Test
	public void testReplaceForbiddenChars()
	{
		assertReplaceForbiddenCharsWorks("(1020739<-) | (1026@313<-)", "(1020739<-) _ (1026@313<-)");
		assertReplaceForbiddenCharsWorks("(1020739&lt;-) | (1026313&lt;-)", "(1020739&lt;-) _ (1026313&lt;-)");
		assertReplaceForbiddenCharsWorks("(1020739&lt;-) - (1026313&lt;-)", "(1020739&lt;-) - (1026313&lt;-)");
	}

	private void assertReplaceForbiddenCharsWorks(final String input, final String expected)
	{
		final String output = SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02.replaceForbiddenChars(input);
		assertThat(output).isEqualTo(expected);
	}

}
