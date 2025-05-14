package de.metas.picking.workflow.handlers.activity_handlers;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.picking.workflow.handlers.PickingMobileApplication;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static de.metas.picking.workflow.handlers.activity_handlers.PickingWFActivityHelper.getPickingJob;

@Component
@RequiredArgsConstructor
public class SetPickFromHUWFActivityHandler implements WFActivityHandler, SetScannedBarcodeSupport
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("picking.setPickFromHU");

	@NonNull private final PickingJobRestService pickingJobRestService;
	@NonNull private final HUQRCodesService huQRCodesService;

	@Override
	public WFActivityType getHandledActivityType()
	{
		return HANDLED_ACTIVITY_TYPE;
	}

	@Override
	public UIComponent getUIComponent(
			final @NonNull WFProcess wfProcess,
			final @NonNull WFActivity wfActivity,
			final @NonNull JsonOpts jsonOpts)
	{
		final JsonQRCode pickFromHU = getPickingJob(wfProcess)
				.getPickFromHU()
				.map(SetPickFromHUWFActivityHandler::toJsonQRCode)
				.orElse(null);

		return SetScannedBarcodeSupportHelper.uiComponent()
				.currentValue(pickFromHU)
				.alwaysAvailableToUser(wfActivity.getAlwaysAvailableToUser())
				.build();
	}

	public static JsonQRCode toJsonQRCode(final HUInfo pickFromHU)
	{
		final HUQRCode qrCode = pickFromHU.getQrCode();
		return JsonQRCode.builder()
				.qrCode(qrCode.toGlobalQRCodeString())
				.caption(qrCode.toDisplayableQRCode())
				.build();
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		final PickingJob pickingJob = getPickingJob(wfProcess);
		return computeActivityState(pickingJob);
	}

	public static WFActivityStatus computeActivityState(final PickingJob pickingJob)
	{
		return pickingJob.getPickFromHU().isPresent() ? WFActivityStatus.COMPLETED : WFActivityStatus.NOT_STARTED;
	}

	@Override
	public WFProcess setScannedBarcode(@NonNull final SetScannedBarcodeRequest request)
	{
		final HUQRCode qrCode = HUQRCode.fromGlobalQRCodeJsonString(request.getScannedBarcode());
		final HuId huId = huQRCodesService.getHuIdByQRCode(qrCode);
		final HUInfo pickFromHU = HUInfo.builder()
				.id(huId)
				.qrCode(qrCode)
				.build();

		return PickingMobileApplication.mapPickingJob(
				request.getWfProcess(),
				pickingJob -> pickingJobRestService.setPickFromHU(pickingJob, pickFromHU)
		);
	}
}
