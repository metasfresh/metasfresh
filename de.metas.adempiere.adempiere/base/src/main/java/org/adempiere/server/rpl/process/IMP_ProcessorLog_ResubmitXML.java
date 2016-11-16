package org.adempiere.server.rpl.process;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.server.rpl.api.IIMPProcessorBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_IMP_ProcessorLog;
import org.compiere.model.Query;

import de.metas.process.SvrProcess;

public class IMP_ProcessorLog_ResubmitXML extends SvrProcess
{
	@Override
	protected void prepare()
	{
	}

	@Override
	protected String doIt() throws Exception
	{
		final Iterator<I_IMP_ProcessorLog> logs = retrieveLogs();

		Services.get(IIMPProcessorBL.class).resubmit(logs, false, this);

		return "OK";
	}

	private Iterator<I_IMP_ProcessorLog> retrieveLogs()
	{
		final StringBuilder whereClause = new StringBuilder("1=1");
		final List<Object> params = new ArrayList<Object>();

		// Window selection
		final String processWhereClause = getProcessInfo().getWhereClause();
		if (!Check.isEmpty(processWhereClause, true))
		{
			whereClause.append(" AND (").append(processWhereClause).append(")");
		}

		// Only those who have errors
		whereClause.append(" AND ").append(I_IMP_ProcessorLog.COLUMNNAME_IsError).append("=?");
		params.add(true);

		return new Query(getCtx(), I_IMP_ProcessorLog.Table_Name, whereClause.toString(), ITrx.TRXNAME_None)
				.setParameters(params)
				// .setApplyAccessFilterRW(true) // if a user can open the window and see the error-log records, we want to let him/her resubmit them
				.setOrderBy(I_IMP_ProcessorLog.COLUMNNAME_IMP_ProcessorLog_ID)
				.iterate(I_IMP_ProcessorLog.class, false); // guaranteed=false
	}

}
