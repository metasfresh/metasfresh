package de.metas.distribution.ddorder.movement.generate;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveSchedule;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveSchedulePickedHU;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveSchedulePickedHUs;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.movement.generate.HUMovementGenerateRequest;
import de.metas.handlingunits.movement.generate.HUMovementGenerator;
import de.metas.handlingunits.movement.generate.HUMovementGeneratorResult;
import de.metas.handlingunits.movement.generate.HuIdsWithPackingMaterialsTransferred;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mmovement.MovementId;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.model.I_DD_Order;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;

public class DirectMovementsFromSchedulesGenerator
{
	// Services
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final DDOrderService ddOrderService;
	private final DDOrderMoveScheduleService ddOrderMoveScheduleService;

	// Parameters
	private final ImmutableList<DDOrderMoveSchedule> schedules;
	private final Instant movementDate = SystemTime.asInstant();
	private LocatorId locatorToIdOverride = null;
	private boolean skipCompletingDDOrder = false;

	// State
	private boolean executed = false;
	private final HuIdsWithPackingMaterialsTransferred huIdsWithPackingMaterialsTransferred = new HuIdsWithPackingMaterialsTransferred();
	private final DDOrdersCache ddOrdersCache;

	private DirectMovementsFromSchedulesGenerator(
			@NonNull final DDOrderService ddOrderService,
			@NonNull final DDOrderMoveScheduleService ddOrderMoveScheduleService,
			//
			@NonNull final List<DDOrderMoveSchedule> schedules)
	{
		Check.assumeNotEmpty(schedules, "schedules not empty");

		this.ddOrderService = ddOrderService;
		this.ddOrderMoveScheduleService = ddOrderMoveScheduleService;

		this.schedules = ImmutableList.copyOf(schedules);
		ddOrdersCache = new DDOrdersCache(ddOrderService);
	}

	public static DirectMovementsFromSchedulesGenerator fromSchedules(
			@NonNull final List<DDOrderMoveSchedule> schedules,
			@NonNull final DDOrderService ddOrderService,
			@NonNull final DDOrderMoveScheduleService ddOrderMoveScheduleService)
	{
		return new DirectMovementsFromSchedulesGenerator(ddOrderService, ddOrderMoveScheduleService, schedules);
	}

	public DirectMovementsFromSchedulesGenerator locatorToIdOverride(@Nullable final LocatorId locatorToIdOverride)
	{
		this.locatorToIdOverride = locatorToIdOverride;
		return this;
	}

	public DirectMovementsFromSchedulesGenerator skipCompletingDDOrder()
	{
		this.skipCompletingDDOrder = true;
		return this;
	}

	/**
	 * Do direct movements (e.g. skip the InTransit warehouse)
	 */
	public void generateDirectMovements()
	{
		trxManager.runInThreadInheritedTrx(this::generateDirectMovementsInTrx);
	}

	private void generateDirectMovementsInTrx()
	{
		trxManager.assertThreadInheritedTrxExists();
		markExecuted();

		schedules.forEach(this::generateDirectMovement);
	}

	private void markExecuted()
	{
		if (executed)
		{
			throw new AdempiereException("Already executed");
		}
		this.executed = true;
	}

	private void generateDirectMovement(final DDOrderMoveSchedule schedule)
	{
		//
		// Make sure DD Order is completed
		final DDOrderId ddOrderId = schedule.getDdOrderId();
		if (!skipCompletingDDOrder)
		{
			final I_DD_Order ddOrder = getDDOrderById(ddOrderId);
			ddOrderService.completeDDOrderIfNeeded(ddOrder);
		}

		schedule.assertNotPickedFrom();
		schedule.assertNotDroppedTo();

		final Quantity qtyToMove = schedule.getQtyToPick();
		final HuId huIdToMove = schedule.getPickFromHUId();

		final MovementId directMovementId = createDirectMovement(schedule, huIdToMove);

		schedule.markAsPickedFrom(
				null,
				DDOrderMoveSchedulePickedHUs.of(
						DDOrderMoveSchedulePickedHU.builder()
								.actualHUIdPicked(huIdToMove)
								.qtyPicked(qtyToMove)
								.pickFromMovementId(directMovementId)
								.inTransitLocatorId(null)
								.build())
		);
		schedule.markAsDroppedTo(directMovementId);
		ddOrderMoveScheduleService.save(schedule);
	}

	private MovementId createDirectMovement(
			final @NonNull DDOrderMoveSchedule schedule,
			final @NonNull HuId huIdToMove)
	{
		final HUMovementGeneratorResult result = new HUMovementGenerator(toMovementGenerateRequest(schedule, huIdToMove))
				.sharedHUIdsWithPackingMaterialsTransferred(huIdsWithPackingMaterialsTransferred)
				.createMovement();

		return result.getSingleMovementLineId().getMovementId();
	}

	private HUMovementGenerateRequest toMovementGenerateRequest(
			final @NonNull DDOrderMoveSchedule schedule,
			final @NonNull HuId huIdToMove)
	{
		final I_DD_Order ddOrder = ddOrdersCache.getById(schedule.getDdOrderId());

		return DDOrderMovementHelper.prepareMovementGenerateRequest(ddOrder, schedule.getDdOrderLineId())
				.movementDate(movementDate)
				.fromLocatorId(schedule.getPickFromLocatorId())
				.toLocatorId(locatorToIdOverride != null ? locatorToIdOverride : schedule.getDropToLocatorId())
				.huIdToMove(huIdToMove)
				.build();
	}

	private I_DD_Order getDDOrderById(final DDOrderId ddOrderId)
	{
		return ddOrdersCache.getById(ddOrderId);
	}
}
