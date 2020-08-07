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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;

import com.google.common.collect.ImmutableSet;

import de.metas.banking.payment.IPaymentString;
import de.metas.banking.payment.impl.AbstractPaymentStringDataProvider;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * @author al
 */
public class QRPaymentStringDataProvider extends AbstractPaymentStringDataProvider
{
	public QRPaymentStringDataProvider(final IPaymentString paymentString)
	{
		super(paymentString);
	}

	@Override
	public List<org.compiere.model.I_C_BP_BankAccount> getC_BP_BankAccounts()
	{
		final IPaymentString paymentString = getPaymentString();

		final String ibanAccountNo = paymentString.getIbanAccountNo();
		final Boolean isQRIBAN = paymentString.isQRIBAN();

		final List<org.compiere.model.I_C_BP_BankAccount> bankAccounts = InterfaceWrapperHelper.createList(
				this.retrieveQRBPBankAccounts(ibanAccountNo, isQRIBAN),
				org.compiere.model.I_C_BP_BankAccount.class);

		return bankAccounts;
	}
	
	private final List<I_C_BP_BankAccount> retrieveQRBPBankAccounts(
			@NonNull final String ibanAccountNo,
			@NonNull final Boolean isQRIBAN)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);


		final IQueryBuilder<I_C_BP_BankAccount> bankAccountQuery = queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();
		if (isQRIBAN) {
			bankAccountQuery
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_QR_IBAN, ibanAccountNo);
		} else {
			bankAccountQuery
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_IBAN, ibanAccountNo);
		}
		
		bankAccountQuery
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_IsEsrAccount, false);
	

		return bankAccountQuery
				.create()
				.list();
	}

	@Override
	public I_C_BP_BankAccount createNewC_BP_BankAccount(final IContextAware contextProvider, final int bpartnerId)
	{
		final IPaymentString paymentString = getPaymentString();

		final I_C_BP_BankAccount bpBankAccount = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class, contextProvider);

		Check.assume(bpartnerId > 0, "We assume the bPartnerId to be greater than 0. This={}", this);
		bpBankAccount.setC_BPartner_ID(bpartnerId);

		final Currency currency = Services.get(ICurrencyDAO.class).getByCurrencyCode(CurrencyCode.ofThreeLetterCode(paymentString.getCurrency()));
		bpBankAccount.setC_Currency_ID(currency.getId().getRepoId());
		bpBankAccount.setIsEsrAccount(false);
		bpBankAccount.setIsACH(true);
		final String bPartnerName = Services.get(IBPartnerDAO.class).getBPartnerNameById(BPartnerId.ofRepoId(bpartnerId));
		bpBankAccount.setA_Name(bPartnerName);
		bpBankAccount.setName(bPartnerName);

		if (paymentString.isQRIBAN()) {
			bpBankAccount.setQR_IBAN(paymentString.getIbanAccountNo());
			
		} else {
			bpBankAccount.setIBAN(paymentString.getIbanAccountNo());			
		}

		InterfaceWrapperHelper.save(bpBankAccount);

		return bpBankAccount;
	}

	@Override
	public String toString()
	{
		return String.format("QRPaymentStringDataProvider [getPaymentString()=%s]", getPaymentString());
	}
}
