/*
 * #%L
 * de.metas.manufacturing
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

package de.metas.distribution.ddorder.lowlevel;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPPrintFormatQuery;
import de.metas.common.util.CoalesceUtil;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.document.DocTypeId;
import de.metas.i18n.Language;
import de.metas.process.AdProcessId;
import de.metas.report.DocumentReportAdvisor;
import de.metas.report.DocumentReportAdvisorUtil;
import de.metas.report.DocumentReportInfo;
import de.metas.report.PrintFormatId;
import de.metas.report.StandardDocumentReportType;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.eevolution.model.I_DD_Order;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class DistributionOrderDocumentReportAdvisor implements DocumentReportAdvisor
{
	private final DDOrderLowLevelService ddOrderLowLevelService;
	private final DocumentReportAdvisorUtil util;

	public DistributionOrderDocumentReportAdvisor(
			@NonNull final DDOrderLowLevelService ddOrderLowLevelService,
			@NonNull final DocumentReportAdvisorUtil util)
	{
		this.ddOrderLowLevelService = ddOrderLowLevelService;
		this.util = util;
	}

	@Override
	public @NonNull String getHandledTableName()
	{
		return I_DD_Order.Table_Name;
	}

	@Override
	public StandardDocumentReportType getStandardDocumentReportType()
	{
		return StandardDocumentReportType.DISTRIBUTION_ORDER;
	}

	@Override
	public @NonNull DocumentReportInfo getDocumentReportInfo(
			@NonNull final TableRecordReference recordRef,
			@Nullable final PrintFormatId adPrintFormatToUseId, final AdProcessId reportProcessIdToUse)
	{
		final DDOrderId ddOrderId = recordRef.getIdAssumingTableName(I_DD_Order.Table_Name, DDOrderId::ofRepoId);
		final I_DD_Order ddOrder = ddOrderLowLevelService.getById(ddOrderId);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(ddOrder.getC_BPartner_ID());
		final I_C_BPartner bpartner = util.getBPartnerById(bpartnerId);

		final DocTypeId docTypeId = extractDocTypeId(ddOrder);
		final I_C_DocType docType = util.getDocTypeById(docTypeId);

		final ClientId clientId = ClientId.ofRepoId(ddOrder.getAD_Client_ID());

		final PrintFormatId printFormatId = CoalesceUtil.coalesceSuppliers(
				() -> adPrintFormatToUseId,
				() -> util.getBPartnerPrintFormats(bpartnerId).getPrintFormatIdByDocTypeId(docTypeId).orElse(null),
				() -> PrintFormatId.ofRepoIdOrNull(docType.getAD_PrintFormat_ID()),
				() -> util.getDefaultPrintFormats(clientId).getDistributionOrderPrintFormatId());
		if (printFormatId == null)
		{
			throw new AdempiereException("@NotFound@ @AD_PrintFormat_ID@");
		}

		final Language language = util.getBPartnerLanguage(bpartner).orElse(null);

		final BPPrintFormatQuery bpPrintFormatQuery = BPPrintFormatQuery.builder()
				.adTableId(recordRef.getAdTableId())
				.bpartnerId(bpartnerId)
				.bPartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, ddOrder.getC_BPartner_Location_ID()))
				.docTypeId(docTypeId)
				.onlyCopiesGreaterZero(true)
				.build();

		return DocumentReportInfo.builder()
				.recordRef(TableRecordReference.of(I_DD_Order.Table_Name, ddOrderId))
				.reportProcessId(util.getReportProcessIdByPrintFormatId(printFormatId))
				.copies(util.getDocumentCopies(docType, bpPrintFormatQuery))
				.documentNo(ddOrder.getDocumentNo())
				.bpartnerId(bpartnerId)
				.docTypeId(docTypeId)
				.language(language)
				.build();
	}

	private DocTypeId extractDocTypeId(@NonNull final I_DD_Order ddOrder)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(ddOrder.getC_DocType_ID());
		if (docTypeId != null)
		{
			return docTypeId;
		}
		throw new AdempiereException("No document type set");
	}
}
