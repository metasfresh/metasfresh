package de.metas.ordercandidate.api;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.util.CCache;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ordercandidate.api.OLCandAggregationColumn.Granularity;
import de.metas.ordercandidate.model.I_C_OLCandAggAndOrder;
import de.metas.ordercandidate.model.I_C_OLCandProcessor;
import de.metas.ordercandidate.model.X_C_OLCandAggAndOrder;

/*
 * #%L
 * de.metas.swat.base
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

@Repository
public class OLCandProcessorRepository
{
	private final CCache<Integer, OLCandProcessorDescriptor> processorsById = CCache.<Integer, OLCandProcessorDescriptor> newCache(I_C_OLCandProcessor.Table_Name + "#by#ID", 10, CCache.EXPIREMINUTES_Never)
			.addResetForTableName(I_C_OLCandAggAndOrder.Table_Name);

	private static final Map<String, Granularity> granularityByADRefListValue = ImmutableMap.<String, Granularity> builder()
			.put(X_C_OLCandAggAndOrder.GRANULARITY_Tag, Granularity.Day)
			.put(X_C_OLCandAggAndOrder.GRANULARITY_Woche, Granularity.Week)
			.put(X_C_OLCandAggAndOrder.GRANULARITY_Monat, Granularity.Month)
			.build();

	public OLCandProcessorDescriptor getById(final int olCandProcessorId)
	{
		return processorsById.getOrLoad(olCandProcessorId, () -> retrieveById(olCandProcessorId));
	}

	private OLCandProcessorDescriptor retrieveById(final int olCandProcessorId)
	{
		Check.assume(olCandProcessorId > 0, "olCandProcessorId > 0");

		final I_C_OLCandProcessor olCandProcessorPO = loadOutOfTrx(olCandProcessorId, I_C_OLCandProcessor.class);
		Check.assumeNotNull(olCandProcessorPO, "olCandProcessorPO is not null for olCandProcessorId={}", olCandProcessorId);

		return toOLCandProcessorDescriptor(olCandProcessorPO);
	}

	private OLCandProcessorDescriptor toOLCandProcessorDescriptor(final I_C_OLCandProcessor olCandProcessorPO)
	{
		final int olCandProcessorId = olCandProcessorPO.getC_OLCandProcessor_ID();

		return OLCandProcessorDescriptor.builder()
				.id(olCandProcessorId)
				.defaults(OLCandOrderDefaults.builder()
						.docTypeTargetId(olCandProcessorPO.getC_DocTypeTarget_ID())
						.deliveryRule(olCandProcessorPO.getDeliveryRule())
						.deliveryViaRule(olCandProcessorPO.getDeliveryViaRule())
						.shipperId(olCandProcessorPO.getM_Shipper_ID())
						.warehouseId(olCandProcessorPO.getM_Warehouse_ID())
						.freightCostRule(olCandProcessorPO.getFreightCostRule())
						.paymentRule(olCandProcessorPO.getPaymentRule())
						.paymentTermId(olCandProcessorPO.getC_PaymentTerm_ID())
						.invoiceRule(olCandProcessorPO.getInvoiceRule())
						.build())
				.userInChangeId(olCandProcessorPO.getAD_User_InCharge_ID())
				.aggregationInfo(retrieveOLCandAggregation(olCandProcessorId))
				.build();
	}

	private OLCandAggregation retrieveOLCandAggregation(final int olCandProcessorId)
	{
		final List<OLCandAggregationColumn> columns = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_OLCandAggAndOrder.class)
				.addEqualsFilter(I_C_OLCandAggAndOrder.COLUMN_C_OLCandProcessor_ID, olCandProcessorId)
				.addOnlyActiveRecordsFilter()
				.orderBy()
				.addColumn(I_C_OLCandAggAndOrder.COLUMN_OrderBySeqNo)
				.endOrderBy()
				.create()
				.stream(I_C_OLCandAggAndOrder.class)
				.map(this::createOLCandAggregationColumn)
				.collect(ImmutableList.toImmutableList());

		return OLCandAggregation.of(columns);
	}

	private OLCandAggregationColumn createOLCandAggregationColumn(final I_C_OLCandAggAndOrder olCandAgg)
	{
		final I_AD_Column adColumn = olCandAgg.getAD_Column_OLCand();

		return OLCandAggregationColumn.builder()
				.columnName(adColumn.getColumnName())
				.adColumnId(adColumn.getAD_Column_ID())
				.orderBySeqNo(olCandAgg.getOrderBySeqNo())
				.splitOrderDiscriminator(olCandAgg.isSplitOrder())
				.groupByColumn(olCandAgg.isGroupBy())
				.granularity(granularityByADRefListValue.get(olCandAgg.getGranularity()))
				.build();
	}

	public void handleADSchedulerBeforeDelete(final int adSchedulerId)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_OLCandProcessor.class)
				.addEqualsFilter(I_C_OLCandProcessor.COLUMN_AD_Scheduler_ID, adSchedulerId)
				.create()
				.list(I_C_OLCandProcessor.class)
				.forEach(processor -> {
					processor.setAD_Scheduler_ID(0);
					InterfaceWrapperHelper.save(processor);
				});
	}
}
