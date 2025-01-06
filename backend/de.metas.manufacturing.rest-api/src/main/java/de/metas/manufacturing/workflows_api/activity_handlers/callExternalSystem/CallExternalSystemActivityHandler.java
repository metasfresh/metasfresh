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

package de.metas.manufacturing.workflows_api.activity_handlers.callExternalSystem;

import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.config.qrcode.ExternalSystemConfigQRCode;
import de.metas.externalsystem.export.pporder.ExportPPOrderToExternalSystem;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivity;
import de.metas.manufacturing.job.model.ManufacturingJobActivityId;
import de.metas.manufacturing.job.service.ManufacturingJobService;
import de.metas.manufacturing.workflows_api.ManufacturingMobileApplication;
import de.metas.manufacturing.workflows_api.ManufacturingRestService;
import de.metas.resource.Resource;
import de.metas.resource.ResourceService;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.resource.qrcode.ResourceQRCode;
import de.metas.util.Services;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.JsonQRCode;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.SetScannedBarcodeRequest;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.SetScannedBarcodeSupport;
import de.metas.workflow.rest_api.activity_features.set_scanned_barcode.SetScannedBarcodeSupportHelper;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityAlwaysAvailableToUser;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFActivityType;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.service.WFActivityHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
@RequiredArgsConstructor
public class CallExternalSystemActivityHandler implements WFActivityHandler, SetScannedBarcodeSupport
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.callExternalSystem");
	public static final AdMessageKey RESEND_TO_SAME_MACHINE_MESSAGE_KEY = AdMessageKey.of("de.metas.manufacturing.callExternalSystem.ResendToSameMachine");
	public static final AdMessageKey NOT_AN_EXTERNAL_SYSTEM_ERR_MESSAGE_KEY = AdMessageKey.of("de.metas.manufacturing.callExternalSystem.NOT_AN_EXTERNAL_SYSTEM_ERR");

	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final ManufacturingJobService manufacturingJobService;
	private final ExportPPOrderToExternalSystem exportToExternalSystemService;
	private final ResourceService resourceService;
	private final ExternalSystemConfigRepo externalSystemConfigRepo;

	@Override
	public WFActivityType getHandledActivityType()
	{
		return HANDLED_ACTIVITY_TYPE;
	}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		final GlobalQRCode scannedQRCode = getScannedQRCode(wfProcess, wfActivity);

		return SetScannedBarcodeSupportHelper.uiComponent()
				.currentValue(toJsonQRCode(scannedQRCode))
				.alwaysAvailableToUser(WFActivityAlwaysAvailableToUser.YES)
				.confirmationModalMsg(msgBL.getMsg(Env.getAD_Language(), RESEND_TO_SAME_MACHINE_MESSAGE_KEY))
				.build();
	}

	@Nullable
	private static GlobalQRCode getScannedQRCode(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity)
	{
		final ManufacturingJob job = getManufacturingJob(wfProcess);
		final ManufacturingJobActivityId jobActivityId = getManufacturingJobActivityId(wfActivity);
		final ManufacturingJobActivity jobActivity = job.getActivityById(jobActivityId);
		return jobActivity.getScannedQRCode();
	}

	@NonNull
	private static ManufacturingJob getManufacturingJob(final @NonNull WFProcess wfProcess)
	{
		return ManufacturingMobileApplication.getManufacturingJob(wfProcess);
	}

	private static ManufacturingJobActivityId getManufacturingJobActivityId(final @NonNull WFActivity wfActivity)
	{
		return wfActivity.getId().getAsId(ManufacturingJobActivityId.class);
	}

	@Nullable
	private static JsonQRCode toJsonQRCode(@Nullable final GlobalQRCode scannedQRCode)
	{
		if (scannedQRCode == null)
		{
			return null;
		}

		if (ResourceQRCode.isTypeMatching(scannedQRCode))
		{
			final ResourceQRCode qrCode = ResourceQRCode.ofGlobalQRCode(scannedQRCode);
			return JsonQRCode.builder()
					.qrCode(qrCode.toGlobalQRCodeJsonString())
					.caption(qrCode.getCaption())
					.build();
		}
		else if (ExternalSystemConfigQRCode.isTypeMatching(scannedQRCode))
		{
			final ExternalSystemConfigQRCode configQRCode = ExternalSystemConfigQRCode.ofGlobalQRCode(scannedQRCode);
			return JsonQRCode.builder()
					.qrCode(configQRCode.toGlobalQRCodeJsonString())
					.caption(configQRCode.getCaption())
					.build();
		}
		else
		{
			throw new AdempiereException("Unknown QR code type=" + scannedQRCode.getType());
		}
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		return getScannedQRCode(wfProcess, wfActivity) != null
				? WFActivityStatus.COMPLETED
				: WFActivityStatus.NOT_STARTED;
	}

	@Override
	public WFProcess setScannedBarcode(final SetScannedBarcodeRequest request)
	{
		final GlobalQRCode scannedQRCode = GlobalQRCode.ofString(request.getScannedBarcode());
		final IExternalSystemChildConfigId childConfigId = getExternalSystemChildConfigId(scannedQRCode);

		final WFActivity wfActivity = request.getWfActivity();
		wfActivity.getWfActivityType().assertExpected(HANDLED_ACTIVITY_TYPE);
		final ManufacturingJobActivityId jobActivityId = getManufacturingJobActivityId(wfActivity);

		final ManufacturingJob job = getManufacturingJob(request.getWfProcess());
		callExternalSystem(childConfigId, job);

		final ManufacturingJob changedJob = manufacturingJobService.withScannedQRCode(job, jobActivityId, scannedQRCode);
		return ManufacturingRestService.toWFProcess(changedJob);
	}

	private void callExternalSystem(final IExternalSystemChildConfigId childConfigId, final ManufacturingJob job)
	{
		final PPOrderId ppOrderId = job.getPpOrderId();

		final PInstanceId pInstanceId = adPInstanceDAO.createSelectionId();

		exportToExternalSystemService.exportToExternalSystem(
				childConfigId,
				TableRecordReference.of(I_PP_Order.Table_Name, ppOrderId),
				pInstanceId);
	}

	@NonNull
	private IExternalSystemChildConfigId getExternalSystemChildConfigId(@NonNull final GlobalQRCode scannedQRCode)
	{
		if (ResourceQRCode.isTypeMatching(scannedQRCode))
		{
			return getExternalSystemChildConfigId(ResourceQRCode.ofGlobalQRCode(scannedQRCode));
		}
		else if (ExternalSystemConfigQRCode.isTypeMatching(scannedQRCode))
		{
			final ExternalSystemConfigQRCode configQRCode = ExternalSystemConfigQRCode.ofGlobalQRCode(scannedQRCode);
			return configQRCode.getChildConfigId();
		}
		else
		{
			throw new AdempiereException(NOT_AN_EXTERNAL_SYSTEM_ERR_MESSAGE_KEY);
		}
	}

	@NonNull
	private IExternalSystemChildConfigId getExternalSystemChildConfigId(@NonNull final ResourceQRCode resourceQRCode)
	{
		final Resource externalSystemResource = resourceService.getResourceById(resourceQRCode.getResourceId());
		if (!externalSystemResource.isExternalSystem())
		{
			throw new AdempiereException(NOT_AN_EXTERNAL_SYSTEM_ERR_MESSAGE_KEY);
		}

		return ExternalSystemParentConfigId.ofRepoIdOptional(externalSystemResource.getExternalSystemParentConfigId())
				.flatMap(externalSystemConfigRepo::getChildByParentId)
				.map(IExternalSystemChildConfig::getId)
				.orElseThrow(() -> new AdempiereException(NOT_AN_EXTERNAL_SYSTEM_ERR_MESSAGE_KEY));
	}
}
