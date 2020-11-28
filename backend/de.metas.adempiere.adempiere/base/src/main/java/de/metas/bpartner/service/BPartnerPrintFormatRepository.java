/**
 *
 */
package de.metas.bpartner.service;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BP_PrintFormat;
import org.springframework.stereotype.Repository;

import de.metas.bpartner.BPartnerId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Repository
public class BPartnerPrintFormatRepository
{
	public BPPrintFormat getByQuery(@NonNull final BPPrintFormatQuery bpPrintFormatQuery)
	{
		final I_C_BP_PrintFormat bpPrintFormatRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BP_PrintFormat.class)
				.addEqualsFilter(I_C_BP_PrintFormat.COLUMNNAME_C_BPartner_ID, bpPrintFormatQuery.getBpartnerId().getRepoId())
				.addEqualsFilter(I_C_BP_PrintFormat.COLUMNNAME_AD_Table_ID, bpPrintFormatQuery.getAdTableId())
				.addEqualsFilter(I_C_BP_PrintFormat.COLUMNNAME_C_DocType_ID, bpPrintFormatQuery.getDocTypeId())
				.addEqualsFilter(I_C_BP_PrintFormat.COLUMNNAME_AD_PrintFormat_ID, bpPrintFormatQuery.getPrintFormatId())
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyOrNull(I_C_BP_PrintFormat.class);

		if (bpPrintFormatRecord == null)
		{
			return null;
		}

		return toBPPrintFormat(bpPrintFormatRecord);
	}

	public BPPrintFormat toBPPrintFormat(@NonNull final I_C_BP_PrintFormat bpPrinfFormatDataRecord)
	{
		return BPPrintFormat.builder()
				.bpartnerId(BPartnerId.ofRepoId(bpPrinfFormatDataRecord.getC_BPartner_ID()))
				.adTableId(bpPrinfFormatDataRecord.getAD_Table_ID())
				.docTypeId(bpPrinfFormatDataRecord.getC_DocType_ID())
				.printFormatId(bpPrinfFormatDataRecord.getAD_PrintFormat_ID())
				.bpPrintFormatId(bpPrinfFormatDataRecord.getC_BP_PrintFormat_ID())
				.build();
	}

	public BPPrintFormat save(@NonNull final BPPrintFormat bpPrintFormat)
	{
		final I_C_BP_PrintFormat bpPrintFormatRecord = createOrUpdateRecord(bpPrintFormat);
		saveRecord(bpPrintFormatRecord);

		return bpPrintFormat.toBuilder()
				.bpPrintFormatId(bpPrintFormatRecord.getC_BP_PrintFormat_ID())
				.build();
	}

	private I_C_BP_PrintFormat createOrUpdateRecord(@NonNull final BPPrintFormat bpPrintFormat)
	{
		final I_C_BP_PrintFormat bpPrintFormatRcord;
		if (bpPrintFormat.getBpPrintFormatId() > 0)
		{
			bpPrintFormatRcord = load(bpPrintFormat.getBpPrintFormatId(), I_C_BP_PrintFormat.class);
		}
		else
		{
			bpPrintFormatRcord = newInstance(I_C_BP_PrintFormat.class);
		}

		bpPrintFormatRcord.setC_BPartner_ID(bpPrintFormat.getBpartnerId().getRepoId());
		bpPrintFormatRcord.setC_DocType_ID(bpPrintFormat.getDocTypeId());
		bpPrintFormatRcord.setAD_Table_ID(bpPrintFormat.getAdTableId());
		bpPrintFormatRcord.setAD_PrintFormat_ID(bpPrintFormat.getPrintFormatId());

		return bpPrintFormatRcord;
	}
}
