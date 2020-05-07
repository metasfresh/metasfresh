package de.metas.inoutcandidate.process;

import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.ShipmentScheduleUpdateInvalidRequest;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Services;

public final class M_ShipmentSchedule_Update extends JavaProcess
{
	private final IShipmentScheduleUpdater shipmentScheduleUpdater = Services.get(IShipmentScheduleUpdater.class);

	public static final String PARAM_IsFullUpdate = "IsFullUpdate";
	@Param(parameterName = PARAM_IsFullUpdate)
	private boolean p_IsFullUpdate = false;

	@Override
	protected String doIt()
	{
		if (p_IsFullUpdate)
		{
			final IShipmentScheduleInvalidateRepository invalidSchedulesRepo = Services.get(IShipmentScheduleInvalidateRepository.class);
			invalidSchedulesRepo.invalidateAll(getCtx());
		}

		final int result = shipmentScheduleUpdater.updateShipmentSchedules(ShipmentScheduleUpdateInvalidRequest.builder()
				.ctx(getCtx())
				.selectionId(getPinstanceId())
				.createMissingShipmentSchedules(true)
				.build());

		return "Updated " + result + " shipment schedule entries";
	}
}
