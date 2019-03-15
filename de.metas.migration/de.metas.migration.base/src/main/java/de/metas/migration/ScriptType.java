package de.metas.migration;

import java.util.HashMap;
import java.util.Map;

import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.migration.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
public class ScriptType
{
	public static ScriptType ofFileExtension(final String fileExtension)
	{
		return scriptTypesByFileExtension.computeIfAbsent(
				normalizeFileExtension(fileExtension),
				ScriptType::new);
	}

	public static final ScriptType NONE = new ScriptType("");
	public static final ScriptType SQL = new ScriptType("sql");

	private static final Map<String, ScriptType> scriptTypesByFileExtension = new HashMap<>();
	static
	{
		scriptTypesByFileExtension.put(NONE.getFileExtension(), NONE);
		scriptTypesByFileExtension.put(SQL.getFileExtension(), SQL);
	}

	private final String fileExtension;

	private ScriptType(@NonNull final String fileExtension)
	{
		this.fileExtension = normalizeFileExtension(fileExtension);
	}

	private static final String normalizeFileExtension(final String fileExtension)
	{
		if (fileExtension == null)
		{
			return "";
		}

		return fileExtension.trim().toLowerCase();
	}
}
