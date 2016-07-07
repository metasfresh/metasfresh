package de.metas.banking.bankstatement.match.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.util.lang.ObjectUtils;

import com.google.common.collect.ImmutableList;

import de.metas.banking.bankstatement.match.model.BankAccount;
import de.metas.banking.bankstatement.match.model.BankStatement;
import de.metas.banking.bankstatement.match.model.IBankStatementPaymentMatching;
import de.metas.banking.bankstatement.match.model.IPayment;

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

/**
 * Immutable bank statement query (value object)
 *
 * @author metas-dev <dev@metasfresh.com>
 * @see IBankStatementMatchDAO
 *
 */
public final class BankStatementMatchQuery
{
	public static final BankStatementMatchQuery EMPTY = builder().build();

	public static final Builder builder()
	{
		return new Builder();
	}

	private final BankStatement bankStatement;
	private final BankAccount bankAccount;
	private final List<Integer> excludeBankStatementLineIds;
	private final List<Integer> excludePaymentIds;

	private BankStatementMatchQuery(final Builder builder)
	{
		super();
		bankStatement = builder.getBankStatement();
		bankAccount = builder.getBankAccount();

		excludeBankStatementLineIds = ImmutableList.copyOf(builder.excludeBankStatementLineIds);
		excludePaymentIds = ImmutableList.copyOf(builder.excludePaymentIds);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public BankStatement getBankStatement()
	{
		return bankStatement;
	}

	public int getC_BankStatement_ID()
	{
		final int bankStatementId = bankStatement == null ? -1 : bankStatement.getC_BankStatement_ID();
		return bankStatementId > 0 ? bankStatementId : -1;
	}

	public BankAccount getBankAccount()
	{
		return bankAccount;
	}

	public int getC_BP_BankAccount_ID()
	{
		final int bankAccountId = bankAccount == null ? -1 : bankAccount.getC_BP_BankAccount_ID();
		return bankAccountId > 0 ? bankAccountId : -1;
	}

	public List<Integer> getExcludeBankStatementLineIds()
	{
		return excludeBankStatementLineIds;
	}

	public List<Integer> getExcludePaymentIds()
	{
		return excludePaymentIds;
	}

	public static final class Builder
	{
		private BankStatement bankStatement;
		private BankAccount bankAccount;

		private final List<Integer> excludeBankStatementLineIds = new ArrayList<>();
		private final List<Integer> excludePaymentIds = new ArrayList<>();

		private Builder()
		{
			super();
		}

		public BankStatementMatchQuery build()
		{
			return new BankStatementMatchQuery(this);
		}

		public Builder setBankStatement(final BankStatement bankStatement)
		{
			this.bankStatement = bankStatement;
			return this;
		}

		private BankStatement getBankStatement()
		{
			return bankStatement;
		}

		public Builder setBankAccount(final BankAccount bankAccount)
		{
			this.bankAccount = bankAccount;
			return this;
		}

		private final BankAccount getBankAccount()
		{
			if (bankAccount != null)
			{
				return bankAccount;
			}

			if (bankStatement != null)
			{
				return bankStatement.getBankAccount();
			}

			return null;
		}

		public Builder addExcludeMatchings(final Collection<IBankStatementPaymentMatching> matchings)
		{
			if (matchings == null || matchings.isEmpty())
			{
				return this;
			}

			for (final IBankStatementPaymentMatching matching : matchings)
			{
				final int bankStatementLineId = matching.getBankStatementLine().getC_BankStatementLine_ID();
				excludeBankStatementLineIds.add(bankStatementLineId);

				for (final IPayment payment : matching.getPayments())
				{
					excludePaymentIds.add(payment.getC_Payment_ID());
				}
			}
			
			return this;
		}
	}
}
