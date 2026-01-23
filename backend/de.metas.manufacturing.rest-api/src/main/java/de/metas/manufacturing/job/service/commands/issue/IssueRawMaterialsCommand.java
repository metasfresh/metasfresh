package de.metas.manufacturing.job.service.commands.issue;

import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueSchedule;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleProcessRequest;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleService;
import de.metas.logging.LogManager;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.RawMaterialsIssueStep;
import de.metas.manufacturing.job.service.ManufacturingJobLoaderAndSaver;
import de.metas.manufacturing.job.service.ManufacturingJobLoaderAndSaverSupportingServices;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

public class IssueRawMaterialsCommand
{
	// Services
	@NonNull private static final Logger logger = LogManager.getLogger(IssueRawMaterialsCommand.class);
	@NonNull private final ITrxManager trxManager;
	@NonNull private final PPOrderIssueScheduleService ppOrderIssueScheduleService;
	@NonNull private final ManufacturingJobLoaderAndSaverSupportingServices loadingAndSavingSupportServices;

	// Params
	@NonNull private final PPOrderIssueScheduleProcessRequest request;

	// State
	@NonNull private ManufacturingJob job;
	private boolean processed;

	@Builder
	private IssueRawMaterialsCommand(
			@NonNull final ITrxManager trxManager,
			@NonNull final PPOrderIssueScheduleService ppOrderIssueScheduleService,
			@NonNull final ManufacturingJobLoaderAndSaverSupportingServices loadingAndSavingSupportServices,
			//
			@NonNull final ManufacturingJob job,
			@NonNull final PPOrderIssueScheduleProcessRequest request)
	{
		this.trxManager = trxManager;
		this.ppOrderIssueScheduleService = ppOrderIssueScheduleService;
		this.loadingAndSavingSupportServices = loadingAndSavingSupportServices;

		this.job = job;
		this.request = request;
	}

	public ManufacturingJob execute()
	{
		trxManager.runInThreadInheritedTrx(this::execute0);
		return job;
	}

	private void execute0()
	{
		job = job.withChangedRawMaterialsIssueStep(
				request.getActivityId(),
				request.getIssueScheduleId(),
				(step) -> {
					if (processed)
					{
						// shall not happen
						logger.warn("Ignoring request because was already processed: request={}, step={}", request, step);
						return step;
					}

					return issueToStep(step);
				});
		if (!processed)
		{
			throw new AdempiereException("Failed fulfilling issue request")
					.setParameter("request", request)
					.setParameter("job", job);
		}

		save();
	}

	private RawMaterialsIssueStep issueToStep(final RawMaterialsIssueStep step)
	{
		step.assertNotIssued();
		final PPOrderIssueSchedule issueSchedule = ppOrderIssueScheduleService.issue(request);
		this.processed = true;

		return step.withIssued(issueSchedule.getIssued());
	}

	private void save()
	{
		newSaver().saveActivityStatuses(job);
	}

	@NonNull
	private ManufacturingJobLoaderAndSaver newSaver() {return new ManufacturingJobLoaderAndSaver(loadingAndSavingSupportServices);}
}
