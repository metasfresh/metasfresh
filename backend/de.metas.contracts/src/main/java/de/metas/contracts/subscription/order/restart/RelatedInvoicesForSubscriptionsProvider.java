package de.metas.contracts.subscription.order.restart;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import de.metas.common.util.pair.IPair;
import org.adempiere.util.lang.ITableRecordReference;
import de.metas.common.util.pair.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.util.RelatedRecordsProvider;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
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
public class RelatedInvoicesForSubscriptionsProvider implements RelatedRecordsProvider
{
	@Override
	public SourceRecordsKey getSourceRecordsKey()
	{
		return SourceRecordsKey.of(I_C_Flatrate_Term.Table_Name);
	}

	@Override
	public IPair<SourceRecordsKey, List<ITableRecordReference>> provideRelatedRecords(
			@NonNull final List<ITableRecordReference> records)
	{
		final List<FlatrateTermId> flatrateTermIds = records
				.stream()
				.map(ref -> FlatrateTermId.ofRepoId(ref.getRecord_ID()))
				.collect(ImmutableList.toImmutableList());

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ImmutableList<ITableRecordReference> invoiceReferences = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, getTableId(I_C_Flatrate_Term.class))
				.addInArrayFilter(I_C_Invoice_Candidate.COLUMNNAME_Record_ID, flatrateTermIds)
				.andCollectChildren(I_C_Invoice_Line_Alloc.COLUMN_C_Invoice_Candidate_ID)
				.addOnlyActiveRecordsFilter()
				.andCollect(I_C_Invoice_Line_Alloc.COLUMN_C_InvoiceLine_ID)
				.andCollect(I_C_InvoiceLine.COLUMN_C_Invoice_ID)
				.create()
				.listIds()
				.stream()
				.map(invoiceRepoId -> TableRecordReference.of(I_C_Invoice.Table_Name, invoiceRepoId))
				.collect(ImmutableList.toImmutableList());

		final SourceRecordsKey resultSourceRecordsKey = SourceRecordsKey.of(I_C_Invoice.Table_Name);

		final IPair<SourceRecordsKey, List<ITableRecordReference>> result = ImmutablePair.of(resultSourceRecordsKey, invoiceReferences);

		return result;
	}

}
