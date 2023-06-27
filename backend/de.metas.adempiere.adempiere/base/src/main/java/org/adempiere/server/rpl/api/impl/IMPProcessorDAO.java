package org.adempiere.server.rpl.api.impl;

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


import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.server.rpl.interfaces.I_IMP_Processor;
import org.compiere.model.AdempiereProcessorLog;
import org.compiere.model.I_IMP_ProcessorLog;
import org.compiere.model.Query;
import org.compiere.model.X_IMP_ProcessorLog;
import org.compiere.util.DB;

public class IMPProcessorDAO extends AbstractIMPProcessorDAO
{
	@Override
	public List<AdempiereProcessorLog> retrieveAdempiereProcessorLogs(final org.compiere.model.I_IMP_Processor impProcessor)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(impProcessor);
		final String trxName = InterfaceWrapperHelper.getTrxName(impProcessor);

		final String whereClause = I_IMP_Processor.COLUMNNAME_IMP_Processor_ID + "=?";
		return new Query(ctx, I_IMP_ProcessorLog.Table_Name, whereClause, trxName)
				.setParameters(impProcessor.getIMP_Processor_ID())
				.setOrderBy(I_IMP_ProcessorLog.COLUMNNAME_Created + " DESC")
				.list(AdempiereProcessorLog.class);
	}

	@Override
	public int deleteLogs(final org.compiere.model.I_IMP_Processor impProcessor, final boolean deleteAll)
	{
		final int keepLogDays = impProcessor.getKeepLogDays();

		if (!deleteAll && keepLogDays < 1)
		{
			return 0;
		}

		final StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM " + X_IMP_ProcessorLog.Table_Name + " ");
		sql.append("WHERE " + X_IMP_ProcessorLog.COLUMNNAME_IMP_Processor_ID + "=?");

		if (!deleteAll)
		{
			sql.append(" AND (Created+" + keepLogDays + ") < now()");
		}

		final String trxName = InterfaceWrapperHelper.getTrxName(impProcessor);
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(),
															   new Object[] { impProcessor.getIMP_Processor_ID() },
															   trxName);
		return no;
	}
}
