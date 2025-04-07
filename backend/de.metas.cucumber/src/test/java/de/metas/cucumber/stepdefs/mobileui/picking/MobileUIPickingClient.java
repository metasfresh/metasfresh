package de.metas.cucumber.stepdefs.mobileui.picking;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.logging.LogManager;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.picking.rest_api.PickingRestController;
import de.metas.picking.rest_api.json.JsonLUPickingTarget;
import de.metas.picking.rest_api.json.JsonPickingStepEvent;
import de.metas.picking.rest_api.json.JsonTUPickingTarget;
import de.metas.picking.workflow.handlers.PickingMobileApplication;
import de.metas.picking.workflow.handlers.activity_handlers.SetPickingSlotWFActivityHandler;
import de.metas.util.Check;
import de.metas.workflow.rest_api.controller.v2.WorkflowRestController;
import de.metas.workflow.rest_api.controller.v2.json.JsonLaunchersQuery;
import de.metas.workflow.rest_api.controller.v2.json.JsonSetScannedBarcodeRequest;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFActivity;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcess;
import de.metas.workflow.rest_api.controller.v2.json.JsonWFProcessStartRequest;
import de.metas.workflow.rest_api.controller.v2.json.JsonWorkflowLauncher;
import de.metas.workflow.rest_api.controller.v2.json.JsonWorkflowLaunchersList;
import de.metas.workflow.rest_api.model.UIComponentType;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class MobileUIPickingClient
{
	private static final Logger logger = LogManager.getLogger(MobileUIPickingClient.class);
	private final WorkflowRestController workflowRestController = SpringContextHolder.instance.getBean(WorkflowRestController.class);
	private final PickingRestController pickingRestController = SpringContextHolder.instance.getBean(PickingRestController.class);
	private final PickingJobService pickingJobService = SpringContextHolder.instance.getBean(PickingJobService.class);

	public JsonWFProcess startJobBySalesDocumentNo(@NonNull final String salesOrderDocumentNo)
	{
		final JsonWorkflowLauncher launcher = getSinglePickingJobLauncherBySalesDocumentNo(salesOrderDocumentNo);

		return workflowRestController.start(JsonWFProcessStartRequest.builder()
				.wfParameters(launcher.getWfParameters())
				.build());
	}

	private JsonWorkflowLauncher getSinglePickingJobLauncherBySalesDocumentNo(final String salesOrderDocumentNo)
	{
		final JsonWorkflowLaunchersList launchers = workflowRestController.getLaunchers(
				JsonLaunchersQuery.builder()
						.applicationId(PickingMobileApplication.APPLICATION_ID)
						.filterByDocumentNo(salesOrderDocumentNo)
						.build());

		final ImmutableList<JsonWorkflowLauncher> launchersList = Check.assumeNotNull(launchers.getLaunchers(), "launchers list shall be set for {}", launchers);
		if (launchersList.isEmpty())
		{
			throw new AdempiereException("No picking jobs found for sales document no `" + salesOrderDocumentNo + "`");
		}
		else if (launchersList.size() > 1)
		{
			throw new AdempiereException("More than one picking job candidate found for sales document no `" + salesOrderDocumentNo + "`: " + launchers);
		}
		else
		{
			return launchersList.get(0);
		}
	}

	public JsonWFProcess scanPickingSlot(@NonNull final String wfProcessId, @NonNull final PickingSlotIdAndCaption pickingSlotIdAndCaption)
	{
		final String activityId = PickingMobileApplication.ACTIVITY_ID_ScanPickingSlot.getAsString();

		final JsonSetScannedBarcodeRequest request = JsonSetScannedBarcodeRequest.builder()
				.barcode(PickingSlotQRCode.ofPickingSlotIdAndCaption(pickingSlotIdAndCaption).toGlobalQRCodeJsonString())
				.build();
		logger.info("scanPickingSlot: Sending wfProcessId={}, activityId={}, request={}", wfProcessId, activityId, request);
		final JsonWFProcess wfProcess = workflowRestController.setScannedBarcode(wfProcessId, activityId, request);
		logger.info("scanPickingSlot: Got response {}", wfProcess);

		final JsonWFActivity activity = wfProcess.getActivityById(activityId);
		assertThat(activity.getComponentType()).isEqualTo(UIComponentType.SCAN_BARCODE.getAsString());
		assertThat(activity.getStatus()).isEqualTo(WFActivityStatus.COMPLETED);
		assertThat(activity.getComponentProps()).containsEntry("currentValue", SetPickingSlotWFActivityHandler.toJsonQRCode(pickingSlotIdAndCaption));

		return wfProcess;
	}

	public JsonWFProcess setPickingTarget(@NonNull final String wfProcessId, LUPickingTarget pickingTarget)
	{
		return pickingRestController.setLUPickingTarget(wfProcessId, null, JsonLUPickingTarget.of(pickingTarget));
	}

	public JsonWFProcess setTUPickingTarget(@NonNull final String wfProcessId,@NonNull final TUPickingTarget pickingTarget)
	{
		return pickingRestController.setTUPickingTarget(wfProcessId, null, JsonTUPickingTarget.of(pickingTarget));
	}

	public Optional<LUPickingTarget> getPickingTarget(@NonNull final String wfProcessIdStr)
	{
		final WFProcessId wfProcessId = WFProcessId.ofString(wfProcessIdStr);
		final PickingJobId pickingJobId = wfProcessId.getRepoId(PickingJobId::ofRepoId);
		final PickingJob pickingJob = pickingJobService.getById(pickingJobId);
		return pickingJob.getLuPickingTarget(null);

	}

	public JsonWFProcess pickLine(JsonPickingStepEvent request)
	{
		Check.assumeEquals(request.getType(), JsonPickingStepEvent.EventType.PICK, "Invalid type: {}", request);
		return pickingRestController.postEvent(request);
	}

	public JsonWFProcess complete(final String wfProcessId)
	{
		return workflowRestController.setUserConfirmation(wfProcessId, PickingMobileApplication.ACTIVITY_ID_Complete.getAsString());
	}
}
