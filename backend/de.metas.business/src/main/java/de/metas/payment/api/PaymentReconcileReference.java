package de.metas.payment.api;

import de.metas.banking.BankStatementAndLineAndRefId;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineRefId;
import de.metas.payment.PaymentId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentReconcileReference
{
	public enum Type
	{
		BANK_STATEMENT_LINE, BANK_STATEMENT_LINE_REF, REVERSAL
	}

	@NonNull
	Type type;

	BankStatementId bankStatementId;
	BankStatementLineId bankStatementLineId;

	BankStatementLineRefId bankStatementLineRefId;

	PaymentId reversalId;

	public static PaymentReconcileReference bankStatementLine(
			@NonNull final BankStatementId bankStatementId,
			@NonNull final BankStatementLineId bankStatementLineId)
	{
		final BankStatementLineRefId bankStatementLineRefId = null;
		final PaymentId reversalId = null;

		return new PaymentReconcileReference(
				Type.BANK_STATEMENT_LINE,
				bankStatementId,
				bankStatementLineId,
				null,
				null);
	}

	public static PaymentReconcileReference bankStatementLineRef(@NonNull final BankStatementAndLineAndRefId bankStatementLineRefId)
	{
		return bankStatementLineRef(
				bankStatementLineRefId.getBankStatementId(),
				bankStatementLineRefId.getBankStatementLineId(),
				bankStatementLineRefId.getBankStatementLineRefId());
	}

	public static PaymentReconcileReference bankStatementLineRef(
			@NonNull final BankStatementId bankStatementId,
			@NonNull final BankStatementLineId bankStatementLineId,
			@NonNull final BankStatementLineRefId bankStatementLineRefId)
	{
		final PaymentId reversalId = null;

		return new PaymentReconcileReference(
				Type.BANK_STATEMENT_LINE_REF,
				bankStatementId,
				bankStatementLineId,
				bankStatementLineRefId,
				reversalId);
	}

	public static PaymentReconcileReference reversal(@NonNull final PaymentId reversalId)
	{
		final BankStatementId bankStatementId = null;
		final BankStatementLineId bankStatementLineId = null;
		final BankStatementLineRefId bankStatementLineRefId = null;

		return new PaymentReconcileReference(
				Type.REVERSAL,
				bankStatementId,
				bankStatementLineId,
				bankStatementLineRefId,
				reversalId);
	}
}
