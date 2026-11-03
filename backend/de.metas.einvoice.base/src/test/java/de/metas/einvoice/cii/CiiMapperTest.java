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
import org.adempiere.exceptions.AdempiereException;
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
import org.compiere.model.I_C_TaxCategory;
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
		currency.setDescription("EUR");
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
	 * Timezone regression (BT-2): {@code DateInvoiced} is a {@code timestamp without time zone},
	 * which JDBC returns as wall-clock midnight in the JVM default zone. Under a positive-offset
	 * zone (CI/prod run {@code TZ=Europe/Berlin}) a naive {@code toInstant().atOffset(UTC)}
	 * conversion shifts the rendered issue date one calendar day early. The mapper must render the
	 * invoice's calendar date unshifted.
	 *
	 * <p>The other tests here build dates as midnight-UTC, which bakes in the same false assumption
	 * as the bug and therefore does NOT reproduce it. This test instead builds the date the way JDBC
	 * actually returns it in production ({@code Timestamp.valueOf(LocalDateTime)} = JVM-local
	 * wall-clock midnight -> instant 2026-07-12T22:00Z under Europe/Berlin).
	 */
	@Test
	void map_issueDate_isNotShiftedByJvmTimezone_BT2() throws Exception
	{
		final java.util.TimeZone originalTz = java.util.TimeZone.getDefault();
		try
		{
			java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("Europe/Berlin"));

			// === Minimal seller org (must have a location so EN16931 BG-5 fail-fast does not trigger) ===
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
			currency.setDescription("EUR");
			saveRecord(currency);
			final I_C_DocType docType = newInstance(I_C_DocType.class);
			docType.setDocBaseType("ARI");
			saveRecord(docType);

			// === Invoice: DateInvoiced built the way JDBC returns a `timestamp without time zone`
			//   column — wall-clock midnight in the JVM default zone (Europe/Berlin here).
			//   Timestamp.valueOf(LocalDateTime) interprets in the JVM zone -> instant 2026-07-12T22:00Z,
			//   exactly the production case that a naive UTC conversion renders one day early (20260712). ===
			final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
			invoice.setAD_Org_ID(org.getAD_Org_ID());
			invoice.setDocumentNo("RE-2026-00013");
			invoice.setDateInvoiced(Timestamp.valueOf(LocalDate.of(2026, 7, 13).atStartOfDay()));
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

			// BT-2 issue date must reflect the stored calendar date (2026-07-13), NOT be shifted one day early.
			xmlAssert.valueByXPath(
							"//rsm:CrossIndustryInvoice/rsm:ExchangedDocument/ram:IssueDateTime/udt:DateTimeString")
					.as("BT-2 issue date must render the stored calendar date unshifted by the JVM timezone")
					.isEqualTo("20260713");
		}
		finally
		{
			java.util.TimeZone.setDefault(originalTz);
		}
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
		currency.setDescription("EUR");
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
		currency.setDescription("EUR");
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
		currency.setDescription("EUR");
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
		currency.setDescription("EUR");
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
		currency.setDescription("EUR");
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
		currency.setDescription("EUR");
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
		currency.setDescription("EUR");
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
		currency.setDescription("EUR");
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
	 * Test fixture bundling the org/seller/buyer/invoice skeleton shared by the seller-contact
	 * precedence tests (see {@code sellerContact_*} tests below). Contacts (AD_User records) are
	 * added by each test individually before calling {@link CiiMapper#map}.
	 */
	private static final class SellerContactFixture
	{
		private final I_C_BPartner sellerBP;
		private final I_C_Invoice invoice;

		private SellerContactFixture(final I_C_BPartner sellerBP, final I_C_Invoice invoice)
		{
			this.sellerBP = sellerBP;
			this.invoice = invoice;
		}
	}

	private SellerContactFixture newSellerContactFixture()
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
		currency.setDescription("EUR");
		saveRecord(currency);
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-2024-00701");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		saveRecord(invoice);

		return new SellerContactFixture(sellerBP, invoice);
	}

	private void assertSellerContactPersonName(@NonNull final I_C_Invoice invoice, @NonNull final String expectedPersonName, @NonNull final String description) throws Exception
	{
		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();
		final CrossIndustryInvoiceType cii = new CiiMapper().map(invoice, recipientConfig);
		final XmlAssert xmlAssert = toXmlAssert(cii);

		final String contactXPath = "//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
				+ "/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:DefinedTradeContact";

		xmlAssert.valueByXPath(contactXPath + "/ram:PersonName")
				.as(description)
				.isEqualTo(expectedPersonName);
	}

	/**
	 * A tier-1 IsSalesContact contact must win even over an earlier (lower SeqNo) contact
	 * that has a phone number — the explicit flag match ignores the phone heuristic entirely.
	 */
	@Test
	void sellerContact_salesContactWins_evenWithoutPhone() throws Exception
	{
		final SellerContactFixture fx = newSellerContactFixture();

		final I_AD_User earlierWithPhone = newInstance(I_AD_User.class);
		earlierWithPhone.setC_BPartner_ID(fx.sellerBP.getC_BPartner_ID());
		earlierWithPhone.setName("Erika Musterfrau");
		earlierWithPhone.setPhone("+49 30 111111");
		earlierWithPhone.setIsDefaultContact(true);
		earlierWithPhone.setSeqNo(10);
		saveRecord(earlierWithPhone);

		final I_AD_User salesContactNoPhone = newInstance(I_AD_User.class);
		salesContactNoPhone.setC_BPartner_ID(fx.sellerBP.getC_BPartner_ID());
		salesContactNoPhone.setName("Max Mustermann");
		salesContactNoPhone.setIsSalesContact(true);
		salesContactNoPhone.setSeqNo(20);
		saveRecord(salesContactNoPhone);

		assertSellerContactPersonName(fx.invoice, "Max Mustermann",
				"IsSalesContact must win over an earlier IsDefaultContact contact with a phone");
	}

	/**
	 * When no contact has IsSalesContact, the tier-2 IsDefaultContact contact must be
	 * selected over a plain (unflagged) contact.
	 */
	@Test
	void sellerContact_defaultContactWins_whenNoSalesContact() throws Exception
	{
		final SellerContactFixture fx = newSellerContactFixture();

		final I_AD_User plainContact = newInstance(I_AD_User.class);
		plainContact.setC_BPartner_ID(fx.sellerBP.getC_BPartner_ID());
		plainContact.setName("Erika Musterfrau");
		plainContact.setSeqNo(10);
		saveRecord(plainContact);

		final I_AD_User defaultContact = newInstance(I_AD_User.class);
		defaultContact.setC_BPartner_ID(fx.sellerBP.getC_BPartner_ID());
		defaultContact.setName("Max Mustermann");
		defaultContact.setIsDefaultContact(true);
		defaultContact.setSeqNo(20);
		saveRecord(defaultContact);

		assertSellerContactPersonName(fx.invoice, "Max Mustermann",
				"IsDefaultContact must win over a plain contact when no IsSalesContact exists");
	}

	/**
	 * When no contact has either flag, fall back to today's rule — prefer the first
	 * (by SeqNo) contact that has a phone number.
	 */
	@Test
	void sellerContact_noFlags_prefersPhoneOverSeqNo() throws Exception
	{
		final SellerContactFixture fx = newSellerContactFixture();

		final I_AD_User firstNoPhone = newInstance(I_AD_User.class);
		firstNoPhone.setC_BPartner_ID(fx.sellerBP.getC_BPartner_ID());
		firstNoPhone.setName("Erika Musterfrau");
		firstNoPhone.setSeqNo(10);
		saveRecord(firstNoPhone);

		final I_AD_User laterWithPhone = newInstance(I_AD_User.class);
		laterWithPhone.setC_BPartner_ID(fx.sellerBP.getC_BPartner_ID());
		laterWithPhone.setName("Max Mustermann");
		laterWithPhone.setPhone("+49 30 222222");
		laterWithPhone.setSeqNo(20);
		saveRecord(laterWithPhone);

		assertSellerContactPersonName(fx.invoice, "Max Mustermann",
				"without any flag, the first-by-SeqNo contact with a phone must win over an earlier contact without a phone");
	}

	/**
	 * When no contact has either flag AND none has a phone, fall back to the first
	 * (by SeqNo) contact overall.
	 */
	@Test
	void sellerContact_noFlagsNoPhone_prefersFirstBySeqNo() throws Exception
	{
		final SellerContactFixture fx = newSellerContactFixture();

		final I_AD_User first = newInstance(I_AD_User.class);
		first.setC_BPartner_ID(fx.sellerBP.getC_BPartner_ID());
		first.setName("Erika Musterfrau");
		first.setSeqNo(10);
		saveRecord(first);

		final I_AD_User second = newInstance(I_AD_User.class);
		second.setC_BPartner_ID(fx.sellerBP.getC_BPartner_ID());
		second.setName("Max Mustermann");
		second.setSeqNo(20);
		saveRecord(second);

		assertSellerContactPersonName(fx.invoice, "Erika Musterfrau",
				"with no flags and no phone anywhere, the first-by-SeqNo contact must win");
	}

	/**
	 * An inactive contact must never be selected, even when it is the only
	 * IsSalesContact match — an active plain contact must be selected instead.
	 */
	@Test
	void sellerContact_inactiveNeverSelected() throws Exception
	{
		final SellerContactFixture fx = newSellerContactFixture();

		final I_AD_User inactiveSalesContact = newInstance(I_AD_User.class);
		inactiveSalesContact.setC_BPartner_ID(fx.sellerBP.getC_BPartner_ID());
		inactiveSalesContact.setName("Inactive Sales");
		inactiveSalesContact.setIsSalesContact(true);
		inactiveSalesContact.setSeqNo(10);
		inactiveSalesContact.setIsActive(false);
		saveRecord(inactiveSalesContact);

		final I_AD_User activePlainContact = newInstance(I_AD_User.class);
		activePlainContact.setC_BPartner_ID(fx.sellerBP.getC_BPartner_ID());
		activePlainContact.setName("Active Plain");
		activePlainContact.setSeqNo(20);
		saveRecord(activePlainContact);

		assertSellerContactPersonName(fx.invoice, "Active Plain",
				"an inactive IsSalesContact contact must never be selected; the active contact must be used instead");
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
		currency.setDescription("EUR");
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
		currency.setDescription("EUR");
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
		currency.setDescription("EUR");
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
		currency.setDescription("EUR");
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

	// ===== Silent factoring (stille Zession): BT-84 payee IBAN =====

	/**
	 * Bill partner is factoring (IsFactoring=Y) and a factorer BPartner (IsFactorer=Y,
	 * same AD_Org_ID) with a default bank account exists: BT-84 must carry the FACTORER's IBAN, never
	 * the seller's, and no BG-10 PayeeTradeParty may be present (the assignment stays undisclosed).
	 */
	@Test
	void payment_factoring_usesFactorerIban_noPayeeParty() throws Exception
	{
		final FactoringFixture fx = newFactoringFixture(true, "T"); // PaymentRule T → UNCL4461 code 30

		// Seller has its OWN default IBAN — must NOT be the one used on a factored invoice
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		currency.setDescription("EUR");
		saveRecord(currency);
		final I_C_BP_BankAccount sellerBankAccount = newInstance(I_C_BP_BankAccount.class);
		sellerBankAccount.setC_BPartner_ID(fx.sellerBP.getC_BPartner_ID());
		sellerBankAccount.setC_Currency_ID(currency.getC_Currency_ID());
		sellerBankAccount.setIBAN("DE89370400440532013000");
		sellerBankAccount.setIsDefault(true);
		sellerBankAccount.setAD_Org_ID(fx.org.getAD_Org_ID());
		saveRecord(sellerBankAccount);

		// Factorer BPartner (IsFactorer=Y, same AD_Org_ID) with its own default IBAN
		final I_C_BPartner factorerBP = newInstance(I_C_BPartner.class);
		factorerBP.setName("Factorer AG");
		factorerBP.setIsFactorer(true);
		factorerBP.setAD_Org_ID(fx.org.getAD_Org_ID());
		saveRecord(factorerBP);
		final I_C_BP_BankAccount factorerBankAccount = newInstance(I_C_BP_BankAccount.class);
		factorerBankAccount.setC_BPartner_ID(factorerBP.getC_BPartner_ID());
		factorerBankAccount.setC_Currency_ID(currency.getC_Currency_ID());
		factorerBankAccount.setIBAN("DE11500105170648489890");
		factorerBankAccount.setIsDefault(true);
		factorerBankAccount.setAD_Org_ID(fx.org.getAD_Org_ID());
		saveRecord(factorerBankAccount);

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();
		final CrossIndustryInvoiceType cii = new CiiMapper().map(fx.invoice, recipientConfig);
		final XmlAssert xmlAssert = toXmlAssert(cii);

		final String paymentMeans = "//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
				+ "/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementPaymentMeans";

		// BT-84: the FACTORER's IBAN, not the seller's
		xmlAssert.valueByXPath(paymentMeans + "/ram:PayeePartyCreditorFinancialAccount/ram:IBANID")
				.as("BT-84 must carry the factorer IBAN on a factored invoice")
				.isEqualTo("DE11500105170648489890");

		// BG-10: PayeeTradeParty must never be populated (assignment stays undisclosed)
		xmlAssert.nodesByXPath(paymentMeans + "/ram:PayeeTradeParty").doNotExist();
	}

	/**
	 * Regression guard — bill partner is not factoring: BT-84 must carry the seller's own
	 * default IBAN, unchanged behaviour.
	 */
	@Test
	void payment_nonFactoring_usesSellerIban() throws Exception
	{
		final FactoringFixture fx = newFactoringFixture(false, "T"); // PaymentRule T → UNCL4461 code 30

		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		currency.setDescription("EUR");
		saveRecord(currency);
		final I_C_BP_BankAccount sellerBankAccount = newInstance(I_C_BP_BankAccount.class);
		sellerBankAccount.setC_BPartner_ID(fx.sellerBP.getC_BPartner_ID());
		sellerBankAccount.setC_Currency_ID(currency.getC_Currency_ID());
		sellerBankAccount.setIBAN("DE89370400440532013000");
		sellerBankAccount.setIsDefault(true);
		sellerBankAccount.setAD_Org_ID(fx.org.getAD_Org_ID());
		saveRecord(sellerBankAccount);

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();
		final CrossIndustryInvoiceType cii = new CiiMapper().map(fx.invoice, recipientConfig);
		final XmlAssert xmlAssert = toXmlAssert(cii);

		xmlAssert.valueByXPath("//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
						+ "/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementPaymentMeans"
						+ "/ram:PayeePartyCreditorFinancialAccount/ram:IBANID")
				.as("BT-84 must carry the seller IBAN when the bill partner is not factoring")
				.isEqualTo("DE89370400440532013000");
	}

	/**
	 * Bill partner is factoring but NO factorer BPartner (IsFactorer=Y) exists for the
	 * invoice's AD_Org_ID: mapping must throw a user-validation error naming the org, and must
	 * never fall back to the seller's IBAN.
	 */
	@Test
	void payment_factoring_noFactorer_throwsUserValidationError()
	{
		final FactoringFixture fx = newFactoringFixture(true, "T");

		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		currency.setDescription("EUR");
		saveRecord(currency);
		final I_C_BP_BankAccount sellerBankAccount = newInstance(I_C_BP_BankAccount.class);
		sellerBankAccount.setC_BPartner_ID(fx.sellerBP.getC_BPartner_ID());
		sellerBankAccount.setC_Currency_ID(currency.getC_Currency_ID());
		sellerBankAccount.setIBAN("DE89370400440532013000");
		sellerBankAccount.setIsDefault(true);
		sellerBankAccount.setAD_Org_ID(fx.org.getAD_Org_ID());
		saveRecord(sellerBankAccount);

		// No IsFactorer=Y C_BPartner exists at all for this AD_Org_ID

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		assertThatThrownBy(() -> new CiiMapper().map(fx.invoice, recipientConfig))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("AD_Org_ID=" + fx.org.getAD_Org_ID());
	}

	/**
	 * More than one factorer (C_BPartner.IsFactorer=Y) exists for the invoice's AD_Org_ID
	 * (e.g. a deactivated old factorer left alongside a new one — the DB uniqueness guard only covers
	 * active rows): mapping must throw a user-validation error reporting the ambiguity, and must never
	 * fall back to the seller's IBAN.
	 */
	@Test
	void payment_factoring_multipleFactorers_throwsUserValidationError()
	{
		final FactoringFixture fx = newFactoringFixture(true, "T");

		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		currency.setDescription("EUR");
		saveRecord(currency);
		final I_C_BP_BankAccount sellerBankAccount = newInstance(I_C_BP_BankAccount.class);
		sellerBankAccount.setC_BPartner_ID(fx.sellerBP.getC_BPartner_ID());
		sellerBankAccount.setC_Currency_ID(currency.getC_Currency_ID());
		sellerBankAccount.setIBAN("DE89370400440532013000");
		sellerBankAccount.setIsDefault(true);
		sellerBankAccount.setAD_Org_ID(fx.org.getAD_Org_ID());
		saveRecord(sellerBankAccount);

		// TWO IsFactorer=Y C_BPartners in the same AD_Org_ID → the factorer is ambiguous
		final I_C_BPartner factorer1 = newInstance(I_C_BPartner.class);
		factorer1.setName("Factorer One AG");
		factorer1.setIsFactorer(true);
		factorer1.setAD_Org_ID(fx.org.getAD_Org_ID());
		saveRecord(factorer1);
		final I_C_BPartner factorer2 = newInstance(I_C_BPartner.class);
		factorer2.setName("Factorer Two AG");
		factorer2.setIsFactorer(true);
		factorer2.setAD_Org_ID(fx.org.getAD_Org_ID());
		saveRecord(factorer2);

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		assertThatThrownBy(() -> new CiiMapper().map(fx.invoice, recipientConfig))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Multiple factorers")
				.hasMessageContaining("AD_Org_ID=" + fx.org.getAD_Org_ID());
	}

	/**
	 * The factorer BPartner exists but has no bank account / no IBAN: mapping must throw a
	 * user-validation error naming the factorer, and must never fall back to the seller's IBAN.
	 */
	@Test
	void payment_factoring_factorerHasNoIban_throwsUserValidationError()
	{
		final FactoringFixture fx = newFactoringFixture(true, "T");

		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		currency.setDescription("EUR");
		saveRecord(currency);
		final I_C_BP_BankAccount sellerBankAccount = newInstance(I_C_BP_BankAccount.class);
		sellerBankAccount.setC_BPartner_ID(fx.sellerBP.getC_BPartner_ID());
		sellerBankAccount.setC_Currency_ID(currency.getC_Currency_ID());
		sellerBankAccount.setIBAN("DE89370400440532013000");
		sellerBankAccount.setIsDefault(true);
		sellerBankAccount.setAD_Org_ID(fx.org.getAD_Org_ID());
		saveRecord(sellerBankAccount);

		// Factorer exists but has NO bank account at all
		final I_C_BPartner factorerBP = newInstance(I_C_BPartner.class);
		factorerBP.setName("Factorer AG");
		factorerBP.setIsFactorer(true);
		factorerBP.setAD_Org_ID(fx.org.getAD_Org_ID());
		saveRecord(factorerBP);

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		assertThatThrownBy(() -> new CiiMapper().map(fx.invoice, recipientConfig))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("C_BPartner_ID=" + factorerBP.getC_BPartner_ID());
	}

	/**
	 * Test fixture bundling the org/seller/bill-partner/invoice skeleton shared by the silent-factoring
	 * payment-means tests above. Bank accounts and the optional factorer BPartner are added by each
	 * test individually.
	 */
	private static final class FactoringFixture
	{
		private final I_AD_Org org;
		private final I_C_BPartner sellerBP;
		private final I_C_Invoice invoice;

		private FactoringFixture(final I_AD_Org org, final I_C_BPartner sellerBP, final I_C_Invoice invoice)
		{
			this.org = org;
			this.sellerBP = sellerBP;
			this.invoice = invoice;
		}
	}

	private FactoringFixture newFactoringFixture(final boolean billPartnerIsFactoring, final String paymentRule)
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

		// === Bill partner (invoice.C_BPartner_ID) — the factoring flag lives here ===
		final I_C_Country billCountry = newInstance(I_C_Country.class);
		billCountry.setCountryCode("DE");
		saveRecord(billCountry);
		final I_C_Location billLocation = newInstance(I_C_Location.class);
		billLocation.setC_Country_ID(billCountry.getC_Country_ID());
		saveRecord(billLocation);
		final I_C_BPartner billBP = newInstance(I_C_BPartner.class);
		billBP.setName("Bill Partner AG");
		billBP.setIsFactoring(billPartnerIsFactoring);
		saveRecord(billBP);
		final I_C_BPartner_Location billBPLoc = newInstance(I_C_BPartner_Location.class);
		billBPLoc.setC_BPartner_ID(billBP.getC_BPartner_ID());
		billBPLoc.setC_Location_ID(billLocation.getC_Location_ID());
		saveRecord(billBPLoc);

		// === Currency + DocType ===
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		currency.setDescription("EUR");
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
		invoice.setC_BPartner_ID(billBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(billBPLoc.getC_BPartner_Location_ID());
		invoice.setPaymentRule(paymentRule);
		saveRecord(invoice);

		return new FactoringFixture(org, sellerBP, invoice);
	}

	/**
	 * REPRO for the KoSIT BR-S-08 tax-included bug (see investigation brief): on a tax-included
	 * price list, {@code C_InvoiceLine.LineNetAmt} holds the GROSS line amount (confirmed by
	 * {@code MInvoiceTax.calculateTaxFromLines}, which computes
	 * {@code TaxBaseAmt = SUM(LineNetAmt) - SUM(TaxAmt)} whenever {@code IsTaxIncluded=Y} — i.e.
	 * {@code LineNetAmt} is treated as the tax-inclusive total, not the net). {@link CiiMapper} has
	 * NO {@code IsTaxIncluded} handling and emits {@code LineNetAmt} raw for BT-131/BT-146 and
	 * {@code invoice.getTotalLines()} raw for BT-106/BT-109 — both tax-EXCLUSIVE per EN16931 — while
	 * BT-116 ({@code C_InvoiceTax.TaxBaseAmt}) is correctly net. This mismatch is exactly what fails
	 * KoSIT BR-S-08 in production (a real tax-included invoice: LineNetAmt=100.00 gross,
	 * TaxBaseAmt=84.03 net, TaxAmt=15.97, 19%).
	 *
	 * <p>This test asserts the EN16931-CORRECT (tax-exclusive) values the mapper SHOULD emit once
	 * fixed. It currently FAILS (RED) because the mapper emits the raw gross amounts instead.
	 */
	@Test
	void tax_included_line_and_header_amounts_must_be_net_not_gross() throws Exception
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
		currency.setDescription("EUR");
		currency.setStdPrecision(2);
		saveRecord(currency);
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice: IsTaxIncluded=Y, single line @19%, mirrors a real production tax-included invoice ===
		// LineNetAmt=100.00 (GROSS, per current metasfresh semantics on a tax-included price list),
		// TaxBaseAmt=84.03 (NET, correctly computed by MInvoiceTax.calculateTaxFromLines),
		// TaxAmt=15.97, GrandTotal=100.00 (gross total is unaffected by the bug).
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-2024-00700");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		invoice.setIsTaxIncluded(true);
		// On a tax-included invoice TotalLines is the gross sum (100.00); the tax-exclusive value is 84.03.
		invoice.setTotalLines(new BigDecimal("100.00"));
		invoice.setGrandTotal(new BigDecimal("100.00"));
		saveRecord(invoice);

		// === UOM + Product ===
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName("Stück");
		uom.setX12DE355("PCE");
		saveRecord(uom);
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName("Testprodukt");
		product.setValue("TP-700");
		saveRecord(product);

		// === Tax: 19% standard 'S' ===
		final I_C_TaxCategory taxCategory = newInstance(I_C_TaxCategory.class);
		saveRecord(taxCategory);
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName("MWSt 19%");
		tax.setEN16931VATCategory("S");
		tax.setRate(new BigDecimal("19"));
		tax.setC_TaxCategory_ID(taxCategory.getC_TaxCategory_ID());
		tax.setValidFrom(Timestamp.from(LocalDate.of(2000, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant()));
		saveRecord(tax);

		// === C_InvoiceTax: correctly net (BT-116/BT-117 are NOT part of the bug) ===
		final I_C_InvoiceTax invoiceTax = newInstance(I_C_InvoiceTax.class);
		invoiceTax.setC_Invoice_ID(invoice.getC_Invoice_ID());
		invoiceTax.setC_Tax_ID(tax.getC_Tax_ID());
		invoiceTax.setIsTaxIncluded(true);
		invoiceTax.setTaxBaseAmt(new BigDecimal("84.03")); // BT-116 — already correct (net)
		invoiceTax.setTaxAmt(new BigDecimal("15.97"));     // BT-117 — already correct
		saveRecord(invoiceTax);

		// === Invoice line: LineNetAmt/PriceActual GROSS (100.00), as on a tax-included price list ===
		final I_C_InvoiceLine line = newInstance(I_C_InvoiceLine.class);
		line.setC_Invoice_ID(invoice.getC_Invoice_ID());
		line.setLine(10);
		line.setM_Product_ID(product.getM_Product_ID());
		line.setC_UOM_ID(uom.getC_UOM_ID());
		line.setC_Tax_ID(tax.getC_Tax_ID());
		line.setQtyInvoiced(new BigDecimal("1"));
		line.setPriceActual(new BigDecimal("100.00"));
		line.setLineNetAmt(new BigDecimal("100.00"));
		line.setTaxAmt(new BigDecimal("15.97"));
		saveRecord(line);

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		final CrossIndustryInvoiceType cii = new CiiMapper().map(invoice, recipientConfig);
		final XmlAssert xmlAssert = toXmlAssert(cii);

		final String lineBase = "//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
				+ "/ram:IncludedSupplyChainTradeLineItem[1]";
		final String summation = "//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
				+ "/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementHeaderMonetarySummation";

		// BT-146 Item net price is the tax-exclusive net (84.03), not the gross PriceActual (100.00).
		xmlAssert.valueByXPath(lineBase + "/ram:SpecifiedLineTradeAgreement/ram:NetPriceProductTradePrice/ram:ChargeAmount")
				.isEqualTo("84.03");

		// BT-131 Line net amount equals the net (84.03), matching BT-116 (BR-S-08/BR-CO-10).
		xmlAssert.valueByXPath(lineBase + "/ram:SpecifiedLineTradeSettlement"
						+ "/ram:SpecifiedTradeSettlementLineMonetarySummation/ram:LineTotalAmount")
				.isEqualTo("84.03");

		// BT-106 Sum of line net amounts equals SUM(TaxBaseAmt) = 84.03, not invoice.TotalLines (100.00).
		xmlAssert.valueByXPath(summation + "/ram:LineTotalAmount").isEqualTo("84.03");

		// BT-109 Tax basis total equals 84.03 for the same reason.
		xmlAssert.valueByXPath(summation + "/ram:TaxBasisTotalAmount").isEqualTo("84.03");
	}

	/**
	 * Multi-line rounding-reconciliation proof for the tax-included fix (see investigation brief §
	 * "THE INVARIANTS"): two lines, same 19% tax, {@code LineNetAmt} 100.00 + 100.00 (gross). A NAIVE
	 * per-line {@code calculateBaseAmt} conversion yields 84.03 + 84.03 = 168.06 — one cent short of
	 * the tax breakdown's round-of-sum {@code TaxBaseAmt}=168.07 — which would still fail KoSIT
	 * BR-S-08/BR-CO-10 (BT-131 sum must equal BT-116 exactly). The fix must reconcile the per-line
	 * BT-131 values so they sum EXACTLY to 168.07 (e.g. 84.03 + 84.04).
	 */
	@Test
	void tax_included_multiLine_bt131_reconciles_to_taxBaseAmt() throws Exception
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
		currency.setDescription("EUR");
		currency.setStdPrecision(2);
		saveRecord(currency);
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice: IsTaxIncluded=Y, two lines @19%, TotalLines/GrandTotal = 200.00 (gross) ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-2024-00701");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		invoice.setIsTaxIncluded(true);
		invoice.setTotalLines(new BigDecimal("200.00"));
		invoice.setGrandTotal(new BigDecimal("200.00"));
		saveRecord(invoice);

		// === UOM + Products ===
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName("Stück");
		uom.setX12DE355("PCE");
		saveRecord(uom);
		final I_M_Product product1 = newInstance(I_M_Product.class);
		product1.setName("Testprodukt A");
		product1.setValue("TP-701-A");
		saveRecord(product1);
		final I_M_Product product2 = newInstance(I_M_Product.class);
		product2.setName("Testprodukt B");
		product2.setValue("TP-701-B");
		saveRecord(product2);

		// === Tax: 19% standard 'S' ===
		final I_C_TaxCategory taxCategory = newInstance(I_C_TaxCategory.class);
		saveRecord(taxCategory);
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName("MWSt 19%");
		tax.setEN16931VATCategory("S");
		tax.setRate(new BigDecimal("19"));
		tax.setC_TaxCategory_ID(taxCategory.getC_TaxCategory_ID());
		tax.setValidFrom(Timestamp.from(LocalDate.of(2000, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant()));
		saveRecord(tax);

		// === C_InvoiceTax: round-of-sum values (168.07 / 31.93), NOT the naive sum-of-rounds (168.06) ===
		final I_C_InvoiceTax invoiceTax = newInstance(I_C_InvoiceTax.class);
		invoiceTax.setC_Invoice_ID(invoice.getC_Invoice_ID());
		invoiceTax.setC_Tax_ID(tax.getC_Tax_ID());
		invoiceTax.setIsTaxIncluded(true);
		invoiceTax.setTaxBaseAmt(new BigDecimal("168.07")); // BT-116
		invoiceTax.setTaxAmt(new BigDecimal("31.93"));       // BT-117
		saveRecord(invoiceTax);

		// === Two invoice lines: LineNetAmt/PriceActual GROSS (100.00 each) ===
		final I_C_InvoiceLine line1 = newInstance(I_C_InvoiceLine.class);
		line1.setC_Invoice_ID(invoice.getC_Invoice_ID());
		line1.setLine(10);
		line1.setM_Product_ID(product1.getM_Product_ID());
		line1.setC_UOM_ID(uom.getC_UOM_ID());
		line1.setC_Tax_ID(tax.getC_Tax_ID());
		line1.setQtyInvoiced(new BigDecimal("1"));
		line1.setPriceActual(new BigDecimal("100.00"));
		line1.setLineNetAmt(new BigDecimal("100.00"));
		line1.setTaxAmt(new BigDecimal("15.97"));
		saveRecord(line1);

		final I_C_InvoiceLine line2 = newInstance(I_C_InvoiceLine.class);
		line2.setC_Invoice_ID(invoice.getC_Invoice_ID());
		line2.setLine(20);
		line2.setM_Product_ID(product2.getM_Product_ID());
		line2.setC_UOM_ID(uom.getC_UOM_ID());
		line2.setC_Tax_ID(tax.getC_Tax_ID());
		line2.setQtyInvoiced(new BigDecimal("1"));
		line2.setPriceActual(new BigDecimal("100.00"));
		line2.setLineNetAmt(new BigDecimal("100.00"));
		line2.setTaxAmt(new BigDecimal("15.97"));
		saveRecord(line2);

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		final CrossIndustryInvoiceType cii = new CiiMapper().map(invoice, recipientConfig);
		final XmlAssert xmlAssert = toXmlAssert(cii);

		final String line1Base = "//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
				+ "/ram:IncludedSupplyChainTradeLineItem[1]";
		final String line2Base = "//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
				+ "/ram:IncludedSupplyChainTradeLineItem[2]";
		final String summation = "//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
				+ "/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementHeaderMonetarySummation";

		// BT-131 line 1 net amount: natural net (84.03) — no delta absorbed (not the last line of the group).
		xmlAssert.valueByXPath(line1Base + "/ram:SpecifiedLineTradeSettlement"
						+ "/ram:SpecifiedTradeSettlementLineMonetarySummation/ram:LineTotalAmount")
				.isEqualTo("84.03");

		// BT-131 line 2 net amount: natural net (84.03) PLUS the 0.01 rounding delta = 84.04 — this is
		// the crux: a naive per-line conversion would emit 84.03 here too, summing to 168.06 (WRONG).
		xmlAssert.valueByXPath(line2Base + "/ram:SpecifiedLineTradeSettlement"
						+ "/ram:SpecifiedTradeSettlementLineMonetarySummation/ram:LineTotalAmount")
				.isEqualTo("84.04");

		// BT-106/BT-109: sum of line nets MUST equal SUM(TaxBaseAmt) = 168.07 EXACTLY (BR-CO-10/BR-S-08).
		xmlAssert.valueByXPath(summation + "/ram:LineTotalAmount").isEqualTo("168.07");
		xmlAssert.valueByXPath(summation + "/ram:TaxBasisTotalAmount").isEqualTo("168.07");
	}

	/**
	 * Guards the BT-146 gate against a whole-tax regression on the tax-EXCLUDED path.
	 *
	 * <p>{@link org.compiere.model.I_C_Tax} with {@code IsWholeTax=Y} makes
	 * {@code Tax.calculateBaseAmt} return {@code ZERO} regardless of the {@code taxIncluded} flag. If the
	 * BT-146 gate keys on whole-tax (rather than on the invoice's {@code IsTaxIncluded} flag), a
	 * tax-EXCLUDED invoice whose line uses a whole-tax {@code C_Tax} would emit BT-146 = 0.00 instead of
	 * the raw {@code PriceActual}. On the tax-excluded path BT-146 must be the raw {@code PriceActual}.
	 */
	@Test
	void tax_excluded_wholeTax_line_bt146_is_raw_priceActual_not_zero() throws Exception
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
		currency.setDescription("EUR");
		currency.setStdPrecision(2);
		saveRecord(currency);
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice: IsTaxIncluded=N (tax-EXCLUDED) ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-2024-00702");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		invoice.setIsTaxIncluded(false);
		invoice.setTotalLines(new BigDecimal("100.00"));
		invoice.setGrandTotal(new BigDecimal("100.00"));
		saveRecord(invoice);

		// === UOM + Product ===
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName("Stück");
		uom.setX12DE355("PCE");
		saveRecord(uom);
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName("Testprodukt");
		product.setValue("TP-702");
		saveRecord(product);

		// === Tax: whole-tax (IsWholeTax=Y). Tax.calculateBaseAmt returns ZERO for such a tax. ===
		final I_C_TaxCategory taxCategory = newInstance(I_C_TaxCategory.class);
		saveRecord(taxCategory);
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName("Whole tax");
		tax.setEN16931VATCategory("S");
		tax.setRate(new BigDecimal("19"));
		tax.setIsWholeTax(true);
		tax.setC_TaxCategory_ID(taxCategory.getC_TaxCategory_ID());
		tax.setValidFrom(Timestamp.from(LocalDate.of(2000, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant()));
		saveRecord(tax);

		// === Invoice line: PriceActual/LineNetAmt = 100.00 (tax-excluded: raw net) ===
		final I_C_InvoiceLine line = newInstance(I_C_InvoiceLine.class);
		line.setC_Invoice_ID(invoice.getC_Invoice_ID());
		line.setLine(10);
		line.setM_Product_ID(product.getM_Product_ID());
		line.setC_UOM_ID(uom.getC_UOM_ID());
		line.setC_Tax_ID(tax.getC_Tax_ID());
		line.setQtyInvoiced(new BigDecimal("1"));
		line.setPriceActual(new BigDecimal("100.00"));
		line.setLineNetAmt(new BigDecimal("100.00"));
		saveRecord(line);

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		final CrossIndustryInvoiceType cii = new CiiMapper().map(invoice, recipientConfig);
		final XmlAssert xmlAssert = toXmlAssert(cii);

		final String lineBase = "//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
				+ "/ram:IncludedSupplyChainTradeLineItem[1]";

		// BT-146 on a tax-EXCLUDED invoice MUST be the raw PriceActual (100.00), NOT 0.00 — even when
		// the line's C_Tax is a whole-tax (which would make calculateBaseAmt return ZERO if wrongly gated).
		xmlAssert.valueByXPath(lineBase + "/ram:SpecifiedLineTradeAgreement/ram:NetPriceProductTradePrice/ram:ChargeAmount")
				.isEqualTo("100.00");
	}

	/**
	 * Fail-fast on a tax-included invoice whose line references a {@code C_Tax} that has NO matching
	 * {@code C_InvoiceTax} breakdown row. Without that row the BT-131 reconciliation has no
	 * {@code TaxBaseAmt} anchor to satisfy BR-S-08/BR-CO-10, so the mapper must throw (with the
	 * offending {@code C_Tax_ID} + {@code C_Invoice_ID}) rather than silently emit broken XML — matching
	 * the fail-fast style of the other mandatory-data checks in this class.
	 */
	@Test
	void tax_included_missingInvoiceTaxRow_throwsWithIds() throws Exception
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
		currency.setDescription("EUR");
		currency.setStdPrecision(2);
		saveRecord(currency);
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice: IsTaxIncluded=Y ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-2024-00703");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		invoice.setIsTaxIncluded(true);
		invoice.setTotalLines(new BigDecimal("100.00"));
		invoice.setGrandTotal(new BigDecimal("100.00"));
		saveRecord(invoice);

		// === UOM + Product ===
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName("Stück");
		uom.setX12DE355("PCE");
		saveRecord(uom);
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName("Testprodukt");
		product.setValue("TP-703");
		saveRecord(product);

		// === Tax: 19% standard 'S' (NOT whole-tax) ===
		final I_C_TaxCategory taxCategory = newInstance(I_C_TaxCategory.class);
		saveRecord(taxCategory);
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName("MWSt 19%");
		tax.setEN16931VATCategory("S");
		tax.setRate(new BigDecimal("19"));
		tax.setC_TaxCategory_ID(taxCategory.getC_TaxCategory_ID());
		tax.setValidFrom(Timestamp.from(LocalDate.of(2000, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant()));
		saveRecord(tax);

		// === Invoice line references the tax — but DELIBERATELY no C_InvoiceTax breakdown row exists ===
		final I_C_InvoiceLine line = newInstance(I_C_InvoiceLine.class);
		line.setC_Invoice_ID(invoice.getC_Invoice_ID());
		line.setLine(10);
		line.setM_Product_ID(product.getM_Product_ID());
		line.setC_UOM_ID(uom.getC_UOM_ID());
		line.setC_Tax_ID(tax.getC_Tax_ID());
		line.setQtyInvoiced(new BigDecimal("1"));
		line.setPriceActual(new BigDecimal("100.00"));
		line.setLineNetAmt(new BigDecimal("100.00"));
		line.setTaxAmt(new BigDecimal("15.97"));
		saveRecord(line);

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		assertThatThrownBy(() -> new CiiMapper().map(invoice, recipientConfig))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("C_Tax_ID=" + tax.getC_Tax_ID())
				.hasMessageContaining("C_Invoice_ID=" + invoice.getC_Invoice_ID());
	}

	/**
	 * Regression guard for tax-included invoices whose tax is DOCUMENT-LEVEL
	 * ({@code C_Tax.IsDocumentLevel=Y}): there the per-line {@code C_InvoiceLine.TaxAmt} is 0 (the tax
	 * is computed on the document total, not per line), so {@code LineNetAmt − TaxAmt = 100.00 − 0 =
	 * 100.00} is the GROSS amount, NOT the net. BT-131 must therefore be DERIVED via
	 * {@link Tax#calculateBaseAmt} (→ 84.03), never taken as {@code LineNetAmt − TaxAmt}.
	 * {@code C_InvoiceTax.TaxBaseAmt} carries the correct net (84.03).
	 */
	@Test
	void tax_included_documentLevelTax_lineTaxAmtZero_bt131_isNet_notGross() throws Exception
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
		currency.setDescription("EUR");
		currency.setStdPrecision(2);
		saveRecord(currency);
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice: IsTaxIncluded=Y ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-2024-00704");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		invoice.setIsTaxIncluded(true);
		invoice.setTotalLines(new BigDecimal("100.00"));
		invoice.setGrandTotal(new BigDecimal("100.00"));
		saveRecord(invoice);

		// === UOM + Product ===
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName("Stück");
		uom.setX12DE355("PCE");
		saveRecord(uom);
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName("Testprodukt");
		product.setValue("TP-704");
		saveRecord(product);

		// === Tax: 19% standard 'S', DOCUMENT-LEVEL — NOT whole-tax ===
		final I_C_TaxCategory taxCategory = newInstance(I_C_TaxCategory.class);
		saveRecord(taxCategory);
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName("Normaler Steuersatz 19% (DE)");
		tax.setEN16931VATCategory("S");
		tax.setRate(new BigDecimal("19"));
		tax.setIsDocumentLevel(true);
		tax.setC_TaxCategory_ID(taxCategory.getC_TaxCategory_ID());
		tax.setValidFrom(Timestamp.from(LocalDate.of(2000, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant()));
		saveRecord(tax);

		// === C_InvoiceTax: net base (BT-116) correctly booked at document level ===
		final I_C_InvoiceTax invoiceTax = newInstance(I_C_InvoiceTax.class);
		invoiceTax.setC_Invoice_ID(invoice.getC_Invoice_ID());
		invoiceTax.setC_Tax_ID(tax.getC_Tax_ID());
		invoiceTax.setIsTaxIncluded(true);
		invoiceTax.setTaxBaseAmt(new BigDecimal("84.03"));
		invoiceTax.setTaxAmt(new BigDecimal("15.97"));
		saveRecord(invoiceTax);

		// === Invoice line: LineNetAmt/PriceActual GROSS (100.00), C_InvoiceLine.TaxAmt = 0 (document-level) ===
		final I_C_InvoiceLine line = newInstance(I_C_InvoiceLine.class);
		line.setC_Invoice_ID(invoice.getC_Invoice_ID());
		line.setLine(10);
		line.setM_Product_ID(product.getM_Product_ID());
		line.setC_UOM_ID(uom.getC_UOM_ID());
		line.setC_Tax_ID(tax.getC_Tax_ID());
		line.setQtyInvoiced(new BigDecimal("1"));
		line.setPriceActual(new BigDecimal("100.00"));
		line.setLineNetAmt(new BigDecimal("100.00"));
		line.setTaxAmt(BigDecimal.ZERO); // document-level: per-line tax not booked — LineNetAmt−TaxAmt would be GROSS
		saveRecord(line);

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		final CrossIndustryInvoiceType cii = new CiiMapper().map(invoice, recipientConfig);
		final XmlAssert xmlAssert = toXmlAssert(cii);

		final String lineBase = "//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
				+ "/ram:IncludedSupplyChainTradeLineItem[1]";
		final String summation = "//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction"
				+ "/ram:ApplicableHeaderTradeSettlement/ram:SpecifiedTradeSettlementHeaderMonetarySummation";

		// BT-131 MUST be the derived net (84.03), NOT LineNetAmt−TaxAmt (=100.00 gross) — the regression
		// that reusing the booked per-line net would introduce for a document-level tax.
		xmlAssert.valueByXPath(lineBase + "/ram:SpecifiedLineTradeSettlement"
						+ "/ram:SpecifiedTradeSettlementLineMonetarySummation/ram:LineTotalAmount")
				.isEqualTo("84.03");
		// BT-146 item net price likewise net.
		xmlAssert.valueByXPath(lineBase + "/ram:SpecifiedLineTradeAgreement/ram:NetPriceProductTradePrice/ram:ChargeAmount")
				.isEqualTo("84.03");
		// BT-106/BT-109 = SUM(TaxBaseAmt) = 84.03.
		xmlAssert.valueByXPath(summation + "/ram:LineTotalAmount").isEqualTo("84.03");
		xmlAssert.valueByXPath(summation + "/ram:TaxBasisTotalAmount").isEqualTo("84.03");
	}

	/**
	 * Safety bound on the last-line reconciliation: the delta absorbed on a group's last line may only
	 * ever be the per-line rounding gap (≤ lineCount × one minor currency unit). If the summed per-line
	 * nets and {@code C_InvoiceTax.TaxBaseAmt} diverge by MORE than that (here {@code TaxBaseAmt=90.00}
	 * vs. the derived net 84.03 for a single 19% line → delta 5.97 ≫ the 0.01 bound), the mapper must
	 * fail loudly rather than silently distort the line by an arbitrary amount.
	 */
	@Test
	void tax_included_reconciliationDeltaExceedsBound_throwsWithIds() throws Exception
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
		currency.setDescription("EUR");
		currency.setStdPrecision(2);
		saveRecord(currency);
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice: IsTaxIncluded=Y ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-2024-00705");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		invoice.setIsTaxIncluded(true);
		invoice.setTotalLines(new BigDecimal("100.00"));
		invoice.setGrandTotal(new BigDecimal("100.00"));
		saveRecord(invoice);

		// === UOM + Product ===
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName("Stück");
		uom.setX12DE355("PCE");
		saveRecord(uom);
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName("Testprodukt");
		product.setValue("TP-705");
		saveRecord(product);

		// === Tax: 19% standard 'S' ===
		final I_C_TaxCategory taxCategory = newInstance(I_C_TaxCategory.class);
		saveRecord(taxCategory);
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName("MWSt 19%");
		tax.setEN16931VATCategory("S");
		tax.setRate(new BigDecimal("19"));
		tax.setC_TaxCategory_ID(taxCategory.getC_TaxCategory_ID());
		tax.setValidFrom(Timestamp.from(LocalDate.of(2000, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant()));
		saveRecord(tax);

		// === C_InvoiceTax: TaxBaseAmt (90.00) diverges from the derived net (84.03) by 5.97 >> 0.01 bound ===
		final I_C_InvoiceTax invoiceTax = newInstance(I_C_InvoiceTax.class);
		invoiceTax.setC_Invoice_ID(invoice.getC_Invoice_ID());
		invoiceTax.setC_Tax_ID(tax.getC_Tax_ID());
		invoiceTax.setIsTaxIncluded(true);
		invoiceTax.setTaxBaseAmt(new BigDecimal("90.00"));
		invoiceTax.setTaxAmt(new BigDecimal("10.00"));
		saveRecord(invoiceTax);

		// === Invoice line: LineNetAmt/PriceActual GROSS (100.00) → calculateBaseAmt = 84.03 ===
		final I_C_InvoiceLine line = newInstance(I_C_InvoiceLine.class);
		line.setC_Invoice_ID(invoice.getC_Invoice_ID());
		line.setLine(10);
		line.setM_Product_ID(product.getM_Product_ID());
		line.setC_UOM_ID(uom.getC_UOM_ID());
		line.setC_Tax_ID(tax.getC_Tax_ID());
		line.setQtyInvoiced(new BigDecimal("1"));
		line.setPriceActual(new BigDecimal("100.00"));
		line.setLineNetAmt(new BigDecimal("100.00"));
		line.setTaxAmt(new BigDecimal("15.97"));
		saveRecord(line);

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.build();

		assertThatThrownBy(() -> new CiiMapper().map(invoice, recipientConfig))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("C_Tax_ID=" + tax.getC_Tax_ID())
				.hasMessageContaining("C_Invoice_ID=" + invoice.getC_Invoice_ID());
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
