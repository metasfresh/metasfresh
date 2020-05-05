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

package de.metas.banking.payment;

import de.metas.bpartner.BPartnerId;
import de.metas.payment.PaymentId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;

import java.util.Set;

public interface IBankStatementPaymentBL extends ISingletonService
{
	Set<PaymentId> findEligiblePaymentIds(@NonNull I_C_BankStatementLine bankStatementLine, @NonNull BPartnerId bpartnerId, int limit);

	void findOrCreateSinglePaymentAndLinkIfPossible(I_C_BankStatement bankStatement, I_C_BankStatementLine line);

	void createSinglePaymentAndLink(I_C_BankStatement bankStatement, I_C_BankStatementLine bankStatementLine);

	void linkSinglePayment(@NonNull I_C_BankStatement bankStatement, @NonNull I_C_BankStatementLine bankStatementLine, @NonNull PaymentId paymentId);

	void linkSinglePayment(@NonNull I_C_BankStatement bankStatement, @NonNull I_C_BankStatementLine bankStatementLine, @NonNull I_C_Payment payment);

	BankStatementLineMultiPaymentLinkResult linkMultiPayments(@NonNull BankStatementLineMultiPaymentLinkRequest request);
}
