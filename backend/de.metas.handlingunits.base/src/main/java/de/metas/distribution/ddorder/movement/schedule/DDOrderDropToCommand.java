package de.metas.distribution.ddorder.movement.schedule;

import de.metas.common.util.time.SystemTime;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.movement.generate.DDOrderMovementHelper;
import de.metas.handlingunits.movement.generate.HUMovementGenerateRequest;
import de.metas.handlingunits.movement.generate.HUMovementGenerator;
import de.metas.handlingunits.movement.generate.HUMovementGeneratorResult;
import de.metas.handlingunits.pporder.source_hu.PPOrderSourceHUService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mmovement.MovementId;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;

class DDOrderDropToCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final DDOrderLowLevelDAO ddOrderLowLevelDAO;
	@NonNull private final DDOrderMoveScheduleRepository ddOrderMoveScheduleRepository;
	@NonNull private final PPOrderSourceHUService ppOrderSourceHUService;

	// Params
	@NonNull private final Instant movementDate = SystemTime.asInstant();
	@NonNull private final Set<DDOrderMoveScheduleId> scheduleIds;
	@Nullable private final LocatorId dropToLocatorId;

	@Builder
	private DDOrderDropToCommand(
			final @NonNull DDOrderLowLevelDAO ddOrderLowLevelDAO,
			final @NonNull DDOrderMoveScheduleRepository ddOrderMoveScheduleRepository,
			final @NonNull PPOrderSourceHUService ppOrderSourceHUService,
			//
			final @NonNull DDOrderDropToRequest request)
	{
		this.ddOrderLowLevelDAO = ddOrderLowLevelDAO;
		this.ddOrderMoveScheduleRepository = ddOrderMoveScheduleRepository;
		this.ppOrderSourceHUService = ppOrderSourceHUService;

		this.scheduleIds = request.getScheduleIds();
		this.dropToLocatorId = request.getDropToLocatorId();
	}

	public List<DDOrderMoveSchedule> execute()
	{
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private List<DDOrderMoveSchedule> executeInTrx()
	{
		return ddOrderMoveScheduleRepository.updateByIds(scheduleIds, this::processSchedule);
	}

	private void processSchedule(final DDOrderMoveSchedule schedule)
	{
		schedule.assertInTransit();

		//
		// generate movement InTransit -> DropTo Locator
		final LocatorId dropToLocatorId = this.dropToLocatorId != null ? this.dropToLocatorId : schedule.getDropToLocatorId();
		final MovementId dropToMovementId = createDropToMovement(schedule, dropToLocatorId);

		//
		// update the schedule
		schedule.markAsDroppedTo(dropToLocatorId, dropToMovementId);
	}

	private MovementId createDropToMovement(@NonNull final DDOrderMoveSchedule schedule, @NonNull final LocatorId dropToLocatorId)
	{
		final DDOrderMoveSchedulePickedHUs pickedHUs = Objects.requireNonNull(schedule.getPickedHUs());

		final I_DD_Order ddOrder = ddOrderLowLevelDAO.getById(schedule.getDdOrderId());

		final HUMovementGenerateRequest request = DDOrderMovementHelper.prepareMovementGenerateRequest(ddOrder, schedule.getDdOrderLineId())
				.movementDate(movementDate)
				.fromLocatorId(pickedHUs.getInTransitLocatorId())
				.toLocatorId(dropToLocatorId)
				.huIdsToMove(pickedHUs.getActualHUIdsPicked())
				.build();

		final HUMovementGeneratorResult result = new HUMovementGenerator(request).createMovement();

		if (ddOrder.getForward_PP_Order_ID() > 0)
		{
			reserveHUsForManufacturing(PPOrderId.ofRepoId(ddOrder.getForward_PP_Order_ID()), result);
		}

		return result.getSingleMovementLineId().getMovementId();
	}

	private void reserveHUsForManufacturing(@NonNull final PPOrderId ppOrderId, @NonNull final HUMovementGeneratorResult result)
	{
		ppOrderSourceHUService.addSourceHUs(ppOrderId, result.getMovedHUIds());
	}
}

