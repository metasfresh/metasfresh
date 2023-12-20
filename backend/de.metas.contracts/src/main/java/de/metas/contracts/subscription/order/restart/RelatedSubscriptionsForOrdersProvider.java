package de.metas.contracts.subscription.order.restart;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import de.metas.common.util.pair.IPair;
import org.adempiere.util.lang.ITableRecordReference;
import de.metas.common.util.pair.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_C_Order;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.order.OrderId;
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

		final List<Integer> flatrateTermRecords = queryBL
				.createQueryBuilder(I_C_Order.class)
				.addInArrayFilter(I_C_Order.COLUMNNAME_C_Order_ID, orderIds)
				.addOnlyActiveRecordsFilter()
				.andCollectChildren(I_C_OrderLine.COLUMN_C_Order_ID)
				.addOnlyActiveRecordsFilter()
				.andCollectChildren(I_C_Flatrate_Term.COLUMN_C_OrderLine_Term_ID) // as of now, extended terms inherit their predecessor's order line ID
				.addOnlyActiveRecordsFilter()

				// order latest first, because we need to void them in that order
				.orderBy().addColumnDescending(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID).endOrderBy()
				.create()
				.listIds();

		final ImmutableList<ITableRecordReference> resultReferences = flatrateTermRecords
				.stream()
				.map(termId -> TableRecordReference.of(I_C_Flatrate_Term.Table_Name, termId))
				.collect(ImmutableList.toImmutableList());

		// construct the result and be done
		final SourceRecordsKey resultSourceRecordsKey = SourceRecordsKey.of(I_C_Flatrate_Term.Table_Name);

		final IPair<SourceRecordsKey, List<ITableRecordReference>> result = ImmutablePair.of(resultSourceRecordsKey, resultReferences);
		return result;
	}

}
