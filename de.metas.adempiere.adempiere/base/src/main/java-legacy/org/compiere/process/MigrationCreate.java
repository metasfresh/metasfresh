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
package org.compiere.process;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.ad.migration.model.I_AD_Migration;
import org.adempiere.ad.migration.model.X_AD_MigrationStep;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.MSession;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

public class MigrationCreate extends JavaProcess
{

	private I_AD_Migration migrationFrom;
	private I_AD_Migration migrationTo;

	private int tableId = 0;
	private int recordId = 0;
	private String entityId = null;

	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] params = getParametersAsArray();
		for (ProcessInfoParameter p : params)
		{
			String para = p.getParameterName();
			if (para.equals("AD_Table_ID"))
				tableId = p.getParameterAsInt();
			else if (para.equals("Record_ID"))
				recordId = p.getParameterAsInt();
			else if (para.equals("EntityType"))
				entityId = (String)p.getParameter();
		}

		if (tableId == 0)
			tableId = getTable_ID();

		if (recordId == 0)
			recordId = getRecord_ID();

	}

	/**
	 * 
	 * Process to create migration from selected records
	 * 
	 * @author Paul Bowden, Adaxa Pty Ltd
	 * 
	 */
	@Override
	protected String doIt() throws Exception
	{

		I_AD_Migration migration = InterfaceWrapperHelper.create(getCtx(), I_AD_Migration.class, get_TrxName());

		MTable table = MTable.get(getCtx(), tableId);

		String whereClause;

		List<PO> pos;

		if (recordId > 0)
		{
			pos = new ArrayList<PO>(1);
			pos.add(table.getPO(recordId, get_TrxName()));
		}
		else
		{
			String where = "EntityType = ?";
			pos = table.createQuery(where, get_TrxName()).list();
		}

		final MSession session = MSession.get(getCtx(), false);
		final POInfo info = POInfo.getPOInfo(getCtx(), tableId, get_TrxName());
		for (PO po : pos)
		{
			Services.get(IMigrationLogger.class).logMigration(session, po, info, X_AD_MigrationStep.ACTION_Insert);
		}

		return "@OK@";
	}
}
