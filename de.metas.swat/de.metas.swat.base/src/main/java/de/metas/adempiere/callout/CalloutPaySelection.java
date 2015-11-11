/**
 *
 */
package de.metas.adempiere.callout;

/*
 * #%L
 * de.metas.swat.base
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

import java.util.List;
import java.util.Properties;

import org.adempiere.model.GridTabWrapper;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.X_C_BP_BankAccount;

import de.metas.adempiere.banking.api.IBPBankAccountDAO;
import de.metas.adempiere.model.I_C_PaySelectionLine;

/**
 * @author cg
 *
 */
// TODO: refactor it and move it to de.metas.banking; also merge it with org.compiere.model.CalloutPaySelection
public class CalloutPaySelection extends CalloutEngine
{
	/**
	 * Updates <code>C_BPartner_ID</code> and <code>C_BP_BankAccount_ID</code> from the line's invoice
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String invoice(final Properties ctx, final int WindowNo, final GridTab mTab, final GridField mField, final Object value)
	{
		if (isCalloutActive())
		{// prevent recursive
			return "";
		}

		final I_C_PaySelectionLine psl = GridTabWrapper.create(mTab, I_C_PaySelectionLine.class);
		if (psl.getC_Invoice_ID() <= 0)
		{
			return "";
		}
		final int partnerID = psl.getC_Invoice().getC_BPartner_ID();
		psl.setC_BPartner_ID(partnerID);
		
		//
		final String paymentRule = psl.getPaymentRule();

		// task 09500 get the currency from the account of the selection header
		// this is safe because the columns are mandatory
		final int currencyID = psl.getC_PaySelection().getC_BP_BankAccount().getC_Currency_ID();
		final List<I_C_BP_BankAccount> bankAccts = Services.get(IBPBankAccountDAO.class).retrieveBankAccountsForPartnerAndCurrency(ctx, partnerID, currencyID);
		// final MBPBankAccount[] bankAccts = MBPBankAccount.getOfBPartner(ctx, C_BPartner_ID);
		if (!bankAccts.isEmpty())
		{
			int primaryAcct = 0;
			int secondaryAcct = 0;
			for (final I_C_BP_BankAccount account : bankAccts)
			{
				final int accountID = account.getC_BP_BankAccount_ID();
				if (accountID > 0)
				{
					if (account.getBPBankAcctUse().equals(X_C_BP_BankAccount.BPBANKACCTUSE_Both))
					{
						secondaryAcct = accountID;
					}
					else if (account.getBPBankAcctUse().equals(paymentRule))
					{
						primaryAcct = accountID;
						break;
					}
				}
			}
			if (primaryAcct != 0)
			{
				psl.setC_BP_BankAccount_ID(primaryAcct);
			}
			else if (secondaryAcct != 0)
			{
				psl.setC_BP_BankAccount_ID(secondaryAcct);
			}
		}
		return "";
	}

}
