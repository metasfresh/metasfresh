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
package org.adempiere.acct.process;

import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

import org.adempiere.acct.api.impl.TaxDeclarationLinesBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableFail;
import org.adempiere.util.Services;
import org.compiere.model.I_C_TaxDeclaration;

/**
 * Create Tax Declaration
 */
public class C_TaxDeclaration_CreateLines extends JavaProcess
{
	/** Delete Old Lines */
	private boolean p_DeleteOld = true;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
			{
				continue;
			}
			//
			if (name.equals("DeleteOld"))
			{
				p_DeleteOld = para.getParameterAsBoolean();
			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}
	}	// prepare

	@Override
	protected String doIt()
	{
		// Retrieve the tax declaration header (out of transaction).
		final I_C_TaxDeclaration taxDeclaration = getProcessInfo().getRecord(I_C_TaxDeclaration.class, ITrx.TRXNAME_None);
		
		Services.get(ITrxManager.class).setThreadInheritedOnRunnableFail(OnRunnableFail.DONT_ROLLBACK); // i.e. don't create savepoints.

		final TaxDeclarationLinesBuilder taxDeclarationLinesBuilder = new TaxDeclarationLinesBuilder()
				.setC_TaxDeclaration(taxDeclaration)
				.setLoggable(this)
				.setDeleteOldLines(p_DeleteOld);

		taxDeclarationLinesBuilder.build();

		return taxDeclarationLinesBuilder.getResultSummary();
	}	// doIt
}
