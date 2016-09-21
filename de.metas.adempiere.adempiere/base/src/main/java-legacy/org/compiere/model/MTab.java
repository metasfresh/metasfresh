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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.Check;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 *	Tab Model
 *	
 *  @author Jorg Janke
 *  @author victor.perez@e-evolution.com, e-Evolution
 * <li>RF [2826384] The Order and Included Columns should be to fill mandatory
 * <li>http://sourceforge.net/tracker/?func=detail&atid=879335&aid=2826384&group_id=176962
 *  @version $Id: MTab.java,v 1.2 2006/07/30 00:58:37 jjanke Exp $
 */
public class MTab extends X_AD_Tab
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4946144044358216142L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_Tab_ID id
	 *	@param trxName transaction
	 */
	public MTab (Properties ctx, int AD_Tab_ID, String trxName)
	{
		super (ctx, AD_Tab_ID, trxName);
		if (AD_Tab_ID == 0)
		{
		//	setAD_Window_ID (0);
		//	setAD_Table_ID (0);
		//	setName (null);
			setEntityType (ENTITYTYPE_UserMaintained);	// U
			setHasTree (false);
			setIsReadOnly (false);
			setIsSingleRow (false);
			setIsSortTab (false);	// N
			setIsTranslationTab (false);
			setSeqNo (0);
			setTabLevel (0);
			setIsInsertRecord(true);
			setIsAdvancedTab(false);
		}
	}	//	M_Tab

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MTab (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	M_Tab

	/**
	 * 	Parent Constructor
	 *	@param parent parent
	 */
	public MTab (MWindow parent)
	{
		this (parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setAD_Window_ID(parent.getAD_Window_ID());
		setEntityType(parent.getEntityType());
	}	//	M_Tab

	/**
	 * 	Parent Constructor
	 *	@param parent parent
	 *	@param from copy from
	 */
	public MTab (MWindow parent, MTab from)
	{
		this (parent.getCtx(), 0, parent.get_TrxName());
		copyValues(from, this);
		setClientOrg(parent);
		setAD_Window_ID(parent.getAD_Window_ID());
		setEntityType(parent.getEntityType());
	}	//	M_Tab
	
	
	/**	The Fields						*/
	private MField[]		m_fields	= null;

	/**	Static Logger	*/
	private static Logger	s_log	= LogManager.getLogger(MTab.class);
	
	/**	Packages for Model Classes	*/
	/**
	 * 	Get Fields
	 *	@param reload reload data
	 *	@return array of lines
	 *	@param trxName transaction
	 */
	public MField[] getFields (boolean reload, String trxName)
	{
		if (m_fields != null && !reload)
			return m_fields;
		String sql = "SELECT * FROM AD_Field WHERE AD_Tab_ID=? ORDER BY SeqNo";
		ArrayList<MField> list = new ArrayList<MField>();
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt (1, getAD_Tab_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MField (getCtx(), rs, trxName));
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		//
		m_fields = new MField[list.size ()];
		list.toArray (m_fields);
		return m_fields;
	}	//	getFields

	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
	//	UPDATE AD_Tab SET IsInsertRecord='N' WHERE IsInsertRecord='Y' AND IsReadOnly='Y'
		if (isReadOnly() && isInsertRecord())
			setIsInsertRecord(false);
		//RF[2826384]
		if(isSortTab())
		{
			if(getAD_ColumnSortOrder_ID() == 0)
			{
				throw new FillMandatoryException("AD_ColumnSortOrder_ID");	
			}			
		}

		// Prevent adding more wrong cases.
		if(is_ValueChanged(COLUMNNAME_OrderByClause) && !Check.isEmpty(getOrderByClause(), true))
		{
			throw new AdempiereException("OrderByClause shall be empty. See https://github.com/metasfresh/metasfresh/issues/412");
		}
		
		return true;
	}
	
	// begin e-evolution vpj-cd
	/**
	 * 	get Tab ID
	 *	@param String AD_Window_ID
	 *	@param String TabName
	 *	@return int retValue
	 */
	public static int getTab_ID(int AD_Window_ID , String TabName) {
		int retValue = 0;
		String SQL = "SELECT AD_Tab_ID FROM AD_Tab WHERE AD_Window_ID= ?  AND Name = ?";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(SQL, null);
			pstmt.setInt(1, AD_Window_ID);
			pstmt.setString(2, TabName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				retValue = rs.getInt(1);
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			s_log.error(SQL, e);
			retValue = -1;
		}
		return retValue;
	}
	//end vpj-cd e-evolution
	
}	//	M_Tab
