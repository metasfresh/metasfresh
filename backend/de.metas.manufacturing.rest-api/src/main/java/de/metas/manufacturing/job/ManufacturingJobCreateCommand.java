package de.metas.manufacturing.job;

import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.manufacturing.issue.plan.PPOrderIssuePlan;
import de.metas.manufacturing.issue.plan.PPOrderIssuePlanCreateCommand;
import de.metas.manufacturing.issue.plan.PPOrderIssuePlanStep;
import de.metas.manufacturing.order.PPOrderIssueSchedule;
import de.metas.manufacturing.order.PPOrderIssueScheduleCreateRequest;
import de.metas.manufacturing.order.PPOrderIssueScheduleRepository;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;

import java.util.ArrayList;

class ManufacturingJobCreateCommand
{
	private final ITrxManager trxManager;
	private final IPPOrderBL ppOrderBL;
	private final HUReservationService huReservationService;
	private final PPOrderIssueScheduleRepository orderIssueScheduleRepository;
	private final ManufacturingJobLoaderSupportingServices loadingSupportServices;

	// Params
	private final PPOrderId ppOrderId;
	private final UserId responsibleId;

	// State
	private ManufacturingJobLoader loader;
	private I_PP_Order ppOrder;

	@Builder
	private ManufacturingJobCreateCommand(
			@NonNull final ITrxManager trxManager,
			@NonNull final IPPOrderBL ppOrderBL,
			@NonNull final HUReservationService huReservationService,
			@NonNull final PPOrderIssueScheduleRepository orderIssueScheduleRepository,
			@NonNull final ManufacturingJobLoaderSupportingServices loadingSupportServices,
			//
			@NonNull final PPOrderId ppOrderId,
			@NonNull final UserId responsibleId
	)
	{
		this.trxManager = trxManager;
		this.ppOrderBL = ppOrderBL;
		this.huReservationService = huReservationService;
		this.orderIssueScheduleRepository = orderIssueScheduleRepository;
		this.loadingSupportServices = loadingSupportServices;

		this.ppOrderId = ppOrderId;
		this.responsibleId = responsibleId;
	}

	public ManufacturingJob execute()
	{
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private ManufacturingJob executeInTrx()
	{
		loader = new ManufacturingJobLoader(loadingSupportServices);

		ppOrder = ppOrderBL.getById(ppOrderId);
		loader.addToCache(ppOrder);

		setResponsible();

		final PPOrderIssuePlan plan = createIssuePlan();
		createIssueSchedules(plan);

		return loader.load(ppOrderId);
	}

	private void setResponsible()
	{
		final UserId previousResponsibleId = UserId.ofRepoIdOrNullIfSystem(ppOrder.getAD_User_Responsible_ID());
		if (UserId.equals(previousResponsibleId, responsibleId))
		{
			//noinspection UnnecessaryReturnStatement
			return;
		}
		else if (previousResponsibleId != null)
		{
			throw new AdempiereException("Order is already assigned");
		}
		else
		{
			ppOrder.setAD_User_Responsible_ID(responsibleId.getRepoId());
			ppOrderBL.save(ppOrder);
		}
	}

	private PPOrderIssuePlan createIssuePlan()
	{
		return PPOrderIssuePlanCreateCommand.builder()
				.huReservationService(huReservationService)
				.ppOrderId(ppOrderId)
				.build()
				.execute();
	}

	private void createIssueSchedules(final PPOrderIssuePlan plan)
	{
		final ArrayList<PPOrderIssueSchedule> schedules = new ArrayList<>();

		int nextSeqNo = 10;
		for (final PPOrderIssuePlanStep planStep : plan.getSteps())
		{
			final int seqNo = nextSeqNo;
			nextSeqNo += 10;

			// TODO: reserve HU

			final PPOrderIssueSchedule schedule = orderIssueScheduleRepository.createSchedule(
					PPOrderIssueScheduleCreateRequest.builder()
							.ppOrderId(ppOrderId)
							.ppOrderBOMLineId(planStep.getOrderBOMLineId())
							.seqNo(seqNo)
							.productId(planStep.getProductId())
							.qtyToIssue(planStep.getQtyToIssue())
							.issueFromHUId(planStep.getPickFromHUId())
							.issueFromLocatorId(planStep.getPickFromLocatorId())
							.build());

			schedules.add(schedule);
		}

		loader.addToCache(schedules);
	}

}
