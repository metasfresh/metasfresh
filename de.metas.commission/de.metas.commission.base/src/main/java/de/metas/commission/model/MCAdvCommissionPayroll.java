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

public class MCAdvCommissionPayroll extends X_C_AdvCommissionPayroll
{

	private int nextLine = 0;

	public int nextLine()
	{
		nextLine += 1;
		return nextLine;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8578828030699244747L;

	public MCAdvCommissionPayroll(final Properties ctx,
			final int C_AdvCommissionPayroll_ID, final String trxName)
	{

		super(ctx, C_AdvCommissionPayroll_ID, trxName);
	}

	public MCAdvCommissionPayroll(final Properties ctx, final ResultSet rs, final String trxName)
	{

		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{

		final StringBuffer sb = new StringBuffer();
		sb.append("MCAdvCommissionPayroll[Id=");
		sb.append(get_ID());
		sb.append(", DocumentNo=");
		sb.append(getDocumentNo());
		sb.append(", C_Period_ID=");
		sb.append(getC_Period_ID());
		sb.append("]");

		return sb.toString();
	}
}
