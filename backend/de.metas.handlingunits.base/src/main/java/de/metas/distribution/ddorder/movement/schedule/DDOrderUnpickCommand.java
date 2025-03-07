package de.metas.distribution.ddorder.movement.schedule;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.movement.generate.DDOrderMovementHelper;
import de.metas.handlingunits.movement.HUIdAndQRCode;
import de.metas.handlingunits.movement.MoveHUCommand;
import de.metas.handlingunits.movement.MoveHURequestItem;
import de.metas.handlingunits.movement.generate.HUMovementGenerateRequest;
import de.metas.handlingunits.movement.generate.HUMovementGenerator;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.eevolution.model.I_DD_Order;

import javax.annotation.Nullable;
import java.time.Instant;

class DDOrderUnpickCommand
{
	@NonNull
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull
	private final DDOrderLowLevelDAO ddOrderLowLevelDAO;
	@NonNull
	private final DDOrderMoveScheduleRepository ddOrderMoveScheduleRepository;
	@NonNull
	private final HUQRCodesService huqrCodesService;

	// Params
	@NonNull
	private final Instant movementDate = SystemTime.asInstant();
	@NonNull
	private final DDOrderMoveScheduleId scheduleId;
	@Nullable
	private final HUQRCode unpickToTargetQRCode;

	// State
	private DDOrderMoveSchedule schedule;
	private I_DD_Order ddOrder;
	private LocatorId inTransitLocatorId;

	@Builder
	private DDOrderUnpickCommand(
			final @NonNull DDOrderLowLevelDAO ddOrderLowLevelDAO,
			final @NonNull DDOrderMoveScheduleRepository ddOrderMoveScheduleRepository,
			final @NonNull HUQRCodesService huqrCodesService,
			final @NonNull DDOrderMoveScheduleId scheduleId,
			final @Nullable HUQRCode unpickToTargetQRCode)
	{
		this.ddOrderLowLevelDAO = ddOrderLowLevelDAO;
		this.ddOrderMoveScheduleRepository = ddOrderMoveScheduleRepository;
		this.huqrCodesService = huqrCodesService;

		this.scheduleId = scheduleId;
		this.unpickToTargetQRCode = unpickToTargetQRCode;
	}

	public void execute()
	{
		trxManager.runInThreadInheritedTrx(this::executeInTrx);
	}

	private void executeInTrx()
	{
		trxManager.assertThreadInheritedTrxExists();

		loadState();
		validateState();

		//
		// generate movement InTransit -> Pick From Locator
		moveBackTheHU();

		deleteSchedule();
	}

	private void moveBackTheHU()
	{
		final HUMovementGenerateRequest request = DDOrderMovementHelper.prepareMovementGenerateRequest(ddOrder, schedule.getDdOrderLineId())
				.movementDate(movementDate)
				.fromLocatorId(inTransitLocatorId)
				.toLocatorId(schedule.getPickFromLocatorId())
				.huIdsToMove(ImmutableSet.of(schedule.getPickFromHUId()))
				.build();

		new HUMovementGenerator(request).createMovement();

		moveToTargetHUIfNeeded();
	}

	private void loadState()
	{
		schedule = ddOrderMoveScheduleRepository.getById(scheduleId);
		ddOrder = ddOrderLowLevelDAO.getById(schedule.getDdOrderId());
		final WarehouseId warehouseInTransitId = WarehouseId.ofRepoId(ddOrder.getM_Warehouse_ID());
		inTransitLocatorId = warehouseBL.getOrCreateDefaultLocatorId(warehouseInTransitId);
	}

	private void validateState()
	{
		if (!schedule.isPickedFrom())
		{
			throw new AdempiereException("Not picked");
		}

		if (schedule.isDropTo())
		{
			throw new AdempiereException("Already dropped!");
		}
	}

	private void deleteSchedule()
	{
		schedule.removePickedHUs();
		ddOrderMoveScheduleRepository.save(schedule);
		ddOrderMoveScheduleRepository.deleteNotStarted(schedule.getId());
	}

	private void moveToTargetHUIfNeeded()
	{
		if ( unpickToTargetQRCode == null)
		{
			return;
		}

		MoveHUCommand.builder()
				.huQRCodesService(huqrCodesService)
				.requestItems(ImmutableSet.of(MoveHURequestItem.ofHUIdAndQRCode(HUIdAndQRCode.ofHuId(schedule.getPickFromHUId()))))
				.targetQRCode(unpickToTargetQRCode.toGlobalQRCode())
				.build()
				.execute();
	}
}
