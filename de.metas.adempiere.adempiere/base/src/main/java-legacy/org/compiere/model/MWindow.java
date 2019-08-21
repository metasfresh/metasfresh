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

import java.awt.Dimension;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 *	Window Model
 *
 *  @author Jorg Janke
 *  @version $Id: MWindow.java,v 1.2 2006/07/30 00:58:05 jjanke Exp $
 */
public class MWindow extends X_AD_Window
{
	/**
	 *
	 */
	private static final long serialVersionUID = 6783399136841920556L;
	/**	Static Logger	*/
	private static Logger	s_log	= LogManager.getLogger(MWindow.class);

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_Window_ID
	 *	@param trxName transaction
	 */
	public MWindow (Properties ctx, int AD_Window_ID, String trxName)
	{
		super (ctx, AD_Window_ID, trxName);
		if (is_new())
		{
			setWindowType (WINDOWTYPE_Maintain);	// M
			setEntityType (ENTITYTYPE_UserMaintained);	// U
			setIsBetaFunctionality (false);
			setIsDefault (false);
			setIsSOTrx (true);	// Y
		}	}	//	M_Window

	/**
	 * 	Koad Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MWindow (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	M_Window

	/**
	 * 	Set Window Size
	 *	@param size size
	 */
	public void setWindowSize (Dimension size)
	{
		if (size != null)
		{
			setWinWidth(size.width);
			setWinHeight(size.height);
		}
	}	//	setWindowSize

	/**	The Lines						*/
	private MTab[]		m_tabs	= null;

	/**
	 * 	Get Fields
	 *	@param reload reload data
	 *	@return array of lines
	 *	@param trxName transaction
	 */
	public MTab[] getTabs (boolean reload, String trxName)
	{
		if (m_tabs != null && !reload)
		{
			return m_tabs;
		}
		String sql = "SELECT * FROM AD_Tab WHERE AD_Window_ID=? ORDER BY SeqNo";
		ArrayList<MTab> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt (1, getAD_Window_ID());
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				list.add (new MTab (getCtx(), rs, trxName));
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//
		m_tabs = new MTab[list.size ()];
		list.toArray (m_tabs);
		return m_tabs;
	}	//	getFields


	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (newRecord)	//	Add to all automatic roles
		{
			// Add to all automatic roles ... handled elsewhere
		}
		// Menu/Workflow update
		else if (is_ValueChanged("IsActive") || is_ValueChanged("Name")
			|| is_ValueChanged("Description") || is_ValueChanged("Help"))
		{
			MMenu[] menues = MMenu.get(getCtx(), "AD_Window_ID=" + getAD_Window_ID(), get_TrxName());
			for (MMenu menue : menues)
			{
				menue.setName(getName());
				menue.setDescription(getDescription());
				menue.setIsActive(isActive());
				menue.save();
			}
			//
			X_AD_WF_Node[] nodes = getWFNodes(getCtx(), "AD_Window_ID=" + getAD_Window_ID(), get_TrxName());
			for (X_AD_WF_Node node : nodes)
			{
				boolean changed = false;
				if (node.isActive() != isActive())
				{
					node.setIsActive(isActive());
					changed = true;
				}
				if (node.isCentrallyMaintained())
				{
					node.setName(getName());
					node.setDescription(getDescription());
					node.setHelp(getHelp());
					changed = true;
				}
				if (changed)
				{
					node.save();
				}
			}
		}
		return success;
	}	//	afterSave


	/**
	 * Get workflow nodes with where clause.
	 * Is here as MWFNode is in base
	 * @param ctx context
	 * @param whereClause where clause w/o the actual WHERE
	 * @param trxName transaction
	 * @return nodes
	 */
	public static X_AD_WF_Node[] getWFNodes (Properties ctx, String whereClause, String trxName)
	{
		String sql = "SELECT * FROM AD_WF_Node";
		if (whereClause != null && whereClause.length() > 0)
		{
			sql += " WHERE " + whereClause;
		}
		ArrayList<X_AD_WF_Node> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				list.add (new X_AD_WF_Node (ctx, rs, trxName));
			}
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		X_AD_WF_Node[] retValue = new X_AD_WF_Node[list.size()];
		list.toArray (retValue);
		return retValue;
	}	//	getWFNode

	//vpj-cd begin e-evolution
	/**
	 * 	get Window ID
	 *	@param String windowName
	 *	@return int retValue
	 */
	public static int getWindow_ID(String windowName) {
		int retValue = 0;
		String SQL = "SELECT AD_Window_ID FROM AD_Window WHERE Name = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(SQL, null);
			pstmt.setString(1, windowName);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				retValue = rs.getInt(1);
			}
		}
		catch (SQLException e)
		{
			s_log.error(SQL, e);
			retValue = -1;
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		return retValue;
	}
	//end vpj-cd e-evolution

}	//	M_Window
