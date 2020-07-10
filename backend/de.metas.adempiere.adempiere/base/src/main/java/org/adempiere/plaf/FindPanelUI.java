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
 * UI settings for embedded find panel.
 * 
 * If you are looking for the collapsible component which is used to wrap the find panel in standard windows, check {@link AdempiereTaskPaneUI}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class FindPanelUI
{
	public static final String KEY_StandardWindow_Height = "FindPanel.StandardWindow.Height";
	// NOTE: task 08592: the width prior to this task was 160; changing it in order to add yet another search field to the invoice candidate window.
	public static final int DEFAULT_StandardWindow_Height = 200;
	//
	public static final String KEY_Dialog_Height = "FindPanel.Dialog.Height";
	public static final int DEFAULT_Dialog_Height = 200;

	public static Object[] getUIDefaults()
	{
		return new Object[] {
				KEY_StandardWindow_Height, DEFAULT_StandardWindow_Height
				, KEY_Dialog_Height, DEFAULT_Dialog_Height
		};
	}

}
