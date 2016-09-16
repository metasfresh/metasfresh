package de.metas.banking.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BP_BankAccount;

import de.metas.banking.api.IBPBankAccountDAO;


public class BPBankAccountDAO implements IBPBankAccountDAO
{

	@Override
	public List<I_C_BP_BankAccount> retrieveBankAccountsForPartnerAndCurrency(Properties ctx, int partnerID, int currencyID)
	{
		final IQueryBuilder<I_C_BP_BankAccount> qb = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BP_BankAccount.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMN_C_BPartner_ID, partnerID);

		if (currencyID > 0)
		{
			qb.addEqualsFilter(I_C_BP_BankAccount.COLUMN_C_Currency_ID, currencyID);
		}

		final List<I_C_BP_BankAccount> bpBankAccounts = qb.addOnlyActiveRecordsFilter()
				.orderBy()
				.addColumn(de.metas.banking.model.I_C_BP_BankAccount.COLUMNNAME_IsDefault, Direction.Descending, Nulls.Last) // DESC (Y, then N)
				.addColumn(I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID)
				.endOrderBy()
				.create()
				.list();

		return bpBankAccounts;
		// return LegacyAdapters.convertToPOArray(bpBankAccounts, MBPBankAccount.class);
	}	// getOfBPartner
}
