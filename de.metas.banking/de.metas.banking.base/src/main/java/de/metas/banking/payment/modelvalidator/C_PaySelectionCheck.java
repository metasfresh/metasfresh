package de.metas.banking.payment.modelvalidator;

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


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Constants;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_PaySelectionCheck;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_C_Order;
import org.slf4j.Logger;

import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.logging.LogManager;

/**
 * This is mainly a copy and paste of the former jboss-aop aspect <code>de.metas.banking.aop.PaySelectionCheckAsp</code>. The purpuse of that aspect was:
 * 
 * <pre>
 * if an payselectioncheck is created for a commission/payroll invoice,
 * make sure that the right bPartner-account is used
 * </pre>
 * 
 * @author ts
 * @task http://dewiki908/mediawiki/index.php/07286_get_rid_of_jboss-aop_for_good_%28104432455599%29
 */
@Interceptor(I_C_PaySelectionCheck.class)
public class C_PaySelectionCheck
{
	private static final Logger logger = LogManager.getLogger(C_PaySelectionCheck.class);

	public static final C_PaySelectionCheck instance = new C_PaySelectionCheck();

	private C_PaySelectionCheck()
	{
		super();
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void newPaySelectionCheck(final I_C_PaySelectionCheck paySelectionCheck)
	{
		final String paymentRule = paySelectionCheck.getPaymentRule();

		if (!X_C_Order.PAYMENTRULE_DirectDeposit.equals(paymentRule))
		{
			// nothing to to
			return;
		}

		// set by the constructor of MPaySelectionCheck for which we roiginally introduces the jboss-aop aspect
		final String docBaseType = InterfaceWrapperHelper.getDynAttribute(paySelectionCheck, I_C_DocType.COLUMNNAME_DocBaseType);
		if (Check.isEmpty(docBaseType))
		{
			return;
		}
		if (!Constants.DOCBASETYPE_AEInvoice.equals(docBaseType))
		{
			// nothing to do
			return;
		}

		// now search the bpartner's accounts
		// if possible, find the commission-account 'P'
		// otherwise find the remittance acct 'T' or the generic acct 'B'
		I_C_BP_BankAccount baToUse = null;
		I_C_BP_BankAccount baRemittance = null;
		I_C_BP_BankAccount baAll = null;

		final Properties ctx = InterfaceWrapperHelper.getCtx(paySelectionCheck);
		final int partnerID = paySelectionCheck.getC_BPartner_ID();
		
		final I_C_BP_BankAccount bankAccount = paySelectionCheck.getC_BP_BankAccount();
		final int currencyID = bankAccount != null ? bankAccount.getC_Currency_ID() : -1;
		
		final List<I_C_BP_BankAccount> accounts = Services.get(IBPBankAccountDAO.class).retrieveBankAccountsForPartnerAndCurrency(ctx, partnerID, currencyID);
		for (final I_C_BP_BankAccount ba : accounts)
		{
			final String bpBankAcctUse = ba.getBPBankAcctUse();
			
			if(bpBankAcctUse == null)
			{
				continue;
			}
			
			if ("P".equals(bpBankAcctUse))
			{
				// found the account we were actually looking for
				baToUse = ba;
				break;

			}
			else if ("T".equals(bpBankAcctUse))
			{
				baRemittance = ba;
			}
			else if ("B".equals(bpBankAcctUse))
			{
				baAll = ba;
			}
		}

		if (baToUse == null)
		{
			baToUse = baRemittance;
		}
		if (baToUse == null)
		{
			baToUse = baAll;
		}

		if (baToUse != null && paySelectionCheck.getC_BP_BankAccount_ID() != baToUse.getC_BP_BankAccount_ID())
		{
			logger.info("Changing bp_account for " + paySelectionCheck + " to " + baToUse);
			paySelectionCheck.setC_BP_BankAccount_ID(baToUse.getC_BP_BankAccount_ID());
		}
	}

}
