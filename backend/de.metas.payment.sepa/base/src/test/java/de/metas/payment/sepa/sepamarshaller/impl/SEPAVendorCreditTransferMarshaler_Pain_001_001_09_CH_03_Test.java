package de.metas.payment.sepa.sepamarshaller.impl;

import de.metas.banking.Bank;
import de.metas.banking.BankCreateRequest;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.sepa.api.ISEPADocumentBL;
import de.metas.payment.sepa.api.SEPAExportContext;
import de.metas.payment.sepa.api.SEPAProtocol;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.annotation.Nullable;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Exercises the pain.001.001.09.ch.03 credit transfer export through {@link ISEPADocumentBL#exportCreditTransferXML}, i.e. the same path the SEPA export process uses.
 * <p>
 * IBANs/BICs are the public example values of the SIX "Swiss Implementation Guidelines pain.001" (chapter 5.2) resp. the well-known ING test IBAN; names and addresses are dummies.
 */
public class SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03_Test
{
	private static final String XSD_RESOURCE = "/pain.001.001.09.ch.03.xsd";

	private static final String DEBTOR_IBAN = "CH72 8000 5000 0888 7776 6";
	private static final String DEBTOR_BIC = "RAIFCH22005";

	private static final String CREDITOR_IBAN_CH = "CH50 2197 7000 0043 3134 6";
	private static final String CREDITOR_IID_FROM_IBAN_CH = "21977";
	private static final String CREDITOR_IBAN_DE = "DE62007620110623852957";
	private static final String CREDITOR_BIC_DE = "UBSWDEFF";
	private static final String CREDITOR_IBAN_NL = "NL31INGB0000000044";

	private BankRepository bankRepository;

	private CurrencyId eur;
	private CurrencyId chf;

	@BeforeEach
	public void beforeTest()
	{
		AdempiereTestHelper.get().init();

		bankRepository = new BankRepository();
		SpringContextHolder.registerJUnitBean(bankRepository);
		SpringContextHolder.registerJUnitBean(new BankAccountService(bankRepository, new CurrencyRepository()));

		eur = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		chf = PlainCurrencyDAO.createCurrencyId(CurrencyCode.CHF);
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
		bankAccount.setSwiftCode(swiftCode);
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

	private static byte[] exportXml(@NonNull final I_SEPA_Export sepaExport)
	{
		final SEPAExportContext exportContext = SEPAExportContext.builder().referenceAsEndToEndId(false).build();
		return Services.get(ISEPADocumentBL.class).exportCreditTransferXML(sepaExport, exportContext).getContent();
	}

	private static void assertValidAgainstXsd(final byte[] xml) throws Exception
	{
		final URL xsd = SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03_Test.class.getResource(XSD_RESOURCE);
		assertThat(xsd).as("XSD %s on the classpath", XSD_RESOURCE).isNotNull();

		SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
				.newSchema(xsd)
				.newValidator()
				.validate(new StreamSource(new ByteArrayInputStream(xml))); // throws SAXParseException with line/column if invalid
	}

	private static String readXsdTargetNamespace() throws Exception
	{
		try (final InputStream xsd = SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03_Test.class.getResourceAsStream(XSD_RESOURCE))
		{
			assertThat(xsd).as("XSD %s on the classpath", XSD_RESOURCE).isNotNull();
			final String targetNamespace = newDocumentBuilderFactory().newDocumentBuilder().parse(xsd).getDocumentElement().getAttribute("targetNamespace");
			assertThat(targetNamespace).isNotBlank();
			return targetNamespace;
		}
	}

	private static Document parse(final byte[] xml) throws Exception
	{
		return newDocumentBuilderFactory().newDocumentBuilder().parse(new ByteArrayInputStream(xml));
	}

	private static DocumentBuilderFactory newDocumentBuilderFactory()
	{
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		return factory;
	}

	/**
	 * @return text of the first node matching the given namespace-agnostic path, e.g. {@code Document/CstmrCdtTrfInitn/GrpHdr/NbOfTxs}
	 */
	private static String text(final Document doc, final String path) throws Exception
	{
		assertThat(count(doc, path)).as("nodes at %s", path).isPositive();
		return XPathFactory.newInstance().newXPath().evaluate(toXPath(path), doc);
	}

	private static int count(final Document doc, final String path) throws Exception
	{
		final NodeList nodes = (NodeList)XPathFactory.newInstance().newXPath().evaluate(toXPath(path), doc, XPathConstants.NODESET);
		return nodes.getLength();
	}

	/**
	 * Turns {@code A/B[C/@x='y']/D} into a namespace-agnostic XPath ({@code /*[local-name()='A']/...}); predicates are converted the same way.
	 */
	private static String toXPath(final String path)
	{
		return "/" + toRelativeXPath(path);
	}

	private static String toRelativeXPath(final String path)
	{
		return Arrays.stream(path.split("/(?![^\\[]*\\])"))
				.map(SEPAVendorCreditTransferMarshaler_Pain_001_001_09_CH_03_Test::toXPathStep)
				.collect(Collectors.joining("/"));
	}

	private static String toXPathStep(final String step)
	{
		if (step.startsWith("@"))
		{
			return step;
		}
		final int predicateStart = step.indexOf('[');
		if (predicateStart < 0)
		{
			return "*[local-name()='" + step + "']";
		}
		final String name = step.substring(0, predicateStart);
		final String predicate = step.substring(predicateStart + 1, step.length() - 1);
		final int eq = predicate.indexOf('=');
		final String predicatePath = eq < 0 ? predicate : predicate.substring(0, eq);
		final String predicateValue = eq < 0 ? "" : predicate.substring(eq);
		return "*[local-name()='" + name + "'][" + toRelativeXPath(predicatePath) + predicateValue + "]";
	}
}
