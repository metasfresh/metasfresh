package de.metas.frontend_testing.expectations;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.pair.IPair;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.generichumodel.HUType;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
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
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.picking.api.PickingSlotId;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.document.engine.IDocument;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_M_Product;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Component;

import java.util.Collection;
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
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@NonNull private final IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	@NonNull private final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
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
		return handlingUnitsDAO.retrieveIncludedHUs(huId);
	}

	public List<I_M_HU> getCUs(final HuId huId) {return handlingUnitsBL.getVHUs(huId);}

	/**
	 * Inventory lines booked on exactly this HU. Deliberately the narrow lookup, not
	 * {@code retrieveAllLinesForHU}: an assertion must not be satisfied by an unrelated inventory that
	 * touched an included HU or an HU assignment. The repository owns the query; do not hand-roll one here.
	 */
	public List<I_M_InventoryLine> getInventoryLinesByHUId(@NonNull final HuId huId)
	{
		return inventoryService.getInventoryRepository().retrieveLinesByHUId(huId);
	}

	public I_M_Inventory getInventoryById(@NonNull final InventoryId inventoryId)
	{
		return inventoryDAO.getById(inventoryId);
	}

	/**
	 * The product of the FIRST packing material carried by the given HU (e.g. the crate/pallet product
	 * an {@code M_HU_PackingMaterial} attaches to a packing-instruction-produced TU).
	 */
	public ProductId getPackingMaterialProductId(@NonNull final HuId huId)
	{
		final I_M_HU hu = getHUById(huId);
		final I_M_HU_PackingMaterial packingMaterial = handlingUnitsDAO.retrievePackingMaterialAndQtys(hu)
				.stream()
				.findFirst()
				.map(IPair::getLeft)
				.orElseThrow(() -> new AdempiereException("HU has no packing material").setParameter("huId", huId));

		final I_M_Product product = IHUPackingMaterialDAO.extractProductOrNull(packingMaterial);
		if (product == null)
		{
			throw new AdempiereException("Packing material has no product").setParameter("huId", huId);
		}
		return ProductId.ofRepoId(product.getM_Product_ID());
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
		final Set<Integer> fromLocatorRepoIds = toRepoIds(warehouseDAO.getLocatorIds(fromWarehouseId));
		final Set<Integer> toLocatorRepoIds = toRepoIds(warehouseDAO.getLocatorIds(toWarehouseId));

		final IQuery<I_M_Movement> completedMovementQuery = queryBL.createQueryBuilder(I_M_Movement.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Movement.COLUMNNAME_DocStatus, IDocument.STATUS_Completed)
				.create();

		return queryBL.createQueryBuilder(I_M_MovementLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_MovementLine.COLUMNNAME_M_Product_ID, productId.getRepoId())
				.addInArrayFilter(I_M_MovementLine.COLUMNNAME_M_Locator_ID, fromLocatorRepoIds)
				.addInArrayFilter(I_M_MovementLine.COLUMNNAME_M_LocatorTo_ID, toLocatorRepoIds)
				.addInSubQueryFilter(I_M_MovementLine.COLUMNNAME_M_Movement_ID, I_M_Movement.COLUMNNAME_M_Movement_ID, completedMovementQuery)
				.create()
				.anyMatch();
	}

	private static Set<Integer> toRepoIds(@NonNull final List<LocatorId> locatorIds)
	{
		return locatorIds.stream().map(LocatorId::getRepoId).collect(ImmutableSet.toImmutableSet());
	}
}
