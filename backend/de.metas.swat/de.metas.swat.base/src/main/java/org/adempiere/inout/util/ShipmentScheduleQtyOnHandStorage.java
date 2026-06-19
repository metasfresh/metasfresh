package org.adempiere.inout.util;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.cockpit.stock.StockDataQuery;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.QtyCalculationsBOM;
import org.eevolution.api.QtyCalculationsBOMLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * All relevant stock for a list of shipment schedules
 * Allows changing (in memory!) the qtyOnHand.
 *
 * @see ShipmentScheduleQtyOnHandStorageLoader
 */
@ToString(of = "stockDetails")
public class ShipmentScheduleQtyOnHandStorage
{
	@NonNull private final ImmutableList<ShipmentScheduleAvailableStockDetail> stockDetails;
	@NonNull private final StockDataQueriesLoadingCache cachedMaterialQueries;
	@NonNull private final PickingBOMsLoadingCache cachedPickingBOMs;

	@Builder
	private ShipmentScheduleQtyOnHandStorage(
			@Nullable final StockDataQueriesLoadingCache cachedMaterialQueries,
			@Nullable final PickingBOMsLoadingCache cachedPickingBOMs,
			@NonNull final List<ShipmentScheduleAvailableStockDetail> stockDetails)
	{
		this.cachedMaterialQueries = cachedMaterialQueries != null ? cachedMaterialQueries : new StockDataQueriesLoadingCache();
		this.cachedPickingBOMs = cachedPickingBOMs != null ? cachedPickingBOMs : new PickingBOMsLoadingCache();
		this.stockDetails = ImmutableList.copyOf(stockDetails);
	}

	// visible for debugging
	public StockDataQuery toQuery(@NonNull final I_M_ShipmentSchedule sched)
	{
		return cachedMaterialQueries.toQuery(sched);
	}

	static StockDataQuery toPickBOMComponentQuery(
			@NonNull final StockDataQuery mainProductQuery,
			@NonNull final QtyCalculationsBOMLine bomLine)
	{
		return mainProductQuery.toBuilder()
				.productId(bomLine.getProductId())
				.storageAttributesKey(AttributesKey.ALL)
				.build();
	}

	public ShipmentScheduleAvailableStock getStockDetailsMatching(@NonNull final I_M_ShipmentSchedule sched)
	{
		if (stockDetails.isEmpty()) {return ShipmentScheduleAvailableStock.of();}

		//
		// Main product
		final StockDataQuery mainProductQuery = toQuery(sched);
		final ArrayList<ShipmentScheduleAvailableStockDetail> availableStockDetails = new ArrayList<>(getStockDetailsMatching(mainProductQuery));

		//
		// Picking (=> for "Trading BOM" feature)
		final PPOrderId pickFromOrderId = PPOrderId.ofRepoIdOrNull(sched.getPickFrom_Order_ID());
		availableStockDetails.addAll(createPickFromStockDetails(mainProductQuery, pickFromOrderId));

		return ShipmentScheduleAvailableStock.builder()
				.stockDetails(availableStockDetails)
				.build();
	}

	private List<ShipmentScheduleAvailableStockDetail> createPickFromStockDetails(
			@NonNull final StockDataQuery mainProductQuery,
			@Nullable final PPOrderId pickFromOrderId)
	{
		final ProductId mainProductId = mainProductQuery.getProductId();

		final QtyCalculationsBOM pickingBOM = cachedPickingBOMs.getPickingBOM(pickFromOrderId).orElse(null);
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
					.stockMatchingKey(StockMatchingKey.builder()
							.productId(mainProductId)
							.warehouseId(warehouseId)
							.attributesKey(AttributesKey.ALL)
							.build())
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
		return stockDetail.getStockMatchingKey().matching(query);
	}
}
