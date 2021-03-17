package de.metas.ui.web.bankstatement_reconciliation;

import java.util.Collection;

import com.google.common.collect.ImmutableSet;

import de.metas.banking.BankStatementLineId;
import de.metas.payment.PaymentId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
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
public class BanksStatementReconciliationViewCreateRequest
{
	ImmutableSet<BankStatementLineId> bankStatementLineIds;
	ImmutableSet<PaymentId> paymentIds;

	@Builder
	private BanksStatementReconciliationViewCreateRequest(
			@NonNull @Singular final Collection<BankStatementLineId> bankStatementLineIds,
			@NonNull @Singular final Collection<PaymentId> paymentIds)
	{
		Check.assumeNotEmpty(bankStatementLineIds, "bankStatementLineIds is not empty");
		Check.assumeNotEmpty(paymentIds, "paymentIds is not empty");

		this.bankStatementLineIds = ImmutableSet.copyOf(bankStatementLineIds);
		this.paymentIds = ImmutableSet.copyOf(paymentIds);
	}
}
