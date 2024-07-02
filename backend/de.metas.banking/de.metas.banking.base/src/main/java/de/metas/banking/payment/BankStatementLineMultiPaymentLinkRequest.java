package de.metas.banking.payment;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.currency.Amount;
import de.metas.payment.PaymentId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Value
public class BankStatementLineMultiPaymentLinkRequest
{
	@NonNull
	BankStatementLineId bankStatementLineId;

	@NonNull
	ImmutableList<PaymentToLink> paymentsToLink;

	@Builder
	private BankStatementLineMultiPaymentLinkRequest(
			@NonNull final BankStatementLineId bankStatementLineId,
			@NonNull @Singular("paymentToLink") final ImmutableList<PaymentToLink> paymentsToLink)
	{
		Check.assumeNotEmpty(paymentsToLink, "paymentsToLink is not empty");
		Amount.assertSameCurrency(paymentsToLink, PaymentToLink::getStatementLineAmt);
		assertUniquePaymentIds(paymentsToLink);

		this.bankStatementLineId = bankStatementLineId;
		this.paymentsToLink = paymentsToLink;
	}

	private static void assertUniquePaymentIds(@NonNull final ImmutableList<PaymentToLink> paymentsToLink)
	{
		final ImmutableList<@NonNull PaymentId> paymentIds = paymentsToLink.stream().map(PaymentToLink::getPaymentId).collect(ImmutableList.toImmutableList());
		final ImmutableSet<@NonNull PaymentId> paymentIdsUnique = ImmutableSet.copyOf(paymentIds);
		if (paymentIdsUnique.size() != paymentIds.size())
		{
			throw new AdempiereException("paymentIds are not unique: " + paymentIds);
		}
	}

	public Amount getTotalStatementLineAmt()
	{
		return paymentsToLink.stream()
				.map(PaymentToLink::getStatementLineAmt)
				.reduce(Amount::add)
				.get();
	}

	@Value
	@Builder
	public static class PaymentToLink
	{
		@NonNull
		PaymentId paymentId;

		@NonNull
		Amount statementLineAmt;
	}
}
