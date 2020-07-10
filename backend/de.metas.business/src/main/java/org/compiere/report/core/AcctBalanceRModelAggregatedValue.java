package org.compiere.report.core;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.math.BigDecimal;
import java.util.List;

import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_Fact_Acct;
import org.compiere.util.KeyNamePair;

import de.metas.acct.api.IAccountBL;
import de.metas.acct.api.IElementValueDAO;
import de.metas.util.Services;

public class AcctBalanceRModelAggregatedValue extends AbstractRModelAggregatedValue
{
	private static final String COLUMNNAME_Account_ID = I_Fact_Acct.COLUMNNAME_Account_ID;
	private static final String COLUMNNAME_AmtAcctDr = I_Fact_Acct.COLUMNNAME_AmtAcctDr;
	private static final String COLUMNNAME_AmtAcctCr = I_Fact_Acct.COLUMNNAME_AmtAcctCr;

	// Services
	private final IAccountBL accountBL = Services.get(IAccountBL.class);
	private final IElementValueDAO elementValueDAO = Services.get(IElementValueDAO.class);

	@Override
	public void reset()
	{
		// nothing
	}

	@Override
	public void add(final RModelCalculationContext calculationCtx, final List<Object> row, final Object columnValue)
	{
		// nothing
	}

	@Override
	public Object getAggregatedValue(final RModelCalculationContext calculationCtx, final List<Object> groupRow)
	{
		if (!isGroupBy(calculationCtx, COLUMNNAME_Account_ID))
		{
			return null;
		}

		final IRModelMetadata metadata = calculationCtx.getMetadata();

		final Object[] groupLevel2Value = calculationCtx.getGroupLevel2Value();
		final int level = calculationCtx.getLevel();

		final Object account_Value = groupLevel2Value[level];
		return calculateBalance(metadata, account_Value, groupRow);
	}

	private BigDecimal calculateBalance(final IRModelMetadata metadata, Object accountObj, final List<Object> row)
	{
		//
		// Retrieve C_ElementValue
		final I_C_ElementValue elementValue = getC_ElementValue(accountObj);
		if (elementValue == null)
		{
			return null;
		}

		//
		// Get AmtAcctDr
		final BigDecimal amtAcctDr = getAmount(metadata, row, COLUMNNAME_AmtAcctDr);
		if (amtAcctDr == null)
		{
			// value does not exist or is invalid => nothing to calculate
			return null;
		}

		//
		// Get AmtAcctCr
		final BigDecimal amtAcctCr = getAmount(metadata, row, COLUMNNAME_AmtAcctCr);
		if (amtAcctCr == null)
		{
			// value does not exist or is invalid => nothing to calculate
			return null;
		}

		//
		// Calculate balance
		final BigDecimal balance = accountBL.calculateBalance(elementValue, amtAcctDr, amtAcctCr);
		return balance;
	}

	private I_C_ElementValue getC_ElementValue(final Object accountObj)
	{
		if (!(accountObj instanceof KeyNamePair))
		{
			return null;
		}
		final KeyNamePair accountKNP = (KeyNamePair)accountObj;
		final int elementValueId = accountKNP.getKey();
		final I_C_ElementValue elementValue = elementValueDAO.retrieveById(getCtx(), elementValueId);
		return elementValue;
	}

	private BigDecimal getAmount(final IRModelMetadata metadata, final List<Object> row, final String amountColumnName)
	{
		final Object amtObj = getRowValueOrNull(metadata, row, amountColumnName);
		if (amtObj == null)
		{
			// value does not exist => nothing to calculate
			return null;
		}
		else if (amtObj instanceof BigDecimal)
		{
			final BigDecimal amt = (BigDecimal)amtObj;
			return amt;
		}
		else
		{
			// invalid amount
			return null;
		}
	}
}
