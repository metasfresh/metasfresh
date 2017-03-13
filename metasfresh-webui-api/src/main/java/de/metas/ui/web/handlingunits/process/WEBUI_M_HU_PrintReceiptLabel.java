package de.metas.ui.web.handlingunits.process;

import java.util.List;
import java.util.Optional;

import org.compiere.model.I_AD_Process;
import org.springframework.context.annotation.Profile;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.report.HUReportExecutor;
import de.metas.handlingunits.report.HUReportService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.WebRestApiApplication;

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
@Profile(WebRestApiApplication.PROFILE_Webui)
public class WEBUI_M_HU_PrintReceiptLabel
		extends HUViewProcessTemplate
		implements IProcessPrecondition
{
	@Param(mandatory = true, parameterName = "Copies")
	private int p_copies = 1;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final HUReportService huReportService = HUReportService.get();

		final Optional<I_AD_Process> process = huReportService.retrievePrintReceiptLabelProcess(getCtx());
		if (!process.isPresent())
		{
			return ProcessPreconditionsResolution.reject("Receipt label process not configured via sysconfig " + HUReportService.SYSCONFIG_RECEIPT_LABEL_PROCESS_ID);
		}
		if (getSelectedDocumentIds().size() != 1)
		{
			return ProcessPreconditionsResolution.reject("No (single) row selected");
		}

		final I_M_HU hu = getSingleSelectedRow().getM_HU();
		if (hu == null)
		{
			return ProcessPreconditionsResolution.reject("No (single) HU selected");
		}

		final List<I_M_HU> husToProcess = huReportService.getHUsToProcess(hu, process.get());
		if (husToProcess.isEmpty())
		{
			return ProcessPreconditionsResolution.reject("current HU's type does not match the receipt label process");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final HUReportService huReportService = HUReportService.get();

		final Optional<I_AD_Process> process = huReportService.retrievePrintReceiptLabelProcess(getCtx());
		final I_M_HU hu = getSingleSelectedRow().getM_HU();

		final List<I_M_HU> husToProcess = huReportService.getHUsToProcess(hu, process.get());

		HUReportExecutor.get(getCtx())
				.withWindowNo(getProcessInfo().getWindowNo())
				.withNumberOfCopies(p_copies)
				.executeHUReportAfterCommit(process.get(), husToProcess);

		return MSG_OK;
	}

}
