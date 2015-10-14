/**
 * 
 */
package org.adempiere.webui.theme;

/*
 * #%L
 * ADempiere ERP - ZkWebUI Lib
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


import org.adempiere.webui.AdempiereWebUI;
import org.compiere.model.MSysConfig;

/**
 * Default Theme Implementation
 * @author hengsin
 * @author Teo Sarca, metas.ro SRL
 */
public class DefaultThemeImpl implements ITheme
{
	//theme resource url prefix
	public static final String THEME_PATH_PREFIX = "/theme/";
	
	public static final String LOGIN_TOP_PANEL_ZUL = "/login-top.zul";
	public static final String LOGIN_BOTTOM_PANEL_ZUL = "/login-bottom.zul";
	public static final String LOGIN_LEFT_PANEL_ZUL = "/login-left.zul";
	public static final String LOGIN_RIGHT_PANEL_ZUL = "/login-right.zul";

	//logo
	public static final String LOGIN_LOGO_IMAGE = "/images/login-logo.png";
	public static final String HEADER_LOGO_IMAGE = "/images/header-logo.png";
	public static final String BROWSER_ICON_IMAGE= "/images/icon.png";

	//stylesheet url
	public static final String THEME_STYLESHEET = "/css/theme.css.dsp";
	public static final String THEME_STYLESHEET_BY_BROWSER = "/css/theme*.css.dsp*";
	
	private String theme;
	
	/**
	 * @return url for large logo
	 */
	@Override
	public String getLargeLogo()
	{
		String def = THEME_PATH_PREFIX + theme + LOGIN_LOGO_IMAGE;
		return MSysConfig.getValue("ZK_LOGO_LARGE", def);
	}
	public org.zkoss.image.Image getLargeLogoImage()
	{
		return null;
	}

	@Override
	public String getSmallLogo()
	{
		String def = THEME_PATH_PREFIX + theme + HEADER_LOGO_IMAGE;
		String url = MSysConfig.getValue("ZK_LOGO_SMALL", null);
		if (url == null)
			url = MSysConfig.getValue("WEBUI_LOGOURL", def);
		return url;
	}
	public org.zkoss.image.Image getSmallLogoImage()
	{
		return null;
	}
	
	@Override
	public void setName(String themeName)
	{
		this.theme = themeName;
	}

	@Override
	public String getStyleSheet()
	{
		return THEME_PATH_PREFIX + theme + THEME_STYLESHEET;
	}

	@Override
	public String getStyleSheetByBrowser()
	{
		return THEME_PATH_PREFIX + theme + THEME_STYLESHEET_BY_BROWSER;
	}

	@Override
	public String getBrowserTitle()
	{
		return MSysConfig.getValue("ZK_BROWSER_TITLE", AdempiereWebUI.APP_NAME);
	}

	@Override
	public String getLoginRightPanel()
	{
		return THEME_PATH_PREFIX + theme + LOGIN_RIGHT_PANEL_ZUL;
	}

	@Override
	public String getLoginLeftPanel()
	{
		return THEME_PATH_PREFIX + theme + LOGIN_LEFT_PANEL_ZUL;
	}

	@Override
	public String getLoginTopPanel()
	{
		return THEME_PATH_PREFIX + theme + LOGIN_TOP_PANEL_ZUL;
	}

	@Override
	public String getLoginBottomPanel()
	{
		return THEME_PATH_PREFIX + theme + LOGIN_BOTTOM_PANEL_ZUL;
	}

	@Override
	public String getBrowserIcon()
	{
		String def = THEME_PATH_PREFIX + theme + BROWSER_ICON_IMAGE;
		return MSysConfig.getValue("ZK_BROWSER_ICON", def);
	}

	@Override
	public String getCssName(String name)
	{
		return name;
	}
}
