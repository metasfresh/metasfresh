package de.metas.einvoice.zugferd;

/*
 * #%L
 * de.metas.einvoice.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.document.archive.spi.IArchiveReportBytesTransformer;
import de.metas.invoice.InvoiceId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * {@link IArchiveReportBytesTransformer} implementation that embeds a ZUGFeRD/Factur-X CII XML
 * into an archived PDF/A-3 for invoices whose CII was pre-attached at completion time.
 *
 * <p>The CII XML is <em>not</em> regenerated here — it is consumed from the
 * {@code <DocumentNo>_zugferd.xml} attachment that the {@code C_Invoice} completion gate
 * ({@code onComplete_validateAndAttachZugferd}) created when the invoice was completed.
 * This ensures each CII XML is generated exactly once (at completion), validated once,
 * and reused at archive time.
 *
 * <p>Registration: declared as a Spring {@code @Component} so
 * {@code SpringContextHolder.instance.getBeanOr(IArchiveReportBytesTransformer.class, null)} can
 * discover it in the application context.
 *
 * <p>No-op contract: returns the input bytes unchanged for:
 * <ul>
 *   <li>Records that are not {@code C_Invoice} (wrong table).</li>
 *   <li>{@code C_Invoice} records that have no {@code <DocumentNo>_zugferd.xml} attachment.</li>
 * </ul>
 *
 * <p>Error handling: if the PDF assembly fails, an {@link AdempiereException} is thrown so the
 * archive transaction rolls back — the caller (DefaultModelArchiver) will propagate it as an
 * archive failure.
 */
@Component
@RequiredArgsConstructor
public class ZugferdArchiveReportBytesTransformer implements IArchiveReportBytesTransformer
{
	private static final Logger log = LoggerFactory.getLogger(ZugferdArchiveReportBytesTransformer.class);

	@NonNull private final AttachmentEntryService attachmentEntryService;

	@Override
	public byte[] transform(@NonNull final TableRecordReference recordRef, final byte[] reportBytes)
	{
		// Fast-path: only C_Invoice is eligible
		if (!I_C_Invoice.Table_Name.equals(recordRef.getTableName()))
		{
			return reportBytes;
		}

		final InvoiceId invoiceId = InvoiceId.ofRepoId(recordRef.getRecord_ID());

		// Load the invoice to build the expected attachment filename. This transformer runs
		// asynchronously (DocOutboundWorkpackageProcessor) AFTER the completion transaction has
		// committed, so read committed state outside any thread transaction (loadOutOfTrx / TRXNAME_None).
		final I_C_Invoice invoice = InterfaceWrapperHelper.loadOutOfTrx(invoiceId.getRepoId(), I_C_Invoice.class);
		final String filename = invoice.getDocumentNo() + "_zugferd.xml";

		// Look up the pre-attached CII XML created by the completion gate
		final AttachmentEntry ciiAttachment = attachmentEntryService.getByFilenameOrNull(invoice, filename);
		if (ciiAttachment == null)
		{
			// No ZUGFeRD attachment: either this is not a ZUGFeRD invoice or the completion
			// gate has not run yet — pass through unchanged.
			log.debug("No {} attachment found for invoice {} — returning report bytes unchanged.", filename, invoiceId);
			return reportBytes;
		}

		final byte[] ciiBytes = attachmentEntryService.retrieveData(ciiAttachment.getId());
		final String ciiXml = new String(ciiBytes, StandardCharsets.UTF_8);

		log.debug("Embedding ZUGFeRD CII from attachment {} for invoice {}", filename, invoiceId);

		// Embed CII XML into the PDF/A-3 bytes
		try
		{
			final byte[] zugferdBytes = ZugferdAssembler.embed(reportBytes, ciiXml);
			log.debug("ZUGFeRD assembly complete for invoice {}. Output size: {} bytes", invoiceId, zugferdBytes.length);
			return zugferdBytes;
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("ZUGFeRD: Failed to embed CII XML into PDF/A-3 for invoice " + invoiceId, ex);
		}
	}
}
