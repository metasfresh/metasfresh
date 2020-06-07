package de.metas.banking.service;

import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;

import de.metas.util.ISingletonService;

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
}
