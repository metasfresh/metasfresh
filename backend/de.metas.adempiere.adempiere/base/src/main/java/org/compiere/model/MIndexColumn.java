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


import java.sql.ResultSet;
import java.util.Properties;

import de.metas.util.Check;

/**
 * AD Index Column
 * 
 * @author Teo Sarca, teo.sarca@gmail.com
 */
public class MIndexColumn extends X_AD_Index_Column {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1907712672821691643L;

	public MIndexColumn(Properties ctx, int AD_Index_Column_ID, String trxName) {
		super(ctx, AD_Index_Column_ID, trxName);
	}

	public MIndexColumn(Properties ctx, ResultSet rs, String trxName) {
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
		int AD_Column_ID = getAD_Column_ID();
		return MColumn.getColumnName(getCtx(), AD_Column_ID);
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MIndexColumn[").append(get_ID())
				.append(", AD_Column_ID=").append(getAD_Column_ID())
				.append("]");
		return sb.toString();
	}

	public static MIndexColumn retrieveOrCreate(final MIndexTable indexTable,
			final String columnName, final int seqNo) {

		final Properties ctx = indexTable.getCtx();
		final String trxName = indexTable.get_TrxName();
		final MTable table = (MTable) indexTable.getAD_Table();

		final MColumn column = table.getColumn(columnName);

		if (column == null) {
			throw new IllegalArgumentException("Illegal column name '"
					+ columnName + "' for table " + table);
		}

		final String whereClause = COLUMNNAME_AD_Index_Table_ID + "=? AND "
				+ COLUMNNAME_AD_Column_ID + "=?";

		final Object[] params = { indexTable.get_ID(), column.get_ID() };

		MIndexColumn indexColumn = new Query(ctx, Table_Name,
				whereClause, trxName).setParameters(params).firstOnly();

		if (indexColumn == null) {
			
			indexColumn = new MIndexColumn(ctx, 0, trxName);
			indexColumn.setAD_Index_Table_ID(indexTable.get_ID());
			indexColumn.setAD_Column_ID(column.get_ID());
		}
		
		indexColumn.setSeqNo(seqNo);
		indexColumn.saveEx();
		
		return indexColumn;
	}
}
