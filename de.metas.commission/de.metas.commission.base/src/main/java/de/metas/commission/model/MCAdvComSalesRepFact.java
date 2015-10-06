package de.metas.commission.model;

/*
 * #%L
 * de.metas.commission.base
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


import java.sql.ResultSet;
import java.util.Properties;

public class MCAdvComSalesRepFact extends X_C_AdvComSalesRepFact
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3108223349961988040L;

	public MCAdvComSalesRepFact(final Properties ctx, final int C_AdvComSalesRepFact_ID, final String trxName)
	{
		super(ctx, C_AdvComSalesRepFact_ID, trxName);
	}

	public MCAdvComSalesRepFact(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("MCAdvComSalesRepFact[Id=");
		sb.append(getC_AdvComSalesRepFact_ID());
		sb.append(", Name=");
		sb.append(getName());
		sb.append(", Status=");
		sb.append(getStatus());
		sb.append(", DateAcct=");
		sb.append(getDateAcct());
		sb.append(", C_Sponsor_ID=");
		sb.append(getC_Sponsor_ID());
		sb.append(", C_Period_ID=");
		sb.append(getC_Period_ID());
		sb.append(", ValueNumber=");
		sb.append(getValueNumber());
		sb.append(", ValueStr=");
		sb.append(getValueStr());
		sb.append(", Rank-ID=");
		sb.append(getC_AdvCommissionSalaryGroup_ID());
		sb.append("]");

		return sb.toString();
	}
}
