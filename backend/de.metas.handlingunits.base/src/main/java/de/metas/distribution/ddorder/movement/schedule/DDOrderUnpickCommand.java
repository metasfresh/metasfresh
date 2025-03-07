package de.metas.distribution.ddorder.movement.schedule;

import com.google.common.collect.ImmutableSet;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.handlingunits.movement.HUIdAndQRCode;
import de.metas.handlingunits.movement.MoveHUCommand;
import de.metas.handlingunits.movement.MoveHURequestItem;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.api.IWarehouseBL;

import javax.annotation.Nullable;

class DDOrderUnpickCommand
{
	@NonNull
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull
	private final DDOrderMoveScheduleRepository ddOrderMoveScheduleRepository;
	@NonNull
	private final HUQRCodesService huqrCodesService;

	// Params
	@NonNull
	private final DDOrderMoveScheduleId scheduleId;
	@Nullable
	private final HUQRCode unpickToTargetQRCode;

	// State
	private DDOrderMoveSchedule schedule;

	@Builder
	private DDOrderUnpickCommand(
			final @NonNull DDOrderMoveScheduleRepository ddOrderMoveScheduleRepository,
			final @NonNull HUQRCodesService huqrCodesService,
			final @NonNull DDOrderMoveScheduleId scheduleId,
			final @Nullable HUQRCode unpickToTargetQRCode)
	{
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
		MoveHUCommand.builder()
				.huQRCodesService(huqrCodesService)
				.requestItems(ImmutableSet.of(MoveHURequestItem.ofHUIdAndQRCode(HUIdAndQRCode.ofHuId(schedule.getPickFromHUId()))))
				.targetQRCode(getTargetQRCode())
				.build()
				.execute();
	}

	private void loadState()
	{
		schedule = ddOrderMoveScheduleRepository.getById(scheduleId);
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

	private GlobalQRCode getTargetQRCode()
	{
		if (unpickToTargetQRCode != null)
		{
			return unpickToTargetQRCode.toGlobalQRCode();
		}
		else
		{
			return warehouseBL.getLocatorQRCode(schedule.getPickFromLocatorId()).toGlobalQRCode();
		}
	}
}
