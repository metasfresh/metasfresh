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
import de.metas.adempiere.util.CacheIgnore;

public class MCAdvCommissionType extends X_C_AdvCommissionType
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6446069684876129699L;

	public MCAdvCommissionType(@CacheCtx final Properties ctx, final int C_AdvCommissionType_ID, @CacheIgnore final String trxName)
	{
		super(ctx, C_AdvCommissionType_ID, trxName);
	}

	public MCAdvCommissionType(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("MCAdvCommissionType[Id=");
		sb.append(get_ID());

		sb.append(", Name=");
		sb.append(getName());
		sb.append(", Version=");
		sb.append(getVersion());
		sb.append(", Classname=");
		sb.append(getClassname());
		sb.append("]");

		return sb.toString();
	}
}
