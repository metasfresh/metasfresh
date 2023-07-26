package de.metas.ui.web.menu.datatypes.json;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

import de.metas.ui.web.menu.MenuNode.MenuNodeType;

/*
 * #%L
 * metasfresh-webui-api
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public enum JSONMenuNodeType
{
	group, //
	window, newRecord, //
	process, report, //
	board //
	;

	public static final JSONMenuNodeType fromNullable(final MenuNodeType type)
	{
		if (type == null)
		{
			return null;
		}

		final JSONMenuNodeType jsonType = type2json.get(type);
		if (jsonType == null)
		{
			throw new IllegalArgumentException("Cannot convert " + type + " to " + JSONMenuNodeType.class);
		}
		return jsonType;
	}

	private static final BiMap<MenuNodeType, JSONMenuNodeType> type2json = ImmutableBiMap.<MenuNodeType, JSONMenuNodeType> builder()
			.put(MenuNodeType.Group, group)
			.put(MenuNodeType.Window, window)
			.put(MenuNodeType.NewRecord, newRecord)
			.put(MenuNodeType.Process, process)
			.put(MenuNodeType.Report, report)
			.put(MenuNodeType.Board, board)
			.build();

	public final MenuNodeType toMenuNodeType()
	{
		final MenuNodeType type = type2json.inverse().get(this);
		if (type == null)
		{
			throw new IllegalArgumentException("Cannot convert " + type + " to " + MenuNodeType.class);
		}
		return type;
	}
}
