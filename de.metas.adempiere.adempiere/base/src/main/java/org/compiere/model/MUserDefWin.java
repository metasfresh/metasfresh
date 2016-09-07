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


import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.compiere.util.Util.ArrayKey;

/**
 * Window Customization
 * @author Teo Sarca, teo.sarca@gmail.com
 * 			<li>BF [ 2726889 ] Finish User Window (AD_UserDef*) functionality
 */
public class MUserDefWin extends X_AD_UserDef_Win
{
	/**
	 *
	 */
	private static final long serialVersionUID = 4180714433818623955L;

	/** Static Cache : Window,Role,User -> array of window customizations */
	private static final CCache<ArrayKey, MUserDefWin[]> s_cache = new CCache<ArrayKey, MUserDefWin[]>(Table_Name, 20, 0);
	/** No Windows (i.e. empty array) */
	private static final MUserDefWin[] NoWindows = new MUserDefWin[]{};

	/**
	 * Get existing User Defined Windows for Role or User
	 * @param ctx context
	 * @param AD_Window_ID window ID
	 * @return array of window customizations or empty array
	 */
	private static MUserDefWin[] get (Properties ctx, int AD_Window_ID)
	{
		if (AD_Window_ID <= 0)
		{
			return NoWindows;
		}
		final int AD_Client_ID = Env.getAD_Client_ID(ctx);
		final int AD_Org_ID = Env.getAD_Org_ID(ctx);
		final int AD_User_ID = Env.getAD_User_ID(ctx);
		final int AD_Role_ID= Env.getAD_Role_ID(ctx);

		// Try from cache
		final ArrayKey key = new ArrayKey(AD_Window_ID, AD_Client_ID, AD_Org_ID, AD_User_ID, AD_Role_ID);
		MUserDefWin[] arr = s_cache.get(key);
		if (arr != null)
		{
			return arr;
		}

		// Load from DB
		final String whereClause = COLUMNNAME_AD_Window_ID+"=?"
							+" AND (AD_Client_ID=? OR AD_Client_ID=0)"
							+" AND (AD_Org_ID=? OR AD_Org_ID=0)"
							+" AND (AD_Role_ID=? OR AD_Role_ID IS NULL)"
							+" AND (AD_User_ID=? OR AD_User_ID IS NULL)";
		final List<MUserDefWin> list = new Query(ctx, Table_Name, whereClause, null)
			.setParameters(AD_Window_ID, AD_Client_ID, AD_Org_ID, AD_Role_ID, AD_User_ID)
			.setOnlyActiveRecords(true)
			.setOrderBy("AD_Client_ID, AD_Org_ID, COALESCE(AD_User_ID,0), COALESCE(AD_Role_ID,0)")
			.list();
		arr = list.toArray(new MUserDefWin[list.size()]);
		if (arr.length > 0)
		{
			s_cache.put(key, arr);
		}
		else
		{
			s_cache.put(key, NoWindows);
		}
		return arr;
	}

	/**
	 * Apply customizations to given GridWindowVO
	 * @param vo
	 */
	public static void apply(GridWindowVO vo)
	{
		for (MUserDefWin uw : get(vo.getCtx(), vo.getAD_Window_ID()))
		{
//			vo.Name = uw.getName();
//			vo.Description = uw.getDescription();
//			vo.Help = uw 	.getHelp();
			vo.setReadWrite(!uw.isReadOnly());
		}
	}

	/**
	 * Apply customizations to given GridTabVO
	 * @param vo
	 * @return true if tab is displayed, false if tab is hidden
	 */
	public static boolean apply(GridTabVO vo)
	{
		boolean isDisplayed = true;
		for (MUserDefWin uw : get(vo.getCtx(), vo.getAD_Window_ID()))
		{
			MUserDefTab ut = uw.getTab(vo.getAD_Tab_ID());
			if (ut != null)
			{
				ut.apply(vo);
				isDisplayed = ut.isDisplayed();
			}
		}
		return isDisplayed;
	}

	/**
	 * Apply customizations to given GridFieldVO
	 * @param vo
	 */
	public static void apply(GridFieldVO vo)
	{
		for (MUserDefWin uw : get(vo.getCtx(), vo.AD_Window_ID))
		{
			MUserDefTab ut = uw.getTab(vo.AD_Tab_ID);
			if (ut == null)
			{
				continue;
			}
			MUserDefField uf = ut.getField(vo.AD_Field_ID);
			if (uf != null)
			{
				uf.apply(vo);
			}
		}
	}

	public MUserDefWin(Properties ctx, int AD_UserDef_Win_ID, String trxName)
	{
		super(ctx, AD_UserDef_Win_ID, trxName);
	}

	public MUserDefWin(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * @param reload if true, do not use cache
	 * @return tab customizations for this window
	 */
	public MUserDefTab[] getTabs(boolean reload)
	{
		if (!reload && m_tabs != null)
		{
			return m_tabs;
		}
		final String whereClause = MUserDefTab.COLUMNNAME_AD_UserDef_Win_ID+"=?";
		final List<MUserDefTab> list = new Query(getCtx(), MUserDefTab.Table_Name, whereClause, get_TrxName())
								.setParameters(get_ID())
								.setOnlyActiveRecords(true)
								.setOrderBy(MUserDefTab.COLUMNNAME_AD_Tab_ID)
								.list();
		//
		m_tabs = list.toArray(new MUserDefTab[list.size()]);
		return m_tabs;
	}
	private MUserDefTab[] m_tabs = null;

	/**
	 * @param AD_Tab_ID
	 * @return tab customization of given AD_Tab_ID or null if not found
	 */
	public MUserDefTab getTab(int AD_Tab_ID)
	{
		if (AD_Tab_ID <= 0)
		{
			return null;
		}
		for (MUserDefTab tab : getTabs(false))
		{
			if (AD_Tab_ID == tab.getAD_Tab_ID())
			{
				return tab;
			}
		}
		return null;
	}

	@Override
	public String toString()
	{
		return getClass().getName()+"[ID="+get_ID()
		+", AD_Org_ID="+getAD_Org_ID()
		+", AD_Role_ID="+getAD_Role_ID()
		+", AD_User_ID="+getAD_User_ID()
		+", AD_Window_ID="+getAD_Window_ID()
		+", Name="+getName()
		+"]";
	}
}
