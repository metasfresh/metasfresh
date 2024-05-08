/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.v2.payment;

import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.payment.api.DefaultPaymentBuilder;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

@Service
public class PaymentService
{
	private final IBPartnerOrgBL bPartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IBPBankAccountDAO bankAccountDAO = Services.get(IBPBankAccountDAO.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

	@NonNull
	public Optional<BankAccountId> determineInboundBankAccountId(
			@NonNull final OrgId orgId,
			@NonNull final CurrencyId currencyId,
			@Nullable final String iban)
	{
		final Optional<BPartnerId> orgBPartnerIdOptional = bPartnerOrgBL.retrieveLinkedBPartnerId(orgId);
		if (!orgBPartnerIdOptional.isPresent())
		{
			return Optional.empty();
		}

		if (Check.isNotBlank(iban))
		{
			return bankAccountDAO.retrieveByBPartnerAndCurrencyAndIBAN(orgBPartnerIdOptional.get(), currencyId, iban);
		}

		return bankAccountDAO.getDefaultBankAccount(orgBPartnerIdOptional.get())
				.filter(bankAccount -> bankAccount.getCurrencyId().equals(currencyId))
				.map(BankAccount::getId);
	}

	@NonNull
	public DefaultPaymentBuilder newInboundReceiptBuilder()
	{
		return paymentBL.newInboundReceiptBuilder();
	}
}
