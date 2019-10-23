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
 * VPanel UI.
 * 
 * @author tsa
 *
 */
public class VPanelUI
{
	public static final String KEY_IncludedTabHeight = "VPanel.IncludedTab.Height";
	public static final int DEFAULT_IncludedTabHeight = 150;

	public static final String KEY_StandardWindow_FieldColumns = "VPanel.StandardWindow.FieldColumns";
	public static final int DEFAULT_StandardWindow_FieldColumns = 3;

	public static final String KEY_StandardWindow_LabelMinWidth = "VPanel.StandardWindow.LabelMinWidth";
	public static final int DEFAULT_StandardWindow_LabelMinWidth = 160;

	public static final String KEY_StandardWindow_LabelMaxWidth = "VPanel.StandardWindow.LabelMaxWidth";
	public static final int DEFAULT_StandardWindow_LabelMaxWidth = 160;

	public static Object[] getUIDefaults()
	{
		return new Object[] {
				KEY_StandardWindow_FieldColumns, DEFAULT_StandardWindow_FieldColumns
				, KEY_StandardWindow_LabelMinWidth, DEFAULT_StandardWindow_LabelMinWidth
				, KEY_StandardWindow_LabelMaxWidth, DEFAULT_StandardWindow_LabelMaxWidth
				, KEY_IncludedTabHeight, DEFAULT_IncludedTabHeight
		};
	}
}
