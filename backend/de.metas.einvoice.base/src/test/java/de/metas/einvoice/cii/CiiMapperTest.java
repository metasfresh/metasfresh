package de.metas.einvoice.cii;

import de.metas.einvoice.EInvoiceFormat;
import de.metas.einvoice.EInvoiceRecipientConfig;
import de.metas.einvoice.cii.model.CrossIndustryInvoiceType;
import de.metas.einvoice.cii.model.ObjectFactory;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xmlunit.assertj.XmlAssert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

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
		sellerBPartner.setTaxID("DE123456789");
		sellerBPartner.setEMail("invoice@muster.de");
		sellerBPartner.setCommercialRegisterNumber("HRB 12345");
		// Mark as org BPartner: set AD_OrgBP_ID so retrieveOrgBPartner can find it
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

		// Link sellerBPartner to the org via AD_OrgBP_ID
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
		buyerBPartner.setTaxID("DE987654321");
		saveRecord(buyerBPartner);

		// Link buyer location to buyer bpartner
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

		// Seller VAT id (BT-31) — value and scheme
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:SpecifiedTaxRegistration[1]/ram:ID")
				.isEqualTo("DE123456789");
		xmlAssert.valueByXPath(
						"//rsm:CrossIndustryInvoice/rsm:SupplyChainTradeTransaction/ram:ApplicableHeaderTradeAgreement/ram:SellerTradeParty/ram:SpecifiedTaxRegistration[1]/ram:ID/@schemeID")
				.isEqualTo("VA");

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
	}

	/**
	 * Verifies BT-3 credit note type code (381) and BT-25/BT-26 preceding invoice reference.
	 */
	@Test
	void credit_note_type_code_and_preceding_invoice_ref() throws Exception
	{
		// Minimal seller org
		final I_AD_Org org = newInstance(I_AD_Org.class);
		saveRecord(org);
		final I_C_BPartner sellerBP = newInstance(I_C_BPartner.class);
		sellerBP.setName("Seller GmbH");
		sellerBP.setAD_OrgBP_ID(org.getAD_Org_ID());
		saveRecord(sellerBP);
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
