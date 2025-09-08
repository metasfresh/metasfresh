/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package org.adempiere.ad.table.api;

import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

@EqualsAndHashCode(doNotUseGetters = true)
public class ColumnName
{
	private final String columnName;

	private ColumnName(@NonNull final String columnName)
	{
		final String columnNameNorm = StringUtils.trimBlankToNull(columnName);
		if (columnNameNorm == null)
		{
			throw new AdempiereException("Invalid column name: `" + columnName + "`");
		}

		this.columnName = columnNameNorm;
	}

	public static ColumnName ofString(@NonNull final String string)
	{
		return new ColumnName(string);
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	public String getAsString()
	{
		return columnName;
	}

	public boolean endsWith(@NonNull final String suffix)
	{
		return columnName.endsWith(suffix);
	}
}
