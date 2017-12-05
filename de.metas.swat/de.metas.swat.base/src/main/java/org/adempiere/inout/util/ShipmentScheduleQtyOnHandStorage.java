package org.adempiere.inout.util;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.mm.attributes.api.StorageAttributesKeys;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.model.WarehousePickingGroup;
import org.compiere.Adempiere;
import org.compiere.util.Util.ArrayKey;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.dispo.commons.repository.StockResult;
import de.metas.material.dispo.commons.repository.StockMultiQuery;
import de.metas.material.dispo.commons.repository.StockQuery;
import de.metas.material.dispo.commons.repository.StockQuery.StockQueryBuilder;
import de.metas.material.dispo.commons.repository.StockRepository;
import de.metas.material.event.commons.StorageAttributesKey;
import lombok.NonNull;

/**
 * Loads stock details which are relevant to given {@link I_M_ShipmentSchedule}s.
 * Allows to change (in memory!) the qtyOnHand.
 */
public class ShipmentScheduleQtyOnHandStorage
{
	public static final ShipmentScheduleQtyOnHandStorage ofShipmentSchedules(final List<I_M_ShipmentSchedule> shipmentSchedules)
	{
		return new ShipmentScheduleQtyOnHandStorage(shipmentSchedules);
	}

	public static final ShipmentScheduleQtyOnHandStorage ofShipmentSchedule(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return new ShipmentScheduleQtyOnHandStorage(ImmutableList.of(shipmentSchedule));
	}

	public static final ShipmentScheduleQtyOnHandStorage ofOlAndScheds(final List<OlAndSched> lines)
	{
		final List<I_M_ShipmentSchedule> shipmentSchedules = lines.stream().map(OlAndSched::getSched).collect(ImmutableList.toImmutableList());
		return new ShipmentScheduleQtyOnHandStorage(shipmentSchedules);
	}

	// services
	private final transient IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final transient IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	private final List<ShipmentScheduleAvailableStockDetail> stockDetails;
	private final Map<ArrayKey, StockQuery> cachedMaterialQueries = new HashMap<>();

	private ShipmentScheduleQtyOnHandStorage(final List<I_M_ShipmentSchedule> shipmentSchedules)
	{
		final StockRepository stockRepository = Adempiere.getBean(StockRepository.class);
		stockDetails = createStockDetailsFromShipmentSchedules(shipmentSchedules, stockRepository);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("storageRecords", stockDetails)
				.toString();
	}

	private final List<ShipmentScheduleAvailableStockDetail> createStockDetailsFromShipmentSchedules(final List<I_M_ShipmentSchedule> shipmentSchedules, final StockRepository stockRepository)
	{
		if (shipmentSchedules.isEmpty())
		{
			return ImmutableList.of();
		}
		final StockMultiQuery multiQuery = createMaterialMultiQueryOrNull(shipmentSchedules);
		if (multiQuery == null)
		{
			return ImmutableList.of();
		}

		final StockResult stockResult = stockRepository.retrieveAvailableStock(multiQuery);
		return createStockDetails(stockResult);
	}

	private StockMultiQuery createMaterialMultiQueryOrNull(final List<I_M_ShipmentSchedule> shipmentSchedules)
	{
		final Set<StockQuery> materialQueries = shipmentSchedules.stream()
				.map(this::getMaterialQuery)
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());
		if (materialQueries.isEmpty())
		{
			return null;
		}

