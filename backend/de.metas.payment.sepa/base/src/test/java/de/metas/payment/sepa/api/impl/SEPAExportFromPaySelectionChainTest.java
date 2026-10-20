package de.metas.payment.sepa.api.impl;

import de.metas.banking.Bank;
import de.metas.banking.BankCreateRequest;
import de.metas.banking.api.BankAccountAcctRepository;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.banking.payment.PaySelectionTrxType;
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
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static de.metas.payment.sepa.Pain001_001_09_XmlTestHelper.assertValidAgainstXsd;
import static de.metas.payment.sepa.Pain001_001_09_XmlTestHelper.parse;
import static de.metas.payment.sepa.Pain001_001_09_XmlTestHelper.readXsdTargetNamespace;
import static de.metas.payment.sepa.Pain001_001_09_XmlTestHelper.text;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Real-usage chain of the process {@code C_PaySelection_SEPA_XmlExport}: a pay selection with a purchase invoice line is turned into a SEPA export
 * ({@link ISEPADocumentBL#createSEPAExportFromPaySelection}) and then marshalled ({@link ISEPADocumentBL#exportCreditTransferXML}), with the protocol the user chose in the process parameter.
 * <p>
 * IBANs/BICs and the QR reference are the public example values of the SIX "Swiss Implementation Guidelines pain.001" (chapter 5.1 / 5.2); names and addresses are dummies.
 */
public class SEPAExportFromPaySelectionChainTest
{
	private static final int ORG_ID = 1000000;

	private static final String NAMESPACE_PAIN_001_001_09 = "urn:iso:std:iso:20022:tech:xsd:pain.001.001.09";
	private static final String NAMESPACE_PAIN_001_001_03_CH_02 = "http://www.six-interbank-clearing.com/de/pain.001.001.03.ch.02.xsd";
	private static final String NAMESPACE_PAIN_008_003_02 = "urn:iso:std:iso:20022:tech:xsd:pain.008.003.02";

	private static final String DEBTOR_IBAN = "CH72 8000 5000 0888 7776 6";
	private static final String DEBTOR_BIC = "RAIFCH22005";
	private static final String CREDITOR_IBAN_CH = "CH50 2197 7000 0043 3134 6";
	private static final String CREDITOR_QR_IBAN = "CH44 3199 9123 0008 8901 2";
	private static final String QR_REFERENCE = "210000000003139471430009017";

	private BankRepository bankRepository;
	private CurrencyId chf;
	private I_C_BPartner vendor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		bankRepository = new BankRepository();
		SpringContextHolder.registerJUnitBean(bankRepository);
		SpringContextHolder.registerJUnitBean(new BankAccountService(bankRepository, new BankAccountAcctRepository(), new CurrencyRepository()));

		chf = PlainCurrencyDAO.createCurrencyId(CurrencyCode.CHF);

		final I_C_BPartner orgBP = newInstance(I_C_BPartner.class);
		orgBP.setName("Test Debtor AG");
		orgBP.setAD_OrgBP_ID(ORG_ID);
		save(orgBP);

		vendor = createVendorWithBillToLocation();
	}

	@Test
	void creditTransfer_noProtocolChosen_exportsPain001_001_09_ch_03() throws Exception
	{
		final I_C_PaySelection paySelection = createPaySelection(PaySelectionTrxType.CREDIT_TRANSFER);
		createPaySelectionLine(paySelection, createVendorBankAccount(CREDITOR_IBAN_CH, null), null);

		final byte[] xml = exportAsProcessDoes(paySelection, null);

		final String targetNamespace = readXsdTargetNamespace();
		assertThat(targetNamespace).isEqualTo(NAMESPACE_PAIN_001_001_09);
		assertThat(rootNamespace(xml)).isEqualTo(targetNamespace);
		assertValidAgainstXsd(xml);

		final Document doc = parse(xml);
		final String cdtTrfTxInf = "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf";
		assertThat(text(doc, cdtTrfTxInf + "/CdtrAcct/Id/IBAN")).isEqualTo("CH5021977000004331346");
		assertThat(text(doc, cdtTrfTxInf + "/Cdtr/Nm")).isEqualTo("Test Creditor AG");
		assertThat(text(doc, cdtTrfTxInf + "/Cdtr/PstlAdr/TwnNm")).isEqualTo("Zuerich");
		assertThat(text(doc, cdtTrfTxInf + "/RmtInf/Ustrd")).isEqualTo("Invoice 1001");
	}

	@Test
	void creditTransfer_pain001_001_03_ch_02Chosen_exportsPain001_001_03_ch_02()
	{
		final I_C_PaySelection paySelection = createPaySelection(PaySelectionTrxType.CREDIT_TRANSFER);
		createPaySelectionLine(paySelection, createVendorBankAccount(CREDITOR_IBAN_CH, null), null);

		final byte[] xml = exportAsProcessDoes(paySelection, SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02);

		assertThat(rootNamespace(xml)).isEqualTo(NAMESPACE_PAIN_001_001_03_CH_02);
	}

	@Test
	void directDebit_pain001_001_03_ch_02Chosen_exportsPain008_003_02()
	{
		final I_C_PaySelection paySelection = createPaySelection(PaySelectionTrxType.DIRECT_DEBIT);
		createPaySelectionLine(paySelection, createVendorBankAccount(CREDITOR_IBAN_CH, null), null);

		final byte[] xml = exportAsProcessDoes(paySelection, SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02);

		assertThat(rootNamespace(xml)).isEqualTo(NAMESPACE_PAIN_008_003_02);
	}

	/**
	 * The vendor's bank account has a QR-IBAN only, so the command puts the QR-IBAN into the export line; with the QR reference of the invoice, the .09 export sends a QRR reference.
	 */
	@Test
	void creditTransfer_qrIbanAccountWithQrReference_exportsQrr() throws Exception
	{
		final I_C_PaySelection paySelection = createPaySelection(PaySelectionTrxType.CREDIT_TRANSFER);
		createPaySelectionLine(paySelection, createVendorBankAccount(null, CREDITOR_QR_IBAN), QR_REFERENCE);

		final I_SEPA_Export sepaExport = createSEPAExport(paySelection, null);
		final List<I_SEPA_Export_Line> exportLines = Services.get(ISEPADocumentBL.class).retrieveLines(sepaExport);
		assertThat(exportLines).extracting(I_SEPA_Export_Line::getIBAN).containsExactly("CH4431999123000889012");
		assertThat(exportLines).extracting(I_SEPA_Export_Line::getStructuredRemittanceInfo).containsExactly(QR_REFERENCE);

		final byte[] xml = exportXml(sepaExport);

		assertValidAgainstXsd(xml);
		final Document doc = parse(xml);
		final String cdtTrfTxInf = "Document/CstmrCdtTrfInitn/PmtInf/CdtTrfTxInf";
		assertThat(text(doc, cdtTrfTxInf + "/CdtrAcct/Id/IBAN")).isEqualTo("CH4431999123000889012");
		final String cdtrRefInf = cdtTrfTxInf + "/RmtInf/Strd/CdtrRefInf";
		assertThat(text(doc, cdtrRefInf + "/Tp/CdOrPrtry/Prtry")).isEqualTo("QRR");
		assertThat(text(doc, cdtrRefInf + "/Ref")).isEqualTo(QR_REFERENCE);
	}

	/**
	 * Same two calls as the process {@code C_PaySelection_SEPA_XmlExport}.
	 */
	private static byte[] exportAsProcessDoes(@NonNull final I_C_PaySelection paySelection, @Nullable final SEPAProtocol chosenProtocol)
	{
		return exportXml(createSEPAExport(paySelection, chosenProtocol));
	}

	private static I_SEPA_Export createSEPAExport(@NonNull final I_C_PaySelection paySelection, @Nullable final SEPAProtocol chosenProtocol)
	{
		final I_SEPA_Export sepaExport = Services.get(ISEPADocumentBL.class).createSEPAExportFromPaySelection(paySelection, chosenProtocol);
		// in the application, the document number comes from the SEPA_Export document sequence when the record is first saved; the in-memory test DB has no sequences
		sepaExport.setDocumentNo("1000001");
		save(sepaExport);
		return sepaExport;
	}

	private static byte[] exportXml(@NonNull final I_SEPA_Export sepaExport)
	{
		final SEPAExportContext exportContext = SEPAExportContext.builder().referenceAsEndToEndId(false).build();
		return Services.get(ISEPADocumentBL.class).exportCreditTransferXML(sepaExport, exportContext).getContent();
	}

	private I_C_PaySelection createPaySelection(@NonNull final PaySelectionTrxType trxType)
	{
		final Bank debtorBank = bankRepository.createBank(BankCreateRequest.builder()
				.bankName("Debtor Bank")
				.routingNo("routingNo")
				.swiftCode(DEBTOR_BIC)
				.build());

		final I_C_BP_BankAccount debtorAccount = newInstance(I_C_BP_BankAccount.class);
		debtorAccount.setC_Bank_ID(debtorBank.getBankId().getRepoId());
		debtorAccount.setC_Currency_ID(chf.getRepoId());
		debtorAccount.setIBAN(DEBTOR_IBAN);
		debtorAccount.setSEPA_CreditorIdentifier("CH51ZZZ12345678901"); // needed for direct debit only
		debtorAccount.setA_Name("Test Debtor AG");
		save(debtorAccount);

		final I_C_PaySelection paySelection = newInstance(I_C_PaySelection.class);
		paySelection.setAD_Org_ID(ORG_ID);
		paySelection.setPaySelectionTrxType(trxType.getCode());
		paySelection.setC_BP_BankAccount_ID(debtorAccount.getC_BP_BankAccount_ID());
		paySelection.setPayDate(Timestamp.valueOf("2026-10-20 00:00:00"));
		paySelection.setIsExportBatchBookings(true);
		save(paySelection);
		return paySelection;
	}

	/**
	 * Vendor bank account without address data, so the export uses the vendor's bill-to location.
	 */
	private I_C_BP_BankAccount createVendorBankAccount(@Nullable final String iban, @Nullable final String qrIban)
	{
		final Bank bank = bankRepository.createBank(BankCreateRequest.builder()
				.bankName("Creditor Bank")
				.routingNo("routingNo")
				.build());

		final I_C_BP_BankAccount bankAccount = newInstance(I_C_BP_BankAccount.class);
		bankAccount.setC_BPartner_ID(vendor.getC_BPartner_ID());
		bankAccount.setC_Bank_ID(bank.getBankId().getRepoId());
		bankAccount.setC_Currency_ID(chf.getRepoId());
		bankAccount.setIBAN(iban);
		bankAccount.setQR_IBAN(qrIban);
		bankAccount.setIsEsrAccount(qrIban != null);
		save(bankAccount);
		return bankAccount;
	}

	private void createPaySelectionLine(
			@NonNull final I_C_PaySelection paySelection,
			@NonNull final I_C_BP_BankAccount vendorBankAccount,
			@Nullable final String reference)
	{
		final I_C_Invoice purchaseInvoice = newInstance(I_C_Invoice.class);
		purchaseInvoice.setIsSOTrx(false);
		purchaseInvoice.setC_BPartner_ID(vendor.getC_BPartner_ID());
		purchaseInvoice.setC_Currency_ID(chf.getRepoId());
		purchaseInvoice.setDocumentNo("1001");
		purchaseInvoice.setDescription("Invoice 1001");
		save(purchaseInvoice);

		final I_C_PaySelectionLine line = newInstance(I_C_PaySelectionLine.class);
		line.setC_PaySelection_ID(paySelection.getC_PaySelection_ID());
		line.setAD_Org_ID(ORG_ID);
		line.setLine(10);
		line.setC_Invoice_ID(purchaseInvoice.getC_Invoice_ID());
		line.setC_BPartner_ID(vendor.getC_BPartner_ID());
		line.setC_BP_BankAccount_ID(vendorBankAccount.getC_BP_BankAccount_ID());
		line.setPayAmt(new BigDecimal("100"));
		line.setReference(reference);
		save(line);
	}

	private static I_C_BPartner createVendorWithBillToLocation()
	{
		final I_C_BPartner vendor = newInstance(I_C_BPartner.class);
		vendor.setName("Test Creditor AG");
		vendor.setValue("TestCreditor");
		save(vendor);

		final I_C_Country country = newInstance(I_C_Country.class);
		country.setCountryCode("CH");
		country.setName("Switzerland");
		save(country);

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setAddress1("Teststrasse 1");
		location.setPostal("8000");
		location.setCity("Zuerich");
		location.setC_Country_ID(country.getC_Country_ID());
		save(location);

		final I_C_BPartner_Location bpLocation = newInstance(I_C_BPartner_Location.class);
		bpLocation.setC_BPartner_ID(vendor.getC_BPartner_ID());
		bpLocation.setC_Location_ID(location.getC_Location_ID());
		bpLocation.setName("Test Billing Location");
		bpLocation.setIsBillTo(true);
		bpLocation.setIsBillToDefault(true);
		bpLocation.setIsActive(true);
		save(bpLocation);
		return vendor;
	}

	private static String rootNamespace(final byte[] xml)
	{
		try
		{
			return parse(xml).getDocumentElement().getNamespaceURI();
		}
		catch (final Exception e)
		{
			throw new IllegalStateException("Could not parse the exported XML", e);
		}
	}

}
