package de.metas.distribution.ddorder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.gui.search.IHUPackingAware;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.distribution.ddorder.lowlevel.model.DDOrderLineHUPackingAware;
import de.metas.distribution.ddorder.lowlevel.model.I_DD_OrderLine_Or_Alternative;
import de.metas.distribution.ddorder.movement.generate.DirectMovementsFromSchedulesGenerator;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveSchedule;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.distribution.ddorder.movement.schedule.generate_from_hu.SchedulesFromHUsGeneratorFactory;
import de.metas.distribution.ddorder.movement.schedule.plan.DDOrderMovePlan;
import de.metas.distribution.ddorder.movement.schedule.plan.DDOrderMovePlanCreateRequest;
import de.metas.distribution.ddorder.producer.HUToDistribute;
import de.metas.distribution.ddorder.producer.HUs2DDOrderProducer;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.X_DD_OrderLine;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
public class DDOrderService
{
	private static final Logger logger = LogManager.getLogger(DDOrderService.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IHUPackingAwareBL huPackingAwareBL = Services.get(IHUPackingAwareBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final DDOrderLowLevelDAO ddOrderLowLevelDAO;
	private final DDOrderLowLevelService ddOrderLowLevelService;
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
	private final IHUInOutDAO huInOutDAO = Services.get(IHUInOutDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final DDOrderMoveScheduleService ddOrderMoveScheduleService;
	private final SchedulesFromHUsGeneratorFactory schedulesFromHUsGeneratorFactory;

	private static final String SYSCONFIG_IsCreateMovementOnComplete = "DDOrder_isCreateMovementOnComplete";

	public DDOrderService(
			@NonNull final DDOrderLowLevelDAO ddOrderLowLevelDAO,
			@NonNull final DDOrderLowLevelService ddOrderLowLevelService,
			@NonNull final DDOrderMoveScheduleService ddOrderMoveScheduleService)
	{
		this.ddOrderLowLevelDAO = ddOrderLowLevelDAO;
		this.ddOrderLowLevelService = ddOrderLowLevelService;
		this.ddOrderMoveScheduleService = ddOrderMoveScheduleService;
		this.schedulesFromHUsGeneratorFactory = new SchedulesFromHUsGeneratorFactory(ddOrderMoveScheduleService, this);
	}

	public I_DD_Order getById(final DDOrderId ddOrderId)
	{
		return ddOrderLowLevelDAO.getById(ddOrderId);
	}

	public Stream<I_DD_Order> streamDDOrders(final DDOrderQuery query)
	{
		return ddOrderLowLevelDAO.streamDDOrders(query);
	}

	public void save(final I_DD_Order ddOrder)
	{
		ddOrderLowLevelDAO.save(ddOrder);
	}

	public List<I_DD_OrderLine> retrieveLines(final I_DD_Order order)
	{
		return ddOrderLowLevelDAO.retrieveLines(order);
	}

	public I_DD_OrderLine getLineById(final DDOrderLineId ddOrderLineId)
	{
		return ddOrderLowLevelDAO.getLineById(ddOrderLineId);
	}

	public List<I_DD_OrderLine_Alternative> retrieveAllAlternatives(final I_DD_OrderLine ddOrderLine)
	{
		return ddOrderLowLevelDAO.retrieveAllAlternatives(ddOrderLine);
	}

	public void completeDDOrderIfNeeded(final I_DD_Order ddOrder)
	{
		ddOrderLowLevelService.completeDDOrderIfNeeded(ddOrder);
	}

	public SchedulesFromHUsGeneratorFactory prepareAllocateFullHUsAndMove()
	{
		return schedulesFromHUsGeneratorFactory;
	}

	public void close(@NonNull final DDOrderId ddOrderId)
	{
		final I_DD_Order ddOrder = getById(ddOrderId);
		documentBL.processEx(ddOrder, IDocument.ACTION_Close, IDocument.STATUS_Closed);
	}

	public void closeLine(final I_DD_OrderLine ddOrderLine)
	{
		final DDOrderLineId ddOrderLineId = DDOrderLineId.ofRepoId(ddOrderLine.getDD_OrderLine_ID());
		if (ddOrderMoveScheduleService.hasInProgressSchedules(ddOrderLineId))
		{
			throw new AdempiereException("Cannot close a line when there are schedules in progress");
		}

		ddOrderLine.setIsDelivered_Override(X_DD_OrderLine.ISDELIVERED_OVERRIDE_Yes);
		InterfaceWrapperHelper.save(ddOrderLine);

		ddOrderMoveScheduleService.removeNotStarted(ddOrderLineId);
	}

	public void unassignHUs(@NonNull final DDOrderLineId ddOrderLineId, @NonNull final Set<HuId> huIdsToUnassign)
	{
		//
		// Unassign the given HUs from DD_OrderLine
		huAssignmentBL.unassignHUs(TableRecordReference.of(I_DD_OrderLine.Table_Name, ddOrderLineId), huIdsToUnassign);

		//
		// Remove those HUs from scheduled to move list (task 08639)
		ddOrderMoveScheduleService.removeFromHUsScheduledToPickList(ddOrderLineId, huIdsToUnassign);
	}

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
			final Optional<I_DD_Order> ddOrder = HUs2DDOrderProducer.newProducer(ddOrderMoveScheduleService)
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

		final de.metas.distribution.ddorder.lowlevel.model.I_DD_OrderLine ddOrderLine =
				InterfaceWrapperHelper.newInstance(de.metas.distribution.ddorder.lowlevel.model.I_DD_OrderLine.class, ddOrder);

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

	public void generateDirectMovements(@NonNull final I_DD_Order ddOrder)
	{
		final DDOrderMovePlan plan = ddOrderMoveScheduleService.createPlan(DDOrderMovePlanCreateRequest.builder()
				.ddOrder(ddOrder)
				.failIfNotFullAllocated(true)
				.build());

		final ImmutableList<DDOrderMoveSchedule> schedules = ddOrderMoveScheduleService.savePlan(plan);

		DirectMovementsFromSchedulesGenerator.fromSchedules(schedules, this, ddOrderMoveScheduleService)
				.skipCompletingDDOrder() // because usually this code is calling on after complete...
				.generateDirectMovements();
	}

	public boolean isCreateMovementOnComplete() {return sysConfigBL.getBooleanValue(SYSCONFIG_IsCreateMovementOnComplete, false);}

	public Quantity getQtyToShip(final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt)
	{
		return ddOrderLowLevelService.getQtyToShip(ddOrderLineOrAlt);
	}

	public void assignToResponsible(@NonNull final I_DD_Order ddOrder, @NonNull final UserId responsibleId)
	{
		final UserId currentResponsibleId = extractCurrentResponsibleId(ddOrder);
		if (currentResponsibleId == null)
		{
			ddOrder.setAD_User_Responsible_ID(responsibleId.getRepoId());
			save(ddOrder);
		}
		else if (!UserId.equals(currentResponsibleId, responsibleId))
		{
			throw new AdempiereException("DD Order already assigned to a different responsible");
		}
		else
		{
			// already assigned to that responsible,
			// shall not happen but we can safely ignore the case
			logger.warn("Order {} already assigned to {}", ddOrder.getDD_Order_ID(), responsibleId);
		}
	}

	@Nullable
	private static UserId extractCurrentResponsibleId(final I_DD_Order ddOrder)
	{
		return UserId.ofRepoIdOrNullIfSystem(ddOrder.getAD_User_Responsible_ID());
	}

	public void unassignFromResponsible(final DDOrderId ddOrderId)
	{
		final I_DD_Order ddOrder = getById(ddOrderId);
		unassignFromResponsibleAndSave(ddOrder);
	}

	private void unassignFromResponsibleAndSave(final I_DD_Order ddOrder)
	{
		ddOrder.setAD_User_Responsible_ID(-1);
		save(ddOrder);
	}
}
