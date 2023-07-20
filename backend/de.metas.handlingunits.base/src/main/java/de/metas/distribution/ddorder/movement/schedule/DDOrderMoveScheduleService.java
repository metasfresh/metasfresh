package de.metas.distribution.ddorder.movement.schedule;

import com.google.common.collect.ImmutableList;
import de.metas.ad_reference.ADReferenceService;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.movement.schedule.plan.DDOrderMovePlan;
import de.metas.distribution.ddorder.movement.schedule.plan.DDOrderMovePlanCreateCommand;
import de.metas.distribution.ddorder.movement.schedule.plan.DDOrderMovePlanCreateRequest;
import de.metas.distribution.ddorder.movement.schedule.plan.DDOrderMovePlanStep;
import de.metas.distribution.ddorder.movement.schedule.plan.DDOrderMovePlanLine;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import de.metas.ad_reference.ADRefList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class DDOrderMoveScheduleService
{
	private final DDOrderLowLevelDAO ddOrderLowLevelDAO;
	private final DDOrderMoveScheduleRepository ddOrderMoveScheduleRepository;
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final ADReferenceService adReferenceService;

	private final HUReservationService huReservationService;

	public DDOrderMoveScheduleService(
			@NonNull final DDOrderLowLevelDAO ddOrderLowLevelDAO,
			@NonNull final DDOrderMoveScheduleRepository ddOrderMoveScheduleRepository,
			@NonNull final ADReferenceService adReferenceService,
			@NonNull final HUReservationService huReservationService)
	{
		this.ddOrderLowLevelDAO = ddOrderLowLevelDAO;
		this.ddOrderMoveScheduleRepository = ddOrderMoveScheduleRepository;
		this.adReferenceService = adReferenceService;
		this.huReservationService = huReservationService;
	}

	public ADRefList getQtyRejectedReasons()
	{
		return adReferenceService.getRefListById(QtyRejectedReasonCode.REFERENCE_ID);
	}

	public void createScheduleToMove(@NonNull final DDOrderMoveScheduleCreateRequest request)
	{
		ddOrderMoveScheduleRepository.createScheduleToMove(request);
	}

	public ImmutableList<DDOrderMoveSchedule> createScheduleToMoveBulk(@NonNull final List<DDOrderMoveScheduleCreateRequest> requests)
	{
		return ddOrderMoveScheduleRepository.createScheduleToMoveBulk(requests);
	}

	public ImmutableList<DDOrderMoveSchedule> getSchedules(@NonNull final DDOrderId ddOrderId) {return ddOrderMoveScheduleRepository.getSchedules(ddOrderId);}

	public DDOrderMoveSchedule getScheduleById(@NonNull final DDOrderMoveScheduleId id) {return ddOrderMoveScheduleRepository.getById(id);}

	public void removeNotStarted(@NonNull final DDOrderId ddOrderId)
	{
		ddOrderMoveScheduleRepository.removeNotStarted(ddOrderId);
	}

	public boolean hasInProgressSchedules(@NonNull final DDOrderId ddOrderId)
	{
		return ddOrderMoveScheduleRepository.hasInProgressSchedules(ddOrderId);
	}

	public void removeNotStarted(@NonNull final DDOrderLineId ddOrderLineId)
	{
		ddOrderMoveScheduleRepository.removeNotStarted(ddOrderLineId);
	}

	public boolean hasInProgressSchedules(@NonNull final DDOrderLineId ddOrderLineId)
	{
		return ddOrderMoveScheduleRepository.hasInProgressSchedules(ddOrderLineId);
	}

	public void removeFromHUsScheduledToPickList(final DDOrderLineId ddOrderLineId, final Set<HuId> huIdsToUnAssign)
	{
		ddOrderMoveScheduleRepository.removeFromHUsScheduledToMoveList(ddOrderLineId, huIdsToUnAssign);
	}

	public IQueryFilter<I_M_HU> getHUsNotAlreadyScheduledToMoveFilter()
	{
		return ddOrderMoveScheduleRepository.getHUsNotAlreadyScheduledToMoveFilter();
	}

	public boolean isScheduledToMove(final HuId huId)
	{
		return ddOrderMoveScheduleRepository.isScheduledToMove(huId);
	}

	public List<HuId> retrieveHUIdsScheduledButNotMovedYet(final DDOrderLineId ddOrderLineId)
	{
		return ddOrderMoveScheduleRepository.retrieveHUIdsScheduledButNotMovedYet(ddOrderLineId);
	}

	public void save(@NonNull final DDOrderMoveSchedule schedule)
	{
		ddOrderMoveScheduleRepository.save(schedule);
	}

	public DDOrderMovePlan createPlan(@NonNull final DDOrderMovePlanCreateRequest request)
	{
		return DDOrderMovePlanCreateCommand.builder()
				.ddOrderLowLevelDAO(ddOrderLowLevelDAO)
				.huReservationService(huReservationService)
				.request(request)
				.build().execute();
	}

	public ImmutableList<DDOrderMoveSchedule> savePlan(@NonNull final DDOrderMovePlan plan)
	{
		return createScheduleToMoveBulk(toScheduleToMoveRequest(plan));
	}

	private static ImmutableList<DDOrderMoveScheduleCreateRequest> toScheduleToMoveRequest(@NonNull final DDOrderMovePlan plan)
	{
		final DDOrderId ddOrderId = plan.getDdOrderId();
		return plan.getLines()
				.stream()
				.flatMap(planLine -> toScheduleToMoveRequest(planLine, ddOrderId))
				.collect(ImmutableList.toImmutableList());
	}

	private static Stream<DDOrderMoveScheduleCreateRequest> toScheduleToMoveRequest(
			@NonNull final DDOrderMovePlanLine planLine,
			@NonNull final DDOrderId ddOrderId)
	{
		final DDOrderLineId ddOrderLineId = planLine.getDdOrderLineId();
		return planLine.getSteps()
				.stream()
				.map(planStep -> toScheduleToMoveRequest(planStep, ddOrderId, ddOrderLineId));
	}

	private static DDOrderMoveScheduleCreateRequest toScheduleToMoveRequest(
			@NonNull final DDOrderMovePlanStep planStep,
			@NonNull final DDOrderId ddOrderId,
			@NonNull final DDOrderLineId ddOrderLineId)
	{
		return DDOrderMoveScheduleCreateRequest.builder()
				.ddOrderId(ddOrderId)
				.ddOrderLineId(ddOrderLineId)
				.productId(planStep.getProductId())
				//
				// Pick From
				.pickFromLocatorId(planStep.getPickFromLocatorId())
				.pickFromHUId(planStep.getPickFromHUId())
				.qtyToPick(planStep.getQtyToPick())
				.isPickWholeHU(planStep.isPickWholeHU())
				//
				// Drop To
				.dropToLocatorId(planStep.getDropToLocatorId())
				//
				.build();
	}

	public DDOrderMoveSchedule pickFromHU(@NonNull final DDOrderPickFromRequest request)
	{
		return DDOrderPickFromCommand.builder()
				.ddOrderLowLevelDAO(ddOrderLowLevelDAO)
				.ddOrderMoveScheduleRepository(ddOrderMoveScheduleRepository)
				.handlingUnitsBL(handlingUnitsBL)
				.request(request)
				.build()
				.execute();
	}

	public DDOrderMoveSchedule dropTo(@NonNull final DDOrderDropToRequest request)
	{
		return DDOrderDropToCommand.builder()
				.ddOrderLowLevelDAO(ddOrderLowLevelDAO)
				.ddOrderMoveScheduleRepository(ddOrderMoveScheduleRepository)
				.request(request)
				.build()
				.execute();
	}

}
