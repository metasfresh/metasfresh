package de.metas.manufacturing.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleId;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleProcessRequest;
import de.metas.i18n.TranslatableStrings;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivity;
import de.metas.manufacturing.job.model.ManufacturingJobReference;
import de.metas.manufacturing.job.service.ManufacturingJobService;
import de.metas.manufacturing.workflows_api.activity_handlers.confirmation.ConfirmationActivityHandler;
import de.metas.manufacturing.workflows_api.activity_handlers.generateHUQRCodes.GenerateHUQRCodesActivityHandler;
import de.metas.manufacturing.workflows_api.activity_handlers.issue.RawMaterialsIssueActivityHandler;
import de.metas.manufacturing.workflows_api.activity_handlers.issue.RawMaterialsIssueAdjustmentActivityHandler;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.MaterialReceiptActivityHandler;
import de.metas.manufacturing.workflows_api.activity_handlers.scanScaleDevice.ScanScaleDeviceActivityHandler;
import de.metas.manufacturing.workflows_api.activity_handlers.work_report.WorkReportActivityHandler;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonManufacturingOrderEvent;
import de.metas.user.UserId;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Stream;

@Service
public class ManufacturingRestService
{
	private final ManufacturingJobService manufacturingJobService;

	public ManufacturingRestService(final ManufacturingJobService manufacturingJobService)
	{
		this.manufacturingJobService = manufacturingJobService;
	}

	public Stream<ManufacturingJobReference> streamJobReferencesForUser(final @NonNull UserId responsibleId, final @NonNull QueryLimit suggestedLimit)
	{
		return manufacturingJobService.streamJobReferencesForUser(responsibleId, suggestedLimit);
	}

	public ManufacturingJob createJob(final PPOrderId ppOrderId, final UserId responsibleId)
	{
		return manufacturingJobService.createJob(ppOrderId, responsibleId);
	}

	public void abortJob(@NonNull final PPOrderId ppOrderId, @NonNull final UserId responsibleId)
	{
		manufacturingJobService.abortJob(ppOrderId, responsibleId);
	}

	public ManufacturingJob getJobById(final PPOrderId ppOrderId)
	{
		return manufacturingJobService.getJobById(ppOrderId);
	}

	private static WFActivity toWFActivity(final ManufacturingJobActivity jobActivity)
	{
		final WFActivity.WFActivityBuilder builder = WFActivity.builder()
				.id(WFActivityId.ofId(jobActivity.getId()))
				.caption(TranslatableStrings.anyLanguage(jobActivity.getName()))
				.status(jobActivity.getStatus());

		switch (jobActivity.getType())
		{
			case RawMaterialsIssue:
				return builder.wfActivityType(RawMaterialsIssueActivityHandler.HANDLED_ACTIVITY_TYPE).build();
			case RawMaterialsIssueAdjustment:
				return builder.wfActivityType(RawMaterialsIssueAdjustmentActivityHandler.HANDLED_ACTIVITY_TYPE).build();
			case MaterialReceipt:
				return builder.wfActivityType(MaterialReceiptActivityHandler.HANDLED_ACTIVITY_TYPE).build();
			case WorkReport:
				return builder.wfActivityType(WorkReportActivityHandler.HANDLED_ACTIVITY_TYPE).build();
			case ActivityConfirmation:
				return builder.wfActivityType(ConfirmationActivityHandler.HANDLED_ACTIVITY_TYPE).build();
			case GenerateHUQRCodes:
				return builder.wfActivityType(GenerateHUQRCodesActivityHandler.HANDLED_ACTIVITY_TYPE).build();
			case ScanScaleDevice:
				return builder.wfActivityType(ScanScaleDeviceActivityHandler.HANDLED_ACTIVITY_TYPE).build();
			default:
				throw new AdempiereException("Unknown type: " + jobActivity);
		}
	}

	public static WFProcess toWFProcess(final ManufacturingJob job)
	{
		return WFProcess.builder()
				.id(WFProcessId.ofIdPart(ManufacturingMobileApplication.HANDLER_ID, job.getPpOrderId()))
				.invokerId(Objects.requireNonNull(job.getResponsibleId()))
				.caption(TranslatableStrings.anyLanguage("" + job.getPpOrderId().getRepoId())) // TODO
				.document(job)
				.activities(job.getActivities()
						.stream()
						.map(ManufacturingRestService::toWFActivity)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	public ManufacturingJob processEvent(final ManufacturingJob job, final JsonManufacturingOrderEvent event)
	{
		job.assertUserReporting();

		if (event.getIssueTo() != null)
		{
			final JsonManufacturingOrderEvent.IssueTo issueTo = event.getIssueTo();
			return manufacturingJobService.issueRawMaterials(job, PPOrderIssueScheduleProcessRequest.builder()
					.ppOrderId(job.getPpOrderId())
					.issueScheduleId(PPOrderIssueScheduleId.ofString(issueTo.getIssueStepId()))
					.qtyIssued(issueTo.getQtyIssued())
					.qtyRejected(issueTo.getQtyRejected())
					.qtyRejectedReasonCode(QtyRejectedReasonCode.ofNullableCode(issueTo.getQtyRejectedReasonCode()).orElse(null))
					.build());
		}
		else if (event.getReceiveFrom() != null)
		{
			final JsonManufacturingOrderEvent.ReceiveFrom receiveFrom = event.getReceiveFrom();
			return manufacturingJobService.receiveGoods(
					job,
					receiveFrom.getFinishedGoodsReceiveLineId(),
					receiveFrom.getAggregateToLU(),
					receiveFrom.getQtyReceived(),
					SystemTime.asZonedDateTime());
		}
		else
		{
			throw new AdempiereException("Cannot handle: " + event);
		}
	}
}
