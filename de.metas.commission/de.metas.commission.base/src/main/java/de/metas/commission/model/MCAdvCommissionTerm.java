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

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Provisionsberechnung_%282009_0023_G106%29'>(2009 0023 G106)</a>"
 */
public class MCAdvCommissionTerm extends X_C_AdvCommissionTerm
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7824920615314984744L;

	public MCAdvCommissionTerm(final Properties ctx, final int C_AdvCommissionTerm_ID, final String trxName)
	{
		super(ctx, C_AdvCommissionTerm_ID, trxName);
	}

	public MCAdvCommissionTerm(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer();
		sb.append("MAdvCommissionTerm[Id=");
		sb.append(get_ID());
		sb.append(", Name=");
		sb.append(getName());
		sb.append(", C_AdvCommissionCondition_ID=");
		sb.append(getC_AdvCommissionCondition_ID());
		sb.append("]");

		return sb.toString();
	}
}
