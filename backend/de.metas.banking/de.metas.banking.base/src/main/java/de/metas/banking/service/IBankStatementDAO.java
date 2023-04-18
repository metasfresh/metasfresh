package de.metas.banking.service;

import com.google.common.collect.ImmutableSet;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineRefId;
import de.metas.banking.BankStatementLineReference;
import de.metas.banking.BankStatementLineReferenceList;
import de.metas.document.engine.DocStatus;
import de.metas.payment.PaymentId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.banking.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

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
	List<I_C_BankStatementLine> getLinesByBankStatementId(@NonNull BankStatementId bankStatementId);

	BankStatementLineReferenceList getLineReferences(BankStatementLineId bankStatementLineId);

	BankStatementLineReferenceList getLineReferences(@NonNull Collection<BankStatementLineId> bankStatementLineIds);

	void deleteReferencesByIds(@NonNull Collection<BankStatementLineRefId> lineRefIds);

	/**
	 * Checks if given payment is present on any line or line reference.
	 * <p>
	 * This method is NOT checking the {@link I_C_Payment#isReconciled()} flag but instead is doing a lookup in bank statement lines.
	 *
	 * @return true if given payment is present on any bank statement line or reference.
	 */
	boolean isPaymentOnBankStatement(PaymentId paymentId);

	/**
	 * Retrieve all the BankStatement documents that are marked as posted but do not actually have fact accounts.
	 * <p>
	 * Exclude the entries that have trxAmt = 0. These entries will produce 0 in posting
	 */
	List<I_C_BankStatement> getPostedWithoutFactAcct(Properties ctx, Date startTime);

	I_C_BankStatement getById(@NonNull BankStatementId bankStatementId);

	Optional<BankStatementId> getFirstIdMatching(@NonNull BankAccountId orgBankAccountId, @NonNull LocalDate statementDate, @NonNull String name, @NonNull DocStatus docStatus);

	I_C_BankStatementLine getLineById(BankStatementLineId lineId);

	List<I_C_BankStatementLine> getLinesByIds(@NonNull Set<BankStatementLineId> lineIds);

	@NonNull
	ImmutableSet<PaymentId> getLinesPaymentIds(@NonNull final BankStatementId bankStatementId);

	void save(@NonNull final org.compiere.model.I_C_BankStatement bankStatement);

	void save(@NonNull final I_C_BankStatementLine bankStatementLine);

	BankStatementId createBankStatement(@NonNull BankStatementCreateRequest request);

	BankStatementLineId createBankStatementLine(@NonNull BankStatementLineCreateRequest request);

	BankStatementLineReference createBankStatementLineRef(@NonNull BankStatementLineRefCreateRequest request);

	void updateBankStatementLinesProcessedFlag(@NonNull BankStatementId bankStatementId, boolean processed);

	int getLastLineNo(@NonNull BankStatementId bankStatementId);
}
