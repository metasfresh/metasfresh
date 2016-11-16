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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_PeriodControl;
import org.compiere.model.X_C_PeriodControl;
import org.compiere.util.CacheMgt;

import de.metas.adempiere.service.IPeriodBL;
import de.metas.process.SvrProcess;

/**
 * Open/Close Period Control
 *
 * @author Jorg Janke
 * @version $Id: PeriodControlStatus.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class PeriodControlStatus extends SvrProcess
{
	private int periodId;

	@Override
	protected void prepare()
	{
		// nothing
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_C_PeriodControl pc = getRecord(I_C_PeriodControl.class);
		this.periodId = pc.getC_Period_ID();

		// Permanently closed
		if (X_C_PeriodControl.PERIODSTATUS_PermanentlyClosed.equals(pc.getPeriodStatus()))
		{
			throw new AdempiereException("@PeriodStatus@ = " + pc.getPeriodStatus());
		}

		// No Action => do nothing
		if (X_C_PeriodControl.PERIODACTION_NoAction.equals(pc.getPeriodAction()))
		{
			return "@OK@";
		}

		//
		// Update the period control
		final String newPeriodStatus = Services.get(IPeriodBL.class).getPeriodStatusForAction(pc.getPeriodAction());
		pc.setPeriodStatus(newPeriodStatus);
		pc.setPeriodAction(X_C_PeriodControl.PERIODACTION_NoAction); // reset the action
		InterfaceWrapperHelper.save(pc);

		return "@OK@";
	}	// doIt
	
	@Override
	protected void postProcess(boolean success)
	{
		// Reset Cache
		CacheMgt.get().reset(I_C_PeriodControl.Table_Name);
		CacheMgt.get().reset(I_C_Period.Table_Name, periodId);
	}
}	// PeriodControlStatus
