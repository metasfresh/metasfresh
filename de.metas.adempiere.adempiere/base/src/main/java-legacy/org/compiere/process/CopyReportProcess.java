/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2007 ADempiere, Inc. All Rights Reserved.                    *
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
 * Adempiere, Inc.                                                            *
 *****************************************************************************/
package org.compiere.process;

import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProcess;
import org.compiere.model.MProcessPara;
import org.compiere.util.Msg;

/**
 * 
 * @author Paul Bowden (phib)
 * Adaxa Pty Ltd
 * Copy settings and parameters from source "Report and Process" to target
 * overwrites existing data (including translations)
 *
 */
public class CopyReportProcess extends SvrProcess {

	private int sourceId = 0;
	private int targetId = 0;

	@Override
	protected String doIt() throws Exception {
		
		MProcess source = new MProcess(getCtx(), sourceId, get_TrxName());
		MProcess target = new MProcess(getCtx(), targetId, get_TrxName());
		
		if ( sourceId <= 0 || targetId <= 0 || source == null || target == null )
			throw new AdempiereException(Msg.getMsg(getCtx(), "CopyProcessRequired"));
		
		target.copyFrom(source);  // saves automatically
		
		return "@OK@";
		
	}

	@Override
	protected void prepare() {
		
		ProcessInfoParameter[] params = getParameter();
		for (ProcessInfoParameter parameter : params)
		{
			String para = parameter.getParameterName();
			if ( para.equals("AD_Process_ID") )
				sourceId = parameter.getParameterAsInt();
			else if ( para.equals("AD_Process_To_ID"))
				targetId = parameter.getParameterAsInt();
			else
				log.log(Level.WARNING, "Unknown paramter: " + para);
		}
		
		if ( targetId == 0 )
			targetId = getRecord_ID();

	}

}
