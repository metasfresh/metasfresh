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

<<<<<<< HEAD

import java.sql.ResultSet;
import java.sql.SQLException;
=======
import de.metas.cache.CCache;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;

import javax.annotation.Nullable;
import java.sql.ResultSet;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

<<<<<<< HEAD
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.cache.CCache;
import de.metas.util.Check;
import de.metas.util.Services;

import javax.annotation.Nullable;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
/**
 * @author Trifon N. Trifonov
 * @author Antonio Cañaveral, e-Evolution
 * 				<li>[ 2195090 ] Implementing ExportFormat cache
<<<<<<< HEAD
 * 				<li>http://sourceforge.net/tracker/index.php?func=detail&aid=2195090&group_id=176962&atid=879335
 * @author victor.perez@e-evolution.com, e-Evolution
 * 				<li>[ 2195090 ] Stabilization of replication
 * 				<li>https://sourceforge.net/tracker/?func=detail&atid=879332&aid=2936561&group_id=176962
=======
 * 				<li><a href="http://sourceforge.net/tracker/index.php?func=detail&aid=2195090&group_id=176962&atid=879335">http://sourceforge.net/tracker/index.php?func=detail&aid=2195090&group_id=176962&atid=879335</a>
 * @author victor.perez@e-evolution.com, e-Evolution
 * 				<li>[ 2195090 ] Stabilization of replication
 * 				<li><a href="https://sourceforge.net/tracker/?func=detail&atid=879332&aid=2936561&group_id=176962">https://sourceforge.net/tracker/?func=detail&atid=879332&aid=2936561&group_id=176962</a>
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 *
 */
public class MEXPFormat extends X_EXP_Format {
	/**
	 *
	 */
	private static final long serialVersionUID = -5011042965945626099L;

<<<<<<< HEAD
	private static CCache<String,MEXPFormat> s_cache = new CCache<>(MEXPFormat.Table_Name, 50 );
	private static CCache<Integer,MEXPFormat> exp_format_by_id_cache = new CCache<>(MEXPFormat.Table_Name, 50);

	private List<I_EXP_FormatLine> m_lines_unique = null;

	public MEXPFormat(Properties ctx, int EXP_Format_ID, String trxName)
=======
	private static final CCache<String,MEXPFormat> s_cache = new CCache<>(MEXPFormat.Table_Name, 50 );
	private static final CCache<Integer,MEXPFormat> exp_format_by_id_cache = new CCache<>(MEXPFormat.Table_Name, 50);

	private static final CCache<Integer, List<I_EXP_FormatLine>> s_cache_lines_unique = CCache.newLRUCache(I_EXP_FormatLine.Table_Name, 100, 100);

	public MEXPFormat(final Properties ctx, final int EXP_Format_ID, final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		super(ctx, EXP_Format_ID, trxName);
	}

<<<<<<< HEAD
	public MEXPFormat(Properties ctx, ResultSet rs, String trxName) {
=======
	public MEXPFormat(final Properties ctx, final ResultSet rs, final String trxName) {
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		super (ctx, rs, trxName);
	}

	public List<I_EXP_FormatLine> getFormatLines() {
		return getFormatLinesOrderedBy(I_EXP_FormatLine.COLUMNNAME_Position);
	}

	private List<I_EXP_FormatLine> _lines = null;
<<<<<<< HEAD
	private String _lines_orderByClause = null;

	public List<I_EXP_FormatLine> getFormatLinesOrderedBy(String orderBy)
	{
		return getFormatLinesOrderedBy(false, orderBy);
	}
	public List<I_EXP_FormatLine> getFormatLinesOrderedBy(boolean requery, String orderBy)
=======
	private final String _lines_orderByClause = null;

	public List<I_EXP_FormatLine> getFormatLinesOrderedBy(final String orderBy)
	{
		return getFormatLinesOrderedBy(false, orderBy);
	}
	public List<I_EXP_FormatLine> getFormatLinesOrderedBy(final boolean requery, final String orderBy)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
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
=======
		return s_cache_lines_unique.getOrLoad(getEXP_Format_ID(), MEXPFormat::getUniqueColumns0);
	}
	
