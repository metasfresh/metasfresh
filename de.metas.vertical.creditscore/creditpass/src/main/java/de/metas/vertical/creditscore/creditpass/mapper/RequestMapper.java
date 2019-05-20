package de.metas.vertical.creditscore.creditpass.mapper;


/*
 * #%L
 * de.metas.vertical.creditscore.creditpass.mapper
 * %%
 * Copyright (C) 2018 metas GmbH
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

import de.metas.util.Services;
import de.metas.vertical.creditscore.creditpass.CreditPassConstants;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfig;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfigPaymentRule;
import de.metas.vertical.creditscore.creditpass.model.CreditPassTransactionData;
import de.metas.vertical.creditscore.creditpass.model.schema.*;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.apache.commons.lang3.StringUtils;
import org.compiere.util.Env;

import java.util.Optional;
import java.util.UUID;

public class RequestMapper
{

	public Request mapToRequest(final @NonNull CreditPassTransactionData transactionData,
			final @NonNull CreditPassConfig creditPassConfig,
			final @NonNull String paymentRule)
	{

		Optional<CreditPassConfigPaymentRule> configPaymentRule = creditPassConfig.getCreditPassConfigPaymentRuleList().stream()
				.filter(p -> StringUtils.equals(p.getPaymentRule(), paymentRule))
				.findFirst();

		//TODO cleanup - only needed for testing mode
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		String amountDefault = sysConfigBL.getValue(CreditPassConstants.SYSCONFIG_AMOUNT_DEFAULT_VALUE, CreditPassConstants.AMOUNT_DEFAULT_VALUE,
				Env.getAD_Client_ID(), Env.getAD_Org_ID(Env.getCtx()));

		return Request.builder()
				.customer(Customer.builder()
						.customerTransactionId(UUID.randomUUID().toString().replace("-", StringUtils.EMPTY))
						.authId(creditPassConfig.getAuthId())
						.authPassword(creditPassConfig.getAuthPassword())
						.build())
				.process(ProcessRequest.builder()
						.processingCode(creditPassConfig.getProcessingCode())
						.requestReason(creditPassConfig.getRequestReason())
						.transactionType(creditPassConfig.getTransactionType())
						.build())
				.query(Query.builder()
						.amount(amountDefault)
						.purchaseType(configPaymentRule.map(CreditPassConfigPaymentRule::getPurchaseType).orElse(-1))
						.contact(Contact.builder()
								.firstName(transactionData.getFirstName())
								.lastName(transactionData.getLastName())
								.dateOfBirth(transactionData.getDateOfBirth())
								.address(Address.builder()
										.city(transactionData.getCity())
										.country(transactionData.getCountry())
										.streetFull(transactionData.getStreetFull())
										.zip(transactionData.getZip())
										.build())
								.build())
						.bankAccount(BankAccount.builder()
								.accountNr(transactionData.getAccountNr())
								.bankRoutingCode(transactionData.getBankRoutingCode())
								.creditCardNr(transactionData.getCreditCardNr())
								.creditCardType(transactionData.getCreditCardType())
								.iban(transactionData.getIban())
								.build())
						.customerDetails(CustomerDetails.builder()
								.companyName(transactionData.getCompanyName())
								.email(transactionData.getEmail())
								.phoneNr(transactionData.getPhoneNr())
								.build())
						.build())
				.build();

	}

}
