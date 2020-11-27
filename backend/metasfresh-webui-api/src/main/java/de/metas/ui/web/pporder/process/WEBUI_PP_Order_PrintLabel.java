/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.pporder.process;

import de.metas.process.AdProcessId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.report.ExecuteReportStrategy.ExecuteReportResult;
import de.metas.report.ExecuteReportStrategyUtil;
import de.metas.report.server.OutputType;

public class WEBUI_PP_Order_PrintLabel
		extends WEBUI_PP_Order_HUEditor_ProcessBase
		implements IProcessPrecondition
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		//
		// OK
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{

		
		final AdProcessId labelProcessId = retrieveProcessIdBySysConfig(584768);
		final byte[] data = ExecuteReportStrategyUtil.executeJasperProcess(labelProcessId.getRepoId(), getProcessInfo(), OutputType.PDF);

		return ExecuteReportResult.of(OutputType.PDF, data).toString();

	}
	
	
	private AdProcessId retrieveProcessIdBySysConfig(final int reportProcessId)
	{
		return AdProcessId.ofRepoIdOrNull(reportProcessId);
	}


}