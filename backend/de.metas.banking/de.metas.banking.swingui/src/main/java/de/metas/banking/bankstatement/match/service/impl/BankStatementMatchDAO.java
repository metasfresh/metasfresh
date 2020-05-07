package de.metas.banking.bankstatement.match.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_BankStatement;
import org.compiere.model.X_C_Payment;
import org.compiere.util.Env;

import de.metas.banking.bankstatement.match.api.IPaymentBatchFactory;
import de.metas.banking.bankstatement.match.model.BankAccount;
import de.metas.banking.bankstatement.match.model.BankStatement;
import de.metas.banking.bankstatement.match.model.BankStatementLine;
import de.metas.banking.bankstatement.match.model.IBankStatementLine;
import de.metas.banking.bankstatement.match.model.IPayment;
import de.metas.banking.bankstatement.match.model.Payment;
import de.metas.banking.bankstatement.match.service.BankStatementMatchQuery;
import de.metas.banking.bankstatement.match.service.IBankStatementMatchDAO;
import de.metas.banking.bankstatement.match.spi.IPaymentBatch;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.service.IBankingBPBankAccountDAO;

/*
 * #%L
 * de.metas.banking.swingui
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class BankStatementMatchDAO implements IBankStatementMatchDAO
{
	private final IQueryFilter<I_C_BankStatementLine> notMatchedBankStatementLineFilter;

	public BankStatementMatchDAO()
	{
		super();

		notMatchedBankStatementLineFilter = createNotMatchedBankStatementLineFilter();
	}

	private IQueryFilter<I_C_BankStatementLine> createNotMatchedBankStatementLineFilter()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryFilter<I_C_BankStatementLine> filters = queryBL.createCompositeQueryFilter(I_C_BankStatementLine.class);

		// Payment is not set
		filters.addEqualsFilter(I_C_BankStatementLine.COLUMN_C_Payment_ID, null);

		// Has no reference lines
		{
			final IQuery<I_C_BankStatementLine_Ref> bankStatementLineRefQuery = queryBL.createQueryBuilder(I_C_BankStatementLine_Ref.class, Env.getCtx(), ITrx.TRXNAME_None)
					.create();

			filters.addNotInSubQueryFilter(I_C_BankStatementLine.COLUMN_C_BankStatementLine_ID,
					I_C_BankStatementLine_Ref.COLUMN_C_BankStatementLine_ID, bankStatementLineRefQuery);
		}

		return filters;
	}

	private IQuery<I_C_BP_BankAccount> createEligibleBankAccountsQuery()
	{
		final Properties ctx = Env.getCtx();
		final IQueryFilter<I_C_BP_BankAccount> notCashFilter = Services.get(IBankingBPBankAccountDAO.class).createBankAccountsExcludingCashFilter(ctx);
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BP_BankAccount.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.filter(notCashFilter) // Only non-cash bank accounts
				.create();
	}

	public IQueryFilter<I_C_BankStatementLine> getNotMatchedBankStatementLineFilter()
	{
		return notMatchedBankStatementLineFilter;
	}

	@Override
	public List<BankStatement> retrieveBankStatementsToMatch()
	{
		final List<I_C_BankStatement> bankStatementPOs = retrieveBankStatementsToMatchQuery()
				.create()
				.list(I_C_BankStatement.class);

		final List<BankStatement> result = new ArrayList<>(bankStatementPOs.size() + 1);
		result.add(BankStatement.NULL);

		for (final I_C_BankStatement bankStatementPO : bankStatementPOs)
		{
			result.add(BankStatement.of(bankStatementPO));
		}

		return result;
	}

	private IQueryBuilder<I_C_BankStatement> retrieveBankStatementsToMatchQuery()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BankStatementLine.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.filter(getNotMatchedBankStatementLineFilter())
				//
				// Collect bank statements
				.andCollect(I_C_BankStatementLine.COLUMN_C_BankStatement_ID)
				.addInArrayOrAllFilter(I_C_BankStatement.COLUMN_DocStatus, X_C_BankStatement.DOCSTATUS_Completed)
				//
				// Order by
				.orderBy()
				.addColumn(I_C_BankStatement.COLUMNNAME_C_BP_BankAccount_ID)
				.addColumn(I_C_BankStatement.COLUMN_StatementDate)
				.endOrderBy()
		//
		;
	}

	@Override
	public List<BankAccount> retrieveBankAccountsToMatch()
	{
		final List<I_C_BP_BankAccount> bankAccountPOs = retrieveBankStatementsToMatchQuery()
				.andCollect(I_C_BankStatement.COLUMN_C_BP_BankAccount_ID)
				.filter(Services.get(IBankingBPBankAccountDAO.class).createBankAccountsExcludingCashFilter(Env.getCtx()))
				.create()
				.list(I_C_BP_BankAccount.class);

		final List<BankAccount> result = new ArrayList<>(bankAccountPOs.size() + 1);
		result.add(BankAccount.NULL);

		for (final I_C_BP_BankAccount bankAccountPO : bankAccountPOs)
		{
			result.add(BankAccount.of(bankAccountPO));
		}

		Collections.sort(result, BankAccount.ORDERING_Name);

		return result;

	}

	@Override
	public List<IBankStatementLine> retrieveBankStatementLinesToMatch(final BankStatementMatchQuery query)
	{
		Check.assumeNotNull(query, "query not null");

		final IQueryBuilder<I_C_BankStatement> queryBuilder_BS = retrieveBankStatementsToMatchQuery();

		final IQueryBuilder<I_C_BankStatementLine> queryBuilder_BSL = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BankStatementLine.class, Env.getCtx(), ITrx.TRXNAME_None)
				.filter(getNotMatchedBankStatementLineFilter());

		// Bank Statement ID
		if (query.getC_BankStatement_ID() > 0)
		{
			queryBuilder_BSL.addEqualsFilter(I_C_BankStatementLine.COLUMN_C_BankStatement_ID, query.getC_BankStatement_ID());
		}

		// Bank Account
		if (query.getC_BP_BankAccount_ID() > 0)
		{
			queryBuilder_BS.addEqualsFilter(I_C_BankStatement.COLUMN_C_BP_BankAccount_ID, query.getC_BP_BankAccount_ID());
		}

		// Exclude bank statement lines
		if (!query.getExcludeBankStatementLineIds().isEmpty())
		{
			queryBuilder_BSL.addNotInArrayFilter(I_C_BankStatementLine.COLUMN_C_BankStatementLine_ID, query.getExcludeBankStatementLineIds());
		}

		final List<I_C_BankStatementLine> bankStatementLinePOs = queryBuilder_BSL
				// Only from matching bank statements
				.addInSubQueryFilter(I_C_BankStatementLine.COLUMNNAME_C_BankStatement_ID,
						I_C_BankStatement.COLUMNNAME_C_BankStatement_ID, queryBuilder_BS.create())
				//
				.orderBy()
				.addColumn(I_C_BankStatementLine.COLUMNNAME_C_BankStatement_ID)
				.addColumn(I_C_BankStatementLine.COLUMNNAME_Line)
				.endOrderBy()
				//
				.create()
				.list(I_C_BankStatementLine.class);

		final List<IBankStatementLine> lines = new ArrayList<>(bankStatementLinePOs.size());
		for (final I_C_BankStatementLine bankStatementLinePO : bankStatementLinePOs)
		{
			lines.add(BankStatementLine.of(bankStatementLinePO));
		}

		return lines;
	}

	@Override
	public List<IPayment> retrievePaymentsToMatch(final BankStatementMatchQuery query)
	{
		Check.assumeNotNull(query, "query not null");

		final Properties ctx = Env.getCtx();
		final IQueryBuilder<I_C_Payment> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Payment.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_Payment.COLUMNNAME_IsReconciled, false)
				.addEqualsFilter(I_C_Payment.COLUMNNAME_Processed, true)
				.addInArrayOrAllFilter(I_C_Payment.COLUMNNAME_DocStatus, X_C_Payment.DOCSTATUS_Completed, X_C_Payment.DOCSTATUS_Closed);

		// Bank Account
		if (query.getC_BP_BankAccount_ID() > 0)
		{
			queryBuilder.addEqualsFilter(I_C_Payment.COLUMNNAME_C_BP_BankAccount_ID, query.getC_BP_BankAccount_ID());
		}

		// Exclude payments
		if (!query.getExcludePaymentIds().isEmpty())
		{
			queryBuilder.addNotInArrayFilter(I_C_Payment.COLUMN_C_Payment_ID, query.getExcludePaymentIds());
		}

		// Only eligible accounts
		queryBuilder.addInSubQueryFilter(I_C_Payment.COLUMNNAME_C_BP_BankAccount_ID, I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID, createEligibleBankAccountsQuery());

		final List<I_C_Payment> paymentPOs = queryBuilder
				.orderBy()
				.addColumn(I_C_Payment.COLUMNNAME_DocumentNo)
				.addColumn(I_C_Payment.COLUMNNAME_C_Payment_ID)
				.endOrderBy()
				//
				.create()
				.list(I_C_Payment.class);

		final List<IPayment> payments = new ArrayList<>(paymentPOs.size());
		for (final I_C_Payment paymentPO : paymentPOs)
		{
			final IPaymentBatch paymentBatch = retrievePaymentBatch(paymentPO);
			payments.add(Payment.of(paymentPO, paymentBatch));
		}

		return payments;
	}

	@Override
	public IPaymentBatch retrievePaymentBatch(final org.compiere.model.I_C_Payment payment)
	{
		return Services.get(IPaymentBatchFactory.class).retrievePaymentBatch(payment);
	}
}
