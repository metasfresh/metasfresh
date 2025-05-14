package de.metas.datev.process;

import de.metas.common.util.time.SystemTime;
import de.metas.datev.DATEVExportCreateLinesRequest;
import de.metas.datev.DATEVExportId;
import de.metas.datev.DATEVExportLinesRepository;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;

/*
 * #%L
 * metasfresh-datev
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class DATEV_CreateExportLines extends JavaProcess implements IProcessPrecondition
{
	private final DATEVExportLinesRepository datevExportLinesRepo = SpringContextHolder.instance.getBean(DATEVExportLinesRepository.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String SYS_CONFIG_ONE_LINE_PER_INVOICETAX = "DATEVExportLines_OneLinePerInvoiceTax";

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
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
		final int countCreated = datevExportLinesRepo.createLines(
				DATEVExportCreateLinesRequest.builder()
						.datevExportId(DATEVExportId.ofRepoId(getRecord_ID()))
						.now(SystemTime.asInstant())
						.userId(getUserId())
						.isOneLinePerInvoiceTax(isOneLinePerInvoiceTax())
						.build());

		return "@Created@ #" + countCreated;
	}

	private boolean isOneLinePerInvoiceTax()
	{
		return sysConfigBL.getBooleanValue(SYS_CONFIG_ONE_LINE_PER_INVOICETAX, false);
	}
}
