package de.metas.distribution.workflows_api.facets;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.workflows_api.DistributionOrderCollector;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.UnaryOperator;

import static de.metas.distribution.workflows_api.DDOrderReferenceCollector.extractQtyEntered;

@Builder
public class DistributionFacetsCollector implements DistributionOrderCollector<DistributionFacet>
{
	@NonNull private final IWarehouseBL warehouseBL;
	@NonNull private final IOrderBL orderBL;
	@NonNull private final IPPOrderBL ppOrderBL;
	@NonNull private final DDOrderService ddOrderService;
	@NonNull private final IProductBL productBL;

	private final HashSet<DistributionFacet> _result = new HashSet<>();
	private final HashSet<DDOrderId> pendingCollectProductsFromDDOrderIds = new HashSet<>();
	private final HashSet<DDOrderId> pendingCollectQuantitiesFromDDOrderIds = new HashSet<>();
	private final HashMultiset<DistributionFacetId> counters = HashMultiset.create();

	private final HashMap<WarehouseId, ITranslatableString> warehouseNames = new HashMap<>();
	private final HashMap<OrderId, ITranslatableString> salesOrderDocumentNos = new HashMap<>();
	private final HashMap<PPOrderId, ITranslatableString> manufacturingOrderDocumentNos = new HashMap<>();
	private final HashMap<ProductId, ITranslatableString> productNames = new HashMap<>();
	private final HashMap<ResourceId, ITranslatableString> resourceNames = new HashMap<>();

	@Override
	public Collection<DistributionFacet> getCollectedItems()
	{
		processPendingRequests();

		return _result.stream()
				.map(facet -> facet.withHitCount(counters.count(facet.getFacetId())))
				.collect(ImmutableSet.toImmutableSet());
	}

	public DistributionFacetsCollection toFacetsCollection() {return DistributionFacetsCollection.ofCollection(getCollectedItems());}

	@Override
	public void collect(final I_DD_Order ddOrder, final boolean isJobStarted_NOTUSED)
	{
		collectWarehouseFrom(ddOrder);
		collectWarehouseTo(ddOrder);
		collectSalesOrder(ddOrder);
		collectManufacturingOrder(ddOrder);
		collectDatePromised(ddOrder);
		collectProducts(ddOrder);
		collectQuantities(ddOrder);
		collectPlant(ddOrder);
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

	private void collectSalesOrder(final I_DD_Order ddOrder)
	{
		final OrderId salesOrderId = OrderId.ofRepoIdOrNull(ddOrder.getC_Order_ID());
		if (salesOrderId == null)
		{
			return;
		}

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
				builder -> builder.caption(getResourceName(plantId))
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
		return TranslatableStrings.anyLanguage(warehouseBL.getWarehouseName(warehouseId));
	}

	private ITranslatableString getSalesOrderDocumentNo(final OrderId orderId)
	{
		return salesOrderDocumentNos.computeIfAbsent(orderId, this::retrieveOrderDocumentNo);
	}

	private ITranslatableString retrieveOrderDocumentNo(final OrderId orderId)
	{
		return TranslatableStrings.anyLanguage(orderBL.getDocumentNoById(orderId));
	}

	private ITranslatableString getManufacturingOrderDocumentNo(final PPOrderId manufacturingOrderId)
	{
		return manufacturingOrderDocumentNos.computeIfAbsent(manufacturingOrderId, this::retrieveManufacturingOrderDocumentNo);
	}

	private ITranslatableString retrieveManufacturingOrderDocumentNo(final PPOrderId manufacturingOrderId)
	{
		return TranslatableStrings.anyLanguage(ppOrderBL.getDocumentNoById(manufacturingOrderId));
	}

	private ITranslatableString getProductName(final ProductId productId)
	{
		return productNames.computeIfAbsent(productId, this::retrieveProductName);
	}

	private ITranslatableString retrieveProductName(final ProductId productId)
	{
		return TranslatableStrings.anyLanguage(productBL.getProductValueAndName(productId));
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
	}

	private ITranslatableString getResourceName(final ResourceId resourceId)
	{
		return resourceNames.computeIfAbsent(resourceId, id -> TranslatableStrings.constant(ppOrderBL.getResourceName(id)));
	}

}
