package de.metas.datev.process;

import org.compiere.Adempiere;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.datev.DATEVExportLinesRepository;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;

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
	@Autowired
	private DATEVExportLinesRepository datevExportLinesRepo;

	public DATEV_CreateExportLines()
	{
		Adempiere.autowire(this);
	}

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
		final int datevExportId = getRecord_ID();

		datevExportLinesRepo.deleteAllByExportId(datevExportId);

		final int created = datevExportLinesRepo.createLinesFromConfig(datevExportId);

		return "@Created@ #" + created;
	}

}
