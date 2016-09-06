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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import org.compiere.util.Language;
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
	 *  @return {@link GridWindowVO}; never returns null
	 *  @see #create(Properties, int, int, int)
	 */
	public static GridWindowVO create (Properties ctx, int WindowNo, int AD_Window_ID)
	{
		final int adMenuId = 0; // N/A
		final boolean loadAllLanguages = false;
		return create (ctx, WindowNo, AD_Window_ID, adMenuId, loadAllLanguages);
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
	public static GridWindowVO create (final Properties ctx, final int WindowNo, final int AD_Window_ID, final int AD_Menu_ID, final boolean loadAllLanguages)
	{
		logger.debug("WindowNo={} - AD_Window_ID={}; AD_Menu_ID={}", WindowNo, AD_Window_ID, AD_Menu_ID);
		final GridWindowVO vo = new GridWindowVO (ctx, WindowNo, loadAllLanguages);
		vo.AD_Window_ID = AD_Window_ID;

		//
		//  Get AD_Window_ID if required - (used by HTML UI)
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
		final String sql = buildSql(vo.getCtx(), vo.getAD_Window_ID(), loadAllLanguages, sqlParams);
		String windowName = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			//	create statement
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);

			// 	get data
			rs = pstmt.executeQuery();
			boolean firstRow = true;
			boolean loaded = false;
			while (rs.next())
			{
				windowName = rs.getString("Name");
				vo.name = windowName;
				vo.description = Util.coalesce(rs.getString("Description"), "");
				vo.help = Util.coalesce(rs.getString("Help"), "");
				
				if(loadAllLanguages)
				{
					final String adLanguage = rs.getString("AD_Language");
					if(firstRow)
					{
						vo.setName(adLanguage, vo.name);
						vo.setDescription(adLanguage, vo.description);
						vo.setHelp(adLanguage, vo.help);
						
						final String baseAD_Language = Language.getBaseAD_Language();
						vo.setName(baseAD_Language, rs.getString("Name_BaseLang"));
						vo.setDescription(baseAD_Language, rs.getString("Description_BaseLang"));
						vo.setHelp(baseAD_Language, rs.getString("Help_BaseLang"));
					}
					else
					{
						vo.setName(adLanguage, rs.getString("Name"));
						vo.setDescription(adLanguage, rs.getString("Description"));
						vo.setHelp(adLanguage, rs.getString("Help"));
					}
				}
				
				vo.WindowType = rs.getString("WindowType");
				//
				vo.AD_Color_ID = rs.getInt("AD_Color_ID");
				vo.AD_Image_ID = rs.getInt("AD_Image_ID");
				// vo.IsReadWrite = rs.getString(7); // metas: not needed
				//
				vo.WinHeight = rs.getInt("WinHeight");
				vo.WinWidth = rs.getInt("WinWidth");
				//
				vo.setIsSOTrx(DisplayType.toBoolean(rs.getString("IsSOTrx")));
				vo.IsOneInstanceOnly = DisplayType.toBoolean(rs.getString("IsOneInstanceOnly")); // metas: US831
				
				firstRow = false;
				loaded = true;
			}
			
			if(!loaded)
			{
				throw new WindowLoadException("@NotFound@", "ID=" + Env.getAD_Role_ID(ctx) + " (does not matter)", windowName, AD_Window_ID);
			}
		}
		catch (SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
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
	
	private static final String buildSql(final Properties ctx, final int AD_Window_ID, final boolean loadAllLanguages, final List<Object> sqlParams)
	{
		//
		final boolean useTrl;
		final boolean filterByLanguage;
		if (loadAllLanguages)
		{
			useTrl = true;
			filterByLanguage = false;
		}
		else if (!Env.isBaseLanguage(ctx, I_AD_Window.Table_Name))
		{
			useTrl= true;
			filterByLanguage = true;
		}
		else
		{
			useTrl = false;
			filterByLanguage = false;
		}

		final StringBuilder sql;
		if (!useTrl)
		{
			sql = new StringBuilder("SELECT "
					+ " w.Name"
					+ ", w.Description"
					+ ", w.Help"
					+ ", w.WindowType"
					+ ", w.AD_Color_ID, w.AD_Image_ID, w.WinHeight, w.WinWidth "
					+ ", IsSOTrx "
					+ ", IsOneInstanceOnly " // 10 // metas: US831
					);
			sql.append(" FROM AD_Window w WHERE w.AD_Window_ID=? AND w.IsActive=?");
			sqlParams.add(AD_Window_ID);
			sqlParams.add(true); // IsActive
		}
		else
		{
			sql = new StringBuilder("SELECT "
					+ " trl.AD_Language"
					+ ", trl.Name"
					+ ", w.Name as Name_BaseLang"
					+ ", trl.Description"
					+ ", w.Description as Description_BaseLang"
					+ ", trl.Help"
					+ ", w.Help as Help_BaseLang"
					+ ", w.WindowType"
					+ ", w.AD_Color_ID, w.AD_Image_ID, w.WinHeight, w.WinWidth "
					+ ", w.IsSOTrx "
					+ ", w.IsOneInstanceOnly " // 10 // metas: US831
					);
			sql.append(" FROM AD_Window w INNER JOIN AD_Window_Trl trl ON (trl.AD_Window_ID=w.AD_Window_ID)");
			sql.append(" WHERE w.AD_Window_ID=? AND w.IsActive=?");
			sqlParams.add(AD_Window_ID);
			sqlParams.add(true); // IsActive
			
			if(filterByLanguage)
			{
				sql.append(" AND trl.AD_Language=?");
				sqlParams.add(Env.getAD_Language(ctx));
			}
		}
		
		return sql.toString();
	}

	/**
	 *  Create Window Tabs
	 *  @param mWindowVO Window Value Object
	 *  @return true if tabs were created
	 */
	private static List<GridTabVO> createTabs (final GridWindowVO mWindowVO)
	{
		final int adWindowId = mWindowVO.getAD_Window_ID();
		final String windowType = mWindowVO.getWindowType();
		final boolean isReadonly = WINDOWTYPE_QUERY.equals(windowType);
		final boolean onlyCurrentRows = WINDOWTYPE_TRX.equals(windowType);
		final boolean isWindowReadonly = !mWindowVO.isReadWrite();

		final List<Object> sqlParams = new ArrayList<>();
		final String sql = GridTabVO.getSQL(mWindowVO.getCtx(), adWindowId, mWindowVO.isLoadAllLanguages(), sqlParams);
		int TabNo = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			//	create statement
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			pstmt.setInt(1, adWindowId);
			rs = pstmt.executeQuery();
			
			final LinkedHashMap<Integer, GridTabVO> tabs = new LinkedHashMap<>();
			boolean firstTab = true;
			while (rs.next())
			{
				final int AD_Tab_ID = rs.getInt("AD_Tab_ID");
				
				GridTabVO mTabVO = tabs.get(AD_Tab_ID);
				if(mTabVO == null)
				{
					//  Create TabVO
					mTabVO = GridTabVO.create(mWindowVO, TabNo, rs, isReadonly, onlyCurrentRows);
					if (mTabVO == null)
					{
						if(firstTab)
						{
							logger.warn("First tab could not be created for {}. Skip creating the window", mWindowVO);
							break; // don't continue if first tab is null
						}
						else
						{
							continue;
						}
					}
					
					if (!mTabVO.isReadOnly() && isWindowReadonly)
					{
						mTabVO.setReadOnly(true);
					}
					tabs.put(AD_Tab_ID, mTabVO);
					TabNo++; //  must be same as mWindow.getTab(x)
					firstTab = false;
				}
				else
				{
					mTabVO.loadAdditionalLanguage(rs);
				}
			}
			
			//  No Tabs
			if (TabNo == 0 || tabs.isEmpty())
			{
				mWindowVO.addLoadErrorMessage("No Tabs - AD_Window_ID=" + adWindowId + " - " + sql, true); // metas: 1934
				logger.error("No Tabs - AD_Window_ID={} - {}", adWindowId, sql);
				return null;
			}

			return ImmutableList.copyOf(tabs.values());
		}
		catch (SQLException e)
		{
			final DBException dbEx = new DBException(e, sql, sqlParams);
			logger.error("Failed creating the tabs", dbEx);
			return null;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}   //  createTabs

	/**************************************************************************
	 *  Private Constructor
	 *  @param Ctx context
	 *  @param windowNo window no
	 * @param loadAllLanguages 
	 */
	private GridWindowVO (final Properties Ctx, final int windowNo, final boolean loadAllLanguages)
	{
		super();
		ctx = Ctx;
		WindowNo = windowNo;
		this.loadAllLanguages = loadAllLanguages;
	}

	static final long serialVersionUID = 3802628212531678981L;

	/** Properties      */
	private Properties   ctx;
	/** Window Number	*/
	private int WindowNo;

	/** Window				*/
	private int AD_Window_ID = 0;
	private final boolean loadAllLanguages;
	/** Name				*/
	private String name = "";
	private Map<String, String> nameTrls = null;
	/** Desription			*/
	private String description = "";
	private Map<String, String> descriptionTrls = null;
	/** Help				*/
	private String help = "";
	private Map<String, String> helpTrls = null;
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
			final GridWindowVO  clone = new GridWindowVO(ctx, windowNo, loadAllLanguages);
			clone.AD_Window_ID = AD_Window_ID;
			clone.name = name;
			clone.description = description;
			clone.help = help;
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
		return name;
	}

	public Map<String, String> getNameTrls()
	{
		return nameTrls;
	}
	
	private void setName(final String adLanguage, final String nameTrl)
	{
		Check.assumeNotEmpty(adLanguage, "adLanguage is not empty");
		
		if(nameTrl == null)
		{
			return;
		}
		
		if(nameTrls == null)
		{
			nameTrls = new HashMap<>();
		}
		nameTrls.put(adLanguage, nameTrl);
	}

	public String getDescription()
	{
		return description;
	}

	public Map<String, String> getDescriptionTrls()
	{
		return descriptionTrls;
	}

	private void setDescription(final String adLanguage, final String descriptionTrl)
	{
		Check.assumeNotEmpty(adLanguage, "adLanguage is not empty");
		
		if(descriptionTrl == null)
		{
			return;
		}
		
		if(descriptionTrls == null)
		{
			descriptionTrls = new HashMap<>();
		}
		descriptionTrls.put(adLanguage, descriptionTrl);
	}

	public String getHelp()
	{
		return help;
	}

	public Map<String, String> getHelpTrls()
	{
		return helpTrls;
	}
	
	private void setHelp(final String adLanguage, final String helpTrl)
	{
		Check.assumeNotEmpty(adLanguage, "adLanguage is not empty");
		
		if(helpTrl == null)
		{
			return;
		}
		
		if(helpTrls == null)
		{
			helpTrls = new HashMap<>();
		}
		helpTrls.put(adLanguage, helpTrl);
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
	
	public boolean isLoadAllLanguages()
	{
		return loadAllLanguages;
	}
}

