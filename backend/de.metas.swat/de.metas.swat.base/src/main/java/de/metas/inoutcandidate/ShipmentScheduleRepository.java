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
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.ModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleAttributeSegment;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Recompute;
import de.metas.order.OrderAndLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Locator;
import org.compiere.model.X_C_Order;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_AD_Client_ID;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_ExportStatus;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_Processed;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMN_CanBeExportedFrom;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMN_QtyToDeliver;
import static de.metas.inoutcandidate.model.X_M_ShipmentSchedule.EXPORTSTATUS_DONT_EXPORT;
import static de.metas.inoutcandidate.model.X_M_ShipmentSchedule.EXPORTSTATUS_EXPORTED;
import static de.metas.inoutcandidate.model.X_M_ShipmentSchedule.EXPORTSTATUS_EXPORTED_AND_FORWARDED;
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
	private final ModelCacheInvalidationService cacheInvalidationService;
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	public ShipmentScheduleRepository(
			@NonNull final ModelCacheInvalidationService cacheInvalidationService)
	{
		this.cacheInvalidationService = cacheInvalidationService;
	}

	public List<ShipmentSchedule> getBy(@NonNull final ShipmentScheduleQuery query)
	{
		final IQueryBuilder<I_M_ShipmentSchedule> queryBuilder = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(COLUMNNAME_AD_Client_ID, ClientId.METASFRESH);

		if (query.getExportStatus() != null)
		{
			queryBuilder.addEqualsFilter(COLUMNNAME_ExportStatus, query.getExportStatus().getCode());
		}

		final Instant canBeExportedFrom = query.getCanBeExportedFrom();

		if (canBeExportedFrom != null)
		{
			if (query.onlyIfAllFromOrderExportable)
			{
				final IQuery<I_C_Order> orderIdsWithSchedulesForTheFuture = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
						.addCompareFilter(I_M_ShipmentSchedule.COLUMNNAME_CanBeExportedFrom, GREATER, canBeExportedFrom)
						.addOnlyActiveRecordsFilter() // if a shipmentSchedule is inactive or was already exported, or shall *not* be exported, then don't let it stop the others
						.addNotInArrayFilter(COLUMNNAME_ExportStatus, ImmutableList.of(EXPORTSTATUS_DONT_EXPORT, EXPORTSTATUS_EXPORTED, EXPORTSTATUS_EXPORTED_AND_FORWARDED))
						.andCollect(I_C_Order.COLUMNNAME_C_Order_ID, I_C_Order.class)
						.create();
				queryBuilder.addNotInSubQueryFilter(I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID, I_C_Order.COLUMNNAME_C_Order_ID, orderIdsWithSchedulesForTheFuture);
			}
			else
			{
				queryBuilder.addCompareFilter(COLUMN_CanBeExportedFrom, LESS_OR_EQUAL, asTimestamp(canBeExportedFrom));
			}
		}

		if (!query.isIncludeInvalid())
		{
			if (query.onlyIfAllFromOrderExportable)
			{
				final IQuery<I_C_Order> orderIdsWithInvalidSchedules = queryBL.createQueryBuilder(I_M_ShipmentSchedule_Recompute.class)
						.andCollect(I_M_ShipmentSchedule_Recompute.COLUMN_M_ShipmentSchedule_ID)
						.addOnlyActiveRecordsFilter() // if a shipmentSchedule is inactive or was already exported, or shall *not* be exported, then don't let it stop the others
						.addNotInArrayFilter(COLUMNNAME_ExportStatus, ImmutableList.of(EXPORTSTATUS_DONT_EXPORT, EXPORTSTATUS_EXPORTED, EXPORTSTATUS_EXPORTED_AND_FORWARDED))
						.andCollect(I_M_ShipmentSchedule.COLUMN_C_Order_ID)
						.create();
				queryBuilder.addNotInSubQueryFilter(I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID, I_C_Order.COLUMNNAME_C_Order_ID, orderIdsWithInvalidSchedules);
			}
			else
			{
				final IQuery<I_M_ShipmentSchedule_Recompute> recomputeQuery = queryBL
						.createQueryBuilder(I_M_ShipmentSchedule_Recompute.class)
						.create();
				queryBuilder.addNotInSubQueryFilter(COLUMNNAME_M_ShipmentSchedule_ID, I_M_ShipmentSchedule_Recompute.COLUMNNAME_M_ShipmentSchedule_ID, recomputeQuery);
			}
		}
		if (!query.includeWithQtyToDeliverZero)
		{
			if (query.onlyIfAllFromOrderExportable)
			{
				final IQuery<I_C_Order> orderIdsWithZeroQtyToDeliver = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
						.addCompareFilter(COLUMN_QtyToDeliver, LESS_OR_EQUAL, BigDecimal.ZERO)
						.addOnlyActiveRecordsFilter() // if a shipmentSchedule is inactive or was already exported, or shall *not* be exported, then don't let it stop the others
						.addNotInArrayFilter(COLUMNNAME_ExportStatus, ImmutableList.of(EXPORTSTATUS_DONT_EXPORT, EXPORTSTATUS_EXPORTED, EXPORTSTATUS_EXPORTED_AND_FORWARDED))
						.andCollect(I_C_Order.COLUMNNAME_C_Order_ID, I_C_Order.class)
						.create();
				queryBuilder.addNotInSubQueryFilter(I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID, I_C_Order.COLUMNNAME_C_Order_ID, orderIdsWithZeroQtyToDeliver);
			}
			else
			{
				queryBuilder.addCompareFilter(COLUMN_QtyToDeliver, GREATER, BigDecimal.ZERO);
			}
		}

		if (!query.isIncludeProcessed())
		{
			queryBuilder.addNotEqualsFilter(COLUMNNAME_Processed, true);
		}

		if (query.fromCompleteOrderOrNullOrder)
		{
			final IQuery<I_C_Order> completedOrClosedOdrersQuery = queryBL.createQueryBuilder(I_C_Order.class)
					.addInArrayFilter(I_C_Order.COLUMN_DocStatus, X_C_Order.DOCSTATUS_Closed, X_C_Order.DOCSTATUS_Completed)
					.create();

			queryBL.createCompositeQueryFilter(I_M_ShipmentSchedule.class)
					.setJoinOr()
					.addEqualsFilter(I_M_ShipmentSchedule.COLUMN_C_Order_ID, null)
					.addInSubQueryFilter(I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID, I_C_Order.COLUMNNAME_C_Order_ID, completedOrClosedOdrersQuery);
		}

		if (query.getLimit().isLimited())
		{
			queryBuilder.setLimit(query.getLimit());
		}

		if (query.orderByOrderId)
		{
			queryBuilder.orderBy()
					.addColumn(I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID, Direction.Ascending, Nulls.First)
					.endOrderBy();
		}
		else
		{
			queryBuilder
					.orderBy(COLUMNNAME_M_ShipmentSchedule_ID);
		}

		final List<I_M_ShipmentSchedule> records = queryBuilder
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

		final OrderAndLineId orderAndLineId = record.getC_Order_ID() > 0 && record.getC_OrderLine_ID() > 0
				? OrderAndLineId.ofRepoIds(record.getC_Order_ID(), record.getC_OrderLine_ID())
				: null;

		final ShipmentSchedule.ShipmentScheduleBuilder shipmentScheduleBuilder = ShipmentSchedule.builder()
				.id(shipmentScheduleId)
				.orgId(orgId)

				.shipBPartnerId(shipmentScheduleEffectiveBL.getBPartnerId(record))
				.shipLocationId(shipmentScheduleEffectiveBL.getBPartnerLocationId(record))
				.shipContactId(shipmentScheduleEffectiveBL.getBPartnerContactId(record))

				.billBPartnerId(BPartnerId.ofRepoIdOrNull(record.getBill_BPartner_ID()))
				.billLocationId(BPartnerLocationId.ofRepoIdOrNull(record.getBill_BPartner_ID(), record.getBill_Location_ID()))
				.billContactId(BPartnerContactId.ofRepoIdOrNull(record.getBill_BPartner_ID(), record.getBill_User_ID()))

				.orderAndLineId(orderAndLineId)
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()))
				.shipperId(ShipperId.ofRepoIdOrNull(record.getM_Shipper_ID()))
				.quantityToDeliver(shipmentScheduleBL.getQtyToDeliver(record))
				.orderedQuantity(shipmentScheduleBL.getQtyOrdered(record))
				.numberOfItemsForSameShipment(record.getNrOfOLCandsWithSamePOReference())
				.deliveredQuantity(shipmentScheduleBL.getQtyDelivered(record))
				.exportStatus(APIExportStatus.ofCode(record.getExportStatus()))
				.isProcessed(record.isProcessed())
				.isClosed(record.isClosed())
				.isDeliveryStop(record.isDeliveryStop());

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
				ModelCacheInvalidationTiming.AFTER_CHANGE);
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
		record.setExportStatus(shipmentSchedule.getExportStatus().getCode()); // right now this is the only mutable property
		saveRecord(record);
	}

	public ShipmentSchedule getById(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_ShipmentSchedule record = load(shipmentScheduleId, I_M_ShipmentSchedule.class);
		return ofRecord(record);
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

	public Stream<ShipmentSchedule> streamFromSegment(@NonNull final IShipmentScheduleSegment shipmentScheduleSegment)
	{
		final IQueryBuilder<I_M_ShipmentSchedule> shipmentScheduleIQueryBuilder = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addOnlyActiveRecordsFilter();

		if (!Check.isEmpty(shipmentScheduleSegment.getBpartnerIds()) && !shipmentScheduleSegment.isAnyBPartner())
		{
			final ICompositeQueryFilter<I_M_ShipmentSchedule> bPartnerFilter = queryBL.createCompositeQueryFilter(I_M_ShipmentSchedule.class)
					.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Override_ID, null)
					.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_ID, shipmentScheduleSegment.getBpartnerIds());

			final ICompositeQueryFilter<I_M_ShipmentSchedule> bPartnerOverrideFilter = queryBL.createCompositeQueryFilter(I_M_ShipmentSchedule.class)
					.addNotNull(I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Override_ID)
					.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Override_ID, shipmentScheduleSegment.getBpartnerIds());

			final ICompositeQueryFilter<I_M_ShipmentSchedule> orJoinedBPartnerFilters = queryBL.createCompositeQueryFilter(I_M_ShipmentSchedule.class)
					.setJoinOr()
					.addFilter(bPartnerFilter)
					.addFilter(bPartnerOverrideFilter);

			shipmentScheduleIQueryBuilder.filter(orJoinedBPartnerFilters);
		}

		if (!Check.isEmpty(shipmentScheduleSegment.getBillBPartnerIds()) && !shipmentScheduleSegment.isAnyBillBPartner())
		{
			shipmentScheduleIQueryBuilder.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_Bill_BPartner_ID, shipmentScheduleSegment.getBillBPartnerIds());
		}

		if (!Check.isEmpty(shipmentScheduleSegment.getLocatorIds()) && !shipmentScheduleSegment.isAnyLocator())
		{
			final Set<WarehouseId> warehouseIds = queryBL.createQueryBuilder(I_M_Locator.class)
					.addInArrayFilter(I_M_Locator.COLUMNNAME_M_Locator_ID, shipmentScheduleSegment.getLocatorIds())
					.create()
					.stream()
					.map(I_M_Locator::getM_Warehouse_ID)
					.map(WarehouseId::ofRepoId)
					.collect(ImmutableSet.toImmutableSet());

			final ICompositeQueryFilter<I_M_ShipmentSchedule> warehouseFilter = queryBL.createCompositeQueryFilter(I_M_ShipmentSchedule.class)
					.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID, null)
					.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_ID, warehouseIds);

			final ICompositeQueryFilter<I_M_ShipmentSchedule> warehouseOverrideFilter = queryBL.createCompositeQueryFilter(I_M_ShipmentSchedule.class)
					.addNotNull(I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID)
					.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID, warehouseIds);

			final ICompositeQueryFilter<I_M_ShipmentSchedule> orJoinedWarehouseFilters = queryBL.createCompositeQueryFilter(I_M_ShipmentSchedule.class)
					.setJoinOr()
					.addFilter(warehouseFilter)
					.addFilter(warehouseOverrideFilter);

			shipmentScheduleIQueryBuilder.filter(orJoinedWarehouseFilters);
		}

		if (!Check.isEmpty(shipmentScheduleSegment.getProductIds()) && !shipmentScheduleSegment.isAnyProduct())
		{
			shipmentScheduleIQueryBuilder.addInArrayFilter(I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID, shipmentScheduleSegment.getProductIds());
		}

		Stream<ShipmentSchedule> shipmentScheduleStream = shipmentScheduleIQueryBuilder.create()
				.stream()
				.map(this::ofRecord);

		if (!Check.isEmpty(shipmentScheduleSegment.getAttributes()))
		{
			final ImmutableSet<AttributeSetInstanceId> targetAsiIds = shipmentScheduleSegment.getAttributes()
					.stream()
					.map(ShipmentScheduleAttributeSegment::getAttributeSetInstanceId)
					.filter(Objects::nonNull)
					.filter(asiId -> !asiId.equals(AttributeSetInstanceId.NONE))
					.collect(ImmutableSet.toImmutableSet());

			shipmentScheduleStream = shipmentScheduleStream.filter(shipmentSchedule -> shipmentSchedule.hasAttributes(targetAsiIds, attributeDAO));
		}

		return shipmentScheduleStream;
	}

	@Value
	@Builder
	public static class ShipmentScheduleQuery
	{
		@NonNull
		@Builder.Default
		QueryLimit limit = QueryLimit.NO_LIMIT;

		Instant canBeExportedFrom;

		APIExportStatus exportStatus;

		@Builder.Default
		boolean includeWithQtyToDeliverZero = false;

		@Builder.Default
		boolean includeInvalid = false;

		@Builder.Default
		boolean includeProcessed = false;

		@Builder.Default
		boolean fromCompleteOrderOrNullOrder = false;

		@Builder.Default
		boolean orderByOrderId = false;

		/**
		 * Only export a shipment schedule if its order does not have any schedule which is not yet ready to be exported.
		 */
		@Builder.Default
		boolean onlyIfAllFromOrderExportable = false;

	}
}
