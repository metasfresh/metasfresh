package org.adempiere.inout.util;

import lombok.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.util.Util.ArrayKey;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.cockpit.stock.StockDataItem;
import de.metas.material.cockpit.stock.StockDataMultiQuery;
import de.metas.material.cockpit.stock.StockDataQuery;
import de.metas.material.cockpit.stock.StockRepository;
import de.metas.product.ProductId;
import de.metas.util.Services;

/**
 * Loads stock details which are relevant to given {@link I_M_ShipmentSchedule}s.
 * Allows to change (in memory!) the qtyOnHand.
 */
public class ShipmentScheduleQtyOnHandStorage
{
	// services
	private final transient IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final transient IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	private final List<ShipmentScheduleAvailableStockDetail> stockDetails;
	private final Map<ArrayKey, StockDataQuery> cachedMaterialQueries = new HashMap<>();

	public ShipmentScheduleQtyOnHandStorage(
			@NonNull final List<I_M_ShipmentSchedule> shipmentSchedules,
			@NonNull final StockRepository stockRepository)
	{
		stockDetails = createStockDetailsFromShipmentSchedules(shipmentSchedules, stockRepository);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("storageRecords", stockDetails)
				.toString();
	}

	private final List<ShipmentScheduleAvailableStockDetail> createStockDetailsFromShipmentSchedules(
			final List<I_M_ShipmentSchedule> shipmentSchedules,
			final StockRepository stockRepository)
	{
		if (shipmentSchedules.isEmpty())
		{
			return ImmutableList.of();
		}
		final StockDataMultiQuery multiQuery = createMaterialMultiQueryOrNull(shipmentSchedules);
		if (multiQuery == null)
		{
			return ImmutableList.of();
		}

		final List<StockDataItem> stockResult = stockRepository
				.streamStockDatatems(multiQuery)
				.collect(ImmutableList.toImmutableList());

		return createStockDetails(stockResult);
	}

	private StockDataMultiQuery createMaterialMultiQueryOrNull(@NonNull final List<I_M_ShipmentSchedule> shipmentSchedules)
	{
		final Set<StockDataQuery> stockDataQueries = shipmentSchedules
				.stream()
				.map(this::getMaterialQuery)
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());
		if (stockDataQueries.isEmpty())
		{
			return null;
		}

		return StockDataMultiQuery.builder()
				.stockDataQueries(stockDataQueries)
				.build();
	}

	public StockDataQuery getMaterialQuery(@NonNull final I_M_ShipmentSchedule sched)
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

	private StockDataQuery createMaterialQuery(@NonNull final I_M_ShipmentSchedule sched)
	{
		final WarehouseId shipmentScheduleWarehouseId = shipmentScheduleEffectiveBL.getWarehouseId(sched);
		final Set<WarehouseId> warehouseIds = warehouseDAO.getWarehouseIdsOfSamePickingGroup(shipmentScheduleWarehouseId);

		final int productId = sched.getM_Product_ID();
		// final BPartnerId bpartnerId = shipmentScheduleEffectiveBL.getBPartnerId(sched);
		// final Date date = shipmentScheduleEffectiveBL.getPreparationDate(sched);

		final StockDataQuery stockQueryBuilder = StockDataQuery.builder()
				.warehouseIds(warehouseIds)
				.productId(ProductId.ofRepoId(productId))
				.build();

		return stockQueryBuilder;
	}

	private static final List<ShipmentScheduleAvailableStockDetail> createStockDetails(final Collection<StockDataItem> stockResult)
	{
		return stockResult
				.stream()
				.map(ShipmentScheduleQtyOnHandStorage::createStockDetail)
				.collect(ImmutableList.toImmutableList());
	}

	private static ShipmentScheduleAvailableStockDetail createStockDetail(@NonNull final StockDataItem result)
	{

		return ShipmentScheduleAvailableStockDetail.builder()
				.productId(result.getProductId())
				.warehouseId(result.getWarehouseId())
				// .bpartnerId(result.getBpartnerId())
				.storageAttributesKey(result.getStorageAttributesKey())
				.qtyOnHand(result.getQtyOnHand())
				.build();
	}

	private Stream<ShipmentScheduleAvailableStockDetail> streamStockDetailsMatching(final StockDataQuery materialQuery)
	{
		return stockDetails
				.stream()
				.filter(stockDetail -> matching(materialQuery, stockDetail));
	}

	private static boolean matching(final StockDataQuery query, final ShipmentScheduleAvailableStockDetail stockDetail)
	{
		//
		// Product
		if (!Objects.equals(query.getProductId(), stockDetail.getProductId()))
		{
			return false;
		}

		//
		// Warehouse
		final Set<WarehouseId> queryWarehouseIds = query.getWarehouseIds();
		if (!queryWarehouseIds.isEmpty() && !queryWarehouseIds.contains(stockDetail.getWarehouseId()))
		{
			return false;
		}

		final boolean queryMatchesAll = query.getStorageAttributesKey().isAll();
		final boolean queryMatchesStockDetail = Objects.equals(query.getStorageAttributesKey(), stockDetail.getStorageAttributesKey());
		if (!queryMatchesAll && !queryMatchesStockDetail)
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
			return ImmutableList.of();
		}

		final StockDataQuery materialQuery = getMaterialQuery(sched);
		return streamStockDetailsMatching(materialQuery)
				.collect(ImmutableList.toImmutableList());
	}
}
