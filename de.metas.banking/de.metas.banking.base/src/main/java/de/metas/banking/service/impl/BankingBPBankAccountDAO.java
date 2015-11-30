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


import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;

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
				.addColumn(I_C_BP_BankAccount.COLUMNNAME_IsDefault, false); // DESC (Y, then N)
		return queryBuilder.create()
				.first();
	}
}
