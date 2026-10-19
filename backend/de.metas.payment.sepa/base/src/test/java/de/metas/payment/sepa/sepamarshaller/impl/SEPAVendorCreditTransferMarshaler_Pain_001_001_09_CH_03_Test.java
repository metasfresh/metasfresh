package de.metas.payment.sepa.sepamarshaller.impl;

import de.metas.banking.Bank;
import de.metas.banking.BankCreateRequest;
import de.metas.banking.api.BankAccountAcctRepository;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.sepa.api.ISEPADocumentBL;
import de.metas.payment.sepa.api.SEPACreditTransferXML;
import de.metas.payment.sepa.api.SEPAExportContext;
import de.metas.payment.sepa.api.SEPAProtocol;
import de.metas.payment.sepa.api.SepaUtils;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.util.Loggables;
import de.metas.util.PlainStringLoggable;
import de.metas.util.Services;
import lombok.NonNull;
import de.metas.i18n.Msg;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Bank;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.annotation.Nullable;
import javax.xml.XMLConstants;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static de.metas.payment.sepa.Pain001_001_09_XmlTestHelper.assertValidAgainstXsd;
import static de.metas.payment.sepa.Pain001_001_09_XmlTestHelper.count;
import static de.metas.payment.sepa.Pain001_001_09_XmlTestHelper.parse;
import static de.metas.payment.sepa.Pain001_001_09_XmlTestHelper.readXsdTargetNamespace;
import static de.metas.payment.sepa.Pain001_001_09_XmlTestHelper.text;
import static de.metas.payment.sepa.Pain001_001_09_XmlTestHelper.texts;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Exercises the pain.001.001.09.ch.03 credit transfer export through {@link ISEPADocumentBL#exportCreditTransferXML}, i.e. the same path the SEPA export process uses.
 * <p>
 * IBANs/BICs are the public example values of the SIX "Swiss Implementation Guidelines pain.001" (chapter 5.2) resp. the well-known ING test IBAN; names and addresses are dummies.
 */
public class SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03_Test
{
	private static final String DEBTOR_IBAN = "CH72 8000 5000 0888 7776 6";
	private static final String DEBTOR_BIC = "RAIFCH22005";

	private static final String CREDITOR_IBAN_CH = "CH50 2197 7000 0043 3134 6";
	private static final String CREDITOR_IID_FROM_IBAN_CH = "21977";
	private static final String CREDITOR_IBAN_DE = "DE62007620110623852957";
	private static final String CREDITOR_BIC_DE = "UBSWDEFF";
	private static final String CREDITOR_IBAN_NL = "NL31INGB0000000044";
	private static final String CREDITOR_QR_IBAN = "CH44 3199 9123 0008 8901 2";
	/**
	 * Example IBANs of the IBAN registry (SWIFT) and well-known BICs: TR is no SEPA scheme country, GB is a non-EEA SEPA scheme country (EPC409-09).
	 */
	private static final String CREDITOR_IBAN_TR = "TR330006100519786457841326";
	private static final String CREDITOR_BIC_TR = "TCZBTR2A";
	private static final String CREDITOR_IBAN_GB = "GB29NWBK60161331926819";
	private static final String CREDITOR_BIC_GB = "NWBKGB2L";

	/**
	 * QR reference and ISO 11649 creditor references of the IG examples (chapter 5.1 / 5.2); all with valid check digits.
	 */
	private static final String QR_REFERENCE = "210000000003139471430009017";
	private static final String SCOR_REFERENCE_SEPA = "RF18539007547034";
	private static final String SCOR_REFERENCE_FOREIGN = "RF4220210323103704APG0018";

	/**
	 * In unit tests, a message is rendered as its AD_Message value followed by its parameters (see PlainMsgBL).
	 */
	private static final String MSG_ADDRESS_INCOMPLETE = "de.metas.payment.sepa.SEPA_Export_CreditorAddressIncomplete";
	private static final String MSG_ADDRESS_OMITTED = "de.metas.payment.sepa.SEPA_Export_CreditorAddressOmitted";
	private static final String MSG_ADDRESS_OF_LOCATION = "de.metas.payment.sepa.SEPA_Export_AddressOfLocation";
	private static final String MSG_ADDRESS_OF_BANK_ACCOUNT = "de.metas.payment.sepa.SEPA_Export_AddressOfBankAccount";
	private static final String MSG_MISSING_TOWN = "de.metas.payment.sepa.SEPA_Export_MissingAddressPartTown";
	private static final String MSG_MISSING_COUNTRY = "de.metas.payment.sepa.SEPA_Export_MissingAddressPartCountry";
	private static final String MSG_MISSING_TOWN_AND_COUNTRY = "de.metas.payment.sepa.SEPA_Export_MissingAddressPartsTownAndCountry";
	private static final String MSG_NO_LINES = "de.metas.payment.sepa.SEPA_Export_NoLines";
	private static final String MSG_DEBTOR_NAME_MISSING = "de.metas.payment.sepa.SEPA_Export_DebtorNameMissing";
	private static final String MSG_DEBTOR_BANK_UNIDENTIFIED = "de.metas.payment.sepa.SEPA_Export_DebtorBankUnidentified";
	private static final String MSG_DEBTOR_ACCOUNT_OF_EXPORT = "de.metas.payment.sepa.SEPA_Export_DebtorAccountOfExport";
	private static final String MSG_IBAN_TOO_SHORT = "de.metas.payment.sepa.SEPA_Export_IbanTooShort";
	private static final String MSG_INVALID_AMOUNT = "de.metas.payment.sepa.SEPA_Export_InvalidAmount";
	private static final String MSG_AMOUNT_TOO_MANY_DECIMALS = "de.metas.payment.sepa.SEPA_Export_AmountTooManyDecimals";
	private static final String MSG_CREDITOR_BANK_UNIDENTIFIED = "de.metas.payment.sepa.SEPA_Export_CreditorBankUnidentified";
	private static final String MSG_CREDITOR_NAME_MISSING = "de.metas.payment.sepa.SEPA_Export_CreditorNameMissing";
	private static final String MSG_QR_IBAN_CURRENCY_NOT_SUPPORTED = "de.metas.payment.sepa.SEPA_Export_QrIbanCurrencyNotSupported";
	private static final String MSG_ISR_NOT_SUPPORTED = "de.metas.payment.sepa.SEPA_Export_IsrNotSupported";
	private static final String MSG_QR_REFERENCE_REQUIRED = "de.metas.payment.sepa.SEPA_Export_QrReferenceRequired";
	private static final String MSG_QR_REFERENCE_CHECK_DIGIT_WRONG = "de.metas.payment.sepa.SEPA_Export_QrReferenceCheckDigitWrong";
	private static final String MSG_NO_STRUCTURED_REFERENCE = "de.metas.payment.sepa.SEPA_Export_NoStructuredReference";

	/**
	 * Partners as shown in the messages: search key and name
	 */
	private static final String PARTNER_TEST_CREDITOR = "TestCreditor (Test Creditor AG)";
	private static final String PARTNER_TEST_VENDOR = "TestCreditor (Test Vendor GmbH)";

	private BankRepository bankRepository;

	private CurrencyId eur;
	private CurrencyId chf;
	private CurrencyId usd;

	@BeforeEach
	public void beforeTest()
	{
		AdempiereTestHelper.get().init();

		bankRepository = new BankRepository();
		SpringContextHolder.registerJUnitBean(bankRepository);
		SpringContextHolder.registerJUnitBean(new BankAccountService(bankRepository, new BankAccountAcctRepository(), new CurrencyRepository()));

		eur = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		chf = PlainCurrencyDAO.createCurrencyId(CurrencyCode.CHF);
		usd = PlainCurrencyDAO.createCurrencyId(CurrencyCode.USD);
	}

	@Test
	public void createDocument_domesticIban_validatesAgainstXsd() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_CH, null, new BigDecimal("100"), chf, "Invoice 1001");
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_CH, null, new BigDecimal("30.50"), chf, "Invoice 1002");

		final byte[] xml = exportXml(sepaExport);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		assertThat(text(doc, "Document/CstmrCdtTrfInitn/GrpHdr/NbOfTxs")).isEqualTo("2");
		assertThat(new BigDecimal(text(doc, "Document/CstmrCdtTrfInitn/GrpHdr/CtrlSum"))).isEqualByComparingTo("130.50");
		assertThat(text(doc, "Document/CstmrCdtTrfInitn/PmtInf/ReqdExctnDt/Dt")).isEqualTo("2026-10-20");
		assertThat(text(doc, "Document/CstmrCdtTrfInitn/PmtInf/DbtrAgt/FinInstnId/BICFI")).isEqualTo(DEBTOR_BIC);
		assertThat(text(doc, "Document/CstmrCdtTrfInitn/PmtInf/DbtrAcct/Id/IBAN")).isEqualTo("CH7280005000088877766");
		// domestic (payment type D): service level SEPA is not permitted
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf/PmtTpInf")).isZero();

		final String cdtTrfTxInf = "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf";
		assertThat(count(doc, cdtTrfTxInf)).isEqualTo(2);
		assertThat(text(doc, cdtTrfTxInf + "/CdtrAcct/Id/IBAN")).isEqualTo("CH5021977000004331346");
		assertThat(text(doc, cdtTrfTxInf + "/CdtrAgt/FinInstnId/ClrSysMmbId/ClrSysId/Cd")).isEqualTo("CHBCC");
		assertThat(text(doc, cdtTrfTxInf + "/CdtrAgt/FinInstnId/ClrSysMmbId/MmbId")).isEqualTo(CREDITOR_IID_FROM_IBAN_CH);
		assertThat(text(doc, cdtTrfTxInf + "/Cdtr/Nm")).isEqualTo("Test Creditor AG");
		assertStructuredCreditorAddress(doc, cdtTrfTxInf);
		assertThat(text(doc, cdtTrfTxInf + "/RmtInf/Ustrd")).isEqualTo("Invoice 1001");
	}

	@Test
	public void createDocument_sepaEur_validatesAgainstXsd() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("8479.25"), eur, "Invoice no. 408");

		final byte[] xml = exportXml(sepaExport);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		assertThat(text(doc, "Document/CstmrCdtTrfInitn/PmtInf/ReqdExctnDt/Dt")).isEqualTo("2026-10-20");
		assertThat(text(doc, "Document/CstmrCdtTrfInitn/PmtInf/PmtTpInf/SvcLvl/Cd")).isEqualTo("SEPA");
		assertThat(text(doc, "Document/CstmrCdtTrfInitn/PmtInf/DbtrAgt/FinInstnId/BICFI")).isEqualTo(DEBTOR_BIC);

		final String cdtTrfTxInf = "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf";
		assertThat(text(doc, cdtTrfTxInf + "/Amt/InstdAmt")).isEqualTo("8479.25");
		assertThat(text(doc, cdtTrfTxInf + "/CdtrAgt/FinInstnId/BICFI")).isEqualTo(CREDITOR_BIC_DE);
		assertThat(text(doc, cdtTrfTxInf + "/CdtrAcct/Id/IBAN")).isEqualTo(CREDITOR_IBAN_DE);
		assertStructuredCreditorAddress(doc, cdtTrfTxInf);
		assertThat(count(doc, cdtTrfTxInf + "/Cdtr/PstlAdr/AdrLine")).isZero();
		assertThat(text(doc, cdtTrfTxInf + "/RmtInf/Ustrd")).isEqualTo("Invoice no. 408");
	}

	@Test
	public void createDocument_noBic_usesClrSysMmbIdOrOmitsAgent() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_CH, null, new BigDecimal("100"), chf, null);
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_NL, null, new BigDecimal("40"), eur, null);

		final byte[] xml = exportXml(sepaExport);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);

		// domestic CH IBAN without BIC: the creditor agent is identified by the IID taken from the IBAN
		final String domesticTx = "Document/CstmrCdtTrfInitn/PmtInf[CdtTrfTxInf/Amt/InstdAmt/@Ccy='CHF']/CdtTrfTxInf";
		assertThat(text(doc, domesticTx + "/CdtrAgt/FinInstnId/ClrSysMmbId/MmbId")).isEqualTo(CREDITOR_IID_FROM_IBAN_CH);
		assertThat(count(doc, domesticTx + "/CdtrAgt/FinInstnId/BICFI")).isZero();

		// SEPA IBAN without BIC: the creditor agent is omitted
		final String sepaTx = "Document/CstmrCdtTrfInitn/PmtInf[CdtTrfTxInf/Amt/InstdAmt/@Ccy='EUR']/CdtTrfTxInf";
		assertThat(count(doc, sepaTx)).isEqualTo(1);
		assertThat(count(doc, sepaTx + "/CdtrAgt")).isZero();

		// never a placeholder BIC
		assertThat(new String(xml, "UTF-8")).doesNotContain("NOTPROVIDED");
	}

	@Test
	public void marshalDocument_namespaceAndSchemaLocation() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), eur, null);

		final byte[] xml = exportXml(sepaExport);

		final String targetNamespace = readXsdTargetNamespace();
		final Document doc = parse(xml);
		assertThat(doc.getDocumentElement().getLocalName()).isEqualTo("Document");
		assertThat(doc.getDocumentElement().getNamespaceURI()).isEqualTo(targetNamespace);
		assertThat(doc.getDocumentElement().getAttributeNS(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "schemaLocation"))
				.isEqualTo(targetNamespace + " pain.001.001.09.ch.03.xsd");

		// the CH restriction types are bound to their elements by the XSD; no xsi:type overrides in the output
		final NodeList xsiTypes = (NodeList)XPathFactory.newInstance().newXPath()
				.evaluate("//@*[local-name()='type' and namespace-uri()='" + XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI + "']", doc, XPathConstants.NODESET);
		assertThat(xsiTypes.getLength()).isZero();
	}

	@Test
	public void toReferenceText_restrictedCharacterSet()
	{
		assertThat(SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03.toReferenceText(null)).isNull();
		assertThat(SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03.toReferenceText(" _ ")).isNull();
		assertThat(SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03.toReferenceText("Ref 123/A-B.C")).isEqualTo("Ref 123/A-B.C");
		assertThat(SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03.toReferenceText("Ref_123&4")).isEqualTo("Ref 123 4");
		assertThat(SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03.toReferenceText("/Ref//123/")).isEqualTo("Ref/123");
		assertThat(SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03.toReferenceText("1234567890123456789012345678901234567890")).isEqualTo("67890123456789012345678901234567890");
	}

	@Test
	public void referenceCheckDigits()
	{
		assertThat(SepaUtils.isValidQrReference(QR_REFERENCE)).isTrue();
		assertThat(SepaUtils.isValidQrReference("210000000003139471430009018")).isFalse(); // check digit
		assertThat(SepaUtils.isValidQrReference("21000000000313947143000901")).isFalse(); // length
		assertThat(SepaUtils.isValidQrReference("21000000000313947143000901A")).isFalse();
		assertThat(SepaUtils.isValidQrReference(null)).isFalse();

		assertThat(SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03.isValidCreditorReference(SCOR_REFERENCE_SEPA)).isTrue();
		assertThat(SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03.isValidCreditorReference(SCOR_REFERENCE_FOREIGN)).isTrue();
		assertThat(SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03.isValidCreditorReference("RF712348231")).isTrue();
		assertThat(SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03.isValidCreditorReference("RF18539007547035")).isFalse(); // check digits
		assertThat(SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03.isValidCreditorReference("RF18 5390 0754 7034")).isFalse(); // whitespace is removed before
		assertThat(SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03.isValidCreditorReference("RF4220210323103704APG00180")).isFalse(); // > 25 characters
		assertThat(SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03.isValidCreditorReference("Ref456")).isFalse();
		assertThat(SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03.isValidCreditorReference(null)).isFalse();
	}

	@Test
	public void createDocument_eurToNonSwissIbanOnEsrAccount_isSepa() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), eur, "Invoice 1001");
		final I_C_BP_BankAccount bankAccount = bankAccountOf(line);
		bankAccount.setIsEsrAccount(true);
		save(bankAccount);

		final byte[] xml = exportXml(sepaExport);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		// IG 3.15: no CH/LI IBAN and EUR => payment type "S", regardless of the ESR flag
		assertThat(text(doc, "Document/CstmrCdtTrfInitn/PmtInf/PmtTpInf/SvcLvl/Cd")).isEqualTo("SEPA");
		assertThat(text(doc, "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf/CdtrAgt/FinInstnId/BICFI")).isEqualTo(CREDITOR_BIC_DE);
	}

	@Test
	public void createDocument_eurToNonSepaCountryIban_isForeign() throws Exception
	{
		// TR is not in the EPC list of SEPA scheme countries, so a EUR payment to a TR IBAN cannot be "S"
		final I_SEPA_Export sepaExport = createSEPAExport();
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_TR, CREDITOR_BIC_TR, new BigDecimal("100"), eur, null);

		final byte[] xml = exportXml(sepaExport);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		// payment type X: no service level SEPA
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf/PmtTpInf")).isZero();
		final String cdtTrfTxInf = "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf";
		assertThat(text(doc, cdtTrfTxInf + "/CdtrAcct/Id/IBAN")).isEqualTo(CREDITOR_IBAN_TR);
		assertThat(text(doc, cdtTrfTxInf + "/CdtrAgt/FinInstnId/BICFI")).isEqualTo(CREDITOR_BIC_TR);
		assertStructuredCreditorAddress(doc, cdtTrfTxInf);
	}

	@Test
	public void export_eurToNonSepaCountryIban_vendorWithoutTown_fails()
	{
		// payment type X (not S), so an incomplete address is not left out but fails the export
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_TR, CREDITOR_BIC_TR, new BigDecimal("100"), eur, null);
		clearAddress(bankAccountOf(line));
		setBillToLocation(line, "Bahnhofstrasse 5", "3000", " ", "TR");

		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_ADDRESS_INCOMPLETE)
				.hasMessageContaining(PARTNER_TEST_CREDITOR)
				.hasMessageContaining(MSG_MISSING_TOWN);
	}

	@Test
	public void createDocument_eurToNonEeaSepaCountryIban_isSepa() throws Exception
	{
		// GB is a non-EEA country in the EPC list of SEPA scheme countries
		final I_SEPA_Export sepaExport = createSEPAExport();
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_GB, CREDITOR_BIC_GB, new BigDecimal("100"), eur, null);

		final byte[] xml = exportXml(sepaExport);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		assertThat(text(doc, "Document/CstmrCdtTrfInitn/PmtInf/PmtTpInf/SvcLvl/Cd")).isEqualTo("SEPA");
		assertThat(text(doc, "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf/CdtrAcct/Id/IBAN")).isEqualTo(CREDITOR_IBAN_GB);
	}

	@Test
	public void createDocument_longCityAndName_truncatedToLimits() throws Exception
	{
		final String name80 = repeat("Creditor_X", 8);
		final String city40 = repeat("Oberdorf_X", 4);

		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_C_BP_BankAccount sepaBankAccount = bankAccountOf(createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), eur, null));
		sepaBankAccount.setA_Name(name80);
		sepaBankAccount.setA_City(city40);
		save(sepaBankAccount);
		final I_C_BP_BankAccount domesticBankAccount = bankAccountOf(createSEPAExportLine(sepaExport, CREDITOR_IBAN_CH, null, new BigDecimal("100"), chf, null));
		domesticBankAccount.setA_Name(name80);
		save(domesticBankAccount);

		final byte[] xml = exportXml(sepaExport);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		final String sepaTx = "Document/CstmrCdtTrfInitn/PmtInf[CdtTrfTxInf/Amt/InstdAmt/@Ccy='EUR']/CdtTrfTxInf";
		assertThat(text(doc, sepaTx + "/Cdtr/PstlAdr/TwnNm")).isEqualTo(city40.substring(0, 35)); // Max35Text
		assertThat(text(doc, sepaTx + "/Cdtr/Nm")).isEqualTo(name80.substring(0, 70)); // IG 4.3: "S: max. 70 characters"
		final String domesticTx = "Document/CstmrCdtTrfInitn/PmtInf[CdtTrfTxInf/Amt/InstdAmt/@Ccy='CHF']/CdtTrfTxInf";
		assertThat(text(doc, domesticTx + "/Cdtr/Nm")).isEqualTo(name80); // Max140Text
	}

	@Test
	public void createDocument_streetStartingWithNumber_keptAsStreetName() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_C_BP_BankAccount bankAccount = bankAccountOf(createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), eur, null));
		bankAccount.setA_Street("12 rue de la Paix");
		save(bankAccount);

		final byte[] xml = exportXml(sepaExport);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		final String pstlAdr = "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf/Cdtr/PstlAdr";
		assertThat(text(doc, pstlAdr + "/StrtNm")).isEqualTo("12 rue de la Paix");
		assertThat(count(doc, pstlAdr + "/BldgNb")).isZero();
	}

	@Test
	public void createDocument_debtorWithoutBic_usesIidFromDebtorIban() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		sepaExport.setSwiftCode(null);
		save(sepaExport);
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), eur, null);

		final byte[] xml = exportXml(sepaExport);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		final String finInstnId = "Document/CstmrCdtTrfInitn/PmtInf/DbtrAgt/FinInstnId";
		assertThat(text(doc, finInstnId + "/ClrSysMmbId/ClrSysId/Cd")).isEqualTo("CHBCC");
		assertThat(text(doc, finInstnId + "/ClrSysMmbId/MmbId")).isEqualTo("80005");
		assertThat(count(doc, finInstnId + "/BICFI")).isZero();
	}

	@Test
	public void export_debtorWithoutBicAndWithoutSwissIban_fails()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		sepaExport.setSwiftCode(null);
		sepaExport.setIBAN(CREDITOR_IBAN_NL);
		save(sepaExport);
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), eur, null);

		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_DEBTOR_BANK_UNIDENTIFIED)
				.hasMessageContaining("1000001");
	}

	@Test
	public void export_debtorWithoutBicAndWithTooShortSwissIban_fails()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		sepaExport.setSwiftCode(null);
		sepaExport.setIBAN("CH72 80");
		save(sepaExport);
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), eur, null);

		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_IBAN_TOO_SHORT)
				.hasMessageContaining("CH72 80")
				.hasMessageContaining(MSG_DEBTOR_ACCOUNT_OF_EXPORT + " - 1000001");
	}

	@Test
	public void export_noLines_fails()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();

		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_NO_LINES)
				.hasMessageContaining("1000001");
	}

	@Test
	public void export_noLines_atSignInDocumentNo_isEscaped()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		sepaExport.setDocumentNo("10@01");
		save(sepaExport);

		// the process runs the error message through parseTranslation; "@@" stands for a plain "@"
		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_NO_LINES)
				.hasMessageContaining("10@@01");
	}

	@Test
	public void export_zeroAmount_fails()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, BigDecimal.ZERO, eur, null);
		setBPartner(line, "Test Vendor GmbH");

		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_INVALID_AMOUNT)
				.hasMessageContaining("Test Vendor GmbH")
				.isInstanceOfSatisfying(AdempiereException.class, e -> assertThat(e.getRecord()).isEqualTo(TableRecordReference.of(line)));
	}

	@Test
	public void export_lineError_partnerNameWithAtSigns_keptInError()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, BigDecimal.ZERO, eur, null);
		setBPartner(line, "Vendor info@muster.ch / buchhaltung@muster.ch");

		// the process runs the error message through parseTranslation, which takes text between two "@" for an AD_Message/AD_Element; "@@" stands for a plain "@"
		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_INVALID_AMOUNT)
				.hasMessageContaining("Vendor info@@muster.ch / buchhaltung@@muster.ch")
				.satisfies(e -> assertThat(Msg.parseTranslation("de_CH", e.getMessage())).contains("Vendor info@muster.ch / buchhaltung@muster.ch"));
	}

	@Test
	public void export_amountWithTooManyDecimals_fails()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100.001"), eur, null);
		setBPartner(line, "Test Vendor GmbH");

		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_AMOUNT_TOO_MANY_DECIMALS)
				.hasMessageContaining("Test Vendor GmbH")
				.hasMessageContaining("100.001")
				.hasMessageContaining("EUR");
	}

	@Test
	public void createDocument_qrIban_qrrReference_validatesAgainstXsd() throws Exception
	{
		// fixture of the .03 test createDocument_batch_with_QR_IBAN, with the QR-IBAN / QR reference of IG 5.1
		final I_SEPA_Export sepaExport = createSEPAExport();
		// account with a QR-IBAN only: the export line's IBAN is the QR-IBAN
		createQrIbanLine(sepaExport, null, new BigDecimal("100"), chf, QR_REFERENCE);
		// account with both an IBAN and a QR-IBAN: the export line's IBAN is the "normal" IBAN, but a QR reference needs the QR-IBAN
		createQrIbanLine(sepaExport, CREDITOR_IBAN_CH, new BigDecimal("40"), eur, QR_REFERENCE);

		final byte[] xml = exportXml(sepaExport);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		assertThat(new BigDecimal(text(doc, "Document/CstmrCdtTrfInitn/GrpHdr/CtrlSum"))).isEqualByComparingTo("140");
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf")).isEqualTo(2);
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf/PmtTpInf")).isZero(); // payment type D

		final String cdtTrfTxInf = "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf";
		assertThat(count(doc, cdtTrfTxInf)).isEqualTo(2);
		assertThat(texts(doc, cdtTrfTxInf + "/CdtrAcct/Id/IBAN")).containsOnly("CH4431999123000889012");
		assertThat(count(doc, cdtTrfTxInf + "/CdtrAgt")).isZero(); // agent is determined from the QR-IBAN; we don't send the QR-IID
		assertThat(texts(doc, cdtTrfTxInf + "/RmtInf/Strd/CdtrRefInf/Tp/CdOrPrtry/Prtry")).containsOnly("QRR");
		assertThat(texts(doc, cdtTrfTxInf + "/RmtInf/Strd/CdtrRefInf/Ref")).containsOnly(QR_REFERENCE);
		assertThat(count(doc, cdtTrfTxInf + "/RmtInf/Ustrd")).isZero();
		assertThat(count(doc, cdtTrfTxInf + "/RmtInf/Strd/CdtrRefInf/Tp/CdOrPrtry/Cd")).isZero();
	}

	@Test
	public void createDocument_ibanAndQrIban_qrReferenceWithNoBreakSpaces_isQrr() throws Exception
	{
		// QR reference as printed on a QR-bill, copied with no-break spaces (U+00A0) between the groups; like .03, all Unicode whitespace is removed
		final String qrReferenceWithNoBreakSpaces = "21 00000 00003 13947 14300 09017";
		final I_SEPA_Export sepaExport = createSEPAExport();
		createQrIbanLine(sepaExport, CREDITOR_IBAN_CH, new BigDecimal("100"), chf, qrReferenceWithNoBreakSpaces);

		final byte[] xml = exportXml(sepaExport);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		final String cdtTrfTxInf = "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf";
		assertThat(text(doc, cdtTrfTxInf + "/CdtrAcct/Id/IBAN")).isEqualTo("CH4431999123000889012");
		assertThat(text(doc, cdtTrfTxInf + "/RmtInf/Strd/CdtrRefInf/Tp/CdOrPrtry/Prtry")).isEqualTo("QRR");
		assertThat(text(doc, cdtTrfTxInf + "/RmtInf/Strd/CdtrRefInf/Ref")).isEqualTo(QR_REFERENCE);
	}

	@Test
	public void export_qrIbanWithoutValidQrReference_fails()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		createQrIbanLine(sepaExport, null, new BigDecimal("100"), chf, "210000000003139471430009018"); // wrong check digit

		// same AD_Message as the .03 marshaller, with the translated reason as its parameter
		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining("SEPA_Export_InvalidReference")
				.hasMessageContaining(MSG_QR_REFERENCE_REQUIRED)
				.hasMessageContaining("210000000003139471430009018");
	}

	@Test
	public void export_qrIbanWithAtSignInReference_fails_referenceEscaped()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		createQrIbanLine(sepaExport, null, new BigDecimal("100"), chf, "Rechnung@4711");

		// the process runs the error message through parseTranslation, which takes text between two "@" for an AD_Message/AD_Element; "@@" stands for a plain "@"
		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining("SEPA_Export_InvalidReference")
				.hasMessageContaining(MSG_QR_REFERENCE_REQUIRED)
				.hasMessageContaining("Rechnung@@4711");
	}

	@Test
	public void export_ibanAndQrIban_mistypedQrReference_fails()
	{
		// looks like a QR reference (27 digits) but the check digit is wrong: a typo, not a reference for the normal IBAN
		final I_SEPA_Export sepaExport = createSEPAExport();
		createQrIbanLine(sepaExport, CREDITOR_IBAN_CH, new BigDecimal("100"), chf, "210000000003139471430009018");

		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining("SEPA_Export_InvalidReference")
				.hasMessageContaining(MSG_QR_REFERENCE_CHECK_DIGIT_WRONG)
				.hasMessageContaining("210000000003139471430009018");
	}

	@Test
	public void export_qrIbanOnlyWithoutReference_fails()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		createQrIbanLine(sepaExport, null, new BigDecimal("100"), chf, " ");

		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining("SEPA_Export_InvalidReference")
				.hasMessageContaining(MSG_QR_REFERENCE_REQUIRED)
				.hasMessageContaining(MSG_NO_STRUCTURED_REFERENCE)
				.hasMessageNotContaining("'null'");
	}

	@Test
	public void createDocument_ibanAndQrIban_scorReference_usesIban() throws Exception
	{
		// the account has both an IBAN and a QR-IBAN; the invoice was paid with an RF reference, so it is a payment to the normal IBAN
		final I_SEPA_Export sepaExport = createSEPAExport();
		createQrIbanLine(sepaExport, CREDITOR_IBAN_CH, new BigDecimal("100"), chf, SCOR_REFERENCE_SEPA);

		final byte[] xml = exportXml(sepaExport);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		final String cdtTrfTxInf = "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf";
		assertThat(text(doc, cdtTrfTxInf + "/CdtrAcct/Id/IBAN")).isEqualTo("CH5021977000004331346");
		assertThat(text(doc, cdtTrfTxInf + "/CdtrAgt/FinInstnId/ClrSysMmbId/MmbId")).isEqualTo(CREDITOR_IID_FROM_IBAN_CH);
		assertThat(text(doc, cdtTrfTxInf + "/RmtInf/Strd/CdtrRefInf/Tp/CdOrPrtry/Cd")).isEqualTo("SCOR");
		assertThat(text(doc, cdtTrfTxInf + "/RmtInf/Strd/CdtrRefInf/Ref")).isEqualTo(SCOR_REFERENCE_SEPA);
	}

	@Test
	public void createDocument_ibanAndQrIban_foreignCurrency_isForeignOnIban() throws Exception
	{
		// the account has both an IBAN and a QR-IBAN; a USD payment without QR reference goes to the normal IBAN as X V1
		final I_SEPA_Export sepaExport = createSEPAExport();
		createQrIbanLine(sepaExport, CREDITOR_IBAN_CH, new BigDecimal("250"), usd, "Invoice 7");

		final byte[] xml = exportXml(sepaExport);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		final String cdtTrfTxInf = "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf";
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf/PmtTpInf")).isZero();
		assertThat(text(doc, cdtTrfTxInf + "/CdtrAcct/Id/IBAN")).isEqualTo("CH5021977000004331346");
		assertThat(text(doc, cdtTrfTxInf + "/CdtrAgt/FinInstnId/ClrSysMmbId/ClrSysId/Cd")).isEqualTo("CHBCC");
		assertThat(text(doc, cdtTrfTxInf + "/RmtInf/Ustrd")).isEqualTo("Invoice 7");
	}

	@Test
	public void createDocument_longDebtorName_truncatedTo70() throws Exception
	{
		final String name80 = repeat("Debtor_XYZ", 8);
		final I_SEPA_Export sepaExport = createSEPAExport();
		sepaExport.setSEPA_CreditorName(name80);
		save(sepaExport);
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), eur, null);

		final byte[] xml = exportXml(sepaExport);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		// IG 4.1 InitgPty/Nm and 4.2 Dbtr/Nm: "S: max. 70 characters"; the group header applies to the whole message
		assertThat(text(doc, "Document/CstmrCdtTrfInitn/GrpHdr/InitgPty/Nm")).isEqualTo(name80.substring(0, 70));
		assertThat(text(doc, "Document/CstmrCdtTrfInitn/PmtInf/Dbtr/Nm")).isEqualTo(name80.substring(0, 70));
	}

	@Test
	public void export_qrIbanInForeignCurrency_fails()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		createQrIbanLine(sepaExport, null, new BigDecimal("100"), usd, QR_REFERENCE);

		// IG 3.16: QR-IBAN and QR reference are intended for CHF (and EUR until November 2027)
		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_QR_IBAN_CURRENCY_NOT_SUPPORTED)
				.hasMessageContaining("USD");
	}

	@Test
	public void createDocument_scorReference_validatesAgainstXsd() throws Exception
	{
		// fixture of the .03 test createDocument_batch_noCollectiveTransfer_withRef (ESR-flagged accounts, reference as EndToEndId), with the ISO 11649 references of IG 5.1/5.2
		final I_SEPA_Export sepaExport = createSEPAExport();
		createEsrFlaggedLine(sepaExport, CREDITOR_IBAN_NL, "INGBNL2A", new BigDecimal("100"), eur, SCOR_REFERENCE_SEPA);
		// a non-ISO structured reference on an ESR-flagged account would be an ISR payment, so this one is a plain account
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_NL, "INGBNL2A", new BigDecimal("30"), eur, "Ref456");
		createEsrFlaggedLine(sepaExport, CREDITOR_IBAN_CH, null, new BigDecimal("40"), chf, "RF71 2348 231");

		final byte[] xml = exportXml(sepaExport, true);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		assertThat(new BigDecimal(text(doc, "Document/CstmrCdtTrfInitn/GrpHdr/CtrlSum"))).isEqualByComparingTo("170");
		assertThat(texts(doc, "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf/PmtId/EndToEndId"))
				.containsExactlyInAnyOrder(SCOR_REFERENCE_SEPA, "Ref456", "RF712348231");

		final String sepaScorTx = "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf[Amt/InstdAmt='100']";
		assertThat(text(doc, sepaScorTx + "/RmtInf/Strd/CdtrRefInf/Tp/CdOrPrtry/Cd")).isEqualTo("SCOR");
		assertThat(text(doc, sepaScorTx + "/RmtInf/Strd/CdtrRefInf/Tp/Issr")).isEqualTo("ISO");
		assertThat(text(doc, sepaScorTx + "/RmtInf/Strd/CdtrRefInf/Ref")).isEqualTo(SCOR_REFERENCE_SEPA);
		assertThat(count(doc, sepaScorTx + "/RmtInf/Ustrd")).isZero();

		final String sepaUnstructuredTx = "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf[Amt/InstdAmt='30']";
		assertThat(text(doc, sepaUnstructuredTx + "/RmtInf/Ustrd")).isEqualTo("Ref456");
		assertThat(count(doc, sepaUnstructuredTx + "/RmtInf/Strd")).isZero();

		final String domesticScorTx = "Document/CstmrCdtTrfInitn/PmtInf[CdtTrfTxInf/Amt/InstdAmt/@Ccy='CHF']/CdtTrfTxInf";
		assertThat(text(doc, domesticScorTx + "/RmtInf/Strd/CdtrRefInf/Tp/CdOrPrtry/Cd")).isEqualTo("SCOR");
		assertThat(text(doc, domesticScorTx + "/RmtInf/Strd/CdtrRefInf/Ref")).isEqualTo("RF712348231"); // whitespace removed
		assertThat(text(doc, domesticScorTx + "/CdtrAgt/FinInstnId/ClrSysMmbId/MmbId")).isEqualTo(CREDITOR_IID_FROM_IBAN_CH);
	}

	@Test
	public void createDocument_foreignCurrency_validatesAgainstXsd() throws Exception
	{
		// CHF line of the .03 test createDocument_batch_noCollectiveTransfer_withRef (X, cross-border) + the USD payment of IG 5.2 (X, foreign currency domestic)
		final I_SEPA_Export sepaExport = createSEPAExport();
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_NL, "INGBNL2A", new BigDecimal("40"), chf, "Ref789");
		final I_SEPA_Export_Line usdLine = createSEPAExportLine(sepaExport, CREDITOR_IBAN_CH, null, new BigDecimal("3949.75"), usd, null);
		usdLine.setStructuredRemittanceInfo(SCOR_REFERENCE_FOREIGN);
		save(usdLine);

		final byte[] xml = exportXml(sepaExport);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf")).isEqualTo(2);
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf/PmtTpInf")).isZero(); // IG 3.15: X has no service level SEPA

		final String crossBorderTx = "Document/CstmrCdtTrfInitn/PmtInf[CdtTrfTxInf/Amt/InstdAmt/@Ccy='CHF']/CdtTrfTxInf";
		assertThat(text(doc, crossBorderTx + "/CdtrAgt/FinInstnId/BICFI")).isEqualTo("INGBNL2A");
		assertThat(text(doc, crossBorderTx + "/CdtrAcct/Id/IBAN")).isEqualTo(CREDITOR_IBAN_NL);
		assertThat(text(doc, crossBorderTx + "/RmtInf/Ustrd")).isEqualTo("Ref789");
		assertStructuredCreditorAddress(doc, crossBorderTx);

		final String foreignCurrencyDomesticTx = "Document/CstmrCdtTrfInitn/PmtInf[CdtTrfTxInf/Amt/InstdAmt/@Ccy='USD']/CdtTrfTxInf";
		assertThat(text(doc, foreignCurrencyDomesticTx + "/Amt/InstdAmt")).isEqualTo("3949.75");
		assertThat(text(doc, foreignCurrencyDomesticTx + "/CdtrAgt/FinInstnId/ClrSysMmbId/ClrSysId/Cd")).isEqualTo("CHBCC");
		assertThat(text(doc, foreignCurrencyDomesticTx + "/CdtrAgt/FinInstnId/ClrSysMmbId/MmbId")).isEqualTo(CREDITOR_IID_FROM_IBAN_CH);
		assertThat(text(doc, foreignCurrencyDomesticTx + "/RmtInf/Strd/CdtrRefInf/Tp/CdOrPrtry/Cd")).isEqualTo("SCOR");
		assertThat(text(doc, foreignCurrencyDomesticTx + "/RmtInf/Strd/CdtrRefInf/Ref")).isEqualTo(SCOR_REFERENCE_FOREIGN);
	}

	@Test
	public void createDocument_foreignWithoutBic_usesBankNameAndAddress() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, null, new BigDecimal("100"), chf, null);
		setBankLocation(line, "Bankstrasse 7", "60311", "Frankfurt", "DE");

		final byte[] xml = exportXml(sepaExport);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		final String finInstnId = "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf/CdtrAgt/FinInstnId";
		assertThat(count(doc, finInstnId + "/BICFI")).isZero();
		assertThat(text(doc, finInstnId + "/Nm")).isEqualTo("Test Bank");
		assertThat(text(doc, finInstnId + "/PstlAdr/StrtNm")).isEqualTo("Bankstrasse");
		assertThat(text(doc, finInstnId + "/PstlAdr/TwnNm")).isEqualTo("Frankfurt");
		assertThat(text(doc, finInstnId + "/PstlAdr/Ctry")).isEqualTo("DE");
	}

	@Test
	public void export_isrLine_fails()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_CH, null, new BigDecimal("100"), chf, null);
		line.setStructuredRemittanceInfo("210000000003139471430009017");
		save(line);
		final I_C_BP_BankAccount bankAccount = bankAccountOf(line);
		bankAccount.setIsEsrAccount(true);
		save(bankAccount);

		setBPartner(line, "Test Vendor GmbH");

		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_ISR_NOT_SUPPORTED)
				.hasMessageContaining("Test Vendor GmbH");
	}

	@Test
	public void export_foreignWithoutBicAndWithoutBankAddress_fails()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, null, new BigDecimal("100"), chf, null);
		setBPartner(line, "Test Vendor GmbH");

		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_CREDITOR_BANK_UNIDENTIFIED)
				.hasMessageContaining("Test Vendor GmbH");
	}

	@Test
	public void createDocument_referenceAsEndToEndId() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), eur, "Invoice_1001 & more");
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("30"), eur, null);

		final byte[] xml = exportXml(sepaExport, true);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		assertThat(texts(doc, "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf/PmtId/EndToEndId"))
				.containsExactlyInAnyOrder("Invoice 1001   more", "NOTPROVIDED");
	}

	@Test
	public void createDocument_noBankAccountAddress_usesBillToLocation() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), eur, null);
		clearAddress(bankAccountOf(line));
		setBillToLocation(line, "Bahnhofstrasse 5", "3000", "Bern", "CH");

		final byte[] xml = exportXml(sepaExport);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		final String pstlAdr = "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf/Cdtr/PstlAdr";
		assertThat(text(doc, pstlAdr + "/StrtNm")).isEqualTo("Bahnhofstrasse");
		assertThat(text(doc, pstlAdr + "/BldgNb")).isEqualTo("5");
		assertThat(text(doc, pstlAdr + "/PstCd")).isEqualTo("3000");
		assertThat(text(doc, pstlAdr + "/TwnNm")).isEqualTo("Bern");
		assertThat(text(doc, pstlAdr + "/Ctry")).isEqualTo("CH");
	}

	@Test
	public void export_foreign_vendorWithoutTownOrCountry_failsWithPartnerName()
	{
		// payment type X (V2, cross-border): a CHF payment to a DE IBAN
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), chf, null);
		clearAddress(bankAccountOf(line));
		setBillToLocation(line, "Bahnhofstrasse 5", "3000", " ", "CH");

		// IG 3.11: TwnNm and Ctry must always be supplied
		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_ADDRESS_INCOMPLETE)
				.hasMessageContaining(PARTNER_TEST_CREDITOR)
				.hasMessageContaining(MSG_ADDRESS_OF_LOCATION + " - Test Billing Location")
				.hasMessageContaining(MSG_MISSING_TOWN);
	}

	@Test
	public void export_foreign_bankAccountAddressWithoutCity_failsWithPartnerName()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), chf, null);
		final I_C_BP_BankAccount bankAccount = bankAccountOf(line);
		bankAccount.setA_City(null);
		save(bankAccount);
		setBPartner(line, "Test Vendor GmbH");

		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_ADDRESS_INCOMPLETE)
				.hasMessageContaining(PARTNER_TEST_VENDOR)
				.hasMessageContaining(MSG_ADDRESS_OF_BANK_ACCOUNT + " - " + CREDITOR_IBAN_DE)
				.hasMessageContaining(MSG_MISSING_TOWN);
	}

	@Test
	public void export_foreign_vendorLocationWithoutCountry_failsWithPartnerName()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), chf, null);
		clearAddress(bankAccountOf(line));
		setBillToLocation(line, "Bahnhofstrasse 5", "3000", "Bern", " ");

		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_ADDRESS_INCOMPLETE)
				.hasMessageContaining(PARTNER_TEST_CREDITOR)
				.hasMessageContaining(MSG_ADDRESS_OF_LOCATION + " - Test Billing Location")
				.hasMessageContaining(MSG_MISSING_COUNTRY);
	}

	@Test
	public void export_foreign_bankAccountAddressWithoutCountry_failsWithPartnerName()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), chf, null);
		final I_C_BP_BankAccount bankAccount = bankAccountOf(line);
		bankAccount.setA_Country(null);
		save(bankAccount);
		setBPartner(line, "Test Vendor GmbH");

		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_ADDRESS_INCOMPLETE)
				.hasMessageContaining(PARTNER_TEST_VENDOR)
				.hasMessageContaining(MSG_ADDRESS_OF_BANK_ACCOUNT + " - " + CREDITOR_IBAN_DE)
				.hasMessageContaining(MSG_MISSING_COUNTRY);
	}

	@Test
	public void export_foreign_bankAccountAddressWithoutTownAndCountry_failsWithPartnerName()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), chf, null);
		final I_C_BP_BankAccount bankAccount = bankAccountOf(line);
		bankAccount.setA_City(null);
		bankAccount.setA_Country(null);
		save(bankAccount);
		setBPartner(line, "Test Vendor GmbH");

		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_ADDRESS_INCOMPLETE)
				.hasMessageContaining(PARTNER_TEST_VENDOR)
				.hasMessageContaining(MSG_ADDRESS_OF_BANK_ACCOUNT + " - " + CREDITOR_IBAN_DE)
				.hasMessageContaining(MSG_MISSING_TOWN_AND_COUNTRY);
	}

	@Test
	public void export_foreignCurrencyDomestic_vendorWithoutTown_failsWithPartnerName()
	{
		// payment type X (V1, foreign currency domestic): a USD payment to a CH IBAN
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_CH, null, new BigDecimal("100"), usd, null);
		clearAddress(bankAccountOf(line));
		setBillToLocation(line, "Bahnhofstrasse 5", "3000", " ", "CH");

		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_ADDRESS_INCOMPLETE)
				.hasMessageContaining(PARTNER_TEST_CREDITOR)
				.hasMessageContaining(MSG_ADDRESS_OF_LOCATION + " - Test Billing Location")
				.hasMessageContaining(MSG_MISSING_TOWN);
	}

	@Test
	public void createDocument_domestic_billToLocationWithoutTown_omitsAddressAndWarns() throws Exception
	{
		// payment type D; IG 3.11: an address without TwnNm is not allowed, but Cdtr/PstlAdr itself is optional (IG 4.3)
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_CH, null, new BigDecimal("100"), chf, null);
		clearAddress(bankAccountOf(line));
		setBillToLocation(line, "Bahnhofstrasse 5", "3000", " ", "CH");

		final PlainStringLoggable warnings = Loggables.newPlainStringLoggable();
		final SEPACreditTransferXML result = exportXml(sepaExport, warnings);
		final byte[] xml = result.getContent();

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf/PmtTpInf")).isZero(); // payment type D
		assertThat(text(doc, "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf/Cdtr/Nm")).isEqualTo("Test Creditor AG");
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf/Cdtr/PstlAdr")).isZero();
		assertThat(singleWarning(result, warnings))
				.contains(MSG_ADDRESS_OMITTED)
				.contains(PARTNER_TEST_CREDITOR)
				.contains(MSG_ADDRESS_OF_LOCATION + " - Test Billing Location")
				.contains(MSG_MISSING_TOWN);
	}

	@Test
	public void createDocument_sepa_billToLocationWithoutCountry_omitsAddressAndWarns() throws Exception
	{
		// payment type S
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), eur, null);
		clearAddress(bankAccountOf(line));
		setBillToLocation(line, "Bahnhofstrasse 5", "3000", "Bern", " ");

		final PlainStringLoggable warnings = Loggables.newPlainStringLoggable();
		final SEPACreditTransferXML result = exportXml(sepaExport, warnings);
		final byte[] xml = result.getContent();

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		assertThat(text(doc, "Document/CstmrCdtTrfInitn/PmtInf/PmtTpInf/SvcLvl/Cd")).isEqualTo("SEPA");
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf/Cdtr/PstlAdr")).isZero();
		assertThat(singleWarning(result, warnings))
				.contains(MSG_ADDRESS_OMITTED)
				.contains(PARTNER_TEST_CREDITOR)
				.contains(MSG_ADDRESS_OF_LOCATION + " - Test Billing Location")
				.contains(MSG_MISSING_COUNTRY);
	}

	@Test
	public void createDocument_domestic_bankAccountAddressWithoutCity_omitsAddressAndWarns() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_CH, null, new BigDecimal("100"), chf, null);
		final I_C_BP_BankAccount bankAccount = bankAccountOf(line);
		bankAccount.setA_City(null);
		save(bankAccount);
		setBPartner(line, "Test Vendor GmbH");

		final PlainStringLoggable warnings = Loggables.newPlainStringLoggable();
		final SEPACreditTransferXML result = exportXml(sepaExport, warnings);
		final byte[] xml = result.getContent();

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf/Cdtr/PstlAdr")).isZero();
		assertThat(singleWarning(result, warnings))
				.contains(MSG_ADDRESS_OMITTED)
				.contains(PARTNER_TEST_VENDOR)
				.contains(MSG_ADDRESS_OF_BANK_ACCOUNT + " - " + CREDITOR_IBAN_CH)
				.contains(MSG_MISSING_TOWN);
	}

	@Test
	public void createDocument_sepa_bankAccountAddressWithoutTownAndCountry_omitsAddressAndWarns() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), eur, null);
		final I_C_BP_BankAccount bankAccount = bankAccountOf(line);
		bankAccount.setA_City(null);
		bankAccount.setA_Country(null);
		save(bankAccount);
		setBPartner(line, "Test Vendor GmbH");

		final PlainStringLoggable warnings = Loggables.newPlainStringLoggable();
		final SEPACreditTransferXML result = exportXml(sepaExport, warnings);
		final byte[] xml = result.getContent();

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf/Cdtr/PstlAdr")).isZero();
		assertThat(singleWarning(result, warnings))
				.contains(MSG_ADDRESS_OMITTED)
				.contains(PARTNER_TEST_VENDOR)
				.contains(MSG_ADDRESS_OF_BANK_ACCOUNT + " - " + CREDITOR_IBAN_DE)
				.contains(MSG_MISSING_TOWN_AND_COUNTRY);
	}

	@Test
	public void createDocument_qrIban_bankAccountAddressWithoutCity_omitsAddressAndWarns() throws Exception
	{
		// payment type D via the QR-IBAN branch of the payment type detection
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createQrIbanLine(sepaExport, null, new BigDecimal("100"), chf, QR_REFERENCE);
		final I_C_BP_BankAccount bankAccount = bankAccountOf(line);
		bankAccount.setA_City(null);
		save(bankAccount);
		setBPartner(line, "Test Vendor GmbH");

		final PlainStringLoggable warnings = Loggables.newPlainStringLoggable();
		final SEPACreditTransferXML result = exportXml(sepaExport, warnings);
		final byte[] xml = result.getContent();

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		final String cdtTrfTxInf = "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf";
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf/PmtTpInf")).isZero(); // payment type D
		assertThat(text(doc, cdtTrfTxInf + "/RmtInf/Strd/CdtrRefInf/Tp/CdOrPrtry/Prtry")).isEqualTo("QRR");
		assertThat(count(doc, cdtTrfTxInf + "/Cdtr/PstlAdr")).isZero();
		assertThat(singleWarning(result, warnings))
				.contains(MSG_ADDRESS_OMITTED)
				.contains(PARTNER_TEST_VENDOR)
				.contains(MSG_ADDRESS_OF_BANK_ACCOUNT + " - " + CREDITOR_QR_IBAN)
				.contains(MSG_MISSING_TOWN);
	}

	@Test
	public void createDocument_domestic_bankAccountAddressWithoutCountry_omitsAddressAndWarns() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_CH, null, new BigDecimal("100"), chf, null);
		final I_C_BP_BankAccount bankAccount = bankAccountOf(line);
		bankAccount.setA_Country(null);
		save(bankAccount);
		setBPartner(line, "Test Vendor GmbH");

		final PlainStringLoggable warnings = Loggables.newPlainStringLoggable();
		final SEPACreditTransferXML result = exportXml(sepaExport, warnings);
		final byte[] xml = result.getContent();

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf/PmtTpInf")).isZero(); // payment type D
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf/Cdtr/PstlAdr")).isZero();
		assertThat(singleWarning(result, warnings))
				.contains(MSG_ADDRESS_OMITTED)
				.contains(PARTNER_TEST_VENDOR)
				.contains(MSG_ADDRESS_OF_BANK_ACCOUNT + " - " + CREDITOR_IBAN_CH)
				.contains(MSG_MISSING_COUNTRY);
	}

	@Test
	public void createDocument_sepa_bankAccountAddressWithoutCountry_omitsAddressAndWarns() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), eur, null);
		final I_C_BP_BankAccount bankAccount = bankAccountOf(line);
		bankAccount.setA_Country(null);
		save(bankAccount);
		setBPartner(line, "Test Vendor GmbH");

		final PlainStringLoggable warnings = Loggables.newPlainStringLoggable();
		final SEPACreditTransferXML result = exportXml(sepaExport, warnings);
		final byte[] xml = result.getContent();

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		assertThat(text(doc, "Document/CstmrCdtTrfInitn/PmtInf/PmtTpInf/SvcLvl/Cd")).isEqualTo("SEPA");
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf/Cdtr/PstlAdr")).isZero();
		assertThat(singleWarning(result, warnings))
				.contains(MSG_ADDRESS_OMITTED)
				.contains(PARTNER_TEST_VENDOR)
				.contains(MSG_ADDRESS_OF_BANK_ACCOUNT + " - " + CREDITOR_IBAN_DE)
				.contains(MSG_MISSING_COUNTRY);
	}

	@Test
	public void createDocument_twoPartnersWithTheSameIncompleteAddress_warnsForEach() throws Exception
	{
		// the same missing part and the same location name, but two partners: the user must be told about both
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line1 = createSEPAExportLine(sepaExport, CREDITOR_IBAN_CH, null, new BigDecimal("100"), chf, "Invoice 1");
		clearAddress(bankAccountOf(line1));
		setBillToLocation(line1, "Bahnhofstrasse 5", "3000", " ", "CH");
		final I_SEPA_Export_Line line2 = createSEPAExportLine(sepaExport, CREDITOR_IBAN_CH, null, new BigDecimal("30"), chf, "Invoice 2");
		clearAddress(bankAccountOf(line2));
		setBillToLocation(line2, "Bahnhofstrasse 7", "3000", " ", "CH");
		final I_C_BPartner otherPartner = InterfaceWrapperHelper.load(line2.getC_BPartner_ID(), I_C_BPartner.class);
		otherPartner.setValue("OtherCreditor");
		otherPartner.setName("Other Creditor AG");
		save(otherPartner);

		final PlainStringLoggable warnings = Loggables.newPlainStringLoggable();
		final SEPACreditTransferXML result = exportXml(sepaExport, warnings);
		final byte[] xml = result.getContent();

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf/Cdtr/PstlAdr")).isZero();
		assertThat(result.getWarnings()).hasSize(2);
		assertThat(warnings.getSingleMessages())
				.hasSize(2)
				.anySatisfy(warning -> assertThat(warning).contains(PARTNER_TEST_CREDITOR))
				.anySatisfy(warning -> assertThat(warning).contains("OtherCreditor (Other Creditor AG)"));
	}

	@Test
	public void createDocument_samePartnerAndIncompleteAddressOnTwoLines_warnsOnce() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line1 = createSEPAExportLine(sepaExport, CREDITOR_IBAN_CH, null, new BigDecimal("100"), chf, "Invoice 1");
		clearAddress(bankAccountOf(line1));
		setBillToLocation(line1, "Bahnhofstrasse 5", "3000", " ", "CH");
		final I_SEPA_Export_Line line2 = createSEPAExportLine(sepaExport, CREDITOR_IBAN_CH, null, new BigDecimal("30"), chf, "Invoice 2");
		clearAddress(bankAccountOf(line2));
		line2.setC_BPartner_ID(line1.getC_BPartner_ID());
		save(line2);

		final PlainStringLoggable warnings = Loggables.newPlainStringLoggable();
		final SEPACreditTransferXML result = exportXml(sepaExport, warnings);
		final byte[] xml = result.getContent();

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf")).isEqualTo(2);
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf/Cdtr/PstlAdr")).isZero();
		assertThat(warnings.getSingleMessages()).hasSize(1);
		assertThat(result.getWarnings()).hasSize(1);
	}

	@Test
	public void createDocument_vendorWithoutAnyAddress_omitsAddressWithoutWarning() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_CH, null, new BigDecimal("100"), chf, null);
		clearAddress(bankAccountOf(line));
		setBPartner(line, "Test Vendor GmbH"); // no bill-to location

		final PlainStringLoggable warnings = Loggables.newPlainStringLoggable();
		final SEPACreditTransferXML result = exportXml(sepaExport, warnings);
		final byte[] xml = result.getContent();

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf/Cdtr/PstlAdr")).isZero();
		assertThat(warnings.isEmpty()).isTrue();
		assertThat(result.getWarnings()).isEmpty();
	}

	@Test
	public void export_blankDebtorName_fails()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		sepaExport.setSEPA_CreditorName("  ");
		save(sepaExport);
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), eur, null);

		// the Dbtr would be an empty group element (IG 3.4, IG 4.2 Dbtr 1..1)
		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_DEBTOR_NAME_MISSING)
				.hasMessageContaining("1000001");
	}

	@Test
	public void export_headerError_userMessageHasNoTechnicalParameters()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		sepaExport.setSEPA_CreditorName("  ");
		save(sepaExport);
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), eur, null);

		// the user sees the translated message only; the export is the exception's record, which AD_Issue stores as AD_Table_ID/Record_ID
		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_DEBTOR_NAME_MISSING)
				.hasMessageNotContaining("Additional parameters")
				.hasMessageNotContaining("POJOWrapper")
				.isInstanceOfSatisfying(AdempiereException.class, e -> assertThat(e.getRecord()).isEqualTo(TableRecordReference.of(sepaExport)));
	}

	@Test
	public void export_lineError_userMessageHasNoTechnicalParameters()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_TR, CREDITOR_BIC_TR, new BigDecimal("100"), eur, null);
		clearAddress(bankAccountOf(line));
		setBillToLocation(line, "Teststrasse 1", "8000", "", "TR");

		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_ADDRESS_INCOMPLETE)
				.hasMessageNotContaining("Additional parameters")
				.hasMessageNotContaining("POJOWrapper")
				.isInstanceOfSatisfying(AdempiereException.class, e -> assertThat(e.getRecord()).isEqualTo(TableRecordReference.of(line)));
	}

	@Test
	public void export_foreign_locationNameWithAtSigns_keptInError()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_TR, CREDITOR_BIC_TR, new BigDecimal("100"), eur, null);
		clearAddress(bankAccountOf(line));
		final I_C_BPartner_Location bpLocation = setBillToLocation(line, "Teststrasse 1", "8000", "", "TR");
		bpLocation.setName("info@muster.ch / buchhaltung@muster.ch");
		save(bpLocation);

		// the process runs the error message through parseTranslation, which takes text between two "@" for an AD_Message/AD_Element; "@@" stands for a plain "@"
		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_ADDRESS_INCOMPLETE)
				.hasMessageContaining("info@@muster.ch / buchhaltung@@muster.ch")
				.satisfies(e -> assertThat(Msg.parseTranslation("de_CH", e.getMessage())).contains("info@muster.ch / buchhaltung@muster.ch"));
	}

	@Test
	public void export_blankCreditorName_fails()
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), eur, null);
		final I_C_BP_BankAccount bankAccount = bankAccountOf(line);
		bankAccount.setA_Name(" ");
		save(bankAccount);
		setBPartner(line, " ");

		// IG 4.3 Cdtr/Nm: "Must be supplied"
		assertThatThrownBy(() -> exportXml(sepaExport))
				.hasMessageContaining(MSG_CREDITOR_NAME_MISSING)
				.hasMessageContaining("C_BP_BankAccount_ID"); // the line's bank account, as SepaUtils.createInfo names it (its column name in unit tests)
	}

	@Test
	public void createDocument_interleavedPaymentTypes_instrIdBelongsToItsPmtInf() throws Exception
	{
		// lines are processed ordered by currency and ID, so interleave the payment types within one currency: D, S, D
		final I_SEPA_Export sepaExport = createSEPAExport();
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_CH, null, new BigDecimal("10"), eur, null);
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("20"), eur, null);
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_CH, null, new BigDecimal("30"), eur, null);

		final byte[] xml = exportXml(sepaExport);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		assertThat(count(doc, "Document/CstmrCdtTrfInitn/PmtInf")).isEqualTo(2);
		for (final String pmtInfId : texts(doc, "Document/CstmrCdtTrfInitn/PmtInf/PmtInfId"))
		{
			final String pmtInfNo = pmtInfId.substring("PMTINF-".length());
			final List<String> instrIds = texts(doc, "Document/CstmrCdtTrfInitn/PmtInf[PmtInfId='" + pmtInfId + "']/CdtTrfTxInf/PmtId/InstrId");
			assertThat(instrIds).isNotEmpty();
			assertThat(instrIds).allSatisfy(instrId -> assertThat(instrId).startsWith("INSTRID-" + pmtInfNo + "-"));
			assertThat(instrIds).doesNotHaveDuplicates();
		}
	}

	@Test
	public void createDocument_softwareInformation() throws Exception
	{
		final I_SEPA_Export sepaExport = createSEPAExport();
		createSEPAExportLine(sepaExport, CREDITOR_IBAN_DE, CREDITOR_BIC_DE, new BigDecimal("100"), eur, null);

		final Document doc = parse(exportXml(sepaExport));

		final String othr = "Document/CstmrCdtTrfInitn/GrpHdr/InitgPty/CtctDtls/Othr";
		assertThat(text(doc, othr + "[ChanlTp='NAME']/Id")).isEqualTo("metasfresh");
		assertThat(text(doc, othr + "[ChanlTp='SPSV']/Id")).isEqualTo("0230"); // IG 3.9: implemented IG version 2.3
		assertThat(count(doc, othr)).isLessThanOrEqualTo(4); // IG 4.1: max. 4 instances
	}

	private void assertStructuredCreditorAddress(@NonNull final Document doc, @NonNull final String cdtTrfTxInf) throws Exception
	{
		assertThat(text(doc, cdtTrfTxInf + "/Cdtr/PstlAdr/StrtNm")).isEqualTo("Teststrasse");
		assertThat(text(doc, cdtTrfTxInf + "/Cdtr/PstlAdr/BldgNb")).isEqualTo("1");
		assertThat(text(doc, cdtTrfTxInf + "/Cdtr/PstlAdr/PstCd")).isEqualTo("8000");
		assertThat(text(doc, cdtTrfTxInf + "/Cdtr/PstlAdr/TwnNm")).isEqualTo("Zuerich");
		assertThat(text(doc, cdtTrfTxInf + "/Cdtr/PstlAdr/Ctry")).isEqualTo("CH");
	}

	private I_SEPA_Export createSEPAExport()
	{
		final I_SEPA_Export sepaExport = newInstance(I_SEPA_Export.class);
		sepaExport.setSEPA_Protocol(SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_09_CH_03.getCode());
		sepaExport.setDocumentNo("1000001");
		sepaExport.setSEPA_CreditorName("Test Debtor AG");
		sepaExport.setSEPA_CreditorIdentifier("12345");
		sepaExport.setIBAN(DEBTOR_IBAN);
		sepaExport.setSwiftCode(DEBTOR_BIC);
		sepaExport.setPaymentDate(Timestamp.valueOf("2026-10-20 00:00:00"));
		sepaExport.setIsExportBatchBookings(true);
		save(sepaExport);
		return sepaExport;
	}

	private I_SEPA_Export_Line createSEPAExportLine(
			@NonNull final I_SEPA_Export sepaExport,
			@NonNull final String iban,
			@Nullable final String swiftCode,
			@NonNull final BigDecimal amt,
			@NonNull final CurrencyId currencyId,
			@Nullable final String description)
	{
		final Bank bank = bankRepository.createBank(BankCreateRequest.builder()
				.bankName("Test Bank")
				.routingNo("routingNo")
				.build());

		final I_C_BP_BankAccount bankAccount = newInstance(I_C_BP_BankAccount.class);
		bankAccount.setC_Bank_ID(bank.getBankId().getRepoId());
		bankAccount.setC_Currency_ID(currencyId.getRepoId());
		bankAccount.setIBAN(iban);
		bankAccount.setIsEsrAccount(false);
		bankAccount.setA_Name("Test Creditor AG");
		bankAccount.setA_Street("Teststrasse 1");
		bankAccount.setA_Zip("8000");
		bankAccount.setA_City("Zuerich");
		bankAccount.setA_Country("CH");
		save(bankAccount);

		final I_SEPA_Export_Line line = newInstance(I_SEPA_Export_Line.class);
		line.setIBAN(iban);
		line.setSwiftCode(swiftCode);
		line.setAmt(amt);
		line.setC_Currency_ID(currencyId.getRepoId());
		line.setDescription(description);
		line.setC_BP_BankAccount_ID(bankAccount.getC_BP_BankAccount_ID());
		line.setSEPA_Export(sepaExport);
		line.setIsActive(true);
		line.setIsError(false);
		save(line);
		return line;
	}

	/**
	 * Like the .03 test's createSEPAExportLineQRVersion: the bank account has a QR-IBAN, and the export line's IBAN is what the export process selects (the IBAN if any, else the QR-IBAN).
	 */
	private I_SEPA_Export_Line createQrIbanLine(
			@NonNull final I_SEPA_Export sepaExport,
			@Nullable final String iban,
			@NonNull final BigDecimal amt,
			@NonNull final CurrencyId currencyId,
			@NonNull final String reference)
	{
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, iban != null ? iban : CREDITOR_QR_IBAN, null, amt, currencyId, null);
		line.setStructuredRemittanceInfo(reference);
		save(line);

		final I_C_BP_BankAccount bankAccount = bankAccountOf(line);
		bankAccount.setIBAN(iban);
		bankAccount.setQR_IBAN(CREDITOR_QR_IBAN);
		bankAccount.setIsEsrAccount(true);
		save(bankAccount);
		return line;
	}

	/**
	 * Like the .03 test's createSEPAExportLine: ESR-flagged bank account and a structured reference.
	 */
	private I_SEPA_Export_Line createEsrFlaggedLine(
			@NonNull final I_SEPA_Export sepaExport,
			@NonNull final String iban,
			@Nullable final String swiftCode,
			@NonNull final BigDecimal amt,
			@NonNull final CurrencyId currencyId,
			@NonNull final String structuredRemittanceInfo)
	{
		final I_SEPA_Export_Line line = createSEPAExportLine(sepaExport, iban, swiftCode, amt, currencyId, null);
		line.setStructuredRemittanceInfo(structuredRemittanceInfo);
		save(line);

		final I_C_BP_BankAccount bankAccount = bankAccountOf(line);
		bankAccount.setIsEsrAccount(true);
		save(bankAccount);
		return line;
	}

	private static byte[] exportXml(@NonNull final I_SEPA_Export sepaExport)
	{
		return exportXml(sepaExport, false);
	}

	private static byte[] exportXml(@NonNull final I_SEPA_Export sepaExport, final boolean referenceAsEndToEndId)
	{
		final SEPAExportContext exportContext = SEPAExportContext.builder().referenceAsEndToEndId(referenceAsEndToEndId).build();
		return Services.get(ISEPADocumentBL.class).exportCreditTransferXML(sepaExport, exportContext).getContent();
	}

	/**
	 * Exports with the given loggable as the thread's loggable, like the SEPA export process does (JavaProcess registers itself as the thread's loggable).
	 */
	private static SEPACreditTransferXML exportXml(@NonNull final I_SEPA_Export sepaExport, @NonNull final PlainStringLoggable processLog)
	{
		try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(processLog))
		{
			final SEPAExportContext exportContext = SEPAExportContext.builder().referenceAsEndToEndId(false).build();
			return Services.get(ISEPADocumentBL.class).exportCreditTransferXML(sepaExport, exportContext);
		}
	}

	/**
	 * @return the single warning of the export result (which the export process shows as its summary); it is also in the process log
	 */
	private static String singleWarning(@NonNull final SEPACreditTransferXML result, @NonNull final PlainStringLoggable processLog)
	{
		assertThat(result.getWarnings()).hasSize(1);
		final String warning = result.getWarnings().get(0).translate("de_CH");
		assertThat(processLog.getSingleMessages()).containsExactly(warning);
		return warning;
	}

	private static I_C_BP_BankAccount bankAccountOf(@NonNull final I_SEPA_Export_Line line)
	{
		return InterfaceWrapperHelper.load(line.getC_BP_BankAccount_ID(), I_C_BP_BankAccount.class);
	}

	private static void clearAddress(@NonNull final I_C_BP_BankAccount bankAccount)
	{
		bankAccount.setA_Street(null);
		bankAccount.setA_Zip(null);
		bankAccount.setA_City(null);
		bankAccount.setA_Country(null);
		save(bankAccount);
	}

	private static I_C_BPartner_Location setBillToLocation(
			@NonNull final I_SEPA_Export_Line line,
			@NonNull final String address1,
			@NonNull final String postal,
			@NonNull final String city,
			@NonNull final String countryCode)
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setCountryCode(countryCode);
		country.setName(countryCode);
		save(country);

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setAddress1(address1);
		location.setPostal(postal);
		location.setCity(city);
		location.setC_Country_ID(country.getC_Country_ID());
		save(location);

		final I_C_BPartner bpartner = setBPartner(line, "Test Creditor AG");

		final I_C_BPartner_Location bpLocation = newInstance(I_C_BPartner_Location.class);
		bpLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpLocation.setC_Location_ID(location.getC_Location_ID());
		bpLocation.setName("Test Billing Location");
		bpLocation.setIsBillTo(true);
		bpLocation.setIsBillToDefault(true);
		bpLocation.setIsActive(true);
		save(bpLocation);
		return bpLocation;
	}

	private static I_C_BPartner setBPartner(@NonNull final I_SEPA_Export_Line line, @NonNull final String name)
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setName(name);
		bpartner.setValue("TestCreditor");
		save(bpartner);

		line.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		save(line);
		return bpartner;
	}

	private static void setBankLocation(
			@NonNull final I_SEPA_Export_Line line,
			@NonNull final String address1,
			@NonNull final String postal,
			@NonNull final String city,
			@NonNull final String countryCode)
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setCountryCode(countryCode);
		country.setName(countryCode);
		save(country);

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setAddress1(address1);
		location.setPostal(postal);
		location.setCity(city);
		location.setC_Country_ID(country.getC_Country_ID());
		save(location);

		final I_C_Bank bank = InterfaceWrapperHelper.load(bankAccountOf(line).getC_Bank_ID(), I_C_Bank.class);
		bank.setC_Location_ID(location.getC_Location_ID());
		save(bank);
	}

	private static String repeat(@NonNull final String text, final int times)
	{
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < times; i++)
		{
			sb.append(text);
		}
		return sb.toString();
	}
}
