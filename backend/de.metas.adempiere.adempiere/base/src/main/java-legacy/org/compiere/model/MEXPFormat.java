/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                          *
 * http://www.adempiere.org                                           *
 *                                                                    *
 * Copyright (C) Trifon Trifonov.                                     *
 * Copyright (C) Contributors                                         *
 *                                                                    *
 * This program is free software; you can redistribute it and/or      *
 * modify it under the terms of the GNU General Public License        *
 * as published by the Free Software Foundation; either version 2     *
 * of the License, or (at your option) any later version.             *
 *                                                                    *
 * This program is distributed in the hope that it will be useful,    *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of     *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the       *
 * GNU General Public License for more details.                       *
 *                                                                    *
 * You should have received a copy of the GNU General Public License  *
 * along with this program; if not, write to the Free Software        *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         *
 * MA 02110-1301, USA.                                                *
 *                                                                    *
 * Contributors:                                                      *
 *  - Trifon Trifonov (trifonnt@users.sourceforge.net)                *
 *  - Antonio Cañaveral, e-Evolution
 *                                                                    *
 * Sponsors:                                                          *
 *  - e-Evolution (http://www.e-evolution.com/)                       *
 *********************************************************************/

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

import de.metas.cache.CCache;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * @author Trifon N. Trifonov
 * @author Antonio Cañaveral, e-Evolution
 * 				<li>[ 2195090 ] Implementing ExportFormat cache
 * 				<li>http://sourceforge.net/tracker/index.php?func=detail&aid=2195090&group_id=176962&atid=879335
 * @author victor.perez@e-evolution.com, e-Evolution
 * 				<li>[ 2195090 ] Stabilization of replication
 * 				<li>https://sourceforge.net/tracker/?func=detail&atid=879332&aid=2936561&group_id=176962
 *
 */
public class MEXPFormat extends X_EXP_Format {
	/**
	 *
	 */
	private static final long serialVersionUID = -5011042965945626099L;

	private static CCache<String,MEXPFormat> s_cache = new CCache<>(MEXPFormat.Table_Name, 50 );
	private static CCache<Integer,MEXPFormat> exp_format_by_id_cache = new CCache<>(MEXPFormat.Table_Name, 50);

	private List<I_EXP_FormatLine> m_lines_unique = null;

	public MEXPFormat(Properties ctx, int EXP_Format_ID, String trxName)
	{
		super(ctx, EXP_Format_ID, trxName);
	}

	public MEXPFormat(Properties ctx, ResultSet rs, String trxName) {
		super (ctx, rs, trxName);
	}

	public List<I_EXP_FormatLine> getFormatLines() {
		return getFormatLinesOrderedBy(I_EXP_FormatLine.COLUMNNAME_Position);
	}

	private List<I_EXP_FormatLine> _lines = null;
	private String _lines_orderByClause = null;

	public List<I_EXP_FormatLine> getFormatLinesOrderedBy(String orderBy)
	{
		return getFormatLinesOrderedBy(false, orderBy);
	}
	public List<I_EXP_FormatLine> getFormatLinesOrderedBy(boolean requery, String orderBy)
	{
		if(!requery && _lines != null && Objects.equals(_lines_orderByClause, orderBy))
		{
			return _lines;
		}

		final String clauseWhere = X_EXP_FormatLine.COLUMNNAME_EXP_Format_ID + "=?";
		_lines = new Query(getCtx() , I_EXP_FormatLine.Table_Name, clauseWhere , get_TrxName())
						.setOnlyActiveRecords(true)
						.setParameters(getEXP_Format_ID())
						.setOrderBy(orderBy)
						.list(I_EXP_FormatLine.class);
		return _lines;
	}

	public List<I_EXP_FormatLine> getUniqueColumns()
	{
		if (m_lines_unique != null)
		{
			return m_lines_unique;
		}

		final String clauseWhere = X_EXP_FormatLine.COLUMNNAME_EXP_Format_ID+"= ?"
								 + " AND " + X_EXP_FormatLine.COLUMNNAME_IsPartUniqueIndex +"= ?";
		m_lines_unique = new Query(getCtx(), I_EXP_FormatLine.Table_Name, clauseWhere, get_TrxName())
													 .setOnlyActiveRecords(true)
													 .setParameters(getEXP_Format_ID(), true)
													 .setOrderBy(X_EXP_FormatLine.COLUMNNAME_Position)
													 .list(I_EXP_FormatLine.class);
		return m_lines_unique;
	}

	public static MEXPFormat get(Properties ctx, int EXP_Format_ID, String trxName)
	{
		MEXPFormat exp_format = exp_format_by_id_cache.get(EXP_Format_ID);
		if(exp_format != null)
		{
			return exp_format;
		}
		exp_format = new MEXPFormat(ctx, EXP_Format_ID , trxName);
		if(exp_format!=null)
		{
		exp_format.getFormatLines();
		exp_format_by_id_cache.put(EXP_Format_ID, exp_format);
		}
		return exp_format;
	}

	/**
	 * Returns the format with the given AD_client_ID (or 0), Value and Version or <code>null</code>. Note that this triple is
	 * unique for all formats.
	 */
	public static MEXPFormat getFormatByValueAD_Client_IDAndVersion(
			final Properties ctx, final String value, final int AD_Client_ID, final String version, @Nullable final String trxName)
	{
		final String key = new String(AD_Client_ID + value + version);

		if (trxName == null)
		{
			final MEXPFormat cachedValue = s_cache.get(key);
			if (cachedValue != null && cachedValue.get_TrxName() == null)
			{
				return cachedValue;
			}
		}
		final StringBuilder whereClause =
				new StringBuilder(X_EXP_Format.COLUMNNAME_Value).append("=?")
						.append(" AND IsActive = 'Y'")
						.append(" AND AD_Client_ID IN (?, 0)")
						.append(" AND ").append(X_EXP_Format.COLUMNNAME_Version).append(" = ?");

		final MEXPFormat retValue =
				new Query(ctx, X_EXP_Format.Table_Name, whereClause.toString(), trxName)
						.setParameters(value, AD_Client_ID, version)
						.setOrderBy("AD_Client_ID DESC")
						.first();

		if(retValue != null)
		{
			retValue.getFormatLines();
			s_cache.put (key, retValue);
			exp_format_by_id_cache.put(retValue.getEXP_Format_ID(), retValue);
		}

		return retValue;
	}

	// metas: tsa: remove throws SQLException
	public static MEXPFormat getFormatByAD_Client_IDAD_Table_IDAndVersion(Properties ctx, int AD_Client_ID, int AD_Table_ID, String version, String trxName)
	{
		String key = new String(Services.get(IADTableDAO.class).retrieveTableName(AD_Table_ID) + version);
		MEXPFormat retValue=null;

		if(trxName == null)
		{
			retValue = s_cache.get(key);
		}
		if(retValue!=null)
		{
			return retValue;
		}

		List<Object> params = new ArrayList<>();
		StringBuffer whereClause = new StringBuffer(" AD_Client_ID = ? ")
			.append("  AND ").append(X_EXP_Format.COLUMNNAME_AD_Table_ID).append(" = ? ");
		params.add(AD_Client_ID);
		params.add(AD_Table_ID);

		// metas: tsa: changed to filter by version only if is provided
		if (!Check.isEmpty(version, true))
		{
			whereClause.append("  AND ").append(X_EXP_Format.COLUMNNAME_Version).append(" = ?");
			params.add(version);
		}

		retValue = (MEXPFormat) new Query(ctx,X_EXP_Format.Table_Name,whereClause.toString(),trxName)
						.setParameters(params)
						.setOrderBy(X_EXP_Format.COLUMNNAME_Version+" DESC")
						.first();
		if(retValue!=null)
		{
			retValue.getFormatLines();
			if(trxName == null) // metas: tsa: cache only if trxName==null
			{
			s_cache.put (key, retValue);
			exp_format_by_id_cache.put(retValue.getEXP_Format_ID(), retValue);
			}
		}

		return retValue;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer ("MEXPFormat[ID=").append(get_ID()).append("; Value = "+getValue()+"]");
		return sb.toString();
	}

	/**
	 * 	Before Delete
	 *	@return true of it can be deleted
	 */
	@Override
	protected boolean beforeDelete ()
	{
		for (I_EXP_FormatLine line : getFormatLinesOrderedBy(true, null))
		{
			InterfaceWrapperHelper.delete(line);
		}
		return true;
	}	//	beforeDelete
}
