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

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.table.api.TableName;

import java.util.List;
import java.util.function.Predicate;

@EqualsAndHashCode
@ToString
public class FTSConfigSourceTablesMap
{
	public static final FTSConfigSourceTablesMap EMPTY = new FTSConfigSourceTablesMap(ImmutableList.of());

	private final ImmutableListMultimap<TableName, FTSConfigSourceTable> sourceTablesByTableName;
	private final ImmutableListMultimap<FTSConfigId, FTSConfigSourceTable> sourceTablesByConfigId;

	private FTSConfigSourceTablesMap(@NonNull final List<FTSConfigSourceTable> sourceTables)
	{
		this.sourceTablesByTableName = Multimaps.index(sourceTables, FTSConfigSourceTable::getTableName);
		this.sourceTablesByConfigId = Multimaps.index(sourceTables, FTSConfigSourceTable::getFtsConfigId);
	}

	public static FTSConfigSourceTablesMap ofList(@NonNull final List<FTSConfigSourceTable> sourceTables)
	{
		return !sourceTables.isEmpty() ? new FTSConfigSourceTablesMap(sourceTables) : EMPTY;
	}

	public ImmutableSet<TableName> getTableNames()
	{
		return sourceTablesByTableName.keySet();
	}

	public ImmutableList<FTSConfigSourceTable> getByTableName(@NonNull final TableName tableName)
	{
		return sourceTablesByTableName.get(tableName);
	}

	public ImmutableList<FTSConfigSourceTable> getByConfigId(@NonNull final FTSConfigId ftsConfigId)
	{
		return sourceTablesByConfigId.get(ftsConfigId);
	}

	public FTSConfigSourceTablesMap filter(@NonNull final Predicate<FTSConfigSourceTable> filter)
	{
		final ImmutableCollection<FTSConfigSourceTable> sourceTables = sourceTablesByConfigId.values();
		final ImmutableList<FTSConfigSourceTable> sourceTablesFiltered = sourceTables
				.stream()
				.filter(filter)
				.collect(ImmutableList.toImmutableList());

		return sourceTables.size() != sourceTablesFiltered.size()
				? ofList(sourceTablesFiltered)
				: this;
	}
}
