package org.adempiere.inout.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.TableRecordMDC;
import de.metas.material.cockpit.stock.StockDataItem;
import de.metas.material.cockpit.stock.StockDataMultiQuery;
import de.metas.material.cockpit.stock.StockDataQuery;
import de.metas.material.cockpit.stock.StockRepository;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.util.Util.ArrayKey;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.QtyCalculationsBOM;
import org.eevolution.api.QtyCalculationsBOMLine;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Loads stock details which are relevant to given {@link I_M_ShipmentSchedule}s.
 * Allows to change (in memory!) the qtyOnHand.
 */
@ToString(of = "stockDetails")
public class ShipmentScheduleQtyOnHandStorage
{
	// services
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IPPOrderBL ppOrdersBL = Services.get(IPPOrderBL.class);

	private final ImmutableList<ShipmentScheduleAvailableStockDetail> stockDetails;
	private final Map<ArrayKey, StockDataQuery> cachedMaterialQueries = new HashMap<>();
	private final Map<PPOrderId, Optional<QtyCalculationsBOM>> cachedPickingBOMs = new HashMap<>();

	public ShipmentScheduleQtyOnHandStorage(
			@NonNull final List<I_M_ShipmentSchedule> shipmentSchedules,
			@NonNull final StockRepository stockRepository)
	{
		this.stockDetails = toStockDetails(shipmentSchedules, stockRepository);
	}

	@VisibleForTesting
	ShipmentScheduleQtyOnHandStorage(@NonNull final List<ShipmentScheduleAvailableStockDetail> stockDetails)
	{
		this.stockDetails = ImmutableList.copyOf(stockDetails);
	}

	private ImmutableList<ShipmentScheduleAvailableStockDetail> toStockDetails(
			final List<I_M_ShipmentSchedule> shipmentSchedules,
			final StockRepository stockRepository)
	{
		if (shipmentSchedules.isEmpty())
		{
			return ImmutableList.of();
		}
		final StockDataMultiQuery multiQuery = toMultiQueryOrNull(shipmentSchedules);
		if (multiQuery == null)
		{
			return ImmutableList.of();
		}

		final List<StockDataItem> stockResult = stockRepository
				.streamStockDataItems(multiQuery)
				.collect(ImmutableList.toImmutableList());

		return toStockDetails(stockResult);
	}

	@Nullable
	private StockDataMultiQuery toMultiQueryOrNull(@NonNull final List<I_M_ShipmentSchedule> shipmentSchedules)
	{
		final Set<StockDataQuery> stockDataQueries = shipmentSchedules
				.stream()
				.flatMap(this::getMaterialQueriesIncludingPickingBOMComponents)
				.collect(ImmutableSet.toImmutableSet());
		if (stockDataQueries.isEmpty())
		{
			return null;
		}

		return StockDataMultiQuery.builder()
				.stockDataQueries(stockDataQueries)
				.build();
	}

	public StockDataQuery toQuery(@NonNull final I_M_ShipmentSchedule sched)
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

