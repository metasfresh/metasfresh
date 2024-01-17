/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
package org.compiere.model;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.WindowLoadException;
import org.adempiere.service.IRolePermLoggingBL;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

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

/**
 * Model Window Value Object
 *
 * @author Jorg Janke
 * @version $Id: GridWindowVO.java,v 1.4 2006/07/30 00:58:04 jjanke Exp $
 */
public class GridWindowVO implements Serializable
{
	private static final long serialVersionUID = -7888502229983659459L;

	public static final String CTXNAME_BaseTable_ID = "BaseTable_ID";

	private static final transient Logger logger = LogManager.getLogger(GridWindowVO.class);

	/**
	 * Create Window Value Object
	 *
	 * @param ctx        context
	 * @param windowNo   window no for ctx
	 * @param adWindowId window id
	 * @return {@link GridWindowVO}; never returns null
	 */
	public static GridWindowVO create(final Properties ctx, final int windowNo, final AdWindowId adWindowId)
	{
		return builder()
				.ctx(ctx)
				.windowNo(windowNo)
				.adWindowId(adWindowId)
				.adMenuId(-1) // N/A
				.loadAllLanguages(false) // no
				.applyRolePermissions(true)
				.build();
	}   //  create

	/**
	 * Load {@link GridWindowVO}.
	 *
	 * @param ctx
	 * @param windowNo
	 * @param adWindowId
	 * @param adMenuId
	 * @return loaded {@link GridWindowVO}; never returns <code>null</code>
	 * @throws WindowLoadException if window was not found or if there is no access for current logged in role.
	 */
	@Builder
	public static GridWindowVO create( //
									   @NonNull final Properties ctx //
			, final int windowNo //
			, final AdWindowId adWindowId //
			, final int adMenuId //
			, final boolean loadAllLanguages //
			, final boolean applyRolePermissions //
	)
	{
		logger.debug("WindowNo={} - AD_Window_ID={}; AD_Menu_ID={}", windowNo, adWindowId, adMenuId);

		//
		//  If AD_Window_ID not provided, try fetching it from AD_Menu_ID (used by HTML UI)
		// TODO: not sure if this is still used
		AdWindowId adWindowIdEffective = adWindowId;
		Boolean isSOTrxOverride = null;
		Boolean readonlyOverride = null;
		if (adWindowIdEffective == null && adMenuId > 0)
		{
			final String sql = "SELECT AD_Window_ID, IsSOTrx, IsReadOnly FROM AD_Menu WHERE AD_Menu_ID=? AND Action='W'";
			final List<Object> sqlParams = Arrays.<Object>asList(adMenuId);
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
				DB.setParameters(pstmt, sqlParams);
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					adWindowIdEffective = AdWindowId.ofRepoIdOrNull(rs.getInt(1));
					isSOTrxOverride = DisplayType.toBoolean(rs.getString(2));
					readonlyOverride = DisplayType.toBoolean(rs.getString(3));
				}
			}
			catch (SQLException e)
			{
				throw new DBException(e, sql, sqlParams);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
			//
			if (adWindowIdEffective == null)
			{
				throw new WindowLoadException("@NotFound@ @AD_Menu_ID@=" + adMenuId, "-", "-", adWindowIdEffective);
			}

			logger.debug("AD_Window_ID={}", adWindowIdEffective);
		}

		final GridWindowVO vo = new GridWindowVO(ctx, windowNo, adWindowIdEffective, loadAllLanguages, applyRolePermissions);
		if (isSOTrxOverride != null)
		{
			vo.setIsSOTrx(isSOTrxOverride);
		}
		if (readonlyOverride != null)
		{
			vo.setReadWrite(!readonlyOverride);
		}

		//
		//  --  Get Window
		final List<Object> sqlParams = new ArrayList<>();
		final String sql = buildSql(vo.getCtx(), vo.getAdWindowId(), loadAllLanguages, sqlParams);
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
				if (firstRow)
				{
					windowName = rs.getString("Name");
					vo.name = windowName;
					vo.description = CoalesceUtil.coalesce(rs.getString("Description"), "");
					vo.help = CoalesceUtil.coalesce(rs.getString("Help"), "");
				}

				if (loadAllLanguages)
				{
					final String adLanguage = rs.getString("AD_Language");
					if (firstRow)
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

				if (firstRow)
				{
					vo.WindowType = rs.getString("WindowType");
					//
					vo.AD_Color_ID = rs.getInt("AD_Color_ID");
					vo.AD_Image_ID = rs.getInt("AD_Image_ID");
					// vo.IsReadWrite = rs.getString(7); // metas: not needed
					//
					vo.WinHeight = rs.getInt("WinHeight");
					vo.WinWidth = rs.getInt("WinWidth");
					//
					vo.IsOneInstanceOnly = DisplayType.toBoolean(rs.getString("IsOneInstanceOnly")); // metas: US831

					final boolean isSOTrx = isSOTrxOverride != null ? isSOTrxOverride : DisplayType.toBoolean(rs.getString("IsSOTrx"));
					vo.setIsSOTrx(isSOTrx);

					final boolean readWrite = readonlyOverride != null ? !readonlyOverride : !X_AD_Window.WINDOWTYPE_QueryOnly.equals(vo.WindowType);
					vo.setReadWrite(readWrite);
				}

				firstRow = false;
				loaded = true;
			}