	private static List<I_EXP_FormatLine> getUniqueColumns0(@NonNull final Integer expFormatId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_EXP_FormatLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EXP_FormatLine.COLUMNNAME_EXP_Format_ID, expFormatId)
				.addEqualsFilter(I_EXP_FormatLine.COLUMNNAME_IsPartUniqueIndex, true)
				.orderBy(X_EXP_FormatLine.COLUMNNAME_Position)
				.create()
				.list();
	}

	public static MEXPFormat get(final Properties ctx, final int EXP_Format_ID, final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		MEXPFormat exp_format = exp_format_by_id_cache.get(EXP_Format_ID);
		if(exp_format != null)
		{
			return exp_format;
		}
		exp_format = new MEXPFormat(ctx, EXP_Format_ID , trxName);
<<<<<<< HEAD
		if(exp_format!=null)
		{
		exp_format.getFormatLines();
		exp_format_by_id_cache.put(EXP_Format_ID, exp_format);
		}
=======
		
		exp_format.getFormatLines();
		exp_format_by_id_cache.put(EXP_Format_ID, exp_format);
		
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return exp_format;
	}

	/**
	 * Returns the format with the given AD_client_ID (or 0), Value and Version or <code>null</code>. Note that this triple is
	 * unique for all formats.
	 */
	public static MEXPFormat getFormatByValueAD_Client_IDAndVersion(
			final Properties ctx, final String value, final int AD_Client_ID, final String version, @Nullable final String trxName)
	{
<<<<<<< HEAD
		final String key = new String(AD_Client_ID + value + version);
=======
		final String key = AD_Client_ID + value + version;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		if (trxName == null)
		{
			final MEXPFormat cachedValue = s_cache.get(key);
			if (cachedValue != null && cachedValue.get_TrxName() == null)
			{
				return cachedValue;
			}
		}
<<<<<<< HEAD
		final StringBuilder whereClause =
				new StringBuilder(X_EXP_Format.COLUMNNAME_Value).append("=?")
						.append(" AND AD_Client_ID IN (?, 0)")
						.append(" AND ").append(X_EXP_Format.COLUMNNAME_Version).append(" = ?");

		final MEXPFormat retValue =
				new Query(ctx, X_EXP_Format.Table_Name, whereClause.toString(), trxName)
=======
		final String whereClause = X_EXP_Format.COLUMNNAME_Value + "=?"
				+ " AND IsActive = 'Y'"
				+ " AND AD_Client_ID IN (?, 0)"
				+ " AND " + X_EXP_Format.COLUMNNAME_Version + " = ?";

		final MEXPFormat retValue =
				new Query(ctx, X_EXP_Format.Table_Name, whereClause, trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public static MEXPFormat getFormatByAD_Client_IDAD_Table_IDAndVersion(Properties ctx, int AD_Client_ID, int AD_Table_ID, String version, String trxName)
	{
		String key = new String(Services.get(IADTableDAO.class).retrieveTableName(AD_Table_ID) + version);
=======
	public static MEXPFormat getFormatByAD_Client_IDAD_Table_IDAndVersion(final Properties ctx, final int AD_Client_ID, final int AD_Table_ID, final String version, final String trxName)
	{
		final String key = Services.get(IADTableDAO.class).retrieveTableName(AD_Table_ID) + version;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		MEXPFormat retValue=null;

		if(trxName == null)
		{
			retValue = s_cache.get(key);
		}
		if(retValue!=null)
		{
			return retValue;
		}

<<<<<<< HEAD
		List<Object> params = new ArrayList<>();
		StringBuffer whereClause = new StringBuffer(" AD_Client_ID = ? ")
=======
		final List<Object> params = new ArrayList<>();
		final StringBuilder whereClause = new StringBuilder(" AD_Client_ID = ? ")
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			.append("  AND ").append(X_EXP_Format.COLUMNNAME_AD_Table_ID).append(" = ? ");
		params.add(AD_Client_ID);
		params.add(AD_Table_ID);

		// metas: tsa: changed to filter by version only if is provided
		if (!Check.isEmpty(version, true))
		{
			whereClause.append("  AND ").append(X_EXP_Format.COLUMNNAME_Version).append(" = ?");
			params.add(version);
		}

<<<<<<< HEAD
		retValue = (MEXPFormat) new Query(ctx,X_EXP_Format.Table_Name,whereClause.toString(),trxName)
=======
		retValue = new Query(ctx,X_EXP_Format.Table_Name,whereClause.toString(),trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		StringBuffer sb = new StringBuffer ("MEXPFormat[ID=").append(get_ID()).append("; Value = "+getValue()+"]");
		return sb.toString();
=======
		return "MEXPFormat[ID=" + get_ID() + "; Value = " + getValue() + "]";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	/**
	 * 	Before Delete
	 *	@return true of it can be deleted
	 */
	@Override
	protected boolean beforeDelete ()
	{
<<<<<<< HEAD
		for (I_EXP_FormatLine line : getFormatLinesOrderedBy(true, null))
=======
		for (final I_EXP_FormatLine line : getFormatLinesOrderedBy(true, null))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			InterfaceWrapperHelper.delete(line);
		}
		return true;
	}	//	beforeDelete
}
