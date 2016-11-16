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

import org.adempiere.ad.service.IADProcessDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Process;

/**
 *
 * @author Paul Bowden (phib)
 *         Adaxa Pty Ltd
 *         Copy settings and parameters from source "Report and Process" to target
 *         overwrites existing data (including translations)
 *
 */
public class CopyReportProcess extends SvrProcess
{

	private int sourceId = 0;
	private int targetId = 0;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter parameter : getParameters())
		{
			final String para = parameter.getParameterName();
			if ("AD_Process_ID".equals(para))
			{
				sourceId = parameter.getParameterAsInt();
			}
			else if ("AD_Process_To_ID".equals(para))
			{
				targetId = parameter.getParameterAsInt();
			}
		}

		if (targetId <= 0 && I_AD_Process.Table_Name.equals(getTableName()))
		{
			targetId = getRecord_ID();
		}

	}

	@Override
	protected String doIt() throws Exception
	{
		final I_AD_Process source = InterfaceWrapperHelper.create(getCtx(), sourceId, I_AD_Process.class, get_TrxName());
		final I_AD_Process target = InterfaceWrapperHelper.create(getCtx(), targetId, I_AD_Process.class, get_TrxName());

		if (sourceId <= 0 || targetId <= 0 || source == null || target == null)
		{
			throw new AdempiereException("@CopyProcessRequired@");
		}

		Services.get(IADProcessDAO.class).copyAD_Process(target, source);

		return MSG_OK;
	}
}
