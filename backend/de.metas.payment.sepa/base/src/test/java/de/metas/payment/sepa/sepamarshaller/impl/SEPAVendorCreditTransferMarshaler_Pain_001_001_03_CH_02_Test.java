package de.metas.payment.sepa.sepamarshaller.impl;

import de.metas.banking.Bank;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankCreateRequest;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.sepa.api.SEPAExportContext;
import de.metas.payment.sepa.api.SEPAProtocol;
import de.metas.payment.sepa.api.SepaMarshallerException;
import de.metas.payment.sepa.api.SepaUtils;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.CreditTransferTransactionInformation10CH;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.Document;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.PaymentIdentification1;
import de.metas.payment.sepa.jaxb.sct.pain_001_001_03_ch_02.PostalAddress6CH;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02_Test
{
	private BankRepository bankRepository;
	private BankAccountService bankAccountService;
	private SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02 xmlGenerator;
	private Document xmlDocument;

	private CurrencyId eur;
	private CurrencyId chf;

	@BeforeEach
	public void beforeTest()
	{
		AdempiereTestHelper.get().init();

		this.bankRepository = new BankRepository();
		this.bankAccountService = new BankAccountService(bankRepository, new CurrencyRepository());
		final SEPAExportContext exportContext = SEPAExportContext.builder()
				.referenceAsEndToEndId(false)
				.build();
		this.xmlGenerator = new SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02(bankRepository, exportContext, bankAccountService);
		this.xmlDocument = null;

		eur = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		chf = PlainCurrencyDAO.createCurrencyId(CurrencyCode.CHF);

		SpringContextHolder.registerJUnitBean(bankAccountService);
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
		assertThat(xmlDocument.getCstmrCdtTrfInitn().getGrpHdr().getNbOfTxs()).isEqualTo("3"); // needs to be 3, no matter if we do batch or not.
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
		this.xmlGenerator = new SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02(bankRepository, exportContext, bankAccountService);

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
		assertThat(xmlDocument.getCstmrCdtTrfInitn().getGrpHdr().getNbOfTxs()).isEqualTo("3"); // needs to be 3, no matter if we do batch or not.
		assertThat(xmlDocument.getCstmrCdtTrfInitn().getGrpHdr().getInitgPty().getNm()).isEqualTo(sepaExport.getSEPA_CreditorName());
		final Set<String> endToEndIds = xmlDocument.getCstmrCdtTrfInitn().getPmtInf()
				.stream()
				.flatMap(pmtInf -> pmtInf.getCdtTrfTxInf().stream())
				.map(CreditTransferTransactionInformation10CH::getPmtId)
				.map(PaymentIdentification1::getEndToEndId)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		assertThat(endToEndIds).containsExactlyInAnyOrder(SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02.BIC_NOTPROVIDED);

		assertThat(xmlDocument.getCstmrCdtTrfInitn().getPmtInf()).hasSize(2);
		assertThat(xmlDocument.getCstmrCdtTrfInitn().getPmtInf()).allSatisfy(pmtInf -> assertThat(pmtInf.isBtchBookg()).isTrue());
	}

	@Test
	public void createDocument_batch_noCollectiveTransfer_withRef() throws Exception
	{
		final SEPAExportContext exportContext = SEPAExportContext.builder()
				.referenceAsEndToEndId(true)
				.build();
		this.xmlGenerator = new SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02(bankRepository, exportContext, bankAccountService);

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
		assertThat(xmlDocument.getCstmrCdtTrfInitn().getGrpHdr().getNbOfTxs()).isEqualTo("3"); // needs to be 3, no matter if we do batch or not.
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
			final String swiftCode,
			final BigDecimal amt,
			final CurrencyId currencyId)
	{
		return createSEPAExportLine(sepaExport, SEPA_MandateRefNo, iban, swiftCode, amt, currencyId, null);
	}

	private I_SEPA_Export_Line createSEPAExportLine(
			final I_SEPA_Export sepaExport,
			final String SEPA_MandateRefNo,
			final String iban,
			final String swiftCode,
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
		bankAccount.setSwiftCode(swiftCode);
		bankAccount.setIsEsrAccount(true);
		bankAccount.setA_Name("bankAccount.A_Name");
		save(bankAccount);

		final I_SEPA_Export_Line line = newInstance(I_SEPA_Export_Line.class);
		line.setIBAN(iban);
		line.setSwiftCode(swiftCode);
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
		assertReplaceForbiddenCharsWorks("Epis d’Ajoie", "Epis d'Ajoie");
		assertReplaceForbiddenCharsWorks("t”", "t_");
		assertReplaceForbiddenCharsWorks("Société", "Société");
		assertReplaceForbiddenCharsWorks("Aæo", "A_o");
	}

	private void assertReplaceForbiddenCharsWorks(final String input, final String expected)
	{
		final String output = SepaUtils.replaceForbiddenChars(input);
		assertThat(output).isEqualTo(expected);
	}

	@Test
	public void testIsInvalidQRReference()
	{
		assertIsInvalidQRReferenceWorks("", true);
		assertIsInvalidQRReferenceWorks("33 36170 00113 54610 59304 00000", true);
		assertIsInvalidQRReferenceWorks("333617000113546105930400000", false);
		assertIsInvalidQRReferenceWorks("210000000003139471430009017", false);
	}

	private void assertIsInvalidQRReferenceWorks(String input, boolean expected)
	{
		boolean result = SEPAVendorCreditTransferMarshaler_Pain_001_001_03_CH_02.isInvalidQRReference(input);
		assertThat(result).isEqualTo(expected);
	}

	// ----------------------------------------------------------------------------------------------
	// Address resolution — bank account is authoritative.
	// See me03#29173: when the C_BP_BankAccount has any address fields populated, the SEPA
	// XML must use those fields verbatim instead of silently falling back to the partner
	// billing location.
	// ----------------------------------------------------------------------------------------------

	private BankAccount bankAccount(
			final String street,
			final String zip,
			final String city,
			final String country)
	{
		return BankAccount.builder()
				.id(BankAccountId.ofRepoId(1))
				.orgId(OrgId.ANY)
				.currencyId(chf)
				.accountStreet(street)
				.accountZip(zip)
				.accountCity(city)
				.accountCountry(country)
				.build();
	}

	private I_C_Location chLocation(final String address1, final String postal, final String city)
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setCountryCode("CH");
		country.setName("Switzerland");
		save(country);

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setAddress1(address1);
		location.setPostal(postal);
		location.setCity(city);
		location.setC_Country_ID(country.getC_Country_ID());
		save(location);
		return location;
	}

	@Test
	public void createStructuredPstlAdr_bankAccountAuthoritative_fullAddress()
	{
		final BankAccount bankAccount = bankAccount("Bankstrasse 1", "8000", "Zuerich", "CH");

		final PostalAddress6CH pstlAdr = xmlGenerator.createStructuredPstlAdr(bankAccount, null);

		assertThat(pstlAdr.getCtry()).isEqualTo("CH");
		assertThat(pstlAdr.getStrtNm()).isEqualTo("Bankstrasse");
		assertThat(pstlAdr.getBldgNb()).isEqualTo("1");
		assertThat(pstlAdr.getPstCd()).isEqualTo("8000");
		assertThat(pstlAdr.getTwnNm()).isEqualTo("Zuerich");
	}

	@Test
	public void createStructuredPstlAdr_bankAccountAuthoritative_partialAddress_streetOnly()
	{
		// Bank account has only the street and country populated; zip / city are blank.
		// Expectation under "bank account is authoritative": the partner billing
		// location is NOT consulted, even though it would provide the missing fields.
		// The blank fields stay blank in the XML.
		final BankAccount bankAccount = bankAccount("Bankstrasse 1", null, null, "CH");
		final I_C_Location partnerLocation = chLocation("Partnerstrasse 5", "3000", "Bern");

		final PostalAddress6CH pstlAdr = xmlGenerator.createStructuredPstlAdr(bankAccount, partnerLocation);

		assertThat(pstlAdr.getCtry()).isEqualTo("CH");
		assertThat(pstlAdr.getStrtNm()).isEqualTo("Bankstrasse");
		assertThat(pstlAdr.getBldgNb()).isEqualTo("1");
		assertThat(pstlAdr.getPstCd()).isNull();
		assertThat(pstlAdr.getTwnNm()).isNull();
	}

	@Test
	public void createStructuredPstlAdr_bankAccountAuthoritative_partialAddress_cityOnly()
	{
		final BankAccount bankAccount = bankAccount(null, null, "Zuerich", "CH");
		final I_C_Location partnerLocation = chLocation("Partnerstrasse 5", "3000", "Bern");

		final PostalAddress6CH pstlAdr = xmlGenerator.createStructuredPstlAdr(bankAccount, partnerLocation);

		assertThat(pstlAdr.getCtry()).isEqualTo("CH");
		assertThat(pstlAdr.getTwnNm()).isEqualTo("Zuerich");
		assertThat(pstlAdr.getStrtNm()).isNull();
		assertThat(pstlAdr.getBldgNb()).isNull();
		assertThat(pstlAdr.getPstCd()).isNull();
	}

	@Test
	public void createStructuredPstlAdr_bankAccountAuthoritative_addressWithoutCountry_throws()
	{
		// Country is mandatory in <Ctry>; emitting a structured address without a
		// valid ISO-3166 alpha-2 country code would produce schema-invalid SEPA XML.
		// The marshaler must surface this as a clear SEPA error instead.
		final BankAccount bankAccount = bankAccount("Bankstrasse 1", "8000", "Zuerich", null);
		final I_C_Location partnerLocation = chLocation("Partnerstrasse 5", "3000", "Bern");

		assertThatThrownBy(() -> xmlGenerator.createStructuredPstlAdr(bankAccount, partnerLocation))
				.isInstanceOf(SepaMarshallerException.class)
				.hasMessageContaining("A_Country is required");
	}

	@Test
	public void createStructuredPstlAdr_bankAccountAuthoritative_invalidCountry_throws()
	{
		// "Switzerland" is not a valid ISO-3166 alpha-2 country code.
		final BankAccount bankAccount = bankAccount("Bankstrasse 1", "8000", "Zuerich", "Switzerland");
		final I_C_Location partnerLocation = chLocation("Partnerstrasse 5", "3000", "Bern");

		assertThatThrownBy(() -> xmlGenerator.createStructuredPstlAdr(bankAccount, partnerLocation))
				.isInstanceOf(SepaMarshallerException.class)
				.hasMessageContaining("'Switzerland' is not a valid ISO-3166 alpha-2 country code");
	}

	@Test
	public void createStructuredPstlAdr_emptyBankAccount_fallsBackToLocation()
	{
		final BankAccount emptyBankAccount = bankAccount(null, null, null, null);
		final I_C_Location partnerLocation = chLocation("Partnerstrasse 5", "3000", "Bern");

		final PostalAddress6CH pstlAdr = xmlGenerator.createStructuredPstlAdr(emptyBankAccount, partnerLocation);

		assertThat(pstlAdr.getStrtNm()).isEqualTo("Partnerstrasse");
		assertThat(pstlAdr.getBldgNb()).isEqualTo("5");
		assertThat(pstlAdr.getPstCd()).isEqualTo("3000");
		assertThat(pstlAdr.getTwnNm()).isEqualTo("Bern");
		assertThat(pstlAdr.getCtry()).isEqualTo("CH");
	}

	@Test
	public void createStructuredPstlAdr_nullBankAccount_fallsBackToLocation()
	{
		final I_C_Location partnerLocation = chLocation("Partnerstrasse 5", "3000", "Bern");

		final PostalAddress6CH pstlAdr = xmlGenerator.createStructuredPstlAdr(null, partnerLocation);

		assertThat(pstlAdr.getStrtNm()).isEqualTo("Partnerstrasse");
		assertThat(pstlAdr.getBldgNb()).isEqualTo("5");
		assertThat(pstlAdr.getPstCd()).isEqualTo("3000");
		assertThat(pstlAdr.getTwnNm()).isEqualTo("Bern");
		assertThat(pstlAdr.getCtry()).isEqualTo("CH");
	}

	@Test
	public void createUnstructuredPstlAdr_bankAccountAuthoritative_partialAddress_streetOnly()
	{
		// Same partial-address case as createStructuredPstlAdr_*, but for the
		// unstructured (PAYMENT_TYPE_5 / PAYMENT_TYPE_6) variant. Country is
		// mandatory and supplied; zip / city stay blank.
		final BankAccount bankAccount = bankAccount("Bankstrasse 1", null, null, "CH");
		final I_C_Location partnerLocation = chLocation("Partnerstrasse 5", "3000", "Bern");

		final PostalAddress6CH pstlAdr = xmlGenerator.createUnstructuredPstlAdr(bankAccount, partnerLocation);

		// One AdrLine for the street, no zip/city line because both are blank.
		// pain.001 forbids mixing structured fields with AdrLine, so the structured
		// fields stay null in the bank-account branch.
		assertThat(pstlAdr.getCtry()).isEqualTo("CH");
		assertThat(pstlAdr.getAdrLine()).containsExactly("Bankstrasse 1");
		assertThat(pstlAdr.getStrtNm()).isNull();
		assertThat(pstlAdr.getBldgNb()).isNull();
		assertThat(pstlAdr.getPstCd()).isNull();
		assertThat(pstlAdr.getTwnNm()).isNull();
	}

	@Test
	public void createUnstructuredPstlAdr_bankAccountAuthoritative_zipAndCityOnly()
	{
		final BankAccount bankAccount = bankAccount(null, "8000", "Zuerich", "CH");
		final I_C_Location partnerLocation = chLocation("Partnerstrasse 5", "3000", "Bern");

		final PostalAddress6CH pstlAdr = xmlGenerator.createUnstructuredPstlAdr(bankAccount, partnerLocation);

		// Only the zip+city line is emitted; no street line because the street is
		// blank. Structured fields stay null (no mixing).
		assertThat(pstlAdr.getCtry()).isEqualTo("CH");
		assertThat(pstlAdr.getAdrLine()).containsExactly("8000 Zuerich");
		assertThat(pstlAdr.getStrtNm()).isNull();
		assertThat(pstlAdr.getBldgNb()).isNull();
		assertThat(pstlAdr.getPstCd()).isNull();
		assertThat(pstlAdr.getTwnNm()).isNull();
	}

	@Test
	public void createUnstructuredPstlAdr_bankAccountAuthoritative_addressWithoutCountry_throws()
	{
		final BankAccount bankAccount = bankAccount("Bankstrasse 1", "8000", "Zuerich", null);
		final I_C_Location partnerLocation = chLocation("Partnerstrasse 5", "3000", "Bern");

		assertThatThrownBy(() -> xmlGenerator.createUnstructuredPstlAdr(bankAccount, partnerLocation))
				.isInstanceOf(SepaMarshallerException.class)
				.hasMessageContaining("A_Country is required");
	}

	@Test
	public void createUnstructuredPstlAdr_emptyBankAccount_fallsBackToLocation()
	{
		// Mirror of createStructuredPstlAdr_emptyBankAccount_fallsBackToLocation:
		// non-null but fully-blank bank account → location is used.
		final BankAccount emptyBankAccount = bankAccount(null, null, null, null);
		final I_C_Location partnerLocation = chLocation("Partnerstrasse 5", "3000", "Bern");

		final PostalAddress6CH pstlAdr = xmlGenerator.createUnstructuredPstlAdr(emptyBankAccount, partnerLocation);

		assertThat(pstlAdr.getCtry()).isEqualTo("CH");
		assertThat(pstlAdr.getAdrLine()).containsExactly("Partnerstrasse 5", "3000 Bern");
	}

	@Test
	public void createUnstructuredPstlAdr_nullBankAccount_fallsBackToLocation()
	{
		final I_C_Location partnerLocation = chLocation("Partnerstrasse 5", "3000", "Bern");

		final PostalAddress6CH pstlAdr = xmlGenerator.createUnstructuredPstlAdr(null, partnerLocation);

		assertThat(pstlAdr.getCtry()).isEqualTo("CH");
		assertThat(pstlAdr.getAdrLine()).containsExactly("Partnerstrasse 5", "3000 Bern");
	}

	@Test
	public void createUnstructuredPstlAdr_locationFallback_handlesNullPostalAndCity()
	{
		// Pre-existing latent bug: location.getPostal() + " " + location.getCity()
		// produced "null null" when both were missing. With joinNonBlank we get
		// an empty string for the second line, which is then suppressed because
		// SEPA XSDs require AdrLine to be non-empty when present.
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setCountryCode("CH");
		country.setName("Switzerland");
		save(country);

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setAddress1("Partnerstrasse 5");
		location.setC_Country_ID(country.getC_Country_ID());
		// Postal and City intentionally not set
		save(location);

		final PostalAddress6CH pstlAdr = xmlGenerator.createUnstructuredPstlAdr(null, location);

		assertThat(pstlAdr.getCtry()).isEqualTo("CH");
		// Only the street line is emitted; the (otherwise empty) zip+city line is suppressed.
		assertThat(pstlAdr.getAdrLine()).containsExactly("Partnerstrasse 5");
	}

	@Test
	public void createStructuredPstlAdr_bankAccountAuthoritative_countryOnly_fallsBackToLocation()
	{
		// Country alone is NOT a usable address; isAddressEmpty() ignores it on
		// purpose so we don't emit a <PstlAdr> with only a <Ctry> child (which
		// would be schema-invalid). The partner billing location is used instead.
		// The bank-account country value is irrelevant here — the bank-account
		// path is never entered, so it is neither validated nor read.
		final BankAccount bankAccount = bankAccount(null, null, null, "ZZ");
		final I_C_Location partnerLocation = chLocation("Partnerstrasse 5", "3000", "Bern");

		final PostalAddress6CH pstlAdr = xmlGenerator.createStructuredPstlAdr(bankAccount, partnerLocation);

		// Resolved from the location — bank-account country is ignored.
		assertThat(pstlAdr.getCtry()).isEqualTo("CH");
		assertThat(pstlAdr.getStrtNm()).isEqualTo("Partnerstrasse");
		assertThat(pstlAdr.getBldgNb()).isEqualTo("5");
		assertThat(pstlAdr.getPstCd()).isEqualTo("3000");
		assertThat(pstlAdr.getTwnNm()).isEqualTo("Bern");
	}

}
