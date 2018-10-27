package de.metas.invoice.order.restart;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import com.google.common.collect.ImmutableList;

import de.metas.order.OrderId;
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

public class RelatedInvoicesForOrdersProvider implements RelatedRecordsProvider
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
		final ImmutableList<OrderId> orderIds = records
				.stream()
				.map(ref -> OrderId.ofRepoId(ref.getRecord_ID()))
				.collect(ImmutableList.toImmutableList());

		final ImmutableList<ITableRecordReference> invoiceReferences = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_OrderLine.COLUMN_C_Order_ID, orderIds)
				.andCollectChildren(I_C_InvoiceLine.COLUMN_C_OrderLine_ID)
				.addOnlyActiveRecordsFilter()
				.andCollect(I_C_InvoiceLine.COLUMN_C_Invoice_ID)
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds()
				.stream()
				.distinct()
				.map(invoiceRepoId -> TableRecordReference.of(I_C_Invoice.Table_Name, invoiceRepoId))
				.collect(ImmutableList.toImmutableList());

		final SourceRecordsKey sourceRecordsKey = SourceRecordsKey.of(I_C_Invoice.Table_Name);

		final IPair<SourceRecordsKey, List<ITableRecordReference>> result = ImmutablePair.of(sourceRecordsKey, invoiceReferences);

		return result;
	}
}
