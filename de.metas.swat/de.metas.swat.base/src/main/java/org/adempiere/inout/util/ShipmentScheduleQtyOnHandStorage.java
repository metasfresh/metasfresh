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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.mm.attributes.api.StorageAttributesKeys;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Util.ArrayKey;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.dispo.commons.repository.AvailableStockResult;
import de.metas.material.dispo.commons.repository.MaterialMultiQuery;
import de.metas.material.dispo.commons.repository.MaterialMultiQuery.AggregationLevel;
import de.metas.material.dispo.commons.repository.MaterialQuery;
import de.metas.material.dispo.commons.repository.MaterialQuery.MaterialQueryBuilder;
import de.metas.material.dispo.commons.repository.StockRepository;
import de.metas.material.event.commons.StorageAttributesKey;
import de.metas.storage.IStorageRecord;
import lombok.NonNull;

/**
 * A complex class to load and cache {@link IStorageRecord}s which are relevant to {@link I_M_ShipmentSchedule}s and their {@link I_C_OrderLine}s.
 *
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

	private final List<ShipmentScheduleStorageRecord> storageRecords;
	private final Map<ArrayKey, MaterialQuery> cachedMaterialQueries = new HashMap<>();

	private ShipmentScheduleQtyOnHandStorage(final List<I_M_ShipmentSchedule> shipmentSchedules)
	{
		final StockRepository stockRepository = Adempiere.getBean(StockRepository.class);
		storageRecords = createStockResultsFromShipmentSchedules(shipmentSchedules, stockRepository);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("storageRecords", storageRecords)
				.toString();
	}

	private final List<ShipmentScheduleStorageRecord> createStockResultsFromShipmentSchedules(final List<I_M_ShipmentSchedule> shipmentSchedules, final StockRepository stockRepository)
	{
		if (shipmentSchedules.isEmpty())
		{
			return ImmutableList.of();
		}
		final MaterialMultiQuery multiQuery = createMaterialMultiQueryOrNull(shipmentSchedules);
		if (multiQuery == null)
		{
			return ImmutableList.of();
		}

		final List<AvailableStockResult> availableStockResults = stockRepository.retrieveAvailableStock(multiQuery);
		return createStockResults(availableStockResults);
	}

	private MaterialMultiQuery createMaterialMultiQueryOrNull(final List<I_M_ShipmentSchedule> shipmentSchedules)
	{
		final Set<MaterialQuery> materialQueries = shipmentSchedules.stream()
				.map(this::createMaterialQuery)
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());
		if (materialQueries.isEmpty())
		{
			return null;
		}

		return MaterialMultiQuery.builder()
				.queries(materialQueries)
				.aggregationLevel(AggregationLevel.NONE)
				.build();
	}

	public MaterialQuery createMaterialQuery(@NonNull final I_M_ShipmentSchedule sched)
	{
		// In case the DeliveryRule is Force, there is no point to load the storage, because it's not needed.
		// FIXME: make sure this works performance wise, then remove the commented code
		// final I_M_ShipmentSchedule shipmentSchedule = olAndSched.getSched();
		// final String deliveryRule = shipmentScheduleEffectiveValuesBL.getDeliveryRule(shipmentSchedule);
		// if (!X_M_ShipmentSchedule.DELIVERYRULE_Force.equals(deliveryRule))
		// return null;

		final TableRecordReference scheduleReference = TableRecordReference.ofReferenced(sched);

		//
		// Get the storage query from cache if available
		final ArrayKey materialQueryCacheKey = ArrayKey.of(
				scheduleReference.getTableName(),
				scheduleReference.getRecord_ID(),
				I_M_ShipmentSchedule.Table_Name,
				sched.getM_ShipmentSchedule_ID());

		final MaterialQuery materialQuery = cachedMaterialQueries.get(materialQueryCacheKey);
		if (materialQuery != null)
		{
			return materialQuery;
		}

		// Create storage query
		final int productId = sched.getM_Product_ID();
		final int warehouseId = shipmentScheduleEffectiveBL.getWarehouseId(sched);
		final int bpartnerId = shipmentScheduleEffectiveBL.getC_BPartner_ID(sched);

		final MaterialQueryBuilder materialQueryBuilder = MaterialQuery.builder()
				.warehouseId(warehouseId)
				.productId(productId)
				.bpartnerId(bpartnerId)
				.date(shipmentScheduleEffectiveBL.getPreparationDate(sched)); // TODO: check with Mark if we shall use DeliveryDate.

		// Add query attributes
		final int asiId = sched.getM_AttributeSetInstance_ID();
		if (asiId > 0)
		{
			materialQueryBuilder.storageAttributesKey(StorageAttributesKeys.createAttributesKeyFromASI(asiId));
		}

		// Cache the storage query and return it
		cachedMaterialQueries.put(materialQueryCacheKey, materialQuery);
		return materialQuery;
	}

	private static final List<ShipmentScheduleStorageRecord> createStockResults(final List<AvailableStockResult> stockResults)
	{
		if (Check.isEmpty(stockResults))
		{
			return ImmutableList.of();
		}

		return stockResults.stream()
				.flatMap(stockResult -> stockResult.getResultGroups().stream())
				.map(ShipmentScheduleStorageRecord::of)
				.collect(ImmutableList.toImmutableList());
	}

	private Stream<ShipmentScheduleStorageRecord> streamStorageRecordsMatching(final MaterialQuery materialQuery)
	{
		return storageRecords
				.stream()
				.filter(storageRecord -> matching(materialQuery, storageRecord));
	}

	private static boolean matching(final MaterialQuery query, final ShipmentScheduleStorageRecord storageRecord)
	{
		final List<Integer> productIds = query.getProductIds();
		if (!productIds.isEmpty() && !productIds.contains(storageRecord.getProductId()))
		{
			return false;
		}

		final Set<Integer> warehouseIds = query.getWarehouseIds();
		if (!warehouseIds.isEmpty() && !warehouseIds.contains(storageRecord.getWarehouseId()))
		{
			return false;
		}

		final int bpartnerId = query.getBpartnerId();
		if (bpartnerId > 0 && bpartnerId != storageRecord.getBpartnerId())
		{
			return false;
		}

		final List<StorageAttributesKey> storageAttributeKeys = query.getStorageAttributesKeys();
		if (!storageAttributeKeys.isEmpty() && !storageAttributeKeys.contains(storageRecord.getStorageAttributesKey()))
		{
			return false;
		}

		return true;
	}

	private boolean hasStorageRecords()
	{
		return !storageRecords.isEmpty();
	}

	public List<ShipmentScheduleStorageRecord> getStorageRecordsMatching(@NonNull final I_M_ShipmentSchedule sched)
	{
		if (!hasStorageRecords())
		{
			return Collections.emptyList();
		}

		final MaterialQuery materialQuery = createMaterialQuery(sched);
		return streamStorageRecordsMatching(materialQuery)
				.collect(ImmutableList.toImmutableList());
	}

	// TODO: remove it
	public BigDecimal getQtyUnconfirmedShipmentsPerShipmentSchedule(final I_M_ShipmentSchedule sched)
	{
		return BigDecimal.ZERO;
	}
}
