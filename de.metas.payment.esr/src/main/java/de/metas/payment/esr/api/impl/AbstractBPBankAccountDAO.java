package de.metas.payment.esr.api.impl;

import java.util.List;

/*
 * #%L
 * de.metas.payment.esr
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

import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.payment.esr.api.IESRBPBankAccountDAO;
import de.metas.payment.esr.model.I_C_BP_BankAccount;

public abstract class AbstractBPBankAccountDAO implements IESRBPBankAccountDAO
{
	@Override
	public List<I_C_BP_BankAccount> retrieveESRBPBankAccounts(final String postAccountNo, final String innerAccountNo)
	{
		Check.assumeNotEmpty(postAccountNo, "postAccountNo not null");
		Check.assumeNotEmpty(innerAccountNo, "innerAccountNo not null");

		final Properties ctx = Env.getCtx();
		final String trxName = ITrx.TRXNAME_None;

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_BP_BankAccount> qb = queryBL.createQueryBuilder(I_C_BP_BankAccount.class, ctx, trxName)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_ESR_RenderedAccountNo, postAccountNo)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_IsEsrAccount, true);

		if (!innerAccountNo.equals("0000000")) // 7 x 0
		{
			qb.addEqualsFilter(org.compiere.model.I_C_BP_BankAccount.COLUMNNAME_AccountNo, innerAccountNo);
		}

		qb
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		return qb
				.create()
				.list();
	}

}
