package de.metas.handlingunits.ddorder.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.DDOrderLineHUPackingAware;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.ddorder.DDOrderLineCreateRequest;
import de.metas.handlingunits.ddorder.IHUDDOrderBL;
import de.metas.handlingunits.ddorder.QuarantineInOutLine;
import de.metas.handlingunits.ddorder.movement.DDOrderLinesAllocatorFactory;
import de.metas.handlingunits.ddorder.movement.MovementsFromSchedulesGenerator;
import de.metas.handlingunits.ddorder.picking.DDOrderPickFromService;
import de.metas.handlingunits.ddorder.picking.DDOrderPickPlan;
import de.metas.handlingunits.ddorder.picking.DDOrderPickSchedule;
import de.metas.handlingunits.ddorder.picking.PickingPlanCreateRequest;
import de.metas.handlingunits.ddorder.producer.HUToDistribute;
import de.metas.handlingunits.ddorder.producer.HUs2DDOrderProducer;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.eevolution.api.DDOrderId;
import org.eevolution.api.DDOrderLineId;
import org.eevolution.api.DDOrderQuery;
import org.eevolution.api.IDDOrderBL;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_DD_OrderLine_Or_Alternative;
import org.eevolution.model.X_DD_OrderLine;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
public class HUDDOrderBL implements IHUDDOrderBL
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IHUPackingAwareBL huPackingAwareBL = Services.get(IHUPackingAwareBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IDDOrderDAO ddOrderDAO = Services.get(IDDOrderDAO.class);
	private final IDDOrderBL ddOrderCoreBL = Services.get(IDDOrderBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
	private final IHUInOutDAO huInOutDAO = Services.get(IHUInOutDAO.class);
	private final DDOrderPickFromService ddOrderPickFromService;
	private final DDOrderLinesAllocatorFactory ddOrderLinesAllocatorFactory;

	private static final String SYSCONFIG_IsCreateMovementOnComplete = "DDOrder_isCreateMovementOnComplete";

	public HUDDOrderBL(@NonNull final DDOrderPickFromService ddOrderPickFromService)
	{
		this.ddOrderPickFromService = ddOrderPickFromService;
		this.ddOrderLinesAllocatorFactory = new DDOrderLinesAllocatorFactory(ddOrderPickFromService, this);
	}

	@Override
	public I_DD_Order getById(final DDOrderId ddOrderId)
	{
		return ddOrderDAO.getById(ddOrderId);
	}

	@Override
	public Stream<I_DD_Order> streamDDOrders(final DDOrderQuery query)
	{
		return ddOrderDAO.streamDDOrders(query);
	}

	@Override
	public void save(final I_DD_Order ddOrder)
	{
		ddOrderDAO.save(ddOrder);
	}

	@Override
	public List<I_DD_OrderLine> retrieveLines(final I_DD_Order order)
	{
		return ddOrderDAO.retrieveLines(order);
	}

	@Override
	public I_DD_OrderLine getLineById(final DDOrderLineId ddOrderLineId)
	{
		return ddOrderDAO.getLineById(ddOrderLineId);
	}

	@Override
	public List<I_DD_OrderLine_Alternative> retrieveAllAlternatives(final I_DD_OrderLine ddOrderLine)
	{
		return ddOrderDAO.retrieveAllAlternatives(ddOrderLine);
	}

	@Override
	public void completeDDOrderIfNeeded(final I_DD_Order ddOrder)
	{
		ddOrderCoreBL.completeDDOrderIfNeeded(ddOrder);
	}

	@Override
	public DDOrderLinesAllocatorFactory prepareAllocateAndMove()
	{
		return ddOrderLinesAllocatorFactory;
	}

	@Override
	public void closeLine(final I_DD_OrderLine ddOrderLine)
	{
		ddOrderLine.setIsDelivered_Override(X_DD_OrderLine.ISDELIVERED_OVERRIDE_Yes);
		InterfaceWrapperHelper.save(ddOrderLine);

		final DDOrderLineId ddOrderLineId = DDOrderLineId.ofRepoId(ddOrderLine.getDD_OrderLine_ID());
		ddOrderPickFromService.removeDraftSchedules(ddOrderLineId);
	}

	@Override
	public void unassignHUs(@NonNull final DDOrderLineId ddOrderLineId, @NonNull final Set<HuId> huIdsToUnassign)
	{
		//
		// Unassign the given HUs from DD_OrderLine
		huAssignmentBL.unassignHUs(TableRecordReference.of(I_DD_OrderLine.Table_Name, ddOrderLineId), huIdsToUnassign);

		//
		// Remove those HUs from scheduled to move list (task 08639)
		ddOrderPickFromService.removeFromHUsScheduledToPickList(ddOrderLineId, huIdsToUnassign);
	}

	@Override
	public List<I_DD_Order> createQuarantineDDOrderForReceiptLines(final List<QuarantineInOutLine> receiptLines)
	{

		final List<HUToDistribute> husToQuarantine = receiptLines.stream()
				.flatMap(receiptLine -> toHUsToQuarantine(receiptLine).stream())
				.collect(ImmutableList.toImmutableList());

		return createQuarantineDDOrderForHUs(husToQuarantine);

	}

	private List<HUToDistribute> toHUsToQuarantine(final QuarantineInOutLine receiptLine)
	{
		return huInOutDAO.retrieveHUsForReceiptLineId(receiptLine.getReceiptLineId())
				.stream()
				.map(hu -> HUToDistribute.builder()
						.hu(hu)
						.quarantineLotNo(receiptLine.getLotNumberQuarantine())
						.bpartnerLocationId(BPartnerLocationId.ofRepoId(receiptLine.getBpartnerId(), receiptLine.getBpartnerLocationId()))
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<I_DD_Order> createQuarantineDDOrderForHUs(final List<HUToDistribute> husToDistribute)
	{
		final WarehouseId quarantineWarehouseId = warehouseDAO.retrieveQuarantineWarehouseId();

		final LocatorId quarantineLocatorId = warehouseBL.getDefaultLocatorId(quarantineWarehouseId);

		final ImmutableSet<Entry<BPartnerLocationId, Collection<HUToDistribute>>> entries = husToDistribute
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(HUToDistribute::getBpartnerLocationId, Function.identity()))
				.asMap()
				.entrySet();

		final ImmutableList.Builder<I_DD_Order> result = ImmutableList.builder();
		for (final Entry<BPartnerLocationId, Collection<HUToDistribute>> entry : entries)
		{
			final Optional<I_DD_Order> ddOrder = HUs2DDOrderProducer.newProducer(ddOrderPickFromService)
					.setLocatorToId(quarantineLocatorId)
					.setBpartnerLocationId(entry.getKey())
					.setHUs(entry.getValue().iterator())
					.process();

			ddOrder.ifPresent(result::add);
		}
		return result.build();
	}

	public DDOrderLineId addDDOrderLine(@NonNull final DDOrderLineCreateRequest ddOrderLineCreateRequest)
	{
		final ProductId productId = ddOrderLineCreateRequest.getProductId();
		final HUPIItemProductId mHUPIProductID = ddOrderLineCreateRequest.getMHUPIProductID();
		final BigDecimal qtyEntered = ddOrderLineCreateRequest.getQtyEntered();
		final I_DD_Order ddOrder = ddOrderLineCreateRequest.getDdOrder();

		final de.metas.handlingunits.model.I_DD_OrderLine ddOrderLine =
				InterfaceWrapperHelper.newInstance(de.metas.handlingunits.model.I_DD_OrderLine.class, ddOrder);

		ddOrderLine.setIsInvoiced(false);
		ddOrderLine.setDD_Order_ID(ddOrder.getDD_Order_ID());
		ddOrderLine.setM_Product_ID(productId.getRepoId());

		ddOrderLine.setC_UOM_ID(productBL.getStockUOMId(productId).getRepoId());

		final WarehouseId warehouseIdFrom = WarehouseId.ofRepoId(ddOrder.getM_Warehouse_From_ID());
		final LocatorId locatorFromId = warehouseBL.getDefaultLocatorId(warehouseIdFrom);
		ddOrderLine.setM_Locator_ID(locatorFromId.getRepoId());

		final WarehouseId warehouseToId = WarehouseId.ofRepoId(ddOrder.getM_Warehouse_To_ID());
		final LocatorId locatorToId = warehouseBL.getDefaultLocatorId(warehouseToId);
		ddOrderLine.setM_LocatorTo_ID(locatorToId.getRepoId());

		if (mHUPIProductID != null)
		{
			ddOrderLine.setM_HU_PI_Item_Product_ID(mHUPIProductID.getRepoId());
			ddOrderLine.setQtyEnteredTU(qtyEntered);

			final IHUPackingAware packingAware = new DDOrderLineHUPackingAware(ddOrderLine);

			huPackingAwareBL.setQtyCUFromQtyTU(packingAware, qtyEntered.intValue());
		}
		else
		{
			ddOrderLine.setQtyEntered(qtyEntered);
		}

		ddOrderLine.setQtyOrdered(ddOrderLine.getQtyEntered());

		InterfaceWrapperHelper.saveRecord(ddOrderLine);

		return DDOrderLineId.ofRepoId(ddOrderLine.getDD_OrderLine_ID());
	}

	@Override
	public void generateMovements(@NonNull final I_DD_Order ddOrder)
	{
		final DDOrderPickPlan plan = ddOrderPickFromService.createPlan(PickingPlanCreateRequest.builder()
				.ddOrder(ddOrder)
				.failIfNotFullAllocated(true)
				.build());

		final ImmutableList<DDOrderPickSchedule> schedules = ddOrderPickFromService.savePlan(plan);

		MovementsFromSchedulesGenerator.fromSchedules(schedules, this, ddOrderPickFromService)
				.skipCompletingDDOrder() // because usually this code is calling on after complete...
				.process();
	}

	@Override
	public boolean isCreateMovementOnComplete() {return sysConfigBL.getBooleanValue(SYSCONFIG_IsCreateMovementOnComplete, false);}

	@Override
	public Quantity getQtyToShip(final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt)
	{
		return ddOrderCoreBL.getQtyToShip(ddOrderLineOrAlt);
	}
}
