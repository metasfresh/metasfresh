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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 *  Reference List Value
 *
 *  @author Jorg Janke
 *  @version $Id: MRefList.java,v 1.3 2006/07/30 00:58:18 jjanke Exp $
 *
 *  @author Teo Sarca, www.arhipac.ro
 *  		<li>BF [ 1748449 ] Info Account - Posting Type is not translated
 *  		<li>FR [ 2694043 ] Query. first/firstOnly usage best practice
 */
public class MRefList extends X_AD_Ref_List
{
	/**
	 *
	 */
	private static final long serialVersionUID = -6948532574960232289L;

	/**
	 * Get Reference List Value Name (cached)
	 * @param ctx context
	 * @param AD_Reference_ID reference
	 * @param Value value
	 * @return List or ""
	 *
	 * @deprecated Please use {@link IADReferenceDAO#retrieveListNameTrl(Properties, int, String)}
	 */
	@Deprecated
	public static String getListName (Properties ctx, int AD_Reference_ID, String Value)
	{
		return Services.get(IADReferenceDAO.class).retrieveListNameTrl(ctx, AD_Reference_ID, Value);
	}	//	getListName

	/**
	 * Get Reference List Value Description (cached)
	 * @param ListName reference
	 */
	public static String getListDescription (Properties ctx, String ListName, String Value)
	{
		String AD_Language = Env.getAD_Language(ctx);
		String key = AD_Language + "_" + ListName + "_" + Value;
		String retValue = s_cache.get(key);
		if (retValue != null)
			return retValue;

		boolean isBaseLanguage = Env.isBaseLanguage(AD_Language, "AD_Ref_List");
		String sql = isBaseLanguage ?
			"SELECT a.Description FROM AD_Ref_List a, AD_Reference b"
			+ " WHERE b.Name=? AND a.Value=?"
			+ " AND a.AD_Reference_ID = b.AD_Reference_ID"
			:
			"SELECT t.Description FROM AD_Reference r"
			+" INNER JOIN AD_Ref_List rl ON (r.AD_Reference_ID=rl.AD_Reference_ID)"
			+" INNER JOIN AD_Ref_List_Trl t ON (t.AD_Ref_List_ID=rl.AD_Ref_List_ID)"
			+" WHERE r.Name=? AND rl.Value=? AND t.AD_Language=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql,null);
			pstmt.setString (1, ListName);
			pstmt.setString(2, Value);
			if (!isBaseLanguage)
				pstmt.setString(3, AD_Language);
			rs = pstmt.executeQuery ();
			if (rs.next ())
				retValue = rs.getString(1);
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (SQLException ex)
		{
			s_log.error(sql + " -- " + key, ex);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
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
	}	//	getListDescription

	/**
	 * Get Reference List (translated)
	 * @param ctx context
	 * @param AD_Reference_ID reference
	 * @param optional if true add "",""
	 * @return List or null
	 */
	public static ValueNamePair[] getList (Properties ctx, int AD_Reference_ID, boolean optional)
	{
		String ad_language = Env.getAD_Language(ctx);
		boolean isBaseLanguage = Env.isBaseLanguage(ad_language, "AD_Ref_List");
		String sql = isBaseLanguage ?
			"SELECT Value, Name, Description FROM AD_Ref_List WHERE AD_Reference_ID=? AND IsActive='Y' ORDER BY Name"
			:
			"SELECT r.Value, t.Name, t.Description FROM AD_Ref_List_Trl t"
			+ " INNER JOIN AD_Ref_List r ON (r.AD_Ref_List_ID=t.AD_Ref_List_ID)"
			+ " WHERE r.AD_Reference_ID=?"
			+ "   AND t.AD_Language=?"
			+ "   AND r.IsActive='Y'"
			+ "   AND t.IsActive='Y'"
			+ "   AND t.IsTranslated='Y'"
			+ " ORDER BY t.Name"
		;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		final ArrayList<ValueNamePair> list = new ArrayList<ValueNamePair>();
		if (optional)
		{
			list.add(ValueNamePair.EMPTY);
		}
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Reference_ID);
			if (!isBaseLanguage)
				pstmt.setString(2, ad_language);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(ValueNamePair.of(rs.getString(1), rs.getString(2), rs.getString(3)));
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (SQLException e)
		{
			throw DBException.wrapIfNeeded(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		ValueNamePair[] retValue = new ValueNamePair[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getList


	/**	Logger							*/
	private static Logger		s_log = LogManager.getLogger(MRefList.class);
	/** Value Cache						*/
	private static CCache<String,String> s_cache = new CCache<String,String>(Table_Name, 20);


	/**************************************************************************
	 * 	Persistency Constructor
	 *	@param ctx context
	 *	@param AD_Ref_List_ID id
	 *	@param trxName transaction
	 */
	public MRefList (Properties ctx, int AD_Ref_List_ID, String trxName)
	{
		super (ctx, AD_Ref_List_ID, trxName);
		if (AD_Ref_List_ID == 0)
		{
		//	setAD_Reference_ID (0);
		//	setAD_Ref_List_ID (0);
			setEntityType (ENTITYTYPE_UserMaintained);	// U
		//	setName (null);
		//	setValue (null);
		}
	}	//	MRef_List

	/**
	 * 	Load Contructor
	 *	@param ctx context
	 *	@param rs result
	 *	@param trxName transaction
	 */
	public MRefList (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRef_List

	/**
	 *	String Representation
	 * 	@return Name
	 */
	@Override
	public String toString()
	{
		return getName();
	}	//	toString

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		// metas: 02827: begin
		if (Check.isEmpty(getValueName(), true))
		{
			setValueName(getName());
		}
		// metas: 02827: end

		return true;
	}



}	//	MRef_List
