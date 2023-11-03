/**
 *
 */
package de.metas.bpartner.service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.table.api.AdTableId;
import org.compiere.model.I_C_BP_PrintFormat;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
	@Nullable
	public BPPrintFormat getByQuery(@NonNull final BPPrintFormatQuery bpPrintFormatQuery, final boolean onlyCopiesGreaterZero)
	{
		final IQueryBuilder<I_C_BP_PrintFormat> query = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BP_PrintFormat.class)
				.addEqualsFilter(I_C_BP_PrintFormat.COLUMNNAME_C_BPartner_ID, bpPrintFormatQuery.getBpartnerId().getRepoId())
				.addOnlyActiveRecordsFilter();

		if(bpPrintFormatQuery.getAdTableId() == null)
		{
			query.addEqualsFilter(I_C_BP_PrintFormat.COLUMNNAME_AD_Table_ID, null);
		}
		else
		{
			query.addInArrayFilter(I_C_BP_PrintFormat.COLUMNNAME_AD_Table_ID, bpPrintFormatQuery.getAdTableId().getRepoId(), null);
		}

		if(bpPrintFormatQuery.getDocTypeId() == null)
		{
			query.addEqualsFilter(I_C_BP_PrintFormat.COLUMNNAME_C_DocType_ID, null);
		}
		else
		{
			query.addInArrayFilter(I_C_BP_PrintFormat.COLUMNNAME_C_DocType_ID, bpPrintFormatQuery.getDocTypeId().getRepoId(), null);
		}

		if(bpPrintFormatQuery.getBPartnerLocationId() == null)
		{
			query.addEqualsFilter(I_C_BP_PrintFormat.COLUMNNAME_C_BPartner_Location_ID, null);
		}
		else
		{
			query.addInArrayFilter(I_C_BP_PrintFormat.COLUMNNAME_C_BPartner_Location_ID, bpPrintFormatQuery.getBPartnerLocationId().getRepoId(), null);
		}

		if(bpPrintFormatQuery.getPrintFormatId() > 0)
		{
			query.addEqualsFilter(I_C_BP_PrintFormat.COLUMNNAME_AD_PrintFormat_ID, bpPrintFormatQuery.getPrintFormatId());
		}

		if(onlyCopiesGreaterZero)
		{
			query.addCompareFilter(I_C_BP_PrintFormat.COLUMNNAME_DocumentCopies_Override, CompareQueryFilter.Operator.GREATER, 0);
		}
        query.orderBy(I_C_BP_PrintFormat.COLUMNNAME_SeqNo);
		final I_C_BP_PrintFormat bpPrintFormatRecord = query.create().first(I_C_BP_PrintFormat.class);

		return bpPrintFormatRecord == null ? null : toBPPrintFormat(bpPrintFormatRecord);
	}

	public BPPrintFormat toBPPrintFormat(@NonNull final I_C_BP_PrintFormat bpPrinfFormatDataRecord)
	{
		return BPPrintFormat.builder()
				.bpartnerId(BPartnerId.ofRepoId(bpPrinfFormatDataRecord.getC_BPartner_ID()))
				.adTableId(AdTableId.ofRepoId(bpPrinfFormatDataRecord.getAD_Table_ID()))
				.docTypeId(DocTypeId.ofRepoId(bpPrinfFormatDataRecord.getC_DocType_ID()))
				.printFormatId(bpPrinfFormatDataRecord.getAD_PrintFormat_ID())
				.bpPrintFormatId(bpPrinfFormatDataRecord.getC_BP_PrintFormat_ID())
				.bPartnerLocationId(BPartnerLocationId.ofRepoIdOrNull(bpPrinfFormatDataRecord.getC_BPartner_ID(), bpPrinfFormatDataRecord.getC_BPartner_Location_ID()))
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
		final I_C_BP_PrintFormat bpPrintFormatRecord;
		if (bpPrintFormat.getBpPrintFormatId() > 0)
		{
			bpPrintFormatRecord = load(bpPrintFormat.getBpPrintFormatId(), I_C_BP_PrintFormat.class);
		}
		else
		{
			bpPrintFormatRecord = newInstance(I_C_BP_PrintFormat.class);
		}

		bpPrintFormatRecord.setC_BPartner_ID(bpPrintFormat.getBpartnerId().getRepoId());
		if(bpPrintFormat.getDocTypeId() != null)
		{
			bpPrintFormatRecord.setC_DocType_ID(bpPrintFormat.getDocTypeId().getRepoId());
		}
		if(bpPrintFormat.getAdTableId() != null)
		{
			bpPrintFormatRecord.setAD_Table_ID(bpPrintFormat.getAdTableId().getRepoId());
		}
		if(bpPrintFormat.getPrintFormatId() > 0)
		{
			bpPrintFormatRecord.setAD_PrintFormat_ID(bpPrintFormat.getPrintFormatId());
		}
		if(bpPrintFormat.getBPartnerLocationId() != null)
		{
			bpPrintFormatRecord.setC_BPartner_Location_ID(bpPrintFormat.getBPartnerLocationId().getRepoId());
		}
		if(bpPrintFormat.getPrintCopies() != null && bpPrintFormat.getPrintCopies().isGreaterThanZero())
		{
			bpPrintFormatRecord.setDocumentCopies_Override(bpPrintFormat.getPrintCopies().toInt());
		}

		return bpPrintFormatRecord;
	}
}
