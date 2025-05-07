package de.metas.frontend_testing.expectations;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AssertExpectationsCommandServices
{
	@NonNull private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	@NonNull final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final PickingJobService pickingJobService;
	@NonNull private final HUQRCodesService huQRCodeService;

	public PickingJob getPickingJobById(final PickingJobId pickingJobId)
	{
		return pickingJobService.getById(pickingJobId);
	}

	public Collection<I_M_ShipmentSchedule> getShipmentSchedulesByIds(final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		return shipmentSchedulePA.getByIds(shipmentScheduleIds).values();
	}

	public HuId getHuIdByQRCode(@NonNull final HUQRCode qrCode)
	{
		return huQRCodeService.getHuIdByQRCode(qrCode);
	}

	public IHUStorage getHUStorage(@NonNull final HuId huId)
	{
		return handlingUnitsBL.getStorageFactory().getStorage(handlingUnitsBL.getById(huId));
	}
}
