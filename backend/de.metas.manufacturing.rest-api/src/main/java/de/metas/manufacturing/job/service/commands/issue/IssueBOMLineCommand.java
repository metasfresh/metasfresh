package de.metas.manufacturing.job.service.commands.issue;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueSchedule;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleProcessRequest;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleService;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivityId;
import de.metas.manufacturing.job.model.RawMaterialsIssue;
import de.metas.manufacturing.job.model.RawMaterialsIssueLine;
import de.metas.manufacturing.job.model.RawMaterialsIssueStep;
import de.metas.manufacturing.job.service.ManufacturingJobLoaderAndSaver;
import de.metas.manufacturing.job.service.ManufacturingJobLoaderAndSaverSupportingServices;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderRoutingActivityId;

public class IssueBOMLineCommand
{
	// Services
	@NonNull private final ITrxManager trxManager;
	@NonNull private final PPOrderIssueScheduleService ppOrderIssueScheduleService;
	@NonNull private final ManufacturingJobLoaderAndSaverSupportingServices loadingAndSavingSupportServices;

	// Params
	@NonNull private final PPOrderRoutingActivityId activityId;
	private final int lineIndex;

	// State
	@NonNull private ManufacturingJob job;

	@Builder
	private IssueBOMLineCommand(
			@NonNull final ITrxManager trxManager,
			@NonNull final PPOrderIssueScheduleService ppOrderIssueScheduleService,
			@NonNull final ManufacturingJobLoaderAndSaverSupportingServices loadingAndSavingSupportServices,
			//
			@NonNull final ManufacturingJob job,
			@NonNull final PPOrderRoutingActivityId activityId,
			final int lineIndex)
	{
		this.trxManager = trxManager;
		this.ppOrderIssueScheduleService = ppOrderIssueScheduleService;
		this.loadingAndSavingSupportServices = loadingAndSavingSupportServices;

		this.job = job;
		this.activityId = activityId;
		this.lineIndex = lineIndex;
	}

	public ManufacturingJob execute()
	{
		trxManager.runInThreadInheritedTrx(this::execute0);
		return job;
	}

	private void execute0()
	{
		final RawMaterialsIssue rawMaterialsIssue = job.getActivityById(ManufacturingJobActivityId.of(activityId)).getRawMaterialsIssueAssumingNotNull();
		final ImmutableList<RawMaterialsIssueLine> lines = rawMaterialsIssue.getLines();

		if (lineIndex < 0 || lineIndex >= lines.size())
		{
			throw new AdempiereException("Invalid lineIndex: " + lineIndex + " (lines count: " + lines.size() + ")");
		}

		final RawMaterialsIssueLine line = lines.get(lineIndex);
		if (!line.isAllowManualIssue())
		{
			throw new AdempiereException("Line does not allow manual issue (IssueOnlyForReceived)")
					.setParameter("lineIndex", lineIndex)
					.setParameter("productId", line.getProductId());
		}

		for (final RawMaterialsIssueStep step : line.getSteps())
		{
			if (step.isIssued())
			{
				continue;
			}

			final PPOrderIssueScheduleProcessRequest request = PPOrderIssueScheduleProcessRequest.builder()
					.activityId(activityId)
					.issueScheduleId(step.getId())
					.qtyIssued(step.getQtyToIssue().toBigDecimal())
					.failIfIssueOnlyForReceived(true)
					.build();

			final PPOrderIssueSchedule issueSchedule = ppOrderIssueScheduleService.issue(request);

			job = job.withChangedRawMaterialsIssueStep(
					activityId,
					step.getId(),
					s -> s.withIssued(issueSchedule.getIssued()));
		}

		new ManufacturingJobLoaderAndSaver(loadingAndSavingSupportServices).saveActivityStatuses(job);
	}
}
