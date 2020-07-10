package de.metas.acct.process;

import java.util.List;

import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.api.impl.CopyColumnsProducer;
import org.adempiere.ad.table.api.impl.CopyColumnsResult;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;

import de.metas.acct.doc.AcctDocRegistry;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Loggables;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.acct.base
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

public class AD_Table_CopyColumnsToAllAcctDocTables extends JavaProcess implements IProcessPrecondition
{
	private final IADTableDAO adTablesRepo = Services.get(IADTableDAO.class);
	private final AcctDocRegistry acctDocRegistry = SpringContextHolder.instance.getBean(AcctDocRegistry.class);

	private static final String TABLENAME_X_AcctDocTableTemplate = "X_AcctDocTableTemplate";

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final AdTableId adTableId = AdTableId.ofRepoId(context.getSingleSelectedRecordId());
		String tableName = adTablesRepo.retrieveTableName(adTableId);
		if (!TABLENAME_X_AcctDocTableTemplate.equals(tableName))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not the accountable doc table template");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final I_AD_Table acctDocTableTemplate = adTablesRepo.retrieveTable(TABLENAME_X_AcctDocTableTemplate);
		final List<I_AD_Column> acctDocTableTemplateColumns = adTablesRepo.retrieveColumnsForTable(acctDocTableTemplate);

		for (final String acctDocTableName : acctDocRegistry.getDocTableNames())
		{
			final I_AD_Table acctDocTable = adTablesRepo.retrieveTable(acctDocTableName);

			final CopyColumnsResult result = CopyColumnsProducer.newInstance()
					.setLogger(Loggables.nop())
					.setTargetTable(acctDocTable)
					.setSourceColumns(acctDocTableTemplateColumns)
					.setSyncDatabase(true)
					.create();

			addLog("" + result);
		}

		return MSG_OK;
	}
}
