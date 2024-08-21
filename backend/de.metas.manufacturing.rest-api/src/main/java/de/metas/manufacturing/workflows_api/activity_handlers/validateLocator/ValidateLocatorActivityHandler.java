/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.manufacturing.workflows_api.activity_handlers.validateLocator;

import com.google.common.collect.ImmutableList;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.i18n.AdMessageKey;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivity;
import de.metas.manufacturing.job.model.ManufacturingJobActivityId;
import de.metas.manufacturing.job.model.ValidateLocatorInfo;
import de.metas.manufacturing.job.service.ManufacturingJobService;
import de.metas.manufacturing.workflows_api.ManufacturingMobileApplication;
import de.metas.manufacturing.workflows_api.ManufacturingRestService;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.JsonQRCode;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.SetScannedBarcodeRequest;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.SetScannedBarcodeSupport;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.SetScannedBarcodeSupportHelper;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.UIComponentType;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFActivityType;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.service.WFActivityHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ValidateLocatorActivityHandler implements WFActivityHandler, SetScannedBarcodeSupport
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.validateLocators");

	private static final AdMessageKey NO_SOURCE_LOCATOR_ERR_MSG = AdMessageKey.of("de.metas.manufacturing.NO_SOURCE_LOCATOR");
	private static final AdMessageKey QR_CODE_DOES_NOT_MATCH_ERR_MSG = AdMessageKey.of("de.metas.manufacturing.QR_CODE_DOES_NOT_MATCH_ERR_MSG");
	private static final AdMessageKey QR_CODE_INVALID_TYPE_ERR_MSG = AdMessageKey.of("de.metas.manufacturing.QR_CODE_INVALID_TYPE_ERR_MSG");

	private final ManufacturingJobService manufacturingJobService;

	@Override
	public WFActivityType getHandledActivityType()
	{
		return HANDLED_ACTIVITY_TYPE;
	}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		final JsonQRCode jsonQRCode = Optional.ofNullable(getScannedQRCode(wfProcess, wfActivity))
				.map(LocatorQRCode::ofGlobalQRCode)
				.map(qrCode -> JsonQRCode.builder()
						.qrCode(qrCode.toGlobalQRCodeJsonString())
						.caption(qrCode.getCaption())
						.build())
				.orElse(null);

		return SetScannedBarcodeSupportHelper.uiComponent()
				.componentType(UIComponentType.SCAN_AND_VALIDATE_BARCODE)
				.alwaysAvailableToUser(wfActivity.getAlwaysAvailableToUser())
				.currentValue(jsonQRCode)
				.validOptions(getSourceLocatorQRCodes(wfProcess, wfActivity))
				.build();
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		return getScannedQRCode(wfProcess, wfActivity) != null
				? WFActivityStatus.COMPLETED
				: WFActivityStatus.NOT_STARTED;
	}

	@Override
	public WFProcess setScannedBarcode(@NonNull final SetScannedBarcodeRequest request)
	{
		final ManufacturingJobActivityId jobActivityId = request.getWfActivity().getId().getAsId(ManufacturingJobActivityId.class);
		final ManufacturingJob job = ManufacturingMobileApplication.getManufacturingJob(request.getWfProcess());
		final ManufacturingJobActivity activity = job.getActivityById(jobActivityId);

		final GlobalQRCode scannedQRCode = GlobalQRCode.ofString(request.getScannedBarcode());
		validateScannedQRCode(activity, scannedQRCode);

		final ManufacturingJob changedJob = manufacturingJobService.withScannedQRCode(job, jobActivityId, scannedQRCode);
		return ManufacturingRestService.toWFProcess(changedJob);
	}

	@Nullable
	private ImmutableList<JsonQRCode> getSourceLocatorQRCodes(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity)
	{
		final ManufacturingJob job = ManufacturingMobileApplication.getManufacturingJob(wfProcess);
		final ManufacturingJobActivity activity = job.getActivityById(wfActivity.getId());

		if (activity.getSourceLocatorValidate() == null)
		{
			return null;
		}

		return activity.getSourceLocatorValidate()
				.getSourceLocatorList()
				.stream()
				.map(locatorInfo -> JsonQRCode.builder()
						.caption(locatorInfo.getCaption())
						.qrCode(LocatorQRCode.builder()
										.locatorId(locatorInfo.getId())
										.caption(locatorInfo.getCaption())
										.build().toGlobalQRCodeJsonString())
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	private static GlobalQRCode getScannedQRCode(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity)
	{
		final ManufacturingJob job = ManufacturingMobileApplication.getManufacturingJob(wfProcess);
		final ManufacturingJobActivityId jobActivityId = wfActivity.getId().getAsId(ManufacturingJobActivityId.class);
		final ManufacturingJobActivity jobActivity = job.getActivityById(jobActivityId);
		return jobActivity.getScannedQRCode();
	}

	private static void validateScannedQRCode(
			@NonNull final ManufacturingJobActivity activity,
			@NonNull final GlobalQRCode qrCode)
	{
		final ValidateLocatorInfo validateLocatorInfo = activity.getSourceLocatorValidate();
		if (validateLocatorInfo == null)
		{
			throw new AdempiereException(NO_SOURCE_LOCATOR_ERR_MSG);
		}

		if (!LocatorQRCode.isTypeMatching(qrCode))
		{
			throw new AdempiereException(QR_CODE_INVALID_TYPE_ERR_MSG);
		}

		final LocatorId scannedLocatorId = LocatorQRCode.ofGlobalQRCode(qrCode).getLocatorId();

		if (!validateLocatorInfo.isLocatorIdValid(scannedLocatorId))
		{
			throw new AdempiereException(QR_CODE_DOES_NOT_MATCH_ERR_MSG);
		}
	}
}
