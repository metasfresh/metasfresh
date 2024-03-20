package de.metas.manufacturing.job.service.commands.create_job;

import com.google.common.collect.ArrayListMultimap;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueSchedule;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleCreateRequest;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleService;
import de.metas.handlingunits.pporder.source_hu.PPOrderSourceHUService;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.manufacturing.issue.plan.PPOrderIssuePlan;
import de.metas.manufacturing.issue.plan.PPOrderIssuePlanCreateCommand;
import de.metas.manufacturing.issue.plan.PPOrderIssuePlanStep;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.service.ManufacturingJobLoaderAndSaver;
import de.metas.manufacturing.job.service.ManufacturingJobLoaderAndSaverSupportingServices;
import de.metas.user.UserId;
import de.metas.util.GuavaCollectors;
import de.metas.util.lang.SeqNoProvider;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;

import java.util.ArrayList;
import java.util.Comparator;

public class ManufacturingJobCreateCommand
{
	private final ITrxManager trxManager;
	private final IHUPPOrderBL ppOrderBL;
	private final HUReservationService huReservationService;
	private final PPOrderSourceHUService ppOrderSourceHUService;
	private final PPOrderIssueScheduleService ppOrderIssueScheduleService;
	private final ManufacturingJobLoaderAndSaverSupportingServices loadingSupportServices;

	// Params
	private final PPOrderId ppOrderId;
	private final UserId responsibleId;

	// State
	private ManufacturingJobLoaderAndSaver loader;
	private I_PP_Order ppOrder;

	@Builder
	private ManufacturingJobCreateCommand(
			@NonNull final ITrxManager trxManager,
			@NonNull final IHUPPOrderBL ppOrderBL,
			@NonNull final HUReservationService huReservationService,
			@NonNull final PPOrderSourceHUService ppOrderSourceHUService,
			@NonNull final PPOrderIssueScheduleService ppOrderIssueScheduleService,
			@NonNull final ManufacturingJobLoaderAndSaverSupportingServices loadingSupportServices,
			//
			@NonNull final PPOrderId ppOrderId,
			@NonNull final UserId responsibleId)
	{
		this.trxManager = trxManager;
		this.ppOrderBL = ppOrderBL;
		this.huReservationService = huReservationService;
		this.ppOrderSourceHUService = ppOrderSourceHUService;
		this.ppOrderIssueScheduleService = ppOrderIssueScheduleService;
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
		loader = new ManufacturingJobLoaderAndSaver(loadingSupportServices);

		ppOrder = ppOrderBL.getById(ppOrderId);
		loader.addToCache(ppOrder);

		setResponsible();

		final PPOrderIssuePlan plan = createIssuePlan();
		createIssueSchedules(plan);

		return loader.load(ppOrderId);
	}

	private void setResponsible()
	{
		final UserId previousResponsibleId = ManufacturingJobLoaderAndSaver.extractResponsibleId(ppOrder);
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
				.ppOrderSourceHUService(ppOrderSourceHUService)
				.ppOrderId(ppOrderId)
				.build()
				.execute();
	}

	private void createIssueSchedules(@NonNull final PPOrderIssuePlan plan)
	{
		final ArrayListMultimap<PPOrderBOMLineId, PPOrderIssueSchedule> allExistingSchedules = ppOrderIssueScheduleService.getByOrderId(plan.getOrderId())
				.stream()
				.collect(GuavaCollectors.toArrayListMultimapByKey(PPOrderIssueSchedule::getPpOrderBOMLineId));


		final ArrayList<PPOrderIssueSchedule> schedules = new ArrayList<>();

		final SeqNoProvider seqNoProvider = SeqNoProvider.ofInt(10);
		for (final PPOrderIssuePlanStep planStep : plan.getSteps())
		{
			final PPOrderBOMLineId orderBOMLineId = planStep.getOrderBOMLineId();
			final ArrayList<PPOrderIssueSchedule> bomLineExistingSchedules = new ArrayList<>(allExistingSchedules.removeAll(orderBOMLineId));
			bomLineExistingSchedules.sort(Comparator.comparing(PPOrderIssueSchedule::getSeqNo));

			for(final PPOrderIssueSchedule existingSchedule : bomLineExistingSchedules)
			{
				if(existingSchedule.isIssued())
				{
					final PPOrderIssueSchedule existingScheduleChanged = ppOrderIssueScheduleService.changeSeqNo(existingSchedule, seqNoProvider.getAndIncrement());
					schedules.add(existingScheduleChanged);
				}
				else
				{
					ppOrderIssueScheduleService.delete(existingSchedule);
				}
			}

			final PPOrderIssueSchedule schedule = ppOrderIssueScheduleService.createSchedule(
					PPOrderIssueScheduleCreateRequest.builder()
							.ppOrderId(ppOrderId)
							.ppOrderBOMLineId(orderBOMLineId)
							.seqNo(seqNoProvider.getAndIncrement())
							.productId(planStep.getProductId())
							.qtyToIssue(planStep.getQtyToIssue())
							.issueFromHUId(planStep.getPickFromTopLevelHUId())
							.issueFromLocatorId(planStep.getPickFromLocatorId())
							.isAlternativeIssue(planStep.isAlternative())
							.build());

			schedules.add(schedule);
		}

		loader.addToCache(plan.getOrderId(), schedules);
	}

}
