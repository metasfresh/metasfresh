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

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.IModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Recompute;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;

import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_AD_Client_ID;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_ExportStatus;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_Processed;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMN_CanBeExportedFrom;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMN_QtyToDeliver;
import static org.adempiere.ad.dao.impl.CompareQueryFilter.Operator.GREATER;
import static org.adempiere.ad.dao.impl.CompareQueryFilter.Operator.LESS_OR_EQUAL;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.util.TimeUtil.asTimestamp;

@Repository
public class ShipmentScheduleRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IModelCacheInvalidationService cacheInvalidationService = Services.get(IModelCacheInvalidationService.class);
	private IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

	public List<ShipmentSchedule> getBy(@NonNull final ShipmentScheduleQuery query)
	{
		final IQueryBuilder<I_M_ShipmentSchedule> queryBuilder = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
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
		if (!query.isIncludeInvalid())
		{
			final IQuery<I_M_ShipmentSchedule_Recompute> recomputeQuery = queryBL
					.createQueryBuilder(I_M_ShipmentSchedule_Recompute.class)
					.create();
			queryBuilder.addNotInSubQueryFilter(
					COLUMNNAME_M_ShipmentSchedule_ID,
					I_M_ShipmentSchedule_Recompute.COLUMNNAME_M_ShipmentSchedule_ID,
					recomputeQuery);
		}
		if (!query.isIncludeProcessed())
		{
			queryBuilder.addNotEqualsFilter(COLUMNNAME_Processed, true);
		}
		if (!query.includeWithQtyToDeliverZero)
		{
			queryBuilder.addCompareFilter(COLUMN_QtyToDeliver, GREATER, BigDecimal.ZERO);
		}
		if (query.getLimit() > 0)
		{
			queryBuilder.setLimit(query.getLimit());
		}

		final List<I_M_ShipmentSchedule> records = queryBuilder
				.orderBy(COLUMNNAME_M_ShipmentSchedule_ID)
				.create()
				.list();

		final ImmutableList.Builder<ShipmentSchedule> result = ImmutableList.builder();
		for (final I_M_ShipmentSchedule record : records)
		{
			final ShipmentSchedule shipmentSchedule = ofRecord(record);
			result.add(shipmentSchedule);
		}
		return result.build();
	}

	private ShipmentSchedule ofRecord(final I_M_ShipmentSchedule record)
	{
		final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());
		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID());

		final ShipmentSchedule.ShipmentScheduleBuilder shipmentScheduleBuilder = ShipmentSchedule.builder()
				.id(shipmentScheduleId)
				.orgId(orgId)
				.customerId(shipmentScheduleEffectiveBL.getBPartnerId(record))
				.locationId(shipmentScheduleEffectiveBL.getBPartnerLocationId(record))
				.contactId(shipmentScheduleEffectiveBL.getBPartnerContactId(record))
				.orderId(OrderId.ofRepoIdOrNull(record.getC_Order_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()))
				.quantityToDeliver(shipmentScheduleBL.getQtyToDeliver(record))
				.exportStatus(APIExportStatus.ofCode(record.getExportStatus()));
		if (record.getDateOrdered() != null)
		{
			shipmentScheduleBuilder.dateOrdered(record.getDateOrdered().toLocalDateTime());
		}
		return shipmentScheduleBuilder.build();
	}

	public void exportStatusMassUpdate(
			@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds,
			@NonNull final APIExportStatus exportStatus)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return;
		}
		final ICompositeQueryUpdater<I_M_ShipmentSchedule> updater = queryBL.createCompositeQueryUpdater(I_M_ShipmentSchedule.class)
				.addSetColumnValue(COLUMNNAME_ExportStatus, exportStatus.getCode());

		queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addInArrayFilter(COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.create()
				.updateDirectly(updater);

		cacheInvalidationService.invalidate(
				CacheInvalidateMultiRequest.fromTableNameAndRepoIdAwares(I_M_ShipmentSchedule.Table_Name, shipmentScheduleIds),
				ModelCacheInvalidationTiming.CHANGE);
	}

	public void saveAll(@NonNull final ImmutableCollection<ShipmentSchedule> shipmentSchedules)
	{
		for (final ShipmentSchedule shipmentSchedule : shipmentSchedules)
		{
			save(shipmentSchedule);
		}
	}

	private void save(@NonNull final ShipmentSchedule shipmentSchedule)
	{
		final I_M_ShipmentSchedule record = load(shipmentSchedule.getId(), I_M_ShipmentSchedule.class);
		record.setExportStatus(shipmentSchedule.getExportStatus().getCode());
		saveRecord(record);
	}

	public ImmutableMap<ShipmentScheduleId, ShipmentSchedule> getByIds(@NonNull final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds)
	{
		final List<I_M_ShipmentSchedule> records = loadByRepoIdAwares(shipmentScheduleIds, I_M_ShipmentSchedule.class);
		final ImmutableMap.Builder<ShipmentScheduleId, ShipmentSchedule> result = ImmutableMap.builder();
		for (final I_M_ShipmentSchedule record : records)
		{
			result.put(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()), ofRecord(record));
		}
		return result.build();
	}

	@Value
	@Builder
	public static class ShipmentScheduleQuery
	{
		int limit;

		Instant canBeExportedFrom;

		APIExportStatus exportStatus;

		@Builder.Default
		boolean includeWithQtyToDeliverZero = false;

		@Builder.Default
		boolean includeInvalid = false;

		@Builder.Default
		boolean includeProcessed = false;
	}
}
