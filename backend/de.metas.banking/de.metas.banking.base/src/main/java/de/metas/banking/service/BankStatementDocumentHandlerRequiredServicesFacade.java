package de.metas.banking.service;

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import de.metas.acct.api.IFactAcctDAO;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineReferenceList;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.banking.payment.IBankStatementPaymentBL;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.PaymentReconcileRequest;
import de.metas.util.Services;
import lombok.NonNull;

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

@Service
class BankStatementDocumentHandlerRequiredServicesFacade
{
	private final IBankStatementPaymentBL bankStatmentPaymentBL;
	private final IBankStatementBL bankStatementBL;

	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private final IBPBankAccountDAO bpBankAccountDAO = Services.get(IBPBankAccountDAO.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	public BankStatementDocumentHandlerRequiredServicesFacade(
			@NonNull final IBankStatementPaymentBL bankStatmentPaymentBL,
			@NonNull final IBankStatementBL bankStatementBL)
	{
		this.bankStatmentPaymentBL = bankStatmentPaymentBL;
		this.bankStatementBL = bankStatementBL;
	}

	public BankAccount getBankAccountById(@NonNull final BankAccountId bankAccountId)
	{
		return bpBankAccountDAO.getById(bankAccountId);
	}

	public List<I_C_BankStatementLine> getBankStatementLinesByBankStatementId(@NonNull final BankStatementId bankStatementId)
	{
		return bankStatementBL.getLinesByBankStatementId(bankStatementId);
	}

	public BankStatementLineReferenceList getBankStatementLineReferences(@NonNull final BankStatementLineId bankStatementLineId)
	{
		return bankStatementDAO.getLineReferences(bankStatementLineId);
	}

	public BankStatementLineReferenceList getBankStatementLineReferences(final Collection<BankStatementLineId> bankStatementLineIds)
	{
		return bankStatementDAO.getLineReferences(bankStatementLineIds);
	}

	public I_C_BankStatementLine getBankStatementLineById(@NonNull final BankStatementLineId bankStatementLineId)
	{
		return bankStatementBL.getLineById(bankStatementLineId);
	}

	public void save(final I_C_BankStatementLine line)
	{
		bankStatementDAO.save(line);
	}

	public void findOrCreateSinglePaymentAndLinkIfPossible(
			final I_C_BankStatement bankStatement,
			final I_C_BankStatementLine line,
			final Set<PaymentId> excludePaymentIds)
	{
		bankStatmentPaymentBL.findOrCreateSinglePaymentAndLinkIfPossible(bankStatement, line, excludePaymentIds);
	}

	public void markReconciled(final List<PaymentReconcileRequest> requests)
	{
		paymentBL.markReconciled(requests);
	}

	public void markBankStatementLinesAsProcessed(final BankStatementId bankStatementId)
	{
		final boolean processed = true;
		bankStatementDAO.updateBankStatementLinesProcessedFlag(bankStatementId, processed);
	}

	public void deleteFactsForBankStatement(final I_C_BankStatement bankStatement)
	{
		factAcctDAO.deleteForDocumentModel(bankStatement);
	}

	public void unlinkPaymentsAndDeleteReferences(final List<I_C_BankStatementLine> lines)
	{
		bankStatementBL.unlinkPaymentsAndDeleteReferences(lines);
	}

	public String getMsg(final AdMessageKey adMessage)
	{
		return msgBL.getMsg(Env.getCtx(), adMessage);
	}

	public String getMsg(final Properties ctx, final AdMessageKey adMessage)
	{
		return msgBL.getMsg(ctx, adMessage);
	}

	public String translate(final Properties ctx, final String adMessageOrColumnName)
	{
		return msgBL.translate(ctx, adMessageOrColumnName);
	}
}
