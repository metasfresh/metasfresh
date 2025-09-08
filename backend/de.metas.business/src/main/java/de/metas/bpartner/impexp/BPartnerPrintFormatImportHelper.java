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

package de.metas.bpartner.impexp;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPPrintFormat;
import de.metas.bpartner.service.BPPrintFormatQuery;
import de.metas.bpartner.service.BPartnerPrintFormatRepository;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.report.PrintCopies;
import de.metas.report.PrintFormatId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.model.I_I_BPartner;

/* package */ class BPartnerPrintFormatImportHelper
{
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	@NonNull private final BPartnerPrintFormatRepository bPartnerPrintFormatRepository;

	@Builder
	private BPartnerPrintFormatImportHelper(
			@NonNull final BPartnerPrintFormatRepository bPartnerPrintFormatRepository)
	{
		this.bPartnerPrintFormatRepository = bPartnerPrintFormatRepository;
	}

	public void importRecord(@NonNull final BPartnerImportContext context)
	{
		final I_I_BPartner importRecord = context.getCurrentImportRecord();
		if (importRecord.getAD_PrintFormat_ID() <= 0 && Check.isBlank(importRecord.getPrintFormat_DocBaseType()))
		{
			return;
		}

		final PrintFormatId printFormatId = PrintFormatId.ofRepoIdOrNull(importRecord.getAD_PrintFormat_ID());
		final DocBaseType docBaseType;

		if(!Check.isBlank(importRecord.getPrintFormat_DocBaseType()))
		{
			docBaseType = DocBaseType.ofCode(importRecord.getPrintFormat_DocBaseType());
		}
		// for vendors we have print format for purchase order
		else if (importRecord.isVendor())
		{
			docBaseType = DocBaseType.PurchaseOrder;
		}
		// for customer we have print format for delivery
		else
		{
			docBaseType = DocBaseType.Shipment;
		}

		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(docBaseType)
				.adClientId(importRecord.getAD_Client_ID())
				.adOrgId(importRecord.getAD_Org_ID())
				.build());
		final AdTableId adTableId = AdTableId.ofRepoId(tableDAO.retrieveTableId(docBaseType.getTableName()));

		final BPartnerId bPartnerId = BPartnerId.ofRepoId(importRecord.getC_BPartner_ID());
		final BPartnerLocationId bPartnerLocationId = importRecord.isSetPrintFormat_C_BPartner_Location()
				? BPartnerLocationId.ofRepoIdOrNull(bPartnerId, importRecord.getC_BPartner_Location_ID())
				: null;
		final BPPrintFormatQuery bpPrintFormatQuery = BPPrintFormatQuery.builder()
				.printFormatId(printFormatId)
				.docTypeId(docTypeId)
				.adTableId(adTableId)
				.bPartnerLocationId(bPartnerLocationId)
				.bpartnerId(bPartnerId)
				.isExactMatch(true)
				.build();

		BPPrintFormat bpPrintFormat = bPartnerPrintFormatRepository.getByQuery(bpPrintFormatQuery);
		if (bpPrintFormat == null)
		{
			bpPrintFormat = BPPrintFormat.builder()
					.adTableId(adTableId)
					.docTypeId(docTypeId)
					.printFormatId(printFormatId)
					.bpartnerId(BPartnerId.ofRepoId(importRecord.getC_BPartner_ID()))
					.bPartnerLocationId(bPartnerLocationId)
					.printCopies(PrintCopies.ofInt(importRecord.getPrintFormat_DocumentCopies_Override()))
					.build();

		}

		bpPrintFormat = bPartnerPrintFormatRepository.save(bpPrintFormat);

		importRecord.setC_BP_PrintFormat_ID(bpPrintFormat.getBpPrintFormatId());
	}
}