		return cachedMaterialQueries.computeIfAbsent(materialQueryCacheKey, k -> toQuery0(sched));
	}

	private StockDataQuery toQuery0(@NonNull final I_M_ShipmentSchedule sched)
	{
		final WarehouseId shipmentScheduleWarehouseId = shipmentScheduleEffectiveBL.getWarehouseId(sched);
		final Set<WarehouseId> warehouseIds = warehouseDAO.getWarehouseIdsOfSamePickingGroup(shipmentScheduleWarehouseId);

		final ProductId productId = ProductId.ofRepoId(sched.getM_Product_ID());

		return StockDataQuery.builder()
				.warehouseIds(warehouseIds)
				.productId(productId)
				.build();
	}

	private static StockDataQuery toPickBOMComponentQuery(
			@NonNull final StockDataQuery mainProductQuery,
			@NonNull final QtyCalculationsBOMLine bomLine)
	{
		return mainProductQuery.toBuilder()
				.productId(bomLine.getProductId())
				.storageAttributesKey(AttributesKey.ALL)
				.build();

	}

	private Stream<StockDataQuery> getMaterialQueriesIncludingPickingBOMComponents(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		try (final MDCCloseable shipmentScheduleMDC = TableRecordMDC.putTableRecordReference(shipmentScheduleRecord))
		{
			final StockDataQuery query = toQuery(shipmentScheduleRecord);

			final QtyCalculationsBOM bom = getPickingBOM(shipmentScheduleRecord).orElse(null);
			if (bom == null)
			{
				return Stream.of(query);
			}
			else
			{
				final Stream<StockDataQuery> componentQueries = bom.getLines()
						.stream()
						.map(bomLine -> toPickBOMComponentQuery(query, bomLine));

				return Stream.concat(Stream.of(query), componentQueries);
			}
		}
	}

	private Optional<QtyCalculationsBOM> getPickingBOM(@NonNull final I_M_ShipmentSchedule sched)
	{
		final PPOrderId pickingOrderId = PPOrderId.ofRepoIdOrNull(sched.getPickFrom_Order_ID());
		return pickingOrderId != null
				? getPickingBOM(pickingOrderId)
				: Optional.empty();
	}

	private Optional<QtyCalculationsBOM> getPickingBOM(@Nullable final PPOrderId pickingOrderId)
	{
		return pickingOrderId != null
				? cachedPickingBOMs.computeIfAbsent(pickingOrderId, ppOrdersBL::getOpenPickingOrderBOM)
				: Optional.empty();
	}

	private static ImmutableList<ShipmentScheduleAvailableStockDetail> toStockDetails(final Collection<StockDataItem> result)
	{
		return result
				.stream()
				.map(ShipmentScheduleQtyOnHandStorage::toStockDetail)
				.collect(ImmutableList.toImmutableList());
	}

	private static ShipmentScheduleAvailableStockDetail toStockDetail(@NonNull final StockDataItem result)
	{
		return ShipmentScheduleAvailableStockDetail.builder()
				.productId(result.getProductId())
				.warehouseId(result.getWarehouseId())
				// .bpartnerId(result.getBpartnerId())
				.storageAttributesKey(result.getStorageAttributesKey())
				.qtyOnHand(result.getQtyOnHand())
				.build();
	}

	private boolean hasStockDetails()
	{
		return !stockDetails.isEmpty();
	}

	public ShipmentScheduleAvailableStock getStockDetailsMatching(@NonNull final I_M_ShipmentSchedule sched)
	{
		if (!hasStockDetails())
		{
			return ShipmentScheduleAvailableStock.of();
		}
		else
		{
			final ArrayList<ShipmentScheduleAvailableStockDetail> availableStockDetails = new ArrayList<>();

			//
			// Main product
			final StockDataQuery mainProductQuery = toQuery(sched);
			availableStockDetails.addAll(getStockDetailsMatching(mainProductQuery));

			//
			// Picking (=> for "Trading BOM" feature)
			final PPOrderId pickFromOrderId = PPOrderId.ofRepoIdOrNull(sched.getPickFrom_Order_ID());
			availableStockDetails.addAll(createPickFromStockDetails(mainProductQuery, pickFromOrderId));

			return ShipmentScheduleAvailableStock.of(availableStockDetails);
		}
	}

	private List<ShipmentScheduleAvailableStockDetail> createPickFromStockDetails(
			@NonNull final StockDataQuery mainProductQuery,
			@Nullable final PPOrderId pickFromOrderId)
	{
		final ProductId mainProductId = mainProductQuery.getProductId();

		final QtyCalculationsBOM pickingBOM = getPickingBOM(pickFromOrderId).orElse(null);
		if (pickingBOM == null)
		{
			return ImmutableList.of();
		}

		final ListMultimap<WarehouseId, ShipmentScheduleAvailableStockDetail> allComponentStockDetails = ArrayListMultimap.create();
		for (final QtyCalculationsBOMLine bomLine : pickingBOM.getLines())
		{
			final StockDataQuery componentQuery = toPickBOMComponentQuery(mainProductQuery, bomLine);
			for (final ShipmentScheduleAvailableStockDetail componentStockDetail : getStockDetailsMatching(componentQuery))
			{
				allComponentStockDetails.put(componentStockDetail.getWarehouseId(), componentStockDetail);
			}
		}

		final ArrayList<ShipmentScheduleAvailableStockDetail> pickFromStockDetails = new ArrayList<>();
		for (final WarehouseId warehouseId : allComponentStockDetails.keySet())
		{
			final List<ShipmentScheduleAvailableStockDetail> componentStockDetails = allComponentStockDetails.get(warehouseId);
			if (componentStockDetails.isEmpty())
			{
				continue;
			}

			pickFromStockDetails.add(ShipmentScheduleAvailableStockDetail.builder()
					.productId(mainProductId)
					.warehouseId(warehouseId)
					.storageAttributesKey(AttributesKey.ALL)
					.qtyOnHand(BigDecimal.ZERO)
					.pickingBOM(pickingBOM)
					.componentStockDetails(componentStockDetails)
					.build());
		}

		return pickFromStockDetails;
	}

	private ImmutableList<ShipmentScheduleAvailableStockDetail> getStockDetailsMatching(@NonNull final StockDataQuery query)
	{
		return stockDetails
				.stream()
				.filter(stockDetail -> matching(query, stockDetail))
				.collect(ImmutableList.toImmutableList());
	}

	private static boolean matching(final StockDataQuery query, final ShipmentScheduleAvailableStockDetail stockDetail)
	{
		//
		// Product
		if (!ProductId.equals(query.getProductId(), stockDetail.getProductId()))
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

		//
		// Attributes
		final boolean queryMatchesAll = query.getStorageAttributesKey().isAll();
		final boolean queryMatchesStockDetail = AttributesKey.equals(query.getStorageAttributesKey(), stockDetail.getStorageAttributesKey());
		if (!queryMatchesAll && !queryMatchesStockDetail)
		{
			return false;
		}

		return true;
	}
}
