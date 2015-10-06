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

import de.metas.adempiere.util.CacheCtx;

public class MCAdvCommissionSalaryGroup extends X_C_AdvCommissionSalaryGroup
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 189260415109050779L;

	public MCAdvCommissionSalaryGroup(
			final @CacheCtx Properties ctx,
			final int C_AdvCommissionSalaryGroup_ID,
			final String trxName)
	{
		super(ctx, C_AdvCommissionSalaryGroup_ID, trxName);
	}

	public MCAdvCommissionSalaryGroup(
			final Properties ctx,
			final ResultSet rs,
			final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("MCAdvCommissionSalaryGroup[Id=");
		sb.append(get_ID());
		sb.append(", Name=");
		sb.append(getName());
		sb.append(", Value=");
		sb.append(getValue());
		sb.append(", SeqNo=");
		sb.append(getSeqNo());
		sb.append("]");

		return sb.toString();
	}

}
