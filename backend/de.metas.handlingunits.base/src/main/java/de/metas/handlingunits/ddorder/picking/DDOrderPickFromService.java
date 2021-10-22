package de.metas.handlingunits.ddorder.picking;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.service.IADReferenceDAO;
import org.eevolution.api.DDOrderId;
import org.eevolution.api.DDOrderLineId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class DDOrderPickFromService
{
	private final DDOrderPickFromRepository ddOrderPickFromRepository;
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);

	public DDOrderPickFromService(
			@NonNull final DDOrderPickFromRepository ddOrderPickFromRepository)
	{
		this.ddOrderPickFromRepository = ddOrderPickFromRepository;
	}

	public IADReferenceDAO.ADRefList getQtyRejectedReasons()
	{
		return adReferenceDAO.getRefListById(QtyRejectedReasonCode.REFERENCE_ID);
	}

	public void addScheduleToPick(@NonNull final ScheduleToPickRequest request)
	{
		ddOrderPickFromRepository.addScheduleToPick(request);
	}

	public ImmutableList<DDOrderPickSchedule> addScheduleToPickBulk(@NonNull final List<ScheduleToPickRequest> requests)
	{
		return ddOrderPickFromRepository.addScheduleToPickBulk(requests);
	}

	public ImmutableList<DDOrderPickSchedule> getSchedules(@NonNull final DDOrderId ddOrderId) {return ddOrderPickFromRepository.getSchedules(ddOrderId);}

	public void removeDraftSchedules(@NonNull final DDOrderId ddOrderId)
	{
		// FIXME: only Draft ones!
		ddOrderPickFromRepository.removeAllSchedulesForOrder(ddOrderId);
	}

	public void removeDraftSchedules(@NonNull final DDOrderLineId ddOrderLineId)
	{
		// FIXME: only Draft ones!
		ddOrderPickFromRepository.removeAllSchedulesForLine(ddOrderLineId);
	}

	public void removeAllSchedulesForLine(@NonNull final DDOrderLineId ddOrderLineId)
	{
		ddOrderPickFromRepository.removeAllSchedulesForLine(ddOrderLineId);
	}

	public void removeFromHUsScheduledToPickList(final DDOrderLineId ddOrderLineId, final Set<HuId> huIdsToUnassign)
	{
		ddOrderPickFromRepository.removeFromHUsScheduledToPickList(ddOrderLineId, huIdsToUnassign);
	}

	public IQueryFilter<I_M_HU> getHUsNotAlreadyScheduledToPickFilter()
	{
		return ddOrderPickFromRepository.getHUsNotAlreadyScheduledToPickFilter();
	}

	public boolean isScheduledToPick(final HuId huId)
	{
		return ddOrderPickFromRepository.isScheduledToPick(huId);
	}

	public List<HuId> retrieveHUIdsScheduledToPick(final DDOrderLineId ddOrderLineId)
	{
		// FIXME: only Draft ones
		return ddOrderPickFromRepository.retrieveHUIdsScheduledToPick(ddOrderLineId);
	}

	public DDOrderPickPlan createPlan(@NonNull final PickingPlanCreateRequest request)
	{
		return PickingPlanCreateCommand.builder()
				.request(request)
				.build().execute();
	}

	public ImmutableList<DDOrderPickSchedule> savePlan(@NonNull final DDOrderPickPlan plan)
	{
		return addScheduleToPickBulk(toScheduleToPickRequest(plan));
	}

	private static ImmutableList<ScheduleToPickRequest> toScheduleToPickRequest(@NonNull final DDOrderPickPlan plan)
	{
		final DDOrderId ddOrderId = plan.getDdOrderId();
		return plan.getLines()
				.stream()
				.flatMap(planLine -> toScheduleToPickRequest(planLine, ddOrderId))
				.collect(ImmutableList.toImmutableList());
	}

	private static Stream<ScheduleToPickRequest> toScheduleToPickRequest(
			@NonNull final DDOrderLinePickPlan planLine,
			@NonNull final DDOrderId ddOrderId)
	{
		final DDOrderLineId ddOrderLineId = planLine.getDdOrderLineId();
		return planLine.getSteps()
				.stream()
				.map(planStep -> toScheduleToPickRequest(planStep, ddOrderId, ddOrderLineId));
	}

	private static ScheduleToPickRequest toScheduleToPickRequest(
			@NonNull final DDOrderLineStepPlan planStep,
			@NonNull final DDOrderId ddOrderId,
			@NonNull final DDOrderLineId ddOrderLineId)
	{
		return ScheduleToPickRequest.builder()
				.ddOrderId(ddOrderId)
				.ddOrderLineId(ddOrderLineId)
				.pickFromHUId(planStep.getPickFromHUId())
				.qtyToPick(planStep.getQtyToPick())
				.isPickWholeHU(planStep.isPickWholeHU())
				.build();
	}

	public void splitFromPickHUIfNeeded(final DDOrderPickSchedule schedule)
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
		ddOrderPickFromRepository.save(schedule);
	}
}
