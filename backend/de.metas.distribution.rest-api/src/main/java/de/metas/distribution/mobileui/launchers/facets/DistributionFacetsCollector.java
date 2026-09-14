package de.metas.distribution.mobileui.launchers.facets;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.mobileui.external_services.product.DistributionProductService;
import de.metas.distribution.mobileui.external_services.sourcedoc.DistributionSourceDocService;
import de.metas.distribution.mobileui.external_services.warehouse.DistributionWarehouseService;
import de.metas.distribution.mobileui.launchers.DistributionOrderCollector;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.shipping.CarrierProductId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.UnaryOperator;

@Builder
public class DistributionFacetsCollector implements DistributionOrderCollector<DistributionFacet>
{
	@NonNull private final DDOrderService ddOrderService;
	@NonNull private final DistributionProductService productService;
	@NonNull private final DistributionWarehouseService warehouseService;
	@NonNull private final DistributionSourceDocService sourceDocService;

	private final HashSet<DistributionFacet> _result = new HashSet<>();
	private final HashSet<DDOrderId> pendingCollectProductsFromDDOrderIds = new HashSet<>();
	private final HashSet<DDOrderId> pendingCollectQuantitiesFromDDOrderIds = new HashSet<>();
	private final HashSet<DDOrderId> pendingCollectCarrierFromDDOrderIds = new HashSet<>();
	private final HashSet<DDOrderId> pendingCollectSalesOrderFromDDOrderIds = new HashSet<>();
	private final HashMultiset<DistributionFacetId> counters = HashMultiset.create();

	private final HashMap<WarehouseId, ITranslatableString> warehouseNames = new HashMap<>();
	private final HashMap<OrderId, ITranslatableString> salesOrderDocumentNos = new HashMap<>();
	private final HashMap<PPOrderId, ITranslatableString> manufacturingOrderDocumentNos = new HashMap<>();
	private final HashMap<ProductId, ITranslatableString> productNames = new HashMap<>();
	private final HashMap<ResourceId, ITranslatableString> resourceNames = new HashMap<>();
	private final HashMap<CarrierProductId, ITranslatableString> carrierProductNames = new HashMap<>();

	@Override
	public List<DistributionFacet> getCollectedItems()
	{
		processPendingRequests();

		return _result.stream()
				.map(facet -> facet.withHitCount(counters.count(facet.getFacetId())))
				.distinct()
				.collect(ImmutableList.toImmutableList());
	}

	public DistributionFacetsCollection toFacetsCollection() {return DistributionFacetsCollection.ofCollection(getCollectedItems());}

	@Override
	public void collect(final I_DD_Order ddOrder)
	{
		collectWarehouseFrom(ddOrder);
		collectWarehouseTo(ddOrder);
		collectSalesOrder(ddOrder);
		collectManufacturingOrder(ddOrder);
		collectDatePromised(ddOrder);
		collectProducts(ddOrder);
		collectQuantities(ddOrder);
		collectPlant(ddOrder);
		collectCarrier(ddOrder);
	}

	private void collectWarehouseFrom(final I_DD_Order ddOrder)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(ddOrder.getM_Warehouse_From_ID());
		if (warehouseId == null)
		{
			return;
		}

