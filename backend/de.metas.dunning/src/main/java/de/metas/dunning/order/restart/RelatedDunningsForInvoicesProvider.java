package de.metas.dunning.order.restart;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning.model.I_C_DunningDoc_Line;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.invoice.InvoiceId;
import de.metas.util.RelatedRecordsProvider;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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

@Component
public class RelatedDunningsForInvoicesProvider implements RelatedRecordsProvider
{

	@Override
	public SourceRecordsKey getSourceRecordsKey()
	{
		return SourceRecordsKey.of(I_C_Invoice.Table_Name);
	}

	@Override
	public IPair<SourceRecordsKey, List<ITableRecordReference>> provideRelatedRecords(
			@NonNull final List<ITableRecordReference> records)
	{
		final ImmutableList<InvoiceId> invoiceIds = records
				.stream()
				.map(ref -> InvoiceId.ofRepoId(ref.getRecord_ID()))
				.collect(ImmutableList.toImmutableList());

		final List<Integer> dunningDocIds = Services.get(IQueryBL.class).createQueryBuilder(I_C_Dunning_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Dunning_Candidate.COLUMN_AD_Table_ID, getTableId(I_C_Invoice.class))
				.addInArrayFilter(I_C_Dunning_Candidate.COLUMN_Record_ID, invoiceIds)
				.andCollectChildren(I_C_DunningDoc_Line_Source.COLUMN_C_Dunning_Candidate_ID)
				.addOnlyActiveRecordsFilter()
				.andCollect(I_C_DunningDoc_Line_Source.COLUMN_C_DunningDoc_Line_ID)
				.addOnlyActiveRecordsFilter()
				.andCollect(I_C_DunningDoc_Line.COLUMN_C_DunningDoc_ID)
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds();

		final SourceRecordsKey sourceRecordsKey = SourceRecordsKey.of(I_C_DunningDoc.Table_Name);

		final ImmutableList<ITableRecordReference> referenceList = dunningDocIds
				.stream()
				.distinct()
				.map(dunningDocId -> TableRecordReference.of(I_C_DunningDoc.Table_Name, dunningDocId))
				.collect(ImmutableList.toImmutableList());

		return ImmutablePair.of(sourceRecordsKey, referenceList);
	}
}
