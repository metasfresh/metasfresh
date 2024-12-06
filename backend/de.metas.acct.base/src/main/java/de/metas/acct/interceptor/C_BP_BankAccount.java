package de.metas.acct.interceptor;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AcctSchema_Default;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.ModelValidator;

/**
 * 
 * @author ts
 * @implSpec task 08354
 */
@Interceptor(I_C_BP_BankAccount.class)
public class C_BP_BankAccount
{
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_NEW_REPLICATION })
	public void insertAccounting(final I_C_BP_BankAccount bankAccount)
	{
		final String nullWhereClause = null;

		InterfaceWrapperHelper.getPO(bankAccount).insert_Accounting(
				I_C_BP_BankAccount.Table_Name + "_Acct",
				I_C_AcctSchema_Default.Table_Name,
				nullWhereClause);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE, ModelValidator.TYPE_BEFORE_DELETE_REPLICATION })
	public void deleteAccounting(final I_C_BP_BankAccount bankAccount)
	{
		InterfaceWrapperHelper.getPO(bankAccount).delete_Accounting(I_C_BP_BankAccount.Table_Name + "_Acct");
	}
}
