package de.metas.handlingunits.process;

import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;
import org.compiere.SpringContextHolder;

import java.util.Set;

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
 * Glue-code process to be called from M_HU_Report.
 * It takes M_HU_IDs from T_Selection, gets/generates QR-Codes for them
 * and then generate the PDF.
 */
public class M_HU_Report_QRCode extends JavaProcess
{
	private final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final Set<HuId> huIds = handlingUnitsBL.getHuIdsBySelectionId(getPinstanceId());

		if (getProcessInfo().isPrintPreview())
		{
			final QRCodePDFResource pdf = huQRCodesService.createPdfForHUIds(huIds);
			getResult().setReportData(pdf, pdf.getFilename(), pdf.getContentType());
		}
		else
		{
			huQRCodesService.printForHUIds(huIds);
		}

		return MSG_OK;
	}
}
