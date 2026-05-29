package org.adempiere.inout.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.qty_reservation.QtyReservationRepository;
import de.metas.logging.TableRecordMDC;
import de.metas.material.cockpit.stock.StockDataItem;
import de.metas.material.cockpit.stock.StockDataMultiQuery;
import de.metas.material.cockpit.stock.StockDataQuery;
import de.metas.material.cockpit.stock.StockRepository;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.QtyCalculationsBOM;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.adempiere.inout.util.ShipmentScheduleQtyOnHandStorage.toPickBOMComponentQuery;

class ShipmentScheduleQtyOnHandStorageLoader
{
	@NonNull private final StockRepository stockRepository;
	@NonNull private final QtyReservationRepository qtyReservationRepository;
	@NonNull private final ImmutableList<I_M_ShipmentSchedule> shipmentSchedules;

	@NonNull private final StockDataQueriesLoadingCache cachedMaterialQueries = new StockDataQueriesLoadingCache();
	@NonNull private final PickingBOMsLoadingCache cachedPickingBOMs = new PickingBOMsLoadingCache();
	@Nullable private ShipmentScheduleReservations _qtyReservations = null;

	@Builder
	private ShipmentScheduleQtyOnHandStorageLoader(
			@NonNull final StockRepository stockRepository,
			@NonNull final QtyReservationRepository qtyReservationRepository,
			@NonNull final List<I_M_ShipmentSchedule> shipmentSchedules)
	{
		this.stockRepository = stockRepository;
		this.qtyReservationRepository = qtyReservationRepository;
		this.shipmentSchedules = ImmutableList.copyOf(shipmentSchedules);
	}

	public ShipmentScheduleQtyOnHandStorage execute()
	{
		return ShipmentScheduleQtyOnHandStorage.builder()
				.cachedMaterialQueries(cachedMaterialQueries)
				.cachedPickingBOMs(cachedPickingBOMs)
				.stockDetails(retrieveStockDetails())
				.build();
	}

	public ImmutableList<ShipmentScheduleAvailableStockDetail> retrieveStockDetails()
	{
		final StockDataMultiQuery multiQuery = createMultiQueryOrNull();
		if (multiQuery == null)
		{
			return ImmutableList.of();
		}

		return stockRepository.streamStockDataItems(multiQuery)
				.map(this::toStockDetail)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ShipmentScheduleReservations getReservations()
	{
		if (_qtyReservations == null)
		{
			_qtyReservations = retrieveReservations();
		}
		return _qtyReservations;
	}

	@NonNull
	private ShipmentScheduleReservations retrieveReservations()
	{
		return ShipmentScheduleReservations.of(qtyReservationRepository.getActiveByProductIds(extractProductIds(shipmentSchedules)));
	}

	private static ImmutableSet<ProductId> extractProductIds(final @NotNull List<I_M_ShipmentSchedule> shipmentSchedules)
	{
		return shipmentSchedules.stream()
				.map(shipmentSchedule -> ProductId.ofRepoId(shipmentSchedule.getM_Product_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	@Nullable
	private StockDataMultiQuery createMultiQueryOrNull()
	{
		if (shipmentSchedules.isEmpty())
		{
			return null;
		}

		final Set<StockDataQuery> stockDataQueries = shipmentSchedules.stream()
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

	private Stream<StockDataQuery> getMaterialQueriesIncludingPickingBOMComponents(@NonNull final I_M_ShipmentSchedule shipmentScheduleRecord)
	{
		try (final MDC.MDCCloseable ignored = TableRecordMDC.putTableRecordReference(shipmentScheduleRecord))
		{
			final StockDataQuery mainProductQuery = cachedMaterialQueries.toQuery(shipmentScheduleRecord);

			final QtyCalculationsBOM bom = getPickingBOM(shipmentScheduleRecord).orElse(null);
			if (bom == null)
			{
				return Stream.of(mainProductQuery);
			}
			else
			{
				final Stream<StockDataQuery> componentQueries = bom.getLines()
						.stream()
						.map(bomLine -> toPickBOMComponentQuery(mainProductQuery, bomLine));

				return Stream.concat(Stream.of(mainProductQuery), componentQueries);
			}
		}
	}

	private Optional<QtyCalculationsBOM> getPickingBOM(@NonNull final I_M_ShipmentSchedule sched)
	{
		return cachedPickingBOMs.getPickingBOM(PPOrderId.ofRepoIdOrNull(sched.getPickFrom_Order_ID()));
	}

	private ShipmentScheduleAvailableStockDetail toStockDetail(@NonNull final StockDataItem stockDataItem)
	{
		final StockMatchingKey stockMatchingKey = extractStockMatchingKey(stockDataItem);

		return ShipmentScheduleAvailableStockDetail.builder()
				.stockMatchingKey(stockMatchingKey)
				.qtyOnHand(stockDataItem.getQtyOnHand())
				.qtyReservations(getReservations().filter(stockMatchingKey))
				.build();
	}

	private static StockMatchingKey extractStockMatchingKey(final @NotNull StockDataItem stockDataItem)
	{
		return StockMatchingKey.builder()
				.productId(stockDataItem.getProductId())
				.warehouseId(stockDataItem.getWarehouseId())
				.attributesKey(stockDataItem.getStorageAttributesKey())
				.build();
	}
}

