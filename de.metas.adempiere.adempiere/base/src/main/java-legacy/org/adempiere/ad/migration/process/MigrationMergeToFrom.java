/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.adempiere.ad.migration.process;

import org.adempiere.ad.migration.model.I_AD_Migration;
import org.adempiere.ad.migration.service.IMigrationBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

/**
 * 
 * Process to merge two migrations together
 * 
 * @author Paul Bowden, Adaxa Pty Ltd
 * @author Teo Sarca
 * 
 */
public class MigrationMergeToFrom extends SvrProcess
{
	private I_AD_Migration migrationFrom;
	private I_AD_Migration migrationTo;
	private boolean isDeleteFrom = false;

	@Override
	protected void prepare()
	{
		int migrationId = -1;
		boolean isMergeTo = false;
		for (ProcessInfoParameter p : getParametersAsArray())
		{
			String para = p.getParameterName();
			if (para == null)
				continue;
			else if (para.equals("AD_Migration_ID"))
				migrationId = p.getParameterAsInt();
			else if (para.equals("IsMergeTo"))
				isMergeTo = p.getParameterAsBoolean();
			else if (para.equals("DeleteOld"))
				isDeleteFrom = true;
		}

		int recordId = -1;
		if (I_AD_Migration.Table_Name.equals(getTableName()) && getRecord_ID() > 0)
		{
			recordId = getRecord_ID();
		}

		final int fromId;
		final int toId;

		if (isMergeTo)
		{
			fromId = recordId;
			toId = migrationId;
		}
		else
		{
			fromId = migrationId;
			toId = recordId;
		}

		migrationTo = InterfaceWrapperHelper.create(getCtx(), toId, I_AD_Migration.class, get_TrxName());
		migrationFrom = InterfaceWrapperHelper.create(getCtx(), fromId, I_AD_Migration.class, get_TrxName());
	}

	@Override
	protected String doIt() throws Exception
	{

		if (migrationFrom == null || migrationFrom.getAD_Migration_ID() <= 0
				|| migrationTo == null || migrationTo.getAD_Migration_ID() <= 0
				|| migrationFrom.getAD_Migration_ID() == migrationTo.getAD_Migration_ID())
		{
			throw new AdempiereException("Two different existing migrations required for merge");
		}

		Services.get(IMigrationBL.class).mergeMigration(migrationTo, migrationFrom);
		addLog("Merged " + migrationFrom + " to " + migrationTo);

		if (isDeleteFrom)
		{
			InterfaceWrapperHelper.delete(migrationFrom);
			addLog("Deleted " + migrationFrom);
		}

		return "@OK@";
	}
}
