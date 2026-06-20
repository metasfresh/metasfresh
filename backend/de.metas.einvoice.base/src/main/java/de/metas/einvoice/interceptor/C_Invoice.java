package de.metas.einvoice.interceptor;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentTags;
import de.metas.einvoice.EInvoiceCiiService;
import de.metas.einvoice.EInvoiceCiiService.GenerateAndValidateResult;
import de.metas.einvoice.EInvoiceConfigService;
import de.metas.einvoice.EInvoiceRecipientConfig;
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
 * <p>After completion ({@link ModelValidator#TIMING_AFTER_COMPLETE}), and only when the buyer
 * BPartner is configured as an XRechnung recipient, this interceptor:
 * <ol>
 *   <li>Generates and validates the CII XML via {@link EInvoiceCiiService#generateAndValidate(InvoiceId)}.</li>
 *   <li>If the result is invalid, throws a user-validation-error {@link AdempiereException} that
 *       names the failing KoSIT rule ids — this rolls back the completion.</li>
 *   <li>If the result is valid, creates an attachment named {@code <DocumentNo>_xrechnung.xml}
 *       on the invoice and tags it {@link AttachmentTags#TAGNAME_SEND_VIA_EMAIL} = {@code true}
 *       so the mailer can pick it up.</li>
 * </ol>
 *
 * <p><b>Idempotency</b>: on re-complete (after reactivate) the interceptor checks whether an
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

		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
		final GenerateAndValidateResult result = eInvoiceCiiService.generateAndValidate(invoiceId)
				.orElseThrow(() -> new AdempiereException(
						"E-Invoice config not resolvable for invoice " + invoice.getDocumentNo()
								+ " — this should not happen when config resolution already succeeded")
						.markAsUserValidationError());

		if (!result.isValid())
		{
			throw new AdempiereException(
					"XRechnung validation failed for invoice " + invoice.getDocumentNo()
							+ ": " + result.getFatalAndErrorRuleIds())
					.markAsUserValidationError();
		}

		final String filename = invoice.getDocumentNo() + "_xrechnung.xml";
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
		attachmentEntryService.save(newEntry.withAdditionalTag(
				AttachmentTags.TAGNAME_SEND_VIA_EMAIL, "true"));
	}
}
