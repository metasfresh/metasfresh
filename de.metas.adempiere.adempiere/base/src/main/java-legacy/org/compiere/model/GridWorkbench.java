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
import java.awt.Image;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.Icon;

import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 *  Window Workbench Model
 *
 *  @author Jorg Janke
 *  @version $Id: GridWorkbench.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class GridWorkbench implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3947016961582548630L;

	/**
	 *  Workbench Model Constructor
	 *  @param ctx context
	 */
	public GridWorkbench (Properties ctx)
	{
		m_ctx = ctx;
	}   //  MWorkbench

	/**
	 *  No Workbench - Just Frame for Window
	 *  @param ctx context
	 *  @param AD_Window_ID window
	 */
	public GridWorkbench (Properties ctx, AdWindowId adWindowId)
	{
		m_ctx = ctx;
		m_windows.add (new WBWindow(TYPE_WINDOW, AdWindowId.toRepoId(adWindowId)));
	}   //  MWorkbench

	/** Properties      */
	private Properties  m_ctx;

	/** List of windows */
	private ArrayList<WBWindow>   m_windows = new ArrayList<>();

	private int         AD_Workbench_ID = 0;
	private String      Name = "";
	private String      Description = "";
	private String      Help = "";
	private int         AD_Column_ID = 0;
	private int         AD_Image_ID = 0;
	private int         AD_Color_ID = 0;
	private int         PA_Goal_ID = 0;
	private String      ColumnName = "";

	/**	Logger			*/
	private static Logger log = LogManager.getLogger(GridWorkbench.class);
	
	/**
	 *  Init Workbench
	 *  @param ad_Workbench_ID workbench
	 *  @return true if initialized
	 */
	public boolean initWorkbench (int ad_Workbench_ID)
	{
		AD_Workbench_ID = ad_Workbench_ID;
		//  Get WB info
		String sql = null;
		if (Env.isBaseLanguage(m_ctx, "AD_Workbench"))
		{
			sql = "SELECT w.Name,w.Description,w.Help,"                         //  1..3
				+ " w.AD_Column_ID,w.AD_Image_ID,w.AD_Color_ID,w.PA_Goal_ID,"   //  4..7
				+ " c.ColumnName "                                              //  8
				+ "FROM AD_Workbench w, AD_Column c "
				+ "WHERE w.AD_Workbench_ID=?"                   //  #1
				+ " AND w.IsActive='Y'"
				+ " AND w.AD_Column_ID=c.AD_Column_ID";
		}
		else
		{
			sql = "SELECT t.Name,t.Description,t.Help,"
				+ " w.AD_Column_ID,w.AD_Image_ID,w.AD_Color_ID,w.PA_Goal_ID,"
				+ " c.ColumnName "
				+ "FROM AD_Workbench w, AD_Workbench_Trl t, AD_Column c "
				+ "WHERE w.AD_Workbench_ID=?"                   //  #1
				+ " AND w.IsActive='Y'"
				+ " AND w.AD_Workbench_ID=t.AD_Workbench_ID"
				+ " AND t.AD_Language='" + Env.getAD_Language(m_ctx) + "'"
				+ " AND w.AD_Column_ID=c.AD_Column_ID";
		}
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Workbench_ID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				Name = rs.getString(1);
				Description = rs.getString(2);
				if (Description == null)
				{
					Description = "";
				}
				Help = rs.getString(3);
				if (Help == null)
				{
					Help = "";
				}
				//
				AD_Column_ID = rs.getInt(4);
				AD_Image_ID = rs.getInt(5);
				AD_Color_ID = rs.getInt(6);
				PA_Goal_ID = rs.getInt(7);
				ColumnName = rs.getString(8);
			}
			else
			{
				AD_Workbench_ID = 0;
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
		}

		if (AD_Workbench_ID == 0)
		{
			return false;
		}
		return initWorkbenchWindows();
	}   //  initWorkbench

	/**
	 *  String Representation
	 *  @return info
	 */
	@Override
	public String toString()
	{
		return "MWorkbench ID=" + AD_Workbench_ID + " " + Name
			+ ", windows=" + m_windows.size() + ", LinkColumn=" + ColumnName;
	}   //  toString

	/**
	 *  Dispose
	 */
	public void dispose()
	{
		for (int i = 0; i < m_windows.size(); i++)
		{
			dispose(i);
		}
		m_windows.clear();
		m_windows = null;
	}   //  dispose

	/**
	 *  Get Workbench Query.
	 *  @return ColumnName=@#ColumnName@
	 */
	public MQuery getQuery()
	{
		return MQuery.getEqualQuery(ColumnName, "@#" + ColumnName + "@");
	}   //  getQuery

	/*************************************************************************/

	/**
	 * 	Get Workbench
	 * 	@return workbensch id
	 */
	public int getAD_Workbench_ID()
	{
		return AD_Workbench_ID;
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
	 * 	Get Link AD_Column_ID
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

	/** Window          */
	public static final int     TYPE_WINDOW = 1;
	/** Form            */
	public static final int     TYPE_FORM = 2;
	/** Process         */
	public static final int     TYPE_PROCESS = 3;
	/** Task            */
	public static final int     TYPE_TASK = 4;

	/**
	 *  Init Workbench Windows
	 *  @return true if init ok
	 */
	private boolean initWorkbenchWindows()
	{
		String sql = "SELECT AD_Window_ID, AD_Form_ID, AD_Process_ID, AD_Task_ID "
			+ "FROM AD_WorkbenchWindow "
			+ "WHERE AD_Workbench_ID=? AND IsActive='Y'"
			+ "ORDER BY SeqNo";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Workbench_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				int AD_Window_ID = rs.getInt(1);
				int AD_Form_ID = rs.getInt(2);
				int AD_Process_ID = rs.getInt(3);
				int AD_Task_ID = rs.getInt(4);
				//
				if (AD_Window_ID > 0)
				{
					m_windows.add (new WBWindow(TYPE_WINDOW, AD_Window_ID));
				}
				else if (AD_Form_ID > 0)
				{
					m_windows.add (new WBWindow(TYPE_FORM, AD_Form_ID));
				}
				else if (AD_Process_ID > 0)
				{
					m_windows.add (new WBWindow(TYPE_PROCESS, AD_Process_ID));
				}
				else if (AD_Task_ID > 0)
				{
					m_windows.add (new WBWindow(TYPE_TASK, AD_Task_ID));
				}
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.error(sql, e);
			return false;
		}
		return true;
	}   //  initWorkbenchWindows

	/**
	 *  Get Window Count
	 *  @return window count
	 */
	public int getWindowCount()
	{
		return m_windows.size();
	}   //  getWindowCount

	/**
	 *  Get Window Type of Window
	 *  @param index index in workbench
	 *  @return -1 if not valid
	 */
	public int getWindowType (int index)
	{
		if (index < 0 || index > m_windows.size())
		{
			return -1;
		}
		WBWindow win = m_windows.get(index);
		return win.Type;
	}   //  getWindowType

	/**
	 *  Get ID for Window
	 *  @param index index in workbench
	 *  @return -1 if not valid
	 */
	public AdWindowId getWindowID (int index)
	{
		if (index < 0 || index > m_windows.size())
		{
			return null;
		}
		WBWindow win = m_windows.get(index);
		return AdWindowId.ofRepoIdOrNull(win.ID);
	}   //  getWindowID

	
	/**************************************************************************
	 *  Set Window Model of Window
	 *  @param index index in workbench
	 *  @param mw model window
	 */
	public void setMWindow (int index, GridWindow mw)
	{
		if (index < 0 || index > m_windows.size())
		{
			throw new IllegalArgumentException ("Index invalid: " + index);
		}
		WBWindow win = m_windows.get(index);
		if (win.Type != TYPE_WINDOW)
		{
			throw new IllegalArgumentException ("Not a MWindow: " + index);
		}
		win.mWindow = mw;
	}   //  setMWindow

	/**
	 *  Get Window Model of Window
	 *  @param index index in workbench
	 *  @return model window
	 */
	public GridWindow getMWindow (int index)
	{
		if (index < 0 || index > m_windows.size())
		{
			throw new IllegalArgumentException ("Index invalid: " + index);
		}
		WBWindow win = m_windows.get(index);
		if (win.Type != TYPE_WINDOW)
		{
			throw new IllegalArgumentException ("Not a MWindow: " + index);
		}
		return win.mWindow;
	}   //  getMWindow

	/**
	 *  Get Name of Window
	 *  @param index index in workbench
	 *  @return Window Name or null if not set
	 */
	public String getName (int index)
	{
		if (index < 0 || index > m_windows.size())
		{
			throw new IllegalArgumentException ("Index invalid: " + index);
		}
		WBWindow win = m_windows.get(index);
		if (win.mWindow != null && win.Type == TYPE_WINDOW)
		{
			return win.mWindow.getName();
		}
		return null;
	}   //  getName

	/**
	 *  Get Description of Window
	 *  @param index index in workbench
	 *  @return Window Description or null if not set
	 */
	public String getDescription (int index)
	{
		if (index < 0 || index > m_windows.size())
		{
			throw new IllegalArgumentException ("Index invalid: " + index);
		}
		WBWindow win = m_windows.get(index);
		if (win.mWindow != null && win.Type == TYPE_WINDOW)
		{
			return win.mWindow.getDescription();
		}
		return null;
	}   //  getDescription

	/**
	 *  Get Help of Window
	 *  @param index index in workbench
	 *  @return Window Help or null if not set
	 */
	public String getHelp (int index)
	{
		if (index < 0 || index > m_windows.size())
		{
			throw new IllegalArgumentException ("Index invalid: " + index);
		}
		WBWindow win = m_windows.get(index);
		if (win.mWindow != null && win.Type == TYPE_WINDOW)
		{
			return win.mWindow.getHelp();
		}
		return null;
	}   //  getHelp

	/**
	 *  Get Icon of Window
	 *  @param index index in workbench
	 *  @return Window Icon or null if not set
	 */
	public Icon getIcon (int index)
	{
		if (index < 0 || index > m_windows.size())
		{
			throw new IllegalArgumentException ("Index invalid: " + index);
		}
		WBWindow win = m_windows.get(index);
		if (win.mWindow != null && win.Type == TYPE_WINDOW)
		{
			return win.mWindow.getIcon();
		}
		return null;
	}   //  getIcon

	/**
	 *  Get Image Icon of Window
	 *  @param index index in workbench
	 *  @return Window Icon or null if not set
	 */
	public Image getImage (int index)
	{
		if (index < 0 || index > m_windows.size())
		{
			throw new IllegalArgumentException ("Index invalid: " + index);
		}
		WBWindow win = m_windows.get(index);
		if (win.mWindow != null && win.Type == TYPE_WINDOW)
		{
			return win.mWindow.getImage();
		}
		return null;
	}   //  getImage

	/**
	 *  Get AD_Color_ID of Window
	 *  @param index index in workbench
	 *  @return Window Color or Workbench color if not set
	 */
	public int getAD_Color_ID (int index)
	{
		if (index < 0 || index > m_windows.size())
		{
			throw new IllegalArgumentException ("Index invalid: " + index);
		}
		WBWindow win = m_windows.get(index);
		int retValue = -1;
	//	if (win.mWindow != null && win.Type == TYPE_WINDOW)
	//		return win.mWindow.getAD_Color_ID();
		if (retValue == -1)
		{
			return getAD_Color_ID();
		}
		return retValue;
	}   //  getAD_Color_ID

	/**
	 *  Set WindowNo of Window
	 *  @param index index in workbench
	 *  @param windowNo window no
	 */
	public void setWindowNo (int index, int windowNo)
	{
		if (index < 0 || index > m_windows.size())
		{
			throw new IllegalArgumentException ("Index invalid: " + index);
		}
		WBWindow win = m_windows.get(index);
		win.WindowNo = windowNo;
	}   //  getWindowNo

	/**
	 *  Get WindowNo of Window
	 *  @param index index in workbench
	 *  @return WindowNo of Window if previously set, otherwise -1;
	 */
	public int getWindowNo (int index)
	{
		if (index < 0 || index > m_windows.size())
		{
			throw new IllegalArgumentException ("Index invalid: " + index);
		}
		WBWindow win = m_windows.get(index);
		return win.WindowNo;
	}   //  getWindowNo

	/**
	 *  Dispose of Window
	 *  @param index index in workbench
	 */
	public void dispose (int index)
	{
		if (index < 0 || index > m_windows.size())
		{
			throw new IllegalArgumentException ("Index invalid: " + index);
		}
		WBWindow win = m_windows.get(index);
		if (win.mWindow != null)
		{
			win.mWindow.dispose();
		}
		win.mWindow = null;
	}   //  dispose

	/**
	 * 	Get Window Size
	 *	@return window size or null if not set
	 */
	public Dimension getWindowSize()
	{
		return null;
	}	//	getWindowSize
	
	
	/**************************************************************************
	 *  Window Type
	 */
	class WBWindow
	{
		/**
		 * 	WBWindow
		 *	@param type
		 *	@param id
		 */
		public WBWindow (int type, int id)
		{
			Type = type;
			ID = id;
		}
		/** Type			*/
		public int      Type = 0;
		/** ID				*/
		public int      ID = 0;
		/** Window No		*/
		public int      WindowNo = -1;
		/** Window Model	*/
		public GridWindow  mWindow = null;
	//	public MFrame   mFrame = null;
	//	public MProcess mProcess = null;
	}   //  WBWindow

// metas: begin
	public GridWindow getMWindowById(AdWindowId adWindowId)
	{
		if (adWindowId == null)
		{
			return null;
		}
		for (WBWindow win : m_windows)
		{
			if (win.Type == TYPE_WINDOW && AdWindowId.equals(adWindowId, win.mWindow.getAdWindowId()))
			{
				return win.mWindow;
			}
			
		}
		return null;
	}
// metas: end
}   //  Workbench
