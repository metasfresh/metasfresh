package de.metas.distribution.ddorder.movement.schedule;

import de.metas.common.util.time.SystemTime;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.movement.generate.DDOrderMovementHelper;
import de.metas.handlingunits.movement.generate.HUMovementGenerateRequest;
import de.metas.handlingunits.movement.generate.HUMovementGenerator;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mmovement.MovementId;
import org.eevolution.model.I_DD_Order;

import java.time.Instant;
import java.util.Objects;

class DDOrderDropToCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final DDOrderLowLevelDAO ddOrderLowLevelDAO;
	@NonNull private final DDOrderMoveScheduleRepository ddOrderMoveScheduleRepository;

	// Params
	@NonNull private final Instant movementDate = SystemTime.asInstant();
	@NonNull private final DDOrderMoveScheduleId scheduleId;

	@Builder
	private DDOrderDropToCommand(
			final @NonNull DDOrderLowLevelDAO ddOrderLowLevelDAO,
			final @NonNull DDOrderMoveScheduleRepository ddOrderMoveScheduleRepository,
			//
			final @NonNull DDOrderDropToRequest request)
	{
		this.ddOrderLowLevelDAO = ddOrderLowLevelDAO;
		this.ddOrderMoveScheduleRepository = ddOrderMoveScheduleRepository;

		this.scheduleId = request.getScheduleId();
	}

	public DDOrderMoveSchedule execute()
	{
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private DDOrderMoveSchedule executeInTrx()
	{
		trxManager.assertThreadInheritedTrxExists();

		final DDOrderMoveSchedule schedule = ddOrderMoveScheduleRepository.getById(scheduleId);
		schedule.assertNotDroppedTo();
		schedule.assertPickedFrom();

		//
		// generate movement InTransit -> DropTo Locator
		final MovementId dropToMovementId = createDropToMovement(schedule);

		//
		// update the schedule
		schedule.markAsDroppedTo(dropToMovementId);
		ddOrderMoveScheduleRepository.save(schedule);

		return schedule;
	}

	private MovementId createDropToMovement(@NonNull final DDOrderMoveSchedule schedule)
	{
		final DDOrderMoveSchedulePickedHUs pickedHUs = Objects.requireNonNull(schedule.getPickedHUs());

		final I_DD_Order ddOrder = ddOrderLowLevelDAO.getById(schedule.getDdOrderId());

		final HUMovementGenerateRequest request = DDOrderMovementHelper.prepareMovementGenerateRequest(ddOrder, schedule.getDdOrderLineId())
				.movementDate(movementDate)
				.fromLocatorId(pickedHUs.getInTransitLocatorId())
				.toLocatorId(schedule.getDropToLocatorId())
				.huIdsToMove(pickedHUs.getActualHUIdsPicked())
				.build();

		return new HUMovementGenerator(request)
				.createMovement()
				.getSingleMovementLineId()
				.getMovementId();
	}
}

