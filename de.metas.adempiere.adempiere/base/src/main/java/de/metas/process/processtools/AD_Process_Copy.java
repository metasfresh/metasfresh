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
package de.metas.process.processtools;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Process;

import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

/**
 *
 * @author Paul Bowden (phib)
 *         Adaxa Pty Ltd
 *         Copy settings and parameters from source "Report and Process" to target
 *         overwrites existing data (including translations)
 *
 */
public class AD_Process_Copy extends SvrProcess
{
	private int p_sourceProcessId = 0;
	private int p_targetProcessId = 0;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter parameter : getParameters())
		{
			final String para = parameter.getParameterName();
			if ("AD_Process_ID".equals(para))
			{
				p_sourceProcessId = parameter.getParameterAsInt();
			}
			else if ("AD_Process_To_ID".equals(para))
			{
				p_targetProcessId = parameter.getParameterAsInt();
			}
		}

		if (p_targetProcessId <= 0 && I_AD_Process.Table_Name.equals(getTableName()))
		{
			p_targetProcessId = getRecord_ID();
		}

	}

	@Override
	protected String doIt()
	{
		final I_AD_Process source = InterfaceWrapperHelper.create(getCtx(), p_sourceProcessId, I_AD_Process.class, get_TrxName());
		final I_AD_Process target = InterfaceWrapperHelper.create(getCtx(), p_targetProcessId, I_AD_Process.class, get_TrxName());

		if (p_sourceProcessId <= 0 || p_targetProcessId <= 0 || source == null || target == null)
		{
			throw new AdempiereException("@CopyProcessRequired@");
		}

		Services.get(IADProcessDAO.class).copyAD_Process(target, source);

		return MSG_OK;
	}
}
