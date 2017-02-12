package de.metas.banking.service.impl;

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


import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Bank;

import de.metas.banking.service.IBankingBPBankAccountDAO;
import de.metas.interfaces.I_C_BP_BankAccount;

/**
 * @author al
 */
public class BankingBPBankAccountDAO implements IBankingBPBankAccountDAO
{
	@Override
	public I_C_BP_BankAccount retrieveDefaultBankAccount(final I_C_BPartner partner)
	{
		final IQueryBuilder<I_C_BP_BankAccount> queryBuilder =
				Services.get(IQueryBL.class).createQueryBuilder(I_C_BP_BankAccount.class, partner);
		queryBuilder.addEqualsFilter(org.compiere.model.I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, partner.getC_BPartner_ID())
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(InterfaceWrapperHelper.getCtx(partner));
		queryBuilder.orderBy()
				.addColumn(de.metas.banking.model.I_C_BP_BankAccount.COLUMNNAME_IsDefault, false); // DESC (Y, then N)
		return queryBuilder.create()
				.first();
	}
	

	@Override
	public IQueryFilter<org.compiere.model.I_C_BP_BankAccount> createBankAccountsExcludingCashFilter(final Properties ctx)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQuery<I_C_Bank> banksQuery = queryBL.createQueryBuilder(I_C_Bank.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Bank.COLUMNNAME_IsCashBank, false)
				.create();

		return queryBL.createCompositeQueryFilter(org.compiere.model.I_C_BP_BankAccount.class)
				.addInSubQueryFilter(I_C_BP_BankAccount.COLUMNNAME_C_Bank_ID, I_C_Bank.COLUMNNAME_C_Bank_ID, banksQuery);
	}

}
