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

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

@EqualsAndHashCode(doNotUseGetters = true)
public final class TableName
{
	private final String tableName;

	private static final Interner<TableName> interner = Interners.newStrongInterner();

	private TableName(@NonNull final String tableName)
	{
		final String tableNameNorm = StringUtils.trimBlankToNull(tableName);
		if (tableNameNorm == null)
		{
			throw new AdempiereException("Invalid table name: `" + tableName + "`");
		}

		this.tableName = tableNameNorm;
	}

	public static TableName ofString(@NonNull final String string)
	{
		return interner.intern(new TableName(string));
	}

	@Nullable
	public static TableName ofNullableString(@Nullable final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		return stringNorm != null
				? ofString(stringNorm)
				: null;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	public String getAsString()
	{
		return tableName;
	}

	public boolean equalsIgnoreCase(@Nullable final String otherTableName)
	{
		return tableName.equalsIgnoreCase(otherTableName);
	}

	public static boolean equals(@Nullable TableName tableName1, @Nullable TableName tableName2)
	{
		return Objects.equals(tableName1, tableName2);
	}
}
