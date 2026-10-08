package de.metas.einvoice.interceptor;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentTags;
import de.metas.einvoice.EInvoiceCiiService;
import de.metas.einvoice.EInvoiceConfigService;
import de.metas.einvoice.EInvoiceFormat;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceTax;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration test proving that the {@link C_Invoice} model interceptor blocks completion of a
 * ZUGFeRD invoice whose CII fails EN16931 validation.
 *
 * <p>Mirrors {@link C_InvoiceXRechnungOnCompleteTest}: registers the interceptor via
 * {@link POJOLookupMap#addModelValidator(Object)} and fires the doc-validate chain via
 * {@link POJOLookupMap#fireDocumentChange(Object, DocTimingType)}.
 *
 * <p>Three scenarios are proven:
 * <ol>
 *   <li><b>Invalid ZUGFeRD</b>: missing seller VAT ID triggers EN16931 BR-CO-26; firing
 *       {@code AFTER_COMPLETE} throws a user-validation-error {@link AdempiereException} that
 *       names the failing rule id — completion is rolled back.</li>
 *   <li><b>Valid ZUGFeRD</b>: firing {@code AFTER_COMPLETE} does not throw and creates exactly
 *       one attachment named {@code <DocumentNo>_zugferd.xml} that is NOT tagged
 *       {@code Send_via_Email} — the CII is an internal PDF-embedding artifact consumed at
 *       archive time by {@code ZugferdArchiveReportBytesTransformer}.</li>
 *   <li><b>Non-e-invoice</b>: the gate does not fire for a buyer that is not an e-invoice
 *       recipient.</li>
 * </ol>
 */
public class C_InvoiceZugferdOnCompleteTest
{
	private AttachmentEntryService attachmentEntryService;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_User_ID, 10);

		attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();
		final EInvoiceConfigService configService = new EInvoiceConfigService();
		final C_Invoice interceptor = new C_Invoice(
				configService,
				new EInvoiceCiiService(configService, null, null),
				attachmentEntryService);
		POJOLookupMap.get().addModelValidator(interceptor);
	}

	// =========================================================================
	// Invalid ZUGFeRD invoice — completion vetoed with user-validation-error
	// =========================================================================

	@Test
	void docValidate_afterComplete_invalidZugferd_completionVetoed()
	{
		// Missing seller VAT ID + no tax registration → EN16931 BR-CO-26 fires
		final I_C_Invoice invoice = buildZugferdInvoice(/* clearSellerVatId */ true);

		assertThatThrownBy(() -> POJOLookupMap.get().fireDocumentChange(invoice, DocTimingType.AFTER_COMPLETE))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> {
					final AdempiereException ade = (AdempiereException)ex;
					assertThat(ade.isUserValidationError())
							.as("Exception must be a user-validation-error so completion rolls back")
							.isTrue();
					assertThat(ade.getMessage())
							.as("Exception message must contain a BR-* rule id (EN16931 rule)")
							.containsPattern("BR-[A-Z0-9-]+");
				});
	}

	// =========================================================================
	// Valid ZUGFeRD invoice — completion succeeds WITH attachment (CII XML)
	// =========================================================================

	@Test
	void docValidate_afterComplete_validZugferd_completionSucceedsAndCiiAttached()
	{
		final I_C_Invoice invoice = buildZugferdInvoice(/* clearSellerVatId */ false);

		// Must not throw
		POJOLookupMap.get().fireDocumentChange(invoice, DocTimingType.AFTER_COMPLETE);

		// Gate MUST produce exactly one attachment: the CII XML named <DocNo>_zugferd.xml
		final List<AttachmentEntry> attachments = attachmentEntryService.getByReferencedRecord(invoice);
		assertThat(attachments)
				.as("ZUGFeRD completion gate must create exactly one CII XML attachment")
				.hasSize(1);

		final AttachmentEntry ciiAttachment = attachments.get(0);
		final String expectedFilename = invoice.getDocumentNo() + "_zugferd.xml";
		assertThat(ciiAttachment.getFilename())
				.as("ZUGFeRD CII attachment must use filename <DocNo>_zugferd.xml")
				.isEqualTo(expectedFilename);

		// ZUGFeRD CII is an internal PDF-embedding artifact — must NOT be tagged for email delivery
		assertThat(ciiAttachment.getTags().getTagValueOrNull(AttachmentTags.TAGNAME_SEND_VIA_EMAIL))
				.as("ZUGFeRD CII attachment must NOT be tagged Send_via_Email (it is embedded in the PDF, not emailed standalone)")
				.isNull();
	}

	// =========================================================================
	// Non-e-invoice buyer — gate is silent
	// =========================================================================

	@Test
	void docValidate_afterComplete_nonEInvoice_doesNotThrow()
	{
		final I_C_Invoice invoice = buildNonEInvoiceInvoice();

		// Gate must not fire for a buyer that is not an e-invoice recipient
		POJOLookupMap.get().fireDocumentChange(invoice, DocTimingType.AFTER_COMPLETE);
	}

	// =========================================================================
	// Fixture builders
	// =========================================================================

	/**
	 * Builds a ZUGFeRD invoice fixture.
	 *
	 * @param clearSellerVatId when {@code true}, the seller's VAT ID is left blank, which causes
	 *                         EN16931 BR-CO-26 to fire (no seller tax identifier of any kind).
	 */
	private I_C_Invoice buildZugferdInvoice(final boolean clearSellerVatId)
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
		if (!clearSellerVatId)
		{
			// BT-31 Seller VAT ID — required by EN16931 BR-CO-26 unless a tax reg ID is also present
			sellerBP.setVATaxID("DE123456789");
		}
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

		// Seller contact
		final I_AD_User sellerContact = newInstance(I_AD_User.class);
		sellerContact.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerContact.setName("Max Mustermann");
		sellerContact.setPhone("+49 30 123456789");
		sellerContact.setEMail("max.mustermann@muster.de");
		saveRecord(sellerContact);

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

		// === Buyer (ZUGFeRD recipient) ===
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
		buyerBP.setEInvoiceType(EInvoiceFormat.ZUGFeRD.getCode());
		buyerBP.setEInvoice_BuyerReference("991-1234512345-06");
		saveRecord(buyerBP);

		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(buyerLocation.getC_Location_ID());
		buyerBPLoc.setIsBillTo(true);
		buyerBPLoc.setEMail("einkauf@kaeufer.de");
		saveRecord(buyerBPLoc);

		// === DocType + Invoice ===
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-ZUGFERD-REGTEST-001");
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

		return invoice;
	}

	/** Builds a minimal invoice for a buyer that is NOT an e-invoice recipient. */
	private I_C_Invoice buildNonEInvoiceInvoice()
	{
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

		final I_C_Country country = newInstance(I_C_Country.class);
		country.setCountryCode("DE");
		saveRecord(country);

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setAddress1("Some Street 1");
		location.setCity("Berlin");
		location.setC_Country_ID(country.getC_Country_ID());
		saveRecord(location);

		final I_C_BPartner buyerBP = newInstance(I_C_BPartner.class);
		buyerBP.setName("Non-Recipient Buyer");
		buyerBP.setIsEInvoiceRecipeint(false);
		saveRecord(buyerBP);

		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(location.getC_Location_ID());
		saveRecord(buyerBPLoc);

		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		saveRecord(currency);

		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-NONRECIPIENT-ZUGFERD-001");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		saveRecord(invoice);

		return invoice;
	}
}