		return StockMultiQuery.builder()
				.queries(materialQueries)
				.addToPredefinedBuckets(false)
				.build();
	}

	public StockQuery getMaterialQuery(@NonNull final I_M_ShipmentSchedule sched)
	{
		// In case the DeliveryRule is Force, there is no point to load the storage, because it's not needed.
		// FIXME: make sure this works performance wise, then remove the commented code
		// final I_M_ShipmentSchedule shipmentSchedule = olAndSched.getSched();
		// final String deliveryRule = shipmentScheduleEffectiveValuesBL.getDeliveryRule(shipmentSchedule);
		// if (!X_M_ShipmentSchedule.DELIVERYRULE_Force.equals(deliveryRule))
		// return null;

		//
		// Get the storage query from cache if available
		final TableRecordReference scheduleReference = TableRecordReference.ofReferenced(sched);
		final ArrayKey materialQueryCacheKey = ArrayKey.of(
				scheduleReference.getTableName(),
				scheduleReference.getRecord_ID(),
				I_M_ShipmentSchedule.Table_Name,
				sched.getM_ShipmentSchedule_ID());

		return cachedMaterialQueries.computeIfAbsent(materialQueryCacheKey, k -> createMaterialQuery(sched));
	}

	private StockQuery createMaterialQuery(@NonNull final I_M_ShipmentSchedule sched)
	{
		final int shipmentScheduleWarehouseId = shipmentScheduleEffectiveBL.getWarehouseId(sched);
		final WarehousePickingGroup warehousePickingGroup = warehouseDAO.getWarehousePickingGroupContainingWarehouseId(shipmentScheduleWarehouseId);
		final Set<Integer> warehouseIds = warehousePickingGroup != null ? warehousePickingGroup.getWarehouseIds() : ImmutableSet.of(shipmentScheduleWarehouseId);

		final int productId = sched.getM_Product_ID();
		final int bpartnerId = shipmentScheduleEffectiveBL.getC_BPartner_ID(sched);
		final Date date = shipmentScheduleEffectiveBL.getPreparationDate(sched);
		final StockQueryBuilder stockQueryBuilder = StockQuery.builder()
				.warehouseIds(warehouseIds)
				.productId(productId)
				.bpartnerId(bpartnerId)
				.date(date);

		// Add query attributes
		final int asiId = sched.getM_AttributeSetInstance_ID();
		if (asiId > 0)
		{
			stockQueryBuilder.storageAttributesKey(StorageAttributesKeys.createAttributesKeyFromASI(asiId));
		}

		// Cache the storage query and return it
		return stockQueryBuilder.build();
	}

	private static final List<ShipmentScheduleAvailableStockDetail> createStockDetails(final StockResult stockResult)
	{
		return stockResult
				.getResultGroups()
				.stream()
				.map(ShipmentScheduleQtyOnHandStorage::createStockDetail)
				.collect(ImmutableList.toImmutableList());
	}

	private static ShipmentScheduleAvailableStockDetail createStockDetail(final StockResult.ResultGroup result)
	{
		return ShipmentScheduleAvailableStockDetail.builder()
				.productId(result.getProductId())
				.warehouseId(result.getWarehouseId())
				.bpartnerId(result.getBpartnerId())
				.storageAttributesKey(result.getStorageAttributesKey())
				.qtyOnHand(result.getQty())
				.build();
	}

	private Stream<ShipmentScheduleAvailableStockDetail> streamStockDetailsMatching(final StockQuery materialQuery)
	{
		return stockDetails
				.stream()
				.filter(stockDetail -> matching(materialQuery, stockDetail));
	}

	private static boolean matching(final StockQuery query, final ShipmentScheduleAvailableStockDetail stockDetail)
	{
		//
		// Product
		final List<Integer> queryProductIds = query.getProductIds();
		if (!queryProductIds.isEmpty() && !queryProductIds.contains(stockDetail.getProductId()))
		{
			return false;
		}

		//
		// Warehouse
		final Set<Integer> queryWarehouseIds = query.getWarehouseIds();
		if (!queryWarehouseIds.isEmpty() && !queryWarehouseIds.contains(stockDetail.getWarehouseId()))
		{
			return false;
		}

		//
		// Partner
		final int stockBPartnerId = stockDetail.getBpartnerId();
		if (stockBPartnerId == StockQuery.BPARTNER_ID_NONE)
		{
			// always match the available stock which is not allocated to a particular BPartner
		}
		else if (!query.isBPartnerMatching(stockBPartnerId))
		{
			return false;
		}

		//
		// Storage Attributes Key
		final List<StorageAttributesKey> queryStorageAttributeKeys = query.getStorageAttributesKeys();
		if (!queryStorageAttributeKeys.isEmpty() && !queryStorageAttributeKeys.contains(stockDetail.getStorageAttributesKey()))
		{
			return false;
		}

		return true;
	}

	private boolean hasStockDetails()
	{
		return !stockDetails.isEmpty();
	}

	public List<ShipmentScheduleAvailableStockDetail> getStockDetailsMatching(@NonNull final I_M_ShipmentSchedule sched)
	{
		if (!hasStockDetails())
		{
			return Collections.emptyList();
		}

		final StockQuery materialQuery = getMaterialQuery(sched);
		return streamStockDetailsMatching(materialQuery)
				.collect(ImmutableList.toImmutableList());
	}

	// TODO: remove it
	public BigDecimal getQtyUnconfirmedShipmentsPerShipmentSchedule(final I_M_ShipmentSchedule sched)
	{
		return BigDecimal.ZERO;
	}
}
