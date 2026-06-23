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

import de.metas.document.archive.spi.IArchiveReportBytesTransformer;
import de.metas.einvoice.EInvoiceCiiService;
import de.metas.einvoice.EInvoiceCiiService.GenerateAndValidateResult;
import de.metas.einvoice.EInvoiceConfigService;
import de.metas.einvoice.EInvoiceRecipientConfig;
import de.metas.invoice.InvoiceId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * {@link IArchiveReportBytesTransformer} implementation that embeds a ZUGFeRD/Factur-X CII XML
 * into an archived PDF/A-3 for invoices whose BPartner is configured as
 * {@code EInvoiceType = ZUGFeRD}.
 *
 * <p>Registration: declared as a Spring {@code @Component} so
 * {@code SpringContextHolder.instance.getBeanOpt(IArchiveReportBytesTransformer.class)} can
 * discover it in the application context.
 *
 * <p>No-op contract: returns the input bytes unchanged for:
 * <ul>
 *   <li>Records that are not {@code C_Invoice} (wrong table).</li>
 *   <li>{@code C_Invoice} records whose BPartner is not an e-invoice recipient.</li>
 *   <li>{@code C_Invoice} records whose e-invoice format is not {@link de.metas.einvoice.EInvoiceFormat#ZUGFeRD}.</li>
 * </ul>
 *
 * <p>Error handling: if the CII generation or PDF assembly fails, an
 * {@link AdempiereException} is thrown so the archive transaction rolls back — the caller
 * (DefaultModelArchiver) will propagate it as an archive failure.
 */
@Component
@RequiredArgsConstructor
public class ZugferdArchiveReportBytesTransformer implements IArchiveReportBytesTransformer
{
	private static final Logger log = LoggerFactory.getLogger(ZugferdArchiveReportBytesTransformer.class);

	@NonNull private final EInvoiceConfigService configService;
	@NonNull private final EInvoiceCiiService ciiService;

	@Override
	@NonNull
	public byte[] transform(@NonNull final TableRecordReference recordRef, @NonNull final byte[] reportBytes)
	{
		// Fast-path: only C_Invoice is eligible
		if (!I_C_Invoice.Table_Name.equals(recordRef.getTableName()))
		{
			return reportBytes;
		}

		final InvoiceId invoiceId = InvoiceId.ofRepoId(recordRef.getRecord_ID());

		// Resolve config — returns empty if not an e-invoice recipient or not ZUGFeRD
		final EInvoiceRecipientConfig config = configService.resolveForInvoice(invoiceId).orElse(null);
		if (config == null || !config.getFormat().isZUGFeRD())
		{
			return reportBytes;
		}

		log.debug("Embedding ZUGFeRD CII for invoice {}", invoiceId);

		// Generate and validate CII XML
		final GenerateAndValidateResult ciiResult = ciiService.generateAndValidate(invoiceId)
				.orElseThrow(() -> new AdempiereException(
						"ZUGFeRD: EInvoice config resolved but CII generation returned empty for invoice " + invoiceId));

		if (!ciiResult.isValid())
		{
			throw new AdempiereException(
					"ZUGFeRD: CII XML for invoice " + invoiceId + " failed Schematron validation. "
							+ "Failing rule IDs: " + ciiResult.getFatalAndErrorRuleIds())
					.markAsUserValidationError();
		}

		// Embed CII XML into the PDF/A-3 bytes
		try
		{
			final byte[] zugferdBytes = ZugferdAssembler.embed(reportBytes, ciiResult.getCiiXml());
			log.debug("ZUGFeRD assembly complete for invoice {}. Output size: {} bytes", invoiceId, zugferdBytes.length);
			return zugferdBytes;
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("ZUGFeRD: Failed to embed CII XML into PDF/A-3 for invoice " + invoiceId, ex);
		}
	}
}
