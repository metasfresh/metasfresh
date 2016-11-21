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
package de.metas.banking.payment.process;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_I_BankStatement;
import org.compiere.model.MBankStatementLine;
import org.compiere.model.X_I_BankStatement;
import org.compiere.process.DocAction;

import de.metas.banking.interfaces.I_C_BankStatementLine_Ref;
import de.metas.banking.payment.IBankStatmentPaymentBL;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.document.engine.IDocActionBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * Renamed and refactored from <code>de.metas.banking.process.BankStatementPayment</code> (swat).
 * 
 * Create Payment from Bank Statement Info
 * 
 */
public class C_Payment_CreateFrom_BankStatement extends JavaProcess implements IProcessPrecondition
{
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

	private final IDocActionBL docActionBL = Services.get(IDocActionBL.class);

	/**
	 * Prepare - e.g., get Parameters.
	 */
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
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}
	}	// prepare

	/**
	 * Perform process.
	 * 
	 * @return Message
	 * @throws Exception if not successful
	 */
	@Override
	protected String doIt() throws Exception
	{
		final String tableName = getTableName();
		final int Record_ID = getRecord_ID();
		log.info("Table_Name=" + tableName + ", Record_ID=" + Record_ID);

		final IBankStatmentPaymentBL bankStatmentPaymentBL = Services.get(IBankStatmentPaymentBL.class);

		if (I_I_BankStatement.Table_Name.equals(tableName))
		{
			return bankStatmentPaymentBL.createPayment(new X_I_BankStatement(getCtx(), Record_ID, get_TrxName()));
		}
		else if (I_C_BankStatement.Table_Name.equals(tableName))
		{
			final I_C_BankStatement bankStatement = InterfaceWrapperHelper.create(getCtx(), Record_ID, I_C_BankStatement.class, getTrxName());
			final List<MBankStatementLine> bankStatementLines = bankStatementDAO.retrieveLines(bankStatement, MBankStatementLine.class);
			for (final MBankStatementLine bankStatementLine : bankStatementLines)
			{
				bankStatmentPaymentBL.createPayment(bankStatementLine);
			}
			return "@Success@";
		}
		else if (I_C_BankStatementLine.Table_Name.equals(tableName))
		{
			return bankStatmentPaymentBL.createPayment(new MBankStatementLine(getCtx(), Record_ID, get_TrxName()));
		}
		else if (I_C_BankStatementLine_Ref.Table_Name.equals(tableName))
		{
			return bankStatmentPaymentBL.createPayment(InterfaceWrapperHelper.create(getCtx(), Record_ID, I_C_BankStatementLine_Ref.class, get_TrxName()));
			// metas: end
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @TableName@: " + tableName);
		}
	}	// doIt

	/**
	 * @return <code>true</code> if the given gridTab belongs to a bank statement that is completed or closed.
	 */
	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		if (I_C_BankStatement.Table_Name.equals(context.getTableName()))
		{
			final I_C_BankStatement bankStatement = context.getModel(I_C_BankStatement.class);
			return docActionBL.isStatusOneOf(bankStatement,
					DocAction.STATUS_Completed, DocAction.STATUS_Closed);
		}
		return false;
	}
}
