package de.metas.distribution.ddorder.movement.schedule;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.movement.generate.DDOrderMovementHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.movement.generate.HUMovementGenerateRequest;
import de.metas.handlingunits.movement.generate.HUMovementGenerator;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.eevolution.model.I_DD_Order;

import java.time.Instant;

class DDOrderReversePickCommand
{
	@NonNull
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull
	private final DDOrderLowLevelDAO ddOrderLowLevelDAO;
	@NonNull
	private final DDOrderMoveScheduleRepository ddOrderMoveScheduleRepository;

	// Params
	@NonNull
	private final Instant movementDate = SystemTime.asInstant();
	@NonNull
	private final DDOrderMoveScheduleId scheduleId;

	// State
	private DDOrderMoveSchedule schedule;
	private I_DD_Order ddOrder;
	private LocatorId inTransitLocatorId;

	@Builder
	private DDOrderReversePickCommand(
			final @NonNull DDOrderLowLevelDAO ddOrderLowLevelDAO,
			final @NonNull DDOrderMoveScheduleRepository ddOrderMoveScheduleRepository,
			final @NonNull DDOrderMoveScheduleId scheduleId)
	{
		this.ddOrderLowLevelDAO = ddOrderLowLevelDAO;
		this.ddOrderMoveScheduleRepository = ddOrderMoveScheduleRepository;

		this.scheduleId = scheduleId;
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
		createMovement(schedule.getPickFromHUId());

		deleteSchedule();
	}

	private void createMovement(@NonNull final HuId huId)
	{
		final HUMovementGenerateRequest request = DDOrderMovementHelper.prepareMovementGenerateRequest(ddOrder, schedule.getDdOrderLineId())
				.movementDate(movementDate)
				.fromLocatorId(inTransitLocatorId)
				.toLocatorId(schedule.getPickFromLocatorId())
				.huIdsToMove(ImmutableSet.of(huId))
				.build();

		new HUMovementGenerator(request).createMovement();
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
}
