/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.table.ddl.TableDDLSyncService;
import org.compiere.SpringContextHolder;

import java.util.List;

/**
 * Synchronize Column with Database
 */
public class ColumnSync extends JavaProcess implements IProcessPrecondition
{
	private final TableDDLSyncService syncService = SpringContextHolder.instance.getBean(TableDDLSyncService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final AdColumnId adColumnId = AdColumnId.ofRepoId(context.getSingleSelectedRecordId());
		if (!syncService.isSynchronizableColumn(adColumnId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not available for synchronization");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final AdColumnId adColumnId = AdColumnId.ofRepoId(getRecord_ID());
		final List<String> sqlStatements = syncService.syncToDatabase(adColumnId);
		sqlStatements.forEach(this::addLog);

		return MSG_OK;
	}
}
