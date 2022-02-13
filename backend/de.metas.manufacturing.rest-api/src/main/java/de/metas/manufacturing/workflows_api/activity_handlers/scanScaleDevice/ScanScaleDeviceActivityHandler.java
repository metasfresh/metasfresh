package de.metas.manufacturing.workflows_api.activity_handlers.scanScaleDevice;

import de.metas.device.accessor.DeviceAccessor;
import de.metas.device.accessor.DeviceAccessorsHubFactory;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.SetScannedBarcodeRequest;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.SetScannedBarcodeSupport;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.SetScannedBarcodeSupportHelper;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFActivityType;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.service.WFActivityHandler;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ScanScaleDeviceActivityHandler implements WFActivityHandler, SetScannedBarcodeSupport
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.scanScaleDevice");

	private final DeviceAccessorsHubFactory deviceAccessorsHubFactory;

	public ScanScaleDeviceActivityHandler(
			@NonNull final DeviceAccessorsHubFactory deviceAccessorsHubFactory)
	{
		this.deviceAccessorsHubFactory = deviceAccessorsHubFactory;
	}

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(
			final @NonNull WFProcess wfProcess,
			final @NonNull WFActivity wfActivity,
			final @NonNull JsonOpts jsonOpts)
	{
		final String barcodeCaption = getCurrentScaleDevice(wfProcess)
				.map(deviceAccessor -> deviceAccessor.getDisplayName().translate(jsonOpts.getAdLanguage()))
				.orElse(null);
		return SetScannedBarcodeSupportHelper.createUIComponent(barcodeCaption);
	}

	private Optional<DeviceAccessor> getCurrentScaleDevice(final @NonNull WFProcess wfProcess)
	{
		final ManufacturingJob job = wfProcess.getDocumentAs(ManufacturingJob.class);
		if (job.getCurrentScaleDeviceId() != null)
		{
			return deviceAccessorsHubFactory
					.getDefaultDeviceAccessorsHub()
					.getDeviceAccessorById(job.getCurrentScaleDeviceId());
		}
		else
		{
			return Optional.empty();
		}
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		return getCurrentScaleDevice(wfProcess).isPresent()
				? WFActivityStatus.COMPLETED
				: WFActivityStatus.NOT_STARTED;
	}

	@Override
	public WFProcess setScannedBarcode(final SetScannedBarcodeRequest request)
	{
		// final PickingSlotBarcode pickingSlotBarcode = PickingSlotBarcode.ofBarcodeString(request.getScannedBarcode());
		// final WFProcess wfProcess = request.getWfProcess();
		// return wfProcess.<PickingJob>mapDocument(pickingJob -> pickingJobRestService.allocateAndSetPickingSlot(pickingJob, pickingSlotBarcode));
		throw new UnsupportedOperationException(); // TODO
	}
}
