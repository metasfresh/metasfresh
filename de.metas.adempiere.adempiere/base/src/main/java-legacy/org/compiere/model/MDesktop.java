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
import java.util.logging.Level;

import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;


/**
 *  Desktop Model
 *
 *  @author Jorg Janke
 *  @version $Id: MDesktop.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MDesktop
{
	/**
	 *  Desktop Model
	 *  @param ctx context
	 */
	public MDesktop(Properties ctx)
	{
		m_ctx = ctx;
	}   //  MDesktop

	/** Properties      */
	private Properties  m_ctx;

	/** List of workbenches */
	private ArrayList<Integer>   m_workbenches = new ArrayList<Integer>();

	private int         AD_Desktop_ID;
	private String      Name;
	private String      Description;
	private String      Help;
	private int         AD_Column_ID;
	private int         AD_Image_ID;
	private int         AD_Color_ID;
	private int         PA_Goal_ID;

	/**	Logger			*/
	private CLogger	log = CLogger.getCLogger(getClass());
	
	/**
	 *  Init Desktop
	 *  @param ad_Desktop_ID desktop
	 *  @return true if initialized
	 */
	public boolean initDesktop (int ad_Desktop_ID)
	{
		AD_Desktop_ID = ad_Desktop_ID;
		//  Get WB info
		String sql = null;
		if (Env.isBaseLanguage(m_ctx, "AD_Desktop"))
			sql = "SELECT Name,Description,Help,"                       //  1..3
				+ " AD_Column_ID,AD_Image_ID,AD_Color_ID,PA_Goal_ID "  //   4..7
				+ "FROM AD_Desktop "
				+ "WHERE AD_Desktop_ID=? AND IsActive='Y'";
		else
			sql = "SELECT t.Name,t.Description,t.Help,"
				+ " w.AD_Column_ID,w.AD_Image_ID,w.AD_Color_ID,w.PA_Goal_ID "
				+ "FROM AD_Desktop w, AD_Desktop_Trl t "
				+ "WHERE w.AD_Desktop_ID=? AND w.IsActive='Y'"
				+ " AND w.AD_Desktop_ID=t.AD_Desktop_ID"
				+ " AND t.AD_Language='" + Env.getAD_Language(m_ctx) + "'";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Desktop_ID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				Name = rs.getString(1);
				Description = rs.getString(2);
				if (Description == null)
					Description = "";
				Help = rs.getString(3);
				if (Help == null)
					Help = "";
				//
				AD_Column_ID = rs.getInt(4);
				AD_Image_ID = rs.getInt(5);
				AD_Color_ID = rs.getInt(6);
				PA_Goal_ID = rs.getInt(7);
			}
			else
				AD_Desktop_ID = 0;
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}

		if (AD_Desktop_ID == 0)
			return false;
		return initDesktopWorkbenches();
	}   //  initDesktop

	/**
	 *  String Representation
	 *  @return info
	 */
	public String toString()
	{
		return "MDesktop ID=" + AD_Desktop_ID + " " + Name;
	}

	/**************************************************************************
	 * 	Get AD_Desktop_ID
	 *	@return desktop
	 */
	public int getAD_Desktop_ID()
	{
		return AD_Desktop_ID;
	}
	/**
	 * 	Get Name
	 *	@return name
	 */
	public String getName()
	{
		return Name;
	}
	/**
	 * 	Get Description
	 *	@return description
	 */
	public String getDescription()
	{
		return Description;
	}
	/**
	 * 	Get Help
	 *	@return help
	 */
	public String getHelp()
	{
		return Help;
	}
	/**
	 * 	Get AD_Column_ID
	 *	@return column
	 */
	public int getAD_Column_ID()
	{
		return AD_Column_ID;
	}
	/**
	 * 	Get AD_Image_ID
	 *	@return image
	 */
	public int getAD_Image_ID()
	{
		return AD_Image_ID;
	}
	/**
	 * 	Get AD_Color_ID
	 *	@return color
	 */
	public int getAD_Color_ID()
	{
		return AD_Color_ID;
	}
	/**
	 * 	Get PA_Goal_ID
	 *	@return goal
	 */
	public int getPA_Goal_ID()
	{
		return PA_Goal_ID;
	}

	/*************************************************************************/

	/**
	 *  Init Workbench Windows
	 *  @return true if initilized
	 */
	private boolean initDesktopWorkbenches()
	{
		String sql = "SELECT AD_Workbench_ID "
			+ "FROM AD_DesktopWorkbench "
			+ "WHERE AD_Desktop_ID=? AND IsActive='Y' "
			+ "ORDER BY SeqNo";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Desktop_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				int AD_Workbench_ID = rs.getInt(1);
				m_workbenches.add (new Integer(AD_Workbench_ID));
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "MWorkbench.initDesktopWorkbenches", e);
			return false;
		}
		return true;
	}   //  initDesktopWorkbenches

	/**
	 *  Get Window Count
	 *  @return no of windows
	 */
	public int getWindowCount()
	{
		return m_workbenches.size();
	}   //  getWindowCount

	/**
	 *  Get AD_Workbench_ID of index
	 *  @param index index
	 *  @return -1 if not valid
	 */
	public int getAD_Workbench_ID (int index)
	{
		if (index < 0 || index > m_workbenches.size())
			return -1;
		Integer id = (Integer)m_workbenches.get(index);
		return id.intValue();
	}   //  getAD_Workbench_ID

}   //  MDesktop
