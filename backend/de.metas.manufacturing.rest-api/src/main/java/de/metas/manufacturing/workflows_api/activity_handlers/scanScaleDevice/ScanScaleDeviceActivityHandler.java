package de.metas.manufacturing.workflows_api.activity_handlers.scanScaleDevice;

import com.google.common.collect.ImmutableList;
import de.metas.device.accessor.DeviceId;
import de.metas.device.accessor.qrcode.DeviceQRCode;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ScaleDevice;
import de.metas.manufacturing.job.service.ManufacturingJobService;
import de.metas.manufacturing.workflows_api.ManufacturingMobileApplication;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.JsonQRCode;
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
import java.util.stream.Stream;

@Component
public class ScanScaleDeviceActivityHandler implements WFActivityHandler, SetScannedBarcodeSupport
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.scanScaleDevice");

	private final ManufacturingJobService manufacturingJobService;

	public ScanScaleDeviceActivityHandler(
			final @NonNull ManufacturingJobService manufacturingJobService)
	{
		this.manufacturingJobService = manufacturingJobService;
	}

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(
			final @NonNull WFProcess wfProcess,
			final @NonNull WFActivity wfActivity,
			final @NonNull JsonOpts jsonOpts)
	{
		return SetScannedBarcodeSupportHelper.uiComponent()
				.alwaysAvailableToUser(wfActivity.getAlwaysAvailableToUser())
				.currentValue(getCurrentScaleDevice(wfProcess)
						.map(scaleDevice -> toJsonQRCode(scaleDevice, jsonOpts.getAdLanguage()))
						.orElse(null))
				.validOptions(streamAvailableScaleDevices(wfProcess)
						.map(scaleDevice1 -> toJsonQRCode(scaleDevice1, jsonOpts.getAdLanguage()))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private Optional<ScaleDevice> getCurrentScaleDevice(final @NonNull WFProcess wfProcess)
	{
		final ManufacturingJob job = ManufacturingMobileApplication.getManufacturingJob(wfProcess);
		return manufacturingJobService.getCurrentScaleDevice(job);
	}

	private Stream<ScaleDevice> streamAvailableScaleDevices(final @NonNull WFProcess wfProcess)
	{
		final ManufacturingJob job = ManufacturingMobileApplication.getManufacturingJob(wfProcess);
		return manufacturingJobService.streamAvailableScaleDevices(job);
	}

	private static JsonQRCode toJsonQRCode(final ScaleDevice scaleDevice, final String adLanguage)
	{
		final DeviceQRCode qrCode = DeviceQRCode.builder()
				.deviceId(scaleDevice.getDeviceId())
				.caption(scaleDevice.getCaption().translate(adLanguage))
				.build();

		return JsonQRCode.builder()
				.qrCode(qrCode.toGlobalQRCodeJsonString())
				.caption(qrCode.getCaption())
				.build();
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
		final DeviceId newScaleDeviceId = DeviceQRCode.ofGlobalQRCodeJsonString(request.getScannedBarcode()).getDeviceId();
		return ManufacturingMobileApplication.mapDocument(
				request.getWfProcess(),
				job -> manufacturingJobService.withCurrentScaleDevice(job, newScaleDeviceId)
		);
	}
}
