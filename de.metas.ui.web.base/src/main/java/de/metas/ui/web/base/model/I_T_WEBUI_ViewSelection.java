package de.metas.ui.web.base.model;

import java.util.List;

import com.google.common.collect.ImmutableList;

/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface I_T_WEBUI_ViewSelection
{
	String Table_Name = "T_WEBUI_ViewSelection";

	String COLUMNNAME_UUID = "UUID";

	/** i.e. the sequence number for ordering */
	String COLUMNNAME_Line = "Line";

	//
	// Record IDs / Aggregate record IDs / Root record IDs
	// IMPORTANT: keep in sync with I_T_WEBUI_ViewSelectionLine !!!
	String COLUMNNAME_IntKey1 = "IntKey1";
	String COLUMNNAME_IntKey2 = "IntKey2";
	final List<String> COLUMNNAME_IntKeys = ImmutableList.of(COLUMNNAME_IntKey1, COLUMNNAME_IntKey2);

	String COLUMNNAME_StringKey1 = "StringKey1";
	final List<String> COLUMNNAME_StringKeys = ImmutableList.of(COLUMNNAME_StringKey1);
}
