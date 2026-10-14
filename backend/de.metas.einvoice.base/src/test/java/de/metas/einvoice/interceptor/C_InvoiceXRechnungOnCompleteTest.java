package de.metas.einvoice.interceptor;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentTags;
import de.metas.einvoice.EInvoiceCiiService;
import de.metas.einvoice.EInvoiceConfigService;
import de.metas.einvoice.EInvoiceFormat;
import lombok.NonNull;
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

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration test proving that the {@link C_Invoice} model interceptor is wired through the
 * metasfresh model-interceptor registration system.
 *
 * <p>Unlike {@link C_InvoiceEInvoiceTest} (which calls the interceptor method directly), this
 * test registers the interceptor via {@link POJOLookupMap#addModelValidator(Object)} — which
 * internally processes the {@code @Interceptor} / {@code @DocValidate} annotations exactly as
 * the production {@code ModelValidationEngine} does — and then fires the doc-validate chain via
 * {@link POJOLookupMap#fireDocumentChange(Object, DocTimingType)}.
 *
 * <p>The two assertions required by Task 5 / Done-when:
 * <ol>
 *   <li><b>Valid XRechnung</b>: firing {@code AFTER_COMPLETE} does not throw, and the invoice
 *       carries exactly one attachment named {@code <DocumentNo>_xrechnung.xml} tagged
 *       {@code Send_via_Email=true}.</li>
 *   <li><b>Invalid XRechnung</b> (missing Leitweg-ID): firing {@code AFTER_COMPLETE} throws an
 *       {@link AdempiereException} that is a user-validation-error (so completion would be
 *       rolled back), and {@link AdempiereException#getMessage()} contains a BR-DE-* rule id.</li>
 * </ol>
 *
 * <p><b>Completion harness</b>: full invoice completion via the document engine (
 * {@code processEx}/{@code ACTION_Complete}) was evaluated and ruled out for this module's test
 * harness. {@code MInvoice.completeIt()} requires live accounting infrastructure
 * ({@code MAcctSchema}, {@code MAccount} GL accounts, sequence generators) that cannot be stood
 * up in the in-memory POJO environment without a DB. Instead this test uses the minimal harness
 * that genuinely drives the {@code @DocValidate(TIMING_AFTER_COMPLETE)} chain:
 * {@code POJOLookupMap.get().fireDocumentChange(invoice, DocTimingType.AFTER_COMPLETE)}.
 * This is the same mechanism {@code ModelValidationEngine.fireDocValidate} delegates to in the
 * POJO environment, and the same seam used by other interceptor tests in the metasfresh codebase
 * (e.g., {@code PP_Product_PlanningTest} in {@code de.metas.manufacturing}).
 * What this proves: (a) the annotation is parsed and dispatched correctly, (b) the correct method
 * fires, (c) veto and attachment behaviour are end-to-end verified through the registration layer.
 */
public class C_InvoiceXRechnungOnCompleteTest
{
	private AttachmentEntryService attachmentEntryService;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		// AttachmentEntryService records the creator's userId; set a valid value to avoid "repoId=-1" failures
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_User_ID, 10);

		attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();

		// Register the interceptor through the annotation-driven mechanism — exactly as
		// ModelValidationEngine.getSpringInterceptors() → addModelValidator() does in production.
		// POJOLookupMap.addModelValidator processes @Interceptor/@DocValidate annotations via
		// AnnotatedModelInterceptorFactory, wiring the @DocValidate method into the doc-validate chain.
		final EInvoiceConfigService configService = new EInvoiceConfigService();
		final C_Invoice interceptor = new C_Invoice(
				configService,
				new EInvoiceCiiService(configService, null, null),
				attachmentEntryService);
		POJOLookupMap.get().addModelValidator(interceptor);
	}

	// =========================================================================
	// Done-when assertion 1: valid XRechnung — completion succeeds, attachment tagged
	// =========================================================================

	@Test
	void docValidate_afterComplete_validXRechnung_attachmentCreated()
	{
		final I_C_Invoice invoice = buildCompleteXRechnungInvoice(/* clearBuyerReference */ false);

		// Fire the @DocValidate(TIMING_AFTER_COMPLETE) chain via the registration seam
		POJOLookupMap.get().fireDocumentChange(invoice, DocTimingType.AFTER_COMPLETE);

		// Verify attachment was created with the correct name and Send_via_Email tag
		final String expectedFilename = invoice.getDocumentNo() + "_xrechnung.xml";
		final AttachmentEntry entry = attachmentEntryService.getByFilenameOrNull(invoice, expectedFilename);

		assertThat(entry)
				.as("An attachment named %s must exist after AFTER_COMPLETE fires on a valid XRechnung invoice", expectedFilename)
				.isNotNull();

		assertThat(entry.getTags().hasTagSetToTrue(AttachmentTags.TAGNAME_SEND_VIA_EMAIL))
				.as("Attachment must have Send_via_Email=true tag so the mailer picks it up")
				.isTrue();
	}

	// =========================================================================
	// Done-when assertion 2: invalid XRechnung — completion vetoed (user-validation-error)
	// =========================================================================

	@Test
	void docValidate_afterComplete_invalidXRechnung_completionVetoed()
	{
		// Missing Leitweg-ID → KoSIT BR-DE-15 / BR-DE-1 rules fire
		final I_C_Invoice invoice = buildCompleteXRechnungInvoice(/* clearBuyerReference */ true);

		// The @DocValidate method must throw a user-validation-error — the engine propagates this
		// to roll back the completion and return the invoice to editable state.
		assertThatThrownBy(() -> POJOLookupMap.get().fireDocumentChange(invoice, DocTimingType.AFTER_COMPLETE))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> {
					final AdempiereException ade = (AdempiereException)ex;
					assertThat(ade.isUserValidationError())
							.as("Exception must be a user-validation-error so completion rolls back")
							.isTrue();
					assertThat(ade.getMessage())
							.as("Exception message must contain a BR-DE-* rule id (KoSIT XRechnung rule)")
							.containsPattern("BR-DE-\\d+");
				});
	}

	// =========================================================================
	// Fixture builder
	// =========================================================================

	private I_C_Invoice buildCompleteXRechnungInvoice(final boolean clearBuyerReference)
	{
		return buildCompleteInvoice(EInvoiceFormat.XRECHNUNG, clearBuyerReference);
	}

	/**
	 * Builds a complete, realistic e-invoice fixture saved to the in-memory DB.
	 * Mirrors the fixture in {@link C_InvoiceEInvoiceTest#buildCompleteInvoice}.
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

		// Seller contact — required for KoSIT BR-DE-2/5/6/7
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
		invoice.setDocumentNo("RE-EINVOICE-REGTEST-001");
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
}
