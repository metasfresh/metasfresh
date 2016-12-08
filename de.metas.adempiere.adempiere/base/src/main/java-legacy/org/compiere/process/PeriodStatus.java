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

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_PeriodControl;
import org.compiere.model.X_C_PeriodControl;
import org.compiere.util.CacheMgt;

import de.metas.adempiere.service.IPeriodBL;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * Open/Close all Period (Control)
 *
 * @author Jorg Janke
 * @version $Id: PeriodStatus.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class PeriodStatus extends JavaProcess
{
	/** Action */
	private String p_PeriodAction = null;

	@Override
	protected void prepare()
	{
		final ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			final String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
			{
				;
			}
			else if (name.equals("PeriodAction"))
			{
				p_PeriodAction = (String)para[i].getParameter();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_C_Period period = getRecord(I_C_Period.class);
		final String newPeriodStatus = Services.get(IPeriodBL.class).getPeriodStatusForAction(p_PeriodAction);

		//
		// Retrieve period controls
		final List<I_C_PeriodControl> periodControls = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PeriodControl.class, getCtx(), getTrxName())
				.addEqualsFilter(I_C_PeriodControl.COLUMN_C_Period_ID, period.getC_Period_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		//
		// Update the period controls
		for (final I_C_PeriodControl pc : periodControls)
		{
			// Don't update permanently closed period controls
			if (X_C_PeriodControl.PERIODSTATUS_PermanentlyClosed.equals(pc.getPeriodStatus()))
			{
				continue;
			}

			pc.setPeriodStatus(newPeriodStatus);
			pc.setPeriodAction(X_C_PeriodControl.PERIODACTION_NoAction);
			InterfaceWrapperHelper.save(pc);
		}

		return "@Ok@";
	}
	
	@Override
	protected void postProcess(boolean success)
	{
		//
		// Reset caches
		CacheMgt.get().reset(I_C_PeriodControl.Table_Name);
		
		final int periodId = getRecord_ID();
		CacheMgt.get().reset(I_C_Period.Table_Name, periodId);
	}
}
