package de.metas.ui.web.handlingunits.process;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.qrcodes.service.QRCodeConfigurationService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ParamBarcodeScannerType;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.WebuiProcess;
import de.metas.ui.web.window.datatypes.PanelLayoutType;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * It takes selected HU, checks if is one piece and then assigns scanned QR Code to it.
 *
 */
@WebuiProcess(layoutType = PanelLayoutType.SingleOverlayField)
public class WEBUI_M_HU_Assign_QRCode extends JavaProcess implements IProcessPrecondition
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);
	private final QRCodeConfigurationService qrCodeConfigurationService = SpringContextHolder.instance.getBean(QRCodeConfigurationService.class);

	@Param(parameterName = "Barcode", mandatory = true, barcodeScannerType = ParamBarcodeScannerType.QRCode)
	private String p_Barcode;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final int huId = context.getSingleSelectedRecordId();
		final I_M_HU hu = handlingUnitsDAO.getById(HuId.ofRepoId(huId));
		final QtyTU qtyTU = handlingUnitsBL.getTUsCount(hu);

		if (handlingUnitsBL.isAggregateHU(hu) && qtyTU.toInt() > 1)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("HU is aggregated and Qty is bigger then 1. Cannot assign QR code to it.");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final HUQRCode huQRCode = HUQRCode.fromGlobalQRCodeJsonString(p_Barcode);
		final HuId selectedHuId = HuId.ofRepoId(getRecord_ID());
		final boolean ensureSingleAssignment = !qrCodeConfigurationService.isOneQrCodeForAggregatedHUsEnabledFor(handlingUnitsBL.getById(selectedHuId));
		huQRCodesService.assign(huQRCode, selectedHuId, ensureSingleAssignment);

		return MSG_OK;
	}

}