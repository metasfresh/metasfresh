package de.metas.distribution.workflows_api.facets;

import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.workflows_api.DistributionOrderCollector;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

@Builder
@RequiredArgsConstructor
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
	private final HashSet<DistributionFacetId> seenFacetIds = new HashSet<>();

	private final HashMap<WarehouseId, ITranslatableString> warehouseNames = new HashMap<>();
	private final HashMap<OrderId, ITranslatableString> salesOrderDocumentNos = new HashMap<>();
	private final HashMap<PPOrderId, ITranslatableString> manufacturingOrderDocumentNos = new HashMap<>();
	private final HashMap<ProductId, ITranslatableString> productNames = new HashMap<>();

	@Override
	public Collection<DistributionFacet> getCollectedItems()
	{
		processPendingRequests();
		return _result;
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
	}

	private void collectWarehouseFrom(final I_DD_Order ddOrder)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(ddOrder.getM_Warehouse_From_ID());
		if (warehouseId == null)
		{
			return;
		}

		final DistributionFacetId facetId = DistributionFacetId.ofWarehouseFromId(warehouseId);
		if (!seenFacetIds.add(facetId))
		{
			return;
		}

		collect(DistributionFacet.of(facetId, getWarehouseName(warehouseId)));
	}

	private void collectWarehouseTo(final I_DD_Order ddOrder)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(ddOrder.getM_Warehouse_To_ID());
		if (warehouseId == null)
		{
			return;
		}

		final DistributionFacetId facetId = DistributionFacetId.ofWarehouseToId(warehouseId);
		if (!seenFacetIds.add(facetId))
		{
			return;
		}

		collect(DistributionFacet.of(facetId, getWarehouseName(warehouseId)));
	}

	private void collectSalesOrder(final I_DD_Order ddOrder)
	{
		final OrderId salesOrderId = OrderId.ofRepoIdOrNull(ddOrder.getC_Order_ID());
		if (salesOrderId == null)
		{
			return;
		}

		final DistributionFacetId facetId = DistributionFacetId.ofSalesOrderId(salesOrderId);
		if (!seenFacetIds.add(facetId))
		{
			return;
		}

		collect(DistributionFacet.of(facetId, getSalesOrderDocumentNo(salesOrderId)));
	}

	private void collectManufacturingOrder(final I_DD_Order ddOrder)
	{
		final PPOrderId manufacturingOrderId = PPOrderId.ofRepoIdOrNull(ddOrder.getForward_PP_Order_ID());
		if (manufacturingOrderId == null)
		{
			return;
		}

		final DistributionFacetId facetId = DistributionFacetId.ofManufacturingOrderId(manufacturingOrderId);
		if (!seenFacetIds.add(facetId))
		{
			return;
		}

		collect(DistributionFacet.of(facetId, getManufacturingOrderDocumentNo(manufacturingOrderId)));
	}

	private void collectDatePromised(final I_DD_Order ddOrder)
	{
		final LocalDate datePromised = TimeUtil.asLocalDate(ddOrder.getDatePromised());
		final DistributionFacetId facetId = DistributionFacetId.ofDatePromised(datePromised);
		if (!seenFacetIds.add(facetId))
		{
			return;
		}

		collect(DistributionFacet.of(facetId, TranslatableStrings.date(datePromised)));
	}

	private void collectProducts(final I_DD_Order ddOrder)
	{
		pendingCollectProductsFromDDOrderIds.add(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()));
	}

	private void collectProduct(final ProductId productId)
	{
		final DistributionFacetId facetId = DistributionFacetId.ofProductId(productId);
		if (!seenFacetIds.add(facetId))
		{
			return;
		}

		collect(DistributionFacet.of(facetId, getProductName(productId)));
	}

	private void collectQuantities(final I_DD_Order ddOrder)
	{
		pendingCollectQuantitiesFromDDOrderIds.add(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()));
	}

	private void collectQuantity(final I_DD_OrderLine ddOrderLine)
	{
		collectQuantity(Quantitys.of(ddOrderLine.getQtyEntered(), UomId.ofRepoId(ddOrderLine.getC_UOM_ID())));
	}

	private void collectQuantity(final Quantity qty)
	{
		final DistributionFacetId facetId = DistributionFacetId.ofQuantity(qty);
		if (!seenFacetIds.add(facetId))
		{
			return;
		}

		collect(DistributionFacet.of(facetId, TranslatableStrings.quantity(qty.toBigDecimal(), qty.getUOMSymbol())));
	}

	private void collect(DistributionFacet facet) {_result.add(facet);}

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

}
