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

import de.metas.banking.payment.PaymentString;
import de.metas.banking.payment.impl.AbstractPaymentStringDataProvider;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.payment.esr.api.IESRBPBankAccountDAO;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.util.Check;
import de.metas.util.Services;

public class QRPaymentStringDataProvider extends AbstractPaymentStringDataProvider
{
	private static final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private static final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private static final IESRBPBankAccountDAO esrbpBankAccountDAO = Services.get(IESRBPBankAccountDAO.class);

	public QRPaymentStringDataProvider(final PaymentString paymentString)
	{
		super(paymentString);
	}

	@Override
	public List<org.compiere.model.I_C_BP_BankAccount> getC_BP_BankAccounts()
	{
		final PaymentString paymentString = getPaymentString();

		final String IBAN = paymentString.getIBAN();

		final List<org.compiere.model.I_C_BP_BankAccount> bankAccounts = InterfaceWrapperHelper.createList(
				esrbpBankAccountDAO.retrieveQRBPBankAccounts(IBAN), org.compiere.model.I_C_BP_BankAccount.class);

		return bankAccounts;
	}

	@Override
	public I_C_BP_BankAccount createNewC_BP_BankAccount(final IContextAware contextProvider, final int bpartnerId)
	{
		final PaymentString paymentString = getPaymentString();

		final I_C_BP_BankAccount bpBankAccount = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class,
				contextProvider);

		Check.assume(bpartnerId > 0, "We assume the bPartnerId to be greater than 0. This={}", this);
		bpBankAccount.setC_BPartner_ID(bpartnerId);

		final Currency currency = currencyDAO.getByCurrencyCode(CurrencyCode.CHF); // CHF, because it's ESR

		bpBankAccount.setC_Currency_ID(currency.getId().getRepoId());
		bpBankAccount.setIsEsrAccount(true); // ..because we are creating this from an ESR/QRR string
		bpBankAccount.setIsACH(true);
		final String bPartnerName = bpartnerDAO.getBPartnerNameById(BPartnerId.ofRepoId(bpartnerId));
		bpBankAccount.setA_Name(bPartnerName);
		bpBankAccount.setName(bPartnerName);

		bpBankAccount.setQR_IBAN(paymentString.getIBAN());
		bpBankAccount.setAccountNo(paymentString.getInnerAccountNo());
		bpBankAccount.setESR_RenderedAccountNo(paymentString.getPostAccountNo()); // we can not know it

		InterfaceWrapperHelper.save(bpBankAccount);

		return bpBankAccount;
	}

	@Override
	public String toString()
	{
		return String.format("QRPaymentStringDataProvider [getPaymentString()=%s]", getPaymentString());
	}
}
