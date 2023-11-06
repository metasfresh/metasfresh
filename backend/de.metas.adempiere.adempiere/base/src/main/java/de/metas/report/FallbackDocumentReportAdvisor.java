/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.report;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPPrintFormatQuery;
import de.metas.bpartner.service.BPartnerPrintFormatMap;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocTypeId;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.Language;
import de.metas.process.AdProcessId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;

import javax.annotation.Nullable;

// @Component // IMPORTANT: don't annotate it with Component
final class FallbackDocumentReportAdvisor implements DocumentReportAdvisor
{
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final DocumentReportAdvisorUtil util;

	public FallbackDocumentReportAdvisor(@NonNull final DocumentReportAdvisorUtil util)
	{
		this.util = util;
	}

	@Override
	@Deprecated
	@NonNull
	public String getHandledTableName()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	@Deprecated
	public StandardDocumentReportType getStandardDocumentReportType()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	@NonNull
	public DocumentReportInfo getDocumentReportInfo(
			@NonNull final TableRecordReference recordRef,
			@Nullable final PrintFormatId adPrintFormatToUseId,
			@Nullable final AdProcessId reportProcessIdToUse)
	{
		final Object record = recordRef.getModel(Object.class);

		final DocTypeId docTypeId = documentBL.getDocTypeId(record).orElse(null);
		final I_C_DocType docType = docTypeId != null ? util.getDocTypeById(docTypeId) : null;
		final String documentNo = documentBL.getDocumentNo(record);
		final BPartnerId bpartnerId = util.getBPartnerIdForModel(record).orElse(null);
		final I_C_BPartner bpartner = bpartnerId != null ? util.getBPartnerById(bpartnerId) : null;
		final Language language = bpartnerId != null
				? util.getBPartnerLanguage(bpartner).orElse(null)
				: null;

		final AdProcessId reportProcessId;
		if (reportProcessIdToUse != null)
		{
			reportProcessId = reportProcessIdToUse;
		}
		else
		{
			final PrintFormatId printFormatId = CoalesceUtil.coalesceSuppliers(
					() -> adPrintFormatToUseId,
					() -> getBPPrintFormatOrNull(bpartnerId, docTypeId, recordRef.getAdTableId()),
					() -> getDocTypePrintFormatOrNull(docType));
			if (printFormatId == null)
			{
				throw new AdempiereException(("@NotFound@ @AD_PrintFormat_ID@"));
			}

			reportProcessId = util.getReportProcessIdByPrintFormatId(printFormatId);
		}

		final BPartnerLocationId bPartnerLocationId = util.getBPartnerLocationId(bpartnerId, record);
		final BPPrintFormatQuery bpPrintFormatQuery = bpartnerId == null ? null : BPPrintFormatQuery.builder()
				.adTableId(recordRef.getAdTableId())
				.bpartnerId(bpartnerId)
				.bPartnerLocationId(bPartnerLocationId)
				.docTypeId(docTypeId)
				.onlyCopiesGreaterZero(true)
				.build();

		return DocumentReportInfo.builder()
				.recordRef(recordRef)
				.reportProcessId(reportProcessId)
				.copies(util.getDocumentCopies(docType, bpPrintFormatQuery))
				.documentNo(documentNo)
				.bpartnerId(bpartnerId)
				.docTypeId(docTypeId)
				.language(language)
				//.printOptionsDescriptor() // will be fetched later based on reportProcessId
				//.printOptions() // none
				.build();
	}

	@Nullable
	private PrintFormatId getBPPrintFormatOrNull(
			@Nullable final BPartnerId bpartnerId,
			@Nullable final DocTypeId docTypeId,
			@NonNull final AdTableId adTableId)
	{
		if (bpartnerId == null)
		{
			return null;
		}

		final BPartnerPrintFormatMap bpPrintFormats = util.getBPartnerPrintFormats(bpartnerId);

		// By DocType
		if (docTypeId != null)
		{
			final PrintFormatId printFormatId = bpPrintFormats.getPrintFormatIdByDocTypeId(docTypeId).orElse(null);
			if (printFormatId != null)
			{
				return printFormatId;
			}
		}

		// By Table
		return bpPrintFormats.getFirstByTableId(adTableId).orElse(null);
	}

	@Nullable
	private PrintFormatId getDocTypePrintFormatOrNull(@Nullable final I_C_DocType docType)
	{
		return docType != null
				? PrintFormatId.ofRepoIdOrNull(docType.getAD_PrintFormat_ID())
				: null;
	}
}
