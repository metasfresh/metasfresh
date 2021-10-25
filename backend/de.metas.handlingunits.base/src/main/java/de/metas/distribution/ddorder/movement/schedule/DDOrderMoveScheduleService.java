package de.metas.distribution.ddorder.movement.schedule;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.movement.schedule.plan.DDOrderMovePlan;
import de.metas.distribution.ddorder.movement.schedule.plan.DDOrderMovePlanCreateCommand;
import de.metas.distribution.ddorder.movement.schedule.plan.DDOrderMovePlanCreateRequest;
import de.metas.distribution.ddorder.movement.schedule.plan.DDOrderMovePlanLine;
import de.metas.distribution.ddorder.movement.schedule.plan.DDOrderMovePlanStep;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.service.IADReferenceDAO;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
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
	private final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);

	public DDOrderMoveScheduleService(
			@NonNull final DDOrderLowLevelDAO ddOrderLowLevelDAO,
			@NonNull final DDOrderMoveScheduleRepository ddOrderMoveScheduleRepository)
	{
		this.ddOrderLowLevelDAO = ddOrderLowLevelDAO;
		this.ddOrderMoveScheduleRepository = ddOrderMoveScheduleRepository;
	}

	public IADReferenceDAO.ADRefList getQtyRejectedReasons()
	{
		return adReferenceDAO.getRefListById(QtyRejectedReasonCode.REFERENCE_ID);
	}

	public void addScheduleToMove(@NonNull final DDOrderMoveScheduleCreateRequest request)
	{
		ddOrderMoveScheduleRepository.addScheduleToMove(request);
	}

	public ImmutableList<DDOrderMoveSchedule> addScheduleToMoveBulk(@NonNull final List<DDOrderMoveScheduleCreateRequest> requests)
	{
		return ddOrderMoveScheduleRepository.addScheduleToMoveBulk(requests);
	}

	public ImmutableList<DDOrderMoveSchedule> getSchedules(@NonNull final DDOrderId ddOrderId) {return ddOrderMoveScheduleRepository.getSchedules(ddOrderId);}

	public void removeDraftSchedules(@NonNull final DDOrderId ddOrderId)
	{
		// FIXME: only Draft ones!
		ddOrderMoveScheduleRepository.removeAllSchedulesForOrder(ddOrderId);
	}

	public void removeDraftSchedules(@NonNull final DDOrderLineId ddOrderLineId)
	{
		// FIXME: only Draft ones!
		ddOrderMoveScheduleRepository.removeAllSchedulesForLine(ddOrderLineId);
	}

	public void removeAllSchedulesForLine(@NonNull final DDOrderLineId ddOrderLineId)
	{
		ddOrderMoveScheduleRepository.removeAllSchedulesForLine(ddOrderLineId);
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

	public List<HuId> retrieveHUIdsScheduledToMove(final DDOrderLineId ddOrderLineId)
	{
		// FIXME: only Draft ones
		return ddOrderMoveScheduleRepository.retrieveHUIdsScheduledToMove(ddOrderLineId);
	}

	public DDOrderMovePlan createPlan(@NonNull final DDOrderMovePlanCreateRequest request)
	{
		return DDOrderMovePlanCreateCommand.builder()
				.ddOrderLowLevelDAO(ddOrderLowLevelDAO)
				.request(request)
				.build().execute();
	}

	public ImmutableList<DDOrderMoveSchedule> savePlan(@NonNull final DDOrderMovePlan plan)
	{
		return addScheduleToMoveBulk(toScheduleToMoveRequest(plan));
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
			@NonNull final DDOrderMovePlanStep planLine,
			@NonNull final DDOrderId ddOrderId)
	{
		final DDOrderLineId ddOrderLineId = planLine.getDdOrderLineId();
		return planLine.getSteps()
				.stream()
				.map(planStep -> toScheduleToMoveRequest(planStep, ddOrderId, ddOrderLineId));
	}

	private static DDOrderMoveScheduleCreateRequest toScheduleToMoveRequest(
			@NonNull final DDOrderMovePlanLine planStep,
			@NonNull final DDOrderId ddOrderId,
			@NonNull final DDOrderLineId ddOrderLineId)
	{
		return DDOrderMoveScheduleCreateRequest.builder()
				.ddOrderId(ddOrderId)
				.ddOrderLineId(ddOrderLineId)
				.pickFromHUId(planStep.getPickFromHUId())
				.qtyToPick(planStep.getQtyToPick())
				.isPickWholeHU(planStep.isPickWholeHU())
				.build();
	}

	public void splitFromPickHUIfNeeded(@NonNull final DDOrderMoveSchedule schedule)
	{
		if (schedule.getActualHUIdPicked() != null)
		{
			return;
		}

		final I_M_HU newCU = HUTransformService.newInstance()
				.huToNewSingleCU(HUTransformService.HUsToNewCUsRequest.builder()
						.sourceHU(handlingUnitsBL.getById(schedule.getPickFromHUId()))
						.qtyCU(schedule.getQtyToPick())
						.keepNewCUsUnderSameParent(false)
						.onlyFromUnreservedHUs(true)
						.build());

		schedule.setActualHUIdPicked(HuId.ofRepoId(newCU.getM_HU_ID()));
		ddOrderMoveScheduleRepository.save(schedule);
	}
}
