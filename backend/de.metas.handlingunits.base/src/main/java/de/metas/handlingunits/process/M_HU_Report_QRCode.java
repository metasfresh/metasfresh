package de.metas.handlingunits.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.HUReportService;
import de.metas.printing.IMassPrintingService;
import de.metas.process.AdProcessId;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;
import de.metas.report.PrintCopies;
import de.metas.report.server.OutputType;
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
 * Glue-code process to be called from M_HU_Report.
 * It takes M_HU_IDs from T_Selection, gets/generates QR-Codes for them
 * and then generate the PDF.
 */

public class M_HU_Report_QRCode extends JavaProcess
{
	private final HUReportService huReportService = HUReportService.get();
	private final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);

	private static final String PARAM_AD_Process_ID = "AD_Process_ID";

	@Param(parameterName = PARAM_AD_Process_ID)
	private int processId;

	@Param(parameterName = "IsPrintPreview")
	private boolean isPrintPreview;

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final ImmutableSet<HuId> huIds = huReportService.getHuIdsFromSelection(getPinstanceId());
		final ImmutableList<HUQRCode> qrCodes = generateQrCodes(huIds);
		final AdProcessId adProcessId = AdProcessId.ofRepoId(processId);

		final QRCodePDFResource pdf = huQRCodesService.createPDF(qrCodes, getPinstanceId(), adProcessId);

		// print preview was set by the flag IsPrintPreview
		if (getProcessInfo().isPrintPreview())
		{
			getResult().setReportData(pdf, pdf.getFilename(), OutputType.PDF.getContentType());
		}
		else
		{
			final int copies = getProcessInfo().getParameterAsIParams().getParameterAsInt(IMassPrintingService.PARAM_PrintCopies, 1);
			huQRCodesService.print(pdf, PrintCopies.ofInt(copies));
		}

		return MSG_OK;
	}

	private ImmutableList<HUQRCode> generateQrCodes(@NonNull final ImmutableSet<HuId> huIds)
	{
		return huQRCodesService.generateForExistingHUs(huIds).toList();
	}
}
