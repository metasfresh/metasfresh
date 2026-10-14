package de.metas.einvoice.cii;

import de.metas.adempiere.model.I_C_InvoiceLine;
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
import org.compiere.model.I_C_InvoiceTax;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * TDD for {@link CiiValidator}: EN16931 Schematron validation of marshalled CII XML.
 *
 * <p>Tests use the same fixture pattern as CiiMapperTest (B3-B5) to build a complete, EN16931-valid
 * invoice XML via CiiMapper, then run the Schematron validator on it.
 */
public class CiiValidatorTest
{
	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
	}

	// ===== Shared fixture builder =====

	/**
	 * Builds a complete, internally-consistent invoice fixture that satisfies the mandatory EN16931 BR-* rules:
	 * <ul>
	 *   <li>BG-4/BG-5: seller name, VAT ID, address (line1, city, country), electronic address</li>
	 *   <li>BG-7/BG-8: buyer name, address, buyer reference</li>
	 *   <li>BG-25: ≥1 line with UOM (PCE), item name, price, line net amount, VAT category S, rate 19</li>
	 *   <li>BG-23: one C_InvoiceTax row with category S, taxable + tax amounts</li>
	 *   <li>BG-22: consistent totals (TotalLines=100, GrandTotal=119)</li>
	 *   <li>BG-16: credit transfer (PaymentRule=T) with IBAN</li>
	 * </ul>
	 */
	private FixtureResult buildCompleteFixture()
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

		// === Seller contact (AD_User) — required for XRechnung BG-6 / BR-DE-2/5/6/7 ===
		// BR-DE-5: PersonName or DepartmentName required
		// BR-DE-6: TelephoneUniversalCommunication/CompleteNumber required (≥3 digits per BR-DE-27)
		// BR-DE-7: EmailURIUniversalCommunication/URIID required (valid email per BR-DE-28)
		final I_AD_User sellerContact = newInstance(I_AD_User.class);
		sellerContact.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerContact.setName("Max Mustermann");
		sellerContact.setPhone("+49 30 123456789");
		sellerContact.setEMail("max.mustermann@muster.de");
		saveRecord(sellerContact);

		// === Currency ===
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		currency.setDescription("EUR");
		saveRecord(currency);

		// === Seller bank account (IBAN for BR-61) ===
		final I_C_BP_BankAccount sellerBank = newInstance(I_C_BP_BankAccount.class);
		sellerBank.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerBank.setC_Currency_ID(currency.getC_Currency_ID());
		sellerBank.setIBAN("DE89370400440532013000");
		sellerBank.setIsDefault(true);
		sellerBank.setAD_Org_ID(org.getAD_Org_ID());
		saveRecord(sellerBank);

		// === Buyer ===
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
		saveRecord(buyerBP);

		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(buyerLocation.getC_Location_ID());
		buyerBPLoc.setIsBillTo(true);
		// BT-49 Buyer electronic address — required by XRechnung (PEPPOL-EN16931-R010)
		buyerBPLoc.setEMail("einkauf@kaeufer.de");
		saveRecord(buyerBPLoc);

		// === DocType (ARI = commercial invoice) ===
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice: totals consistent with S-group: 100 base + 19 VAT = 119 grand total ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-2024-00042");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		invoice.setTotalLines(new BigDecimal("100.00"));
		invoice.setGrandTotal(new BigDecimal("119.00"));
		invoice.setPaymentRule("T"); // credit transfer
		saveRecord(invoice);

		// === Tax: 19% standard S ===
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName("MWSt 19%");
		tax.setEN16931VATCategory("S");
		tax.setRate(new BigDecimal("19"));
		saveRecord(tax);

		// === VAT breakdown (BG-23) ===
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
		product.setName("Testprodukt Alpha");
		product.setValue("TP-001");
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

		final EInvoiceRecipientConfig recipientConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.ZUGFeRD)
				.buyerReference("991-1234512345-06")
				.build();

		return new FixtureResult(invoice, recipientConfig);
	}

	private static class FixtureResult
	{
		final I_C_Invoice invoice;
		final EInvoiceRecipientConfig recipientConfig;

		FixtureResult(final I_C_Invoice invoice, final EInvoiceRecipientConfig recipientConfig)
		{
			this.invoice = invoice;
			this.recipientConfig = recipientConfig;
		}
	}

	/**
	 * Builds a complete, EN16931/XRechnung-valid, tax-INCLUDED, multi-line invoice fixture: two lines
	 * at 19% with {@code LineNetAmt} 100.00 + 100.00 (GROSS, per the tax-included bug), where the
	 * VAT breakdown carries the round-of-sum values ({@code TaxBaseAmt}=168.07, {@code TaxAmt}=31.93)
	 * rather than the naive per-line sum (168.06) — proving the fix's rounding reconciliation
	 * (see {@link CiiMapperTest#tax_included_multiLine_bt131_reconciles_to_taxBaseAmt}) also satisfies
	 * the real KoSIT schematron (no BR-S-08 / BR-CO-10).
	 */
	private FixtureResult buildXRechnungTaxIncludedMultiLineFixture()
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
		sellerBP.setVATaxID("DE123456789");
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

		// === Seller contact (BG-6 / BR-DE-2/5/6/7) ===
		final I_AD_User sellerContact = newInstance(I_AD_User.class);
		sellerContact.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerContact.setName("Max Mustermann");
		sellerContact.setPhone("+49 30 123456789");
		sellerContact.setEMail("max.mustermann@muster.de");
		saveRecord(sellerContact);

		// === Currency ===
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		currency.setDescription("EUR");
		currency.setStdPrecision(2);
		saveRecord(currency);

		// === Seller bank account (IBAN for BR-61) ===
		final I_C_BP_BankAccount sellerBank = newInstance(I_C_BP_BankAccount.class);
		sellerBank.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerBank.setC_Currency_ID(currency.getC_Currency_ID());
		sellerBank.setIBAN("DE89370400440532013000");
		sellerBank.setIsDefault(true);
		sellerBank.setAD_Org_ID(org.getAD_Org_ID());
		saveRecord(sellerBank);

		// === Buyer ===
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
		saveRecord(buyerBP);

		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(buyerLocation.getC_Location_ID());
		buyerBPLoc.setIsBillTo(true);
		buyerBPLoc.setEMail("einkauf@kaeufer.de");
		saveRecord(buyerBPLoc);

		// === DocType ===
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		// === Invoice: IsTaxIncluded=Y, two lines, TotalLines/GrandTotal = 200.00 (gross) ===
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-2024-00702");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		invoice.setIsTaxIncluded(true);
		invoice.setTotalLines(new BigDecimal("200.00"));
		invoice.setGrandTotal(new BigDecimal("200.00"));
		invoice.setPaymentRule("T");
		saveRecord(invoice);

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

		// === VAT breakdown (BG-23): round-of-sum values, NOT the naive per-line sum (168.06) ===
		final I_C_InvoiceTax invoiceTax = newInstance(I_C_InvoiceTax.class);
		invoiceTax.setC_Invoice_ID(invoice.getC_Invoice_ID());
		invoiceTax.setC_Tax_ID(tax.getC_Tax_ID());
		invoiceTax.setIsTaxIncluded(true);
		invoiceTax.setTaxBaseAmt(new BigDecimal("168.07"));
		invoiceTax.setTaxAmt(new BigDecimal("31.93"));
		saveRecord(invoiceTax);

		// === UOM + products + two lines: LineNetAmt/PriceActual GROSS (100.00 each) ===
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName("Stück");
		uom.setX12DE355("PCE");
		saveRecord(uom);

		final I_M_Product product1 = newInstance(I_M_Product.class);
		product1.setName("Testprodukt Alpha");
		product1.setValue("TP-702-A");
		saveRecord(product1);

		final I_M_Product product2 = newInstance(I_M_Product.class);
		product2.setName("Testprodukt Beta");
		product2.setValue("TP-702-B");
		saveRecord(product2);

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
				.format(EInvoiceFormat.XRECHNUNG)
				.buyerReference("991-1234512345-06")
				.build();

		return new FixtureResult(invoice, recipientConfig);
	}

	/**
	 * Real KoSIT-schematron proof of the tax-included rounding-reconciliation fix: a multi-line
	 * tax-included XRechnung invoice (whose VAT breakdown carries the round-of-sum {@code TaxBaseAmt},
	 * not the naive per-line sum) must validate with ZERO fatal/error rule ids — specifically NEITHER
	 * BR-S-08 (BT-131 sum vs. BT-116) NOR BR-CO-10 (BT-106 vs. Σ BT-131).
	 */
	@Test
	void validate_xrechnung_taxIncludedMultiLine_noFatalErrors_noBrS08_noBrCo10() throws Exception
	{
		final FixtureResult fixture = buildXRechnungTaxIncludedMultiLineFixture();
		final CrossIndustryInvoiceType cii = new CiiMapper().map(fixture.invoice, fixture.recipientConfig);
		final String xml = marshalToXml(cii);

		final CiiValidator validator = new CiiValidator();
		final CiiValidationResult result = validator.validate(xml, EInvoiceFormat.XRECHNUNG);

		final List<String> fatalErrors = result.getFatalAndErrorRuleIds();
		assertThat(fatalErrors)
				.as("Expected ZERO fatal/error KoSIT rule violations on the tax-included multi-line "
						+ "XRechnung fixture. Firing rules (mapper gaps): " + fatalErrors
						+ ". All failed assertions: " + result.getFailedAssertions())
				.isEmpty();
		assertThat(fatalErrors)
				.as("BR-S-08 (line/VAT-breakdown net mismatch) must not fire. Actual rule ids: %s", fatalErrors)
				.doesNotContain("BR-S-08");
		assertThat(fatalErrors)
				.as("BR-CO-10 (sum of BT-131 vs. BT-106) must not fire. Actual rule ids: %s", fatalErrors)
				.doesNotContain("BR-CO-10");
	}

	private String marshalToXml(@NonNull final CrossIndustryInvoiceType cii) throws JAXBException
	{
		final JAXBContext ctx = JAXBContext.newInstance(CrossIndustryInvoiceType.class, ObjectFactory.class);
		final Marshaller marshaller = ctx.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		final StringWriter sw = new StringWriter();
		marshaller.marshal(new ObjectFactory().createCrossIndustryInvoice(cii), sw);
		return sw.toString();
	}

	/**
	 * Valid case: complete happy-path fixture → no FATAL/ERROR-level EN16931 failures.
	 * If mandatory BR-* rules fire, they reveal real mapper gaps — the test fails and the gap is reported.
	 */
	@Test
	void validFixture_noFatalErrors() throws Exception
	{
		final FixtureResult fixture = buildCompleteFixture();
		final CrossIndustryInvoiceType cii = new CiiMapper().map(fixture.invoice, fixture.recipientConfig);
		final String xml = marshalToXml(cii);

		final CiiValidator validator = new CiiValidator();
		final CiiValidationResult result = validator.validate(xml);

		// Assert no FATAL/ERROR failures - if this fails, specific BR rules should be listed
		final List<String> fatalErrors = result.getFatalAndErrorRuleIds();
		assertThat(fatalErrors)
				.as("Expected no FATAL/ERROR EN16931 rule violations on the valid fixture. "
						+ "Firing rules (mapper gaps): " + fatalErrors)
				.isEmpty();
	}

	/**
	 * Invalid case: seller name cleared (BT-27 mandatory per BR-06) → validator reports invalid
	 * with at least one failed assertion.
	 *
	 * <p>The seller name is cleared directly on the CII object model to avoid any
	 * namespace-prefix dependency in string-replacement approaches.
	 */
	@Test
	void invalidFixture_missingSellerName_reportsBrViolation() throws Exception
	{
		final FixtureResult fixture = buildCompleteFixture();
		final CrossIndustryInvoiceType cii = new CiiMapper().map(fixture.invoice, fixture.recipientConfig);

		// Corrupt the seller name via the object model — avoids namespace-prefix issues with string replace.
		// BR-06: "An Invoice shall contain the Seller name (BT-27)."
		cii.getSupplyChainTradeTransaction()
				.getApplicableHeaderTradeAgreement()
				.getSellerTradeParty()
				.setName(null);

		final String invalidXml = marshalToXml(cii);

		final CiiValidator validator = new CiiValidator();
		final CiiValidationResult result = validator.validate(invalidXml);

		assertThat(result.isValid())
				.as("Invoice with empty seller name should be invalid")
				.isFalse();
		assertThat(result.getFailedAssertions())
				.as("Expected at least one failed assertion for missing seller name")
				.isNotEmpty();
	}

	// ===== XRechnung / KoSIT schematron tests =====

	/**
	 * Builds a fixture identical to {@link #buildCompleteFixture()} but with XRechnung format
	 * and a Leitweg-ID buyer reference (BT-10), so it satisfies BR-DE-15.
	 */
	private FixtureResult buildXRechnungFixture()
	{
		return buildXRechnungFixture("991-1234512345-06");
	}

	/**
	 * Builds a fixture identical to {@link #buildCompleteFixture()} but with XRechnung format
	 * and the given BT-10 buyer reference.
	 */
	private FixtureResult buildXRechnungFixture(final String buyerReference)
	{
		// Reuse the full fixture; override format + buyer reference to XRechnung.
		final FixtureResult base = buildCompleteFixture();
		final EInvoiceRecipientConfig xrConfig = EInvoiceRecipientConfig.builder()
				.format(EInvoiceFormat.XRECHNUNG)
				.buyerReference(buyerReference)
				.build();
		return new FixtureResult(base.invoice, xrConfig);
	}

	/**
	 * KoSIT schematron is applied when format is XRECHNUNG: the call runs without throwing and
	 * produces a result. The valid fixture may still have BR-DE-* mapper gaps (Task 3 fixes those);
	 * here we only assert that the KoSIT layer is wired up (result is non-null and its assertion
	 * list is a superset of the EN16931-only call OR carries BR-DE-* ids).
	 * A dedicated "KoSIT fired" assertion is provided by the missingLeitweg test below.
	 */
	@Test
	void validate_xrechnung_validInvoice_kosItSchematronApplied() throws Exception
	{
		final FixtureResult fixture = buildXRechnungFixture();
		final CrossIndustryInvoiceType cii = new CiiMapper().map(fixture.invoice, fixture.recipientConfig);
		final String xml = marshalToXml(cii);

		final CiiValidator validator = new CiiValidator();

		// EN16931-only call (no KoSIT)
		final CiiValidationResult en16931Only = validator.validate(xml);
		// XRechnung call (EN16931 + KoSIT)
		final CiiValidationResult withKoSIT = validator.validate(xml, EInvoiceFormat.XRECHNUNG);

		// Both must be non-null; the XRechnung result must be produced without exception.
		assertThat(withKoSIT).isNotNull();
		assertThat(withKoSIT.getFailedAssertions()).isNotNull();

		// The XRechnung result must have at least as many assertions as the EN16931-only result
		// (KoSIT adds assertions on top; it never removes EN16931 ones).
		assertThat(withKoSIT.getFailedAssertions().size())
				.as("XRechnung validation must run the KoSIT layer in addition to EN16931; "
						+ "EN16931-only failures: %s, XRechnung failures: %s",
						en16931Only.getFatalAndErrorRuleIds(), withKoSIT.getFatalAndErrorRuleIds())
				.isGreaterThanOrEqualTo(en16931Only.getFailedAssertions().size());
	}

	/**
	 * Valid XRechnung fixture → KoSIT produces ZERO fatal/error BR-DE rule ids.
	 *
	 * <p>This test verifies that {@link CiiMapper} emits all XRechnung-mandatory fields
	 * (BG-6 seller contact BR-DE-2/5/6/7, BT-34 seller electronic address BR-DE-2 precondition,
	 * correct guideline ID BR-DE-21, payment means BR-DE-1, buyer reference BR-DE-15, etc.)
	 * so that a fully-populated sales invoice passes KoSIT validation with no fatal or error assertions.
	 */
	@Test
	void validate_xrechnung_validInvoice_noFatalErrors() throws Exception
	{
		final FixtureResult fixture = buildXRechnungFixture();
		final CrossIndustryInvoiceType cii = new CiiMapper().map(fixture.invoice, fixture.recipientConfig);
		final String xml = marshalToXml(cii);

		final CiiValidator validator = new CiiValidator();
		final CiiValidationResult result = validator.validate(xml, EInvoiceFormat.XRECHNUNG);

		final List<String> fatalErrors = result.getFatalAndErrorRuleIds();
		assertThat(fatalErrors)
				.as("Expected ZERO fatal/error KoSIT (BR-DE-*) rule violations on the valid XRechnung fixture. "
						+ "Firing rules (mapper gaps): " + fatalErrors
						+ ". All failed assertions: " + result.getFailedAssertions())
				.isEmpty();
	}

	/**
	 * BR-DE-15: "Das Element »Buyer reference« (BT-10) muss übermittelt werden." is a KoSIT-only rule.
	 * When buyer reference is missing and format is XRECHNUNG, BR-DE-15 must appear.
	 * When the same XML is validated as EN16931-only (null format), BR-DE-15 must NOT appear.
	 */
	@Test
	void validate_xrechnung_missingLeitweg_failsBrDe15() throws Exception
	{
		final FixtureResult fixture = buildXRechnungFixture();
		final CrossIndustryInvoiceType cii = new CiiMapper().map(fixture.invoice, fixture.recipientConfig);

		// Remove the buyer reference from the mapped CII to trigger BR-DE-15.
		cii.getSupplyChainTradeTransaction()
				.getApplicableHeaderTradeAgreement()
				.setBuyerReference(null);

		final String xmlWithoutLeitweg = marshalToXml(cii);

		final CiiValidator validator = new CiiValidator();

		// XRechnung call must flag BR-DE-15
		final CiiValidationResult xrResult = validator.validate(xmlWithoutLeitweg, EInvoiceFormat.XRECHNUNG);
		final List<String> xrRuleIds = xrResult.getFatalAndErrorRuleIds();
		assertThat(xrRuleIds)
				.as("Missing Leitweg-ID must trigger BR-DE-15 when validated as XRechnung. Actual rule ids: %s", xrRuleIds)
				.contains("BR-DE-15");

		// EN16931-only call must NOT flag any BR-DE-* rule (it does not run the KoSIT schematron)
		final CiiValidationResult en16931Result = validator.validate(xmlWithoutLeitweg, null);
		final List<String> en16931RuleIds = en16931Result.getFatalAndErrorRuleIds();
		assertThat(en16931RuleIds)
				.as("EN16931-only validation must not produce any BR-DE-* rule id. Actual: %s", en16931RuleIds)
				.noneMatch(id -> id != null && id.startsWith("BR-DE-"));
	}

	/**
	 * BR-DE-15 only requires the buyer reference (BT-10) to be non-empty; it does not enforce any
	 * particular format (e.g. Leitweg-ID). An arbitrary, non-Leitweg-format buyer reference must
	 * still satisfy BR-DE-15 on a valid XRechnung.
	 */
	@Test
	void validate_xrechnung_arbitraryBuyerReference_passesBrDe15() throws Exception
	{
		final FixtureResult fixture = buildXRechnungFixture("KD-4711");

		final CrossIndustryInvoiceType cii = new CiiMapper().map(fixture.invoice, fixture.recipientConfig);
		final String xml = marshalToXml(cii);

		final CiiValidator validator = new CiiValidator();
		final CiiValidationResult result = validator.validate(xml, EInvoiceFormat.XRECHNUNG);

		final List<String> fatalErrors = result.getFatalAndErrorRuleIds();
		assertThat(fatalErrors)
				.as("Expected ZERO fatal/error KoSIT (BR-DE-*) rule violations for an arbitrary buyer reference. "
						+ "Firing rules: " + fatalErrors)
				.isEmpty();
		assertThat(fatalErrors)
				.as("BR-DE-15 must not fire for a non-empty, non-Leitweg-format buyer reference. Actual rule ids: %s", fatalErrors)
				.doesNotContain("BR-DE-15");
	}
}
