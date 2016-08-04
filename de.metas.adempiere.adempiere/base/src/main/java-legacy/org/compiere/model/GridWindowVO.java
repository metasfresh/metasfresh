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

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.WindowLoadException;
import org.adempiere.service.IRolePermLoggingBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;

/**
 *  Model Window Value Object
 *
 *  @author Jorg Janke
 *  @version  $Id: GridWindowVO.java,v 1.4 2006/07/30 00:58:04 jjanke Exp $
 */
public class GridWindowVO implements Serializable
{
	public static final String CTXNAME_BaseTable_ID = "BaseTable_ID";

	private static final transient Logger logger = LogManager.getLogger(GridWindowVO.class);

	/**
	 *  Create Window Value Object
	 *  @param ctx context
	 *  @param WindowNo window no for ctx
	 *  @param AD_Window_ID window id
	 *  @return MWindowVO
	 */
	public static GridWindowVO create (Properties ctx, int WindowNo, int AD_Window_ID)
	{
		final int adMenuId = 0; // N/A
		return create (ctx, WindowNo, AD_Window_ID, adMenuId);
	}   //  create

	/**
	 * Load {@link GridWindowVO}.
	 *
	 * @param ctx
	 * @param WindowNo
	 * @param AD_Window_ID
	 * @param AD_Menu_ID
	 * @return loaded {@link GridWindowVO}; never returns <code>null</code>
	 * @throws WindowLoadException if window was not found or if there is no access for current logged in role.
	 */
	public static GridWindowVO create (final Properties ctx, final int WindowNo, final int AD_Window_ID, final int AD_Menu_ID)
	{
		logger.info("WindowNo={} - AD_Window_ID={}; AD_Menu_ID={}", WindowNo, AD_Window_ID, AD_Menu_ID);
		GridWindowVO vo = new GridWindowVO (ctx, WindowNo);
		vo.AD_Window_ID = AD_Window_ID;

		//  Get Window_ID if required	- (used by HTML UI)
		if (vo.AD_Window_ID <= 0 && AD_Menu_ID > 0)
		{
			final String sql = "SELECT AD_Window_ID, IsSOTrx, IsReadOnly FROM AD_Menu WHERE AD_Menu_ID=? AND Action='W'";
			final List<Object> sqlParams = Arrays.<Object>asList(AD_Menu_ID);
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
				DB.setParameters(pstmt, sqlParams);
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					vo.AD_Window_ID = rs.getInt(1);
					final boolean isSOTrx = DisplayType.toBoolean(rs.getString(2));
					vo.setIsSOTrx(isSOTrx);
					//
					final boolean isReadOnly = DisplayType.toBoolean(rs.getString(3));
					vo.setReadWrite(!isReadOnly);
				}
			}
			catch (SQLException e)
			{
				throw new DBException(e, sql, sqlParams);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null; pstmt = null;
			}
			//
			if (vo.getAD_Window_ID() <= 0)
			{
				throw new WindowLoadException("@NotFound@ @AD_Menu_ID@="+AD_Menu_ID, "-", "-", vo.getAD_Window_ID());
			}
			
