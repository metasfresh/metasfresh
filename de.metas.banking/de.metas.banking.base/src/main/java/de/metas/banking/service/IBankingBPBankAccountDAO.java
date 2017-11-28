package de.metas.banking.service;

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

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;

import de.metas.banking.model.I_C_Bank;

/**
 * Provides data retrieval methods for partner bank accounts
 *
 * @author al
 */
public interface IBankingBPBankAccountDAO extends ISingletonService
{
	/**
	 * @param partner
	 * @return default bank account for given partner
	 */
	I_C_BP_BankAccount retrieveDefaultBankAccount(I_C_BPartner partner);

	/**
	 * @param ctx
	 * @return query filter used to filter out the cash bank accounts
	 * @see I_C_Bank#isCashBank()
	 */
	IQueryFilter<org.compiere.model.I_C_BP_BankAccount> createBankAccountsExcludingCashFilter(Properties ctx);
}
