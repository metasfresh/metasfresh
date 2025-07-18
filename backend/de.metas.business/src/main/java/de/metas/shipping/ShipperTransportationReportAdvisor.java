/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipping;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPPrintFormatQuery;
import de.metas.document.DocTypeId;
import de.metas.i18n.Language;
import de.metas.process.AdProcessId;
import de.metas.report.DocumentReportAdvisor;
import de.metas.report.DocumentReportAdvisorUtil;
import de.metas.report.DocumentReportInfo;
import de.metas.report.PrintFormatId;
import de.metas.report.StandardDocumentReportType;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
@RequiredArgsConstructor
public class ShipperTransportationReportAdvisor implements DocumentReportAdvisor
{

	private final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);

	@NonNull
	private final DocumentReportAdvisorUtil util;

	@Override
	public @NonNull String getHandledTableName()
	{
		return I_M_ShipperTransportation.Table_Name;
	}

	@Override
	public StandardDocumentReportType getStandardDocumentReportType()
	{
		return StandardDocumentReportType.SHIPPER_TRANSPORTATION;
	}

	@Override
	public @NonNull DocumentReportInfo getDocumentReportInfo(
			@NonNull final TableRecordReference recordRef,
			@Nullable final PrintFormatId adPrintFormatToUseId, final AdProcessId reportProcessIdToUse)
	{
		final ShipperTransportationId shipperTransportationId = recordRef.getIdAssumingTableName(I_M_ShipperTransportation.Table_Name, ShipperTransportationId::ofRepoId);
		final I_M_ShipperTransportation shipperTransportation = shipperTransportationDAO.getById(shipperTransportationId);
		final BPartnerId shipperPartnerId = BPartnerId.ofRepoId(shipperTransportation.getShipper_BPartner_ID());
		final I_C_BPartner shipperPartner = util.getBPartnerById(shipperPartnerId);

		final DocTypeId docTypeId = extractDocTypeId(shipperTransportation);
		final I_C_DocType docType = util.getDocTypeById(docTypeId);

		final Language language = util.getBPartnerLanguage(shipperPartner).orElse(null);

		final BPPrintFormatQuery bpPrintFormatQuery = BPPrintFormatQuery.builder()
				.adTableId(recordRef.getAdTableId())
				.bpartnerId(shipperPartnerId)
				.bPartnerLocationId(BPartnerLocationId.ofRepoId(shipperPartnerId, shipperTransportation.getShipper_Location_ID()))
				.docTypeId(docTypeId)
				.onlyCopiesGreaterZero(true)
				.build();

		return DocumentReportInfo.builder()
				.recordRef(TableRecordReference.of(I_M_ShipperTransportation.Table_Name, shipperTransportationId))
				.reportProcessId(reportProcessIdToUse)
				.copies(util.getDocumentCopies(docType, bpPrintFormatQuery))
				.documentNo(shipperTransportation.getDocumentNo())
				.bpartnerId(shipperPartnerId)
				.docTypeId(docTypeId)
				.language(language)
				.build();
	}

	@NonNull
	private DocTypeId extractDocTypeId(@NonNull final I_M_ShipperTransportation shipperTransportation)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(shipperTransportation.getC_DocType_ID());
		if (docTypeId != null)
		{
			return docTypeId;
		}
		throw new AdempiereException("No document type set");
	}
}
