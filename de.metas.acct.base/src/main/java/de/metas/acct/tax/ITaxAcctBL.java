package de.metas.acct.tax;

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


import java.util.Properties;

import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.MAccount;

import de.metas.acct.api.AcctSchemaId;
import de.metas.util.ISingletonService;

/**
 * Tax Accounting
 *
 * @author tsa
 *
 */
public interface ITaxAcctBL extends ISingletonService
{
	/** Tax Due Acct */
	int ACCTTYPE_TaxDue = 0;
	/** Tax Liability */
	int ACCTTYPE_TaxLiability = 1;
	/** Tax Credit */
	int ACCTTYPE_TaxCredit = 2;
	/** Tax Receivables */
	int ACCTTYPE_TaxReceivables = 3;
	/** Tax Expense */
	int ACCTTYPE_TaxExpense = 4;

	/**
	 * Get Account (legacy)
	 *
	 * @param taxId tax (C_Tax_ID)
	 * @param as account schema
	 * @param acctType see ACCTTYPE_*
	 * @return Account
	 */
	public abstract MAccount getAccount(Properties ctx, int taxId, AcctSchemaId acctSchemaId, int acctType);

	/**
	 * Get Account (Valid Combination)
	 *
	 * @param taxId tax (C_Tax_ID)
	 * @param as account schema
	 * @param acctType see ACCTTYPE_*
	 * @return Account
	 */
	public abstract I_C_ValidCombination getC_ValidCombination(Properties ctx, int taxId, AcctSchemaId acctSchemaId, int acctType);

}
