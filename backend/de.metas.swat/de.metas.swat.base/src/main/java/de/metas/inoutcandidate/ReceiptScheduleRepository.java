/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.inoutcandidate;

import static de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_AD_Client_ID;
import static de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_ExportStatus;
import static de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID;
import static de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMNNAME_Processed;
import static de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMN_CanBeExportedFrom;
import static de.metas.inoutcandidate.model.I_M_ReceiptSchedule.COLUMN_QtyToMove;
import static org.adempiere.ad.dao.impl.CompareQueryFilter.Operator.GREATER;
import static org.adempiere.ad.dao.impl.CompareQueryFilter.Operator.LESS_OR_EQUAL;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.util.TimeUtil.asTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.IModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Repository
public class ReceiptScheduleRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
	private final IModelCacheInvalidationService cacheInvalidationService = Services.get(IModelCacheInvalidationService.class);

	public List<ReceiptSchedule> getBy(@NonNull final ReceiptScheduleQuery query)
	{
		final IQueryBuilder<I_M_ReceiptSchedule> queryBuilder = queryBL.createQueryBuilder(I_M_ReceiptSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(COLUMNNAME_AD_Client_ID, ClientId.METASFRESH);

		if (query.getExportStatus() != null)
		{
			queryBuilder.addEqualsFilter(COLUMNNAME_ExportStatus, query.getExportStatus().getCode());
		}
		if (query.getCanBeExportedFrom() != null)
		{
			queryBuilder.addCompareFilter(COLUMN_CanBeExportedFrom, LESS_OR_EQUAL, asTimestamp(query.getCanBeExportedFrom()));
		}

		if (!query.isIncludeProcessed())
		{
			queryBuilder.addNotEqualsFilter(COLUMNNAME_Processed, true);
		}
		if (!query.includeWithQtyToDeliverZero)
		{
			queryBuilder.addCompareFilter(COLUMN_QtyToMove, GREATER, BigDecimal.ZERO);
		}
		if (query.getLimit().isLimited())
		{
			queryBuilder.setLimit(query.getLimit());
		}

		final List<I_M_ReceiptSchedule> records = queryBuilder
				.orderBy(COLUMNNAME_M_ReceiptSchedule_ID)
				.create()
				.list();

		final ImmutableList.Builder<ReceiptSchedule> result = ImmutableList.builder();
		for (final I_M_ReceiptSchedule record : records)
		{
			final ReceiptSchedule receiptSchedule = ofRecord(record);
			result.add(receiptSchedule);
		}
		return result.build();
	}

	private ReceiptSchedule ofRecord(@NonNull final I_M_ReceiptSchedule record)
	{
		final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());
		final ReceiptScheduleId receiptScheduleId = ReceiptScheduleId.ofRepoId(record.getM_ReceiptSchedule_ID());


		final ReceiptSchedule.ReceiptScheduleBuilder receiptScheduleBuilder = ReceiptSchedule.builder()
				.id(receiptScheduleId)
				.orgId(orgId)
				.vendorId(receiptScheduleBL.getBPartnerEffectiveId(record))
				.orderId(OrderId.ofRepoIdOrNull(record.getC_Order_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()))
				.quantityToDeliver(receiptScheduleBL.getQtyToMove(record).getStockQty())
				.exportStatus(APIExportStatus.ofCode(record.getExportStatus()));
		if (record.getDateOrdered() != null)
		{
			receiptScheduleBuilder.dateOrdered(record.getDateOrdered().toLocalDateTime());
		}
		return receiptScheduleBuilder.build();
	}

	public void exportStatusMassUpdate(
			@NonNull final Set<ReceiptScheduleId> receiptScheduleIds,
			@NonNull final APIExportStatus exportStatus)
	{
		if (receiptScheduleIds.isEmpty())
		{
			return;
		}
		final ICompositeQueryUpdater<I_M_ReceiptSchedule> updater = queryBL.createCompositeQueryUpdater(I_M_ReceiptSchedule.class)
				.addSetColumnValue(COLUMNNAME_ExportStatus, exportStatus.getCode());

		queryBL.createQueryBuilder(I_M_ReceiptSchedule.class)
				.addInArrayFilter(COLUMNNAME_M_ReceiptSchedule_ID, receiptScheduleIds)
				.create()
				.updateDirectly(updater);

		cacheInvalidationService.invalidate(
				CacheInvalidateMultiRequest.fromTableNameAndRepoIdAwares(I_M_ReceiptSchedule.Table_Name, receiptScheduleIds),
				ModelCacheInvalidationTiming.CHANGE);
	}

	public void saveAll(@NonNull final ImmutableCollection<ReceiptSchedule> receiptSchedules)
	{
		for (final ReceiptSchedule receiptSchedule : receiptSchedules)
		{
			save(receiptSchedule);
		}
	}

	private void save(@NonNull final ReceiptSchedule receiptSchedule)
	{
		final I_M_ReceiptSchedule record = load(receiptSchedule.getId(), I_M_ReceiptSchedule.class);
		record.setExportStatus(receiptSchedule.getExportStatus().getCode());
		saveRecord(record);
	}

	public ImmutableMap<ReceiptScheduleId, ReceiptSchedule> getByIds(@NonNull final ImmutableSet<ReceiptScheduleId> receiptScheduleIds)
	{
		final List<I_M_ReceiptSchedule> records = loadByRepoIdAwares(receiptScheduleIds, I_M_ReceiptSchedule.class);
		final ImmutableMap.Builder<ReceiptScheduleId, ReceiptSchedule> result = ImmutableMap.builder();
		for (final I_M_ReceiptSchedule record : records)
		{
			result.put(ReceiptScheduleId.ofRepoId(record.getM_ReceiptSchedule_ID()), ofRecord(record));
		}
		return result.build();
	}

	@Value
	@Builder
	public static class ReceiptScheduleQuery
	{
		@NonNull
		@Builder.Default
		QueryLimit limit = QueryLimit.NO_LIMIT;

		Instant canBeExportedFrom;

		APIExportStatus exportStatus;

		@Builder.Default
		boolean includeWithQtyToDeliverZero = false;

		@Builder.Default
		boolean includeProcessed = false;
	}
}
