package de.metas.frontend_testing.expectations;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.generichumodel.HUType;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.slot.PickingSlotQueue;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.inout.IInOutDAO;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.picking.api.PickingSlotId;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AssertExpectationsCommandServices
{
	@NonNull private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	@NonNull private final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);
	@NonNull private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	@NonNull private final IShipmentScheduleInvalidateRepository invalidationRepository = Services.get(IShipmentScheduleInvalidateRepository.class);
	@NonNull public final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@NonNull private final IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	@NonNull private final IHUInOutDAO huInOutDAO = Services.get(IHUInOutDAO.class);
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final PickingJobService pickingJobService;
	@NonNull private final HUQRCodesService huQRCodeService;
	@NonNull private final PickingSlotService pickingSlotService;

	public PickingJob getPickingJobById(final PickingJobId pickingJobId)
	{
		return pickingJobService.getById(pickingJobId);
	}

	public Collection<I_M_ShipmentSchedule> getShipmentSchedulesByIds(final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		return shipmentScheduleBL.getByIds(shipmentScheduleIds).values();
	}

	public List<I_M_ShipmentSchedule_QtyPicked> getShipmentScheduleQtyPickedRecords(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		return shipmentScheduleAllocDAO.retrieveAllQtyPickedRecords(shipmentScheduleIds, I_M_ShipmentSchedule_QtyPicked.class);
	}

	public StockQtyAndUOMQty extractQtyPicked(@NonNull final I_M_ShipmentSchedule_QtyPicked alloc, @NonNull final ProductId productId)
	{
		return shipmentScheduleAllocBL.extractQtyPicked(alloc, productId);
	}

	public I_M_HU getHUById(@NonNull final HuId huId)
	{
		return handlingUnitsBL.getById(huId);
	}

	public HuId getHuIdByQRCode(@NonNull final HUQRCode qrCode)
	{
		return huQRCodeService.getHuIdByQRCode(qrCode);
	}

	public HUType getHUUnitType(@NonNull final I_M_HU hu)
	{
		return handlingUnitsBL.getHUUnitType(hu);
	}

	public IHUStorage getHUStorage(@NonNull final HuId huId)
	{
		return handlingUnitsBL.getStorageFactory().getStorage(handlingUnitsBL.getById(huId));
	}

	public IHUProductStorage getSingleProductStorage(@NonNull final I_M_HU hu)
	{
		return handlingUnitsBL.getSingleHUProductStorage(hu);
	}

	public ImmutableAttributeSet getAttributes(@NonNull final I_M_HU hu)
	{
		return handlingUnitsBL.getImmutableAttributeSet(hu);
	}

	public PickingSlotQueue getPickingSlotQueue(@NonNull final PickingSlotId pickingSlotId)
	{
		return pickingSlotService.getPickingSlotQueue(pickingSlotId);
	}

	public List<I_PP_Order_Qty> getPPOrderQtyForFinishedGoodsReceive(@NonNull final PPOrderId ppOrderId)
	{
		return huPPOrderQtyDAO.retrieveOrderQtyForFinishedGoodsReceive(ppOrderId);
	}

	public List<I_M_HU> getIncludedHUs(@NonNull final HuId huId)
	{
		return handlingUnitsDAO.retrieveIncludedHUs(huId);
	}

	public List<I_M_HU> getCUs(final HuId huId) {return handlingUnitsBL.getVHUs(huId);}

	public List<de.metas.handlingunits.model.I_M_InOutLine> getInOutLinesForHU(@NonNull final I_M_HU hu)
	{
		return huInOutDAO.retrieveInOutLinesForHU(hu);
	}

	/**
	 * Fresh <b>in-transaction</b> read of the SALES-shipment lines ({@code M_InOut.IsSOTrx=Y}) the given
	 * HU is currently assigned to.
	 *
	 * <p>Deliberately bypasses the out-of-transaction model cache (the documented {@code InTrx} cache-bypass
	 * pattern): the shipment and its {@code M_HU_Assignment} are created by the <i>async</i>
	 * shipment-generation workpackage in a SEPARATE transaction. An out-of-trx query (as used by the plain
	 * {@link #getInOutLinesForHU(I_M_HU)}) returns a <i>stale empty</i> result that was cached before the
	 * shipment existed, and the cache entry is not refreshed for the polling caller — so
	 * {@code assertShipped} would poll forever and never observe the committed assignment. Loading the HU
	 * and running the assignment query inside a fresh trx forces a committed DB read each call.
	 */
	@SuppressWarnings("deprecation") // intentional callInNewTrx — see the inline rationale below
	public List<de.metas.handlingunits.model.I_M_InOutLine> getSalesShipmentLinesForHUInTrx(@NonNull final HuId huId)
	{
		// callInNewTrx (a fresh trx, NOT the caller's): the assertShipped poll loop has no open
		// transaction, so there is no atomicity to break here. A fresh trx forces the HU + assignment
		// queries to read COMMITTED state and bypass the out-of-trx model cache (which otherwise returns
		// a stale empty result cached before the async shipment workpackage committed the assignment).
		// To remove this dedicated trx in future: give IHUInOutDAO.retrieveInOutLinesForHU /
		// IHandlingUnitsDAO.getById a documented cache-bypass overload and call that instead.
		return trxManager.callInNewTrx(() -> huInOutDAO.retrieveInOutLinesForHU(handlingUnitsDAO.getById(huId))
				.stream()
				.filter(line -> {
					final I_M_InOut inOut = line.getM_InOut();
					return inOut != null && inOut.isSOTrx();
				})
				.collect(Collectors.toList()));
	}

	public boolean isAllValid(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		return invalidationRepository.isAllValid(shipmentScheduleIds);
	}

	public List<I_M_InOut> getInOutsByOrderId(@NonNull final OrderId orderId)
	{
		return inOutDAO.retrieveInOutsByOrderId(orderId);
	}

	public List<I_M_InOutLine> getInOutLines(@NonNull final I_M_InOut inOut)
	{
		return inOutDAO.retrieveLines(inOut);
	}

	public Set<OrderLineId> getOrderLineIdsByOrderId(@NonNull final OrderId orderId)
	{
		return orderDAO.retrieveOrderLines(orderId)
				.stream()
				.map(line -> OrderLineId.ofRepoId(line.getC_OrderLine_ID()))
				.collect(Collectors.toSet());
	}

	public List<I_M_InOutLine> getProcessedShipmentLinesByOrderLineIds(@NonNull final Set<OrderLineId> orderLineIds)
	{
		return inOutDAO.retrieveProcessedLinesForOrderLineIds(orderLineIds);
	}
}
