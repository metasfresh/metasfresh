/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.               *
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
package org.adempiere.plaf;

import javax.swing.plaf.ColorUIResource;

/**
 * Adempiere default Blue Metal Color Theme
 *
 * @author Jorg Janke, Adam Michau
 * @version $Id: AdempiereThemeBlueMetal.java,v 1.2 2006/07/30 00:52:24 jjanke Exp $
 */
public class AdempiereThemeInnova extends org.adempiere.plaf.AdempiereTheme
{
	/**
	 * Adempiere default Theme Blue Metal
	 */
	public AdempiereThemeInnova()
	{
		setDefault();
		s_theme = this;
		s_name = NAME;
	}	// AdempiereThemeBlueMetal

	/** Name */
	public static final String NAME = "Adempiere Theme";

	/**
	 * Set Defaults
	 */
	@Override
	public void setDefault()
	{
		/** Blue 51,51,102 */
		primary0 = new ColorUIResource(103, 152, 203);
		/** Blue 102, 102, 153 */
		// protected static ColorUIResource primary1;
		primary1 = new ColorUIResource(101, 138, 187);
		/** Blue 153, 153, 204 */
		primary2 = new ColorUIResource(103, 152, 203);
		/** Blue 204, 204, 255 */
		primary3 = new ColorUIResource(233, 238, 245); //

		/** Black */
		// secondary0 = new ColorUIResource(0, 0, 0);
		/** Gray 102, 102, 102 */
		// protected static ColorUIResource secondary1;
		secondary1 = new ColorUIResource(190, 179, 153);
		/** Gray 153, 153, 153 */
		// protected static ColorUIResource secondary2;
		secondary2 = new ColorUIResource(246, 239, 224);
		/** BlueGray 214, 224, 234 - background */
		// protected static ColorUIResource secondary3;
		secondary3 = new ColorUIResource(251, 248, 241);
		/** White */
		// secondary4 = new ColorUIResource(255, 255, 255);

		/** Black */
		black = BLACK;
		/** White */
		white = WHITE;

		/** Background for mandatory fields */
		mandatory = new ColorUIResource(233, 238, 245); // blueish
		/** Background for fields in error 180,220,143 */
		error = new ColorUIResource(220, 241, 203); // green ;
		/** Background for inactive fields */
		inactive = new ColorUIResource(241, 239, 222);// 241,239,222
		/** Background for info fields */
		info = new ColorUIResource(251, 248, 251); // somewhat white

		/** Foreground Text OK */
		txt_ok = new ColorUIResource(0, 153, 255); // blue ;
		/** Foreground Text Error */
		txt_error = new ColorUIResource(255, 0, 51); // red ;

		/** Black */
		// secondary0 = new ColorUIResource(0, 0, 0);
		/** Control font */
		controlFont = null;
		_getControlTextFont();
		/** System font */
		systemFont = null;
		_getSystemTextFont();
		/** User font */
		userFont = null;
		_getUserTextFont();
		/** Small font */
		smallFont = null;
		_getSubTextFont();
		/** Window Title font */
		windowFont = null;
		_getWindowTitleFont();
		/** Menu font */
		menuFont = null;
		_getMenuTextFont();
	}   // setDefault

}	// AdempiereThemeBlueMetal
