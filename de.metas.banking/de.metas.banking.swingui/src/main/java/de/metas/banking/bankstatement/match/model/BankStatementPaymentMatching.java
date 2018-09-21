package de.metas.banking.bankstatement.match.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ObjectUtils;

import com.google.common.collect.ImmutableList;

import de.metas.util.Check;

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

public class BankStatementPaymentMatching implements IBankStatementPaymentMatching
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final IBankStatementLine bankStatementLine;
	private final List<IPayment> payments;

	private BankStatementPaymentMatching(final Builder builder)
	{
		super();

		bankStatementLine = builder.bankStatementLine;
		Check.assumeNotNull(bankStatementLine, "bankStatementLine not null");

		payments = ImmutableList.copyOf(builder.payments);
		Check.assumeNotEmpty(payments, "payments not empty");
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public IBankStatementLine getBankStatementLine()
	{
		return bankStatementLine;
	}

	@Override
	public BankAccount getBankAccount()
	{
		return bankStatementLine.getBankAccount();
	}

	@Override
	public Date getStatementDate()
	{
		return bankStatementLine.getStatementDate();
	}

	@Override
	public BigDecimal getTrxAmt()
	{
		return bankStatementLine.getTrxAmt();
	}

	@Override
	public String getDocumentNo()
	{
		return bankStatementLine.getDocumentNo();
	}

	@Override
	public String getBPartnerName()
	{
		return bankStatementLine.getBPartnerName();
	}

	@Override
	public List<IPayment> getPayments()
	{
		return payments;
	}

	public static class Builder
	{
		private IBankStatementLine bankStatementLine;
		private List<IPayment> payments;
		private boolean failIfNotValid = true;

		private Builder()
		{
			super();
		}

		@Override
		public String toString()
		{
			return ObjectUtils.toString(this);
		}

		public boolean isValid()
		{
			if (bankStatementLine == null)
			{
				return false;
			}

			if (payments == null || payments.isEmpty())
			{
				return false;
			}

			final BigDecimal bsTrxAmt = bankStatementLine.getTrxAmt();

			BigDecimal payAmtTotal = BigDecimal.ZERO;
			for (final IPayment payment : payments)
			{
				if (!isMatching(payment))
				{
					return false;
				}

				payAmtTotal = payAmtTotal.add(payment.getPayAmt());
			}

			if (bsTrxAmt.compareTo(payAmtTotal) != 0)
			{
				return false;
			}

			return true;
		}

		public IBankStatementPaymentMatching build()
		{
			if (!isValid())
			{
				if (failIfNotValid)
				{
					throw new AdempiereException("Invalid: " + this); // shall not happen because we shall call isValid first
				}
				return null;
			}

			return new BankStatementPaymentMatching(this);

		}

		private boolean isMatching(final IPayment payment)
		{
			if (bankStatementLine == null)
			{
				return false;
			}
			if (payment == null)
			{
				return false;
			}

			return bankStatementLine.isMatchable(payment);
		}

		public Builder setBankStatementLine(final IBankStatementLine bankStatementLine)
		{
			this.bankStatementLine = bankStatementLine;
			return this;
		}

		public Builder setPayments(final List<IPayment> payments)
		{
			this.payments = payments;
			return this;
		}
		
		public Builder setFailIfNotValid(boolean failIfNotValid)
		{
			this.failIfNotValid = failIfNotValid;
			return this;
		}
	}
}
