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
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class FTSModelIndexerRegistry
{
	private static final Logger logger = LogManager.getLogger(FTSModelIndexerRegistry.class);

	private final ImmutableListMultimap.Builder<TableName, FTSModelIndexer> indexersBySourceTableName = ImmutableListMultimap.builder();

	public FTSModelIndexerRegistry(@NonNull final Optional<List<FTSModelIndexer>> indexers)
	{
		// this.indexersBySourceTableName = indexers.orElseGet(ImmutableList::of)
		// 		.stream()
		// 		.flatMap(indexer -> indexer.getHandledSourceTableNames()
		// 				.stream()
		// 				.map(sourceTableName -> GuavaCollectors.entry(sourceTableName, indexer)))
		// 		.collect(GuavaCollectors.toImmutableMap());

		indexers.orElseGet(ImmutableList::of).stream()
						.forEach(indexer -> indexer.getHandledSourceTableNames().stream()
								.forEach(table -> indexersBySourceTableName.put(table, indexer)));


		logger.info("Indexers: {}", this.indexersBySourceTableName);
	}

	public Optional<List<FTSModelIndexer>> getBySourceTableName(@NonNull final TableName sourceTableName)
	{
		return Optional.ofNullable(indexersBySourceTableName.build().get(sourceTableName));
	}

	public List<FTSModelIndexer> getBySourceTableNames(@NonNull final Collection<TableName> sourceTableNames)
	{
		final Set<FTSModelIndexer> indexes = new HashSet<>();

		for(final TableName tableName : sourceTableNames)
		{
			indexes.addAll(indexersBySourceTableName.build().get(tableName));
		}

		return indexes.stream().toList();


		// return sourceTableNames.stream()
		// 		.distinct()
		// 		.map(indexersBySourceTableName::get)
		// 		.filter(Objects::nonNull)
		// 		.distinct()
		// 		.collect(ImmutableList.toImmutableList());
	}

}
