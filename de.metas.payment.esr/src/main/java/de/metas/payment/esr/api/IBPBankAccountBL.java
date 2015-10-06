package de.metas.payment.esr.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.ISingletonService;

import de.metas.payment.esr.model.I_C_BP_BankAccount;

/**
 * 
 * Contains ESR-specific logic around the C_BP_BankAccount.
 *
 */
public interface IBPBankAccountBL extends ISingletonService
{
	/**
	 * @param bankAccount
	 * @return the <code>C_BP_BankAccount.AccountNo</code> of the given <code>bankAccount</code>, or <code>000000</code>, if the account's <code>C_Bank</code> is the ESR-PostBank.
	 */
	String retrieveBankAccountNo(I_C_BP_BankAccount bankAccount);

	/**
	 * Returns an "unrendered" (i.e. without the "-") version of the given <code>bankAccount</code>'s <code>ESR_RenderedAccountNo</code>. Assumes that the given <code>bankAccount</code> is an ESR
	 * bank account. Never returns <code>null</code>.
	 * 
	 * @param bankAccount
	 * @return 
	 */
	String retrieveESRAccountNo(I_C_BP_BankAccount bankAccount);
}
