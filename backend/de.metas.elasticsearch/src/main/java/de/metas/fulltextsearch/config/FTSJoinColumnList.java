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

package de.metas.fulltextsearch.config;

import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class FTSJoinColumnList implements Iterable<FTSJoinColumn>
{
	private final ImmutableList<FTSJoinColumn> joinColumns;

	private FTSJoinColumnList(@NonNull final List<FTSJoinColumn> joinColumns)
	{
		Check.assumeNotEmpty(joinColumns, "at least one JOIN column shall be defined");
		this.joinColumns = ImmutableList.copyOf(joinColumns);
	}

	public static FTSJoinColumnList ofList(@NonNull final List<FTSJoinColumn> joinColumns)
	{
		return new FTSJoinColumnList(joinColumns);
	}

	public int size()
	{
		return joinColumns.size();
	}

	@Override
	public Iterator<FTSJoinColumn> iterator()
	{
		return joinColumns.iterator();
	}

	public String buildJoinCondition(
			@NonNull final String targetTableNameOrAlias,
			@Nullable final String selectionTableNameOrAlias)
	{
		Check.assumeNotEmpty(targetTableNameOrAlias, "targetTableNameOrAlias not empty");

		final String selectionTableNameOrAliasWithDot = StringUtils.trimBlankToOptional(selectionTableNameOrAlias)
				.map(alias -> alias + ".")
				.orElse("");

		final StringBuilder sql = new StringBuilder();
		for (final FTSJoinColumn joinColumn : joinColumns)
		{
			if (sql.length() > 0)
			{
				sql.append(" AND ");
			}

			if (joinColumn.isNullable())
			{
				sql.append("(")
						.append(selectionTableNameOrAliasWithDot).append(joinColumn.getSelectionTableColumnName()).append(" IS NULL")
						.append(" OR ")
						.append(targetTableNameOrAlias).append(".").append(joinColumn.getTargetTableColumnName())
						.append(" IS NOT DISTINCT FROM ")
						.append(selectionTableNameOrAliasWithDot).append(joinColumn.getSelectionTableColumnName())
						.append(")");
			}
			else
			{
				sql.append(targetTableNameOrAlias).append(".").append(joinColumn.getTargetTableColumnName())
						.append("=")
						.append(selectionTableNameOrAliasWithDot).append(joinColumn.getSelectionTableColumnName());
			}
		}

		return sql.toString();
	}
}
