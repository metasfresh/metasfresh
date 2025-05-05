/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.ui.web.contract.flatrate.process;

import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.X_C_Flatrate_DataEntry;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocumentBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;

public class WEBUI_C_Flatrate_DataEntry_ReactivateIt extends JavaProcess implements IProcessPrecondition
{

	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		final I_C_Flatrate_DataEntry entryRecord = ProcessUtil.extractEntryOrNull(context);
		if (entryRecord == null)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		
		if (!DocStatus.ofCode(entryRecord.getDocStatus()).isCompleted())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("C_Flatrate_DataEntry not completed");
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected final String doIt()
	{
		final I_C_Flatrate_DataEntry entryRecord = ProcessUtil.extractEntry(getProcessInfo());

		documentBL.processEx(entryRecord, X_C_Flatrate_DataEntry.DOCACTION_Re_Activate, X_C_Flatrate_DataEntry.DOCSTATUS_InProgress);

		return MSG_OK;
	}
}
