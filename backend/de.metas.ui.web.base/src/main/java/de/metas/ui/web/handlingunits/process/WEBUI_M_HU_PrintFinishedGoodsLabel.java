package de.metas.ui.web.handlingunits.process;

import java.util.List;

import org.springframework.context.annotation.Profile;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.handlingunits.report.HUReportExecutor;
import de.metas.handlingunits.report.HUReportService;
import de.metas.handlingunits.report.HUToReport;
import de.metas.process.AdProcessId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

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
@Profile(Profiles.PROFILE_Webui)
public class WEBUI_M_HU_PrintFinishedGoodsLabel
		extends HUEditorProcessTemplate
		implements IProcessPrecondition
{

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isHUEditorView())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not the HU view");
		}

		final HUReportService huReportService = HUReportService.get();

		final AdProcessId adProcessId = huReportService.retrievePrintFinishedGoodsLabelProcessIdOrNull();
		if (adProcessId == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Finished Goods label process not configured via sysconfig " + HUReportService.SYSCONFIG_FINISHEDGOODS_LABEL_PROCESS_ID);
		}
		
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (selectedRowIds.isMoreThanOneDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final HUReportService huReportService = HUReportService.get();

		final AdProcessId adProcessId = huReportService.retrievePrintFinishedGoodsLabelProcessIdOrNull();
		final HUToReport hu = getSingleSelectedRow().getAsHUToReport();

		final List<HUToReport> husToProcess = huReportService.getHUsToProcess(hu, adProcessId)
				.stream()
				.collect(ImmutableList.toImmutableList());

		HUReportExecutor.newInstance(getCtx())
				.executeHUReportAfterCommit(adProcessId, husToProcess);

		return MSG_OK;
	}

}
