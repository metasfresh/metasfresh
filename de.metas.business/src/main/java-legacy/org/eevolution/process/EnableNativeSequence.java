/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/

package org.eevolution.process;

import java.util.Properties;

import org.adempiere.ad.service.ISequenceDAO;
import org.adempiere.ad.service.ITableSequenceChecker;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.util.DB;

import de.metas.process.SvrProcess;

/**
 * Enable Native Sequence
 * 
 * @author Victor Perez, e-Evolution, S.C.
 */
public class EnableNativeSequence extends SvrProcess
{
	@Override
	protected void prepare()
	{
		// nothing
	}

	@Override
	protected String doIt() throws Exception
	{
		final boolean useNativeSequences = DB.isUseNativeSequences();

		if (useNativeSequences)
		{
			throw new AdempiereException("Native Sequence is Actived");
		}

		final Properties ctx = getCtx();
		// final String trxName = get_TrxName();

		final ITableSequenceChecker sequenceChecker = Services.get(ISequenceDAO.class).createTableSequenceChecker(ctx);
		sequenceChecker.setFailOnFirstError(true);
		sequenceChecker.setSequenceRangeCheck(true);

		DB.setUseNativeSequences(true);

		try
		{
			sequenceChecker.run();
		}
		catch (Exception e)
		{
			DB.setUseNativeSequences(false);
			throw e;
		}

		return "@OK@";
	}
}
