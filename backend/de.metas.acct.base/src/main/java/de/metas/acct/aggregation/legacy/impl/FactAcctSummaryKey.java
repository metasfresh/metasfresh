package de.metas.acct.aggregation.legacy.impl;

import de.metas.acct.aggregation.legacy.IFactAcctSummaryKey;
import de.metas.acct.model.I_Fact_Acct_Log;
import de.metas.util.Check;
import org.adempiere.util.lang.EqualsBuilder;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import java.util.Date;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

final class FactAcctSummaryKey implements IFactAcctSummaryKey
{
	public static IFactAcctSummaryKey of(final I_Fact_Acct_Log log)
	{
		return new FactAcctSummaryKey(log);
	}

	private final int C_ElementValue_ID;
	private final int C_AcctSchema_ID;
	private final String postingType;
	private final int C_Period_ID;
	private final long dateAcctMs;
	private final int AD_Client_ID;
	private final int AD_Org_ID;
	private final int PA_ReportCube_ID;
	private final ArrayKey _hashKey;

	private FactAcctSummaryKey(final I_Fact_Acct_Log log)
	{
		super();
		Check.assumeNotNull(log, "log not null");

		C_ElementValue_ID = log.getC_ElementValue_ID();
		C_AcctSchema_ID = log.getC_AcctSchema_ID();
		postingType = log.getPostingType();
		C_Period_ID = log.getC_Period_ID();
		
		final Date dateAcct = log.getDateAcct();
		Check.assumeNotNull(dateAcct, "dateAcct not null for {}", log);
		dateAcctMs = TimeUtil.trunc(dateAcct, TimeUtil.TRUNC_DAY).getTime();

		AD_Client_ID = log.getAD_Client_ID();
		AD_Org_ID = log.getAD_Org_ID(); // FRESH-326: set the AD_Org from the log
		PA_ReportCube_ID = -1;
		_hashKey = Util.mkKey(C_ElementValue_ID, C_AcctSchema_ID, postingType, C_Period_ID, dateAcctMs, AD_Client_ID, AD_Org_ID, PA_ReportCube_ID);
	}

	@Override
	public String toString()
	{
		return asString();
	}

	@Override
	public String asString()
	{
		return _hashKey.toString();
	}

	@Override
	public int hashCode()
	{
		return _hashKey.hashCode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final FactAcctSummaryKey other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(_hashKey, other._hashKey)
				.isEqual();
	}

	@Override
	public int getC_ElementValue_ID()
	{
		return C_ElementValue_ID;
	}

	@Override
	public int getC_AcctSchema_ID()
	{
		return C_AcctSchema_ID;
	}

	@Override
	public String getPostingType()
	{
		return postingType;
	}

	@Override
	public int getC_Period_ID()
	{
		return C_Period_ID;
	}

	@Override
	public Date getDateAcct()
	{
		return new Date(dateAcctMs);
	}

	@Override
	public int getAD_Client_ID()
	{
		return AD_Client_ID;
	}

	@Override
	public int getAD_Org_ID()
	{
		return AD_Org_ID;
	}

	@Override
	public int getPA_ReportCube_ID()
	{
		return PA_ReportCube_ID;
	}
}