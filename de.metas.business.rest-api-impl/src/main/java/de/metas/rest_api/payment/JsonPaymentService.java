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
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Payment;
import org.compiere.util.Env;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class JsonPaymentService
{
	private final CurrencyService currencyService;

	private final IBPBankAccountDAO bankAccountDAO = Services.get(IBPBankAccountDAO.class);
	private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);

	public JsonPaymentService(final CurrencyService currencyService)
	{
		this.currencyService = currencyService;
	}

	public ResponseEntity<String> createPaymentFromJson(@NonNull @RequestBody final JsonPaymentInfo jsonPaymentInfo)
	{
		final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
		final LocalDate dateTrx = SystemTime.asLocalDate();

		final CurrencyId currencyId = currencyService.getCurrencyId(jsonPaymentInfo.getCurrencyCode());

		if (currencyId == null)
		{
			return ResponseEntity.unprocessableEntity().body("Wrong currency: " + jsonPaymentInfo.getCurrencyCode());
		}

		final Optional<BPartnerId> bPartnerIdOptional = partnerDAO.getBPartnerIdByExternalId(ExternalId.of(jsonPaymentInfo.getExternalBpartnerId().getValue()));
		if (!bPartnerIdOptional.isPresent())
		{
			return ResponseEntity.unprocessableEntity().body("Cannot find bpartner: " + jsonPaymentInfo.getExternalBpartnerId().getValue());
		}

		final BPartnerId bPartnerId = bPartnerIdOptional.get();
		final Optional<BankAccountId> bankAccountIdOptional = bankAccountDAO.retrieveBankAccountByBPartnerAndCurrencyAndIBAN(bPartnerId, currencyId, jsonPaymentInfo.getTargetIBAN());

		if (!bankAccountIdOptional.isPresent())
		{
			return ResponseEntity.unprocessableEntity().body(String.format("Cannot find Bank Account for bpartner: %s, currency: %s and account: %s", jsonPaymentInfo.getExternalBpartnerId().getValue(), jsonPaymentInfo.getCurrencyCode(), jsonPaymentInfo.getTargetIBAN()));
		}

		final I_C_Payment payment = paymentBL.newOutboundPaymentBuilder()
				.bpartnerId(bPartnerId)
				.payAmt(jsonPaymentInfo.getAmount())
				.currencyId(currencyId)
				.bpBankAccountId(bankAccountIdOptional.get())

				.adOrgId(Env.getOrgId())
				.tenderType(TenderType.DirectDeposit)
				.dateAcct(dateTrx)
				.dateTrx(dateTrx)
				.createAndProcess();

		payment.setExternalOrderId(jsonPaymentInfo.getExternalOrderId().getValue());
		InterfaceWrapperHelper.save(payment);

		return ResponseEntity.ok().build();
	}
}
