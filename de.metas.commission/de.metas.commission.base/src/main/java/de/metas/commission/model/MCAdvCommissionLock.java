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

public class MCAdvCommissionLock extends X_C_AdvCommissionLock
{
	// private static final Logger logger = CLogMgt.getLogger(MCAdvCommissionLock.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -6903484861105853165L;

	public MCAdvCommissionLock(final Properties ctx, final int C_AdvCommissionLock_ID, final String trxName)
	{
		super(ctx, C_AdvCommissionLock_ID, trxName);
	}

	public MCAdvCommissionLock(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{

		final StringBuffer sb = new StringBuffer();
		sb.append("MCAdvCommissionLock[Id=");
		sb.append(get_ID());
		sb.append(", C_BPartner_ID=");
		sb.append(getC_BPartner_ID());
		sb.append(", DateFrom=");
		sb.append(getDateFrom());
		sb.append(", DateTo=");
		sb.append(getDateTo());
		sb.append("]");

		return sb.toString();
	}

}
