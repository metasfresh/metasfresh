package de.metas.banking.payment;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.banking.model.BankStatementAndLineAndRefId;
import de.metas.banking.model.BankStatementLineId;
import de.metas.money.Money;
import de.metas.payment.PaymentId;
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
public class BankStatementLineMultiPaymentLinkResult
{
	@NonNull
	BankStatementLineId bankStatementLineId;

	@NonNull
	ImmutableList<PaymentResult> payments;

	@Value
	@Builder
	public static class PaymentResult
	{
		@NonNull
		BankStatementAndLineAndRefId bankStatementLineRefId;

		@NonNull
		PaymentId paymentId;

		@NonNull
		Money amount;

		boolean paymentMarkedAsReconciled;
	}

	@Builder
	private BankStatementLineMultiPaymentLinkResult(
			@NonNull final BankStatementLineId bankStatementLineId,
			@NonNull @Singular final ImmutableList<PaymentResult> payments)
	{
		this.bankStatementLineId = bankStatementLineId;
		this.payments = payments;
	}

	public boolean isEmpty()
	{
		return payments.isEmpty();
	}

	public ImmutableMap<PaymentId, BankStatementAndLineAndRefId> getBankStatementLineRefIdIndexByPaymentId()
	{
		return getPayments()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						PaymentResult::getPaymentId,
						PaymentResult::getBankStatementLineRefId));
	}
}
