/**
 *
 */
package org.compiere.model;

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

import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.table.api.IADTableDAO;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * AD Index Column
 *
 * @author Teo Sarca, teo.sarca@gmail.com
 */
public class MIndexColumn extends X_AD_Index_Column
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1907712672821691643L;

	@SuppressWarnings("unused")
	public MIndexColumn(Properties ctx, int AD_Index_Column_ID, String trxName)
	{
		super(ctx, AD_Index_Column_ID, trxName);
	}

	@SuppressWarnings("unused")
	public MIndexColumn(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Get Column Name
	 *
	 * @return column name
	 */
	public String getColumnName()
	{
		String sql = getColumnSQL();
		if (!Check.isEmpty(sql, true))
		{
			return sql;
		}
		return Services.get(IADTableDAO.class).retrieveColumnName(getAD_Column_ID());
	}

	@Override
	public String toString()
	{
		return "MIndexColumn[" + get_ID()
				+ ", AD_Column_ID=" + getAD_Column_ID()
				+ "]";
	}

}
