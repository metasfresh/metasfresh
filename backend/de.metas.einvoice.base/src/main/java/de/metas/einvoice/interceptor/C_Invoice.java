package de.metas.einvoice.interceptor;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentTags;
import de.metas.einvoice.EInvoiceCiiService;
import de.metas.einvoice.EInvoiceCiiService.GenerateAndValidateResult;
import de.metas.einvoice.EInvoiceConfigService;
import de.metas.einvoice.EInvoiceRecipientConfig;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * C_Invoice model interceptor for e-invoicing.
 *
 * <p>After completion ({@link ModelValidator#TIMING_AFTER_COMPLETE}), two independent gates fire:
 *
 * <h3>XRechnung gate ({@link #onComplete_generateXRechnung})</h3>
 * <p>Only when the buyer BPartner is configured as an XRechnung recipient:
 * <ol>
 *   <li>Generates and validates the CII XML via {@link EInvoiceCiiService#generateAndValidate(InvoiceId)}.</li>
 *   <li>If the result is invalid, throws a user-validation-error {@link AdempiereException} that
 *       names the failing KoSIT rule ids — this rolls back the completion.</li>
 *   <li>If the result is valid, creates an attachment named {@code <DocumentNo>_xrechnung.xml}
 *       on the invoice and tags it {@link AttachmentTags#TAGNAME_SEND_VIA_EMAIL} = {@code true}
 *       so the mailer can pick it up.</li>
 * </ol>
 *
 * <h3>ZUGFeRD completion gate ({@link #onComplete_validateAndAttachZugferd})</h3>
 * <p>Only when the buyer BPartner is configured as a ZUGFeRD recipient:
 * <ol>
 *   <li>Generates and validates the CII XML against EN16931 rules via
 *       {@link EInvoiceCiiService#generateAndValidate(InvoiceId)}.</li>
 *   <li>If the result is invalid, throws a user-validation-error {@link AdempiereException} that
 *       names the failing EN16931 rule ids — this rolls back the completion.</li>
 *   <li>If valid, creates an attachment named {@code <DocumentNo>_zugferd.xml} on the invoice
 *       (NOT tagged for email — ZUGFeRD CII is an internal PDF-embedding artifact, not a
 *       standalone email deliverable). The attachment is consumed at archive time by
 *       {@code ZugferdArchiveReportBytesTransformer} to embed the CII into the PDF/A-3.</li>
 * </ol>
 *
 * <p><b>Idempotency</b>: on re-complete (after reactivate) both gates check whether an
 * attachment with the expected filename already exists via
 * {@link AttachmentEntryService#getByFilenameOrNull(Object, String)}. If one is found it is
 * unattached first (via {@link AttachmentEntryService#unattach(Object, AttachmentEntry)}),
 * then a fresh attachment is created — avoiding duplicate entries.
 *
 * <p><b>Spring registration</b>: {@code @Component} + {@code @Interceptor} is sufficient when
 * the application context component-scans {@code de.metas.einvoice}.
 */
@Interceptor(I_C_Invoice.class)
@Component
@RequiredArgsConstructor
public class C_Invoice
{
	/** User-facing, localized error (AD_Message) shown when the XRechnung is invalid; {0} = failed rule ids. */
	private static final AdMessageKey MSG_XRechnungInvalid = AdMessageKey.of("EInvoice_XRechnungInvalid");

	/** User-facing, localized error (AD_Message) shown when the ZUGFeRD CII is invalid; {0} = failed rule ids. */
	private static final AdMessageKey MSG_ZUGFeRDInvalid = AdMessageKey.of("EInvoice_ZUGFeRDInvalid");

	@NonNull private final EInvoiceConfigService configService;
	@NonNull private final EInvoiceCiiService eInvoiceCiiService;
	@NonNull private final AttachmentEntryService attachmentEntryService;

	/**
	 * After invoice completion: generate and validate the XRechnung XML, then block or attach.
	 *
	 * <p>Returns immediately if the buyer is not configured as an XRechnung recipient.
	 */
	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void onComplete_generateXRechnung(@NonNull final I_C_Invoice invoice)
	{
		final EInvoiceRecipientConfig cfg = configService.resolveForInvoice(invoice).orElse(null);
		if (cfg == null || !cfg.getFormat().isXRechnung())
		{
			return;
		}

		final String filename = invoice.getDocumentNo() + "_xrechnung.xml";
		generateValidateAndAttach(invoice, MSG_XRechnungInvalid, filename, /* tagSendViaEmail */ true);
	}

	/**
	 * After invoice completion: generate and validate the ZUGFeRD CII XML, then block or attach.
	 *
	 * <p>Returns immediately if the buyer is not configured as a ZUGFeRD recipient.
	 *
	 * <p>The attached {@code <DocumentNo>_zugferd.xml} is consumed at archive time by the
	 * archive seam ({@code ZugferdArchiveReportBytesTransformer}) — no CII regeneration at archive time.
	 * The attachment is intentionally NOT tagged for email: ZUGFeRD CII is embedded into the PDF,
	 * not sent as a standalone email deliverable.
	 */
	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void onComplete_validateAndAttachZugferd(@NonNull final I_C_Invoice invoice)
	{
		final EInvoiceRecipientConfig cfg = configService.resolveForInvoice(invoice).orElse(null);
		if (cfg == null || !cfg.getFormat().isZUGFeRD())
		{
			return;
		}

		final String filename = invoice.getDocumentNo() + "_zugferd.xml";
		generateValidateAndAttach(invoice, MSG_ZUGFeRDInvalid, filename, /* tagSendViaEmail */ false);
	}

	/**
	 * Shared logic for both gates: generate CII XML, validate, block on invalid, or
	 * create/replace the attachment with {@code filename} on success.
	 *
	 * @param invoice        the invoice being completed
	 * @param invalidMsgKey  AD_Message key for the user-validation-error when CII is invalid
	 * @param filename       attachment filename, e.g. {@code RE-001_xrechnung.xml}
	 * @param tagSendViaEmail when {@code true}, the attachment is tagged
	 *                       {@link AttachmentTags#TAGNAME_SEND_VIA_EMAIL} = {@code "true"};
	 *                       when {@code false}, no email tag is added (ZUGFeRD internal artifact)
	 */
	private void generateValidateAndAttach(
			@NonNull final I_C_Invoice invoice,
			@NonNull final AdMessageKey invalidMsgKey,
			@NonNull final String filename,
			final boolean tagSendViaEmail)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
		final GenerateAndValidateResult result = eInvoiceCiiService.generateAndValidate(invoiceId)
				.orElseThrow(() -> new AdempiereException(
						"E-Invoice config not resolvable for invoice " + invoice.getDocumentNo()
								+ " — this should not happen when config resolution already succeeded")
						.markAsUserValidationError());

		if (!result.isValid())
		{
			throw new AdempiereException(invalidMsgKey, result.getFatalAndErrorRuleIds())
					.markAsUserValidationError();
		}

		final byte[] xmlBytes = result.getCiiXml().getBytes(StandardCharsets.UTF_8);

		// Idempotency: if an attachment with this filename already exists (re-complete after reactivate),
		// unattach it first so we don't accumulate duplicates, then create a fresh one below.
		// Unattach+recreate is chosen over updateData because
		// AttachmentEntryRepository.updateAttachmentEntryData has an inverted type guard that throws
		// for Data entries — the only type we create here. Unattach+recreate is always safe.
		final AttachmentEntry existing = attachmentEntryService.getByFilenameOrNull(invoice, filename);
		if (existing != null)
		{
			attachmentEntryService.unattach(invoice, existing);
		}

		final AttachmentEntry newEntry = attachmentEntryService.createNewAttachment(invoice, filename, xmlBytes);
		if (tagSendViaEmail)
		{
			attachmentEntryService.save(newEntry.withAdditionalTag(
					AttachmentTags.TAGNAME_SEND_VIA_EMAIL, "true"));
		}
	}
}
