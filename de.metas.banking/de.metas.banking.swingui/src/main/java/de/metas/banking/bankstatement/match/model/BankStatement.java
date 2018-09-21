package de.metas.banking.bankstatement.match.model;

import java.text.DateFormat;
import java.util.Date;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.compiere.model.I_C_BankStatement;
import org.compiere.util.DisplayType;

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

public class BankStatement
{
	public static final BankStatement NULL = new BankStatement();

	public static final BankStatement of(final I_C_BankStatement bankStatementPO)
	{
		return new BankStatement(bankStatementPO);
	}

	private final int bankStatementId;
	private final String name;
	private final BankAccount bankAccount;
	private final Date statementDate;

	private BankStatement(final I_C_BankStatement bankStatementPO)
	{
		super();
		Check.assumeNotNull(bankStatementPO, "bankStatementPO not null");
		bankStatementId = bankStatementPO.getC_BankStatement_ID();
		bankAccount = BankAccount.of(bankStatementPO.getC_BP_BankAccount());
		statementDate = bankStatementPO.getStatementDate();

		final DateFormat dateFormat = DisplayType.getDateFormat(DisplayType.Date);
		name = bankStatementPO.getDocumentNo()
				+ ", " + bankAccount.getName()
				+ ", " + dateFormat.format(statementDate);
	}

	/** Null constructor */
	private BankStatement()
	{
		super();
		bankStatementId = -1;
		name = "";
		bankAccount = BankAccount.NULL;
		statementDate = null;
	}

	@Override
	public String toString()
	{
		// NOTE: this method is used list/combobox/table renderers
		return name;
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(bankStatementId)
				.toHashcode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final BankStatement other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(bankStatementId, other.bankStatementId)
				.isEqual();
	}

	public int getC_BankStatement_ID()
	{
		return bankStatementId;
	}

	public BankAccount getBankAccount()
	{
		return bankAccount;
	}

	public String getName()
	{
		return name;
	}

	public Date getStatementDate()
	{
		return statementDate;
	}
}
