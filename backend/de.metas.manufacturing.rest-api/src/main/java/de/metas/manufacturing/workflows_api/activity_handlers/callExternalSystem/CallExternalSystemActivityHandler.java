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

import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.config.qrcode.ExternalSystemConfigQRCode;
import de.metas.externalsystem.export.pporder.ExportPPOrderToExternalSystem;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
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
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Component;

@Component
public class CallExternalSystemActivityHandler implements WFActivityHandler, SetScannedBarcodeSupport
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("manufacturing.callExternalSystem");

	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final ExportPPOrderToExternalSystem exportToExternalSystemService;

	public CallExternalSystemActivityHandler(
			@NonNull final ExportPPOrderToExternalSystem exportToExternalSystemService)
	{
		this.exportToExternalSystemService = exportToExternalSystemService;
	}

	@Override
	public WFActivityType getHandledActivityType() {return HANDLED_ACTIVITY_TYPE;}

	@Override
	public UIComponent getUIComponent(final @NonNull WFProcess wfProcess, final @NonNull WFActivity wfActivity, final @NonNull JsonOpts jsonOpts)
	{
		return SetScannedBarcodeSupportHelper.uiComponent()
				.alwaysAvailableToUser(WFActivityAlwaysAvailableToUser.YES)
				.build();
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity wfActivity)
	{
		return WFActivityStatus.COMPLETED;
	}

	@Override
	public WFProcess setScannedBarcode(final SetScannedBarcodeRequest request)
	{
		final ExternalSystemConfigQRCode configQRCode = ExternalSystemConfigQRCode.ofGlobalQRCodeJsonString(request.getScannedBarcode());
		final IExternalSystemChildConfigId childConfigId = configQRCode.getChildConfigId();

		final WFProcess wfProcess = request.getWfProcess();
		final ManufacturingJob manufacturingJob = wfProcess.getDocumentAs(ManufacturingJob.class);
		final PPOrderId ppOrderId = manufacturingJob.getPpOrderId();

		final PInstanceId pInstanceId = adPInstanceDAO.createSelectionId();

		exportToExternalSystemService.exportToExternalSystem(
				childConfigId,
				TableRecordReference.of(I_PP_Order.Table_Name, ppOrderId),
				pInstanceId);

		return wfProcess;
	}

}