			logger.debug("AD_Window_ID={}", vo.getAD_Window_ID());
		}

		//
		//  --  Get Window
		final List<Object> sqlParams = new ArrayList<>();
		final StringBuilder sql = new StringBuilder("SELECT Name,Description,Help,WindowType, "
			+ "AD_Color_ID,AD_Image_ID,WinHeight,WinWidth, " // metas: removed a.IsReadWrite
			+ "IsSOTrx "
			+ ", IsOneInstanceOnly " // 10 // metas: US831
			);
		if (Env.isBaseLanguage(vo.getCtx(), I_AD_Window.Table_Name))
		{
			sql.append("FROM AD_Window w WHERE w.AD_Window_ID=? AND w.IsActive=?"); // metas: tsa: changed the query because we check for permissions later
			sqlParams.add(vo.getAD_Window_ID());
			sqlParams.add(true); // IsActive
		}
		else
		{
			sql.append("FROM AD_Window_vt w WHERE w.AD_Window_ID=?")  // metas: tsa: changed the query because we check for permissions later
				.append(" AND w.IsActive=?")
				.append(" AND w.AD_Language=?");
			sqlParams.add(vo.getAD_Window_ID());
			sqlParams.add(true); // IsActive
			sqlParams.add(Env.getAD_Language(vo.getCtx()));
		}
		
		String windowName = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			//	create statement
			pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);

			// 	get data
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				windowName = rs.getString(1);
				vo.Name = windowName;
				vo.Description = Util.coalesce(rs.getString(2), "");
				vo.Help = Util.coalesce(rs.getString(3), "");
				vo.WindowType = rs.getString(4);
				//
				vo.AD_Color_ID = rs.getInt(5);
				vo.AD_Image_ID = rs.getInt(6);
				// vo.IsReadWrite = rs.getString(7); // metas: not needed
				//
				vo.WinHeight = rs.getInt(7);
				vo.WinWidth = rs.getInt(8);
				//
				final boolean isSOTrx = DisplayType.toBoolean(rs.getString(9));
				vo.setIsSOTrx(isSOTrx);
				vo.IsOneInstanceOnly = DisplayType.toBoolean(rs.getString(10)); // metas: US831
			}
			else
			{
				throw new WindowLoadException("@NotFound@", "ID=" + Env.getAD_Role_ID(ctx) + " (does not matter)", windowName, AD_Window_ID);
			}
		}
		catch (SQLException ex)
		{
			throw new DBException(ex, sql.toString(), sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		//
		// Check for permissions using Role API
		if (vo != null)
		{
			final IUserRolePermissions role = Env.getUserRolePermissions(ctx);
			final Boolean windowAccess = role.checkWindowAccess(vo.getAD_Window_ID());
			
			// no access 
			if (windowAccess == null)
			{
				final WindowLoadException ex = new WindowLoadException("@NoAccess@", role.getName(), windowName, AD_Window_ID);
				Services.get(IRolePermLoggingBL.class).logWindowAccess(role.getAD_Role_ID(), AD_Window_ID, null, ex.getLocalizedMessage());
				throw ex;
			}
			// read-only access
			else if (Boolean.FALSE.equals(windowAccess))
			{
				vo.setReadWrite(false);
			}
			// read-write access
			else
			{
				vo.setReadWrite(true);
			}
		}

		//
		// Apply UserDef settings
		MUserDefWin.apply(vo);

		//  Create Tabs
		final List<GridTabVO> tabs = createTabs(vo);
		if (tabs == null || tabs.isEmpty())
		{
			final String loadErrorMessage = vo.getLoadErrorMessage() == null ? "Window tabs load error" : vo.getLoadErrorMessage();
			final String roleName = "-";
			final WindowLoadException ex = new WindowLoadException(loadErrorMessage, roleName, windowName, AD_Window_ID);
			throw ex;
		}
		vo._tabs = tabs;
		vo._BaseTable_ID = tabs.get(0).getAD_Table_ID();

		return vo;
	}   //  create

	/**
	 *  Create Window Tabs
	 *  @param mWindowVO Window Value Object
	 *  @return true if tabs were created
	 */
	private static List<GridTabVO> createTabs (final GridWindowVO mWindowVO)
	{
		final int adWindowId = mWindowVO.getAD_Window_ID();
		final String windowType = mWindowVO.getWindowType();
		
		final List<GridTabVO> tabs = new ArrayList<GridTabVO>();

		final String sql = GridTabVO.getSQL(mWindowVO.ctx);
		int TabNo = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			//	create statement
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt(1, adWindowId);
			rs = pstmt.executeQuery();
			boolean firstTab = true;
			while (rs.next())
			{
				//  Create TabVO
				final GridTabVO mTabVO = GridTabVO.create(mWindowVO, TabNo, rs,
					windowType.equals(WINDOWTYPE_QUERY),  //  isRO
					windowType.equals(WINDOWTYPE_TRX));   //  onlyCurrentRows
				
				if (mTabVO == null && firstTab)
				{
					break;		//	don't continue if first tab is null
				}
				if (mTabVO != null)
				{
					if (!mTabVO.isReadOnly() && !mWindowVO.isReadWrite())
					{
						mTabVO.setReadOnly(true);
					}
					tabs.add(mTabVO);
					TabNo++;        //  must be same as mWindow.getTab(x)
					firstTab = false;
				}
			}
		}
		catch (SQLException e)
		{
			logger.error("createTabs", e);
			return null;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		//  No Tabs
		if (TabNo == 0 || tabs.isEmpty())
		{
			mWindowVO.addLoadErrorMessage("No Tabs - AD_Window_ID=" + adWindowId + " - " + sql, true); // metas: 1934
			logger.error("No Tabs - AD_Window_ID=" + adWindowId + " - " + sql);
			return null;
		}

		return ImmutableList.copyOf(tabs);
	}   //  createTabs


	/**************************************************************************
	 *  Private Constructor
	 *  @param Ctx context
	 *  @param windowNo window no
	 */
	private GridWindowVO (final Properties Ctx, final int windowNo)
	{
		super();
		ctx = Ctx;
		WindowNo = windowNo;
	}

	static final long serialVersionUID = 3802628212531678981L;

	/** Properties      */
	private Properties   ctx;
	/** Window Number	*/
	private int WindowNo;

	/** Window				*/
	private int AD_Window_ID = 0;
	/** Name				*/
	private String Name = "";
	/** Desription			*/
	private String Description = "";
	/** Help				*/
	private String Help = "";
	/** Window Type			*/
	private String WindowType = "";
	/** Image				*/
	private int AD_Image_ID = 0;
	/** Color				*/
	private int AD_Color_ID = 0;
	/** Read Write			*/
	private Boolean _isReadWrite = null;
	/** Window Width		*/
	private int WinWidth = 0;
	/** Window Height		*/
	private int WinHeight = 0;
	/** Sales Order Trx		*/
	private boolean _isSOTrx = false;

	/** Tabs contains GridTabVO elements   */
	private List<GridTabVO> _tabs = null;
	/** Base Table (AD_Table_ID) */
	private int _BaseTable_ID = 0;

	/** Qyery				*/
	public static final String	WINDOWTYPE_QUERY = "Q";
	/** Transaction			*/
	public static final String	WINDOWTYPE_TRX = "T";
	/** Maintenance			*/
	public static final String	WINDOWTYPE_MMAINTAIN = "M";

	/**
	 *  Set Context including contained elements
	 *  @param newCtx context
	 */
	public void setCtx (final Properties newCtx)
	{
		ctx = newCtx;
		final List<GridTabVO> tabs = getTabs();
		if(tabs != null)
		{
			for (GridTabVO tab : tabs)
			{
				tab.setCtx(newCtx);
			}
		}
	}   //  setCtx

	/**
	 * Clone
	 * 
	 * @param windowNo no
	 * @return cloned VO or <code>null</code>
	 */
	public GridWindowVO clone (final int windowNo)
	{
		try
		{
			final GridWindowVO  clone = new GridWindowVO(ctx, windowNo);
			clone.AD_Window_ID = AD_Window_ID;
			clone.Name = Name;
			clone.Description = Description;
			clone.Help = Help;
			clone.WindowType = WindowType;
			clone.AD_Image_ID = AD_Image_ID;
			clone.AD_Color_ID = AD_Color_ID;
			clone._isReadWrite = _isReadWrite;
			clone.WinWidth = WinWidth;
			clone.WinHeight = WinHeight;
			clone._isSOTrx = _isSOTrx;
			clone.IsOneInstanceOnly = this.IsOneInstanceOnly;
			
			//
			// Tabs
			clone._BaseTable_ID = _BaseTable_ID;
			//
			final List<GridTabVO> tabs = getTabs();
			if (tabs != null)
			{
				final List<GridTabVO> tabsClone = new ArrayList<GridTabVO>();
				for (GridTabVO tab : tabs)
				{
					final GridTabVO cloneTab = tab.clone(clone.getCtx(), windowNo);
					if (cloneTab == null)
						return null;
					tabsClone.add(cloneTab);
				}
				
				clone._tabs = ImmutableList.copyOf(tabsClone);
			}
			
			clone.updateContext();
			
			return clone;
		}
		catch (Exception e)
		{
			logger.warn("Failed cloning {}. Returning null.", this, e);
			return null;
		}
	}
	
	private void updateContext()
	{
		final Properties ctx = getCtx();
		final int windowNo = getWindowNo();
		Env.setContext(ctx, windowNo, Env.CTXNAME_IsSOTrx, isSOTrx());
		
		//	Put base table of window in ctx (for VDocAction)
		Env.setContext(ctx, windowNo, CTXNAME_BaseTable_ID, getBaseTable_ID());
	}

// metas: begin
	private boolean IsOneInstanceOnly = false; // metas: US831
	public boolean isOneInstanceOnly() // metas: US831
	{
		return IsOneInstanceOnly;
	}

	private StringBuffer loadErrorMessages = null;
	protected void addLoadErrorMessage(String message, boolean checkEmpty)
	{
		if (Check.isEmpty(message, true))
			return;
		if (loadErrorMessages == null)
			loadErrorMessages = new StringBuffer();
		if (loadErrorMessages.length() > 0)
		{
			// Don't add this message
			if (checkEmpty)
				return;

			loadErrorMessages.append("\n");
		}
		loadErrorMessages.append(message);
	}
	
	private String getLoadErrorMessage()
	{
		return loadErrorMessages == null || loadErrorMessages.length() == 0 ? null : loadErrorMessages.toString();
	}
	
	public int getAD_Window_ID()
	{
		return AD_Window_ID;
	}
	
	public int getWindowNo()
	{
		return WindowNo;
	}

	public Properties getCtx()
	{
		return ctx;
	}

	public List<GridTabVO> getTabs()
	{
		return _tabs;
	}
	
	public GridTabVO getTab(final int tabNo)
	{
		return _tabs.get(tabNo);
	}
	
	/**
	 * Gets direct children of given tab.
	 * 
	 * @param tabNo
	 * @return list of direct children
	 */
	public List<GridTabVO> getChildTabs(final int tabNo)
	{
		final GridTabVO masterTab = _tabs.get(tabNo);
		final int masterTabLevel = masterTab.getTabLevel();
		final int childTabLevelExpected = masterTabLevel + 1;
		
		final int tabsCount = _tabs.size();
		final List<GridTabVO> childTabs = new ArrayList<>();
		for (int childTabNo = tabNo + 1; childTabNo < tabsCount; childTabNo++)
		{
			final GridTabVO childTab = _tabs.get(childTabNo);
			final int childTabLevel = childTab.getTabLevel();
			
			if(childTabLevel == masterTabLevel)
			{
				// we just moved to another master tab. Stop here.
				break;
			}
			else if (childTabLevel == childTabLevelExpected)
			{
				// we found a child tab. Collect it.
				childTabs.add(childTab);
			}
			else // childTabLevel > childTabLevelExpected
			{
				// we found a child of a child tab. Ignore it.
				continue;
			}
		}
		
		return childTabs;
	}
	
	public String getName()
	{
		return Name;
	}
	
	public String getDescription()
	{
		return Description;
	}
	
	public String getHelp()
	{
		return Help;
	}
	
	void setReadWrite(final boolean readWrite)
	{
		this._isReadWrite = readWrite;
	}
	
	public boolean isReadWrite()
	{
		return Boolean.TRUE.equals(_isReadWrite);
	}
	
	private void setIsSOTrx(final boolean isSOTrx)
	{
		this._isSOTrx = isSOTrx;
	}
	
	public boolean isSOTrx()
	{
		return _isSOTrx;
	}
	
	public int getAD_Color_ID()
	{
		return AD_Color_ID;
	}
	
	public int getAD_Image_ID()
	{
		return AD_Image_ID;
	}
	
	public int getWinWidth()
	{
		return WinWidth;
	}
	
	public int getWinHeight()
	{
		return WinHeight;
	}
	
	public String getWindowType()
	{
		return WindowType;
	}
	
	private int getBaseTable_ID()
	{
		return _BaseTable_ID;
	}
	
	private void setBaseTable_ID(final int adTableId)
	{
		this._BaseTable_ID = adTableId;
	}
}   //  MWindowVO

