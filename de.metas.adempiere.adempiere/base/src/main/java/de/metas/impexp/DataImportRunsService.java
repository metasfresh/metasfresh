package de.metas.impexp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.compiere.model.I_C_DataImport_Run;
import org.springframework.stereotype.Service;

import de.metas.impexp.config.DataImportConfigId;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Service
public class DataImportRunsService
{
	public DataImportRunId createNewRun(final DataImportRunCreateRequest request)
	{
		final I_C_DataImport_Run record = newInstance(I_C_DataImport_Run.class);
		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setAD_User_ID(request.getUserId().getRepoId());
		record.setIsDocComplete(request.isCompleteDocuments());
		record.setAD_ImpFormat_ID(request.getImportFormatId().getRepoId());
		record.setC_DataImport_ID(DataImportConfigId.toRepoId(request.getDataImportConfigId()));
		saveRecord(record);

		return DataImportRunId.ofRepoId(record.getC_DataImport_Run_ID());
	}
}
