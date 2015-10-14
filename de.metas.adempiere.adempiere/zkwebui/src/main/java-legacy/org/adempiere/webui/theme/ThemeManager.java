/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.webui.theme;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.compiere.model.MSysConfig;
import org.compiere.util.CLogger;

/**
 * 
 * @author hengsin
 * @author Teo Sarca, metas.ro SRL
 * 
 */
public final class ThemeManager
{

	// theme key in MSysConfig
	public static final String ZK_THEME = "ZK_THEME";
	// default theme
	public static final String ZK_THEME_DEFAULT = "default";

	public static final String ZK_THEME_IMPL = "ZK_THEME_IMPL";
	// default theme
	public static final String ZK_THEME_IMPL_DEFAULT = DefaultThemeImpl.class.getCanonicalName();

	private static final CLogger log = CLogger.getCLogger(ThemeManager.class);

	private static final Map<String, ITheme> themes = new HashMap<String, ITheme>();

	public static void registerThemeImpl(String themeName, ITheme themeImpl)
	{
		themes.put(themeName, themeImpl);
		themeImpl.setName(themeName);
	}

	/**
	 * @return active theme implementation
	 */
	public static ITheme getThemeImpl()
	{
		String themeName = getTheme();
		ITheme themeImpl = themes.get(themeName);
		if (themeImpl != null)
			return themeImpl;

		String classname = MSysConfig.getValue(ZK_THEME_IMPL, ZK_THEME_IMPL_DEFAULT);
		try
		{
			themeImpl = (ITheme)ThemeManager.class.getClassLoader().loadClass(classname).newInstance();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
			themeImpl = new DefaultThemeImpl();
		}
		registerThemeImpl(themeName, themeImpl);
		return themeImpl;
	}

	/**
	 * @return name of active theme
	 */
	private static String getTheme()
	{
		return MSysConfig.getValue(ZK_THEME, ZK_THEME_DEFAULT);
	}

	/**
	 * @return url for large logo
	 */
	@Deprecated
	public static String getLargeLogo()
	{
		return getThemeImpl().getLargeLogo();
	}

	/**
	 * @return url for small logo
	 */
	@Deprecated
	public static String getSmallLogo()
	{
		return getThemeImpl().getSmallLogo();
	}

	/**
	 * @return url of theme stylesheet
	 */
	@Deprecated
	public static String getStyleSheet()
	{
		return getThemeImpl().getStyleSheet();
	}

	/**
	 * @return url of theme stylesheet by browser
	 */
	@Deprecated
	public static String getStyleSheetByBrowser()
	{
		return getThemeImpl().getStyleSheetByBrowser();
	}

	/**
	 * @return title text for the browser window
	 */
	@Deprecated
	public static String getBrowserTitle()
	{
		return getThemeImpl().getBrowserTitle();
	}

	/**
	 * @return url for right panel
	 */
	@Deprecated
	public static String getLoginRightPanel()
	{
		return getThemeImpl().getLoginRightPanel();
	}

	/**
	 * @return url for left panel
	 */
	@Deprecated
	public static String getLoginLeftPanel()
	{
		return getThemeImpl().getLoginLeftPanel();
	}

	/**
	 * @return url for top panel
	 */
	@Deprecated
	public static String getLoginTopPanel()
	{
		return getThemeImpl().getLoginTopPanel();
	}

	/**
	 * @return url for bottom panel
	 */
	@Deprecated
	public static String getLoginBottomPanel()
	{
		return getThemeImpl().getLoginBottomPanel();
	}

	/**
	 * @return url for browser icon
	 */
	@Deprecated
	public static String getBrowserIcon()
	{
		return getThemeImpl().getBrowserIcon();
	}
}
