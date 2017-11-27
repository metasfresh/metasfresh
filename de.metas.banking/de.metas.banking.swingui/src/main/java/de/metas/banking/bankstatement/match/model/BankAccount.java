package de.metas.banking.bankstatement.match.model;

import java.util.Comparator;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.compiere.util.Env;

import de.metas.banking.model.I_C_BP_BankAccount;
import de.metas.currency.ICurrencyDAO;

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

public class BankAccount
{
	public static final BankAccount NULL = new BankAccount();

	public static final BankAccount of(final org.compiere.model.I_C_BP_BankAccount bankAccountPO)
	{
		if (bankAccountPO == null || bankAccountPO.getC_BP_BankAccount_ID() <= 0)
		{
			return NULL;
		}
		return new BankAccount(InterfaceWrapperHelper.create(bankAccountPO, I_C_BP_BankAccount.class));
	}

	public static final Comparator<BankAccount> ORDERING_Name = new Comparator<BankAccount>()
	{

		@Override
		public int compare(final BankAccount o1, final BankAccount o2)
		{
			return getName(o1).compareTo(getName(o2));
		}

		private final String getName(final BankAccount bankAccount)
		{
			if (bankAccount == null)
			{
				return "";
			}
			final String name = bankAccount.getName();
			if (name == null)
			{
				return "";
			}
			return name.trim();
		}
	};

	private final int bpBankAccountId;
	private final String name;

	private BankAccount(final I_C_BP_BankAccount bankAccountPO)
	{
		super();
		Check.assumeNotNull(bankAccountPO, "bankAccountPO not null");

		bpBankAccountId = bankAccountPO.getC_BP_BankAccount_ID();
		final StringBuilder name = new StringBuilder();

		final String iban = bankAccountPO.getIBAN();
		if (!Check.isEmpty(iban, true))
		{
			name.append(iban.trim());
		}

		final String accountNo = bankAccountPO.getAccountNo();
		if (!Check.isEmpty(accountNo, true))
		{
			if (name.length() > 0)
			{
				name.append(" / ");
			}
			name.append(accountNo.trim());
		}

		final String currencyISOCode = Services.get(ICurrencyDAO.class).getISO_Code(Env.getCtx(), bankAccountPO.getC_Currency_ID());
		if (!Check.isEmpty(currencyISOCode, true))
		{
			name.append(" ").append(currencyISOCode);
		}

		this.name = name.toString();
	}

	private BankAccount()
	{
		super();
		bpBankAccountId = -1;
		name = "";
	}

	@Override
	public String toString()
	{
		// NOTE: this method is used list/combobox renderers
		return name;
	};

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(bpBankAccountId)
				.toHashcode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final BankAccount other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(bpBankAccountId, other.bpBankAccountId)
				.isEqual();
	}

	public int getC_BP_BankAccount_ID()
	{
		return bpBankAccountId;
	}

	public String getName()
	{
		return name;
	}
}
