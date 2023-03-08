/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.bpartner.impexp.blockstatus.process;

import de.metas.bpartner.blockstatus.file.BPartnerBlockFileId;
import de.metas.bpartner.blockstatus.file.BPartnerBlockFileService;
import de.metas.bpartner.blockstatus.file.BPartnerBlockStatusFile;
import de.metas.impexp.DataImportService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.report.ReportResultData;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class GenerateTemplate extends JavaProcess implements IProcessPrecondition
{
	private final DataImportService dataImportService = SpringContextHolder.instance.getBean(DataImportService.class);
	private final BPartnerBlockFileService bPartnerBlockFileService = SpringContextHolder.instance.getBean(BPartnerBlockFileService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final BPartnerBlockFileId blockFileId = BPartnerBlockFileId.ofRepoId(getProcessInfo().getRecord_ID());
		final BPartnerBlockStatusFile blockFile = bPartnerBlockFileService.getById(blockFileId);

		final ReportResultData reportResultData = dataImportService.generateTemplate(blockFile.getDataImportConfigId());

		getProcessInfo().getResult().setReportData(reportResultData);

		return MSG_OK;
	}
}