		collect(
				DistributionFacetId.ofWarehouseFromId(warehouseId),
				builder -> builder.caption(getWarehouseName(warehouseId))
		);
	}

	private void collectWarehouseTo(final I_DD_Order ddOrder)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(ddOrder.getM_Warehouse_To_ID());
		if (warehouseId == null)
		{
			return;
		}

		collect(
				DistributionFacetId.ofWarehouseToId(warehouseId),
				builder -> builder.caption(getWarehouseName(warehouseId))
		);
	}

	/**
	 * Batched like {@link #collectCarrier(I_DD_Order)} — a job's sales order is cut off from the order by
	 * aggregation the same way its carrier is (two of the same demand routes, plus the header), so the batch
	 * result must come back as {@code (DD_Order_ID, value)} pairs, not a plain distinct set of order ids, and the
	 * header route is folded into the SAME batch rather than read here directly: a real candidate order normally
	 * carries both the header and one line-level route (both aggregation sysconfigs default {@code true}), and
	 * reading the header separately would double the hit count for such an order. See
	 * {@code DDOrderLineDemandSqlHelper#getSalesOrderIdsByDDOrderIds} for the full routing.
	 */
	private void collectSalesOrder(final I_DD_Order ddOrder)
	{
		pendingCollectSalesOrderFromDDOrderIds.add(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()));
	}

	private void collectSalesOrder(final OrderId salesOrderId)
	{
		collect(
				DistributionFacetId.ofSalesOrderId(salesOrderId),
				builder -> builder.caption(getSalesOrderDocumentNo(salesOrderId))
		);
	}

	private void collectManufacturingOrder(final I_DD_Order ddOrder)
	{
		final PPOrderId manufacturingOrderId = PPOrderId.ofRepoIdOrNull(ddOrder.getForward_PP_Order_ID());
		if (manufacturingOrderId == null)
		{
			return;
		}

		collect(
				DistributionFacetId.ofManufacturingOrderId(manufacturingOrderId),
				builder -> builder.caption(getManufacturingOrderDocumentNo(manufacturingOrderId))
		);
	}

	private void collectPlant(final I_DD_Order ddOrder)
	{
		final ResourceId plantId = ResourceId.ofRepoIdOrNull(ddOrder.getPP_Plant_ID());
		if (plantId == null)
		{
			return;
		}

		collect(
				DistributionFacetId.ofPlantId(plantId),
				builder -> builder.caption(getPlantName(plantId))
		);
	}

	private void collectDatePromised(final I_DD_Order ddOrder)
	{
		final LocalDate datePromised = TimeUtil.asLocalDate(ddOrder.getDatePromised());

		collect(
				DistributionFacetId.ofDatePromised(datePromised),
				builder -> builder.sortNo(datePromised.toEpochDay()).caption(TranslatableStrings.date(datePromised))
		);
	}

	private void collectProducts(final I_DD_Order ddOrder)
	{
		pendingCollectProductsFromDDOrderIds.add(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()));
	}

	private void collectProduct(final ProductId productId)
	{
		collect(
				DistributionFacetId.ofProductId(productId),
				builder -> builder.caption(getProductName(productId))
		);
	}

	private void collectQuantities(final I_DD_Order ddOrder)
	{
		pendingCollectQuantitiesFromDDOrderIds.add(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()));
	}

	/**
	 * Batched like {@link #collectProducts(I_DD_Order)} — but unlike that one, the batch result MUST come back as
	 * {@code (DD_Order_ID, value)} pairs, not a plain distinct set of carrier ids: a job's carrier is cut off from
	 * the order by aggregation (two demand routes, see {@code DDOrderLineDemandSqlHelper}), so the per-job
	 * association has to survive the batch for the hit counter to increment once per job that actually carries the
	 * chip's carrier — not once per carrier, period.
	 * <p>
	 * {@link #processPendingRequests()} consumes the pairs via the returned multimap's {@code values()}, which — for
	 * an {@code ImmutableSetMultimap} — yields exactly one element per distinct {@code (DD_Order_ID, value)} entry
	 * (never one per distinct value only), so {@link #collectCarrier(CarrierProductId)} below is invoked once per
	 * job that carries that carrier, giving the correct per-job hit count.
	 */
	private void collectCarrier(final I_DD_Order ddOrder)
	{
		pendingCollectCarrierFromDDOrderIds.add(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()));
	}

	private void collectCarrier(final CarrierProductId carrierProductId)
	{
		collect(
				DistributionFacetId.ofCarrierProductId(carrierProductId),
				builder -> builder.caption(getCarrierProductName(carrierProductId))
		);
	}

	private void collectQuantity(final I_DD_OrderLine ddOrderLine)
	{
		collectQuantity(extractQtyEntered(ddOrderLine));
	}

	private void collectQuantity(final Quantity qty)
	{
		collect(
				DistributionFacetId.ofQuantity(qty),
				builder -> builder
						.sortNo(qty.toBigDecimal().multiply(new BigDecimal("10000")).longValue())
						.caption(TranslatableStrings.quantity(qty.toBigDecimal(), qty.getUOMSymbol()))
		);
	}

	private void collect(
			@NonNull final DistributionFacetId facetId,
			@NonNull final UnaryOperator<DistributionFacet.DistributionFacetBuilder> facetSupplier)
	{
		final boolean isFirst = !counters.contains(facetId);
		counters.add(facetId);

		if (isFirst)
		{
			final DistributionFacet.DistributionFacetBuilder facetBuilder = DistributionFacet.builder().facetId(facetId);
			facetSupplier.apply(facetBuilder);
			final DistributionFacet facet = facetBuilder.build();
			_result.add(facet);
		}
	}

	private ITranslatableString getWarehouseName(final WarehouseId warehouseId)
	{
		return warehouseNames.computeIfAbsent(warehouseId, this::retrieveWarehouseName);
	}

	private ITranslatableString retrieveWarehouseName(WarehouseId warehouseId)
	{
		return TranslatableStrings.anyLanguage(warehouseService.getWarehouseName(warehouseId));
	}

	private ITranslatableString getSalesOrderDocumentNo(final OrderId orderId)
	{
		return salesOrderDocumentNos.computeIfAbsent(orderId, this::retrieveOrderDocumentNo);
	}

	private ITranslatableString retrieveOrderDocumentNo(final OrderId orderId)
	{
		return TranslatableStrings.anyLanguage(sourceDocService.getDocumentNoById(orderId));
	}

	private ITranslatableString getManufacturingOrderDocumentNo(final PPOrderId manufacturingOrderId)
	{
		return manufacturingOrderDocumentNos.computeIfAbsent(manufacturingOrderId, this::retrieveManufacturingOrderDocumentNo);
	}

	private ITranslatableString retrieveManufacturingOrderDocumentNo(final PPOrderId manufacturingOrderId)
	{
		return TranslatableStrings.anyLanguage(sourceDocService.getDocumentNoById(manufacturingOrderId));
	}

	private ITranslatableString getProductName(final ProductId productId)
	{
		return productNames.computeIfAbsent(productId, this::retrieveProductName);
	}

	private ITranslatableString retrieveProductName(final ProductId productId)
	{
		return TranslatableStrings.anyLanguage(productService.getProductValueAndName(productId));
	}

	private ITranslatableString getCarrierProductName(final CarrierProductId carrierProductId)
	{
		return carrierProductNames.computeIfAbsent(carrierProductId, this::retrieveCarrierProductName);
	}

	private ITranslatableString retrieveCarrierProductName(final CarrierProductId carrierProductId)
	{
		return TranslatableStrings.anyLanguage(sourceDocService.getCarrierProductName(carrierProductId));
	}

	@NonNull
	public static Quantity extractQtyEntered(final I_DD_OrderLine ddOrderLine)
	{
		return Quantitys.of(ddOrderLine.getQtyEntered(), UomId.ofRepoId(ddOrderLine.getC_UOM_ID()));
	}

	private void processPendingRequests()
	{
		if (!pendingCollectProductsFromDDOrderIds.isEmpty())
		{
			ddOrderService.getProductIdsByDDOrderIds(pendingCollectProductsFromDDOrderIds)
					.forEach(this::collectProduct);
			pendingCollectProductsFromDDOrderIds.clear();
		}

		if (!pendingCollectQuantitiesFromDDOrderIds.isEmpty())
		{
			ddOrderService.streamLinesByDDOrderIds(pendingCollectQuantitiesFromDDOrderIds)
					.forEach(this::collectQuantity);
			pendingCollectQuantitiesFromDDOrderIds.clear();
		}

		if (!pendingCollectCarrierFromDDOrderIds.isEmpty())
		{
			ddOrderService.getCarrierProductIdsByDDOrderIds(pendingCollectCarrierFromDDOrderIds)
					.values()
					.forEach(this::collectCarrier);
			pendingCollectCarrierFromDDOrderIds.clear();
		}

		if (!pendingCollectSalesOrderFromDDOrderIds.isEmpty())
		{
			ddOrderService.getSalesOrderIdsByDDOrderIds(pendingCollectSalesOrderFromDDOrderIds)
					.values()
					.forEach(this::collectSalesOrder);
			pendingCollectSalesOrderFromDDOrderIds.clear();
		}
	}

	private ITranslatableString getPlantName(final ResourceId resourceId)
	{
		return resourceNames.computeIfAbsent(resourceId, id -> TranslatableStrings.constant(sourceDocService.getPlantName(id)));
	}
}
