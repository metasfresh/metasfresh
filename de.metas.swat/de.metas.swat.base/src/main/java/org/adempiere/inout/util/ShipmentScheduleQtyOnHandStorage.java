package org.adempiere.inout.util;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.model.WarehousePickingGroup;
import org.compiere.Adempiere;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util.ArrayKey;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.dispo.commons.repository.AvailableToPromiseMultiQuery;
import de.metas.material.dispo.commons.repository.AvailableToPromiseQuery;
import de.metas.material.dispo.commons.repository.AvailableToPromiseQuery.AvailableToPromiseQueryBuilder;
import de.metas.material.dispo.commons.repository.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.AvailableToPromiseResult;
import de.metas.material.event.commons.AttributesKey;
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
	private final Map<ArrayKey, AvailableToPromiseQuery> cachedMaterialQueries = new HashMap<>();

	private ShipmentScheduleQtyOnHandStorage(final List<I_M_ShipmentSchedule> shipmentSchedules)
	{
		final AvailableToPromiseRepository stockRepository = Adempiere.getBean(AvailableToPromiseRepository.class);
		stockDetails = createStockDetailsFromShipmentSchedules(shipmentSchedules, stockRepository);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("storageRecords", stockDetails)
				.toString();
	}

	private final List<ShipmentScheduleAvailableStockDetail> createStockDetailsFromShipmentSchedules(final List<I_M_ShipmentSchedule> shipmentSchedules, final AvailableToPromiseRepository stockRepository)
	{
		if (shipmentSchedules.isEmpty())
		{
			return ImmutableList.of();
		}
		final AvailableToPromiseMultiQuery multiQuery = createMaterialMultiQueryOrNull(shipmentSchedules);
		if (multiQuery == null)
		{
			return ImmutableList.of();
		}

		final AvailableToPromiseResult stockResult = stockRepository.retrieveAvailableStock(multiQuery);
		return createStockDetails(stockResult);
	}

	private AvailableToPromiseMultiQuery createMaterialMultiQueryOrNull(final List<I_M_ShipmentSchedule> shipmentSchedules)
	{
		final Set<AvailableToPromiseQuery> materialQueries = shipmentSchedules.stream()
				.map(this::getMaterialQuery)
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());
		if (materialQueries.isEmpty())
		{
			return null;
		}

		return AvailableToPromiseMultiQuery.builder()
				.queries(materialQueries)
				.addToPredefinedBuckets(false)
				.build();
	}

	public AvailableToPromiseQuery getMaterialQuery(@NonNull final I_M_ShipmentSchedule sched)
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

	private AvailableToPromiseQuery createMaterialQuery(@NonNull final I_M_ShipmentSchedule sched)
	{
		final int shipmentScheduleWarehouseId = shipmentScheduleEffectiveBL.getWarehouseId(sched);
		final WarehousePickingGroup warehousePickingGroup = warehouseDAO.getWarehousePickingGroupContainingWarehouseId(shipmentScheduleWarehouseId);
		final Set<Integer> warehouseIds = warehousePickingGroup != null ? warehousePickingGroup.getWarehouseIds() : ImmutableSet.of(shipmentScheduleWarehouseId);

		final int productId = sched.getM_Product_ID();
		final int bpartnerId = shipmentScheduleEffectiveBL.getC_BPartner_ID(sched);
		final Date date = shipmentScheduleEffectiveBL.getPreparationDate(sched);

		final AvailableToPromiseQueryBuilder stockQueryBuilder = AvailableToPromiseQuery.builder()
				.warehouseIds(warehouseIds)
				.productId(productId)
				.bpartnerId(bpartnerId)
				.date(TimeUtil.asLocalDateTime(date));

		// Add query attributes
		final int asiId = sched.getM_AttributeSetInstance_ID();
		if (asiId > 0)
		{
			stockQueryBuilder.storageAttributesKey(AttributesKeys
					.createAttributesKeyFromASIStorageAttributes(asiId)
					.orElse(AttributesKey.ALL));
		}

		// Cache the storage query and return it
		return stockQueryBuilder.build();
	}

	private static final List<ShipmentScheduleAvailableStockDetail> createStockDetails(final AvailableToPromiseResult stockResult)
	{
		return stockResult
				.getResultGroups()
				.stream()
				.map(ShipmentScheduleQtyOnHandStorage::createStockDetail)
				.collect(ImmutableList.toImmutableList());
	}

	private static ShipmentScheduleAvailableStockDetail createStockDetail(final AvailableToPromiseResult.ResultGroup result)
	{
		return ShipmentScheduleAvailableStockDetail.builder()
				.productId(result.getProductId())
				.warehouseId(result.getWarehouseId())
				.bpartnerId(result.getBpartnerId())
				.storageAttributesKey(result.getStorageAttributesKey())
				.qtyOnHand(result.getQty())
				.build();
	}

	private Stream<ShipmentScheduleAvailableStockDetail> streamStockDetailsMatching(final AvailableToPromiseQuery materialQuery)
	{
		return stockDetails
				.stream()
				.filter(stockDetail -> matching(materialQuery, stockDetail));
	}

	private static boolean matching(final AvailableToPromiseQuery query, final ShipmentScheduleAvailableStockDetail stockDetail)
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
		if (stockBPartnerId == AvailableToPromiseQuery.BPARTNER_ID_NONE)
		{
			// always match the available stock which is not allocated to a particular BPartner
		}
		else if (!query.isBPartnerMatching(stockBPartnerId))
		{
			return false;
		}

		//
		// Storage Attributes Key
		final List<AttributesKey> queryStorageAttributeKeys = query.getStorageAttributesKeys();
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

		final AvailableToPromiseQuery materialQuery = getMaterialQuery(sched);
		return streamStockDetailsMatching(materialQuery)
				.collect(ImmutableList.toImmutableList());
	}
}
