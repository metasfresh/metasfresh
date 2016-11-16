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

import org.adempiere.ad.migration.model.I_AD_Migration;
import org.adempiere.ad.migration.service.IMigrationBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

public class MigrationMerge extends SvrProcess {

	private I_AD_Migration migrationFrom;
	private I_AD_Migration migrationTo;

	/**
	 * 
	 * Process to merge two migrations together
	 * 
	 * @author Paul Bowden, Adaxa Pty Ltd
	 *
	 */
	@Override
	protected String doIt() throws Exception {

		if ( migrationFrom == null || migrationFrom.getAD_Migration_ID() <= 0 
				|| migrationTo == null || migrationTo.getAD_Migration_ID() <= 0
				|| migrationFrom.getAD_Migration_ID() == migrationTo.getAD_Migration_ID() )
		{
			addLog("Two different existing migrations required for merge");
			return "@Error@";
		}
		
		Services.get(IMigrationBL.class).mergeMigration(migrationTo, migrationFrom);
		
		return "@OK@";
	}

	@Override
	protected void prepare() {
		
		int fromId = 0, toId = 0;
		
		ProcessInfoParameter[] params = getParametersAsArray();
		for ( ProcessInfoParameter p : params)
		{
			String para = p.getParameterName();
			if ( para.equals("AD_MigrationFrom_ID") )
				fromId  = p.getParameterAsInt();
			else if ( para.equals("AD_MigrationTo_ID") )
				toId = p.getParameterAsInt();
		}

		// launched from migration window
		if ( toId == 0 )
			toId = getRecord_ID();
		
		migrationTo = InterfaceWrapperHelper.create(getCtx(), toId, I_AD_Migration.class, get_TrxName());
		migrationFrom = InterfaceWrapperHelper.create(getCtx(), fromId, I_AD_Migration.class, get_TrxName());
	}

}
