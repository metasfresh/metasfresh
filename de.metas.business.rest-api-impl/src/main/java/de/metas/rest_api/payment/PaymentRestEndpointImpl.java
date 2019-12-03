/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.rest_api.payment;

import de.metas.Profiles;
import de.metas.banking.api.BankAccountId;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.money.CurrencyId;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.rest_api.utils.CurrencyService;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import de.metas.util.time.SystemTime;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.apache.poi.ss.formula.functions.T;
import org.compiere.model.I_C_Payment;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping(PaymentRestEndpoint.ENDPOINT)
@Profile(Profiles.PROFILE_App)
public class PaymentRestEndpointImpl implements PaymentRestEndpoint
{
	private final CurrencyService currencyService;

	public PaymentRestEndpointImpl(final CurrencyService currencyService)
	{
		this.currencyService = currencyService;
	}

	@PostMapping
	@Override
	public ResponseEntity<T> createPayment(@RequestBody @NonNull final JsonPaymentInfo jsonPaymentInfo)
	{

		final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
		final LocalDate dateTrx = SystemTime.asLocalDate();

		final CurrencyId currencyId = currencyService.getCurrencyId(jsonPaymentInfo.getCurrencyCode());

		final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);
		final BPartnerId bPartnerId = partnerDAO.getBPartnerIdByExternalId(ExternalId.of(jsonPaymentInfo.getExternalBpartnerId().getValue())).get();

		final IBPBankAccountDAO bankAccountDAO = Services.get(IBPBankAccountDAO.class);
		final Optional<BankAccountId> bankAccountIdOptional = bankAccountDAO.retrieveBankAccountByBPartnerAndCurrencyAndIBAN(bPartnerId, currencyId, jsonPaymentInfo.getTargetIBAN());

		if (!bankAccountIdOptional.isPresent())
		{
			throw new AdempiereException("WHYYYYYYYYYYYY user, why?");
		}

		final I_C_Payment transferBackPayment = paymentBL.newOutboundPaymentBuilder()
				.bpartnerId(bPartnerId)
				.payAmt(jsonPaymentInfo.getAmount())
				.currencyId(currencyId)
				.bpBankAccountId(bankAccountIdOptional.get())

				.adOrgId(Env.getOrgId())
				.tenderType(TenderType.DirectDeposit)
				.dateAcct(dateTrx)
				.dateTrx(dateTrx)
				.createAndProcess();

		transferBackPayment.setExternalOrderId(jsonPaymentInfo.getExternalOrderId().getValue());

		return ResponseEntity.ok().build();
	}
}
