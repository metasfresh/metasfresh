package de.metas.payment.esr.api;

import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.util.ISingletonService;

/**
 * 
 * Contains ESR-specific logic around the C_BP_BankAccount.
 *
 */
public interface IESRBPBankAccountBL extends ISingletonService
{
	/**
	 * @return the <code>C_BP_BankAccount.AccountNo</code> of the given <code>bankAccount</code>, or <code>000000</code>, if the account's <code>C_Bank</code> is the ESR-PostBank.
	 */
	String retrieveBankAccountNo(I_C_BP_BankAccount bankAccount);

	/**
	 * Returns an "unrendered" (i.e. without the "-") version of the given <code>bankAccount</code>'s <code>ESR_RenderedAccountNo</code>. Assumes that the given <code>bankAccount</code> is an ESR
	 * bank account. Never returns <code>null</code>.
	 */
	String retrieveESRAccountNo(I_C_BP_BankAccount bankAccount);
}
