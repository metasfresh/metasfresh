package de.metas.ui.web.picking.pickingslot.process;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_PICK_SOMETHING;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_SELECT_PICKED_HU;
import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.List;
import java.util.Properties;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.report.HUReportExecutor;
import de.metas.handlingunits.report.HUReportService;
import de.metas.handlingunits.report.HUToReport;
import de.metas.handlingunits.report.HUToReportWrapper;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class WEBUI_Picking_TU_Label extends PickingSlotViewBasedProcess
{


	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		if (!pickingSlotRow.isPickedHURow())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKED_HU));
		}
		if (!pickingSlotRow.isTopLevelHU())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKED_HU));
		}

		if (pickingSlotRow.getHuQtyCU().signum() <= 0)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_PICK_SOMETHING));
		}



		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final PickingSlotRow rowToProcess = getSingleSelectedRow();
		
		final I_M_HU hu = load(rowToProcess.getHuId(), I_M_HU.class);
		final HUToReportWrapper huToReport = HUToReportWrapper.of(hu);
		
		printPickingLabel(huToReport);
		
		return MSG_OK;

		
	}
	
	private void printPickingLabel(final HUToReportWrapper huToReport)
	{
		if (huToReport == null)
		{
			addLog("Param 'hu'==null; nothing to do");
			return;
		}

		final HUReportService huReportService = HUReportService.get();
		
		if (!huToReport.isTopLevel())
		{
			addLog("We only print top level HUs; nothing to do; hu={}", huToReport);
			return;
		}

		final int adProcessId = huReportService.retrievePickingLabelProcessID();
		if (adProcessId <= 0)
		{
			addLog("No process configured via SysConfig {}; nothing to do", HUReportService.SYSCONFIG_PICKING_LABEL_PROCESS_ID);
			return;
		}

		final List<HUToReport> husToProcess = huReportService
				.getHUsToProcess(huToReport, adProcessId)
				.stream()
				.filter(HUToReport::isTopLevel) // gh #1160: here we need to filter because we still only want to process top level HUs (either LUs or TUs)
				.collect(ImmutableList.toImmutableList());

		if (husToProcess.isEmpty())
		{
			addLog("hu's type does not match process {}; nothing to do; hu={}", adProcessId, huToReport);
			return;
		}

		final Properties ctx = getCtx();
		HUReportExecutor.newInstance(ctx)
				.executeHUReportAfterCommit(adProcessId, husToProcess);

	}

}