			if (!loaded)
			{
				throw new WindowLoadException("@NotFound@", "ID=" + Env.getAD_Role_ID(ctx) + " (does not matter)", windowName, adWindowId);
			}
		}
		catch (SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		//
		// Check for permissions using Role API
		if (vo != null && applyRolePermissions)
		{
			final IUserRolePermissions role = Env.getUserRolePermissions(ctx);
			final Boolean windowAccess = role.checkWindowPermission(vo.getAdWindowId())
					.getReadWriteBoolean();

			// no access 
			if (windowAccess == null)
			{
				final WindowLoadException ex = new WindowLoadException("@NoAccess@", role.getName(), windowName, adWindowId);
				Services.get(IRolePermLoggingBL.class).logWindowAccess(role.getRoleId(), adWindowId, null, ex.getLocalizedMessage());
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
		//		if (tabs == null || tabs.isEmpty())
		//		{
		//			final String loadErrorMessage = vo.getLoadErrorMessage() == null ? "Window tabs load error" : vo.getLoadErrorMessage();
		//			final String roleName = "-";
		//			final WindowLoadException ex = new WindowLoadException(loadErrorMessage, roleName, windowName, adWindowId);
		//			throw ex;
		//		}
		vo._tabs = tabs;
		vo._BaseTable_ID = tabs.get(0).getAD_Table_ID();

		return vo;
	}   //  create

	private static final String buildSql(final Properties ctx, final AdWindowId adWindowId, final boolean loadAllLanguages, final List<Object> sqlParams)
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
			useTrl = true;
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
			sqlParams.add(adWindowId);
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
			sqlParams.add(adWindowId);
			sqlParams.add(true); // IsActive

			if (filterByLanguage)
			{
				sql.append(" AND trl.AD_Language=?");
				sqlParams.add(Env.getAD_Language(ctx));
			}
		}

		return sql.toString();
	}

	/**
	 * Create Window Tabs
	 *
	 * @param mWindowVO Window Value Object
	 * @return created tabs; never returns null or empty
	 */
	private static List<GridTabVO> createTabs(final GridWindowVO mWindowVO)
	{
		final AdWindowId adWindowId = mWindowVO.getAdWindowId();
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
			rs = pstmt.executeQuery();

			final LinkedHashMap<Integer, GridTabVO> tabsByAD_Tab_ID = new LinkedHashMap<>();
			boolean firstTab = true;
			while (rs.next())
			{
				final int AD_Tab_ID = rs.getInt("AD_Tab_ID");

				GridTabVO mTabVO = tabsByAD_Tab_ID.get(AD_Tab_ID);
				if (mTabVO == null)
				{
					//  Create TabVO
					mTabVO = GridTabVO.create(mWindowVO, TabNo, rs, isReadonly, onlyCurrentRows);
					if (mTabVO == null)
					{
						if (firstTab)
						{
							logger.warn("First tab could not be created for {}. Skip creating the window.\nThe error message was: {}",
									mWindowVO,
									mWindowVO.loadErrorMessages);
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
					tabsByAD_Tab_ID.put(AD_Tab_ID, mTabVO);
					TabNo++; //  must be same as mWindow.getTab(x)
					firstTab = false;
				}
				else
				{
					mTabVO.loadAdditionalLanguage(rs);
				}
			}

			//  No Tabs
			if (TabNo == 0 || tabsByAD_Tab_ID.isEmpty())
			{
				final WindowLoadException windowLoadException = new WindowLoadException("No tabs", null, mWindowVO.getName(), mWindowVO.getAdWindowId());
				final String loadErrorMessages = mWindowVO.loadErrorMessages != null ? StringUtils.trimBlankToNull(mWindowVO.loadErrorMessages.toString()) : null;
				if (loadErrorMessages != null)
				{
					windowLoadException.setParameter("loadErrorMessages", loadErrorMessages);
				}
				throw windowLoadException;
			}

			return ImmutableList.copyOf(tabsByAD_Tab_ID.values());
		}
		catch (SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}   //  createTabs

	private GridWindowVO(
			final Properties ctx,
			final int windowNo,
			@NonNull final AdWindowId adWindowId,
			final boolean loadAllLanguages,
			final boolean applyRolePermissions)
	{
		super();
		this.ctx = ctx;
		this.windowNo = windowNo;
		this.adWindowId = adWindowId;
		this.loadAllLanguages = loadAllLanguages;
		this.applyRolePermissions = applyRolePermissions;
	}

	private GridWindowVO(final GridWindowVO from, final int windowNo)
	{
		this(from.ctx, windowNo, from.adWindowId, from.loadAllLanguages, from.applyRolePermissions);

		this.name = from.name;
		this.nameTrls = from.nameTrls == null ? null : new HashMap<>(from.nameTrls);
		this.description = from.description;
		this.descriptionTrls = from.descriptionTrls == null ? null : new HashMap<>(from.descriptionTrls);
		this.help = from.help;
		this.helpTrls = from.helpTrls == null ? null : new HashMap<>(from.helpTrls);

		this.WindowType = from.WindowType;
		this.AD_Image_ID = from.AD_Image_ID;
		this.AD_Color_ID = from.AD_Color_ID;
		this._isReadWrite = from._isReadWrite;
		this.WinWidth = from.WinWidth;
		this.WinHeight = from.WinHeight;
		this._isSOTrx = from._isSOTrx;
		this.IsOneInstanceOnly = from.IsOneInstanceOnly;

		//
		// Tabs
		this._BaseTable_ID = from._BaseTable_ID;
		//
		final List<GridTabVO> fromTabs = from._tabs;
		if (fromTabs != null)
		{
			final List<GridTabVO> tabsClone = new ArrayList<>();
			for (final GridTabVO fromTab : fromTabs)
			{
				final GridTabVO cloneTab = fromTab.clone(from.ctx, windowNo);
				if (cloneTab == null)
				{
					throw new AdempiereException("Failed cloning tab: " + fromTab);
				}
				tabsClone.add(cloneTab);
			}

			this._tabs = ImmutableList.copyOf(tabsClone);
		}

		this.updateContext();
	}

	/**
	 * Properties
	 */
	private Properties ctx; // FIXME: get rid of GridWindwoVO.ctx
	/**
	 * Window Number
	 */
	private final int windowNo; // FIXME: get rid of GridWindwoVO.WindowNo

	/**
	 * Window
	 */
	private final AdWindowId adWindowId;
	private final boolean loadAllLanguages;
	private final boolean applyRolePermissions;

	/**
	 * Name
	 */
	private String name = "";
	private Map<String, String> nameTrls = null;
	/**
	 * Desription
	 */
	private String description = "";
	private Map<String, String> descriptionTrls = null;
	/**
	 * Help
	 */
	private String help = "";
	private Map<String, String> helpTrls = null;
	/**
	 * Window Type
	 */
	private String WindowType = "";
	/**
	 * Image
	 */
	private int AD_Image_ID = 0;
	/**
	 * Color
	 */
	private int AD_Color_ID = 0;
	/**
	 * Read Write
	 */
	private Boolean _isReadWrite = null;
	/**
	 * Window Width
	 */
	private int WinWidth = 0;
	/**
	 * Window Height
	 */
	private int WinHeight = 0;
	/**
	 * Sales Order Trx
	 */
	private boolean _isSOTrx = false;

	/**
	 * Tabs contains GridTabVO elements
	 */
	private List<GridTabVO> _tabs = null;
	/**
	 * Base Table (AD_Table_ID)
	 */
	private int _BaseTable_ID = 0;

	/**
	 * Qyery
	 */
	public static final String WINDOWTYPE_QUERY = "Q";
	/**
	 * Transaction
	 */
	public static final String WINDOWTYPE_TRX = "T";
	/**
	 * Maintenance
	 */
	public static final String WINDOWTYPE_MMAINTAIN = "M";

	/**
	 * Set Context including contained elements
	 *
	 * @param newCtx context
	 */
	public void setCtx(final Properties newCtx)
	{
		ctx = newCtx;
		final List<GridTabVO> tabs = getTabs();
		if (tabs != null)
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
	public GridWindowVO clone(final int windowNo)
	{
		try
		{
			return new GridWindowVO(this, windowNo);
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
		{
			return;
		}
		if (loadErrorMessages == null)
		{
			loadErrorMessages = new StringBuffer();
		}
		if (loadErrorMessages.length() > 0)
		{
			// Don't add this message
			if (checkEmpty)
			{
				return;
			}

			loadErrorMessages.append("\n");
		}
		loadErrorMessages.append(message);
	}

	public AdWindowId getAdWindowId()
	{
		return adWindowId;
	}

	public int getWindowNo()
	{
		return windowNo;
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

			if (childTabLevel == masterTabLevel)
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

		if (nameTrl == null)
		{
			return;
		}

		if (nameTrls == null)
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

		if (descriptionTrl == null)
		{
			return;
		}

		if (descriptionTrls == null)
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

		if (helpTrl == null)
		{
			return;
		}

		if (helpTrls == null)
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

	boolean isLoadAllLanguages()
	{
		return loadAllLanguages;
	}

	boolean isApplyRolePermissions()
	{
		return applyRolePermissions;
	}
}

