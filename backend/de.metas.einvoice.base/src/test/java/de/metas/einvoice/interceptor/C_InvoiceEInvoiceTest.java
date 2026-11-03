package de.metas.einvoice.interceptor;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentTags;
import de.metas.einvoice.EInvoiceCiiService;
import de.metas.einvoice.EInvoiceConfigService;
import de.metas.einvoice.EInvoiceFormat;
import de.metas.invoice.InvoiceId;
import lombok.NonNull;
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

import org.adempiere.util.lang.impl.TableRecordReference;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit test for {@link C_Invoice#onComplete_generateXRechnung(I_C_Invoice)}.
 *
 * <p>Calls the interceptor method directly — no document engine needed.
 *
 * <p>Six cases:
 * <ol>
 *   <li>Invalid XRechnung (missing Leitweg-ID) → throws {@link AdempiereException} that is a user-validation-error
 *       and whose message names a BR-DE-* rule id.</li>
 *   <li>Valid XRechnung → no throw; exactly one attachment named {@code <DocumentNo>_xrechnung.xml}
 *       is created, tagged {@code Send_via_Email=true}.</li>
 *   <li>Non-recipient buyer → no throw, no attachment.</li>
 *   <li>ZUGFeRD buyer → scope guard exits early; no throw, no attachment.</li>
 *   <li>Idempotency: re-completing (simulating reactivate + re-complete) produces exactly one attachment.</li>
 *   <li>Mailer selection query ({@code streamEmailAttachments}) finds the XRechnung XML — proves the
 *       attachment is reachable by the email-delivery path.</li>
 * </ol>
 */
public class C_InvoiceEInvoiceTest
{
	private C_Invoice interceptor;
	private AttachmentEntryService attachmentEntryService;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		// AttachmentEntryService records the creator's userId; set a valid value to avoid "repoId=-1" failures
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_User_ID, 10);

		attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();
		final EInvoiceConfigService configService = new EInvoiceConfigService();
		interceptor = new C_Invoice(
				configService,
				new EInvoiceCiiService(configService, null, null),
				attachmentEntryService);
	}

	// =========================================================================
	// Case 1: Invalid XRechnung (missing Leitweg-ID) → block completion
	// =========================================================================

	@Test
	void onComplete_xrechnung_invalid_throwsUserValidationError()
	{
		// Missing Leitweg-ID → KoSIT BR-DE-* rules fire
		final I_C_Invoice invoice = buildCompleteXRechnungInvoice(/* clearBuyerReference */ true);

		assertThatThrownBy(() -> interceptor.onComplete_generateXRechnung(invoice))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> {
					final AdempiereException ade = (AdempiereException)ex;
					assertThat(ade.isUserValidationError())
							.as("Exception must be a user-validation-error so completion is rolled back")
							.isTrue();
					assertThat(ade.getMessage())
							.as("Exception message must contain at least one BR-DE-* rule id")
							.containsPattern("BR-DE-\\d+");
				});
	}

	// =========================================================================
	// Case 2: Valid XRechnung → create attachment tagged Send_via_Email=true
	// =========================================================================

	@Test
	void onComplete_xrechnung_valid_createsTaggedAttachment()
	{
		final I_C_Invoice invoice = buildCompleteXRechnungInvoice(/* clearBuyerReference */ false);

		// Should not throw
		interceptor.onComplete_generateXRechnung(invoice);

		// Verify attachment was created
		final String expectedFilename = invoice.getDocumentNo() + "_xrechnung.xml";
		final AttachmentEntry entry = attachmentEntryService.getByFilenameOrNull(invoice, expectedFilename);

		assertThat(entry)
				.as("An attachment named %s must exist on the invoice", expectedFilename)
				.isNotNull();

		assertThat(entry.getTags().hasTagSetToTrue(AttachmentTags.TAGNAME_SEND_VIA_EMAIL))
				.as("Attachment must have Send_via_Email=true tag")
				.isTrue();
	}

	// =========================================================================
	// Case 3: Non-recipient buyer → no throw, no attachment
	// =========================================================================

	@Test
	void onComplete_nonRecipient_noThrowNoAttachment()
	{
		final I_C_Invoice invoice = buildNonRecipientInvoice();

		// Should not throw
		interceptor.onComplete_generateXRechnung(invoice);

		// No attachment must have been created
		assertThat(attachmentEntryService.getByReferencedRecord(invoice))
				.as("No attachment should be created for a non-recipient invoice")
				.isEmpty();
	}

	// =========================================================================
	// Case 4: ZUGFeRD recipient → scope guard: no throw, no attachment
	// =========================================================================

	@Test
	void onComplete_zugferd_noThrowNoAttachment()
	{
		final I_C_Invoice invoice = buildCompleteInvoice(EInvoiceFormat.ZUGFeRD, /* clearBuyerReference */ false);

		// Should not throw
		interceptor.onComplete_generateXRechnung(invoice);

		// No attachment must have been created (ZUGFeRD is not XRechnung → scope guard exits early)
		assertThat(attachmentEntryService.getByReferencedRecord(invoice))
				.as("No attachment should be created for a ZUGFeRD invoice")
				.isEmpty();
	}

	// =========================================================================
	// Idempotency: re-completing does not produce a duplicate attachment
	// =========================================================================

	@Test
	void onComplete_xrechnung_valid_idempotent()
	{
		final I_C_Invoice invoice = buildCompleteXRechnungInvoice(/* clearBuyerReference */ false);

		interceptor.onComplete_generateXRechnung(invoice);
		interceptor.onComplete_generateXRechnung(invoice); // simulate reactivate + re-complete

		final String expectedFilename = invoice.getDocumentNo() + "_xrechnung.xml";
		final long attachmentCount = attachmentEntryService.getByReferencedRecord(invoice)
				.stream()
				.filter(e -> expectedFilename.equals(e.getFilename()))
				.count();

		assertThat(attachmentCount)
				.as("Exactly one xrechnung.xml attachment should exist even after two completions")
				.isEqualTo(1);
	}

	// =========================================================================
	// Case 6: Mailer selection query finds the XRechnung XML
	// =========================================================================

	/**
	 * Asserts that the exact query {@code MailWorkpackageProcessor} runs —
	 * {@code streamEmailAttachments(ofReferenced(docOutboundLog) = invoice, Send_via_Email)} —
	 * returns the XRechnung XML produced by the interceptor.
	 * The XRechnung XML therefore rides along on the invoice email; actual SMTP transmission is covered at UAT.
	 */
	@Test
	void onComplete_xrechnung_valid_attachmentIsSelectableForEmail()
	{
		final I_C_Invoice invoice = buildCompleteInvoice(EInvoiceFormat.XRECHNUNG, /* clearBuyerReference */ false);
		interceptor.onComplete_generateXRechnung(invoice);

		final String expectedFilename = invoice.getDocumentNo() + "_xrechnung.xml";

		final List<String> emailAttachmentFilenames = attachmentEntryService
				.streamEmailAttachments(TableRecordReference.of(invoice), AttachmentTags.TAGNAME_SEND_VIA_EMAIL)
				.map(de.metas.attachments.EmailAttachment::getFilename)
				.collect(Collectors.toList());

		assertThat(emailAttachmentFilenames)
				.as("streamEmailAttachments(invoice, Send_via_Email) must include the XRechnung XML — "
						+ "this is the query MailWorkpackageProcessor runs to collect email attachments")
				.contains(expectedFilename);
	}

	// =========================================================================
	// Fixture builders
	// =========================================================================

	/** Builds an XRechnung invoice. Pass {@code clearBuyerReference=true} for an invalid document. */
	private I_C_Invoice buildCompleteXRechnungInvoice(final boolean clearBuyerReference)
	{
		return buildCompleteInvoice(EInvoiceFormat.XRECHNUNG, clearBuyerReference);
	}

	/**
	 * Builds a complete, realistic e-invoice fixture saved to the in-memory DB.
	 *
	 * @param format             the e-invoice format to configure on the buyer BPartner.
	 * @param clearBuyerReference when {@code true}, the buyer reference (Leitweg-ID) is left empty
	 *                            so that KoSIT BR-DE-15 / BR-DE-1 rules trigger for XRechnung.
	 */
	private I_C_Invoice buildCompleteInvoice(
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

		// === Seller contact — required for XRechnung BR-DE-2/5/6/7 ===
		// BR-DE-5: PersonName required; BR-DE-6: phone ≥3 digits; BR-DE-7: email required
		final I_AD_User sellerContact = newInstance(I_AD_User.class);
		sellerContact.setC_BPartner_ID(sellerBP.getC_BPartner_ID());
		sellerContact.setName("Max Mustermann");
		sellerContact.setPhone("+49 30 123456789");
		sellerContact.setEMail("max.mustermann@muster.de");
		saveRecord(sellerContact);

		// === Currency + bank ===
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code("EUR");
		currency.setDescription("EUR");
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
		// clearBuyerReference=true → Leitweg-ID left empty → BR-DE-15 / BR-DE-1 fires for XRechnung
		saveRecord(buyerBP);

		final I_C_BPartner_Location buyerBPLoc = newInstance(I_C_BPartner_Location.class);
		buyerBPLoc.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		buyerBPLoc.setC_Location_ID(buyerLocation.getC_Location_ID());
		buyerBPLoc.setIsBillTo(true);
		// BT-49 Buyer electronic address — required by PEPPOL-EN16931-R010
		buyerBPLoc.setEMail("einkauf@kaeufer.de");
		saveRecord(buyerBPLoc);

		// === DocType + Invoice ===
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-EINVOICE-TEST-001");
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

	/** Builds an invoice for a buyer that is NOT an e-invoice recipient. */
	private I_C_Invoice buildNonRecipientInvoice()
	{
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
		currency.setDescription("EUR");
		saveRecord(currency);

		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType("ARI");
		saveRecord(docType);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(org.getAD_Org_ID());
		invoice.setDocumentNo("RE-NONRECIPIENT-001");
		invoice.setDateInvoiced(Timestamp.from(LocalDate.of(2024, 6, 15).atStartOfDay(ZoneOffset.UTC).toInstant()));
		invoice.setC_Currency_ID(currency.getC_Currency_ID());
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(buyerBP.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(buyerBPLoc.getC_BPartner_Location_ID());
		saveRecord(invoice);

		return invoice;
	}
}
