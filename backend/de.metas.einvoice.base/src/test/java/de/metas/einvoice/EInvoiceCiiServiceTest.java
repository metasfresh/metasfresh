package de.metas.einvoice;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.einvoice.cii.CiiValidationResult;
import de.metas.invoice.InvoiceId;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceTax;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link EInvoiceCiiService#generateAndValidate(InvoiceId)}.
 *
 * <p>Uses the same full fixture pattern as CiiValidatorTest to exercise the entire pipeline:
 * config resolution → CiiMapper → marshal → CiiValidator.
 */
public class EInvoiceCiiServiceTest
{
	private EInvoiceCiiService service;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		service = new EInvoiceCiiService(new EInvoiceConfigService(), null, null);
	}

	@Test
	void generateAndValidate_nonRecipient_returnsEmpty()
	{
		// Buyer BPartner without isEInvoiceRecipient=true → service returns empty
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setCountryCode("DE");
		saveRecord(country);

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setAddress1("Some street 1");
		location.setCity("Berlin");
		location.setC_Country_ID(country.getC_Country_ID());
		saveRecord(location);

		final I_C_BPartner buyerBP = newInstance(I_C_BPartner.class);
		buyerBP.setName("Non-eInvoice Buyer");
		buyerBP.setIsEInvoiceRecipeint(false); // NOT an e-invoice recipient
		saveRecord(buyerBP);

		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(location.getC_Location_ID());
		saveRecord(buyerBPLoc);

		// Minimal seller
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

		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		saveRecord(currency);

		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-NON-EINVOICE-001");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		saveRecord(invoice);

		final Optional<EInvoiceCiiService.GenerateAndValidateResult> result =
				service.generateAndValidate(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));

		assertThat(result).isEmpty();
	}

	@Test
	void generateAndValidate_recipient_returnsXmlAndValidation()
	{
		// Build a complete fixture for an e-invoice-enabled BPartner
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
		sellerBP.setVATaxID("DE123456789"); // USt-IdNr -> BT-31 (VAT identifier); required by BR-CO-26
		sellerBP.setEMail("invoice@muster.de");
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

		// === Currency + bank ===
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		saveRecord(currency);

		final I_C_BP_BankAccount sellerBank = newInstance(I_C_BP_BankAccount.class);
		sellerBank.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerBank.setC_Currency_ID(currency.getC_Currency_ID());
		sellerBank.setIBAN("DE89370400440532013000");
		sellerBank.setIsDefault(true);
		sellerBank.setAD_Org_ID(org.getAD_Org_ID());
		saveRecord(sellerBank);

		// === Buyer (e-invoice recipient) ===
		final I_C_Country buyerCountry = newInstance(I_C_Country.class);
		buyerCountry.setCountryCode("DE");
		saveRecord(buyerCountry);

		final I_C_Location buyerLocation = newInstance(I_C_Location.class);
		buyerLocation.setAddress1("Käuferweg 5");
		buyerLocation.setCity("Hamburg");
		buyerLocation.setPostal("20095");
		buyerLocation.setC_Country_ID(buyerCountry.getC_Country_ID());
		saveRecord(buyerLocation);

		final I_C_BPartner buyerBP = newInstance(I_C_BPartner.class);
		buyerBP.setName("Käufer AG");
		buyerBP.setIsEInvoiceRecipeint(true); // IS an e-invoice recipient
		buyerBP.setEInvoiceType(EInvoiceFormat.ZUGFeRD.getCode());
		buyerBP.setEInvoice_BuyerReference("991-1234512345-06");
		saveRecord(buyerBP);

		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(buyerLocation.getC_Location_ID());
		buyerBPLoc.setIsBillTo(true);
		saveRecord(buyerBPLoc);

		// === DocType + Invoice ===
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-SERVICE-001");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		invoice.setTotalLines(new BigDecimal("100.00"));
		invoice.setGrandTotal(new BigDecimal("119.00"));
		invoice.setPaymentRule("T");
		saveRecord(invoice);

		// === Tax + VAT breakdown ===
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName("MWSt 19%");
		tax.setEN16931VATCategory("S");
		tax.setRate(new BigDecimal("19"));
		saveRecord(tax);

		final I_C_InvoiceTax invoiceTax = newInstance(I_C_InvoiceTax.class);
		invoiceTax.setC_Invoice_ID(invoice.getC_Invoice_ID());
		invoiceTax.setC_Tax_ID(tax.getC_Tax_ID());
		invoiceTax.setTaxBaseAmt(new BigDecimal("100.00"));
		invoiceTax.setTaxAmt(new BigDecimal("19.00"));
		saveRecord(invoiceTax);

		// === UOM + Product + line ===
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName("Stück");
		uom.setX12DE355("PCE");
		saveRecord(uom);

		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName("Testprodukt");
		product.setValue("TP-SVC-001");
		saveRecord(product);

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

		// === Execute ===
		final Optional<EInvoiceCiiService.GenerateAndValidateResult> resultOpt =
				service.generateAndValidate(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));

		assertThat(resultOpt).isPresent();

		final EInvoiceCiiService.GenerateAndValidateResult result = resultOpt.get();

		// XML must be non-empty and contain CII namespace markers.
		// The root MUST carry the standard "rsm:" prefix (not JAXB's auto-generated ns2/ns3):
		// Mustangproject's CustomXMLProvider.setXML() checks for rsm:CrossIndustryInvoice, so a
		// regression in the NamespacePrefixMapper would silently break ZUGFeRD embedding.
		assertThat(result.getCiiXml())
				.isNotEmpty()
				.contains("rsm:CrossIndustryInvoice")
				.doesNotContain("ns2:CrossIndustryInvoice")
				.contains("RE-SERVICE-001");

		// Validation result must be present
		final CiiValidationResult validationResult = result.getValidationResult();
		assertThat(validationResult).isNotNull();

		// The full pipeline (mapper → marshal → Schematron) must produce a valid CII document.
		assertThat(result.isValid())
				.as("EN16931 Schematron validation must pass; violations: %s", validationResult.getFatalAndErrorRuleIds())
				.isTrue();
	}

	/**
	 * XRechnung invoice with a missing buyer reference (Leitweg-ID) must be invalid
	 * and contain at least one BR-DE-* rule ID — proving that KoSIT validation ran.
	 */
	@Test
	void generateAndValidate_xrechnung_appliesKoSit()
	{
		final InvoiceId invoiceId = buildCompleteInvoice(EInvoiceFormat.XRECHNUNG, /* clearBuyerReference */ true);

		final Optional<EInvoiceCiiService.GenerateAndValidateResult> resultOpt =
				service.generateAndValidate(invoiceId);

		assertThat(resultOpt).isPresent();
		final EInvoiceCiiService.GenerateAndValidateResult result = resultOpt.get();

		// A missing Leitweg-ID is a hard KoSIT BR-DE-* violation — document must be invalid.
		assertThat(result.isValid())
				.as("XRechnung with missing buyer reference must fail KoSIT validation")
				.isFalse();

		// At least one BR-DE-* id must appear, proving the KoSIT schematron ran.
		assertThat(result.getFatalAndErrorRuleIds())
				.as("Expected at least one BR-DE-* rule id from KoSIT validation")
				.anyMatch(id -> id.startsWith("BR-DE"));
	}

	/**
	 * ZUGFeRD invoice (even with missing buyer reference) must NOT contain BR-DE-* rule ids —
	 * proving that KoSIT validation is NOT applied for non-XRechnung formats.
	 */
	@Test
	void generateAndValidate_zugferd_doesNotApplyKoSit()
	{
		final InvoiceId invoiceId = buildCompleteInvoice(EInvoiceFormat.ZUGFeRD, /* clearBuyerReference */ true);

		final Optional<EInvoiceCiiService.GenerateAndValidateResult> resultOpt =
				service.generateAndValidate(invoiceId);

		assertThat(resultOpt).isPresent();
		final EInvoiceCiiService.GenerateAndValidateResult result = resultOpt.get();

		// No BR-DE-* ids expected — KoSIT schematron must not have run for ZUGFeRD.
		assertThat(result.getFatalAndErrorRuleIds())
				.as("ZUGFeRD validation must not produce any BR-DE-* rule ids")
				.noneMatch(id -> id.startsWith("BR-DE"));
	}

	// -------------------------------------------------------------------------
	// Fixture builder
	// -------------------------------------------------------------------------

	/**
	 * Builds a complete, realistic e-invoice fixture and saves it to the in-memory DB.
	 *
	 * @param format              the e-invoice format to configure on the buyer BPartner.
	 * @param clearBuyerReference when {@code true}, the buyer reference (Leitweg-ID) is left empty
	 *                            so that KoSIT BR-DE-15 / BR-DE-1 rules trigger for XRechnung.
	 * @return the {@link InvoiceId} of the saved invoice.
	 */
	private InvoiceId buildCompleteInvoice(
			@NonNull final EInvoiceFormat format,
			final boolean clearBuyerReference)
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
		sellerBP.setVATaxID("DE123456789"); // USt-IdNr -> BT-31 (VAT identifier); required by BR-CO-26
		sellerBP.setEMail("invoice@muster.de");
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

		// === Currency + bank ===
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		saveRecord(currency);

		final I_C_BP_BankAccount sellerBank = newInstance(I_C_BP_BankAccount.class);
		sellerBank.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerBank.setC_Currency_ID(currency.getC_Currency_ID());
		sellerBank.setIBAN("DE89370400440532013000");
		sellerBank.setIsDefault(true);
		sellerBank.setAD_Org_ID(org.getAD_Org_ID());
		saveRecord(sellerBank);

		// === Buyer (e-invoice recipient) ===
		final I_C_Country buyerCountry = newInstance(I_C_Country.class);
		buyerCountry.setCountryCode("DE");
		saveRecord(buyerCountry);

		final I_C_Location buyerLocation = newInstance(I_C_Location.class);
		buyerLocation.setAddress1("Käuferweg 5");
		buyerLocation.setCity("Hamburg");
		buyerLocation.setPostal("20095");
		buyerLocation.setC_Country_ID(buyerCountry.getC_Country_ID());
		saveRecord(buyerLocation);

		final I_C_BPartner buyerBP = newInstance(I_C_BPartner.class);
		buyerBP.setName("Käufer AG");
		buyerBP.setIsEInvoiceRecipeint(true);
		buyerBP.setEInvoiceType(format.getCode());
		if (!clearBuyerReference)
		{
			buyerBP.setEInvoice_BuyerReference("991-1234512345-06");
		}
		// clearBuyerReference=true → leave EInvoice_BuyerReference null/empty (triggers BR-DE-* for XRechnung)
		saveRecord(buyerBP);

		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(buyerLocation.getC_Location_ID());
		buyerBPLoc.setIsBillTo(true);
		saveRecord(buyerBPLoc);

		// === DocType + Invoice ===
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-KOSIT-TEST-001");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		invoice.setTotalLines(new BigDecimal("100.00"));
		invoice.setGrandTotal(new BigDecimal("119.00"));
		invoice.setPaymentRule("T");
		saveRecord(invoice);

		// === Tax + VAT breakdown ===
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName("MWSt 19%");
		tax.setEN16931VATCategory("S");
		tax.setRate(new BigDecimal("19"));
		saveRecord(tax);

		final I_C_InvoiceTax invoiceTax = newInstance(I_C_InvoiceTax.class);
		invoiceTax.setC_Invoice_ID(invoice.getC_Invoice_ID());
		invoiceTax.setC_Tax_ID(tax.getC_Tax_ID());
		invoiceTax.setTaxBaseAmt(new BigDecimal("100.00"));
		invoiceTax.setTaxAmt(new BigDecimal("19.00"));
		saveRecord(invoiceTax);

		// === UOM + Product + line ===
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName("Stück");
		uom.setX12DE355("PCE");
		saveRecord(uom);

		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName("Testprodukt");
		product.setValue("TP-SVC-001");
		saveRecord(product);

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

		return InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
	}
}
