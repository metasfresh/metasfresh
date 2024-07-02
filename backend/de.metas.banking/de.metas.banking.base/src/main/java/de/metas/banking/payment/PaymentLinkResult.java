package de.metas.banking.payment;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import de.metas.banking.BankStatementAndLineAndRefId;
import de.metas.money.Money;
import de.metas.payment.PaymentId;
import lombok.Builder;
import lombok.NonNull;
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
@Builder
public class PaymentLinkResult
{
	@NonNull
	BankStatementId bankStatementId;
	@NonNull
	BankStatementLineId bankStatementLineId;
	@Nullable
	BankStatementLineRefId bankStatementLineRefId;

	@NonNull
	PaymentId paymentId;

	@NonNull
	Money statementTrxAmt;

	boolean paymentMarkedAsReconciled;

	public boolean isBankStatementLineReferenceLink()
	{
		return bankStatementLineRefId != null;
	}

	public BankStatementAndLineAndRefId getBankStatementAndLineAndRefId()
	{
		if (bankStatementLineRefId == null)
		{
			throw new AdempiereException("Payment wasn't link on a bank statement line reference: " + this);
		}
		return BankStatementAndLineAndRefId.of(
				bankStatementId,
				bankStatementLineId,
				bankStatementLineRefId);
	}
}
