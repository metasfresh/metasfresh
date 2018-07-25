package de.metas.contracts.subscription.order.restart;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_C_Order;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.order.OrderId;
import de.metas.util.RelatedRecordsProvider;
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
public class RelatedSubscriptionsForOrdersProvider implements RelatedRecordsProvider
{
	@Override
	public SourceRecordsKey getSourceRecordsKey()
	{
		return SourceRecordsKey.of(I_C_Order.Table_Name);
	}

	@Override
	public IPair<SourceRecordsKey, List<ITableRecordReference>> provideRelatedRecords(
			@NonNull final List<ITableRecordReference> records)
	{
		// get the C_Flatrate_Term records that *directly* reference the given orders.
		final List<OrderId> orderIds = records
				.stream()
				.map(ref -> OrderId.ofRepoId(ref.getRecord_ID()))
				.collect(ImmutableList.toImmutableList());

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ImmutableList<I_C_Flatrate_Term> flatrateTermRecords = queryBL
				.createQueryBuilder(I_C_Order.class)
				.addInArrayFilter(I_C_Order.COLUMNNAME_C_Order_ID, orderIds)
				.addOnlyActiveRecordsFilter()
				.andCollectChildren(I_C_OrderLine.COLUMN_C_Order_ID)
				.addOnlyActiveRecordsFilter()
				.andCollectChildren(I_C_Flatrate_Term.COLUMN_C_OrderLine_Term_ID)
				.addOnlyActiveRecordsFilter()
				.create()
				.listImmutable(I_C_Flatrate_Term.class);

		// now, get *all* C_Flatrate_Term_ID including the successors of the terms we got until now
		// note: we need them because in another provider we are going to load their invoices.
		// note II: we iterate because right now that's be simples and most stable way (and we assume that there aren't a lot of terms to iterate)
		final ImmutableList.Builder<ITableRecordReference> resultReferences = ImmutableList.builder();

		for (final I_C_Flatrate_Term flatrateTermRecord : flatrateTermRecords)
		{
			I_C_Flatrate_Term nextFlatrateTermRecord = flatrateTermRecord;
			resultReferences.add(TableRecordReference.of(I_C_Flatrate_Term.Table_Name, nextFlatrateTermRecord.getC_Flatrate_Term_ID()));

			while (nextFlatrateTermRecord.getC_FlatrateTerm_Next_ID() > 0)
			{
				nextFlatrateTermRecord = nextFlatrateTermRecord.getC_FlatrateTerm_Next();
				resultReferences.add(TableRecordReference.of(I_C_Flatrate_Term.Table_Name, nextFlatrateTermRecord.getC_Flatrate_Term_ID()));
			}
		}

		// construct the result and be done
		final SourceRecordsKey resultSourceRecordsKey = SourceRecordsKey.of(I_C_Flatrate_Term.Table_Name);

		final IPair<SourceRecordsKey, List<ITableRecordReference>> result = ImmutablePair.of(resultSourceRecordsKey, resultReferences.build());
		return result;
	}

}
