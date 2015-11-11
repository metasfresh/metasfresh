package de.metas.adempiere.banking.api;

import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BP_BankAccount;

public interface IBPBankAccountDAO extends ISingletonService
{
	/**
	 * Retrieve all the bank accounts of the currency <code>currencyID</code> for the partner <code> partnerID</code>
	 * In case the currencyID is not set (<=0)  just retrieve all accounts of the bpartner
	 * 
	 * @param ctx
	 * @param partner
	 * @param currency
	 * @return
	 */
	List<I_C_BP_BankAccount> retrieveBankAccountsForPartnerAndCurrency(Properties ctx, int partnerID, int currencyID);
}
