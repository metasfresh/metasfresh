package de.metas.payment.api.impl;

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

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;

import de.metas.banking.payment.IPaymentString;
import de.metas.banking.payment.impl.AbstractPaymentStringDataProvider;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.payment.esr.api.IESRBPBankAccountDAO;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * @author al
 */
public class ESRPaymentStringDataProvider extends AbstractPaymentStringDataProvider
{
	public ESRPaymentStringDataProvider(final IPaymentString paymentString)
	{
		super(paymentString);
	}

	@Override
	public List<org.compiere.model.I_C_BP_BankAccount> getC_BP_BankAccounts()
	{
		final IPaymentString paymentString = getPaymentString();

		final String postAccountNo = paymentString.getPostAccountNo();
		final String innerAccountNo = paymentString.getInnerAccountNo();

		final IESRBPBankAccountDAO esrbpBankAccountDAO = Services.get(IESRBPBankAccountDAO.class);

		final List<org.compiere.model.I_C_BP_BankAccount> bankAccounts = InterfaceWrapperHelper.createList(
				esrbpBankAccountDAO.retrieveESRBPBankAccounts(postAccountNo, innerAccountNo),
				org.compiere.model.I_C_BP_BankAccount.class);

		return bankAccounts;
	}

	@Override
	public I_C_BP_BankAccount createNewC_BP_BankAccount(final IContextAware contextProvider, final int bpartnerId)
	{
		final IPaymentString paymentString = getPaymentString();

		final I_C_BP_BankAccount bpBankAccount = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class, contextProvider);

		Check.assume(bpartnerId > 0, "We assume the bPartnerId to be greater than 0. This={}", this);
		bpBankAccount.setC_BPartner_ID(bpartnerId);

		// bpBankAccount.setC_Bank_ID(C_Bank_ID); // introduce a standard ESR-Dummy-Bank, or leave it empty

		final Currency currency = Services.get(ICurrencyDAO.class).getByCurrencyCode(CurrencyCode.CHF); // CHF, because it's ESR
		bpBankAccount.setC_Currency_ID(currency.getId().getRepoId());
		bpBankAccount.setIsEsrAccount(true); // ..because we are creating this from an ESR string
		bpBankAccount.setIsACH(true);
		bpBankAccount.setA_Name(bpBankAccount.getC_BPartner().getName());

		bpBankAccount.setAccountNo(paymentString.getInnerAccountNo());
		bpBankAccount.setESR_RenderedAccountNo(paymentString.getPostAccountNo());

		InterfaceWrapperHelper.save(bpBankAccount);

		return bpBankAccount;
	}

	@Override
	public String toString()
	{
		return String.format("ESRPaymentStringDataProvider [getPaymentString()=%s]", getPaymentString());
	}
}
