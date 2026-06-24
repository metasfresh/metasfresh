package de.metas.einvoice.cii;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipient;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientId;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipients;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientProvider;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientRegistry;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientRequest;
import de.metas.email.EMailAddress;
import de.metas.email.MailService;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.mailboxes.MailboxQuery;
import de.metas.email.mailboxes.MailboxRepository;
import de.metas.email.mailboxes.MailboxType;
import de.metas.email.mailboxes.SMTPConfig;
import de.metas.email.templates.MailTemplateRepository;
import de.metas.einvoice.EInvoiceFormat;
import de.metas.einvoice.EInvoiceRecipientConfig;
import de.metas.einvoice.cii.model.CrossIndustryInvoiceType;
import de.metas.einvoice.cii.model.ObjectFactory;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_InvoiceTax;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xmlunit.assertj.XmlAssert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CiiMapperTest
{
	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void header_parties() throws Exception
	{
		// === Seller (org BPartner) setup ===
		final I_C_Country sellerCountry = newInstance(I_C_Country.class);
		sellerCountry.setCountryCode("DE");
		saveRecord(sellerCountry);

		final I_C_Location sellerLocation = newInstance(I_C_Location.class);
		sellerLocation.setAddress1("Musterstraße 1");
		sellerLocation.setCity("Berlin");
		sellerLocation.setPostal("10115");
		sellerLocation.setC_Country_ID(sellerCountry.getC_Country_ID());
		saveRecord(sellerLocation);

		final I_C_BPartner sellerBPartner = newInstance(I_C_BPartner.class);
		sellerBPartner.setName("Muster GmbH");
		sellerBPartner.setVATaxID("DE123456789");   // BT-31 — Umsatzsteuer-ID (VAT identifier, scheme VA)
		sellerBPartner.setTaxID("Steuernr-0815");   // BT-32 — Steuernummer (tax registration, scheme FC)
		sellerBPartner.setEMail("invoice@muster.de");
		sellerBPartner.setCommercialRegisterNumber("HRB 12345");
		saveRecord(sellerBPartner);

		final I_C_BPartner_Location sellerBPLocation = newInstance(I_C_BPartner_Location.class);
		sellerBPLocation.setC_BPartner_ID(sellerBPartner.getC_BPartner_ID());
		sellerBPLocation.setC_Location_ID(sellerLocation.getC_Location_ID());
		sellerBPLocation.setIsBillTo(true);
		saveRecord(sellerBPLocation);

		// Org and OrgInfo setup
		final I_AD_Org org = newInstance(I_AD_Org.class);
		org.setValue("MUSTER");
		org.setName("Muster GmbH Org");
		saveRecord(org);

		// Mark as org BPartner: AD_OrgBP_ID links the BPartner to the org so retrieveOrgBPartner can find it
		sellerBPartner.setAD_OrgBP_ID(org.getAD_Org_ID());
		saveRecord(sellerBPartner);

		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(org.getAD_Org_ID());
		orgInfo.setOrg_BPartner_ID(sellerBPartner.getC_BPartner_ID());
		saveRecord(orgInfo);

		// === Buyer BPartner setup ===
		final I_C_Country buyerCountry = newInstance(I_C_Country.class);
		buyerCountry.setCountryCode("DE");
		saveRecord(buyerCountry);

		final I_C_Location buyerLocation = newInstance(I_C_Location.class);
		buyerLocation.setAddress1("Käuferweg 5");
		buyerLocation.setCity("Hamburg");
		buyerLocation.setPostal("20095");
		buyerLocation.setC_Country_ID(buyerCountry.getC_Country_ID());
		saveRecord(buyerLocation);

		final I_C_BPartner_Location buyerBPLocation = newInstance(I_C_BPartner_Location.class);
		buyerBPLocation.setC_Location_ID(buyerLocation.getC_Location_ID());
		buyerBPLocation.setIsBillTo(true);
		buyerBPLocation.setEMail("einkauf@buyer.de");
		saveRecord(buyerBPLocation);

		final I_C_BPartner buyerBPartner = newInstance(I_C_BPartner.class);
		buyerBPartner.setName("Käufer AG");
		buyerBPartner.setVATaxID("DE987654321");   // BT-48 — Buyer VAT identifier (scheme VA)
		saveRecord(buyerBPartner);

		buyerBPLocation.setC_BPartner_ID(buyerBPartner.getC_BPartner_ID());
		saveRecord(buyerBPLocation);

		// === Currency ===
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		saveRecord(currency);

		// === DocType (ARI = commercial invoice, code 380) ===
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-2024-00042");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBPartner.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLocation.getC_BPartner_Location_ID());
		invoice.setPOReference("PO-2024-999");
		invoice.setDueDate(Timestamp.from(LocalDate.of(2024, 7, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		saveRecord(invoice);

		// === EInvoiceRecipientConfig ===
		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.buyerReference("991-1234512345-06")
				.build();

		// === Map ===
		final CrossIndustryInvoiceType cii = new CiiMapper().map(invoice, recipientConfig);
		final XmlAssert xmlAssert = toXmlAssert(cii);

		// Guideline ID (ZUGFeRD / Factur-X EN 16931 profile)
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:ExchangedDocumentContext"
								+ "/ram:GuidelineSpecifiedDocumentContextParameter/ram:ID")
				.isEqualTo("urn:cen.eu:en16931:2017#compliant#urn:factur-x.eu:1p0:en16931");

		// BT-1 Invoice number
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:ExchangedDocument/ram:ID")
				.isEqualTo("RE-2024-00042");

		// BT-3 Type code (ARI → 380)
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:ExchangedDocument/ram:TypeCode")
				.isEqualTo("380");

		// BT-2 Issue date
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:ExchangedDocument/ram:IssueDateTime/udt:DateTimeString")
				.isEqualTo("20240615");

		// BT-5 Currency
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement/ram:InvoiceCurrencyCode")
				.isEqualTo("EUR");

		// BT-10 Buyer reference (Leitweg-ID)
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:BuyerReference")
				.isEqualTo("991-1234512345-06");

		// BT-13 Purchase order reference
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:BuyerOrderReferencedDocument/ram:IssuerAssignedID")
				.isEqualTo("PO-2024-999");

		// Seller name (BT-27)
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:Name")
				.isEqualTo("Muster GmbH");

		// Seller address line 1 (BT-35)
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:PostalTradeAddress/ram:LineOne")
				.isEqualTo("Musterstraße 1");

		// Seller city (BT-37)
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:PostalTradeAddress/ram:CityName")
				.isEqualTo("Berlin");

		// Seller country (BT-40)
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:PostalTradeAddress/ram:CountryID")
				.isEqualTo("DE");

		// Seller postal code (BT-38)
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:PostalTradeAddress/ram:PostcodeCode")
				.isEqualTo("10115");

		// Seller electronic address (BT-34, scheme EM)
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:URIUniversalCommunication/ram:URIID")
				.isEqualTo("invoice@muster.de");
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:URIUniversalCommunication/ram:URIID/@schemeID")
				.isEqualTo("EM");

		// Seller legal registration (BT-30)
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:SpecifiedLegalOrganization/ram:ID")
				.isEqualTo("HRB 12345");

		// Seller VAT id (BT-31) — from VATaxID, scheme VA
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:SpecifiedTaxRegistration[1]/ram:ID")
				.isEqualTo("DE123456789");
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:SpecifiedTaxRegistration[1]/ram:ID/@schemeID")
				.isEqualTo("VA");

		// Seller tax registration (BT-32) — from TaxID (Steuernummer), scheme FC
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:SpecifiedTaxRegistration[2]/ram:ID")
				.isEqualTo("Steuernr-0815");
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:SpecifiedTaxRegistration[2]/ram:ID/@schemeID")
				.isEqualTo("FC");

		// Buyer name (BT-44)
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:BuyerTradeParty/ram:Name")
				.isEqualTo("Käufer AG");

		// Buyer address line 1 (BT-50)
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:BuyerTradeParty/ram:PostalTradeAddress/ram:LineOne")
				.isEqualTo("Käuferweg 5");

		// Buyer city (BT-52)
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:BuyerTradeParty/ram:PostalTradeAddress/ram:CityName")
				.isEqualTo("Hamburg");

		// Buyer country (BT-55)
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:BuyerTradeParty/ram:PostalTradeAddress/ram:CountryID")
				.isEqualTo("DE");

		// Buyer postal code (BT-53)
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:BuyerTradeParty/ram:PostalTradeAddress/ram:PostcodeCode")
				.isEqualTo("20095");

		// Buyer electronic address (BT-49, scheme EM)
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:BuyerTradeParty/ram:URIUniversalCommunication/ram:URIID")
				.isEqualTo("einkauf@buyer.de");
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:BuyerTradeParty/ram:URIUniversalCommunication/ram:URIID/@schemeID")
				.isEqualTo("EM");

		// Buyer VAT id (BT-48) — value and scheme
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:BuyerTradeParty/ram:SpecifiedTaxRegistration[1]/ram:ID")
				.isEqualTo("DE987654321");
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:BuyerTradeParty/ram:SpecifiedTaxRegistration[1]/ram:ID/@schemeID")
				.isEqualTo("VA");

		// BT-9 Payment due date
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement"
								+ "/ram:SpecifiedTradePaymentTerms/ram:DueDateDateTime/udt:DateTimeString")
				.isEqualTo("20240715");
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement"
								+ "/ram:SpecifiedTradePaymentTerms/ram:DueDateDateTime/udt:DateTimeString/@format")
				.isEqualTo("102");
	}

	/**
	 * Verifies BT-3 credit note type code (381) and BT-25/BT-26 preceding invoice reference.
	 */
	@Test
	void credit_note_type_code_and_preceding_invoice_ref() throws Exception
	{
		// Minimal seller org — must have a location so EN16931 BG-5 fail-fast does not trigger
		final I_AD_Org org = newInstance(I_AD_Org.class);
		saveRecord(org);
		final I_C_Country sellerCountry = newInstance(I_C_Country.class);
		sellerCountry.setCountryCode("DE");
		saveRecord(sellerCountry);
		final I_C_Location sellerLocation = newInstance(I_C_Location.class);
		sellerLocation.setC_Country_ID(sellerCountry.getC_Country_ID());
		saveRecord(sellerLocation);
		final I_C_BPartner sellerBP = newInstance(I_C_BPartner.class);
		sellerBP.setName("Seller GmbH");
		sellerBP.setAD_OrgBP_ID(org.getAD_Org_ID());
		saveRecord(sellerBP);
		final I_C_BPartner_Location sellerBPLoc = newInstance(I_C_BPartner_Location.class);
		sellerBPLoc.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerBPLoc.setC_Location_ID(sellerLocation.getC_Location_ID());
		saveRecord(sellerBPLoc);
		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(org.getAD_Org_ID());
		orgInfo.setOrg_BPartner_ID(sellerBP.getC_BPartner_ID());
		saveRecord(orgInfo);

		// Minimal buyer
		final I_C_Country buyerCountry = newInstance(I_C_Country.class);
		buyerCountry.setCountryCode("DE");
		saveRecord(buyerCountry);
		final I_C_Location buyerLocation = newInstance(I_C_Location.class);
		buyerLocation.setC_Country_ID(buyerCountry.getC_Country_ID());
		saveRecord(buyerLocation);
		final I_C_BPartner buyerBP = newInstance(I_C_BPartner.class);
		buyerBP.setName("Buyer AG");
		saveRecord(buyerBP);
		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(buyerLocation.getC_Location_ID());
		saveRecord(buyerBPLoc);

		// Currency
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		saveRecord(currency);

		// Preceding (original) invoice — referenced by BT-25/BT-26
		final I_C_DocType ariDocType = newInstance(I_C_DocType.class);
		ariDocType.setDocBaseType("ARI");
		saveRecord(ariDocType);

		final I_C_Invoice origInvoice = newInstance(I_C_Invoice.class);
		origInvoice.setAD_Org_ID(org.getAD_Org_ID());
		origInvoice.setDocumentNo("RE-2024-00001");
		origInvoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant()));
		origInvoice.setC_Currency_ID(currency.getC_Currency_ID());
		origInvoice.setC_DocType_ID(ariDocType.getC_DocType_ID());
		origInvoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		origInvoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		saveRecord(origInvoice);

		// Credit note (ARC) referencing the preceding invoice
		final I_C_DocType arcDocType = newInstance(I_C_DocType.class);
		arcDocType.setDocBaseType("ARC");
		saveRecord(arcDocType);

		final I_C_Invoice creditNote = newInstance(I_C_Invoice.class);
		creditNote.setAD_Org_ID(org.getAD_Org_ID());
		creditNote.setDocumentNo("GS-2024-00099");
		creditNote.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 20).atStartOfDay(ZoneOffset.UTC).toInstant()));
		creditNote.setC_Currency_ID(currency.getC_Currency_ID());
		creditNote.setC_DocType_ID(arcDocType.getC_DocType_ID());
		creditNote.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		creditNote.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		creditNote.setRef_Invoice_ID(origInvoice.getC_Invoice_ID());
		saveRecord(creditNote);

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		final CrossIndustryInvoiceType cii = new CiiMapper().map(creditNote, recipientConfig);
		final XmlAssert xmlAssert = toXmlAssert(cii);

		// BT-3 Credit note type code
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:ExchangedDocument/ram:TypeCode")
				.isEqualTo("381");

		// BT-25 Preceding invoice number
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
								+ "/ram:ApplicableHeaderTradeSettlement/ram:InvoiceReferencedDocument/ram:IssuerAssignedID")
				.isEqualTo("RE-2024-00001");

		// BT-26 Preceding invoice issue date value + format attribute
		// FormattedIssueDateTime is in ram: namespace; DateTimeString is in qdt: namespace
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
								+ "/ram:ApplicableHeaderTradeSettlement/ram:InvoiceReferencedDocument"
								+ "/ram:FormattedIssueDateTime/qdt:DateTimeString")
				.isEqualTo("20240101");
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
								+ "/ram:ApplicableHeaderTradeSettlement/ram:InvoiceReferencedDocument"
								+ "/ram:FormattedIssueDateTime/qdt:DateTimeString/@format")
				.isEqualTo("102");
	}

	/**
	 * Verifies BG-25 invoice lines: BT-126 line id, BT-153 item name, BT-129/BT-130 qty + unit,
	 * BT-146 net price, BT-131 line net amount, BT-151 VAT category, BT-152 VAT rate.
	 */
	@Test
	void lines() throws Exception
	{
		// === Minimal seller org (required by CiiMapper seller-party logic) ===
		final I_AD_Org org = newInstance(I_AD_Org.class);
		saveRecord(org);
		final I_C_Country sellerCountry = newInstance(I_C_Country.class);
		sellerCountry.setCountryCode("DE");
		saveRecord(sellerCountry);
		final I_C_Location sellerLocation = newInstance(I_C_Location.class);
		sellerLocation.setC_Country_ID(sellerCountry.getC_Country_ID());
		saveRecord(sellerLocation);
		final I_C_BPartner sellerBP = newInstance(I_C_BPartner.class);
		sellerBP.setName("Seller GmbH");
		sellerBP.setAD_OrgBP_ID(org.getAD_Org_ID());
		saveRecord(sellerBP);
		final I_C_BPartner_Location sellerBPLoc = newInstance(I_C_BPartner_Location.class);
		sellerBPLoc.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerBPLoc.setC_Location_ID(sellerLocation.getC_Location_ID());
		saveRecord(sellerBPLoc);
		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(org.getAD_Org_ID());
		orgInfo.setOrg_BPartner_ID(sellerBP.getC_BPartner_ID());
		saveRecord(orgInfo);

		// === Minimal buyer ===
		final I_C_Country buyerCountry = newInstance(I_C_Country.class);
		buyerCountry.setCountryCode("DE");
		saveRecord(buyerCountry);
		final I_C_Location buyerLocation = newInstance(I_C_Location.class);
		buyerLocation.setC_Country_ID(buyerCountry.getC_Country_ID());
		saveRecord(buyerLocation);
		final I_C_BPartner buyerBP = newInstance(I_C_BPartner.class);
		buyerBP.setName("Buyer AG");
		saveRecord(buyerBP);
		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(buyerLocation.getC_Location_ID());
		saveRecord(buyerBPLoc);

		// === Currency ===
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		saveRecord(currency);

		// === DocType ===
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-2024-00100");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		saveRecord(invoice);

		// === UOM (BT-130 unit code) ===
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName("Stück");
		uom.setX12DE355("PCE");
		saveRecord(uom);

		// === Product (BT-153 item name) ===
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName("Testprodukt Alpha");
		product.setValue("TP-001");
		saveRecord(product);

		// === Tax with EN16931VATCategory = 'S', Rate = 19 (BT-151 + BT-152) ===
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName("Normale MWSt 19%");
		tax.setEN16931VATCategory("S");
		tax.setRate(new BigDecimal("19"));
		saveRecord(tax);

		// === Invoice line ===
		final I_C_InvoiceLine line = newInstance(I_C_InvoiceLine.class);
		line.setC_Invoice_ID(invoice.getC_Invoice_ID());
		line.setLine(10);
		line.setM_Product_ID(product.getM_Product_ID());
		line.setC_UOM_ID(uom.getC_UOM_ID());
		line.setC_Tax_ID(tax.getC_Tax_ID());
		line.setQtyInvoiced(new BigDecimal("5"));
		line.setPriceActual(new BigDecimal("100.00"));
		line.setLineNetAmt(new BigDecimal("500.00"));
		saveRecord(line);

		// === Map ===
		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		final CrossIndustryInvoiceType cii = new CiiMapper().map(invoice, recipientConfig);
		final XmlAssert xmlAssert = toXmlAssert(cii);

		final String lineBase = "//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
				+ "/ram:IncludedSupplyChainTradeLineItem[1]";

		// BT-126 Line id
		xmlAssert.valueByXPath(lineBase + "/ram:AssociatedDocumentLineDocument/ram:LineID")
				.isEqualTo("10");

		// BT-153 Item name
		xmlAssert.valueByXPath(lineBase + "/ram:SpecifiedTradeProduct/ram:Name")
				.isEqualTo("Testprodukt Alpha");

		// BT-129 Billed quantity value
		xmlAssert.valueByXPath(lineBase + "/ram:SpecifiedLineTradeDelivery/ram:BilledQuantity")
				.isEqualTo("5");

		// BT-130 Unit code attribute — PCE (metasfresh X12DE355) maps to C62 (EN16931 UN/ECE Rec 20 "one/piece")
		xmlAssert.valueByXPath(lineBase + "/ram:SpecifiedLineTradeDelivery/ram:BilledQuantity/@unitCode")
				.isEqualTo("C62");

		// BT-146 Net price (ChargeAmount of NetPriceProductTradePrice)
		xmlAssert.valueByXPath(lineBase + "/ram:SpecifiedLineTradeAgreement/ram:NetPriceProductTradePrice/ram:ChargeAmount")
				.isEqualTo("100.00");

		// BT-131 Line net amount
		xmlAssert.valueByXPath(lineBase + "/ram:SpecifiedLineTradeSettlement"
				+ "/ram:SpecifiedTradeSettlementLineMonetarySummation/ram:LineTotalAmount")
				.isEqualTo("500.00");

		// BT-151 VAT category code
		xmlAssert.valueByXPath(lineBase + "/ram:SpecifiedLineTradeSettlement/ram:ApplicableTradeTax/ram:CategoryCode")
				.isEqualTo("S");

		// BT-152 VAT rate
		xmlAssert.valueByXPath(lineBase + "/ram:SpecifiedLineTradeSettlement/ram:ApplicableTradeTax/ram:RateApplicablePercent")
				.isEqualTo("19");
	}

	/**
	 * BT-153 override path: when {@code ProductDescription} is set on the line, it takes
	 * precedence over {@code M_Product.Name}.
	 */
	@Test
	void lines_bt153_productDescription_override() throws Exception
	{
		// === Minimal seller org ===
		final I_AD_Org org = newInstance(I_AD_Org.class);
		saveRecord(org);
		final I_C_Country sellerCountry = newInstance(I_C_Country.class);
		sellerCountry.setCountryCode("DE");
		saveRecord(sellerCountry);
		final I_C_Location sellerLocation = newInstance(I_C_Location.class);
		sellerLocation.setC_Country_ID(sellerCountry.getC_Country_ID());
		saveRecord(sellerLocation);
		final I_C_BPartner sellerBP = newInstance(I_C_BPartner.class);
		sellerBP.setName("Seller GmbH");
		sellerBP.setAD_OrgBP_ID(org.getAD_Org_ID());
		saveRecord(sellerBP);
		final I_C_BPartner_Location sellerBPLoc = newInstance(I_C_BPartner_Location.class);
		sellerBPLoc.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerBPLoc.setC_Location_ID(sellerLocation.getC_Location_ID());
		saveRecord(sellerBPLoc);
		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(org.getAD_Org_ID());
		orgInfo.setOrg_BPartner_ID(sellerBP.getC_BPartner_ID());
		saveRecord(orgInfo);

		// === Minimal buyer ===
		final I_C_Country buyerCountry = newInstance(I_C_Country.class);
		buyerCountry.setCountryCode("DE");
		saveRecord(buyerCountry);
		final I_C_Location buyerLocation = newInstance(I_C_Location.class);
		buyerLocation.setC_Country_ID(buyerCountry.getC_Country_ID());
		saveRecord(buyerLocation);
		final I_C_BPartner buyerBP = newInstance(I_C_BPartner.class);
		buyerBP.setName("Buyer AG");
		saveRecord(buyerBP);
		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(buyerLocation.getC_Location_ID());
		saveRecord(buyerBPLoc);

		// === Currency + DocType ===
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		saveRecord(currency);
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-2024-00200");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		saveRecord(invoice);

		// === UOM with X12DE355 ===
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName("Stück");
		uom.setX12DE355("PCE");
		saveRecord(uom);

		// === Product — name would be used as fallback; ProductDescription on the line overrides ===
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName("Generischer Produktname");
		product.setValue("GP-001");
		saveRecord(product);

		// === Tax ===
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName("MWSt 19%");
		tax.setEN16931VATCategory("S");
		tax.setRate(new BigDecimal("19"));
		saveRecord(tax);

		// === Invoice line — ProductDescription set → must override M_Product.Name (BT-153) ===
		final I_C_InvoiceLine line = newInstance(I_C_InvoiceLine.class);
		line.setC_Invoice_ID(invoice.getC_Invoice_ID());
		line.setLine(10);
		line.setM_Product_ID(product.getM_Product_ID());
		line.setProductDescription("Spezifische Artikelbeschreibung");
		line.setC_UOM_ID(uom.getC_UOM_ID());
		line.setC_Tax_ID(tax.getC_Tax_ID());
		line.setQtyInvoiced(new BigDecimal("1"));
		line.setPriceActual(new BigDecimal("50.00"));
		line.setLineNetAmt(new BigDecimal("50.00"));
		saveRecord(line);

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		final CrossIndustryInvoiceType cii = new CiiMapper().map(invoice, recipientConfig);
		final XmlAssert xmlAssert = toXmlAssert(cii);

		final String lineBase = "//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
				+ "/ram:IncludedSupplyChainTradeLineItem[1]";

		// BT-153 must reflect ProductDescription, not M_Product.Name
		xmlAssert.valueByXPath(lineBase + "/ram:SpecifiedTradeProduct/ram:Name")
				.isEqualTo("Spezifische Artikelbeschreibung");
	}

	/**
	 * BT-130 fail-fast: when the line's UOM has no X12DE355 unit code, the mapper must throw
	 * with a message naming C_UOM_ID and C_InvoiceLine_ID.
	 */
	@Test
	void lines_bt130_missingUnitCode_throwsWithIds()
	{
		// === Minimal seller org ===
		final I_AD_Org org = newInstance(I_AD_Org.class);
		saveRecord(org);
		final I_C_Country sellerCountry = newInstance(I_C_Country.class);
		sellerCountry.setCountryCode("DE");
		saveRecord(sellerCountry);
		final I_C_Location sellerLocation = newInstance(I_C_Location.class);
		sellerLocation.setC_Country_ID(sellerCountry.getC_Country_ID());
		saveRecord(sellerLocation);
		final I_C_BPartner sellerBP = newInstance(I_C_BPartner.class);
		sellerBP.setName("Seller GmbH");
		sellerBP.setAD_OrgBP_ID(org.getAD_Org_ID());
		saveRecord(sellerBP);
		final I_C_BPartner_Location sellerBPLoc = newInstance(I_C_BPartner_Location.class);
		sellerBPLoc.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerBPLoc.setC_Location_ID(sellerLocation.getC_Location_ID());
		saveRecord(sellerBPLoc);
		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(org.getAD_Org_ID());
		orgInfo.setOrg_BPartner_ID(sellerBP.getC_BPartner_ID());
		saveRecord(orgInfo);

		// === Minimal buyer ===
		final I_C_Country buyerCountry = newInstance(I_C_Country.class);
		buyerCountry.setCountryCode("DE");
		saveRecord(buyerCountry);
		final I_C_Location buyerLocation = newInstance(I_C_Location.class);
		buyerLocation.setC_Country_ID(buyerCountry.getC_Country_ID());
		saveRecord(buyerLocation);
		final I_C_BPartner buyerBP = newInstance(I_C_BPartner.class);
		buyerBP.setName("Buyer AG");
		saveRecord(buyerBP);
		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(buyerLocation.getC_Location_ID());
		saveRecord(buyerBPLoc);

		// === Currency + DocType ===
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		saveRecord(currency);
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-2024-00300");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		saveRecord(invoice);

		// === UOM WITHOUT X12DE355 ===
		final I_C_UOM uomWithoutCode = newInstance(I_C_UOM.class);
		uomWithoutCode.setName("Karton");
		// X12DE355 intentionally left empty/null
		saveRecord(uomWithoutCode);

		// === Product ===
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName("Testprodukt");
		product.setValue("TP-002");
		saveRecord(product);

		// === Tax ===
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName("MWSt 19%");
		tax.setEN16931VATCategory("S");
		tax.setRate(new BigDecimal("19"));
		saveRecord(tax);

		// === Invoice line with UOM that has no X12DE355 ===
		final I_C_InvoiceLine line = newInstance(I_C_InvoiceLine.class);
		line.setC_Invoice_ID(invoice.getC_Invoice_ID());
		line.setLine(10);
		line.setM_Product_ID(product.getM_Product_ID());
		line.setC_UOM_ID(uomWithoutCode.getC_UOM_ID());
		line.setC_Tax_ID(tax.getC_Tax_ID());
		line.setQtyInvoiced(new BigDecimal("2"));
		line.setPriceActual(new BigDecimal("10.00"));
		line.setLineNetAmt(new BigDecimal("20.00"));
		saveRecord(line);

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		assertThatThrownBy(() -> new CiiMapper().map(invoice, recipientConfig))
				.isInstanceOf(org.adempiere.exceptions.AdempiereException.class)
				.hasMessageContaining("C_UOM_ID=" + uomWithoutCode.getC_UOM_ID())
				.hasMessageContaining("C_InvoiceLine_ID=" + line.getC_InvoiceLine_ID());
	}

	/**
	 * BT-151 fail-fast: when the line's tax has no EN16931VATCategory, the mapper must throw
	 * with a message naming C_Tax_ID and C_InvoiceLine_ID.
	 */
	@Test
	void lines_bt151_missingVatCategory_throwsWithIds()
	{
		// === Minimal seller org ===
		final I_AD_Org org = newInstance(I_AD_Org.class);
		saveRecord(org);
		final I_C_Country sellerCountry = newInstance(I_C_Country.class);
		sellerCountry.setCountryCode("DE");
		saveRecord(sellerCountry);
		final I_C_Location sellerLocation = newInstance(I_C_Location.class);
		sellerLocation.setC_Country_ID(sellerCountry.getC_Country_ID());
		saveRecord(sellerLocation);
		final I_C_BPartner sellerBP = newInstance(I_C_BPartner.class);
		sellerBP.setName("Seller GmbH");
		sellerBP.setAD_OrgBP_ID(org.getAD_Org_ID());
		saveRecord(sellerBP);
		final I_C_BPartner_Location sellerBPLoc = newInstance(I_C_BPartner_Location.class);
		sellerBPLoc.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerBPLoc.setC_Location_ID(sellerLocation.getC_Location_ID());
		saveRecord(sellerBPLoc);
		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(org.getAD_Org_ID());
		orgInfo.setOrg_BPartner_ID(sellerBP.getC_BPartner_ID());
		saveRecord(orgInfo);

		// === Minimal buyer ===
		final I_C_Country buyerCountry = newInstance(I_C_Country.class);
		buyerCountry.setCountryCode("DE");
		saveRecord(buyerCountry);
		final I_C_Location buyerLocation = newInstance(I_C_Location.class);
		buyerLocation.setC_Country_ID(buyerCountry.getC_Country_ID());
		saveRecord(buyerLocation);
		final I_C_BPartner buyerBP = newInstance(I_C_BPartner.class);
		buyerBP.setName("Buyer AG");
		saveRecord(buyerBP);
		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(buyerLocation.getC_Location_ID());
		saveRecord(buyerBPLoc);

		// === Currency + DocType ===
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		saveRecord(currency);
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-2024-00400");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		saveRecord(invoice);

		// === UOM with X12DE355 (so BT-130 does not trigger first) ===
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName("Stück");
		uom.setX12DE355("PCE");
		saveRecord(uom);

		// === Product ===
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName("Testprodukt");
		product.setValue("TP-003");
		saveRecord(product);

		// === Tax WITHOUT EN16931VATCategory ===
		final I_C_Tax taxWithoutCategory = newInstance(I_C_Tax.class);
		taxWithoutCategory.setName("Unbekannte Steuer");
		// EN16931VATCategory intentionally left null
		taxWithoutCategory.setRate(new BigDecimal("7"));
		saveRecord(taxWithoutCategory);

		// === Invoice line ===
		final I_C_InvoiceLine line = newInstance(I_C_InvoiceLine.class);
		line.setC_Invoice_ID(invoice.getC_Invoice_ID());
		line.setLine(10);
		line.setM_Product_ID(product.getM_Product_ID());
		line.setC_UOM_ID(uom.getC_UOM_ID());
		line.setC_Tax_ID(taxWithoutCategory.getC_Tax_ID());
		line.setQtyInvoiced(new BigDecimal("3"));
		line.setPriceActual(new BigDecimal("30.00"));
		line.setLineNetAmt(new BigDecimal("90.00"));
		saveRecord(line);

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		assertThatThrownBy(() -> new CiiMapper().map(invoice, recipientConfig))
				.isInstanceOf(org.adempiere.exceptions.AdempiereException.class)
				.hasMessageContaining("C_Tax_ID=" + taxWithoutCategory.getC_Tax_ID())
				.hasMessageContaining("C_InvoiceLine_ID=" + line.getC_InvoiceLine_ID());
	}

	/**
	 * Verifies BG-23 VAT breakdown (2 groups: 19% S and 0% AE), BG-22 monetary totals, BG-16 payment means,
	 * and BT-84 payee IBAN.
	 *
	 * <p>Fixture is internally consistent with EN 16931 BR-CO-13: GrandTotal = TaxBasisTotal + sum(TaxAmt).
	 * S group: base 1000 @19% = 190 tax; AE group: base 500 @0% = 0 tax;
	 * TotalLines=1500, TaxTotal=190, GrandTotal=1690.
	 */
	@Test
	void vat_breakdown_totals_payment() throws Exception
	{
		// === Minimal seller org ===
		final I_AD_Org org = newInstance(I_AD_Org.class);
		saveRecord(org);
		final I_C_Country sellerCountry = newInstance(I_C_Country.class);
		sellerCountry.setCountryCode("DE");
		saveRecord(sellerCountry);
		final I_C_Location sellerLocation = newInstance(I_C_Location.class);
		sellerLocation.setC_Country_ID(sellerCountry.getC_Country_ID());
		saveRecord(sellerLocation);
		final I_C_BPartner sellerBP = newInstance(I_C_BPartner.class);
		sellerBP.setName("Seller GmbH");
		sellerBP.setAD_OrgBP_ID(org.getAD_Org_ID());
		saveRecord(sellerBP);
		final I_C_BPartner_Location sellerBPLoc = newInstance(I_C_BPartner_Location.class);
		sellerBPLoc.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerBPLoc.setC_Location_ID(sellerLocation.getC_Location_ID());
		saveRecord(sellerBPLoc);
		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(org.getAD_Org_ID());
		orgInfo.setOrg_BPartner_ID(sellerBP.getC_BPartner_ID());
		saveRecord(orgInfo);

		// === Seller bank account with IBAN (required for BR-61: code 30 must carry BT-84) ===
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		saveRecord(currency);

		final I_C_BP_BankAccount sellerBankAccount = newInstance(I_C_BP_BankAccount.class);
		sellerBankAccount.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerBankAccount.setC_Currency_ID(currency.getC_Currency_ID());
		sellerBankAccount.setIBAN("DE89370400440532013000");
		sellerBankAccount.setIsDefault(true);
		sellerBankAccount.setAD_Org_ID(org.getAD_Org_ID());
		saveRecord(sellerBankAccount);

		// === Minimal buyer ===
		final I_C_Country buyerCountry = newInstance(I_C_Country.class);
		buyerCountry.setCountryCode("DE");
		saveRecord(buyerCountry);
		final I_C_Location buyerLocation = newInstance(I_C_Location.class);
		buyerLocation.setC_Country_ID(buyerCountry.getC_Country_ID());
		saveRecord(buyerLocation);
		final I_C_BPartner buyerBP = newInstance(I_C_BPartner.class);
		buyerBP.setName("Buyer AG");
		saveRecord(buyerBP);
		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(buyerLocation.getC_Location_ID());
		saveRecord(buyerBPLoc);

		// === DocType ===
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice: BR-CO-13 consistent — GrandTotal = TaxBasisTotal + sum(TaxAmt)
		//   S group: base 1000, tax 190 (19%)
		//   AE group: base 500, tax 0 (0%)
		//   TotalLines = 1500, TaxTotal = 190, GrandTotal = 1690 ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-2024-00500");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		invoice.setTotalLines(new BigDecimal("1500.00"));
		invoice.setGrandTotal(new BigDecimal("1690.00"));
		invoice.setPaymentRule("T"); // DirectDeposit → UNCL4461 code 30 (credit transfer)
		saveRecord(invoice);

		// === Tax 1: 19% standard 'S' ===
		final I_C_Tax taxS = newInstance(I_C_Tax.class);
		taxS.setName("MWSt 19%");
		taxS.setEN16931VATCategory("S");
		taxS.setRate(new BigDecimal("19"));
		saveRecord(taxS);

		// === Tax 2: 0% reverse charge 'AE' ===
		final I_C_Tax taxAE = newInstance(I_C_Tax.class);
		taxAE.setName("Reverse Charge 0%");
		taxAE.setEN16931VATCategory("AE");
		taxAE.setRate(new BigDecimal("0"));
		saveRecord(taxAE);

		// === C_InvoiceTax rows: group 1 = S (1000 base, 190 tax), group 2 = AE (500 base, 0 tax) ===
		final I_C_InvoiceTax invoiceTaxS = newInstance(I_C_InvoiceTax.class);
		invoiceTaxS.setC_Invoice_ID(invoice.getC_Invoice_ID());
		invoiceTaxS.setC_Tax_ID(taxS.getC_Tax_ID());
		invoiceTaxS.setTaxBaseAmt(new BigDecimal("1000.00")); // BT-116
		invoiceTaxS.setTaxAmt(new BigDecimal("190.00"));      // BT-117 (1000 * 19%)
		saveRecord(invoiceTaxS);

		final I_C_InvoiceTax invoiceTaxAE = newInstance(I_C_InvoiceTax.class);
		invoiceTaxAE.setC_Invoice_ID(invoice.getC_Invoice_ID());
		invoiceTaxAE.setC_Tax_ID(taxAE.getC_Tax_ID());
		invoiceTaxAE.setTaxBaseAmt(new BigDecimal("500.00")); // BT-116
		invoiceTaxAE.setTaxAmt(new BigDecimal("0.00"));       // BT-117
		saveRecord(invoiceTaxAE);

		// === UOM + Product + line (needed to pass invoice mapping) ===
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName("Stück");
		uom.setX12DE355("PCE");
		saveRecord(uom);
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName("Testprodukt");
		product.setValue("TP-500");
		saveRecord(product);
		final I_C_InvoiceLine line = newInstance(I_C_InvoiceLine.class);
		line.setC_Invoice_ID(invoice.getC_Invoice_ID());
		line.setLine(10);
		line.setM_Product_ID(product.getM_Product_ID());
		line.setC_UOM_ID(uom.getC_UOM_ID());
		line.setC_Tax_ID(taxS.getC_Tax_ID());
		line.setQtyInvoiced(new BigDecimal("1"));
		line.setPriceActual(new BigDecimal("1500.00"));
		line.setLineNetAmt(new BigDecimal("1500.00"));
		saveRecord(line);

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		final CrossIndustryInvoiceType cii = new CiiMapper().map(invoice, recipientConfig);
		final XmlAssert xmlAssert = toXmlAssert(cii);

		final String settlement = "//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeSettlement";

		// BG-23 VAT breakdown: expect exactly 2 ApplicableTradeTax groups
		// Group 1: S / 19%
		xmlAssert.valueByXPath(settlement + "/ram:ApplicableTradeTax[ram:CategoryCode='S']/ram:BasisAmount")
				.isEqualTo("1000.00");
		xmlAssert.valueByXPath(settlement + "/ram:ApplicableTradeTax[ram:CategoryCode='S']/ram:CalculatedAmount")
				.isEqualTo("190.00");
		xmlAssert.valueByXPath(settlement + "/ram:ApplicableTradeTax[ram:CategoryCode='S']/ram:RateApplicablePercent")
				.isEqualTo("19");
		// No exemption reason for S category

		// Group 2: AE / 0% reverse charge
		xmlAssert.valueByXPath(settlement + "/ram:ApplicableTradeTax[ram:CategoryCode='AE']/ram:BasisAmount")
				.isEqualTo("500.00");
		xmlAssert.valueByXPath(settlement + "/ram:ApplicableTradeTax[ram:CategoryCode='AE']/ram:CalculatedAmount")
				.isEqualTo("0.00");
		xmlAssert.valueByXPath(settlement + "/ram:ApplicableTradeTax[ram:CategoryCode='AE']/ram:RateApplicablePercent")
				.isEqualTo("0");
		// BT-120 exemption reason for AE
		xmlAssert.valueByXPath(settlement + "/ram:ApplicableTradeTax[ram:CategoryCode='AE']/ram:ExemptionReason")
				.isEqualTo("Reverse charge");

		// BG-22 monetary totals
		final String summation = settlement + "/ram:SpecifiedTradeSettlementHeaderMonetarySummation";
		// BT-106 sum of line net = TotalLines
		xmlAssert.valueByXPath(summation + "/ram:LineTotalAmount").isEqualTo("1500.00");
		// BT-109 total without VAT = TotalLines (no header charges/allowances)
		xmlAssert.valueByXPath(summation + "/ram:TaxBasisTotalAmount").isEqualTo("1500.00");
		// BT-110 total VAT = sum of BT-117 (190 + 0)
		xmlAssert.valueByXPath(summation + "/ram:TaxTotalAmount").isEqualTo("190.00");
		// BR-CO-15: TaxTotalAmount/@currencyID must match invoice currency
		xmlAssert.valueByXPath(summation + "/ram:TaxTotalAmount/@currencyID").isEqualTo("EUR");
		// BT-112 total with VAT = GrandTotal
		xmlAssert.valueByXPath(summation + "/ram:GrandTotalAmount").isEqualTo("1690.00");
		// BT-115 amount due for payment = GrandTotal
		xmlAssert.valueByXPath(summation + "/ram:DuePayableAmount").isEqualTo("1690.00");

		// EN 16931 BR-CO-13: GrandTotal = TaxBasisTotal + sum(TaxAmt)
		// 1690 = 1500 + 190  ✓ (verified numerically; XPath arithmetic asserts the relationship)
		xmlAssert.valueByXPath(
						"number(" + summation + "/ram:GrandTotalAmount)"
								+ " - number(" + summation + "/ram:TaxBasisTotalAmount)"
								+ " - number(" + summation + "/ram:TaxTotalAmount)")
				.isEqualTo("0");

		// BG-16 payment means: PaymentRule=T → UNCL4461 code 30 (credit transfer)
		xmlAssert.valueByXPath(settlement + "/ram:SpecifiedTradeSettlementPaymentMeans/ram:TypeCode")
				.isEqualTo("30");

		// BT-84 Payee IBAN (BR-61: mandatory for code 30/58)
		xmlAssert.valueByXPath(settlement
						+ "/ram:SpecifiedTradeSettlementPaymentMeans/ram:PayeePartyCreditorFinancialAccount/ram:IBANID")
				.isEqualTo("DE89370400440532013000");
	}

	/**
	 * Header-level fail-fast: when a {@code C_InvoiceTax} row references a {@code C_Tax} with a NULL
	 * {@code EN16931VATCategory}, the mapper must throw {@link org.adempiere.exceptions.AdempiereException}
	 * with a message naming the C_Tax_ID (mirrors the existing line-level test).
	 */
	@Test
	void header_vat_missing_category_throwsWithTaxId()
	{
		// === Minimal seller org ===
		final I_AD_Org org = newInstance(I_AD_Org.class);
		saveRecord(org);
		final I_C_Country sellerCountry = newInstance(I_C_Country.class);
		sellerCountry.setCountryCode("DE");
		saveRecord(sellerCountry);
		final I_C_Location sellerLocation = newInstance(I_C_Location.class);
		sellerLocation.setC_Country_ID(sellerCountry.getC_Country_ID());
		saveRecord(sellerLocation);
		final I_C_BPartner sellerBP = newInstance(I_C_BPartner.class);
		sellerBP.setName("Seller GmbH");
		sellerBP.setAD_OrgBP_ID(org.getAD_Org_ID());
		saveRecord(sellerBP);
		final I_C_BPartner_Location sellerBPLoc = newInstance(I_C_BPartner_Location.class);
		sellerBPLoc.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerBPLoc.setC_Location_ID(sellerLocation.getC_Location_ID());
		saveRecord(sellerBPLoc);
		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(org.getAD_Org_ID());
		orgInfo.setOrg_BPartner_ID(sellerBP.getC_BPartner_ID());
		saveRecord(orgInfo);

		// === Minimal buyer ===
		final I_C_Country buyerCountry = newInstance(I_C_Country.class);
		buyerCountry.setCountryCode("DE");
		saveRecord(buyerCountry);
		final I_C_Location buyerLocation = newInstance(I_C_Location.class);
		buyerLocation.setC_Country_ID(buyerCountry.getC_Country_ID());
		saveRecord(buyerLocation);
		final I_C_BPartner buyerBP = newInstance(I_C_BPartner.class);
		buyerBP.setName("Buyer AG");
		saveRecord(buyerBP);
		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(buyerLocation.getC_Location_ID());
		saveRecord(buyerBPLoc);

		// === Currency + DocType ===
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		saveRecord(currency);
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-2024-00600");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		invoice.setTotalLines(new BigDecimal("100.00"));
		invoice.setGrandTotal(new BigDecimal("100.00"));
		saveRecord(invoice);

		// === UOM + Product + line (so the mapper reaches the settlement / VAT breakdown phase) ===
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName("Stück");
		uom.setX12DE355("PCE");
		saveRecord(uom);
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName("Testprodukt");
		product.setValue("TP-600");
		saveRecord(product);

		// A line-level tax with a valid category (so BG-25 succeeds and we reach BG-23)
		final I_C_Tax lineTax = newInstance(I_C_Tax.class);
		lineTax.setName("MWSt 19%");
		lineTax.setEN16931VATCategory("S");
		lineTax.setRate(new BigDecimal("19"));
		saveRecord(lineTax);

		final I_C_InvoiceLine line = newInstance(I_C_InvoiceLine.class);
		line.setC_Invoice_ID(invoice.getC_Invoice_ID());
		line.setLine(10);
		line.setM_Product_ID(product.getM_Product_ID());
		line.setC_UOM_ID(uom.getC_UOM_ID());
		line.setC_Tax_ID(lineTax.getC_Tax_ID());
		line.setQtyInvoiced(new BigDecimal("1"));
		line.setPriceActual(new BigDecimal("100.00"));
		line.setLineNetAmt(new BigDecimal("100.00"));
		saveRecord(line);

		// === C_InvoiceTax row referencing a tax WITHOUT EN16931VATCategory ===
		final I_C_Tax taxWithoutCategory = newInstance(I_C_Tax.class);
		taxWithoutCategory.setName("Unbekannte Steuer");
		// EN16931VATCategory intentionally left null
		taxWithoutCategory.setRate(new BigDecimal("0"));
		saveRecord(taxWithoutCategory);

		final I_C_InvoiceTax invoiceTax = newInstance(I_C_InvoiceTax.class);
		invoiceTax.setC_Invoice_ID(invoice.getC_Invoice_ID());
		invoiceTax.setC_Tax_ID(taxWithoutCategory.getC_Tax_ID());
		invoiceTax.setTaxBaseAmt(new BigDecimal("100.00"));
		invoiceTax.setTaxAmt(new BigDecimal("0.00"));
		saveRecord(invoiceTax);

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		assertThatThrownBy(() -> new CiiMapper().map(invoice, recipientConfig))
				.isInstanceOf(org.adempiere.exceptions.AdempiereException.class)
				.hasMessageContaining("C_Tax_ID=" + taxWithoutCategory.getC_Tax_ID());
	}

	/**
	 * BG-6 Seller contact: when the seller BPartner has an AD_User contact with name, phone, and email,
	 * the mapper must emit DefinedTradeContact/PersonName (BT-41), TelephoneUniversalCommunication/CompleteNumber
	 * (BT-42), and EmailURIUniversalCommunication/URIID (BT-43) on SellerTradeParty.
	 *
	 * <p>XRechnung CIUS BR-DE-2 mandates DefinedTradeContact; BR-DE-5 requires PersonName or DepartmentName;
	 * BR-DE-6 requires a telephone number; BR-DE-7 requires an email URI.
	 */
	@Test
	void seller_contact_mapping_bt41_bt42_bt43() throws Exception
	{
		// === Seller org ===
		final I_AD_Org org = newInstance(I_AD_Org.class);
		saveRecord(org);

		final I_C_Country sellerCountry = newInstance(I_C_Country.class);
		sellerCountry.setCountryCode("DE");
		saveRecord(sellerCountry);

		final I_C_Location sellerLocation = newInstance(I_C_Location.class);
		sellerLocation.setAddress1("Musterstraße 1");
		sellerLocation.setCity("Berlin");
		sellerLocation.setPostal("10115");
		sellerLocation.setC_Country_ID(sellerCountry.getC_Country_ID());
		saveRecord(sellerLocation);

		final I_C_BPartner sellerBP = newInstance(I_C_BPartner.class);
		sellerBP.setName("Muster GmbH");
		sellerBP.setAD_OrgBP_ID(org.getAD_Org_ID());
		saveRecord(sellerBP);

		final I_C_BPartner_Location sellerBPLoc = newInstance(I_C_BPartner_Location.class);
		sellerBPLoc.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerBPLoc.setC_Location_ID(sellerLocation.getC_Location_ID());
		sellerBPLoc.setIsBillTo(true);
		saveRecord(sellerBPLoc);

		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(org.getAD_Org_ID());
		orgInfo.setOrg_BPartner_ID(sellerBP.getC_BPartner_ID());
		saveRecord(orgInfo);

		// === Seller contact (AD_User) — the subject under test ===
		final I_AD_User sellerContact = newInstance(I_AD_User.class);
		sellerContact.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerContact.setName("Max Mustermann");
		sellerContact.setPhone("+49 30 123456789");
		sellerContact.setEMail("max.mustermann@muster.de");
		saveRecord(sellerContact);

		// === Minimal buyer ===
		final I_C_Country buyerCountry = newInstance(I_C_Country.class);
		buyerCountry.setCountryCode("DE");
		saveRecord(buyerCountry);
		final I_C_Location buyerLocation = newInstance(I_C_Location.class);
		buyerLocation.setC_Country_ID(buyerCountry.getC_Country_ID());
		saveRecord(buyerLocation);
		final I_C_BPartner buyerBP = newInstance(I_C_BPartner.class);
		buyerBP.setName("Buyer AG");
		saveRecord(buyerBP);
		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(buyerLocation.getC_Location_ID());
		saveRecord(buyerBPLoc);

		// === Currency + DocType ===
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		saveRecord(currency);
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-2024-00700");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		saveRecord(invoice);

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		final CrossIndustryInvoiceType cii = new CiiMapper().map(invoice, recipientConfig);
		final XmlAssert xmlAssert = toXmlAssert(cii);

		final String contactXPath = "//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
				+ "/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:DefinedTradeContact";

		// BT-41 PersonName
		xmlAssert.valueByXPath(contactXPath + "/ram:PersonName")
				.as("BT-41: DefinedTradeContact/PersonName must match the seller contact name")
				.isEqualTo("Max Mustermann");

		// BT-42 phone (CompleteNumber)
		xmlAssert.valueByXPath(contactXPath + "/ram:TelephoneUniversalCommunication/ram:CompleteNumber")
				.as("BT-42: TelephoneUniversalCommunication/CompleteNumber must match the seller contact phone")
				.isEqualTo("+49 30 123456789");

		// BT-43 email (URIID)
		xmlAssert.valueByXPath(contactXPath + "/ram:EmailURIUniversalCommunication/ram:URIID")
				.as("BT-43: EmailURIUniversalCommunication/URIID must match the seller contact email")
				.isEqualTo("max.mustermann@muster.de");
	}

	/**
	 * BT-49 happy path: when a {@link DocOutboundLogMailRecipientRegistry} stub returns a recipient with
	 * a distinct email address, the mapper must use that address (not the BPartnerLocation email).
	 */
	@Test
	void bt49_buyerElectronicAddress_usesRegistryEmail() throws Exception
	{
		// === Minimal seller org ===
		final I_AD_Org org = newInstance(I_AD_Org.class);
		saveRecord(org);
		final I_C_Country sellerCountry = newInstance(I_C_Country.class);
		sellerCountry.setCountryCode("DE");
		saveRecord(sellerCountry);
		final I_C_Location sellerLocation = newInstance(I_C_Location.class);
		sellerLocation.setC_Country_ID(sellerCountry.getC_Country_ID());
		saveRecord(sellerLocation);
		final I_C_BPartner sellerBP = newInstance(I_C_BPartner.class);
		sellerBP.setName("Seller GmbH");
		sellerBP.setAD_OrgBP_ID(org.getAD_Org_ID());
		saveRecord(sellerBP);
		final I_C_BPartner_Location sellerBPLoc = newInstance(I_C_BPartner_Location.class);
		sellerBPLoc.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerBPLoc.setC_Location_ID(sellerLocation.getC_Location_ID());
		saveRecord(sellerBPLoc);
		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(org.getAD_Org_ID());
		orgInfo.setOrg_BPartner_ID(sellerBP.getC_BPartner_ID());
		saveRecord(orgInfo);

		// === Buyer ===
		final I_C_Country buyerCountry = newInstance(I_C_Country.class);
		buyerCountry.setCountryCode("DE");
		saveRecord(buyerCountry);
		final I_C_Location buyerLocation = newInstance(I_C_Location.class);
		buyerLocation.setC_Country_ID(buyerCountry.getC_Country_ID());
		saveRecord(buyerLocation);
		final I_C_BPartner buyerBP = newInstance(I_C_BPartner.class);
		buyerBP.setName("Buyer AG");
		saveRecord(buyerBP);
		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(buyerLocation.getC_Location_ID());
		buyerBPLoc.setEMail("location@buyer.de");   // fallback — must NOT appear in BT-49
		saveRecord(buyerBPLoc);

		// === Currency + DocType ===
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		saveRecord(currency);
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-2024-00800");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		saveRecord(invoice);

		// === Stub registry: always returns a fixed "resolver" email (distinct from the location email) ===
		final String resolverEmail = "registry-resolved@buyer.de";
		final DocOutboundLogMailRecipientRegistry stubRegistry = new DocOutboundLogMailRecipientRegistry(
				Optional.of(Collections.singletonList(new DocOutboundLogMailRecipientProvider()
				{
					@Override
					public boolean isDefault() { return true; }

					@Override
					public String getTableName() { return null; }

					@Override
					public Optional<DocOutBoundRecipients> provideMailRecipient(final DocOutboundLogMailRecipientRequest request)
					{
						return DocOutBoundRecipients.optionalOfTo(
								DocOutBoundRecipient.builder()
										.id(DocOutBoundRecipientId.ofRepoId(1))
										.emailAddress(resolverEmail)
										.invoiceAsEmail(true)
										.build());
					}
				})));

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		final CrossIndustryInvoiceType cii = new CiiMapper(stubRegistry, null).map(invoice, recipientConfig);
		final XmlAssert xmlAssert = toXmlAssert(cii);

		// BT-49 must use the registry-resolved email, NOT the BPartnerLocation email
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
								+ "/ram:ApplicableHeaderTradeAgreement/ram:BuyerTradeParty"
								+ "/ram:URIUniversalCommunication/ram:URIID")
				.as("BT-49: must use the registry-resolved email, not the BPartnerLocation email")
				.isEqualTo(resolverEmail);
	}

	/**
	 * BT-49 fallback: when no registry is injected (null) the mapper must fall back to the
	 * BPartnerLocation email.
	 */
	@Test
	void bt49_buyerElectronicAddress_fallsBackToLocationEmailWhenNoRegistry() throws Exception
	{
		// === Minimal seller org ===
		final I_AD_Org org = newInstance(I_AD_Org.class);
		saveRecord(org);
		final I_C_Country sellerCountry = newInstance(I_C_Country.class);
		sellerCountry.setCountryCode("DE");
		saveRecord(sellerCountry);
		final I_C_Location sellerLocation = newInstance(I_C_Location.class);
		sellerLocation.setC_Country_ID(sellerCountry.getC_Country_ID());
		saveRecord(sellerLocation);
		final I_C_BPartner sellerBP = newInstance(I_C_BPartner.class);
		sellerBP.setName("Seller GmbH");
		sellerBP.setAD_OrgBP_ID(org.getAD_Org_ID());
		saveRecord(sellerBP);
		final I_C_BPartner_Location sellerBPLoc = newInstance(I_C_BPartner_Location.class);
		sellerBPLoc.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerBPLoc.setC_Location_ID(sellerLocation.getC_Location_ID());
		saveRecord(sellerBPLoc);
		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(org.getAD_Org_ID());
		orgInfo.setOrg_BPartner_ID(sellerBP.getC_BPartner_ID());
		saveRecord(orgInfo);

		// === Buyer ===
		final I_C_Country buyerCountry = newInstance(I_C_Country.class);
		buyerCountry.setCountryCode("DE");
		saveRecord(buyerCountry);
		final I_C_Location buyerLocation = newInstance(I_C_Location.class);
		buyerLocation.setC_Country_ID(buyerCountry.getC_Country_ID());
		saveRecord(buyerLocation);
		final I_C_BPartner buyerBP = newInstance(I_C_BPartner.class);
		buyerBP.setName("Buyer AG");
		saveRecord(buyerBP);
		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(buyerLocation.getC_Location_ID());
		buyerBPLoc.setEMail("einkauf@buyer.de");
		saveRecord(buyerBPLoc);

		// === Currency + DocType ===
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		saveRecord(currency);
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-2024-00801");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		saveRecord(invoice);

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		// No registry and no mailService injected — mapper uses the no-arg-equivalent path (both null)
		final CrossIndustryInvoiceType cii = new CiiMapper(/* no registry */ (DocOutboundLogMailRecipientRegistry)null, /* no mailService */ null)
				.map(invoice, recipientConfig);
		final XmlAssert xmlAssert = toXmlAssert(cii);

		// BT-49 must fall back to the BPartnerLocation email
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
								+ "/ram:ApplicableHeaderTradeAgreement/ram:BuyerTradeParty"
								+ "/ram:URIUniversalCommunication/ram:URIID")
				.as("BT-49: must fall back to BPartnerLocation email when no registry is injected")
				.isEqualTo("einkauf@buyer.de");
	}

	/**
	 * BT-34 happy path: when a stub {@link MailService} returns a mailbox with a distinct "From"
	 * email, the mapper must use that address (not the org BPartner email).
	 */
	@Test
	void bt34_sellerElectronicAddress_usesMailboxFromAddress() throws Exception
	{
		// === Seller org ===
		final I_AD_Org org = newInstance(I_AD_Org.class);
		saveRecord(org);
		final I_C_Country sellerCountry = newInstance(I_C_Country.class);
		sellerCountry.setCountryCode("DE");
		saveRecord(sellerCountry);
		final I_C_Location sellerLocation = newInstance(I_C_Location.class);
		sellerLocation.setC_Country_ID(sellerCountry.getC_Country_ID());
		saveRecord(sellerLocation);
		final I_C_BPartner sellerBP = newInstance(I_C_BPartner.class);
		sellerBP.setName("Seller GmbH");
		sellerBP.setVATaxID("DE123456789");
		sellerBP.setEMail("bpartner-email@seller.de");  // must NOT appear in BT-34 when mailbox is resolved
		sellerBP.setAD_OrgBP_ID(org.getAD_Org_ID());
		saveRecord(sellerBP);
		final I_C_BPartner_Location sellerBPLoc = newInstance(I_C_BPartner_Location.class);
		sellerBPLoc.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerBPLoc.setC_Location_ID(sellerLocation.getC_Location_ID());
		saveRecord(sellerBPLoc);
		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(org.getAD_Org_ID());
		orgInfo.setOrg_BPartner_ID(sellerBP.getC_BPartner_ID());
		saveRecord(orgInfo);

		// === Minimal buyer ===
		final I_C_Country buyerCountry = newInstance(I_C_Country.class);
		buyerCountry.setCountryCode("DE");
		saveRecord(buyerCountry);
		final I_C_Location buyerLocation = newInstance(I_C_Location.class);
		buyerLocation.setC_Country_ID(buyerCountry.getC_Country_ID());
		saveRecord(buyerLocation);
		final I_C_BPartner buyerBP = newInstance(I_C_BPartner.class);
		buyerBP.setName("Buyer AG");
		saveRecord(buyerBP);
		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(buyerLocation.getC_Location_ID());
		saveRecord(buyerBPLoc);

		// === Currency + DocType ===
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		saveRecord(currency);
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-BT34-001");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		saveRecord(invoice);

		// === Stub MailService: always returns a fixed mailbox "From" address ===
		final String mailboxFromEmail = "noreply@outbound-mail.seller.de";
		final MailService stubMailService = new MailService(
				new MailboxRepository(),
				new MailTemplateRepository(),
				Collections.emptyList())
		{
			@Override
			public Mailbox findMailbox(@NonNull final MailboxQuery query)
			{
				return Mailbox.builder()
						.type(MailboxType.SMTP)
						.email(EMailAddress.ofString(mailboxFromEmail))
						.smtpConfig(SMTPConfig.builder()
								.smtpHost("smtp.example.com")
								.smtpPort(587)
								.smtpAuthorization(false)
								.build())
						.build();
			}
		};

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		final CrossIndustryInvoiceType cii = new CiiMapper(null, stubMailService).map(invoice, recipientConfig);
		final XmlAssert xmlAssert = toXmlAssert(cii);

		// BT-34 must use the mailbox "From" address, NOT the BPartner email
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
								+ "/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty"
								+ "/ram:URIUniversalCommunication/ram:URIID")
				.as("BT-34: must use the mailbox From address, not the BPartner email")
				.isEqualTo(mailboxFromEmail);

		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
								+ "/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty"
								+ "/ram:URIUniversalCommunication/ram:URIID/@schemeID")
				.isEqualTo("EM");
	}

	/**
	 * BT-34 fallback: when no {@link MailService} is injected (null), the mapper must fall back
	 * to the org BPartner email.
	 */
	@Test
	void bt34_sellerElectronicAddress_fallsBackToBPartnerEmailWhenNoMailService() throws Exception
	{
		// === Seller org ===
		final I_AD_Org org = newInstance(I_AD_Org.class);
		saveRecord(org);
		final I_C_Country sellerCountry = newInstance(I_C_Country.class);
		sellerCountry.setCountryCode("DE");
		saveRecord(sellerCountry);
		final I_C_Location sellerLocation = newInstance(I_C_Location.class);
		sellerLocation.setC_Country_ID(sellerCountry.getC_Country_ID());
		saveRecord(sellerLocation);
		final I_C_BPartner sellerBP = newInstance(I_C_BPartner.class);
		sellerBP.setName("Seller GmbH");
		sellerBP.setVATaxID("DE123456789");
		sellerBP.setEMail("invoice@seller.de");
		sellerBP.setAD_OrgBP_ID(org.getAD_Org_ID());
		saveRecord(sellerBP);
		final I_C_BPartner_Location sellerBPLoc = newInstance(I_C_BPartner_Location.class);
		sellerBPLoc.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerBPLoc.setC_Location_ID(sellerLocation.getC_Location_ID());
		saveRecord(sellerBPLoc);
		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(org.getAD_Org_ID());
		orgInfo.setOrg_BPartner_ID(sellerBP.getC_BPartner_ID());
		saveRecord(orgInfo);

		// === Minimal buyer ===
		final I_C_Country buyerCountry = newInstance(I_C_Country.class);
		buyerCountry.setCountryCode("DE");
		saveRecord(buyerCountry);
		final I_C_Location buyerLocation = newInstance(I_C_Location.class);
		buyerLocation.setC_Country_ID(buyerCountry.getC_Country_ID());
		saveRecord(buyerLocation);
		final I_C_BPartner buyerBP = newInstance(I_C_BPartner.class);
		buyerBP.setName("Buyer AG");
		saveRecord(buyerBP);
		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(buyerLocation.getC_Location_ID());
		saveRecord(buyerBPLoc);

		// === Currency + DocType ===
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		saveRecord(currency);
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-BT34-002");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		saveRecord(invoice);

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		// No MailService — must fall back to BPartner email
		final CrossIndustryInvoiceType cii = new CiiMapper(null, null).map(invoice, recipientConfig);
		final XmlAssert xmlAssert = toXmlAssert(cii);

		// BT-34 must fall back to the BPartner email
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
								+ "/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty"
								+ "/ram:URIUniversalCommunication/ram:URIID")
				.as("BT-34: must fall back to BPartner email when no MailService is injected")
				.isEqualTo("invoice@seller.de");
	}

	// ===== Shared helpers =====

	private XmlAssert toXmlAssert(@NonNull final CrossIndustryInvoiceType cii) throws JAXBException
	{
		final JAXBContext ctx = JAXBContext.newInstance(CrossIndustryInvoiceType.class, ObjectFactory.class);
		final Marshaller marshaller = ctx.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		final StringWriter sw = new StringWriter();
		marshaller.marshal(new ObjectFactory().createCrossIndustryInvoice(cii), sw);
		final String xml = sw.toString();

		assertThat(xml).as("XML must not be empty").isNotEmpty();

		final Map<String, String> ns = new HashMap<>();
		ns.put("rsm", "urn:un:unece:uncefact:data:standard:CrossIndustryInvoice:100");
		ns.put("ram", "urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:100");
		ns.put("udt", "urn:un:unece:uncefact:data:standard:UnqualifiedDataType:100");
		ns.put("qdt", "urn:un:unece:uncefact:data:standard:QualifiedDataType:100");
		return XmlAssert.assertThat(xml).withNamespaceContext(ns);
	}
}
