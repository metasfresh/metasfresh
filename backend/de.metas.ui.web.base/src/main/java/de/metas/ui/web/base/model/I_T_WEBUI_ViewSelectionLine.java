package de.metas.ui.web.base.model;

import com.google.common.collect.ImmutableList;

/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public interface I_T_WEBUI_ViewSelectionLine
{
	String Table_Name = "T_WEBUI_ViewSelectionLine";

	String COLUMNNAME_UUID = "UUID";

	/** Line ID / Included record ID */
	String COLUMNNAME_Line_ID = "Line_ID";

	//
	// Record IDs / Aggregate record IDs / Root record IDs
	// IMPORTANT: keep in sync with I_T_WEBUI_ViewSelection !!!
	String COLUMNNAME_IntKey1 = "IntKey1";
	String COLUMNNAME_IntKey2 = "IntKey2";
	String COLUMNNAME_IntKey3 = "IntKey3";
	String COLUMNNAME_IntKey4 = "IntKey4";
	ImmutableList<String> COLUMNNAME_IntKeys = ImmutableList.of(COLUMNNAME_IntKey1, COLUMNNAME_IntKey2, COLUMNNAME_IntKey3, COLUMNNAME_IntKey4);

	String COLUMNNAME_StringKey1 = "StringKey1";
	String COLUMNNAME_StringKey2 = "StringKey2";
	String COLUMNNAME_StringKey3 = "StringKey3";
	ImmutableList<String> COLUMNNAME_StringKeys = ImmutableList.of(COLUMNNAME_StringKey1, COLUMNNAME_StringKey2, COLUMNNAME_StringKey3);
}
