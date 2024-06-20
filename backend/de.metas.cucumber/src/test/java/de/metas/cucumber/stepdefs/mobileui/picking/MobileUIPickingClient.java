package de.metas.cucumber.stepdefs.mobileui.picking;

import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.picking.api.PickingSlotIdAndCaption;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.picking.rest_api.PickingRestController;
import de.metas.picking.rest_api.json.JsonPickingStepEvent;
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
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import static org.assertj.core.api.Assertions.*;

class MobileUIPickingClient
{
	private static final Logger logger = LogManager.getLogger(MobileUIPickingClient.class);
	private final WorkflowRestController workflowRestController = SpringContextHolder.instance.getBean(WorkflowRestController.class);
	private final PickingRestController pickingRestController = SpringContextHolder.instance.getBean(PickingRestController.class);

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

	public JsonWFProcess pickLine(JsonPickingStepEvent request)
	{
		Check .assumeEquals(request.getType(), JsonPickingStepEvent.EventType.PICK, "Invalid type: {}", request);
		return pickingRestController.postEvent(request);
	}

	public JsonWFProcess complete(final String wfProcessId)
	{
		return workflowRestController.setUserConfirmation(wfProcessId, PickingMobileApplication.ACTIVITY_ID_Complete.getAsString());
	}
}
