package de.metas.banking.bankstatement.match.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_BankStatementLine;

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

public class BankStatementLine implements IBankStatementLine
{
	public static final BankStatementLine of(final I_C_BankStatementLine bankStatementLinePO)
	{
		return new BankStatementLine(bankStatementLinePO);
	}

	private final int bankStatementLineId;
	private final BigDecimal trxAmt;
	private final String bpartnerName;
	private final int bpartnerId;
	private final String documentNo;
	private final BankAccount bankAccount;
	private final Timestamp statementDate;
	private final int adOrgId;

	private BankStatementLine(final I_C_BankStatementLine bankStatementLinePO)
	{
		super();
		Check.assumeNotNull(bankStatementLinePO, "bankStatementLinePO not null");

		bankStatementLineId = bankStatementLinePO.getC_BankStatementLine_ID();
		adOrgId = bankStatementLinePO.getAD_Org_ID();

		Check.assumeNotNull(bankStatementLinePO, "bankStatementLinePO not empty");
		trxAmt = bankStatementLinePO.getTrxAmt();

		bpartnerId = bankStatementLinePO.getC_BPartner_ID();
		if (bpartnerId != 0)
		{
			final I_C_BPartner bpartner = Services.get(IBPartnerDAO.class).getById(BPartnerId.ofRepoId(bpartnerId));
			bpartnerName = bpartner.getName();
		}
		else
		{
			bpartnerName = null;
		}

		final I_C_BankStatement bankStatement = bankStatementLinePO.getC_BankStatement();

		documentNo = bankStatement.getDocumentNo();
		bankAccount = BankAccount.of(bankStatement.getC_BP_BankAccount());

		statementDate = bankStatement.getStatementDate();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public int getC_BankStatementLine_ID()
	{
		return bankStatementLineId;
	}

	@Override
	public BigDecimal getTrxAmt()
	{
		return trxAmt;
	}

	@Override
	public String getBPartnerName()
	{
		return bpartnerName;
	}

	@Override
	public int getC_BPartner_ID()
	{
		return bpartnerId;
	}

	@Override
	public BankAccount getBankAccount()
	{
		return bankAccount;
	}

	@Override
	public String getDocumentNo()
	{
		return documentNo;
	}

	@Override
	public Timestamp getStatementDate()
	{
		return statementDate;
	}

	@Override
	public int getAD_Org_ID()
	{
		return adOrgId;
	}

	@Override
	public boolean isMatchable(final IPayment payment)
	{
		Check.assumeNotNull(payment, "payment not null");

		// Same Bank Account
		if (!Check.equals(getBankAccount(), payment.getBankAccount()))
		{
			return false;
		}

		// Compatible BPartner
		if (!isMatchable_BPartner(payment))
		{
			return false;
		}

		return true;
	}

	private final boolean isMatchable_BPartner(final IPayment payment)
	{
		final int bslBPartnerId = getC_BPartner_ID();
		final int paymentBPartnerId = payment.getC_BPartner_ID();
		if (bslBPartnerId > 0)
		{
			return paymentBPartnerId <= 0 || paymentBPartnerId == bslBPartnerId;
		}
		else
		{
			return true;
		}
	}

}
