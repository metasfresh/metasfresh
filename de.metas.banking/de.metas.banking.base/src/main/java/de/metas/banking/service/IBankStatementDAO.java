package de.metas.banking.service;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;

import com.google.common.collect.ImmutableSet;

import de.metas.banking.interfaces.I_C_BankStatementLine_Ref;
import de.metas.banking.model.BankStatementId;
import de.metas.payment.PaymentId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

public interface IBankStatementDAO extends ISingletonService
{
	default List<I_C_BankStatementLine> retrieveLines(BankStatementId bankStatementId)
	{
		return retrieveLines(bankStatementId, I_C_BankStatementLine.class);
	}

	<T extends I_C_BankStatementLine> List<T> retrieveLines(BankStatementId bankStatementId, Class<T> clazz);

	List<I_C_BankStatementLine_Ref> retrieveLineReferences(I_C_BankStatementLine bankStatementLine);

	/**
	 * Checks if given payment is present on any {@link I_C_BankStatementLine} or {@link I_C_BankStatementLine_Ref}.
	 * <p>
	 * This method is NOT checking the {@link I_C_Payment#isReconciled()} flag but instead is doing a lookup in bank statement lines.
	 *
	 * @return true if given payment is present on any bank statement line or reference.
	 */
	boolean isPaymentOnBankStatement(I_C_Payment payment);

	/**
	 * Retrieve all the BankStatement documents that are marked as posted but do not actually have fact accounts.
	 * <p>
	 * Exclude the entries that have trxAmt = 0. These entries will produce 0 in posting
	 */
	List<I_C_BankStatement> retrievePostedWithoutFactAcct(Properties ctx, Date startTime);

	/**
	 * @deprecated Please use {@link #getById(BankStatementId)}
	 */
	@Deprecated
	de.metas.banking.model.I_C_BankStatement getById(int id);

	de.metas.banking.model.I_C_BankStatement getById(@NonNull BankStatementId bankStatementId);

	/**
	 * @deprecated please use the repoIdAware version
	 */
	@Deprecated
	de.metas.banking.model.I_C_BankStatementLine getLineById(int lineId);

	@NonNull
	ImmutableSet<PaymentId> getLinesPaymentIds(@NonNull final BankStatementId bankStatementId);

	void save(@NonNull final I_C_BankStatement bankStatement);

	void save(@NonNull final I_C_BankStatementLine bankStatementLine);

	void save(@NonNull final I_C_BankStatementLine_Ref lineOrRef);

	int retrieveLastLineNo(@NonNull BankStatementId bankStatementId);
}
