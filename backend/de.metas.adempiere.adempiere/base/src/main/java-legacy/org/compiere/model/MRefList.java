/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import de.metas.ad_reference.ADReferenceService;
import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.ad_reference.ReferenceId;
import de.metas.util.Check;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Reference List Value
 *
 * @author Jorg Janke
 * @author Teo Sarca, www.arhipac.ro
 * <li>BF [ 1748449 ] Info Account - Posting Type is not translated
 * <li>FR [ 2694043 ] Query. first/firstOnly usage best practice
 * @version $Id: MRefList.java,v 1.3 2006/07/30 00:58:18 jjanke Exp $
 */
@Deprecated
public class MRefList extends X_AD_Ref_List
{
	/**
	 *
	 */
	private static final long serialVersionUID = -6948532574960232289L;

	@Deprecated
	public static String getListDescription(Properties ctx, String ListName, String Value)
	{
		final String AD_Language = Env.getAD_Language(ctx);
		final String key = AD_Language + "_" + ListName + "_" + Value;
		String retValue = s_cache.get(key);
		if (retValue != null)
			return retValue;

		final boolean isBaseLanguage = Env.isBaseLanguage(AD_Language, "AD_Ref_List");
		final String sql = isBaseLanguage ?
				"SELECT a.Description FROM AD_Ref_List a, AD_Reference b"
						+ " WHERE b.Name=? AND a.Value=?"
						+ " AND a.AD_Reference_ID = b.AD_Reference_ID"
				:
				"SELECT t.Description FROM AD_Reference r"
						+ " INNER JOIN AD_Ref_List rl ON (r.AD_Reference_ID=rl.AD_Reference_ID)"
						+ " INNER JOIN AD_Ref_List_Trl t ON (t.AD_Ref_List_ID=rl.AD_Ref_List_ID)"
						+ " WHERE r.Name=? AND rl.Value=? AND t.AD_Language=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, ListName);
			pstmt.setString(2, Value);
			if (!isBaseLanguage)
				pstmt.setString(3, AD_Language);
			rs = pstmt.executeQuery();
			if (rs.next())
				retValue = rs.getString(1);
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (final SQLException ex)
		{
			s_log.error(sql + " -- " + key, ex);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		//	Save into Cache
		if (retValue == null)
		{
			retValue = "";
			s_log.info("getListDescription - Not found " + key);
		}
		s_cache.put(key, retValue);
		//
		return retValue;
	}

	/**
	 * Logger
	 */
	private static final Logger s_log = LogManager.getLogger(MRefList.class);
	/**
	 * Value Cache
	 */
	private static final CCache<String, String> s_cache = new CCache<String, String>(Table_Name, 20);

	public MRefList(final Properties ctx, final int AD_Ref_List_ID, final String trxName)
	{
		super(ctx, AD_Ref_List_ID, trxName);
		if (is_new())
		{
			//	setAD_Reference_ID (0);
			//	setAD_Ref_List_ID (0);
			setEntityType(ENTITYTYPE_UserMaintained);    // U
			//	setName (null);
			//	setValue (null);
		}
	}    //	MRef_List

	public MRefList(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}    //	MRef_List

	/**
	 * String Representation
	 *
	 * @return Name
	 */
	@Override
	public String toString()
	{
		return getName();
	}    //	toString

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		// metas: 02827: begin
		if (Check.isEmpty(getValueName(), true))
		{
			setValueName(getName());
		}
		// metas: 02827: end

		return true;
	}

}    //	MRef_List
