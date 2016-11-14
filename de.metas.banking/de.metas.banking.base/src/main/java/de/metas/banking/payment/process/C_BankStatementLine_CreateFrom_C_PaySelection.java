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


import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_PaySelection;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import de.metas.banking.model.I_C_BankStatement;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.document.engine.IDocActionBL;

public class C_BankStatementLine_CreateFrom_C_PaySelection extends SvrProcess implements ISvrProcessPrecondition
{
	private final IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);

	private final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
	
	private int p_C_PaySelection_ID;

	@Override
	protected void prepare()
	{
		final ProcessInfoParameter[] para = getParametersAsArray();

		for (int i = 0; i < para.length; i++)
		{
			final String name = para[i].getParameterName();

			if (name.equals(I_C_PaySelection.COLUMNNAME_C_PaySelection_ID))
			{
				p_C_PaySelection_ID = para[i].getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		Check.errorIf(getRecord_ID() <= 0, "Process {} needs to be run with a Record_ID", this);

		final I_C_PaySelection paySelection = InterfaceWrapperHelper.create(getCtx(), p_C_PaySelection_ID, I_C_PaySelection.class, getTrxName());
		final I_C_BankStatement bankStatement = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), I_C_BankStatement.class, getTrxName());

		paySelectionBL.createBankStatementLines(bankStatement, paySelection);

		return "@Success@";
	}

	/**
	 * @return <code>true</code> if the given gridTab belongs to a bank statement that is drafted or in progress
	 */
	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		if (I_C_BankStatement.Table_Name.equals(context.getTableName()))
		{
			final I_C_BankStatement bankStatement = context.getModel(I_C_BankStatement.class);
			return docActionBL.isStatusOneOf(bankStatement,
					DocAction.STATUS_Drafted, DocAction.STATUS_InProgress);
		}
		return false;
	}
}
