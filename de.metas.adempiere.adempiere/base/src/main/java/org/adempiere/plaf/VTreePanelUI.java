package org.adempiere.plaf;

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

/**
 * UI settings for VTreePanel (the tree which is displayed in main window and in standard windows which have a tree)
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class VTreePanelUI
{
	/** Shall we display the "search toolbar" on top (if true) or on bottom (if false) */
	public static final String KEY_SearchPanelAnchorOnTop = "VTreePanelUI.SearchPanelAnchorOnTop";
	public static final boolean DEFAULT_SearchPanelAnchorOnTop = true;

	public static Object[] getUIDefaults()
	{
		return new Object[] {
				KEY_SearchPanelAnchorOnTop, DEFAULT_SearchPanelAnchorOnTop
		};
	}

}
