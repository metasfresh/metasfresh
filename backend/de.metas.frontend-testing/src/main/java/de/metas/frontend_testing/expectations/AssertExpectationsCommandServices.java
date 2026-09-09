package de.metas.frontend_testing.expectations;

import com.google.common.collect.ImmutableList;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.generichumodel.HUType;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InventoryLine;
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
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inventory.InventoryId;
import de.metas.picking.api.PickingSlotId;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.mmovement.MovementLineQuery;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AssertExpectationsCommandServices
{
	@NonNull private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	@NonNull private final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);
	@NonNull private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	@NonNull public final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IMovementDAO movementDAO = Services.get(IMovementDAO.class);
	@NonNull private final InventoryService inventoryService;
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

	public HUType getHUUnitType(@NonNull I_M_HU hu)
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
		return handlingUnitsBL.retrieveIncludedHUs(huId);
	}

	public List<I_M_HU> getCUs(final HuId huId) {return handlingUnitsBL.getVHUs(huId);}

	/**
	 * Inventory lines booked on exactly this HU. {@code retrieveAllLinesForHU} widens to included HUs,
	 * {@code M_InventoryLine_HU} and HU assignments, so the result is narrowed back to lines whose own
	 * {@code M_HU_ID} is this HU: an assertion must not be satisfied by an unrelated inventory elsewhere
	 * in the HU's graph.
	 * <p>
	 * {@code retrieveAllLinesForHU} loads the HU first, so a fixture naming an HU that does not exist fails
	 * loudly here instead of silently satisfying an {@code isExists: false} expectation.
	 */
	public List<I_M_InventoryLine> getInventoryLinesByHUId(@NonNull final HuId huId)
	{
		return inventoryService.retrieveAllLinesForHU(huId)
				.stream()
				.filter(line -> line.getM_HU_ID() == huId.getRepoId())
				.sorted(Comparator.comparing(I_M_InventoryLine::getM_InventoryLine_ID))
				.collect(ImmutableList.toImmutableList());
	}

	public Inventory getInventoryById(@NonNull final InventoryId inventoryId)
	{
		return inventoryService.getById(inventoryId);
	}

	/** The packing-material product of the HU; which packing material counts is the BL's decision, not the harness'. */
	public ProductId getPackingMaterialProductId(@NonNull final HuId huId)
	{
		return handlingUnitsBL.getFirstPackingMaterialProductId(huId)
				.orElseThrow(() -> new AdempiereException("HU has no packing material product").setParameter("huId", huId));
	}

	/**
	 * Whether a completed {@code M_Movement} exists with an {@code M_MovementLine} moving the given
	 * product from a locator of {@code fromWarehouseId} to a locator of {@code toWarehouseId}.
	 */
	public boolean hasCompletedMovementLine(
			@NonNull final ProductId productId,
			@NonNull final WarehouseId fromWarehouseId,
			@NonNull final WarehouseId toWarehouseId)
	{
		return movementDAO.getLineByQuery(MovementLineQuery.builder()
						.productId(productId)
						.fromLocatorIds(toLocatorIds(fromWarehouseId))
						.toLocatorIds(toLocatorIds(toWarehouseId))
						.movementDocStatus(DocStatus.Completed)
						.build())
				.isPresent();
	}

	private Set<LocatorId> toLocatorIds(@NonNull final WarehouseId warehouseId)
	{
		return warehouseBL.getLocatorIdsByWarehouseId(warehouseId);
	}
}
