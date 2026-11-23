package de.metas.frontend_testing.expectations;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.generichumodel.HUType;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
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
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inout.IInOutDAO;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.ShipmentScheduleAllocQuery;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.api.PickingSlotId;
import de.metas.process.AdProcessId;
import de.metas.document.engine.DocStatus;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@NonNull private final IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	@NonNull private final IArchiveDAO archiveDAO = Services.get(IArchiveDAO.class);
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
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

	/**
	 * @return the TU count of the packing instruction item (of the parent LU) via which the given HU is attached to its LU,
	 * or {@code null} if the HU has no parent item or that item has no packing instruction item
	 */
	@Nullable
	public Integer getParentPIItemQtyTUs(@NonNull final HuId huId)
	{
		final I_M_HU_Item parentItem = handlingUnitsDAO.retrieveParentItem(handlingUnitsBL.getById(huId));
		return parentItem != null && parentItem.getM_HU_PI_Item_ID() > 0
				? parentItem.getM_HU_PI_Item().getQty().intValueExact()
				: null;
	}

	public List<I_M_HU> getCUs(final HuId huId) {return handlingUnitsBL.getVHUs(huId);}

	public boolean isAllValid(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		return invalidationRepository.isAllValid(shipmentScheduleIds);
	}

	/**
	 * @return one entry per active picked-qty record ({@code M_ShipmentSchedule_QtyPicked}) of the given LU ({@code M_LU_HU_ID});
	 * {@code docStatus} is the {@code M_InOut.DocStatus} of the record's shipment line, or {@code null} if no shipment line exists yet.
	 */
	public List<ShipmentAllocation> getShipmentAllocationsByLU(@NonNull final HuId luId)
	{
		final List<I_M_ShipmentSchedule_QtyPicked> allocs = new ArrayList<>();
		// the query requires an explicit shipped / not-shipped selector, so we ask for both
		for (final boolean alreadyShipped : new boolean[] { true, false })
		{
			allocs.addAll(shipmentScheduleAllocDAO.list(
					I_M_ShipmentSchedule_QtyPicked.class,
					ShipmentScheduleAllocQuery.builder().onlyLUIds(ImmutableSet.of(luId)).alreadyShipped(alreadyShipped).build()));
		}

		final Set<InOutLineId> inOutLineIds = allocs.stream()
				.map(alloc -> InOutLineId.ofRepoIdOrNull(alloc.getM_InOutLine_ID()))
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		final Map<InOutLineId, I_M_InOutLine> linesById = inOutDAO.getLinesByIds(inOutLineIds, I_M_InOutLine.class).stream()
				.collect(Collectors.toMap(line -> InOutLineId.ofRepoId(line.getM_InOutLine_ID()), line -> line));
		final Set<InOutId> inOutIds = linesById.values().stream()
				.map(line -> InOutId.ofRepoId(line.getM_InOut_ID()))
				.collect(Collectors.toSet());
		final Map<InOutId, I_M_InOut> inOutsById = inOutDAO.getByIds(inOutIds, I_M_InOut.class).stream()
				.collect(Collectors.toMap(inout -> InOutId.ofRepoId(inout.getM_InOut_ID()), inout -> inout));

		final ArrayList<ShipmentAllocation> result = new ArrayList<>();
		for (final I_M_ShipmentSchedule_QtyPicked alloc : allocs)
		{
			final HuId tuId = HuId.ofRepoIdOrNull(alloc.getM_TU_HU_ID());
			final InOutLineId lineId = InOutLineId.ofRepoIdOrNull(alloc.getM_InOutLine_ID());
			final I_M_InOutLine line = lineId != null ? linesById.get(lineId) : null;
			final I_M_InOut inout = line != null ? inOutsById.get(InOutId.ofRepoId(line.getM_InOut_ID())) : null;
			result.add(new ShipmentAllocation(tuId, inout != null ? DocStatus.ofNullableCode(inout.getDocStatus()) : null));
		}
		return result;
	}

	@Value
	public static class ShipmentAllocation
	{
		/** {@code M_ShipmentSchedule_QtyPicked.M_TU_HU_ID}; may be null */
		@Nullable HuId tuId;
		/** {@code M_InOut.DocStatus} of the shipment; null if no shipment line yet */
		@Nullable DocStatus docStatus;

		public boolean isShipmentCompleted() {return docStatus != null && docStatus.isCompleted();}
	}

	/**
	 * @param processId if not {@code null}, only archives of this {@code AD_Archive.AD_Process_ID} are counted
	 */
	public int countArchivesOfHU(@NonNull final HuId huId, @Nullable final AdProcessId processId)
	{
		return archiveDAO.countArchives(TableRecordReference.of(I_M_HU.Table_Name, huId), processId);
	}
}
