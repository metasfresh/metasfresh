/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.elasticsearch.model;

import com.google.common.collect.ImmutableList;

public interface I_T_ES_FTS_Search_Result
{
	String Table_Name = "T_ES_FTS_Search_Result";

	String COLUMNNAME_Search_UUID = "Search_UUID";
	String COLUMNNAME_Line = "Line";
	String COLUMNNAME_Created = "Created";

	String COLUMNNAME_IntKey1 = "IntKey1";
	String COLUMNNAME_IntKey2 = "IntKey2";
	String COLUMNNAME_IntKey3 = "IntKey3";
	ImmutableList<String> COLUMNNAME_IntKeys = ImmutableList.of(COLUMNNAME_IntKey1, COLUMNNAME_IntKey2, COLUMNNAME_IntKey3);

	String COLUMNNAME_StringKey1 = "StringKey1";
	String COLUMNNAME_StringKey2 = "StringKey2";
	ImmutableList<String> COLUMNNAME_StringKeys = ImmutableList.of(COLUMNNAME_StringKey1, COLUMNNAME_StringKey2);

	ImmutableList<String> COLUMNNAME_ALL_Keys = ImmutableList.<String>builder()
			.addAll(COLUMNNAME_IntKeys)
			.addAll(COLUMNNAME_StringKeys)
			.build();

	String COLUMNNAME_JSON = "JSON";
}
