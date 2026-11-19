package de.metas.frontend_testing.expectations;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.generichumodel.HUType;
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
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.api.PickingSlotId;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Archive;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
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
	@NonNull private final IShipmentScheduleInvalidateRepository invalidationRepository = Services.get(IShipmentScheduleInvalidateRepository.class);
	@NonNull public final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@NonNull private final IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
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

	public boolean isAllValid(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		return invalidationRepository.isAllValid(shipmentScheduleIds);
	}

	/**
	 * @return the {@code M_InOut.DocStatus} of every shipment line that the picked-qty records of the given LU point to;
	 * an allocation without a shipment line yet (shipment not generated) is reported as {@code null} entry.
	 */
	public List<String> getShipmentDocStatusesByLU(@NonNull final HuId luId)
	{
		final List<I_M_ShipmentSchedule_QtyPicked> allocs = queryBL.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_LU_HU_ID, luId)
				.create()
				.list();

		final java.util.ArrayList<String> result = new java.util.ArrayList<>();
		for (final I_M_ShipmentSchedule_QtyPicked alloc : allocs)
		{
			if (alloc.getM_InOutLine_ID() <= 0)
			{
				result.add(null);
				continue;
			}
			final I_M_InOutLine line = InterfaceWrapperHelper.load(alloc.getM_InOutLine_ID(), I_M_InOutLine.class);
			final I_M_InOut inout = InterfaceWrapperHelper.load(line.getM_InOut_ID(), I_M_InOut.class);
			result.add(inout.getDocStatus());
		}
		return result;
	}

	public int countArchivesOfHU(@NonNull final HuId huId)
	{
		return queryBL.createQueryBuilder(I_AD_Archive.class)
				.addEqualsFilter(I_AD_Archive.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_M_HU.class))
				.addEqualsFilter(I_AD_Archive.COLUMNNAME_Record_ID, huId)
				.create()
				.count();
	}
}
