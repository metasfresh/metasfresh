package de.metas.handlingunits.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.HUReportService;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.PInstanceRequest;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.report.client.ReportsClient;
import de.metas.report.server.OutputType;
import de.metas.report.server.ReportResult;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.SpringContextHolder;
import org.springframework.core.io.ByteArrayResource;

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
 * It takes M_HU_IDs (LUs) from T_Selection, gets/generates QR-Codes for them
 * and then generate the PDF that will contain the QR Code and more detailed product infos.
 */

public class M_HU_Report_LU_Label extends JavaProcess implements IProcessPrecondition
{
	private final HUReportService huReportService = HUReportService.get();
	private final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);
	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{

		final HuId huId = queryBL.createQueryBuilder(I_M_HU.class)
				.setOnlySelection(getPinstanceId())
				.create()
				.firstId(HuId::ofRepoId);

		generateQrCode(huId);

		// print
		final ReportResult label = printLabel(huId);

		getResult().setReportData(new ByteArrayResource(label.getReportContent()), label.getReportFilename(), OutputType.PDF.getContentType());

		return MSG_OK;
	}

	private void generateQrCode(@NonNull final HuId huId)
	{
		huQRCodesService.generateForExistingHU(huId);
	}

	private ReportResult printLabel(@NonNull final HuId huId)
	{
		final AdProcessId processId = huReportService.retrievePrintLUQRCodeLabelProcessId();

		final PInstanceId pinstanceId = adPInstanceDAO.createADPinstanceAndADPInstancePara(
				PInstanceRequest.builder()
						.processId(processId)
						.processParams(ImmutableList.of(ProcessInfoParameter.of(I_M_HU.COLUMNNAME_M_HU_ID, huId)
						))
						.build());

		final ProcessInfo jasperProcessInfo = ProcessInfo.builder()
				.setCtx(getCtx())
				.setAD_Process_ID(processId)
				.setAD_PInstance(adPInstanceDAO.getById(pinstanceId))
				.setReportLanguage(getProcessInfo().getReportLanguage())
				.setJRDesiredOutputType(OutputType.PDF)
				.build();

		final ReportsClient reportsClient = ReportsClient.get();

		return reportsClient.report(jasperProcessInfo);
	}
}
