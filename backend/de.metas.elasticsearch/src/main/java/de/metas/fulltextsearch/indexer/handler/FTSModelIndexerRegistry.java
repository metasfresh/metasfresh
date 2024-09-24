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

package de.metas.fulltextsearch.indexer.handler;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.ad.table.api.TableName;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class FTSModelIndexerRegistry
{
	private static final Logger logger = LogManager.getLogger(FTSModelIndexerRegistry.class);

	private final ImmutableListMultimap<TableName, FTSModelIndexer> indexersBySourceTableName;

	public FTSModelIndexerRegistry(@NonNull final Optional<List<FTSModelIndexer>> indexers)
	{
		this.indexersBySourceTableName = indexBySourceTableName(indexers);
		logger.info("Indexers: {}", this.indexersBySourceTableName);
	}

	@NotNull
	private static ImmutableListMultimap<TableName, FTSModelIndexer> indexBySourceTableName(final @NotNull Optional<List<FTSModelIndexer>> indexers)
	{
		final ImmutableListMultimap.Builder<TableName, FTSModelIndexer> builder = ImmutableListMultimap.builder();
		for (final FTSModelIndexer indexer : indexers.orElseGet(ImmutableList::of))
		{
			for (final TableName table : indexer.getHandledSourceTableNames())
			{
				builder.put(table, indexer);
			}
		}
		return builder.build();
	}

	public List<FTSModelIndexer> getBySourceTableNames(@NonNull final Collection<TableName> sourceTableNames)
	{
		return sourceTableNames.stream()
				.flatMap(sourceTableName -> indexersBySourceTableName.get(sourceTableName).stream())
				.collect(ImmutableList.toImmutableList());
	}

}
